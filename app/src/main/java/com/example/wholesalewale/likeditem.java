package com.example.wholesalewale;
public class likeditem {
    public String getShopname() {
        return shopname;
    }

    public String getItemname() {
        return itemname;
    }

    public likeditem(String shopname, String itemname,String category,String key) {
        this.shopname = shopname;
        this.itemname = itemname;
        this.category=category;
        this.key=key;
    }
    public likeditem(String shopname, String itemname,String category,String key,String qnt) {
        this.shopname = shopname;
        this.itemname = itemname;
        this.category=category;
        this.key=key;
        this.qnt=qnt;
    }
public  likeditem(){}
    String shopname;
    String itemname;

    public String getQnt() {
        return qnt;
    }

    String qnt;

    public String getCategory() {
        return category;
    }

    public String getKey() {
        return key;
    }

    String category;
    String key;




}
