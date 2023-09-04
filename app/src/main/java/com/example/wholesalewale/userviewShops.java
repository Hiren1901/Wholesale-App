package com.example.wholesalewale;

import java.util.ArrayList;

public class userviewShops {

    public String getShopname() {
        return shopname;
    }

    String shopname;

    public String getProductname() {
        return productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public String getAbout() {
        return about;
    }
    public userviewShops(){

    }
    public userviewShops(String shopname,String productname, int quantity, double price, int color, String material, String about, ArrayList<String> links) {
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.material = material;
        this.about = about;
        this.links=links;
        this.shopname=shopname;
    }

    String productname;
    int quantity=0;


    public ArrayList<String> getLinks() {
        return links;
    }

    ArrayList<String> links;
    double price=0.0;
    int color;
    String material;
    String about;



}
