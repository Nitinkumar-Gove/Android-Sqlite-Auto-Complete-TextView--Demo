package com.task.database;

import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.task.autocomplete.Record;
import com.task.database.DatabaseContract.Database;


public class DatabaseOperations {
	
	
	
	// ideally all the database functions, declared below,  should also be placed in a separate class
	
	/*
	 *  insert dummy records in database table
	 */
	public static void insertDummyRecords(SQLiteDatabase mdb,Record [] r)
	{
		deleteAllRecords(mdb,Database.TABLE_NAME);  
		
		// Log.d("insertDummyRecords", " came here ");
		
		if(isTableEmpty(mdb,Database.TABLE_NAME))
			for (int i = 0 ; i<r.length;i++)
				insertRecord(mdb,r[i]);
		// else
		//	Log.d("insertDummyRecords", " table not empty ");
	}
	
	/*
	 *  insert record into database table
	 */
	
	public static void insertRecord(SQLiteDatabase mdb,Record r)
	{
		
		// Log.d("insertRecord", " came here ");
		
		// Create a new map of values, where column names are the keys
		
		ContentValues values = new ContentValues();
		values.put(Database.COLUMN_NAME_RECORD_NAME, r.getName());
		values.put(Database.COLUMN_NAME_RECORD_AGE, r.getAge());
		values.put(Database.COLUMN_NAME_RECORD_DEPARTMENT, r.getDepartment());
		
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = mdb.insert(
		         Database.TABLE_NAME,
		         null,
		         values);
		
		if(newRowId!=-1)
		{
			// Log.d("record inserted at", newRowId + " row created ");
		}
		//  else
			// Log.d("record inserted at", newRowId + " row created ");
	}
	
	
	/*
	 *  delete all rows in table
	 */
	
	public static void deleteAllRecords(SQLiteDatabase mdb,String tableName)
	{
		mdb.execSQL("delete from "+ tableName);
	}
	
	/*
	 *  check if table is empty
	 */
	
	public static  boolean isTableEmpty(SQLiteDatabase mdb, String tableName)
	{
		// Log.d("isTableEmpty", " came here ");
		
		Cursor cur = mdb.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
		
		if (cur != null) {
		    cur.moveToFirst();                       // Always one row returned.
		    if (cur.getInt (0) == 0) {               // Zero count means empty table.
		        return true;
		    }
		}
		
		return false;
	}
	
	// get all names from the table
	
	public static String []  getAllRecordNames(SQLiteDatabase mdb,String tableName, String clmName)
	{
		Vector <String> allnames= new Vector<String>();
		
		Cursor cursor = mdb.query(tableName, null, null, null, null, null, null, null);
	    if(cursor != null)
	            {
	                while(cursor.moveToNext()){
	                    String term = cursor.getString(cursor.getColumnIndex(clmName));
	                    
	                    if(!isPresent(allnames,term)) // ### check for duplicate names
	                    {
	                    	allnames.add(term);
	                    }
	                   	                   
	                }
	            }
	    
	    String [] allNames = new String[allnames.size()];
	    
	    for(int i=0; i < allnames.size(); i++)
	    {
	    	allNames[i] = allnames.get(i);
	    }
	    
	    return allNames;
	    
	}
	
	// check whether a string is already present in the vector or  not
	
	public static boolean isPresent(Vector<String> s, String chk)
	{
				
		for(int i = 0 ; i<s.size();i++)
		{
			if(s.get(i).equalsIgnoreCase(chk))
			{
				return true;
			}
		}
		return false;
	}
	
	// fetch all the records matching the selected name
	
	public static Record[] getAllRecordsForName(SQLiteDatabase mdb,String tableName, String clmName, String matchValue)
	{
		Vector <Record> allRecords= new Vector<Record>();
		
		String q="SELECT * FROM " + tableName + " WHERE " + clmName + "='" + matchValue +"'";

		Cursor  cursor = mdb.rawQuery(q,null);

		        if (cursor != null) {
		        	 while(cursor.moveToNext())
		        	   {
		                    String dept = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME_RECORD_DEPARTMENT));
		                    String name = cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME_RECORD_NAME));
		                    String age =  cursor.getString(cursor.getColumnIndex(Database.COLUMN_NAME_RECORD_AGE));
		                    
		                    // Log.d("getAllRecordsForName", matchValue + " in " + dept + " department " );

		                    Record temp = new Record(name, age,dept);
		                    allRecords.add(temp);
		                    
		                    // use these strings as you want
		                }
		        }
		
		Record records[] = new Record[allRecords.size()];
		for(int i = 0 ; i < allRecords.size() ; i++ )
		{
			records[i] = allRecords.get(i);
		}
		
		return records;
	}
}
