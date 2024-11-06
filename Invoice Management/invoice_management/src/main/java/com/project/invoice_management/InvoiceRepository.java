package com.project.invoice_management;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	    List<Invoice> findByStatus(String status);
	}

