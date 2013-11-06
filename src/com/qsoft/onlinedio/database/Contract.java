package com.qsoft.onlinedio.database;

import android.net.Uri;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class Contract
{

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.onlinedio.homefeed";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.onlinedio.homefeed";
    private static final String PATH_ENTRIES = "homefeeds";

    public static final String CONTENT_ITEM_TYPE_PROFILE = "vnd.android.cursor.item/vnd.onlinedio.profile";
    public static final String CONTENT_TYPE_DIR_PROFILE = "vnd.android.cursor.dir/vnd.onlinedio.profile";
    private static final String PATH_ENTRIES_PROFILE = "profiles";

    public static final String AUTHORITY = "com.qsoft.onlinedio.homefeed.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri CONTENT_URI_PROFILE =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES_PROFILE).build();
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

}
