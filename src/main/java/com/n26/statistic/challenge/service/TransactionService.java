package com.n26.statistic.challenge.service;

import com.n26.statistic.challenge.model.Transaction;
import com.n26.statistic.challenge.model.TransactionSummary;

public interface TransactionService {

	void addTransaction(Transaction transaction);

	TransactionSummary getTransactionSummary();
}
