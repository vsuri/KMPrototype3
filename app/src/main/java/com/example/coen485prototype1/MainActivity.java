package com.example.coen485prototype1;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.content.Intent;
import android.view.*;

public class MainActivity extends Activity {

	SQLiteDatabase database_ob;
	DBHelper openHelper_ob;
	EditText mEdit;
	ArrayAdapter<String> adapter;
	List<String> itemList;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    // Navigate to Shopping List
    public void shopNav(View view) {
        Intent intent = new Intent(this, Shopping.class);
        startActivity(intent);
    }

    // Navigate to Inventory List
    public void invNav(View view) {
        Intent intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }
    // Navigate to Inventory List
    public void eventsNav(View view) {
        Intent intent = new Intent(this, Events.class);
        startActivity(intent);
    }
}
