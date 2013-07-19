package com.qsoft.bankaccount_3_2.data.entity;

public class TransactionDTO {
	private String accountNumber;
	private double amount;
	private long timestamp;
	private String description;

	public TransactionDTO(String accountNumber, double amount, long timestamp,
			String description) {
		super();
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.timestamp = timestamp;
		this.description = description;
	}

	public TransactionDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
