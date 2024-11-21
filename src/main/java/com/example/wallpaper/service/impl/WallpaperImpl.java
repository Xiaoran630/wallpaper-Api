package com.example.wallpaper.service.impl;

import com.example.wallpaper.service.WallpaperService;
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
        if (minioClient == null) {
            try {
                minioClient = MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(minioName, minioPass)
                        .build();
                log.info("MinIO连接成功 {}", minioClient);
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
    public String randomImageUrl() throws Exception {
        initMinio();
        // 列出指定存储桶中的所有对象
        List<Item> objects = listObjects(bucketName);
        if(!objects.isEmpty()){
            // 从对象列表中随机选择一个对象
            Random random = new Random();
            Item randomItem = objects.get(random.nextInt(objects.size()));
            GetPresignedObjectUrlArgs wallpaper = GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(randomItem.objectName()).build();
            return minioClient.getPresignedObjectUrl(wallpaper);
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
            if(!objects.isEmpty()){
                Item randomItem = objects.get(random.nextInt(objects.size()));
                GetObjectResponse response =
                        minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(randomItem.objectName()).build());
                return response.readAllBytes();
            }
            return null;
    }
}
