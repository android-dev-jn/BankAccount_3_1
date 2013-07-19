package com.qsoft.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.qsoft.bankaccount_3_2.data.dao.TransactionDAO;
import com.qsoft.bankaccount_3_2.data.entity.TransactionDTO;

public class TransactionDAOTest extends AndroidTestCase {

	private TransactionDAO transactionDAO;

	private String accountNumber = "1234567890";
	private long timestamp = System.currentTimeMillis();
	private double amount = 100;
	private String description = "add 100 dollars";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		transactionDAO = new TransactionDAO(getContext(), null);
	}

	// 1 - Test create new transaction
	public void testCreateNewTransaction() {
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, timestamp, description);
		long result = transactionDAO.createTransaction(transactionDTO);

		assertEquals(1, result);
		assertEquals(1, transactionDAO.getSize());
	}

	// 2 - Test get existed transaction
	public void testGetExistedTransactionByAccountNumber() {
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, timestamp, description);
		transactionDAO.createTransaction(transactionDTO);

		TransactionDTO transactionDTO2 = transactionDAO
				.getTransaction(accountNumber);

		assertEquals(transactionDTO.getAccountNumber(),
				transactionDTO2.getAccountNumber());
		assertEquals(transactionDTO.getAmount(), transactionDTO2.getAmount());
		assertEquals(transactionDTO.getTimestamp(),
				transactionDTO2.getTimestamp());
		assertEquals(transactionDTO.getDescription(),
				transactionDTO2.getDescription());
	}

	// 3 - Test get unexist transaction
	public void testGetUnexistTransactionByAccountNumber() {
		TransactionDTO transactionDTO = transactionDAO
				.getTransaction(accountNumber);

		assertEquals(null, transactionDTO);
	}

	// 4 - Test get list transaction in period of time
	public void testGetTransactionsOccurredWithPeriodOfTime() {
		Long startTime = 1000L;
		Long stopTime = 2000L;
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, startTime, description);
		transactionDAO.createTransaction(transactionDTO);

		description = "Withdraw";
		TransactionDTO transactionDTO2 = new TransactionDTO(accountNumber,
				amount, stopTime, description);
		transactionDAO.createTransaction(transactionDTO2);

		ArrayList<TransactionDTO> list = transactionDAO
				.getTransactionsInPeriodOfTime(accountNumber, startTime,
						stopTime);
		assertTrue(list != null);
		assertEquals(2, list.size());
	}

	// 5 - Test get list of the last n transaction
	public void testGetTheLastNTransactions() {
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, timestamp, description);
		transactionDAO.createTransaction(transactionDTO);

		TransactionDTO transactionDTO2 = new TransactionDTO(accountNumber,
				amount, timestamp, description);
		transactionDAO.createTransaction(transactionDTO2);

		ArrayList<TransactionDTO> list = transactionDAO
				.getTheLastNTransactions(accountNumber, 2);
		assertTrue(list != null);
		assertEquals(2, list.size());
	}

}
