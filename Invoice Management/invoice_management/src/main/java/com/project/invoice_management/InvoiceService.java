package com.project.invoice_management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {

	private final InvoiceRepository repository;

	public InvoiceService(InvoiceRepository repository) {
		this.repository = repository;
	}

	public Invoice createInvoice(BigDecimal amount, LocalDate dueDate) {
		Invoice invoice = new Invoice(amount, dueDate);
		return repository.save(invoice);
	}

	public List<Invoice> getInvoices() {
		return repository.findAll();
	}

	public Optional<Invoice> getInvoiceById(Long id) {
		return repository.findById(id);
	}

	public Optional<Invoice> updateInvoice(Long id, BigDecimal paymentAmount) {
		return repository.findById(id).map(invoice -> {
			invoice.setPaidAmount(invoice.getPaidAmount().add(paymentAmount));
			if (invoice.getPaidAmount().compareTo(invoice.getAmount()) >= 0) {
				invoice.setStatus("paid");
			}
			return repository.save(invoice);
		});
	}

	public void deleteInvoice(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	public void processOverdue(BigDecimal lateFee, int overdueDays) {
		LocalDate today = LocalDate.now();
		repository.findByStatus("pending").stream()
				.filter(invoice -> today.isAfter(invoice.getDueDate().plusDays(overdueDays))).forEach(invoice -> {
					invoice.setStatus("void");
					Invoice newInvoice = new Invoice(invoice.getAmount().add(lateFee), today.plusDays(overdueDays));
					repository.save(newInvoice);
				});
	}
}
