package com.n26.transaction.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.n26.transaction.model.*;
import com.n26.transaction.service.*;

@RestController
public class MainController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private StatisticsService statisticsService;
 
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<Object> addTransaction(@RequestBody TransactionItem transactionItem) {

		return transactionService.addTransactionItem(transactionItem.getAmount(), transactionItem.getTimestamp());

	}
	
	@GetMapping(value = "/statistics")
	public Statistics getStatistics() {
		
		return statisticsService.getStatistics(60);
	}
	
	@GetMapping(value = "/transactionrecords")
	public List<TransactionItem> getTransactionRecords() {
		
		return transactionService.getTransactionRecordList();
		
	}
}
