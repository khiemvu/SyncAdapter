package com.qsoft.onlinedio.restfullservice;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.onlinedio.restfullservice.container.HomeFeedContainer;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * User: khiemvx
 * Date: 11/13/13
 */
@Rest(rootUrl="http://192.168.1.222/testing/ica467/trunk/public/home-rest?access_token=",converters={MappingJacksonHttpMessageConverter.class})
public interface RestClientHomeFeed
{
    @Get("{auth}")
    @Accept(MediaType.APPLICATION_JSON)
    HomeFeedContainer getHomeFeeds(String auth);
}
