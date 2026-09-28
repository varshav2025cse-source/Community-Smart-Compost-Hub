package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="payments")
public class Payment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate paymentDate; private String party; private String type; private String reference; private double amount; private String mode;
 public Payment(){} public Payment(LocalDate d,String p,String t,String r,double a,String m){paymentDate=d;party=p;type=t;reference=r;amount=a;mode=m;}
 public Long getId(){return id;} public LocalDate getPaymentDate(){return paymentDate;} public void setPaymentDate(LocalDate v){paymentDate=v;} public String getParty(){return party;} public void setParty(String v){party=v;}
 public String getType(){return type;} public void setType(String v){type=v;} public String getReference(){return reference;} public void setReference(String v){reference=v;} public double getAmount(){return amount;} public void setAmount(double v){amount=v;}
 public String getMode(){return mode;} public void setMode(String v){mode=v;}
}