package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="waste_intakes")
public class WasteIntake {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate intakeDate; private String source; private double weightKg; private String contractor; private String status;
 public WasteIntake(){ } public WasteIntake(LocalDate d,String s,double w,String c,String st){intakeDate=d;source=s;weightKg=w;contractor=c;status=st;}
 public Long getId(){return id;} public LocalDate getIntakeDate(){return intakeDate;} public void setIntakeDate(LocalDate v){intakeDate=v;} public String getSource(){return source;} public void setSource(String v){source=v;}
 public double getWeightKg(){return weightKg;} public void setWeightKg(double v){weightKg=v;} public String getContractor(){return contractor;} public void setContractor(String v){contractor=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
}