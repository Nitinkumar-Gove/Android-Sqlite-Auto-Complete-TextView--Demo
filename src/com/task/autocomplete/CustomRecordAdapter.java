package com.task.autocomplete;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class CustomRecordAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private Record []  records;
   
 
    public CustomRecordAdapter(Activity activity, Record [] r) {
        this.activity = activity;
        this.records = r;
    }
 
    @Override
    public int getCount() {
        return records.length;
    }
 
    @Override
    public Object getItem(int location) {
        return records[location];
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_customitem, null);
 
        
        TextView rName = (TextView) convertView.findViewById(R.id.txtRName);
        TextView rAge = (TextView) convertView.findViewById(R.id.txtRAge);
        TextView rDept = (TextView) convertView.findViewById(R.id.txtRDept);
        
 
        // getting movie data for the row
        Record r=records[position];
 
        // name
        rName.setText(r.getName());
         
        // age
        rAge.setText(r.getAge());
         
        // department
        rDept.setText(r.getDepartment());
 
        return convertView;
    }
 
}