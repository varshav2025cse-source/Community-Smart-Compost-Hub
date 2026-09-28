package com.composthub.model;
import jakarta.persistence.*;
@Entity @Table(name="contacts")
public class Contact {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name; private String type; private String phone; private String email; private String address;
 public Contact() {}
 public Contact(String name,String type,String phone,String email,String address){this.name=name;this.type=type;this.phone=phone;this.email=email;this.address=address;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getType(){return type;} public void setType(String v){type=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getAddress(){return address;} public void setAddress(String v){address=v;}
}