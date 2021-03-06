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
import android.graphics.Path;
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
	    	filePath.add("Go to Parent Folder (../)");
	    }
	    
	    for(int i = 0; i < files.length; i++) {
	    	file = files[i];
	    	if(file.isDirectory()) {
	    		filePath.add("/"+file.getName());
	    	} else {
	    		String fName = getFileName(file.getName());
	    		String fExt = getFileExt(file.getName());
	    		String fSize = getFileSize(file);
	    		filePath.add(fName+"   "+fExt+"   "+fSize);
	    		//filePath.add(file.getName());
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
		         if(position > 0) { //do nothing
		        	 if(position == 1) { // go to parent folder
		        		 navigateRev();
		        	 } else { // two extra entry at top, so roll back.
		        		 if(files[position-2].isDirectory()){ //check if directory
			        	 navigateDir(position);
		        		 } else {
			        	 showFilename(position);
		        		 }
		        	 }
		        }
		     }

		});

	}
		
	private String getFileSize(File name) {
		// TODO Auto-generated method stub
		return(file.length()/1024+"kb");
	}


	private String getFileExt(String name) {
		// TODO Auto-generated method stub
		int mid= name.lastIndexOf(".");
		if(mid != -1)
			return(name.substring(mid+1,name.length()));
		else
			return("" );

	}


	private String getFileName(String name) {
		// TODO Auto-generated method stub
		int mid= name.lastIndexOf(".");
		if(mid != -1)
			return(name.substring(0,mid));
		else
			return(name);
	}


	public boolean isRoot(String root) {
		if(root.equals("/"))
			return false;
		else
			return true;
	}
		
	private void showFilename(int position) {
		// TODO Auto-generated method stub
    	 Toast.makeText(MainActivity.this, "Position ["+position+"] -  ["+"File: "+files[position-2].getName()+"]", Toast.LENGTH_SHORT).show();

	}

	private void navigateDir(int position) {
		// TODO Auto-generated method stub
    	 rootStr += "/"+files[position-2].getName();
    	 showFiles();
    	 Toast.makeText(MainActivity.this, "Position ["+position+"] -  ["+"Opening folder: "+rootStr+"]", Toast.LENGTH_SHORT).show();
		
	}

	private void navigateRev() {
		// TODO Auto-generated method stub
    	rootStr = file.getParentFile().getParent();
		showFiles();
    	Toast.makeText(MainActivity.this, "["+rootStr+"]", Toast.LENGTH_SHORT).show();
	}
	
}
