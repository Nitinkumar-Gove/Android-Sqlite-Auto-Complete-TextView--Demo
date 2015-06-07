package com.task.database;

import com.task.database.DatabaseContract.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



// this class is used to performs different database operations
@SuppressLint("NewApi")
public class DatabaseHelper extends SQLiteOpenHelper {

	// defining constants required for database creation and query execution
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Records.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    // query string to create table
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + Database.TABLE_NAME + "(" +
       
        Database.COLUMN_NAME_RECORD_NAME + TEXT_TYPE + COMMA_SEP +
        Database.COLUMN_NAME_RECORD_AGE + TEXT_TYPE + COMMA_SEP +
        Database.COLUMN_NAME_RECORD_DEPARTMENT + TEXT_TYPE +
       
        ")";

    
    // query string to delete table from database
    private static final String SQL_DELETE_ENTRIES =
    	    "DROP TABLE IF EXISTS " + Database.TABLE_NAME;
    
	public DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null, DATABASE_VERSION);
	}
	
	
	// create records database	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	// refresh database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		// This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
       
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

	}
	
	// downgrade database to previous versions
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		super.onDowngrade(db, oldVersion, newVersion);
	}
	
	

}
