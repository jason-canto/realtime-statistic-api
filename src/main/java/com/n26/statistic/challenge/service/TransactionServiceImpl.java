package com.n26.statistic.challenge.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import com.n26.statistic.challenge.exception.TimeOutOfScopeException;
import com.n26.statistic.challenge.model.Transaction;
import com.n26.statistic.challenge.model.TransactionSummary;

public class TransactionServiceImpl implements TransactionService {

	private final Queue<Transaction> transactions;

	private final int TOTAL_SECONDS = 60;

	public TransactionServiceImpl(Queue<Transaction> transactions) {
		super();
		this.transactions = new ConcurrentLinkedQueue<Transaction>();
	}

	@Override
	public synchronized void addTransaction(Transaction transaction) throws TimeOutOfScopeException {
		ZonedDateTime transationTime = getTransactionTime(transaction.getTimestamp());
		ZonedDateTime currentTime = getCurrentTime();
		if (transationTime.isBefore(currentTime.minusSeconds(TOTAL_SECONDS))) {
			throw new TimeOutOfScopeException("Transaction is older than 60 seconds");
		}
		transactions.add(transaction);
	}

	@Override
	public TransactionSummary getTransactionSummary() {
		DoubleSummaryStatistics summary = transactions.stream()
				.filter(t -> getTransactionTime(t.getTimestamp()).isBefore(getCurrentTime().minusSeconds(TOTAL_SECONDS)))
				.collect(Collectors.summarizingDouble(Transaction::getAmount));
		return new TransactionSummary(summary.getSum(), summary.getAverage(), summary.getMax(), 
				summary.getMin(), summary.getCount());
	}

	private ZonedDateTime getTransactionTime(Long transationTime) {
		return Instant.ofEpochMilli(transationTime).atZone(ZoneOffset.UTC);
	}

	private ZonedDateTime getCurrentTime() {
		return Instant.now().atZone(ZoneOffset.UTC);
	}

}
