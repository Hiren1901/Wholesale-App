package com.example.wholesalewale;
public class owners {
    public String getName() {
        return name;
    }

    public String getMob() {
        return mob;
    }

    public String getAddress() {
        return address;
    }
    public owners(){

    }

    String name;
    String mob;
    String address;
    public owners(String name, String mob, String address) {
        this.name = name;
        this.mob = mob;
        this.address = address;
    }



}
