package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="budgets")
public class Budget {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String analyticAccount; private String category; private double plannedAmount; private double actualAmount; private LocalDate fromDate; private LocalDate toDate;
 public Budget(){} public Budget(String a,String c,double p,double x,LocalDate f,LocalDate t){analyticAccount=a;category=c;plannedAmount=p;actualAmount=x;fromDate=f;toDate=t;}
 public Long getId(){return id;} public String getAnalyticAccount(){return analyticAccount;} public void setAnalyticAccount(String v){analyticAccount=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public double getPlannedAmount(){return plannedAmount;} public void setPlannedAmount(double v){plannedAmount=v;} public double getActualAmount(){return actualAmount;} public void setActualAmount(double v){actualAmount=v;}
 public LocalDate getFromDate(){return fromDate;} public void setFromDate(LocalDate v){fromDate=v;} public LocalDate getToDate(){return toDate;} public void setToDate(LocalDate v){toDate=v;}
}