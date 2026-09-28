package com.composthub;

import com.composthub.controller.ApiController;
import com.composthub.model.*;
import com.composthub.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompostHubApplicationTests {

	@Autowired private ApiController apiController;
	@Autowired private DigesterRepository digesters;
	@Autowired private WasteIntakeRepository intakes;
	@Autowired private CompostBatchRepository batches;
	@Autowired private SalesOrderRepository sales;
	@Autowired private CustomerInvoiceRepository invoices;
	@Autowired private VendorBillRepository bills;

	@BeforeEach
	void setup() {
		bills.deleteAll();
		invoices.deleteAll();
		sales.deleteAll();
		batches.deleteAll();
		intakes.deleteAll();
		digesters.deleteAll();
	}

	@Test
	void contextLoads() {}

	@Test
	void dashboardShouldAggregateOperationalMetrics() {
		digesters.save(new Digester("Digester A", 55, 53, 45));
		digesters.save(new Digester("Digester B", 58, 57, 45));

		intakes.save(new WasteIntake(LocalDate.now(), "Community Gardens", 310, "Eco Collectors", "RECEIVED"));
		intakes.save(new WasteIntake(LocalDate.now().minusDays(2), "School Campus", 120, "Eco Collectors", "PROCESSED"));

		batches.save(new CompostBatch("B-101", LocalDate.now().minusDays(5), LocalDate.now().minusDays(2), 300, 240, "COMPLETED"));
		batches.save(new CompostBatch("B-102", LocalDate.now().minusDays(1), LocalDate.now(), 180, 150, "ACTIVE"));

		sales.save(new SalesOrder(LocalDate.now().minusDays(3), "Green Valley Farms", 60, 20, "PAID"));
		sales.save(new SalesOrder(LocalDate.now().minusDays(1), "Community Market", 35, 18, "PAID"));

		invoices.save(new CustomerInvoice(LocalDate.now().minusDays(4), "Green Valley Farms", "INV-100", 1200, "PAID"));
		invoices.save(new CustomerInvoice(LocalDate.now().minusDays(1), "Community Market", "INV-101", 630, "PAID"));

		bills.save(new VendorBill(LocalDate.now().minusDays(6), "Waste Hauler", "VB-110", 450, "PENDING"));
		bills.save(new VendorBill(LocalDate.now().minusDays(2), "Lab Services", "VB-111", 275, "PAID"));

		Map<String, Object> dashboard = apiController.dashboard();

		assertEquals(2, ((Number) dashboard.get("digesters")).intValue());
		assertEquals(2, ((Number) dashboard.get("wasteIntakes")).intValue());
		assertEquals(2, ((Number) dashboard.get("batches")).intValue());
		assertEquals(430.0, ((Number) dashboard.get("wasteCollectedKg")).doubleValue(), 0.01);
		assertEquals(390.0, ((Number) dashboard.get("fertilizerProducedKg")).doubleValue(), 0.01);
		assertEquals(1830.0, ((Number) dashboard.get("salesRevenue")).doubleValue(), 0.01);
		assertEquals(450.0, ((Number) dashboard.get("vendorLiability")).doubleValue(), 0.01);
		assertNotNull(dashboard.get("recentActivity"));
		assertInstanceOf(List.class, dashboard.get("recentActivity"));
		assertFalse(((List<?>) dashboard.get("recentActivity")).isEmpty());
	}
}