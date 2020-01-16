package com.gerry.jnshu.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.stream.Stream;

public class AliyunStorage implements Storage {

    private String bucketName;
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBaseUrl() {
        return "https://" + bucketName + "." + endPoint + "/";
    }

    public OSS getOssClient() {
        return new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            objectMetadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata);
            PutObjectResult putObjectResult = getOssClient().putObject(putObjectRequest);


    }

    @Override
    public void delete(String keyName) {
        try{
            getOssClient().deleteObject(bucketName,keyName);
        }catch (Exception e){

        }
    }

    @Override
    public String generateUrl(String keyName) {

        return getBaseUrl()+keyName;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String keyName) {
        return null;
    }

    @Override
    public Resource loadAsResource(String keyName) {
        try {
            URL url = new URL(getBaseUrl() + keyName);
            Resource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }


}
