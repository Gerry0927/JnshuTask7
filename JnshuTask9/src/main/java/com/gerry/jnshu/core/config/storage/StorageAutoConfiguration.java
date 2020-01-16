package com.gerry.jnshu.core.config.storage;

import com.gerry.jnshu.storage.AliyunStorage;
import com.gerry.jnshu.storage.LocalStorage;
import com.gerry.jnshu.storage.StorageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.activation.ActivationID;

@Configuration
@EnableConfigurationProperties({StorageProperties.class})
public class StorageAutoConfiguration {

    private final StorageProperties storageProperties;

    public StorageAutoConfiguration(StorageProperties storageProperties){
        this.storageProperties = storageProperties;
    }

    @Bean
    public StorageService storageService(){
        String active = this.storageProperties.getActive();
        StorageService  storageService = new StorageService();
        if(active.equals("aliyun")){
            storageService.setStorage(aliyunStorage());
        }
        else{
            storageService.setStorage(localStorage());
        }
        return storageService;
    }

    @Bean
    public AliyunStorage aliyunStorage(){
        AliyunStorage aliyunStorage = new AliyunStorage();
        StorageProperties.Aliyun aliyun = this.storageProperties.getAliyun();
        aliyunStorage.setAccessKeyId(aliyun.getAccessKeyId());
        aliyunStorage.setAccessKeySecret(aliyun.getAccessKeySecret());
        aliyunStorage.setBucketName(aliyun.getBucketName());
        aliyunStorage.setEndPoint(aliyun.getEndpoint());
        return aliyunStorage;
    }

    @Bean
    public LocalStorage localStorage(){
        LocalStorage localStorage = new LocalStorage();
        StorageProperties.Local local = this.storageProperties.getLocal();
        localStorage.setStoragePath(local.getStoragePath());
        localStorage.setAddress(local.getAddress());
        return localStorage;
    }

}
