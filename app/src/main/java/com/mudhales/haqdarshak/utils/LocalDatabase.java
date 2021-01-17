package com.mudhales.haqdarshak.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mudhales.haqdarshak.data.UserData;
import java.util.ArrayList;
import java.util.List;

public class LocalDatabase extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Haqdarshak.db";
    private static final String TABLE_RECORD_LIST = "recordlist";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String incidentTable = "CREATE TABLE " + TABLE_RECORD_LIST + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_AGE + " TEXT," +
                COLUMN_GENDER + " TEXT," +
                COLUMN_MOBILE + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_IMAGE + " TEXT)";

        db.execSQL(incidentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD_LIST);
        onCreate(db);

    }


    // Adding new records
    public synchronized long addRecords(String name,String age,String gender,String email,String mobile,String password,String image) {
        deleteRecords();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = 0;
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_AGE, age); //
            values.put(COLUMN_GENDER, gender); //
            values.put(COLUMN_EMAIL, email); //
            values.put(COLUMN_MOBILE, mobile); //
            values.put(COLUMN_PASSWORD, password); //
            values.put(COLUMN_IMAGE, image); //

            // Inserting Row
            id = db.insert(TABLE_RECORD_LIST, null, values);

        db.close(); // Closing database connection
        return id;
    }


    public boolean checkUserExit(String username) {

        boolean retVal = false;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_RECORD_LIST
                + " WHERE " + COLUMN_EMAIL + " = " + username
                + " OR "+ COLUMN_MOBILE + " = '" + username + "'";

        Cursor cursor = db.rawQuery(query, null);
        try {
            retVal = !(cursor != null && cursor.getCount() > 0);

            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }

            return retVal;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

    }

    // Getting records
    public synchronized ArrayList<UserData> getUserRecord(String username) {
        ArrayList<UserData> list = new ArrayList<>();
        // Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_RECORD_LIST
//                + " WHERE " + COLUMN_EMAIL + " = " + username
//                + " OR "+ COLUMN_MOBILE + " = '" + username + "'";
        // Select All Query
        String selectQuery = "SELECT * FROM "+TABLE_RECORD_LIST +" WHERE "+COLUMN_EMAIL +"= ? OR "+COLUMN_MOBILE +"= ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, new String[]{username, username});
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UserData data = new UserData();
                    data.setId(cursor.getString(0));
                    data.setName(cursor.getString(1));
                    data.setAge(cursor.getString(2));
                    data.setGender(cursor.getString(3));
                    data.setMobile(cursor.getString(4));
                    data.setEmail(cursor.getString(5));
                    data.setPassword(cursor.getString(6));
                    data.setImage(cursor.getString(7));
                    // Adding records to list
                    list.add(data);
                } while (cursor.moveToNext());
            }
            // return records list
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
            return list;
        } catch (Exception e) {
          //  Log.e("all_records", "" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return list;
    }
    public synchronized UserData checkLoginUser(String username,String password) {
        UserData data = null;
        String selectQuery;
        // Select All Query
        if (username.contains("@")){
            selectQuery = "SELECT * FROM "+TABLE_RECORD_LIST +" WHERE "+COLUMN_EMAIL +"= ? AND "+COLUMN_PASSWORD +"= ? ";
        }
        else selectQuery = "SELECT * FROM "+TABLE_RECORD_LIST +" WHERE "+COLUMN_MOBILE +"= ? AND "+COLUMN_PASSWORD +"= ? ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, new String[]{username, password});
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    data = new UserData();
                    data.setId(cursor.getString(0));
                    data.setName(cursor.getString(1));
                    data.setAge(cursor.getString(2));
                    data.setGender(cursor.getString(3));
                    data.setMobile(cursor.getString(4));
                    data.setEmail(cursor.getString(5));
                    data.setPassword(cursor.getString(6));
                    data.setImage(cursor.getString(7));
                    // Adding records to list
                } while (cursor.moveToNext());
            }
            // return records list
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
            return data;
        } catch (Exception e) {
            //  Log.e("all_records", "" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return data;
    }


    public synchronized void deleteRecords() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_RECORD_LIST, null,
                    null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
