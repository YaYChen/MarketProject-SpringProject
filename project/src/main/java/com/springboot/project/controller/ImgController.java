package com.springboot.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ImgController.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImgController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${project.imgFilePath}")
    private String imgFilePath;


    @RequestMapping(value = "/upload-img",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> saveImg(@RequestBody MultipartFile file){
        logger.info("Upload img...");
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
                logger.info("Upload img success...");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } catch (Exception e) {
                map.put("message", e.getMessage());
                logger.error("Upload img cause abnormal: " + e.getMessage());
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
            }
        } else {
            map.put("message", "上传失败，因为文件是空的.");
            logger.error("Upload img fail: img file is empty!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/show-img",method = RequestMethod.GET,produces = "image/jpg")
    public ResponseEntity<?> showImg(@RequestParam("fileName") String fileName){
        logger.info("Return img...");
        try {
            logger.info("Return img url: " + (imgFilePath + fileName));
            return ResponseEntity.ok(resourceLoader.getResource("file:" + imgFilePath + fileName));
        } catch (Exception e) {
            logger.error("Return img cause abnormal: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
