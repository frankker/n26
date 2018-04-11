package com.n26.transaction.test;

import org.mockito.junit.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.n26.transaction.model.TransactionRecordKeeper;
import com.n26.transaction.service.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@Mock
	TransactionRecordKeeper transactionRecordKeeper;	
	@InjectMocks
	TransactionServiceImpl transactionService;
	
	// Test if the POST /transactions produces 201 response code when 
	// the transaction carries time-stamp not older than 60 seconds
	@Test
	public void testAddTransactionItem_201(){
		
		long currentTimestamp = System.currentTimeMillis();
		long timestampBefore60Seconds = currentTimestamp - 50009;
		
		int returnStatusCodeCurrentTimestamp
				= transactionService
					.addTransactionItem(99, currentTimestamp)
						.getStatusCodeValue();
		
		int returnStatusCodeBefore60Seconds 
				= transactionService
					.addTransactionItem(99, timestampBefore60Seconds)
						.getStatusCodeValue();
		
		assertEquals(201, returnStatusCodeCurrentTimestamp);
		assertEquals(201, returnStatusCodeBefore60Seconds);		
		
	}
	
	
	// Test if the POST /transactions produces 204 response code when 
	// the transaction carries time-stamp older than 60 seconds
	@Test
	public void testAddTransactionItem_204(){
		
		long currentTimestamp = System.currentTimeMillis();
		long timestampOlderThan60Seconds = currentTimestamp - 60001;
		long timestampOlderThan90Seconds = currentTimestamp - 90001;	

		int returnStatusCodeOlderThan60Seconds 
				= transactionService
					.addTransactionItem(99, timestampOlderThan60Seconds)
						.getStatusCodeValue();
		
		int returnStatusCodeOlderThan90Seconds 
				= transactionService
					.addTransactionItem(99, timestampOlderThan90Seconds)
						.getStatusCodeValue();
		
		assertEquals(204, returnStatusCodeOlderThan60Seconds);
		assertEquals(204, returnStatusCodeOlderThan90Seconds);
		
	}	
}