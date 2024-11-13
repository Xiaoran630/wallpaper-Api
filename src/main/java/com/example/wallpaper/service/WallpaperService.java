package com.example.wallpaper.service;

import io.minio.messages.Item;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface WallpaperService {
    List<Item> listObjects(String bucketName) throws IOException;

    String randomImageUrl() throws Exception;
}
