package com.composthub.config;
import com.composthub.model.*; import com.composthub.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import java.time.LocalDate;
@Configuration
public class DataLoader {
 @Bean CommandLineRunner seed(DigesterRepository d,ContactRepository c,ProductRepository p,AccountRepository a,BudgetRepository b){
  return args -> {
   if(d.count()==0){d.save(new Digester("Bio-Digester Unit 01",55,53,45));d.save(new Digester("Bio-Digester Unit 02",58,57,45));}
   if(c.count()==0){c.save(new Contact("Green Valley Farms","CUSTOMER","9876543210","farm@example.com","East Zone"));c.save(new Contact("EcoWaste Collectors","VENDOR","9876500000","vendor@example.com","City Center"));c.save(new Contact("BioLab Testing Services","VENDOR","9876511111","lab@example.com","Industrial Area"));}
   if(p.count()==0){p.save(new Product("Organic Compost Fertilizer","GOODS","KG",18));p.save(new Product("Organic Waste Ingestion","SERVICE","KG",2));p.save(new Product("Bio-Digester Testing","SERVICE","TEST",1500));}
   if(a.count()==0){a.save(new Account("1000","Bio-Digester Machinery","ASSET"));a.save(new Account("1100","Compost Stock","ASSET"));a.save(new Account("1200","Cash/Bank","ASSET"));a.save(new Account("2000","Collection Contractor Payables","LIABILITY"));a.save(new Account("2100","Testing Lab Creditors","LIABILITY"));a.save(new Account("4000","Organic Fertilizer Sales Revenue","INCOME"));a.save(new Account("4100","Waste Ingestion Fees","INCOME"));a.save(new Account("5000","Digester Heating Electricity","EXPENSE"));a.save(new Account("5100","Bio-Lab Testing Costs","EXPENSE"));}
   if(b.count()==0){b.save(new Budget("Organic Recovery Hub East","Digester Heating Electricity",50000,32000,LocalDate.now().withDayOfMonth(1),LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1)));b.save(new Budget("Organic Recovery Hub East","Fertilizer Sales Revenue",120000,85000,LocalDate.now().withDayOfMonth(1),LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1)));}
  };
 }
}