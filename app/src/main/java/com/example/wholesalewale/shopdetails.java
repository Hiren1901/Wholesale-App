package com.example.wholesalewale;
public class shopdetails {
    public shopdetails(String shopname, String category, String address, String mob) {
        this.shopname = shopname;
        this.category = category;
        this.address = address;
        this.mob = mob;
    }
public shopdetails(){}
    public String getShopname() {
        return shopname;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getMob() {
        return mob;
    }

    String shopname;
    String category;
    String address;
   String mob;
}
