package com.ats.bse_tv.bean;

/**
 * Created by MIRACLEINFOTAINMENT on 04/01/18.
 */

public class LastOrderList {

    private int orderDetailsId;
    private int orderId;
    private int itemId;
    private int quantity;
    private float rate;
    private int status;
    private int isMixer;
    private String itemName;
    private float maxRate;
    private float minRate;

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsMixer() {
        return isMixer;
    }

    public void setIsMixer(int isMixer) {
        this.isMixer = isMixer;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(float maxRate) {
        this.maxRate = maxRate;
    }

    public float getMinRate() {
        return minRate;
    }

    public void setMinRate(float minRate) {
        this.minRate = minRate;
    }

    @Override
    public String toString() {
        return "LastOrder [orderDetailsId=" + orderDetailsId + ", orderId=" + orderId + ", itemId=" + itemId
                + ", quantity=" + quantity + ", rate=" + rate + ", status=" + status + ", isMixer=" + isMixer
                + ", itemName=" + itemName + ", maxRate=" + maxRate + ", minRate=" + minRate + "]";
    }

}
