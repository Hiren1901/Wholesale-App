package com.example.wholesalewale;

import java.io.Serializable;
import java.util.ArrayList;

public class oderdetails implements Serializable {
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

    public Long getPrice() {
        return price;
    }

    public Integer getQnt() {
        return qnt;
    }

   Long price;
    Integer qnt;

    public oderdetails( String itemNAme,Integer qnt,ArrayList<String> links,Long price) {
        this.links = links;
        this.price = price;
        this.qnt = qnt;
        this.itemName=itemNAme;
    }
    public oderdetails( String itemNAme,Integer qnt,ArrayList<String> links, Long price,String shopName) {
        this.links = links;
        this.price = price;
        this.qnt = qnt;
        this.itemName=itemNAme;
        this.shopName=shopName;
    }
    public oderdetails(){}


}
