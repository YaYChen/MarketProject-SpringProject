package com.springboot.project.controller;

import com.springboot.project.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class ImgController {

    private final StorageService storageService;

    @Autowired
    public ImgController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload-img",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> saveImg(@RequestBody MultipartFile file){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                storageService.store(file);
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
            Resource file = storageService.loadAsResource(fileName);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
