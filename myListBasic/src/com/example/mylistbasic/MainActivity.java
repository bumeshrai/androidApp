package com.example.mylistbasic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showFiles();
	     
	}

	
	private void showFiles() {

		String rootStr = Environment.getRootDirectory().getPath();
		
	    File file = new File(rootStr);
	    File[] files = file.listFiles();
	        
	    ArrayList<String> item = new ArrayList<String>();
	    ArrayList<String> path = new ArrayList<String>();
	    
	    item.add(rootStr);
	    path.add(rootStr);

	    if(isRoot(rootStr)){
	    	path.add(file.getParent());
	    	item.add("../");
	    }
	    
	    for(int i = 0; i < files.length; i++) {
	    	file = files[i];
	    	if(file.isDirectory()) {
	    		item.add(file.getName()+"/");
	    	} else {
	    		item.add(file.getName());
	    	}
	    }
	    //rootStr = item.toString();

	    ListView lv = (ListView) findViewById(R.id.listView);
	    ArrayAdapter adapter = new ArrayAdapter<String>(this, 
	            android.R.layout.simple_list_item_1, item);
	    lv.setAdapter(adapter);
	     
	}
	
	public boolean isRoot(String rootStr) {
		if(rootStr.equals("/"))
			return false;
		else
			return true;
	}
	
}