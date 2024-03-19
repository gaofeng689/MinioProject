package com.example.accesslimitproject.util;

import com.example.accesslimitproject.config.MinioConfiguration;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioConfiguration minioConfiguration;

    /**
     * 查看存储bucketanme是否存在
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName){
        boolean found;
        try {
            found =minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     * @param bucketName
     * @return
     */
    public boolean makeBucket(String bucketName){
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     * @param bucketName
     * @return
     */
    public boolean removeBucket(String bucketName){
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取全部bucket
     * @return
     */
    public List<Bucket> getAllBuckets(){
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public String upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if(StringUtils.isEmpty(originalFilename)){
            throw new RuntimeException();
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-","")
                + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName ="20240319"+"/"+fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioConfiguration.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * 预览图片
     * @param fileName
     * @return
     */
    public String preview(String fileName){
        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs.Builder()
                .bucket(minioConfiguration.getBucketName())
                .object(fileName)
                .method(Method.GET)
                .build();
        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件下载
     * @param fileName
     * @param res
     */
    public void download(String fileName, HttpServletResponse res){
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioConfiguration.getBucketName()).object(fileName).build();
        try{
            GetObjectResponse response = minioClient.getObject(objectArgs);
            byte[] bytes =new byte[1024];
            int length;
            try{
                FastByteArrayOutputStream os =new FastByteArrayOutputStream();
                while((length =response.read(bytes))!=-1){
                    os.write(bytes,0,length);
                }
                response.close();
                os.flush();
                byte[] array =os.toByteArray();
                os.close();
                res.setCharacterEncoding("utf-8");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try{
                    ServletOutputStream outputStream = res.getOutputStream();
                    outputStream.write(array);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查看文件对象
     * @return
     */
    public List<Item> listObjects(){
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(minioConfiguration.getBucketName()).build());
        List<Item> items =new ArrayList<>();
        try{
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return items;
    }

    public boolean remove(String fileName){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfiguration.getBucketName())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
