package com.example.accesslimitproject.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoWriteData {

    @ExcelProperty(value ={"主标题","字符串标题"},index =0)
    private String string;

    @ExcelProperty(value ={"主标题","日期标题"},index =1)
    private String date;

    @ExcelProperty(value ={"主标题","数字标题"},index =3)  //这里设置3会导致第二列为空，index不是必写的
    private Double doubleData;

    /**
     *
     */
    @ExcelIgnore
    private String ignore;
}
