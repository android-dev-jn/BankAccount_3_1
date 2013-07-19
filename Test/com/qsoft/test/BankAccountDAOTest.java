package com.qsoft.test;

import android.test.AndroidTestCase;

import com.qsoft.bankaccount_3_2.data.dao.BankAccountDAO;
import com.qsoft.bankaccount_3_2.data.entity.BankAccountDTO;

public class BankAccountDAOTest extends AndroidTestCase {

	private BankAccountDAO bankAccountDAO;

	private String accountNumber = "1234567890";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		bankAccountDAO = new BankAccountDAO(getContext(), null);
	}

	// 1 - Test insert new account, db should increase by 1
	public void testInsertNewAccount() {
		BankAccountDTO bankAccount = createBankAccount(accountNumber);
		long result = bankAccountDAO.save(bankAccount);
		assertEquals(1, result);
		assertEquals(1, bankAccountDAO.getSize());
	}

	// 2 - Test get unexisting account
	public void testGetUnexistAccountShouldReturnNull() {
		BankAccountDTO bankaccount = bankAccountDAO.getAccount(accountNumber);
		assertEquals(null, bankaccount);
	}

	// 3 - Test get exist account
	public void testGetExistAccount() {
		BankAccountDTO bankAccount1 = createBankAccount(accountNumber);
		bankAccountDAO.save(bankAccount1);
		BankAccountDTO bankAccount2 = bankAccountDAO.getAccount(accountNumber);

		assertEquals(bankAccount1.getAccountNumber(),
				bankAccount2.getAccountNumber());
		assertEquals(bankAccount1.getBalance(), bankAccount2.getBalance());
		assertEquals(bankAccount1.getTimestamp(), bankAccount2.getTimestamp());
	}

	// 4 - Test overwrite existing account
	public void testSaveWithExistingAccountNumberOverwritesExistingRecord() {
		BankAccountDTO bankAccount1 = createBankAccount(accountNumber);
		bankAccountDAO.save(bankAccount1);

		BankAccountDTO bankAccount2 = createBankAccount(accountNumber);
		bankAccountDAO.save(bankAccount2);

		assertEquals(1, bankAccountDAO.getSize());
	}

	// 5 - Test update account
	public void testUpdateExistingAccount() {
		BankAccountDTO bankAccount1 = createBankAccount(accountNumber);
		bankAccountDAO.save(bankAccount1);

		double newBalance = 200;
		BankAccountDTO bankAccount2 = bankAccountDAO.getAccount(accountNumber);
		bankAccount2.setBalance(newBalance);
		int result = bankAccountDAO.update(bankAccount2);

		assertEquals(1, result);

		BankAccountDTO bankAccount3 = bankAccountDAO.getAccount(accountNumber);
		assertEquals(newBalance, bankAccount3.getBalance());
	}

	private BankAccountDTO createBankAccount(String accountNumber) {
		return new BankAccountDTO(accountNumber);
	}

}
