package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="customer_invoices")
public class CustomerInvoice {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate invoiceDate; private String customer; private String reference; private double amount; private String status;
 public CustomerInvoice(){} public CustomerInvoice(LocalDate d,String c,String r,double a,String s){invoiceDate=d;customer=c;reference=r;amount=a;status=s;}
 public Long getId(){return id;} public LocalDate getInvoiceDate(){return invoiceDate;} public void setInvoiceDate(LocalDate v){invoiceDate=v;} public String getCustomer(){return customer;} public void setCustomer(String v){customer=v;}
 public String getReference(){return reference;} public void setReference(String v){reference=v;} public double getAmount(){return amount;} public void setAmount(double v){amount=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}