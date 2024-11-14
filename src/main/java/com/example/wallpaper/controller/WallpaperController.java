package com.example.wallpaper.controller;

import com.example.wallpaper.service.WallpaperService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 **/
@CrossOrigin
@RestController
@RequestMapping
@AllArgsConstructor(onConstructor_ = @Autowired)
public class WallpaperController {

    private final WallpaperService wallpaperService;

    @GetMapping(value = "/wallpaper")
    ResponseEntity<byte[]> getWallpaperTest(HttpServletResponse response) throws Exception {
        //return ResponseEntity.ok()
        //        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // 根据实际情况设置 MIME 类型
        //        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
        //        .body(inputStream);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;")
                .body(wallpaperService.randomImageFile());
    }

    @GetMapping(value = "/test",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    ResponseEntity<String> getWallpaper() throws Exception {
        String targetUrl = wallpaperService.randomImageUrl();
        return new ResponseEntity<>(targetUrl, HttpStatus.OK);
    }
}
