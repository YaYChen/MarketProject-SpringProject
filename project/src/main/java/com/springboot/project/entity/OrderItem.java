package com.springboot.project.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class OrderItem implements Serializable {
    @NotNull
    private long id = 0;
    @NotNull
    private long orderID;
    @NotNull
    private Product product;
    @NotNull
    private int quantity;
    @NotNull
    private int totalPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long order_id) {
        this.orderID = order_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
