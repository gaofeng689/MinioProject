package com.example.accesslimitproject.util;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdviceUtil {

    /**
     * 全局异常处理
     * @param e
     */
    @ExceptionHandler(NullPointerException.class)
    public void customException(Exception e){
        System.out.println("空指针异常");
    }

    /**
     * 全局数据绑定
     * @return
     */
    @ModelAttribute(name ="md")
    public Map<String,Object> data(){
        HashMap<String,Object> map =new HashMap<>();
        map.put("age", 99);
        map.put("gender", "男");
        return map;
    }

    /**
     * 全局数据预处理
     * @param binder
     */
    @InitBinder("a")
    public void a(WebDataBinder binder){
        binder.setFieldDefaultPrefix("a.");
    }

    @InitBinder("b")
    public void b(WebDataBinder binder){
        binder.setFieldDefaultPrefix("b.");
    }
}
