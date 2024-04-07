package com.example.accesslimitproject.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.accesslimitproject.util.CustomStringStringConverter;
import lombok.Data;

@Data
public class DemoData {

    @ExcelProperty(index = 2,converter = CustomStringStringConverter.class)
    private String string;
    @ExcelProperty("字符串标题1")
    private String string1;
    @ExcelProperty("字符串标题2")
    private String string2;
}
