package com.example.accesslimitproject.exception;

import com.example.accesslimitproject.constant.ResultCode;

/**
 * @author: Zero
 * @time: 2023-2-13
 * @description:
 */

public class CommonException extends Exception{
    public CommonException(String context) {
        super(context);
    }

    public CommonException(ResultCode context) {
        super(context.message());
    }
}
