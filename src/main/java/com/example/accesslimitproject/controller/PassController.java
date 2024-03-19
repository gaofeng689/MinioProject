package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.annotation.AccessLimit;
import com.example.accesslimitproject.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description:
 */

@RestController
@RequestMapping("/pass")
@Slf4j
@AccessLimit(second = 20,maxTime = 2,forbiddenTime = 40)
public class PassController {

    @GetMapping("/getOne/{id}")
    public Result getOne(@PathVariable("id") Integer id){
        log.info("执行[pass]-getOne()方法，id为{}", id);
        return Result.SUCCESS();
    }


    @GetMapping("/get")
    @AccessLimit(second = 20,maxTime = 5,forbiddenTime = 60)
    public Result get(){
        log.info("执行【pass】-get()方法");
        return Result.SUCCESS();
    }
    @PostMapping("/post")
    public Result post(){
        log.info("执行【pass】-post()方法");
        return Result.SUCCESS();
    }

    @PutMapping("/put")
    public Result put(){
        log.info("执行【pass】-put()方法");
        return Result.SUCCESS();
    }

    @DeleteMapping("/delete")
    public Result delete(){
        log.info("执行【pass】-delete()方法");
        return Result.SUCCESS();
    }
}
