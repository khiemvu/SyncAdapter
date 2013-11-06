package com.qsoft.onlinedio.database;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import static com.qsoft.onlinedio.database.Contract.AUTHORITY;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class ContentProvider extends android.content.ContentProvider
{
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "homefeeds";
    public static final int PATH_TOKEN = 1;
    public static final String PATH_FOR_ID = "homefeeds/*";
    public static final int PATH_FOR_ID_TOKEN = 2;

    public static final String PATH_PROFILE = "profiles";
    public static final int PATH_TOKEN_PROFILE = 3;
    public static final String PATH_FOR_ID_PROFILE = "profiles/*";
    public static final int PATH_FOR_PROFILE_ID_TOKEN = 4;
    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        matcher.addURI(authority, PATH_PROFILE, PATH_TOKEN_PROFILE);
        matcher.addURI(authority, PATH_FOR_ID_PROFILE, PATH_FOR_PROFILE_ID_TOKEN);
        return matcher;
    }

    // Content Provider stuff
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case PATH_TOKEN:
                return Contract.CONTENT_TYPE_DIR;
            case PATH_FOR_ID_TOKEN:
                return Contract.CONTENT_ITEM_TYPE;
            case PATH_TOKEN_PROFILE:
                return Contract.CONTENT_TYPE_DIR_PROFILE;
            case PATH_FOR_PROFILE_ID_TOKEN:
                return Contract.CONTENT_ITEM_TYPE_PROFILE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            // retrieve tv shows list
            case PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.HOMEFEED_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_ID_TOKEN: {
                int homeFeedId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.HOMEFEED_TABLE_NAME);
                builder.appendWhere(DbHelper.HOMEFEED_COL_ID + "=" + homeFeedId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            case PATH_TOKEN_PROFILE: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.PROFILE_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_PROFILE_ID_TOKEN: {
                int profileId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(DbHelper.PROFILE_TABLE_NAME);
                builder.appendWhere(DbHelper.PROFILE_COL_ID + "=" + profileId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case PATH_TOKEN: {
                long id = db.insertOrThrow(DbHelper.HOMEFEED_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(Contract.CONTENT_URI + "/" + id);
            }
            case PATH_TOKEN_PROFILE: {
                long id = db.insertOrThrow(DbHelper.PROFILE_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(Contract.CONTENT_URI_PROFILE + "/" + id);
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch (token) {
            case (PATH_TOKEN):
                rowsDeleted = db.delete(DbHelper.HOMEFEED_TABLE_NAME, selection, selectionArgs);
                break;
            case (PATH_FOR_ID_TOKEN):
                String homeFeedIdWhereClause = DbHelper.HOMEFEED_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    homeFeedIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DbHelper.HOMEFEED_TABLE_NAME, homeFeedIdWhereClause, selectionArgs);
                break;
            case (PATH_TOKEN_PROFILE):
                rowsDeleted = db.delete(DbHelper.PROFILE_TABLE_NAME, selection, selectionArgs);
                break;
            case (PATH_FOR_PROFILE_ID_TOKEN):
                String profileIdWhereClause = DbHelper.PROFILE_COL_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    profileIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(DbHelper.PROFILE_TABLE_NAME, profileIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int token = URI_MATCHER.match(uri);
        int rowsUpdated = -1;
        switch (token){
            case (PATH_TOKEN):
                rowsUpdated = db.update(DbHelper.HOMEFEED_TABLE_NAME, values, selection,selectionArgs);
                break;
            case (PATH_FOR_ID_TOKEN):
                rowsUpdated = db.update(DbHelper.HOMEFEED_TABLE_NAME, values, DbHelper.HOMEFEED_COL_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection)? "AND (" +
                        selection + ')' : ""),selectionArgs);
                break;
            case (PATH_TOKEN_PROFILE):
                rowsUpdated = db.update(DbHelper.PROFILE_TABLE_NAME, values, selection,selectionArgs);
                break;
            case (PATH_FOR_PROFILE_ID_TOKEN):
                rowsUpdated = db.update(DbHelper.PROFILE_TABLE_NAME, values, DbHelper.PROFILE_COL_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection)? "AND (" +
                                selection + ')' : ""),selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if(rowsUpdated != 1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}