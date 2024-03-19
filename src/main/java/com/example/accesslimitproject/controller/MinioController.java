package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.config.MinioConfiguration;
import com.example.accesslimitproject.util.MinioUtil;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfiguration minioConfiguration;

    @GetMapping("bucketExists")
    public boolean bucketExists(@RequestParam("bucketName")String bucketName){
        return minioUtil.bucketExists(bucketName);
    }

    @GetMapping("makeBucket")
    public boolean makeBucket(String bucketName) {
        return minioUtil.makeBucket(bucketName);
    }

    @GetMapping("/removeBucket")
    public boolean removeBucket(String bucketName) {
        return minioUtil.removeBucket(bucketName);
    }

    @GetMapping("/getAllBuckets")
    public List<Bucket> getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return allBuckets;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return minioConfiguration.getEndpoint() + "/" + minioConfiguration.getBucketName() + "/" + objectName;
        }
        return null;
    }

    @GetMapping("/preview")
    public String preview(@RequestParam("fileName") String fileName) {
        return minioUtil.preview(fileName);
    }

    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName,res);
    }
    @PostMapping("/delete")
    public boolean remove(String url) {
        String objName = url.substring(url.lastIndexOf(minioConfiguration.getBucketName()+"/") + minioConfiguration.getBucketName().length()+1);
        return minioUtil.remove(objName);
    }


}
