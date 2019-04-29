package com.springboot.project.controller;

import com.springboot.project.entity.Supplier;
import com.springboot.project.mapper.SupplierMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class SupplierController {

    private SupplierMapper supplierMapper;

    @Autowired
    public SupplierController(SupplierMapper mapper){
        this.supplierMapper = mapper;
    }

    @GetMapping(value = "/getAllSupplier")
    public ResponseEntity<List<Supplier>> getAllSupplier(){
        return ResponseEntity.ok(supplierMapper.selectAll());
    }

    @GetMapping(value = "/getSupplierByID")
    public ResponseEntity<Supplier> getSupplierByID(@RequestParam("id") String id){
        return ResponseEntity.ok(supplierMapper.getSupplierByID(Integer.getInteger(id)));
    }

    @PostMapping(value = "/updateSupplier")
    public ResponseEntity<Map<String,Object>> update(@RequestBody Supplier supplier){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierMapper.update(supplier);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/insertSupplier")
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Supplier supplier){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierMapper.insert(supplier);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/deleteSupplier")
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierMapper.delete(id);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
