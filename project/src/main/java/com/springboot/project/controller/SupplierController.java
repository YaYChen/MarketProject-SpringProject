package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Supplier;
import com.springboot.project.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class SupplierController {

    @Autowired
    private HttpServletRequest request;

    private SupplierService supplierService;
    private JwtHelper jwtHelper;

    @Autowired
    public SupplierController(SupplierService supplierService,JwtHelper jwtHelper){
        this.supplierService = supplierService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/p/getAllSupplier")
    @ResponseBody
    public ResponseEntity<List<Supplier>> getAllSupplier(){
        int userId = Integer.getInteger(
                jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId").toString());
        return ResponseEntity.ok(supplierService.getAllSupplier(userId));
    }

    @GetMapping(value = "/p/getSupplierByID")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierByID(@RequestParam("id") int id){
        int userId = Integer.getInteger(
                jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId").toString());
        return ResponseEntity.ok(supplierService.getSupplierByID(id,userId));
    }

    @PostMapping(value = "/p/updateSupplier")
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

    @PostMapping(value = "/p/insertSupplier")
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

    @DeleteMapping(value = "/p/deleteSupplier")
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
