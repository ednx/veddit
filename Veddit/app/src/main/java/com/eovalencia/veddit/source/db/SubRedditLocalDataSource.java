package com.eovalencia.veddit.source.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.source.SubRedditDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eovalencia on 22/01/17.
 */

public final class SubRedditLocalDataSource implements SubRedditDataSource {

    private static SubRedditLocalDataSource INSTANCE;

    private SubRedditDbHelper mDbHelper;

    private SubRedditLocalDataSource(Context context){
        mDbHelper = new SubRedditDbHelper(context);
    }

    public static SubRedditLocalDataSource getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SubRedditLocalDataSource.class) {
                if(INSTANCE == null)
                    INSTANCE = new SubRedditLocalDataSource(context);
            }
        }
        return INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    public ArrayList<SubReddit> readSubReddits(){
        ArrayList<SubReddit> list = new ArrayList<>();
        return null;
    }

    @Override
    public void saveSubReddit(SubReddit subReddit) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubRedditContract.SubRedditEntry.ID, subReddit.getId());
        values.put(SubRedditContract.SubRedditEntry.TITLE, subReddit.getTitle());
        values.put(SubRedditContract.SubRedditEntry.HEADER_IMG, subReddit.getImage());
        values.put(SubRedditContract.SubRedditEntry.PUBLIC_DESCRIPTION, subReddit.getDescription());
        values.put(SubRedditContract.SubRedditEntry.SUBSCRIBERS, subReddit.getSubscriber());
        values.put(SubRedditContract.SubRedditEntry.CREATED, subReddit.getDate());
        values.put(SubRedditContract.SubRedditEntry.ICON_IMG, subReddit.getIcon_img());
        values.put(SubRedditContract.SubRedditEntry.DISPLAY_NAME, subReddit.getDisplay_name());
        values.put(SubRedditContract.SubRedditEntry.DESCRIPTION, subReddit.getDescription_long());
        values.put(SubRedditContract.SubRedditEntry.URL, subReddit.getUrl());
        db.insert(SubRedditContract.SubRedditEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getSubReddits(SubRedditDataSource.OnFinishedListener listener) {
        List<SubReddit> subReddits = new ArrayList<SubReddit>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String query = SubRedditContract.SubRedditEntry.queryReadSubReddits;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                String itemId = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.TITLE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.HEADER_IMG));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.PUBLIC_DESCRIPTION));
                String subscribers = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.SUBSCRIBERS));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.CREATED));
                String icon = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.ICON_IMG));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.DISPLAY_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.DESCRIPTION));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.URL));
                SubReddit subReddit = new SubReddit(itemId, image, title, description, Long.parseLong(subscribers), Long.parseLong(created), icon, name, desc, url);
                subReddits.add(subReddit);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        if (subReddits.isEmpty()) {
           listener.onFailedSearch();
        } else {
            listener.onFinished((ArrayList<SubReddit>) subReddits);
        }

    }

    @Override
    public void getSubReddit(String subRedditId, SubRedditDataSource.GetSubRedditCallBack listener) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = SubRedditContract.SubRedditEntry.querySearchSubReddit + subRedditId;
        Cursor cursor = db.rawQuery(query, null);

        SubReddit subReddit = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String itemId = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.TITLE));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.HEADER_IMG));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.PUBLIC_DESCRIPTION));
            String subscribers = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.SUBSCRIBERS));
            String created = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.CREATED));
            String icon = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.ICON_IMG));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.DISPLAY_NAME));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.DESCRIPTION));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(SubRedditContract.SubRedditEntry.URL));
            subReddit = new SubReddit(itemId, image, title, description, Long.parseLong(subscribers), Long.parseLong(created), icon, name, desc, url);
        }
        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (subReddit != null) {
            listener.onFinished(subReddit);
        } else {
            listener.onFailedSearch();
        }
    }

    @Override
    public void deleteAllSubReddit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(SubRedditContract.SubRedditEntry.TABLE_NAME, null, null);

        db.close();
    }
}
