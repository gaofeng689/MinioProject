package com.example.accesslimitproject.domain;

import com.example.accesslimitproject.annotation.Sensitive;
import com.example.accesslimitproject.constant.SensitiveStrategy;
import lombok.Data;

@Data
public class Person {
    @Sensitive(strategy = SensitiveStrategy.USERNAME)
    private String realName;

    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
    private String address;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phoneNumber;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;
}
