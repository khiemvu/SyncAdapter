package com.qsoft.onlinedio.restfullservice.container;

import com.qsoft.onlinedio.database.dto.HomeFeedDTO;
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
    public int code;

    @JsonProperty("status")
    public String status;

    @JsonProperty("data")
    public ArrayList<HomeFeedDTO> data;

    public HomeFeedContainer()
    {
    }

    public ArrayList<HomeFeedDTO> getData()
    {
        return data;
    }

    public void setData(ArrayList<HomeFeedDTO> data)
    {
        this.data = data;
    }

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
}