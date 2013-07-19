package com.qsoft.bankaccount_3_2.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;

	public MyDBHelper(Context context, String name) {
		super(context, name, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		BankAccountScheme.onCreate(db);

		TransactionScheme.onCreate(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "
				+ BankAccountScheme.TABLE_BANK_ACCOUNT);
		db.execSQL("DROP TABLE IF EXISTS "
				+ TransactionScheme.TABLE_TRANSACTION);
	}

}
