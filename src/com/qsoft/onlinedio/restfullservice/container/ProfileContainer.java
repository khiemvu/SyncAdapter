package com.qsoft.onlinedio.restfullservice.container;

import com.qsoft.onlinedio.database.entity.ProfileModel;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * User: khiemvx
 * Date: 11/14/13
 */
public class ProfileContainer implements Serializable
{
    @JsonProperty("code")
    public int code;

    @JsonProperty("status")
    public String status;

    @JsonProperty("data")
    public ProfileModel data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public ProfileModel getData()
    {
        return data;
    }

    public void setData(ProfileModel data)
    {
        this.data = data;
    }
}
