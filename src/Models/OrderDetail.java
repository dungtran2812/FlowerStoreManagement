/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author asus
 */
public class OrderDetail implements Serializable{
    String orderDetailId;
    String flowerId;
    int quanity;
    int flowerCost;

    public OrderDetail(String orderDetailId, String flowerId, int quanity, int flowerCost) {
        this.orderDetailId = orderDetailId;
        this.flowerId = flowerId;
        this.quanity = quanity;
        this.flowerCost = flowerCost;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public int getFlowerCost() {
        return flowerCost;
    }

    public void setFlowerCost(int flowerCost) {
        this.flowerCost = flowerCost;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailId=" + orderDetailId + ", flowerId=" + flowerId + ", quanity=" + quanity + ", flowerCost=" + flowerCost + '}';
    }
    
}
