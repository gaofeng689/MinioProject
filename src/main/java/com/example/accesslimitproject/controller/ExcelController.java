package com.example.accesslimitproject.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;

@RestController
public class ExcelController {

    @GetMapping("upload")
    public Object upload() throws Exception{
        File file =new File("C:\\Users\\admin\\Desktop\\给高锋\\bbb.xlsx");    //初始文件
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println(sheet.getPhysicalNumberOfRows());
        JSONArray jsonArray =new JSONArray();
        int sum =0;
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            String hetongNo = sheet.getRow(i).getCell(0).getStringCellValue();
            String hetongName = sheet.getRow(i).getCell(1).getStringCellValue();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("hetongNo",hetongNo);
            jsonObject.put("hetongName",hetongName);
            jsonArray.add(jsonObject);
            sum++;
        }
        System.out.println(sum);
        workbook.close();

        //填写到指定的excel位置
        int a =0;
        for (int i = 0; i < sum; i++) {
            String targetFilePath ="C:\\Users\\admin\\Desktop\\给高锋\\zzz.xlsx";  //模板文件
            File targetFile =new File(targetFilePath);
            Workbook targetWorkbook = WorkbookFactory.create(targetFile);
            Sheet targetSheet = targetWorkbook.getSheetAt(0);
            targetSheet.getRow(3).getCell(2).setCellValue(jsonArray.getJSONObject(i).getString("hetongNo"));
            targetSheet.getRow(4).getCell(2).setCellValue(jsonArray.getJSONObject(i).getString("hetongName"));

            String newFilePath ="C:\\Users\\admin\\Desktop\\给高锋\\gf\\"+jsonArray.getJSONObject(i).getString("hetongNo")+".xlsx";
            File newFile =new File(newFilePath);
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            targetWorkbook.write(new FileOutputStream(newFilePath));
            targetWorkbook.close();
            a++;
        }
        System.out.println(a);

        return jsonArray;
    }
}
