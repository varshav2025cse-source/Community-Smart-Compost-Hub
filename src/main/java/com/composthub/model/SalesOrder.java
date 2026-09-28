package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="sales_orders")
public class SalesOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate orderDate; private String customer; private double quantityKg; private double unitPrice; private double total; private String status;
 public SalesOrder(){} public SalesOrder(LocalDate d,String c,double q,double p,String s){orderDate=d;customer=c;quantityKg=q;unitPrice=p;total=q*p;status=s;}
 public Long getId(){return id;} public LocalDate getOrderDate(){return orderDate;} public void setOrderDate(LocalDate v){orderDate=v;} public String getCustomer(){return customer;} public void setCustomer(String v){customer=v;}
 public double getQuantityKg(){return quantityKg;} public void setQuantityKg(double v){quantityKg=v;} public double getUnitPrice(){return unitPrice;} public void setUnitPrice(double v){unitPrice=v;}
 public double getTotal(){return total;} public void setTotal(double v){total=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}