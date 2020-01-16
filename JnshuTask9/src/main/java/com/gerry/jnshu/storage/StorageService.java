package com.gerry.jnshu.storage;

import com.gerry.jnshu.core.utils.UUID;
import com.gerry.jnshu.pojo.StorageInfo;

import java.io.InputStream;

public class StorageService {

    private String active;
    private Storage storage;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    //存储一个文件对象
    public StorageInfo store(InputStream inputStream
            ,long contentLength
            ,String contentType
            ,String fileName) throws Exception{
        String key = generateKey(fileName);
        storage.store(inputStream,contentLength,contentType,fileName);
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setName(fileName);
        storageInfo.setSize((int) contentLength);
        storageInfo.setType(contentType);
        storageInfo.setKey(key);
        storageInfo.setUrl(generateUrl(key));

        return storageInfo;

    }

    private String generateKey(String originalFilename){
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        return UUID.fastUUID().toString()+suffix;

    }

    public String generateUrl(String keyName){
        return storage.generateUrl(keyName);
    }



}
