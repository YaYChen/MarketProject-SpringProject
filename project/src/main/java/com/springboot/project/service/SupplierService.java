package com.springboot.project.service;

import com.springboot.project.entity.Supplier;
import com.springboot.project.mapper.SupplierMapper;
import com.springboot.project.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private RedisUtil redisUtil;

    private List<Supplier> getAllSuppliersFromDB(int userId){
        return this.supplierMapper.selectAll(userId);
    }

    public List<Supplier> getAllSuppliers(int userId){
        Object cacheObject = this.redisUtil.hget(
                "supplierCache",
                "allSuppliers" + userId);
        List<Supplier> suppliers;
        if(cacheObject == null){
            suppliers = this.getAllSuppliersFromDB(userId);
            this.redisUtil.hset(
                    "supplierCache",
                    "allSuppliers" + userId,
                    suppliers);
        }else{
            suppliers = (List<Supplier>) cacheObject;
        }
        return suppliers;
    }

    public Supplier getSupplierByID(int id,int userId){
        for (Supplier item : this.getAllSuppliers(userId)) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public void updateSupplier(Supplier supplier) {
        this.supplierMapper.update(supplier);
        List<Supplier> suppliers = this.getAllSuppliers(supplier.getUserId());
        for (Supplier item : suppliers) {
            if(item.getId() == supplier.getId()){
                suppliers.set(suppliers.indexOf(item),supplier);
            }
        }
        this.redisUtil.hset(
                "supplierCache",
                "allSuppliers" + supplier.getUserId(),
                suppliers);
    }

    public void insertSupplier(Supplier supplier) {
        this.supplierMapper.insert(supplier);
        this.redisUtil.hset(
                "supplierCache",
                "allSuppliers" + supplier.getUserId(),
                this.getAllSuppliersFromDB(supplier.getUserId()));
    }

    public void deleteSupplier(int userId, int id) {
        this.supplierMapper.delete(id);
        List<Supplier> suppliers = this.getAllSuppliers(userId);
        for (Supplier item : suppliers) {
            if(item.getId() == id){
                suppliers.remove(item);
                break;
            }
        }
        this.redisUtil.hset(
                "supplierCache",
                "allSuppliers" + userId,
                suppliers);
    }
}
