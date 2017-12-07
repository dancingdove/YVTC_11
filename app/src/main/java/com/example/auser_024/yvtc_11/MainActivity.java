package com.example.auser_024.yvtc_11;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView lv1;
    EditText et1,et2,et3;
    AlertDialog ad1,ad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = (ListView) findViewById(R.id.lsitView01);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        lv1.setOnItemClickListener(this);
        mDbHelper = new DB(this);
        mDbHelper.open();
        setAdapter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private DB mDbHelper;
    private Cursor mNotesCursor;


    private void setAdapter() {
        mNotesCursor = mDbHelper.getAll();
        if (mNotesCursor != null)
            mNotesCursor.moveToFirst();
        startManagingCursor(mNotesCursor);
        String[] from = new String[]{"note", "created","email"};
        int[] to = new int[]{R.id.tv1,R.id.tv2,R.id.tv3};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.mylist,
                mNotesCursor,
                from,
                to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        lv1.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"新增").setIcon(android.R.drawable.ic_menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,2,0,"修改").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,3,0,"刪除").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    int count;
    long rowId;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
//                count++;
//                mDbHelper.create(count+". note");
//                setAdapter();
                break;

            case 2:
//                mDbHelper.update(rowId,"note");
//                setAdapter();
                break;

            case 3:
                mDbHelper.delete(rowId);
                setAdapter();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rowId = id;
        Toast.makeText(this,"您點了第" + position + "項",Toast.LENGTH_SHORT).show();
    }

    public void add(View v) {
        mDbHelper.create(et1.getText().toString().trim(),
                et2.getText().toString().trim(),
                et3.getText().toString().trim());
        setAdapter();
    }

    public void update(View v) {
        mDbHelper.update(rowId,et1.getText().toString().trim(),
                et2.getText().toString().trim(),
                et3.getText().toString().trim());
        setAdapter();
    }

    public void delete(View v) {
        mDbHelper.delete(rowId);

        setAdapter();
    }

}