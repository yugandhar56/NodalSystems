package com.nodel.nodalsytems.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yugandhar on 1/8/2018.
 */

public class DBHelper extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "nodalSystemManager";

    //users table
    private static final String TABLE_USERS = "users";
    //users table fields
    private static final String USER_ID="user_id";
    private static final String USER_NAME="user_name";
    private static final String USER_EMAIL="user_email";
    private static final String USER_PASSWORD="user_password";
    private static final String USER_PhoneNumber="user_phone_number";
    private static final String USER_ADDRESS="user_address";



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
