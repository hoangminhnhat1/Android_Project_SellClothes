package com.example.androidproject_sellclothes.Model;



public class Cart {
    private String pid,pname,price,quantity,idDetailorder;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String idDetailorder) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.idDetailorder = idDetailorder;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdDetailorder() {
        return idDetailorder;
    }

    public void setIdDetailorder(String idDetailorder) {
        this.idDetailorder = idDetailorder;
    }
//    public String getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(String discount) {
//        this.discount = discount;
//    }
}
