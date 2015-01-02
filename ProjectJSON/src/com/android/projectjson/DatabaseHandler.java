package com.android.projectjson;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "androidmfd";

	// Login table name
	private static final String TABEL_LOGIN = "login";
	// private static final String TABEL_SESION = "sesi";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_UID = "id_member";
	private static final String KEY_NAMA = "nama_member";
	private static final String KEY_USERNAME = "username_member";
	private static final String KEY_PASSWRD = "password_member";
	private static final String KEY_ALAMAT = "alamat_member";
	private static final String KEY_NOTLP = "notlp_member";

	private static final String TABEL_SESION = "sesimfd";
	private static final String KEY_SESI = "id_sesi";

	// tabel sesi
	private static final String CREATE_SESI_TABLE = "CREATE TABLE "
			+ TABEL_SESION + "(" + KEY_ID + "INTEGER PRIMARY KEY," + KEY_SESI
			+ " TEXT" + ")";

	// tabel sesi
	private static final String CREATE_LOGIN_TABLE = "CREATE TABLE "
			+ TABEL_LOGIN + "(" + KEY_ID + "INTEGER PRIMARY KEY," + KEY_UID
			+ " TEXT," + KEY_NAMA + " TEXT," + KEY_USERNAME + " TEXT,"
			+ KEY_PASSWRD + " TEXT," + KEY_ALAMAT + " TEXT UNIQUE," + KEY_NOTLP
			+ " TEXT" + ")";

	// private static final String KEY_SESI = "id_sesi";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// buat tabel login
		db.execSQL(CREATE_LOGIN_TABLE);
		db.execSQL(CREATE_SESI_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABEL_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABEL_SESION);
		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */

	public void addUser(String uid) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_UID, uid); // ID unik

		// Inserting Row
		db.insert(TABEL_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting user data from database
	 * */

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT * FROM " + TABEL_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("id_member", cursor.getString(1));
			user.put("nama_member", cursor.getString(2));
			user.put("username_member", cursor.getString(3));
			user.put("password_member", cursor.getString(4));
			user.put("alamat_member", cursor.getString(5));
			user.put("notlp_member", cursor.getString(6));
		}
		cursor.close();
		db.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT * FROM " + TABEL_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public int getRowCountSesi() {
		String countQuery = "SELECT * FROM " + TABEL_SESION;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABEL_LOGIN, null, null);
		db.close();
	}

	public void addSession(String idSesi) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues nilai = new ContentValues();

		nilai.put(KEY_SESI, idSesi);

		db.insert(TABEL_SESION, null, nilai);
		db.close();
	}

	public HashMap<String, String> getIdSesi() {
		HashMap<String, String> kode = new HashMap<String, String>();
		String q = "SELECT * FROM " + TABEL_SESION;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(q, null);

		cur.moveToFirst();
		if (cur.getCount() > 0) {
			kode.put("id_sesi", cur.getString(1));
		}
		cur.close();
		db.close();

		return kode;

	}

	public void resetSesi() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABEL_SESION, null, null);
		db.close();
	}

}
