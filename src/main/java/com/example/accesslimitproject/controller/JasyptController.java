package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.domain.Person;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JasyptController {

    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 加密
     * @param content
     * @return
     */
    @GetMapping("encrypt/{content}")
    public String encrypt(@PathVariable String content) {
        String encrypt = stringEncryptor.encrypt(content);
        return encrypt;
    }

    @GetMapping("desensitize")
    public Object desensitize() {
        Person user = new Person();
        user.setRealName("姓名姓名");
        user.setPhoneNumber("19796328206");
        user.setAddress("北京市北京市丰台区....");
        user.setIdCard("4333333333334334333");
        return user;
    }
}
