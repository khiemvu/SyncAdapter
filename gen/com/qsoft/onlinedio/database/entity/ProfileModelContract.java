package com.qsoft.onlinedio.database.entity;


import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public final class ProfileModelContract
    implements BaseColumns {
  public static final String AUTHORITY = "com.qsoft.onlinedio.database.dao";

  public static final String CONTENT_URI_PATH = "profiles";

  public static final String MIMETYPE_TYPE = "profiles";
  public static final String MIMETYPE_NAME = "com.qsoft.onlinedio.database.dao.provider";

  public static final int CONTENT_URI_PATTERN_MANY = 3;
  public static final int CONTENT_URI_PATTERN_ONE = 4;

  public static final Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).appendPath(CONTENT_URI_PATH).build();

  private ProfileModelContract() {
  }


  public static final String ID = "id";
  public static final String FACEBOOK_ID = "facebook_id";
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String AVATAR = "avatar";
  public static final String COVER_IMAGE = "cover_image";
  public static final String DISPLAY_NAME = "display_name";
  public static final String FULL_NAME = "full_name";
  public static final String PHONE = "phone";
  public static final String BIRTHDAY = "birthday";
  public static final String GENDER = "gender";
  public static final String COUNTRY_ID = "country_id";
  public static final String STORAGE_PLAN_ID = "storage_plan_id";
  public static final String DESCRIPTION = "description";
  public static final String CREATED_AT = "created_at";
  public static final String UPDATED_AT = "updated_at";
  public static final String SOUNDS = "sounds";
  public static final String FAVORITES = "favorites";
  public static final String LIKES = "likes";
  public static final String FOLLOWINGS = "followings";
  public static final String AUDIENCES = "audiences";
}
