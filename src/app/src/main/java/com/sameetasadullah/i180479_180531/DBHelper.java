package com.sameetasadullah.i180479_180531;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    String CREATE_CHATS_TABLE = "CREATE TABLE " +
            chatsContract.Chats.TABLENAME + "(" +
            chatsContract.Chats._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            chatsContract.Chats._SENDER_NAME + " TEXT NOT NULL, " +
            chatsContract.Chats._RECEIVER_NAME + " TEXT NOT NULL, " +
            chatsContract.Chats._MESSAGE + " TEXT NOT NULL, " +
            chatsContract.Chats._TIME + " TEXT NOT NULL );";
    String DELETE_CHATS_TABLE = "DROP TABLE IF EXISTS " + chatsContract.Chats.TABLENAME;

    public DBHelper(@Nullable Context context) {
        super(context, chatsContract.DB_NAME, null, chatsContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CHATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_CHATS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CHATS_TABLE);
        onCreate(db);
    }
}
