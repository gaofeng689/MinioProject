package com.example.accesslimitproject.interceptor;


import com.example.accesslimitproject.annotation.AccessLimit;
import com.example.accesslimitproject.constant.ResultCode;
import com.example.accesslimitproject.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AccessLimitInteceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${accesslimit.second}")
    private Long second =10L;
    @Value("${accesslimit.time}")
    private Long time =3L;
    @Value("${accesslimit.lockTime}")
    private Long lockTime =60L;

    private static final String LOCK_PREFIX ="LOCK";
    private static final String COUNT_PREFIX ="COUNT";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod targetMethod =(HandlerMethod) handler;
            AccessLimit targetClassAnnotation = targetMethod.getMethod().getDeclaringClass().getAnnotation(AccessLimit.class);
            boolean isClassHave =false;
            String uri = request.getRequestURI();
            String ip = request.getRemoteAddr();
            long second =0L;
            long maxTime =0L;
            long forbiddenTime =0L;
            if(!Objects.isNull(targetClassAnnotation)){
                log.info("目标接口方法所在类上有@AccessLimit注解");
                isClassHave =true;
                second = targetClassAnnotation.second();
                maxTime = targetClassAnnotation.maxTime();
                forbiddenTime = targetClassAnnotation.forbiddenTime();
            }
            //取出目标方法中的AccessLimit注解
            AccessLimit accessLimit = targetMethod.getMethodAnnotation(AccessLimit.class);
            if(!Objects.isNull(accessLimit)){
                second =accessLimit.second();
                maxTime =accessLimit.maxTime();
                forbiddenTime =accessLimit.forbiddenTime();
                if(isForbindden(second,maxTime,forbiddenTime,ip,uri)){
                    throw new CommonException(ResultCode.ACCESS_FREQUENT);
                }
            }else{
                if(isClassHave&&isForbindden(second,maxTime,forbiddenTime,ip,uri)){
                    throw new CommonException(ResultCode.ACCESS_FREQUENT);
                }
            }
        }
        return true;
    }

    private boolean isForbindden(long second, long maxTime, long forbiddenTime, String ip, String uri) {
        String lockKey =LOCK_PREFIX+ip+uri;
        Object isLock =redisTemplate.opsForValue().get(lockKey);
        if(Objects.isNull(isLock)){
            //还未被禁用
            String countKey =COUNT_PREFIX+ip+uri;
            Object count = redisTemplate.opsForValue().get(countKey);
            if(Objects.isNull(count)){
                //首次访问
                redisTemplate.opsForValue().set(countKey,1,second,TimeUnit.SECONDS);
            }else{
                if((Integer)count<maxTime){
                    redisTemplate.opsForValue().increment(countKey);
                }else{
                    log.info("{}禁用访问{}",ip,uri);
                    //禁用
                    redisTemplate.opsForValue().set(lockKey,1,forbiddenTime,TimeUnit.SECONDS);
                    //删除统计
                    redisTemplate.delete(countKey);
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }


}
