package com.qsoft.onlinedio.database.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.onlinedio.database.Contract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultSortOrder;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/18/13
 */

@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "homefeeds")
@AdditionalAnnotation.DefaultContentUri(authority= Contract.AUTHORITY, path= Contract.HOME_CONTENT_URI_PATH)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name= Contract.HOME_MIMETYPE_NAME, type= Contract.HOME_MIMETYPE_TYPE)
public class HomeModel implements Serializable
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @DefaultSortOrder
    private Long _id;
    @DatabaseField
    private Long id;
    @DatabaseField
    private Long user_id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String thumbnail;
    @DatabaseField
    private String description;
    @DatabaseField
    private String sound_path;
    @DatabaseField
    private int duration;
    @DatabaseField
    private int played;
    @DatabaseField
    private String created_at;
    @DatabaseField
    private String updated_at;
    @DatabaseField
    private int viewed;
    @DatabaseField
    private String username;
    @DatabaseField
    private int likes;
    @DatabaseField
    private int comments;
    @DatabaseField
    private String display_name;
    @DatabaseField
    private String avatar;

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

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
        values.put(HomeModelContract.ID, id);
        values.put(HomeModelContract.USER_ID, user_id);
        values.put(HomeModelContract.TITLE, title);
        values.put(HomeModelContract.THUMBNAIL, thumbnail);
        values.put(HomeModelContract.DESCRIPTION, description);
        values.put(HomeModelContract.SOUND_PATH, sound_path);
        values.put(HomeModelContract.DURATION, duration);
        values.put(HomeModelContract.PLAYED, played);
        values.put(HomeModelContract.CREATED_AT, created_at);
        values.put(HomeModelContract.UPDATED_AT, updated_at);
        values.put(HomeModelContract.LIKES, likes);
        values.put(HomeModelContract.VIEWED, viewed);
        values.put(HomeModelContract.COMMENTS, comments);
        values.put(HomeModelContract.USERNAME, username);
        values.put(HomeModelContract.DISPLAY_NAME, display_name);
        values.put(HomeModelContract.AVATAR, avatar);
        return values;
    }

    // Create a object from a cursor
    public static HomeModel fromCursor(Cursor curHomeFeeds)
    {
        Long id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(HomeModelContract.ID));
        Long user_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(HomeModelContract.USER_ID));
        String title = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.TITLE));
        String thumbnail = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.THUMBNAIL));
        String description = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.DESCRIPTION));
        String sound_path = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.SOUND_PATH));
        int duration = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(HomeModelContract.DURATION));
        int played = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(HomeModelContract.PLAYED));
        String created_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.CREATED_AT));
        String updated_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.UPDATED_AT));
        int likes = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(HomeModelContract.LIKES));
        int viewed = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(HomeModelContract.VIEWED));
        int comment = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(HomeModelContract.COMMENTS));
        String userName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.USERNAME));
        String displayName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.DISPLAY_NAME));
        String avatar = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(HomeModelContract.AVATAR));

        return new HomeModel(id, user_id, title, thumbnail, description, sound_path, duration, played, created_at,
                updated_at, viewed, userName, likes, comment, displayName, avatar);
    }
}
