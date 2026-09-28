package com.composthub.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="journal_entries")
public class JournalEntry {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private LocalDate entryDate; private String journal; private String account; private String description; private double debit; private double credit;
 public JournalEntry(){} public JournalEntry(LocalDate d,String j,String a,String desc,double debit,double credit){entryDate=d;journal=j;account=a;description=desc;this.debit=debit;this.credit=credit;}
 public Long getId(){return id;} public LocalDate getEntryDate(){return entryDate;} public void setEntryDate(LocalDate v){entryDate=v;} public String getJournal(){return journal;} public void setJournal(String v){journal=v;}
 public String getAccount(){return account;} public void setAccount(String v){account=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public double getDebit(){return debit;} public void setDebit(double v){debit=v;} public double getCredit(){return credit;} public void setCredit(double v){credit=v;}
}