package com.qsoft.onlinedio.database.entity;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.onlinedio.database.DbHelper;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class HomeModel implements Serializable
{
    private Long id;
    private Long user_id;
    private String title;
    private String thumbnail;
    private String description;
    private String sound_path;
    private int duration;
    private int played;
    private String created_at;
    private String updated_at;
    private int viewed;
    private String username;
    private int likes;
    private int comments;
    private String display_name;
    private String avatar;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Long user_id)
    {
        this.user_id = user_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSound_path()
    {
        return sound_path;
    }

    public void setSound_path(String sound_path)
    {
        this.sound_path = sound_path;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getPlayed()
    {
        return played;
    }

    public void setPlayed(int played)
    {
        this.played = played;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }

    public int getViewed()
    {
        return viewed;
    }

    public void setViewed(int viewed)
    {
        this.viewed = viewed;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getComments()
    {
        return comments;
    }

    public void setComments(int comments)
    {
        this.comments = comments;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public HomeModel(Long id, Long user_id, String title, String thumbnail, String desccription,
                     String sound_path, int duration, int played, String created_at,
                     String updated_at, int viewed, String username, int likes, int comments,
                     String display_name, String avatar)
    {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = desccription;
        this.sound_path = sound_path;
        this.duration = duration;
        this.played = played;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.viewed = viewed;
        this.username = username;
        this.likes = likes;
        this.comments = comments;
        this.display_name = display_name;
        this.avatar = avatar;
    }

    public HomeModel()
    {

    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(DbHelper.HOMEFEED_COL_ID, id);
        values.put(DbHelper.HOMEFEED_COL_USER_ID, user_id);
        values.put(DbHelper.HOMEFEED_COL_TITLE, title);
        values.put(DbHelper.HOMEFEED_COL_THUMBNAIL, thumbnail);
        values.put(DbHelper.HOMEFEED_COL_DESCRIPTION, description);
        values.put(DbHelper.HOMEFEED_COL_SOUND_PATH, sound_path);
        values.put(DbHelper.HOMEFEED_COL_DURATION, duration);
        values.put(DbHelper.HOMEFEED_COL_PLAYED, played);
        values.put(DbHelper.HOMEFEED_COL_CREATED_AT, created_at);
        values.put(DbHelper.HOMEFEED_COL_UPDATED_AT, updated_at);
        values.put(DbHelper.HOMEFEED_COL_LIKES, likes);
        values.put(DbHelper.HOMEFEED_COL_VIEWED, viewed);
        values.put(DbHelper.HOMEFEED_COL_COMMENTS, comments);
        values.put(DbHelper.HOMEFEED_COL_USERNAME, username);
        values.put(DbHelper.HOMEFEED_COL_DISPLAY_NAME, display_name);
        values.put(DbHelper.HOMEFEED_COL_AVATAR, avatar);
        return values;
    }

    // Create a object from a cursor
    public static HomeModel fromCursor(Cursor curHomeFeeds)
    {
        Long id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_ID));
        Long user_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USER_ID));
        String title = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_TITLE));
        String thumbnail = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_THUMBNAIL));
        String description = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DESCRIPTION));
        String sound_path = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_SOUND_PATH));
        int duration = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DURATION));
        int played = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_PLAYED));
        String created_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_CREATED_AT));
        String updated_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_UPDATED_AT));
        int likes = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_LIKES));
        int viewed = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_VIEWED));
        int comment = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_COMMENTS));
        String userName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USERNAME));
        String displayName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DISPLAY_NAME));
        String avatar = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_AVATAR));

        return new HomeModel(id, user_id, title, thumbnail, description, sound_path, duration, played, created_at,
                updated_at, viewed, userName, likes, comment, displayName, avatar);
    }
}
