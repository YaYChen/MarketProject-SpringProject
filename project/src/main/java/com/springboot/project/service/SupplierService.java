package com.springboot.project.service;

import com.springboot.project.entity.Supplier;
import com.springboot.project.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {

    private SupplierMapper supplierMapper;

    @Autowired
    public SupplierService(SupplierMapper supplierMapper){this.supplierMapper = supplierMapper;}

    @Cacheable(value = "supplierCache",key = "#root.methodName",unless = "#result == null")
    public List<Supplier> getAllSupplier(int userId){
        return supplierMapper.selectAll(userId);
    }

    @Cacheable(value = "supplierCache",key = "#id",unless = "#result == null")
    public Supplier getSupplierByID(int id,int userId){
        return supplierMapper.getSupplierByID(id,userId);
    }

    @CachePut(value = "supplierCache",key = "#result.id")
    public Supplier updateSupplier(Supplier supplier) throws Exception{
        this.supplierMapper.update(supplier);
        return supplier;
    }

    public Supplier insertSupplier(Supplier supplier) throws Exception{
        int id = this.supplierMapper.insert(supplier);
        return this.getSupplierByID(id,supplier.getCreateUser().getId());
    }

    @CacheEvict(value = "supplierCache",key = "#id")
    public void deleteSupplier(int id) throws Exception{
        this.supplierMapper.delete(id);
    }
}
