package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Product {
@NotNull
    private String name;
@NotNull
    private int price;
@NotNull
    private int number;
@NotNull
    private String unit;

    public Product(){
    }

    public Product(String name, int price,int number,String unit){
        this.name = name;
        this.price = price;
        this.number = number;
        this.unit = unit;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String name) {
        this.name = name;
    }

public int getPrice(){
        return price;
}
    public void setPrice(int price) {
        this.price = price;
    }
    public int getNumber(){
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit ) {
        this.unit = unit;
    }
}
