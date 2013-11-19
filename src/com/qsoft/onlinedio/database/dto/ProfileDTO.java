package com.qsoft.onlinedio.database.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/19/13
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProfileDTO implements Serializable
{

    @JsonProperty
    public String display_name;

    @JsonProperty
    public String full_name;

    @JsonProperty
    public String phone;

    @JsonProperty
    public String birthday;

    @JsonProperty
    public int gender;

    @JsonProperty
    public String country_id;

    @JsonProperty
    public String description;

    public ProfileDTO()
    {
    }

    public ProfileDTO(String display_name, String full_name, String phone, String birthday, int gender, String country_id,
                      String desccription)
    {

        this.display_name = display_name;
        this.full_name = full_name;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country_id = country_id;
        this.description = desccription;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}