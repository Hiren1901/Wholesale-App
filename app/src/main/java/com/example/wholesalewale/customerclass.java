package com.example.wholesalewale;
public class customerclass {
    public customerclass(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }
    customerclass(){}

    String name;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    String address;
  String number;

}
