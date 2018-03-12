package com.n26.statistic.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistic.challenge.exception.TimeOutOfScopeException;
import com.n26.statistic.challenge.model.Transaction;
import com.n26.statistic.challenge.model.TransactionSummary;
import com.n26.statistic.challenge.service.TransactionService;

@RestController
public class TransactionRestController {

	@Autowired
	private TransactionService service;

	@PostMapping("/transactions")
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {

		try {
			service.addTransaction(transaction);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (TimeOutOfScopeException ex) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/statistics")
	public ResponseEntity<TransactionSummary> getTransactionSummary(){
		TransactionSummary statistic = service.getTransactionSummary();
		return new ResponseEntity<TransactionSummary>(statistic, HttpStatus.OK);
	}

}
