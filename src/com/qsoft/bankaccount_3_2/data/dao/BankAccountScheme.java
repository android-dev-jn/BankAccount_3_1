package com.qsoft.bankaccount_3_2.data.dao;

import android.database.sqlite.SQLiteDatabase;

public class BankAccountScheme {

	public static final String TABLE_BANK_ACCOUNT = "BankAccount";

	public static final String KEY_ID = "BankAccountId";
	public static final String KEY_ACCOUNT_NUMER = "AccountNumber";
	public static final String KEY_BALANCE = "Balance";
	public static final String KEY_OPEN_TIME = "OpenTimeStamp";

	private static final String CREATE_TABLE_BANK_ACCOUNT = "CREATE TABLE "
			+ TABLE_BANK_ACCOUNT + " (" + KEY_ID
			+ " integer PRIMARY KEY AUTOINCREMENT, " + KEY_ACCOUNT_NUMER
			+ " text unique, " + KEY_BALANCE + " real, " + KEY_OPEN_TIME + " integer)";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_BANK_ACCOUNT);
	}

}
