package com.n26.statistic.challenge.service;

import org.springframework.stereotype.Service;

import com.n26.statistic.challenge.model.Transaction;
import com.n26.statistic.challenge.model.TransactionSummary;

@Service
public interface TransactionService {

	void addTransaction(Transaction transaction);

	TransactionSummary getTransactionSummary();
}
