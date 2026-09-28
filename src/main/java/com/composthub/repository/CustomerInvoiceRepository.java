package com.composthub.repository;
import com.composthub.model.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {}