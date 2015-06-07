package com.task.database;

import android.provider.BaseColumns;


// this class will have declarations for all the tables that we want to have in our database
public final class DatabaseContract {

	/*  
	 * To prevent someone from accidentally instantiating the contract class,
     * give it an empty constructor.
	 */
	public DatabaseContract() {
		
		}
	
	 /* Inner class that defines the table contents */
	public static abstract class Database implements BaseColumns
	{
		public static final String TABLE_NAME="record_master";
		
		public static final String COLUMN_NAME_RECORD_NAME="record_name";
		public static final String COLUMN_NAME_RECORD_AGE="record_age";
		public static final String COLUMN_NAME_RECORD_DEPARTMENT="record_department";
	}

}