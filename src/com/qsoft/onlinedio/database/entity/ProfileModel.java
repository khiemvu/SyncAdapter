package com.qsoft.onlinedio.database.entity;

import android.content.ContentValues;
import android.database.Cursor;
import com.qsoft.onlinedio.database.DbHelper;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/5/13
 */
public class ProfileModel implements Serializable
{
    private int id;
    private int facebook_id;
    private String username;
    private String password;
    private String avartar;
    private String cover_image;
    private String display_name;
    private String full_name;
    private String phone;
    private String birthday;
    private int gender;
    private String country_id;
    private int storage_plan_id;
    private String desccription;
    private String created_at;
    private String updated_at;
    private int sounds;
    private int favorites;
    private int likes;
    private int followings;
    private int audiences;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getFacebook_id()
    {
        return facebook_id;
    }

    public void setFacebook_id(int facebook_id)
    {
        this.facebook_id = facebook_id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvartar()
    {
        return avartar;
    }

    public void setAvartar(String avartar)
    {
        this.avartar = avartar;
    }

    public String getCover_image()
    {
        return cover_image;
    }

    public void setCover_image(String cover_image)
    {
        this.cover_image = cover_image;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getCountry_id()
    {
        return country_id;
    }

    public void setCountry_id(String country_id)
    {
        this.country_id = country_id;
    }

    public int getStorage_plan_id()
    {
        return storage_plan_id;
    }

    public void setStorage_plan_id(int storage_plan_id)
    {
        this.storage_plan_id = storage_plan_id;
    }

    public String getDesccription()
    {
        return desccription;
    }

    public void setDesccription(String desccription)
    {
        this.desccription = desccription;
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

    public int getSounds()
    {
        return sounds;
    }

    public void setSounds(int sounds)
    {
        this.sounds = sounds;
    }

    public int getFavorites()
    {
        return favorites;
    }

    public void setFavorites(int favorites)
    {
        this.favorites = favorites;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getFollowings()
    {
        return followings;
    }

    public void setFollowings(int followings)
    {
        this.followings = followings;
    }

    public int getAudiences()
    {
        return audiences;
    }

    public void setAudiences(int audiences)
    {
        this.audiences = audiences;
    }

    public ProfileModel()
    {
    }

    public ProfileModel(int id, int facebook_id, String username, String password, String avartar, String cover_image,
                        String display_name, String full_name, String phone, String birthday, int gender, String country_id,
                        int storage_plan_id, String desccription, String created_at, String updated_at,
                        int sounds, int favorites, int likes, int followings, int audiences)
    {
        this.id = id;
        this.facebook_id = facebook_id;
        this.username = username;
        this.password = password;
        this.avartar = avartar;
        this.cover_image = cover_image;
        this.display_name = display_name;
        this.full_name = full_name;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country_id = country_id;
        this.storage_plan_id = storage_plan_id;
        this.desccription = desccription;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.sounds = sounds;
        this.favorites = favorites;
        this.likes = likes;
        this.followings = followings;
        this.audiences = audiences;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(DbHelper.PROFILE_COL_ID, id);
        values.put(DbHelper.PROFILE_COL_FACEBOOK_ID, facebook_id);
        values.put(DbHelper.PROFILE_COL_USERNAME, username);
        values.put(DbHelper.PROFILE_COL_PASSWORD, password);
        values.put(DbHelper.PROFILE_COL_AVATAR, avartar);
        values.put(DbHelper.PROFILE_COL_COVER_IMAGE, cover_image);
        values.put(DbHelper.PROFILE_COL_DISPLAY_NAME, display_name);
        values.put(DbHelper.PROFILE_COL_FULL_NAME, full_name);
        values.put(DbHelper.PROFILE_COL_PHONE, phone);
        values.put(DbHelper.PROFILE_COL_BIRTHDAY, birthday);
        values.put(DbHelper.PROFILE_COL_GENDER, gender);
        values.put(DbHelper.PROFILE_COL_COUNTRY_ID, country_id);
        values.put(DbHelper.PROFILE_COL_STORAGE_PLAN_ID, storage_plan_id);
        values.put(DbHelper.PROFILE_COL_DESCRIPTION, desccription);
        values.put(DbHelper.PROFILE_COL_CREATED_AT, created_at);
        values.put(DbHelper.PROFILE_COL_UPDATED_AT, updated_at);
        values.put(DbHelper.PROFILE_COL_SOUND, sounds);
        values.put(DbHelper.PROFILE_COL_FAVORITE, favorites);
        values.put(DbHelper.PROFILE_COL_LIKE, likes);
        values.put(DbHelper.PROFILE_COL_FOLLOWING, followings);
        values.put(DbHelper.PROFILE_COL_AUDIENCE, audiences);
        return values;
    }

    // Create a object from a cursor
    public static ProfileModel fromCursor(Cursor cur)
    {
        int id = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_ID));
        int facebook_id = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_FACEBOOK_ID));
        String username = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_USERNAME));
        String password = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_PASSWORD));
        String avatar = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_AVATAR));
        String cover_image = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_COVER_IMAGE));
        String display_name = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_DISPLAY_NAME));
        String full_name = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_FULL_NAME));
        String phone = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_PHONE));
        String birthday = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_BIRTHDAY));
        int gender = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_GENDER));
        String country_id = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_COUNTRY_ID));
        int storage_plan_id = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_STORAGE_PLAN_ID));
        String description = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_DESCRIPTION));
        String created_at = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_CREATED_AT));
        String updated_at = cur.getString(cur.getColumnIndex(DbHelper.PROFILE_COL_UPDATED_AT));
        int sounds = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_SOUND));
        int favorites = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_FAVORITE));
        int likes = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_LIKE));
        int followings = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_FOLLOWING));
        int audiences = cur.getInt(cur.getColumnIndex(DbHelper.PROFILE_COL_AUDIENCE));
        return new ProfileModel(id, facebook_id, username, password, avatar, cover_image, display_name, full_name,
                phone, birthday, gender, country_id, storage_plan_id, description, created_at, updated_at,
                sounds, favorites, likes, followings, audiences);
    }
}
