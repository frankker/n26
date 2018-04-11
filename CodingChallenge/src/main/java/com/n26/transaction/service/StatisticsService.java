package com.n26.transaction.service;

import java.util.List;
import com.n26.transaction.model.Statistics;
import com.n26.transaction.model.TransactionItem;

public interface StatisticsService {

	public List<TransactionItem> getTransactionItems(int seconds);
	
	public Statistics getStatistics(int seconds);	
	
}
