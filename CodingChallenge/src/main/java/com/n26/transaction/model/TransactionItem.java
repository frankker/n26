package com.n26.transaction.model;

import org.springframework.stereotype.Repository;

@Repository 
public class TransactionItem {

	private double amount;
	private long timestamp;	
	
	public TransactionItem(double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}	
	
	public TransactionItem() {
		super();
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
