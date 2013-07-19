package com.qsoft.bankaccount_3_2.data.dao;

import android.database.sqlite.SQLiteDatabase;

public class TransactionScheme {

	public static final String TABLE_TRANSACTION = "Transactions";

	public static final String KEY_ID = "TransactionId";
	public static final String KEY_ACCOUNT_NUMBER = "AccountNumber";
	public static final String KEY_AMOUNT = "Amount";
	public static final String KEY_TIMESTAMP = "Timestamp";
	public static final String KEY_DESCRIPTION = "Description";

	private static final String CREATE_TABLE_TRANSACTION = "Create Table "
			+ TABLE_TRANSACTION + " (" + KEY_ID
			+ " integer primary key autoincrement ," + KEY_ACCOUNT_NUMBER
			+ " text," + KEY_AMOUNT + " double," + KEY_DESCRIPTION + " text,"
			+ KEY_TIMESTAMP + " integer)";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TRANSACTION);
	}

}
