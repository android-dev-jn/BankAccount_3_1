package com.qsoft.bankaccount_3_2.data.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qsoft.bankaccount_3_2.data.entity.TransactionDTO;

public class TransactionDAO {
	private SQLiteDatabase db;

	public TransactionDAO(Context context, String dbName) {
		super();
		MyDBHelper dbHelper = new MyDBHelper(context, dbName);
		this.db = dbHelper.getWritableDatabase();
	}

	public long createTransaction(TransactionDTO transactionDTO) {
		ContentValues values = new ContentValues();
		values.put(TransactionScheme.KEY_ACCOUNT_NUMBER,
				transactionDTO.getAccountNumber());
		values.put(TransactionScheme.KEY_AMOUNT, transactionDTO.getAmount());
		values.put(TransactionScheme.KEY_TIMESTAMP,
				transactionDTO.getTimestamp());
		values.put(TransactionScheme.KEY_DESCRIPTION,
				transactionDTO.getDescription());
		return db.insert(TransactionScheme.TABLE_TRANSACTION, null, values);
	}

	public ArrayList<TransactionDTO> getTransactionOccurred(String accountNumber) {
		return new ArrayList<TransactionDTO>();
	}

	public ArrayList<TransactionDTO> getTransactionsInPeriodOfTime(
			String accountNumber, long startTime, long stopTime) {
		String query = "SELECT * FROM " + TransactionScheme.TABLE_TRANSACTION
				+ " WHERE " + TransactionScheme.KEY_TIMESTAMP + " >= "
				+ startTime + " AND " + TransactionScheme.KEY_TIMESTAMP
				+ " <= " + stopTime;
		ArrayList<TransactionDTO> list = new ArrayList<TransactionDTO>();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setAccountNumber(cursor.getString(1));
				transactionDTO.setAmount(cursor.getDouble(2));
				transactionDTO.setDescription(cursor.getString(3));
				transactionDTO.setTimestamp(cursor.getLong(4));
				list.add(transactionDTO);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public ArrayList<TransactionDTO> getTheLastNTransactions(
			String accountNumber, int n) {
		String query = "SELECT * FROM " + TransactionScheme.TABLE_TRANSACTION
				+ " ORDER BY " + TransactionScheme.KEY_ID + " LIMIT " + n;

		ArrayList<TransactionDTO> list = new ArrayList<TransactionDTO>();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setAccountNumber(cursor.getString(1));
				transactionDTO.setAmount(cursor.getDouble(2));
				transactionDTO.setDescription(cursor.getString(3));
				transactionDTO.setTimestamp(cursor.getLong(4));
				list.add(transactionDTO);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public int getSize() {
		Cursor cursor = db.query(TransactionScheme.TABLE_TRANSACTION, null,
				null, null, null, null, null);
		int result = cursor.getCount();
		cursor.close();
		return result;
	}

	public TransactionDTO getTransaction(String accountNumber) {
		Cursor cursor = db.query(TransactionScheme.TABLE_TRANSACTION, null,
				TransactionScheme.KEY_ACCOUNT_NUMBER + " = " + accountNumber,
				null, null, null, null);
		TransactionDTO transactionDTO = null;
		Log.e("TransactionDAO", cursor.getCount() + "");
		if (cursor.moveToFirst()) {
			transactionDTO = new TransactionDTO();
			transactionDTO.setAccountNumber(cursor.getString(1));
			transactionDTO.setAmount(cursor.getDouble(2));
			transactionDTO.setDescription(cursor.getString(3));
			transactionDTO.setTimestamp(cursor.getLong(4));
		}
		cursor.close();
		return transactionDTO;
	}
}
