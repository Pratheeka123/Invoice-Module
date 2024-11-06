package com.project.invoice_management;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
	@RequestMapping("/invoices")
	public class InvoiceController {

	    private final InvoiceService service;

	    public InvoiceController(InvoiceService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
	        Invoice invoice = service.createInvoice(request.getAmount(), request.getDueDate());
	        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
	    }

	    @GetMapping
	    public List<Invoice> getInvoices() {
	        return service.getInvoices();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
	        return service.getInvoiceById(id)
	                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PutMapping("/{id}/pay")
	    public ResponseEntity<Invoice> payInvoice(@PathVariable Long id, @RequestBody PaymentRequest payment) {
	        return service.updateInvoice(id, payment.getAmount())
	                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
	        service.deleteInvoice(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    @PostMapping("/process-overdue")
	    public ResponseEntity<Void> processOverdue(@RequestBody OverdueRequest request) {
	        service.processOverdue(request.getLateFee(), request.getOverdueDays());
	        return new ResponseEntity<>(HttpStatus.OK);
	    }
	}
