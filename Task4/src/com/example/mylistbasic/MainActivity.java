package com.example.mylistbasic;

import java.io.File;
import java.util.ArrayList;
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
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private GridView gv;
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
	    
	    filePath.add("Loc: "+rootStr);
	    filePath.add(" ");
	    filePath.add(" ");
	    
	    if(isRoot(rootStr)){
	    	filePath.add("(../)");
	    	filePath.add(" ");
	    	filePath.add(" ");
	    }
	    
	    for(int i = 0; i < files.length; i++) {
	    	file = files[i];
	    	if(file.isDirectory()) {
	    		filePath.add("/"+file.getName());
	    		for (int j = 0; j < 2; j++)
	    			filePath.add(" ");
	    	} else {
	    		filePath.add(getFileName(file.getName()));
	    		filePath.add(getFileExt(file.getName()));
	    		filePath.add(getFileSize(file));
	    	}
	    }

	    gv = (GridView) findViewById(R.id.gridview);
	    adapter = new ArrayAdapter<String>(this, 
	            android.R.layout.simple_list_item_1, filePath);
	    gv.setAdapter(adapter);

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		     public void onItemClick(AdapterView<?> parentAdapter, 
		    		 View view, int position, long id) {
		         if(position > 0) { //do something
		        	 if(position/3 == 1) { // go to parent folder
		        		 navigateRev();
		        	 } else { // two extra entry at top, so roll back.
		        		 if(files[position/3-2].isDirectory()){ //check if directory
			        	 navigateDir(position/3);
		        		 } else {
			        	 showFilename(position/3);
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
