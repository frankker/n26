package com.n26.transaction.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRecordKeeper {

	// "Wrapped" the transaction list in a synchronized list
	// Make the list thread-safe with concurrent requests
	private List<TransactionItem> transactionsListedByTimestamp 
								= Collections.synchronizedList(new ArrayList<>());
	private int transactionCounter;
	private long lastTransactionTimestamp; 
	
	public List<TransactionItem> getTransactionsListedByTimestamp() {
		return transactionsListedByTimestamp;
	}
	
	public void addTransactionsListedByTimestamp(TransactionItem transactionItem) {
		
		this.transactionsListedByTimestamp.add(transactionItem);
	}
	
	public void addTransactionsListedByTimestamp(int index, TransactionItem transactionItem) {
		
		this.transactionsListedByTimestamp.add(index, transactionItem);
	}
	
	public int getTransactionCounter() {
		return transactionCounter;
	}
	
	public void setTransactionCounter(int transactionCounter) {
		this.transactionCounter = transactionCounter;
	}
	
	public long getLastTransactionTimestamp() {
		return lastTransactionTimestamp;
	}
	
	public void setLastTransactionTimestamp(long lastTransactionTimestamp) {
		this.lastTransactionTimestamp = lastTransactionTimestamp;
	}
	
}