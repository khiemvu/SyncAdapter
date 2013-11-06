package com.qsoft.onlinedio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "onlinedio.db";
    private static final int DATABASE_VERSION = 1;

    // DB Table consts
    public static final String HOMEFEED_TABLE_NAME = "homefeed";
    public static final String HOMEFEED_COL_ID = "_id";
    public static final String HOMEFEED_COL_USER_ID = "user_id";
    public static final String HOMEFEED_COL_TITLE = "title";
    public static final String HOMEFEED_COL_THUMBNAIL = "thumbnail";
    public static final String HOMEFEED_COL_DESCRIPTION = "description";
    public static final String HOMEFEED_COL_SOUND_PATH = "sound_path";
    public static final String HOMEFEED_COL_DURATION= "duration";
    public static final String HOMEFEED_COL_PLAYED= "played";
    public static final String HOMEFEED_COL_CREATED_AT = "created_at";
    public static final String HOMEFEED_COL_UPDATED_AT= "updated_at";
    public static final String HOMEFEED_COL_LIKES = "likes";
    public static final String HOMEFEED_COL_VIEWED= "viewed";
    public static final String HOMEFEED_COL_COMMENTS = "comments";
    public static final String HOMEFEED_COL_USERNAME = "user_name";
    public static final String HOMEFEED_COL_DISPLAY_NAME = "display_name";
    public static final String HOMEFEED_COL_AVATAR = "avatar";


    // DB Table consts
    public static final String PROFILE_TABLE_NAME = "profile";
    public static final String PROFILE_COL_ID = "_id";
    public static final String PROFILE_COL_FACEBOOK_ID = "facebook_id";
    public static final String PROFILE_COL_USERNAME = "username";
    public static final String PROFILE_COL_PASSWORD = "password";
    public static final String PROFILE_COL_AVATAR = "avatar";
    public static final String PROFILE_COL_COVER_IMAGE = "cover_image";
    public static final String PROFILE_COL_DISPLAY_NAME= "display_name";
    public static final String PROFILE_COL_FULL_NAME= "full_name";
    public static final String PROFILE_COL_PHONE = "phone";
    public static final String PROFILE_COL_BIRTHDAY= "birthday";
    public static final String PROFILE_COL_GENDER = "gender";
    public static final String PROFILE_COL_COUNTRY_ID= "country_id";
    public static final String PROFILE_COL_STORAGE_PLAN_ID = "storage_plan_id";
    public static final String PROFILE_COL_DESCRIPTION = "description";
    public static final String PROFILE_COL_CREATED_AT = "created_at";
    public static final String PROFILE_COL_UPDATED_AT = "updated_at";
    public static final String PROFILE_COL_SOUND = "sounds";
    public static final String PROFILE_COL_FAVORITE = "favorites";
    public static final String PROFILE_COL_LIKE = "likes";
    public static final String PROFILE_COL_FOLLOWING = "followings";
    public static final String PROFILE_COL_AUDIENCE = "audiences";

    // Database creation sql statement
    public static final String DATABASE_CREATE_PROFILE = "create table "
            + PROFILE_TABLE_NAME + "(" +
            PROFILE_COL_ID + " integer primary key autoincrement, " +
            PROFILE_COL_FACEBOOK_ID + " integer, " +
            PROFILE_COL_USERNAME + " text, " +
            PROFILE_COL_PASSWORD + " text, " +
            PROFILE_COL_AVATAR + " text, " +
            PROFILE_COL_COVER_IMAGE + " text, " +
            PROFILE_COL_DISPLAY_NAME + " text, " +
            PROFILE_COL_FULL_NAME + " text, " +
            PROFILE_COL_PHONE + " text, " +
            PROFILE_COL_BIRTHDAY + " text, " +
            PROFILE_COL_GENDER + " integer, " +
            PROFILE_COL_COUNTRY_ID + " integer, " +
            PROFILE_COL_STORAGE_PLAN_ID + " integer, " +
            PROFILE_COL_DESCRIPTION + " text, " +
            PROFILE_COL_CREATED_AT + " text, " +
            PROFILE_COL_UPDATED_AT + " text, " +
            PROFILE_COL_SOUND + " integer, " +
            PROFILE_COL_FAVORITE + " integer, " +
            PROFILE_COL_LIKE + " integer, " +
            PROFILE_COL_FOLLOWING + " integer, " +
            PROFILE_COL_AUDIENCE + " integer " +
            ");";

    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table "
            + HOMEFEED_TABLE_NAME + "(" +
            HOMEFEED_COL_ID + " integer primary key autoincrement, " +
            HOMEFEED_COL_USER_ID + " integer, " +
            HOMEFEED_COL_TITLE + " text, " +
            HOMEFEED_COL_THUMBNAIL + " text, " +
            HOMEFEED_COL_DESCRIPTION + " text, " +
            HOMEFEED_COL_SOUND_PATH + " text, " +
            HOMEFEED_COL_DURATION + " integer, " +
            HOMEFEED_COL_PLAYED + " integer, " +
            HOMEFEED_COL_CREATED_AT + " text, " +
            HOMEFEED_COL_UPDATED_AT + " text, " +
            HOMEFEED_COL_LIKES + " integer, " +
            HOMEFEED_COL_VIEWED + " integer, " +
            HOMEFEED_COL_COMMENTS + " integer, " +
            HOMEFEED_COL_USERNAME + " text, " +
            HOMEFEED_COL_DISPLAY_NAME + " text, " +
            HOMEFEED_COL_AVATAR + " text " +
            ");";


    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + HOMEFEED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        onCreate(db);
    }

}
