package com.example.accesslimitproject.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/websocket/{userId}")
@Slf4j
public class WebSocketServer {

    /**
     * 存放在线连接数
     */
    private static AtomicInteger onlineCount =new AtomicInteger(0);

    /**
     * 用来存放每个客户端对应的WebSocketServer对象
     */
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap =new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，通过这个来给客户端发送数据
     */
    private Session session;

    /**
     * 接收userId
     */
    private String userId ="";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){
        this.session =session;
        this.userId =userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        }else{
            webSocketMap.put(userId,this);
            addOnlineCount();
        }
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("连接成功！");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 服务器接收消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        log.info("用户消息:" + userId + ",报文:" + message);
        if(!StringUtils.isEmpty(message)){
            try{
                JSONObject jsonObject = JSON.parseObject(message);
                jsonObject.put("fromUserId",this.userId);
                String toUserId =jsonObject.getString("toUserId");
                if(!StringUtils.isEmpty(toUserId)&&webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                }else{
                    log.error("请求的 userId:" + toUserId + "不在该服务器上");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session seesion,Throwable error){
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized AtomicInteger getOnlineCount(){
        return onlineCount;
    }

    public static synchronized  void addOnlineCount(){
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount.getAndDecrement();
    }


}
