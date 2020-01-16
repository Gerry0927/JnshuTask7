package com.gerry.jnshu.storage;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LocalStorage implements Storage {

    private String storagePath;
    private String address;
    private Path rootLocation;

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        this.rootLocation = Paths.get(storagePath);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {
        try {
            Files.copy(inputStream,rootLocation.resolve(keyName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation,1).filter(new Predicate<Path>() {
                @Override
                public boolean test(Path path) {
                    return !path.equals(rootLocation);
                }
            }).map(new Function<Path, Path>() {
                @Override
                public Path apply(Path path) {
                    return rootLocation.relativize(path);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String keyName) {
        return rootLocation.resolve(keyName);
    }

    @Override
    public Resource loadAsResource(String keyName) {
        return null;
    }

    @Override
    public void delete(String keyName) {
        Path file = load(keyName);
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateUrl(String keyName) {
        return address+keyName;
    }
}
