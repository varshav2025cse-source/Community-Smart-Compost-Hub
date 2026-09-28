package com.composthub.model;
import jakarta.persistence.*;
@Entity @Table(name="digesters")
public class Digester {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name; private double targetTemperature; private double currentTemperature; private double minSafetyTemperature; private boolean active=true;
 public Digester() {}
 public Digester(String name,double targetTemperature,double currentTemperature,double minSafetyTemperature){this.name=name;this.targetTemperature=targetTemperature;this.currentTemperature=currentTemperature;this.minSafetyTemperature=minSafetyTemperature;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public double getTargetTemperature(){return targetTemperature;} public void setTargetTemperature(double v){targetTemperature=v;}
 public double getCurrentTemperature(){return currentTemperature;} public void setCurrentTemperature(double v){currentTemperature=v;} public double getMinSafetyTemperature(){return minSafetyTemperature;} public void setMinSafetyTemperature(double v){minSafetyTemperature=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}