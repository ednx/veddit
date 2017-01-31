package com.eovalencia.veddit.source.db;

/**
 * Created by Eovalencia on 22/01/17.
 */

public class SubRedditContract {

    public static final String DATABASE_NAME = "subreddit.db";

    public static final int DATABASE_VERSION = 1;

    interface Tables {

    }

    interface ColumnSubReddit {
        String TABLE_NAME = "subreddit";
        String ID = "id";
        String TITLE = "title";
        String HEADER_IMG = "header_img";
        String PUBLIC_DESCRIPTION = "public_description";
        String SUBSCRIBERS = "subscribers";
        String CREATED = "created";
        String ICON_IMG = "icon_img";
        String DISPLAY_NAME = "display_name";
        String DESCRIPTION = "description";
        String URL = "url";
    }

    public abstract static class SubRedditEntry implements ColumnSubReddit {
        public static final String queryReadSubReddits = "SELECT * FROM " + TABLE_NAME;
        public static final String querySearchSubReddit = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ";

    }


}
