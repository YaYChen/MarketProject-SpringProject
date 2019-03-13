package com.springboot.project.entity;

import javax.validation.constraints.NotNull;

public class OrderItem {
    @NotNull
    private long id = 0;
    @NotNull
    private long orderID;
    @NotNull
    private int productID;
    @NotNull
    private int quantity;

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

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
