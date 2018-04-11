package com.n26.transaction.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.n26.transaction.model.TransactionItem;
import com.n26.transaction.model.TransactionRecordKeeper;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
	
	//@Autowired
	private TransactionRecordKeeper transactionRecordKeeper;
	
	public TransactionServiceImpl(TransactionRecordKeeper transactionRecordKeeper) {
		this.transactionRecordKeeper = transactionRecordKeeper;
	}
	
	@Override
	public ResponseEntity<Object> addTransactionItem(double amount, long timestamp) {
		
		TransactionItem transactionItem = new TransactionItem(amount, timestamp);
		int transactionCounter = transactionRecordKeeper.getTransactionCounter();
		long lastTransactionTimestamp = transactionRecordKeeper.getLastTransactionTimestamp();
		long currentTime = System.currentTimeMillis();
		long transactionTimeDiff = currentTime - timestamp;

		// Add the transaction straight away if the time-stamp of the transaction is later than all the transactions in the list
		if (timestamp >= lastTransactionTimestamp) {
			
			transactionRecordKeeper.addTransactionsListedByTimestamp(transactionItem);	
			transactionRecordKeeper.setLastTransactionTimestamp(timestamp);
		}
		// This handles the cases where the transaction time-stamp is earlier than one or more of the recorded transactions
		else {
			
			int transactionIndex = transactionCounter - 1;
			boolean isTransactionAdded = false;
			
			while (transactionIndex >= 0) {
				
				if (transactionRecordKeeper.getTransactionsListedByTimestamp().
						get(transactionIndex).getTimestamp() <= timestamp) {
					
					transactionRecordKeeper.addTransactionsListedByTimestamp(transactionIndex+1, transactionItem);
					isTransactionAdded = true;
					break;
				}
				
				--transactionIndex;
			}
			
			// To initialized the transaction list and for edge case handling if every 
			// time-stamp of all the transactions in the list is later than it 
			if (!isTransactionAdded) {
				transactionRecordKeeper.addTransactionsListedByTimestamp(0, transactionItem);
			}
		}
		
		transactionRecordKeeper.setTransactionCounter(++transactionCounter);
		
		// If transaction is older than 6 seconds, returns status code 204, else returns 201
		if (transactionTimeDiff > 60000) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		}
		
	}	
	
	@Override
	public List<TransactionItem> getTransactionRecordList() {
		
		return transactionRecordKeeper.getTransactionsListedByTimestamp();
		
	}	
	
}
