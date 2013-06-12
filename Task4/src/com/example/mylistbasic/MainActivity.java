package com.example.mylistbasic;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private GridView gv;
    private ArrayList<String> filePath;
    private String rootStr;
    private File file;
    private File[] files;
    private boolean goDown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		rootStr = Environment.getRootDirectory().getPath();
		showFiles();
	}

	
	private void showFiles() {
		
		goDown = true;
		
	    file = new File(rootStr);
	    files = file.listFiles();
	        
	    filePath = new ArrayList<String>();
	    
	    filePath.add("Loc: "+rootStr);
	    add2Space();
	    
	    if(isRoot(rootStr)){
	    	filePath.add("(../)");
	    	add2Space();
	    } else {
	    	filePath.add("You are at root");
	    	add2Space();
	    	goDown = false;
	    }
	    
	    for(int i = 0; i < files.length; i++) {
	    	file = files[i];
	    	if(file.isDirectory()) {
	    		filePath.add("/"+file.getName());
	    		add2Space();
	    		} else {
	    		filePath.add(getFileName(file.getName()));
	    		filePath.add(getFileExt(file.getName()));
	    		filePath.add(getFileSize(file));
	    	}
	    }

	    gv = (GridView) findViewById(R.id.gridview);
	    ArrayAdapter adapter = new ArrayAdapter<String>(this, 
	            android.R.layout.simple_list_item_1, filePath);
	    gv.setAdapter(adapter);

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		     public void onItemClick(AdapterView<?> parentAdapter, 
		    		 View view, int position, long id) {
		    	 switch(position/3) {
		            case 1:
		              //Go back to parent
		            	navigateRev();
		              break;
		            case 0:
		              //do nothing
		              break;
		            default:
		            	//open if directory 
		        		 if(files[position/3-2].isDirectory()){ //check if directory
			        	 navigateDir(position/3);
		        		 } else {
			        	 showFilename(position/3);
		        		 }
		        		 break;
		          }
		    	 
		     }

		});

	}
		
	private void add2Space() {
		// TODO Auto-generated method stub
		filePath.add(" ");
		filePath.add(" ");
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
    	 Toast.makeText(MainActivity.this, "["+"File: "+files[position-2].getName()+
    			 	"; Size: "+files[position-2].length()+
    			 	"; modified on: "+ new Date(files[position-2].lastModified()) +
    			 	"]", Toast.LENGTH_LONG).show();

	}

	private void navigateDir(int position) {
		// TODO Auto-generated method stub
    	 rootStr += "/"+files[position-2].getName();
    	 showFiles();
    	 Toast.makeText(MainActivity.this, "["+"Opening folder: "+rootStr+"]", Toast.LENGTH_LONG).show();
		
	}

	private void navigateRev() {
		// TODO Auto-generated method stub
    	rootStr = file.getParentFile().getParent();
		showFiles();
    	Toast.makeText(MainActivity.this, "["+rootStr+"]", Toast.LENGTH_SHORT).show();
	}

}
