package com.qsoft.onlinedio.restfullservice;

import com.qsoft.onlinedio.database.entity.HomeModel;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * User: khiemvx
 * Date: 11/13/13
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeFeedContainer
{
    @JsonProperty("code")
    public String code;

    @JsonProperty("status")
    public String status;

    @JsonProperty("data")
    public ArrayList<HomeModel> data;

    public HomeFeedContainer()
    {
    }

    public ArrayList<HomeModel> getData()
    {
        return data;
    }

    public void setData(ArrayList<HomeModel> data)
    {
        this.data = data;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
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
}