package com.amit.languagetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "testapp";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static final String TABLE_LANG_INFO = "lang_info";
    public static final String LANG_ID = "id";
    public static final String LANG_CODE = "lang_code";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_LANG_INFO + "("
                    + LANG_ID + " INTEGER PRIMARY KEY ,"
                    + LANG_CODE + " TEXT "
                    + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public int insertVideos(String langId, String langCode) {
        open();
        int i;
        ContentValues values = new ContentValues();

        //    values.put(LANG_ID, Integer.parseInt(langId));
        values.put(LANG_CODE, langCode);

        String Query = "Select * from " + TABLE_LANG_INFO;
        Cursor cursor = db.rawQuery(Query, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            // Update Row
            i = (int) db.update(TABLE_LANG_INFO, values, LANG_ID + " = " + Integer.parseInt(langId), null);

        } else {
            // Inserting Row
            i = (int) db.insert(TABLE_LANG_INFO, null, values);
        }
        close();
        return i;
    }

    public String getLanguageCode(String langId) {
        String whereClause = LANG_ID + " IN ( " + langId + " )";
        String countQuery = "SELECT " + LANG_CODE + " FROM " + TABLE_LANG_INFO + " where " + whereClause;
        open();
        Cursor cursor = db.rawQuery(countQuery, null);
        String lang = "en";
        if (cursor.moveToFirst()) {
            do {
                lang = cursor.getString(cursor.getColumnIndex(LANG_CODE));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return lang;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropAllTable(db);
    }

    private void open() {
        db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    private void dropAllTable(SQLiteDatabase db) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANG_INFO);
        onCreate(db);
    }


}