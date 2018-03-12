package com.n26.statistic.challenge;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.n26.statistic.challenge.controller.TransactionRestController;
import com.n26.statistic.challenge.model.Transaction;
import com.n26.statistic.challenge.service.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RealtimeStatisticApiApplicationTests {

	@Autowired
	TransactionRestController controller;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private Gson json;

	@Autowired
	TransactionService transactionService;

	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}

	@Test
	public void shouldAcceptValidTransactionsTest() throws Exception {

		for(Transaction transaction : loadTransactionSuccessList()) {
			String transactionToJson = json.toJson(transaction);
			mvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}
	}

	private List<Transaction> loadTransactionSuccessList() {
		List<Transaction> transactionsSuccess = new ArrayList<Transaction>();
		Instant time = Instant.now();
		transactionsSuccess.add(new Transaction(1.0, time.toEpochMilli()));
		transactionsSuccess.add(new Transaction(2.0, time.toEpochMilli()));
		transactionsSuccess.add(new Transaction(2.0, time.toEpochMilli()));
		transactionsSuccess.add(new Transaction(5.0, time.toEpochMilli()));
		transactionsSuccess.add(new Transaction(10.0, time.toEpochMilli()));
		return transactionsSuccess;
	}

	@Test
	public void shouldRejectInvalidTimeFrameTest() throws Exception {

		for(Transaction transaction : loadTransactionErrorList()) {
			String transactionToJson = json.toJson(transaction);
			mvc.perform(post("/transactions")
					.content(transactionToJson)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNoContent());
		}
	}

	private List<Transaction> loadTransactionErrorList() {
		List<Transaction> transactionsSuccess = new ArrayList<Transaction>();
		Instant time = Instant.now();
		transactionsSuccess.add(new Transaction(10.0, time.minusSeconds(61).toEpochMilli()));
		return transactionsSuccess;
	}

	@Test
    public void shouldReturnSummaryResults() throws Exception {

        mvc.perform(get("/statistics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("count", is(5)))
                .andExpect(jsonPath("sum", is(20.0)))
                .andExpect(jsonPath("avg", is(4.0)))
                .andExpect(jsonPath("max", is(10.0)))
                .andExpect(jsonPath("min", is(1.0)));
    }

}
