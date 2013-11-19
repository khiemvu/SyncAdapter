package com.qsoft.onlinedio.database.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/19/13
 */

public class HomeFeedDTO implements Serializable
{
    @JsonProperty
    private Long id;
    @JsonProperty
    private Long user_id;
    @JsonProperty
    private String title;
    @JsonProperty
    private String thumbnail;
    @JsonProperty
    private String description;
    @JsonProperty
    private String sound_path;
    @JsonProperty
    private int duration;
    @JsonProperty
    private int played;
    @JsonProperty
    private String created_at;
    @JsonProperty
    private String updated_at;
    @JsonProperty
    private int viewed;
    @JsonProperty
    private String username;
    @JsonProperty
    private int likes;
    @JsonProperty
    private int comments;
    @JsonProperty
    private String display_name;
    @JsonProperty
    private String avatar;


    public HomeFeedDTO(Long id, Long user_id, String title, String thumbnail, String desccription,
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

    public HomeFeedDTO()
    {

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
}