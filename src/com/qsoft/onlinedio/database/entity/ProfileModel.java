package com.qsoft.onlinedio.database.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.qsoft.onlinedio.database.Contract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/18/13
 */

@AdditionalAnnotation.Contract
@DatabaseTable(tableName = "profiles")
@AdditionalAnnotation.DefaultContentUri(authority= Contract.AUTHORITY, path= Contract.PROFILE_CONTENT_URI_PATH)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name= Contract.PROFILE_MIMETYPE_NAME, type= Contract.PROFILE_MIMETYPE_TYPE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProfileModel implements Serializable
{
    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    @AdditionalAnnotation.DefaultSortOrder
    private Long _id;
    @JsonProperty
    @DatabaseField
    public int id;
    @JsonProperty
    @DatabaseField
    public int facebook_id;
    @JsonProperty
    @DatabaseField
    public String username;
    @JsonProperty
    @DatabaseField
    public String password;
    @JsonProperty
    @DatabaseField
    public String avatar;
    @JsonProperty
    @DatabaseField
    public String cover_image;
    @JsonProperty
    @DatabaseField
    public String display_name;
    @JsonProperty
    @DatabaseField
    public String full_name;
    @JsonProperty
    @DatabaseField
    public String phone;
    @JsonProperty
    @DatabaseField
    public String birthday;
    @JsonProperty
    @DatabaseField
    public int gender;
    @JsonProperty
    @DatabaseField
    public String country_id;
    @JsonProperty
    @DatabaseField
    public int storage_plan_id;
    @JsonProperty
    @DatabaseField
    public String description;
    @JsonProperty
    @DatabaseField
    public String created_at;
    @JsonProperty
    @DatabaseField
    public String updated_at;
    @JsonProperty
    @DatabaseField
    public int sounds;
    @JsonProperty
    @DatabaseField
    public int favorites;
    @JsonProperty
    @DatabaseField
    public int likes;
    @JsonProperty
    @DatabaseField
    public int followings;
    @JsonProperty
    @DatabaseField
    public int audiences;

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

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        this.avatar = avartar;
        this.cover_image = cover_image;
        this.display_name = display_name;
        this.full_name = full_name;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country_id = country_id;
        this.storage_plan_id = storage_plan_id;
        this.description = desccription;
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
        values.put(ProfileModelContract.ID, id);
        values.put(ProfileModelContract.FACEBOOK_ID, facebook_id);
        values.put(ProfileModelContract.USERNAME, username);
        values.put(ProfileModelContract.PASSWORD, password);
        values.put(ProfileModelContract.AVATAR, avatar);
        values.put(ProfileModelContract.COVER_IMAGE, cover_image);
        values.put(ProfileModelContract.DISPLAY_NAME, display_name);
        values.put(ProfileModelContract.FULL_NAME, full_name);
        values.put(ProfileModelContract.PHONE, phone);
        values.put(ProfileModelContract.BIRTHDAY, birthday);
        values.put(ProfileModelContract.GENDER, gender);
        values.put(ProfileModelContract.COUNTRY_ID, country_id);
        values.put(ProfileModelContract.STORAGE_PLAN_ID, storage_plan_id);
        values.put(ProfileModelContract.DESCRIPTION, description);
        values.put(ProfileModelContract.CREATED_AT, created_at);
        values.put(ProfileModelContract.UPDATED_AT, updated_at);
        values.put(ProfileModelContract.SOUNDS, sounds);
        values.put(ProfileModelContract.FAVORITES, favorites);
        values.put(ProfileModelContract.LIKES, likes);
        values.put(ProfileModelContract.FOLLOWINGS, followings);
        values.put(ProfileModelContract.AUDIENCES, audiences);
        return values;
    }

    // Create a object from a cursor
    public static ProfileModel fromCursor(Cursor cur)
    {
        int id = cur.getInt(cur.getColumnIndex(ProfileModelContract.ID));
        int facebook_id = cur.getInt(cur.getColumnIndex(ProfileModelContract.FACEBOOK_ID));
        String username = cur.getString(cur.getColumnIndex(ProfileModelContract.USERNAME));
        String password = cur.getString(cur.getColumnIndex(ProfileModelContract.PASSWORD));
        String avatar = cur.getString(cur.getColumnIndex(ProfileModelContract.AVATAR));
        String cover_image = cur.getString(cur.getColumnIndex(ProfileModelContract.COVER_IMAGE));
        String display_name = cur.getString(cur.getColumnIndex(ProfileModelContract.DISPLAY_NAME));
        String full_name = cur.getString(cur.getColumnIndex(ProfileModelContract.FULL_NAME));
        String phone = cur.getString(cur.getColumnIndex(ProfileModelContract.PHONE));
        String birthday = cur.getString(cur.getColumnIndex(ProfileModelContract.BIRTHDAY));
        int gender = cur.getInt(cur.getColumnIndex(ProfileModelContract.GENDER));
        String country_id = cur.getString(cur.getColumnIndex(ProfileModelContract.COUNTRY_ID));
        int storage_plan_id = cur.getInt(cur.getColumnIndex(ProfileModelContract.STORAGE_PLAN_ID));
        String description = cur.getString(cur.getColumnIndex(ProfileModelContract.DESCRIPTION));
        String created_at = cur.getString(cur.getColumnIndex(ProfileModelContract.CREATED_AT));
        String updated_at = cur.getString(cur.getColumnIndex(ProfileModelContract.UPDATED_AT));
        int sounds = cur.getInt(cur.getColumnIndex(ProfileModelContract.SOUNDS));
        int favorites = cur.getInt(cur.getColumnIndex(ProfileModelContract.FAVORITES));
        int likes = cur.getInt(cur.getColumnIndex(ProfileModelContract.LIKES));
        int followings = cur.getInt(cur.getColumnIndex(ProfileModelContract.FOLLOWINGS));
        int audiences = cur.getInt(cur.getColumnIndex(ProfileModelContract.AUDIENCES));
        return new ProfileModel(id, facebook_id, username, password, avatar, cover_image, display_name, full_name,
                phone, birthday, gender, country_id, storage_plan_id, description, created_at, updated_at,
                sounds, favorites, likes, followings, audiences);
    }
}
