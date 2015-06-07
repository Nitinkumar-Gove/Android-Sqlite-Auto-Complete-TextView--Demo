package com.task.autocomplete;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.task.database.DatabaseContract.Database;
import com.task.database.DatabaseHelper;
import com.task.database.DatabaseOperations;



public class AutoCompleteActivity extends ActionBarActivity {

	// declaration of dummy data - arrays can be derived from xml files which can result in reduction of dependency
	
    private String [] names = { "Rahul", "Pankaj","Manoj", "Pawan", "Anoop",
    		
    					"Sarita", "Rajesh", "Rohit", "Rahul","Rakesh",
    					
    				     "Rahul","Amey","Bhawna","Pawan","Ramesh"  };
    
	
	private String [] ages = { "20", "23", "24", "22","21",
			
				      "22", "24", "20", "24","21",
				      
			          "24", "22", "25", "24","23"
					};
	
	
    private String [] departments = { "Computer", "Information Technology","Mechanical", "Civil", "Mechanical",
    		
			"Information Technology", "Mechanical", "Computer", "Information Technology","Electrical",
			
		     "Electrical","Civil","Computer","Mechanical","Information Technology"  };
    
	// declaration of records array to hold record data
    
    private Record [] records;
    
    // declaration of helper and sql lite database instances
    
	public static DatabaseHelper mDbHelper;
	public static SQLiteDatabase mdb;
	
	// declaration of listview
	private ListView lstRecords;
    private CustomRecordAdapter adapterList;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_complete);
		
		// stop auto keyboard popping up
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		// initialize database instances
		
        mDbHelper=new DatabaseHelper(getApplicationContext());
		mdb =mDbHelper.getReadableDatabase();
		
		// initialize the listview
		
		lstRecords = (ListView)findViewById(R.id.lstRecords);
		
		// initialize record list
		
		createRecordList(names,ages,departments);
		
		// insert records into database 
		
		DatabaseOperations.insertDummyRecords(mdb,records);
		
		// initialize auto complete text view
		
		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView txtRecordName = (AutoCompleteTextView) findViewById(R.id.txtAutoName);
		
		// Initialize the array names
		String allNames [] = DatabaseOperations.getAllRecordNames(mdb,Database.TABLE_NAME, Database.COLUMN_NAME_RECORD_NAME);
		
		// Create the adapter and set it to the AutoCompleteTextView 
		final ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allNames);
		txtRecordName.setAdapter(adapter);
		
		// get selected item	
		txtRecordName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), parent.getItemAtPosition((int)id).toString(), Toast.LENGTH_LONG).show();
				
				
					
				// fetch all the records matching the selected name and display them in listview
				Record [] found = DatabaseOperations.getAllRecordsForName(mdb,Database.TABLE_NAME, Database.COLUMN_NAME_RECORD_NAME, parent.getItemAtPosition((int)id).toString());
				
				adapterList = new CustomRecordAdapter(AutoCompleteActivity.this, found);
		        lstRecords.setAdapter(adapterList);
				
			}
		});
		
		// reset list when there is no text in edittext
		txtRecordName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				if(s.length()==0)
				{
					Record [] found = new Record[0];
					
					adapterList = new CustomRecordAdapter(AutoCompleteActivity.this, found);
			        lstRecords.setAdapter(adapterList);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auto_complete, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// handle back press event here
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Close Application")
	        .setMessage("Do you want to close the application ?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}
	
	public void createRecordList(String []n, String [] a, String [] d)
	{
		// Log.d("createRecordList", " came here ");
		
		records= new Record[n.length];
		
		for(int i=0;i<n.length;i++)
		{
			records[i] = new Record(n[i],a[i],d[i]);
		}
	}
	
}
