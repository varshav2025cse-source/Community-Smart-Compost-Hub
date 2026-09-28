package com.composthub.model;
import jakarta.persistence.*;
@Entity @Table(name="accounts")
public class Account {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String code; private String name; private String category;
 public Account() {} public Account(String code,String name,String category){this.code=code;this.name=name;this.category=category;}
 public Long getId(){return id;} public String getCode(){return code;} public void setCode(String v){code=v;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
}