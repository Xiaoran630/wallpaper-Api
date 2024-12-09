package com.example.wallpaper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.wallpaper.service.WallpaperService;
import com.example.wallpaper.common.constant.CommonConstant;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Configuration
public class WallpaperImpl implements WallpaperService {
    private static MinioClient minioClient = null;
    @Value(value = "${server.minio.minio_url}")
    private String minioUrl;
    @Value(value = "${server.minio.minio_name}")
    private String minioName;
    @Value(value = "${server.minio.minio_pass}")
    private String minioPass;
    @Value(value = "${server.minio.bucketName}")
    private String bucketName;

    private void initMinio() {
        if(!minioUrl.endsWith(CommonConstant.Str_SLASH)){
            minioUrl = minioUrl.concat(CommonConstant.Str_SLASH);
        }
        if (minioClient == null) {
            try {
                minioClient = MinioClient.builder()
                        .endpoint(minioUrl, 9000, true)
                        .credentials(minioName, minioPass)
                        .build();
                log.info("MinIO连接成功 {}", minioUrl);
            } catch (Exception e) {
                log.error("MinIO连接失败 {}", e.getMessage());
            }
        }
    }

    @Override
    public List<Item> listObjects(String bucketName) throws IOException {
        try {
            List<Item> items = new ArrayList<>();
            // 列出指定存储桶中的所有对象
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            log.error("Error occurred while listing objects in MinIO {}", e.getMessage());
            throw new IOException("Error occurred while listing objects in MinIO", e);
        }
    }


    @Override
    public JSONObject randomImageUrl() throws Exception {
        initMinio();
        // 列出指定存储桶中的所有对象
        List<Item> objects = listObjects(bucketName);
        JSONObject obj = new JSONObject(new LinkedHashMap<>());
        if (!objects.isEmpty()) {
            // 从对象列表中随机选择一个对象
            Random random = new Random();
            Item randomItem = objects.get(random.nextInt(objects.size()));
            log.info("randomImageUrl-path: {}/{}", bucketName, randomItem.objectName());
//            GetPresignedObjectUrlArgs wallpaper = GetPresignedObjectUrlArgs.builder()
//                    .method(Method.GET).bucket(bucketName).object(randomItem.objectName()).build();
//            String url = minioClient.getPresignedObjectUrl(wallpaper);
            String url = minioUrl + bucketName + CommonConstant.Str_SLASH + randomItem.objectName();
            obj.put("url", url);
            obj.put("path", bucketName + CommonConstant.Str_SLASH + randomItem.objectName());
            return obj;
        }
        return null;
    }

    @SneakyThrows
    @Override
    public byte[] randomImageFile() throws IOException {
        initMinio();
        // 列出指定存储桶中的所有对象
        List<Item> objects = listObjects(bucketName);
        // 从对象列表中随机选择一个对象
        Random random = new Random();
        if (!objects.isEmpty()) {
            Item randomItem = objects.get(random.nextInt(objects.size()));
            log.info("randomImageFile-path: {}/{}", bucketName, randomItem.objectName());
            GetObjectResponse response =
                    minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(randomItem.objectName()).build());
            return response.readAllBytes();
        }
        return null;
    }
}
