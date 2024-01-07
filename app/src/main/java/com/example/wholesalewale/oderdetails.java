package com.example.wholesalewale;

import java.util.ArrayList;

public class oderdetails {
    ArrayList<String> links;
    ArrayList<String> customerDetails;

    public String getItemName() {
        return itemName;
    }

    String itemName;

    public String getShopName() {
        return shopName;
    }

    String shopName;

    public ArrayList<String> getLinks() {
        return links;
    }

    public ArrayList<String> getCustomerDetails() {
        return customerDetails;
    }

    public double getPrice() {
        return price;
    }

    public Integer getQnt() {
        return qnt;
    }

    double price;
    Integer qnt;

    public oderdetails( String itemNAme,Integer qnt,ArrayList<String> links, double price, ArrayList<String> customerDetails) {
        this.links = links;
        this.customerDetails = customerDetails;
        this.price = price;
        this.qnt = qnt;
        this.itemName=itemNAme;
    }
    public oderdetails( String itemNAme,Integer qnt,ArrayList<String> links, double price,String shopName) {
        this.links = links;
        this.price = price;
        this.qnt = qnt;
        this.itemName=itemNAme;
        this.shopName=shopName;
    }


}
