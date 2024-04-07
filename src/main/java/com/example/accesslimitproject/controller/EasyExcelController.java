package com.example.accesslimitproject.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.example.accesslimitproject.domain.DemoData;
import com.example.accesslimitproject.domain.DemoWriteData;
import com.example.accesslimitproject.util.DemoDataListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping("write")
    public String write(){
        String filePath ="C:\\Users\\admin\\Desktop\\给高锋\\easyexcel2.xlsx";
        EasyExcel.write(filePath, DemoWriteData.class).sheet("模板").doWrite(data());   //导出数据
        Set<String> columnNames =new HashSet<>();
        columnNames.add("date");
//        EasyExcel.write(filePath,DemoWriteData.class).excludeColumnFieldNames(columnNames).sheet("模板").doWrite(data());  //排除某行
//        EasyExcel.write(filePath,DemoWriteData.class).includeColumnFieldNames(columnNames).sheet("模板").doWrite(data());  //包括某行
        return "success";
    }
    private List<DemoWriteData> data() {
        List<DemoWriteData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoWriteData data = new DemoWriteData();
            data.setString("字符串" + i);
            data.setDate("字符串" + i);
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
