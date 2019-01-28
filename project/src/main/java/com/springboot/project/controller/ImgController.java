package com.springboot.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class ImgController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImgController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${project.imgFilePath}")
    private String imgFilePath;


    @RequestMapping(value = "/upload-img",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> saveImg(@RequestBody MultipartFile file){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            String filePath = imgFilePath + fileName;
            File desFile = new File(filePath);
            if(!desFile.getParentFile().exists()){
                desFile.mkdirs();
            }
            try {
                file.transferTo(desFile);
                map.put("message", fileName);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } catch (Exception e) {
                map.put("message", e.getMessage());
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
            }
        } else {
            map.put("message", "上传失败，因为文件是空的.");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/show-img",method = RequestMethod.GET,produces = "image/jpg")
    public ResponseEntity<?> showImg(@RequestParam("fileName") String fileName){
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + imgFilePath + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
