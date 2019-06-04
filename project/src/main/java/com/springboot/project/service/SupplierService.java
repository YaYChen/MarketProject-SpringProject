package com.springboot.project.service;

import com.springboot.project.entity.Supplier;
import com.springboot.project.mapper.SupplierMapper;
import org.mybatis.spring.annotation.MapperScan;
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

    @Cacheable(value = "supplierCache",key = "allSupplier")
    public List<Supplier> getAllSupplier(){
        return supplierMapper.selectAll();
    }

    @Cacheable(value = "supplierCache",key = "#id")
    public Supplier getSupplierByID(int id){
        return supplierMapper.getSupplierByID(id);
    }

    @CachePut(value = "supplierCache",key = "#result.id")
    public Supplier updateSupplier(Supplier supplier) throws Exception{
        this.supplierMapper.update(supplier);
        return supplier;
    }

    public Supplier insertSupplier(Supplier supplier) throws Exception{
        int id = this.supplierMapper.insert(supplier);
        return this.getSupplierByID(id);
    }

    @CacheEvict(value = "supplierCache",key = "#id")
    public void deleteSupplier(int id) throws Exception{
        this.supplierMapper.delete(id);
    }
}
