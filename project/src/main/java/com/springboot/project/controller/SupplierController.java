package com.springboot.project.controller;

import com.springboot.project.entity.Supplier;
import com.springboot.project.service.SupplierService;
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

    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @GetMapping(value = "/getAllSupplier")
    @ResponseBody
    public ResponseEntity<List<Supplier>> getAllSupplier(){
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }

    @GetMapping(value = "/getSupplierByID")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierByID(@RequestParam("id") int id){
        return ResponseEntity.ok(supplierService.getSupplierByID(id));
    }

    @PostMapping(value = "/updateSupplier")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> update(@RequestBody Supplier supplier){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierService.updateSupplier(supplier);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/insertSupplier")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Supplier supplier){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierService.insertSupplier(supplier);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/deleteSupplier")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            supplierService.deleteSupplier(id);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
