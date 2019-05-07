package com.springboot.project.service;

import com.springboot.project.entity.Supplier;
import com.springboot.project.mapper.SupplierMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@MapperScan("com.springboot.project.mapper")
@Transactional
public class SupplierService {

    private SupplierMapper supplierMapper;

    @Autowired
    public SupplierService(SupplierMapper supplierMapper){this.supplierMapper = supplierMapper;}

    public List<Supplier> getAllSupplier(){
        return supplierMapper.selectAll();
    }

    public Supplier getSupplierByID(int id){
        return supplierMapper.getSupplierByID(id);
    }

    public void updateSupplier(Supplier supplier) throws Exception{
        this.supplierMapper.update(supplier);
    }

    public void insertSupplier(Supplier supplier) throws Exception{
        this.supplierMapper.insert(supplier);
    }

    public void deleteSupplier(int id) throws Exception{
        this.supplierMapper.delete(id);
    }
}
