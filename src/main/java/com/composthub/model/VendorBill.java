package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="vendor_bills")
public class VendorBill {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate billDate; private String vendor; private String reference; private double amount; private String status;
 public VendorBill(){} public VendorBill(LocalDate d,String v,String r,double a,String s){billDate=d;vendor=v;reference=r;amount=a;status=s;}
 public Long getId(){return id;} public LocalDate getBillDate(){return billDate;} public void setBillDate(LocalDate v){billDate=v;} public String getVendor(){return vendor;} public void setVendor(String v){vendor=v;}
 public String getReference(){return reference;} public void setReference(String v){reference=v;} public double getAmount(){return amount;} public void setAmount(double v){amount=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}