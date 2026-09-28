package com.composthub.controller;

import com.composthub.model.*;
import com.composthub.repository.*;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/compost")
public class ApiController {

    private final DigesterRepository digesters;
    private final WasteIntakeRepository intakes;
    private final CompostBatchRepository batches;
    private final ContactRepository contacts;
    private final ProductRepository products;
    private final AccountRepository accounts;
    private final PurchaseOrderRepository pos;
    private final VendorBillRepository bills;
    private final SalesOrderRepository sales;
    private final CustomerInvoiceRepository invoices;
    private final PaymentRepository payments;
    private final BudgetRepository budgets;
    private final JournalEntryRepository entries;

    public ApiController(
            DigesterRepository d,
            WasteIntakeRepository i,
            CompostBatchRepository b,
            ContactRepository c,
            ProductRepository p,
            AccountRepository a,
            PurchaseOrderRepository po,
            VendorBillRepository vb,
            SalesOrderRepository so,
            CustomerInvoiceRepository ci,
            PaymentRepository pay,
            BudgetRepository bu,
            JournalEntryRepository je) {

        digesters = d;
        intakes = i;
        batches = b;
        contacts = c;
        products = p;
        accounts = a;
        pos = po;
        bills = vb;
        sales = so;
        invoices = ci;
        payments = pay;
        budgets = bu;
        entries = je;
    }

    // =========================
    // DASHBOARD
    // =========================

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> result = new LinkedHashMap<>();

        double wasteCollectedKg =
            intakes.findAll().stream()
                .mapToDouble(WasteIntake::getWeightKg)
                .sum();

        double fertilizerProduced =
                batches.findAll()
                        .stream()
                        .mapToDouble(CompostBatch::getFertilizerOutputKg)
                        .sum();

        double fertilizerSold =
                sales.findAll()
                        .stream()
                        .mapToDouble(SalesOrder::getQuantityKg)
                        .sum();

        double salesRevenue =
                invoices.findAll()
                        .stream()
                        .mapToDouble(CustomerInvoice::getAmount)
                        .sum();

        double vendorLiability =
                bills.findAll()
                        .stream()
                        .filter(x -> !"PAID".equalsIgnoreCase(x.getStatus()))
                        .mapToDouble(VendorBill::getAmount)
                        .sum();

        List<Map<String, Object>> recentActivity = new ArrayList<>();

        for (WasteIntake intake : intakes.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("title", "Waste intake: " + intake.getSource());
            row.put("type", "Collection");
            row.put("value", intake.getWeightKg() + " kg");
            row.put("status", intake.getStatus());
            row.put("date", intake.getIntakeDate());
            recentActivity.add(row);
        }

        for (CompostBatch batch : batches.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("title", "Batch: " + batch.getBatchCode());
            row.put("type", "Production");
            row.put("value", batch.getFertilizerOutputKg() + " kg");
            row.put("status", batch.getStatus());
            row.put("date", batch.getCompletionDate() != null ? batch.getCompletionDate() : batch.getStartDate());
            recentActivity.add(row);
        }

        for (SalesOrder sale : sales.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("title", "Sale: " + sale.getCustomer());
            row.put("type", "Finance");
            row.put("value", "₹" + sale.getTotal());
            row.put("status", sale.getStatus());
            row.put("date", sale.getOrderDate());
            recentActivity.add(row);
        }

        recentActivity.sort((left, right) -> {
            Object leftDate = left.get("date");
            Object rightDate = right.get("date");
            if (leftDate == null && rightDate == null) return 0;
            if (leftDate == null) return 1;
            if (rightDate == null) return -1;
            return ((Comparable<Object>) rightDate).compareTo(leftDate);
        });

        if (recentActivity.size() > 6) {
            recentActivity = recentActivity.subList(0, 6);
        }

        result.put("digesters", digesters.count());
        result.put("wasteIntakes", intakes.count());
        result.put("batches", batches.count());
        result.put("wasteCollectedKg", wasteCollectedKg);
        result.put("fertilizerProducedKg", fertilizerProduced);
        result.put("fertilizerSoldKg", fertilizerSold);
        result.put("salesRevenue", salesRevenue);
        result.put("vendorLiability", vendorLiability);
        result.put("recentActivity", recentActivity);

        return result;
    }

    // =========================
    // DIGESTER
    // =========================

    @PostMapping("/digesters")
    public Digester addDigester(@RequestBody Digester digester) {
        return digesters.save(digester);
    }

    @GetMapping("/digesters")
    public List<Digester> getDigesters() {
        return digesters.findAll();
    }

    // =========================
    // ORGANIC WASTE INTAKE
    // =========================

    @PostMapping("/intake")
    public WasteIntake addIntake(@RequestBody WasteIntake intake) {
        return intakes.save(intake);
    }

    @GetMapping("/intake")
    public List<WasteIntake> getIntakes() {
        return intakes.findAll();
    }

    // =========================
    // COMPOST BATCH
    // =========================

    @PostMapping("/batches")
    public CompostBatch addBatch(@RequestBody CompostBatch batch) {
        return batches.save(batch);
    }

    @GetMapping("/batches")
    public List<CompostBatch> getBatches() {
        return batches.findAll();
    }

    // =========================
    // CONTACT MASTER
    // =========================

    @PostMapping("/contacts")
    public Contact addContact(@RequestBody Contact contact) {
        return contacts.save(contact);
    }

    @GetMapping("/contacts")
    public List<Contact> getContacts() {
        return contacts.findAll();
    }

    // =========================
    // PRODUCT MASTER
    // =========================

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return products.save(product);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return products.findAll();
    }

    // =========================
    // CHART OF ACCOUNTS
    // =========================

    @PostMapping("/accounts")
    public Account addAccount(@RequestBody Account account) {
        return accounts.save(account);
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accounts.findAll();
    }

    // =========================
    // PURCHASE ORDER
    // =========================

    @PostMapping("/purchase-orders")
    public PurchaseOrder addPurchaseOrder(
            @RequestBody PurchaseOrder purchaseOrder) {

        return pos.save(purchaseOrder);
    }

    @GetMapping("/purchase-orders")
    public List<PurchaseOrder> getPurchaseOrders() {
        return pos.findAll();
    }

    // =========================
    // VENDOR BILL
    // =========================

    @PostMapping("/vendor-bills")
    public VendorBill addVendorBill(
            @RequestBody VendorBill vendorBill) {

        return bills.save(vendorBill);
    }

    @GetMapping("/vendor-bills")
    public List<VendorBill> getVendorBills() {
        return bills.findAll();
    }

    // =========================
    // SALES ORDER
    // =========================

    @PostMapping("/sales-orders")
    public SalesOrder addSalesOrder(
            @RequestBody SalesOrder salesOrder) {

        return sales.save(salesOrder);
    }

    @GetMapping("/sales-orders")
    public List<SalesOrder> getSalesOrders() {
        return sales.findAll();
    }

    // =========================
    // CUSTOMER INVOICE
    // =========================

    @PostMapping("/customer-invoices")
    public CustomerInvoice addCustomerInvoice(
            @RequestBody CustomerInvoice invoice) {

        return invoices.save(invoice);
    }

    @GetMapping("/customer-invoices")
    public List<CustomerInvoice> getCustomerInvoices() {
        return invoices.findAll();
    }

    // =========================
    // PAYMENT
    // =========================

    @PostMapping("/payments")
    public Payment addPayment(
            @RequestBody Payment payment) {

        return payments.save(payment);
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return payments.findAll();
    }

    // =========================
    // BUDGET
    // =========================

    @PostMapping("/budgets")
    public Budget addBudget(
            @RequestBody Budget budget) {

        return budgets.save(budget);
    }

    @GetMapping("/budgets")
    public List<Budget> getBudgets() {
        return budgets.findAll();
    }

    // =========================
    // JOURNAL ENTRY
    // =========================

    @PostMapping("/journal-entries")
    public JournalEntry addJournalEntry(
            @RequestBody JournalEntry entry) {

        return entries.save(entry);
    }

    @GetMapping("/journal-entries")
    public List<JournalEntry> getJournalEntries() {
        return entries.findAll();
    }

    // =========================
    // PROFIT & LOSS REPORT
    // =========================

    @GetMapping("/reports/pl")
    public Map<String, Double> profitLoss() {

        double revenue =
                invoices.findAll()
                        .stream()
                        .mapToDouble(CustomerInvoice::getAmount)
                        .sum();

        double expenses =
                bills.findAll()
                        .stream()
                        .mapToDouble(VendorBill::getAmount)
                        .sum();

        double netResult = revenue - expenses;

        Map<String, Double> result = new LinkedHashMap<>();

        result.put("fertilizerSalesRevenue", revenue);
        result.put("testingAndVendorExpenses", expenses);
        result.put("netResult", netResult);

        return result;
    }

    // =========================
    // BUDGET REPORT
    // =========================

    @GetMapping("/reports/budget")
    public List<Map<String, Object>> budgetReport() {

        List<Map<String, Object>> result = new ArrayList<>();

        for (Budget budget : budgets.findAll()) {

            Map<String, Object> row = new LinkedHashMap<>();

            row.put("analyticAccount",
                    budget.getAnalyticAccount());

            row.put("category",
                    budget.getCategory());

            row.put("planned",
                    budget.getPlannedAmount());

            row.put("actual",
                    budget.getActualAmount());

            row.put("variance",
                    budget.getPlannedAmount()
                            - budget.getActualAmount());

            result.add(row);
        }

        return result;
    }

    // =========================
    // BALANCE SHEET
    // =========================

    @GetMapping("/reports/balance-sheet")
    public Map<String, Double> balanceSheet() {

        double compostStock =
                batches.findAll()
                        .stream()
                        .mapToDouble(CompostBatch::getFertilizerOutputKg)
                        .sum();

        double openVendorLiabilities =
                bills.findAll()
                        .stream()
                        .filter(x -> !"PAID".equalsIgnoreCase(x.getStatus()))
                        .mapToDouble(VendorBill::getAmount)
                        .sum();

        Map<String, Double> result = new LinkedHashMap<>();

        result.put("compostStockKg", compostStock);
        result.put("openVendorLiabilities",
                openVendorLiabilities);

        return result;
    }
}