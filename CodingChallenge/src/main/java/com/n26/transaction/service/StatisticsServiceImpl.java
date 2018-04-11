package com.n26.transaction.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.n26.transaction.model.Statistics;
import com.n26.transaction.model.TransactionItem;
import com.n26.transaction.model.TransactionRecordKeeper;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	//@Autowired
	private TransactionRecordKeeper transactionRecordKeeper;
	
	public StatisticsServiceImpl(TransactionRecordKeeper transactionRecordKeeper) {
		this.transactionRecordKeeper = transactionRecordKeeper;
	}
	
	@Override
	public List<TransactionItem> getTransactionItems(int seconds) {
		
		long currentTime = System.currentTimeMillis();
		long statisticsStartTime = currentTime - (seconds * 1000);
		int transactionIndex = transactionRecordKeeper.getTransactionCounter() - 1;
		List<TransactionItem> transactionItemsFound = new LinkedList<>();
		
		// Returns a synchronized (thread-safe) list backed by the specified list.
		List<TransactionItem> transactionsListedByTimestamp 
								= transactionRecordKeeper.getTransactionsListedByTimestamp();
		
		// In order to guarantee serial access, manually synchronize on the returned list here
		// refer to Oracle doc at https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedList-java.util.List-
		synchronized (transactionsListedByTimestamp) {
			
			while (transactionIndex >= 0) {
				
				TransactionItem transactionItem = transactionsListedByTimestamp
													.get(transactionIndex);				
				long transactionTimestamp = transactionItem.getTimestamp();
				
				if (transactionTimestamp >= statisticsStartTime) {
					
					// Although the API accepts transactions with time-stamp in the future, 
					// only transactions in the last 60 seconds are added to the result.
					if (transactionTimestamp <= currentTime) {						
						transactionItemsFound.add(transactionItem);						
					}
					
					transactionIndex--;					
				}
				
				else {
					// Stop iterating here as we are sure at this point that the 
					// remaining transactions happened more than 60 seconds ago.
					break;
					
				}
			}
		}
		
		return transactionItemsFound;		
	}
	
	@Override
	public Statistics getStatistics(int seconds) {
		
		double sum = 0;
		double average = 0;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		long count = 0;		
		// Get the synchronized list for the transactions happened in the last 60 seconds
		List<TransactionItem> transactionItemsFound = getTransactionItems(seconds);
		
		for (TransactionItem transactionItem: transactionItemsFound) {
			
			double transactionAmount = transactionItem.getAmount();
			
			if (transactionAmount > max) {				
				max = transactionAmount;				
			}
			if (transactionAmount < min) {				
				min = transactionAmount;				
			}
			
			sum += transactionAmount;
			count++;
			
		}
		
		if (count > 0) {
			
			average = sum / count;			
			return new Statistics(sum, average, max, min, count);
			
		}
		else {
			// Return empty response body if no transactions found.
			return null;
			
		}
		
	}
	
}
