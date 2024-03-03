package com.example.wholesalewale;

import android.net.Uri;

import java.util.ArrayList;

public class uploadDetails {

    public String getProductname() {
        return productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getPrice() {
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
public uploadDetails(){

}
    public uploadDetails(String productname, int quantity, long price, int color, String material, String about, ArrayList<String> links) {
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.material = material;
        this.about = about;
        this.links=links;
    }

    String productname;
    int quantity=0;


    public ArrayList<String> getLinks() {
        return links;
    }

    ArrayList<String> links;
  long price;
    int color;
    String material;
    String about;






}
