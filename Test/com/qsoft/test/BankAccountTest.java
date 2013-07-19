package com.qsoft.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import android.util.Log;

import com.qsoft.bankaccount_3_2.business.service.BankAccount;
import com.qsoft.bankaccount_3_2.data.dao.BankAccountDAO;
import com.qsoft.bankaccount_3_2.data.dao.TransactionDAO;
import com.qsoft.bankaccount_3_2.data.entity.BankAccountDTO;
import com.qsoft.bankaccount_3_2.data.entity.TransactionDTO;

public class BankAccountTest extends TestCase {
	private String accountNumber = "1234567890";
	BankAccountDAO mockBankAccountDAO = mock(BankAccountDAO.class);
	TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);
	Calendar mockCalendar = mock(Calendar.class);

	public void setUp() {
		MockitoAnnotations.initMocks(this);
		reset(mockBankAccountDAO);
		reset(mockTransactionDAO);
		reset(mockCalendar);
		BankAccount.bankAccountDAO = mockBankAccountDAO;
		BankAccount.transactionDAO = mockTransactionDAO;
		BankAccount.calendar = mockCalendar;
	}

	// 1
	public void testOpenAccount() {
		BankAccountDTO bankAccountDTO = BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> argumentCaptor = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(argumentCaptor.capture());

		assertEquals(accountNumber, argumentCaptor.getValue()
				.getAccountNumber());
		assertTrue(0 == argumentCaptor.getValue().getBalance());
	}

	// 2
	public void testGetAccountByAccountNumber() {
		ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor
				.forClass(String.class);
		BankAccountDTO bankAccountDTO = BankAccount.getAccount(accountNumber);
		verify(mockBankAccountDAO, times(1)).getAccount(
				accountNumberCaptor.capture());

		assertEquals(accountNumber, accountNumberCaptor.getValue());
	}

	// 3
	public void testDeposit() {
		double amount = 100, DELTA = 1e-2;
		String description = "deposit 100";

		BankAccountDTO bankAccountDTO = new BankAccountDTO(accountNumber, 50);
		when(mockBankAccountDAO.getAccount(bankAccountDTO.getAccountNumber()))
				.thenReturn(bankAccountDTO);
		BankAccount.deposit(accountNumber, amount, description);

		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(150, argument.getValue().getBalance(), DELTA);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	// 4
	public void testTimeStampDeposit() {
		double amount = 100;
		long timestamp = 1000;
		String description = "deposit 100";

		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber, 50);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber()))
				.thenReturn(bankAccount);
		when(mockCalendar.getTimeInMillis()).thenReturn(timestamp);
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, timestamp, description);

		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argumentCaptor = ArgumentCaptor
				.forClass(TransactionDTO.class);

		verify(mockTransactionDAO).createTransaction(argumentCaptor.capture());
		assertEquals(timestamp, argumentCaptor.getValue().getTimestamp());
	}

	// 5
	public void testWithdraw() {
		double amount = 100, DELTA = 1e-2;
		String description = "deposit 100";

		BankAccountDTO bankAccountDTO = new BankAccountDTO(accountNumber, 150);
		when(mockBankAccountDAO.getAccount(bankAccountDTO.getAccountNumber()))
				.thenReturn(bankAccountDTO);
		BankAccount.withdraw(accountNumber, amount, description);

		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(50, argument.getValue().getBalance(), DELTA);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	// 6
	public void testTimeStampWithDraw() {
		double amount = 100;
		long timestamp = 1000;
		String description = "deposit 100";

		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber, 150);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber()))
				.thenReturn(bankAccount);
		when(mockCalendar.getTimeInMillis()).thenReturn(timestamp);
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, timestamp, description);

		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argumentCaptor = ArgumentCaptor
				.forClass(TransactionDTO.class);

		verify(mockTransactionDAO).createTransaction(argumentCaptor.capture());
		assertEquals(timestamp, argumentCaptor.getValue().getTimestamp());
	}

	// 7
	public void testGetTransactionsOccurred() {
		ArgumentCaptor<String> accountNubmerCaptor = ArgumentCaptor
				.forClass(String.class);
		BankAccount.getTransactionOccurred(accountNumber);
		verify(mockTransactionDAO, times(1)).getTransactionOccurred(
				accountNubmerCaptor.capture());
		assertEquals(accountNumber, accountNubmerCaptor.getValue());
	}

	// 8
	public void testGestListTransactionsInPeriodOfTime() {
		long startTime = 1000, stopTime = 2000;

		BankAccount.getTransactionsInPeriodOfTime(accountNumber, startTime,
				stopTime);
		ArgumentCaptor<String> accountNubmerCaptor = ArgumentCaptor
				.forClass(String.class);
		verify(mockTransactionDAO, times(1)).getTransactionsInPeriodOfTime(
				accountNumber, startTime, stopTime);
	}

	// 9
	public void testGetTransactionsOccurredWithANumber() {
		int n = 10;
		BankAccount.getTheLastNTransactions(accountNumber, n);
		verify(mockTransactionDAO, times(1)).getTheLastNTransactions(
				accountNumber, n);
	}

	// 10
	public void testOpenAccountHasTimeStamp() {
		Long timestamp = System.currentTimeMillis();

		when(mockCalendar.getTimeInMillis()).thenReturn(timestamp);
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());
		Log.e("value", openAccount.getValue().getTimestamp() + "");
		assertTrue(openAccount.getValue() != null);
		assertEquals(timestamp, openAccount.getValue().getTimestamp());
	}
}
