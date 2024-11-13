package com.example.wallpaper.controller;

import com.example.wallpaper.service.WallpaperService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * @author
 **/
@CrossOrigin
@RestController
@RequestMapping
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    @GetMapping(value = "/wallpaper",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    ResponseEntity<String> getWallpaper() throws Exception {
        String targetUrl = wallpaperService.randomImageUrl();
        return new ResponseEntity<>(targetUrl, HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    byte[] getWallpaperTest(InputStream inputStream) throws Exception {
        String targetUrl = wallpaperService.randomImageUrl();
        return targetUrl.getBytes();
    }

}
