package com.n26.transaction.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.n26.transaction.model.*;
import com.n26.transaction.service.*;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {
 
	@Test
	public void testStatisticsService(){
		
		TransactionRecordKeeper transactionRecordKeeper = new TransactionRecordKeeper();
		TransactionService transactionService = new TransactionServiceImpl(transactionRecordKeeper);
		StatisticsService statisticsService = new StatisticsServiceImpl(transactionRecordKeeper);
		
		long currentTimestamp = System.currentTimeMillis();
		long timestampBeforeLessThan60Seconds = currentTimestamp - 50009;
		long timestampBeforeMoreThan60Seconds = currentTimestamp - 60001;
		
		TransactionItem t1 = new TransactionItem(30, currentTimestamp);
		TransactionItem t2 = new TransactionItem(100, timestampBeforeLessThan60Seconds);
		TransactionItem t3 = new TransactionItem(500, timestampBeforeMoreThan60Seconds);
		
		transactionService.addTransactionItem(t1.getAmount(), t1.getTimestamp());
		transactionService.addTransactionItem(t2.getAmount(), t2.getTimestamp());
		transactionService.addTransactionItem(t3.getAmount(), t3.getTimestamp());		
		
		Statistics transactionsRecordsInLast60Seconds = statisticsService.getStatistics(60);
		
		double DELTA = 1e-15;
		double statistics_count = 2;
		double statistics_average = (t1.getAmount() + t2.getAmount()) / statistics_count;
		double statistics_max = t2.getAmount();
		double statistics_min = t1.getAmount();
		double statistics_sum = t1.getAmount() + t2.getAmount();
		
		assertEquals(statisticsService.getStatistics(60).getCount(), statistics_count, DELTA);
		assertEquals(transactionsRecordsInLast60Seconds.getAverage(), statistics_average, DELTA);
		assertEquals(transactionsRecordsInLast60Seconds.getMax(), statistics_max, DELTA);
		assertEquals(transactionsRecordsInLast60Seconds.getMin(), statistics_min, DELTA);
		assertEquals(transactionsRecordsInLast60Seconds.getSum(), statistics_sum, DELTA);		
		
	}	
}