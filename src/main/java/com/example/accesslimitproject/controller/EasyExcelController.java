package com.example.accesslimitproject.controller;

import com.alibaba.excel.EasyExcel;
import com.example.accesslimitproject.domain.DemoData;
import com.example.accesslimitproject.util.DemoDataListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EasyExcelController {

    @GetMapping("read")
    public String read(){
        String filePath ="C:\\Users\\admin\\Desktop\\给高锋\\easyexcel.xlsx";
//        EasyExcel.read(filePath, DemoData.class,new DemoDataListener()).sheet().doRead();
        EasyExcel.read(filePath, DemoData.class,new DemoDataListener()).doReadAll();
        return "success";
    }
}
