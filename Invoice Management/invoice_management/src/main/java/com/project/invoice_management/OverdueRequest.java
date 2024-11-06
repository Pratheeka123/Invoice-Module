package com.project.invoice_management;

import java.math.BigDecimal;

public class OverdueRequest {

	private BigDecimal lateFee;
	  private int overdueDays;
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}
	public int getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}
	  
	  
}
