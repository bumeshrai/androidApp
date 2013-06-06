package com.example.mylistbasic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
	
	private ListView lv;
	private ArrayAdapter adapter;
    private ArrayList<String> filePath;
    private String rootStr;
    private File file;
    private File[] files;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		rootStr = Environment.getRootDirectory().getPath();
		showFiles();
	}

	
	private void showFiles() {
		
	    file = new File(rootStr);
	    files = file.listFiles();
	        
	    filePath = new ArrayList<String>();
	    
	    filePath.add("Present Location is: "+rootStr);

	    if(isRoot(rootStr)){
	    	//path.add(file.getParent());
	    }
	    
	    for(int i = 0; i < files.length; i++) {
	    	file = files[i];
	    	if(file.isDirectory()) {
	    		filePath.add("/"+file.getName());
	    	} else {
	    		filePath.add(file.getName());
	    	}
	    }
	    //rootStr = item.toString();

	    lv = (ListView) findViewById(R.id.listView);
	    adapter = new ArrayAdapter<String>(this, 
	            android.R.layout.simple_list_item_1, filePath);
	    lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
		                             long id) {
		         
		    	 //Position starts from 1
		         if(files[position-1].isDirectory()){
		        	 rootStr += "/"+files[position-1].getName();
		        	 showFiles();
		        	 Toast.makeText(MainActivity.this, "Item with id ["+id+"] - Position ["+position+"] -  ["+rootStr+"]", Toast.LENGTH_SHORT).show();
		         } else {
		        	 Toast.makeText(MainActivity.this, "["+files[position-1].getName()+"]", Toast.LENGTH_SHORT).show();
		         }
		     }
		});

	}
		
	public boolean isRoot(String root) {
		if(root.equals("/"))
			return false;
		else
			return true;
	}
		
	
}
