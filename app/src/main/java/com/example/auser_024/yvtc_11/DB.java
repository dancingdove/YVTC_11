package com.example.auser_024.yvtc_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Auser-024 on 2017/11/7.
 */

public class DB {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "mytable";
    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS mytable(_id INTEGER PRIMARY KEY,note TEXT NOT NULL,created TEXT,email TEXT);";

    private static class DatabasesHelper extends SQLiteOpenHelper {
        public DatabasesHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
            onCreate(db);
        }
    }

    private Context mCtx = null;
    private DatabasesHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.mCtx = ctx;
    }

    public DB open() throws SQLException {
        dbHelper = new DatabasesHelper(mCtx);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_CREATED = "created";
    public static final String KEY_EMAIL = "email";

    String[] strcols = new String[] {
            KEY_ROWID,KEY_NOTE,KEY_CREATED,KEY_EMAIL
    };

    public Cursor getAll() {
//        return  db.rawQuery("SELECT * FROM mytable",null);
        return db.query(DATABASE_TABLE,strcols,null,null,null,null,null);
    }

    public long create(String com,String phone,String mail) {
        Date now = new Date();

        ContentValues args = new ContentValues();
        args.put(KEY_NOTE,com);
        args.put(KEY_CREATED,phone);
        args.put(KEY_EMAIL,mail);
        return db.insert(DATABASE_TABLE,null,args);
    }

    public boolean delete (long rowId) {
        if (rowId > 0)
            return db.delete(DATABASE_TABLE,KEY_ROWID + "=" + rowId,null) > 0;
        else
            return db.delete(DATABASE_TABLE,null,null) > 0;
    }

    public boolean delete() {
//        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) >0;
        return delete(-1);
    }


    public boolean update(long roeId,String com,String phone,String mail) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE,com);
        args.put(KEY_CREATED,phone);
        args.put(KEY_EMAIL,mail);
        return db.update(DATABASE_TABLE,args,KEY_ROWID + "=" + roeId, null) >0;
    }
}
