package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="purchase_orders")
public class PurchaseOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate orderDate; private String vendor; private String description; private double amount; private String status;
 public PurchaseOrder(){} public PurchaseOrder(LocalDate d,String v,String desc,double a,String s){orderDate=d;vendor=v;description=desc;amount=a;status=s;}
 public Long getId(){return id;} public LocalDate getOrderDate(){return orderDate;} public void setOrderDate(LocalDate v){orderDate=v;} public String getVendor(){return vendor;} public void setVendor(String v){vendor=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public double getAmount(){return amount;} public void setAmount(double v){amount=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}