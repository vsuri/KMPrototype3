package com.example.coen485prototype1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Shopping extends Activity {

    SQLiteDatabase database_ob;
    DBHelper openHelper_ob;
    EditText mEdit;
    ArrayAdapter<String> adapter;
    List<String> itemList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


        ListView myListView;
        db = new DBHelper(this).getWritableDatabase();
        itemList = new ArrayList<String>();

        //iterate through DB entries and populate captionList

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper.ID_COLUMN, DBHelper.ITEM_COLUMN};
        Cursor cursor = db.query(DBHelper.TABLE_SHOP, resultColumns, where, whereArgs, groupBy, having,
                order);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String item = cursor.getString(1);

            itemList.add(item);
            Log.d("DBVAL", "id =" + id + "item=" + item);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemList );

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(adapter);


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                removeItemFromList(position);

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    // Save item into database
    public void saveItemDetails(View view)
    {
        mEdit= (EditText) findViewById(R.id.editText1);
        String item =(mEdit.getText().toString());
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.ITEM_COLUMN, item);
        db.insert(DBHelper.TABLE_SHOP, null, newValues);
        db.close();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        //adapter.notifyDataSetChanged();
    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(Shopping.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete the item "+itemList.get(deletePosition) +"?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                //String pos=String.valueOf(deletePosition);
                //Toast.makeText(MainActivity.this, itemList.get(deletePosition), Toast.LENGTH_SHORT).show();

                //Storing item name to be deleted
                String tmp_name = itemList.get(deletePosition);
                //add into inventory table
                ContentValues newValues = new ContentValues();
                newValues.put(DBHelper.ITEM_COLUMN, tmp_name);
                db.insert(DBHelper.TABLE_INV, null, newValues);

                //Delete item in ShoppingList Table
                String whereClause = DBHelper.ITEM_COLUMN + "=?";
                String[] whereArgs = {itemList.get(deletePosition)};
                db.delete(DBHelper.TABLE_SHOP, whereClause, whereArgs);
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

}
