package com.example.accesslimitproject.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
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
//        EasyExcel.read(filePath, DemoData.class,new DemoDataListener()).doReadAll();   //读取多个sheet页
//        EasyExcel.read(filePath,DemoData.class,new DemoDataListener()).sheet().headRowNumber(1).doRead();  //一行表头

        //同步读取，不推荐使用，如果数据量大会把数据放到内存里
//        EasyExcel.read(filePath,DemoData.class,new DemoDataListener()).sheet().doReadSync();
        EasyExcel.read(filePath, DemoData.class,new DemoDataListener())
                // 需要读取批注 默认不读取
                .extraRead(CellExtraTypeEnum.COMMENT)
                // 需要读取超链接 默认不读取
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                // 需要读取合并单元格信息 默认不读取
                .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
        return "success";
    }
}
