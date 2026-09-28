package com.composthub.controller;
import com.composthub.repository.*;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class WebController {
 private final DigesterRepository d; private final WasteIntakeRepository i; private final CompostBatchRepository b;
 private final CustomerInvoiceRepository ci; private final VendorBillRepository vb; private final BudgetRepository bu;
 public WebController(DigesterRepository d,WasteIntakeRepository i,CompostBatchRepository b,CustomerInvoiceRepository ci,VendorBillRepository vb,BudgetRepository bu){this.d=d;this.i=i;this.b=b;this.ci=ci;this.vb=vb;this.bu=bu;}
 @GetMapping("/") public String home(Model m){m.addAttribute("digesters",d.findAll());m.addAttribute("intakes",i.findAll());m.addAttribute("batches",b.findAll());m.addAttribute("invoices",ci.findAll());m.addAttribute("bills",vb.findAll());m.addAttribute("budgets",bu.findAll());return "index";}
}