package com.n26.transaction.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.n26.transaction.model.TransactionItem;

public interface TransactionService {

	public ResponseEntity<Object> addTransactionItem(double amount, long timestamp);
	
	public List<TransactionItem> getTransactionRecordList();
	
}
