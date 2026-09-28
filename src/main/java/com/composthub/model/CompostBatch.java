package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="compost_batches")
public class CompostBatch {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String batchCode; private LocalDate startDate; private LocalDate completionDate; private double wasteInputKg; private double fertilizerOutputKg; private String status;
 public CompostBatch(){}
 public CompostBatch(String code,LocalDate start,double input,double output,String status){batchCode=code;startDate=start;wasteInputKg=input;fertilizerOutputKg=output;this.status=status;}
 public CompostBatch(String code,LocalDate start,LocalDate completion,double input,double output,String status){
  this.batchCode=code;
  this.startDate=start;
  this.completionDate=completion;
  this.wasteInputKg=input;
  this.fertilizerOutputKg=output;
  this.status=status;
 }
 public Long getId(){return id;} public String getBatchCode(){return batchCode;} public void setBatchCode(String v){batchCode=v;} public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
 public LocalDate getCompletionDate(){return completionDate;} public void setCompletionDate(LocalDate v){completionDate=v;} public double getWasteInputKg(){return wasteInputKg;} public void setWasteInputKg(double v){wasteInputKg=v;}
 public double getFertilizerOutputKg(){return fertilizerOutputKg;} public void setFertilizerOutputKg(double v){fertilizerOutputKg=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;}
}