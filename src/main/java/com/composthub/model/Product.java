package com.composthub.model;
import jakarta.persistence.*;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name; private String type; private String unit; private double unitPrice;
 public Product() {} public Product(String name,String type,String unit,double unitPrice){this.name=name;this.type=type;this.unit=unit;this.unitPrice=unitPrice;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getType(){return type;} public void setType(String v){type=v;}
 public String getUnit(){return unit;} public void setUnit(String v){unit=v;} public double getUnitPrice(){return unitPrice;} public void setUnitPrice(double v){unitPrice=v;}
}