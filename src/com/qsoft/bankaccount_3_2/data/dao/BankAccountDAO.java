package com.qsoft.bankaccount_3_2.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qsoft.bankaccount_3_2.data.entity.BankAccountDTO;

public class BankAccountDAO {

	private SQLiteDatabase db;

	public BankAccountDAO(Context context, String dbName) {
		super();
		MyDBHelper dbHelper = new MyDBHelper(context, dbName);
		this.db = dbHelper.getWritableDatabase();
	}

	public long save(BankAccountDTO bankAccount) {
		ContentValues value = new ContentValues();
		value.put(BankAccountScheme.KEY_ACCOUNT_NUMER,
				bankAccount.getAccountNumber());
		value.put(BankAccountScheme.KEY_BALANCE, bankAccount.getBalance());
		value.put(BankAccountScheme.KEY_OPEN_TIME, bankAccount.getTimestamp());
		return db.insert(BankAccountScheme.TABLE_BANK_ACCOUNT, null, value);
	}

	public BankAccountDTO getAccount(String accountNumber) {
		Cursor cursor = db.query(BankAccountScheme.TABLE_BANK_ACCOUNT, null,
				BankAccountScheme.KEY_ACCOUNT_NUMER + " = " + accountNumber,
				null, null, null, null);
		BankAccountDTO bankaccount = null;
		Log.e("BankAccountDAO", " null");
		if (cursor.moveToFirst()) {
			Log.e("BankAccountDAO", cursor.getString(1));
			Log.e("BankAccountDAO", "khac null");
			bankaccount = new BankAccountDTO();
			bankaccount.setAccountNumber(cursor.getString(1));
			bankaccount.setBalance(cursor.getDouble(2));
			bankaccount.setTimestamp(cursor.getLong(3));
		}
		cursor.close();
		return bankaccount;
	}

	public int getSize() {
		Cursor cursor = db.query(BankAccountScheme.TABLE_BANK_ACCOUNT, null,
				null, null, null, null, null);
		return cursor.getCount();
	}

	public int update(BankAccountDTO bankAccount) {
		ContentValues value = new ContentValues();
		value.put(BankAccountScheme.KEY_ACCOUNT_NUMER,
				bankAccount.getAccountNumber());
		value.put(BankAccountScheme.KEY_BALANCE, bankAccount.getBalance());
		value.put(BankAccountScheme.KEY_OPEN_TIME, bankAccount.getTimestamp());
		return db.update(
				BankAccountScheme.TABLE_BANK_ACCOUNT,
				value,
				BankAccountScheme.KEY_ACCOUNT_NUMER + " = "
						+ bankAccount.getAccountNumber(), null);
	}
}
