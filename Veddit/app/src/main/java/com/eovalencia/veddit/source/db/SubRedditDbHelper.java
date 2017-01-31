package com.eovalencia.veddit.source.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Eovalencia on 22/01/17.
 */

public class SubRedditDbHelper extends SQLiteOpenHelper {

    public SubRedditDbHelper(Context context){
        super(context, SubRedditContract.DATABASE_NAME, null, SubRedditContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
                SubRedditContract.SubRedditEntry.TABLE_NAME, BaseColumns._ID,
                SubRedditContract.SubRedditEntry.ID,
                SubRedditContract.SubRedditEntry.TITLE, SubRedditContract.SubRedditEntry.HEADER_IMG,
                SubRedditContract.SubRedditEntry.PUBLIC_DESCRIPTION, SubRedditContract.SubRedditEntry.SUBSCRIBERS,
                SubRedditContract.SubRedditEntry.CREATED, SubRedditContract.SubRedditEntry.ICON_IMG,
                SubRedditContract.SubRedditEntry.DISPLAY_NAME, SubRedditContract.SubRedditEntry.DESCRIPTION,
                SubRedditContract.SubRedditEntry.URL));
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SubRedditContract.SubRedditEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
