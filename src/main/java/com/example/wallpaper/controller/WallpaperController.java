package com.example.wallpaper.controller;

import com.example.wallpaper.service.WallpaperService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/wallpaper")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class WallpaperController {

    private final WallpaperService wallpaperService;

    @GetMapping(value = "/")
    ResponseEntity<byte[]> getWallpaper(HttpServletResponse response) throws Exception {
        //return ResponseEntity.ok()
        //        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // 根据实际情况设置 MIME 类型
        //        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
        //        .body(inputStream);
        byte[] bytes = wallpaperService.randomImageFile();
        if (bytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;")
                    .body(bytes);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body("<html><meta charset=\"UTF-8\"></meta><body><h1>Wallpaper-Api</h1><p>暂无壁纸，请联系管理员添加壁纸。</p></body></html>".getBytes());
    }

    @GetMapping(value = "/url")
    ResponseEntity<Object> getWallpaperUrl() throws Exception {
        String url = wallpaperService.randomImageUrl();
        JSONObject jsonObject = new JSONObject();
        if (url != null) {
            jsonObject.put("url", url);
            jsonObject.put("code", 200);
            jsonObject.put("msg", "success");
            return ResponseEntity.ok().body(jsonObject);
        }
        jsonObject.put("code", 500);
        jsonObject.put("msg", "暂无壁纸，请联系管理员添加壁纸。");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("暂无壁纸，请联系管理员添加壁纸。");
    }
}
