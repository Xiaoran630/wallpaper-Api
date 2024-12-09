package com.example.wallpaper.service;

import com.alibaba.fastjson.JSONObject;
import io.minio.messages.Item;

import java.io.IOException;
import java.util.List;

public interface WallpaperService {
    List<Item> listObjects(String bucketName) throws IOException;

    JSONObject randomImageUrl() throws Exception;

    byte[] randomImageFile() throws IOException;
}
