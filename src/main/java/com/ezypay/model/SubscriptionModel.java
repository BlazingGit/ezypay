package com.ezypay.model;

import java.math.BigDecimal;

public class SubscriptionModel {
	private String customerName;
	private SubscriptionType type;
	private String dayOfDate;
	private String startDate;
	private String endDate;
	private BigDecimal amount;
	private BigDecimal totalAmount;
	private String[] invoiceDates;

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String[] getInvoiceDates() {
		return invoiceDates;
	}

	public void setInvoiceDates(String[] invoiceDates) {
		this.invoiceDates = invoiceDates;
	}

	public String getDayOfDate() {
		return dayOfDate;
	}

	public void setDayOfDate(String dayOfDate) {
		this.dayOfDate = dayOfDate;
	}
}
