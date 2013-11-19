package com.qsoft.onlinedio.database.entity;


import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public final class HomeModelContract
    implements BaseColumns {
  public static final String AUTHORITY = "com.qsoft.onlinedio.database.dao";

  public static final String CONTENT_URI_PATH = "homefeeds";

  public static final String MIMETYPE_TYPE = "homefeeds";
  public static final String MIMETYPE_NAME = "com.qsoft.onlinedio.database.dao.provider";

  public static final int CONTENT_URI_PATTERN_MANY = 1;
  public static final int CONTENT_URI_PATTERN_ONE = 2;

  public static final Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).appendPath(CONTENT_URI_PATH).build();

  private HomeModelContract() {
  }


  public static final String ID = "id";
  public static final String USER_ID = "user_id";
  public static final String TITLE = "title";
  public static final String THUMBNAIL = "thumbnail";
  public static final String DESCRIPTION = "description";
  public static final String SOUND_PATH = "sound_path";
  public static final String DURATION = "duration";
  public static final String PLAYED = "played";
  public static final String CREATED_AT = "created_at";
  public static final String UPDATED_AT = "updated_at";
  public static final String VIEWED = "viewed";
  public static final String USERNAME = "username";
  public static final String LIKES = "likes";
  public static final String COMMENTS = "comments";
  public static final String DISPLAY_NAME = "display_name";
  public static final String AVATAR = "avatar";
}
