package com.qsoft.onlinedio.restfullservice;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Put;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.onlinedio.database.dto.ProfileDTO;
import com.qsoft.onlinedio.restfullservice.container.ProfileContainer;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * User: khiemvx
 * Date: 11/14/13
 */
@Rest(rootUrl="http://192.168.1.222/testing/ica467/trunk/public/user-rest/",converters={MappingJacksonHttpMessageConverter.class})
public interface RestClientProfile
{
    @Get("{userId}?access_token={auth}")
    @Accept(MediaType.APPLICATION_JSON)
    ProfileContainer getProfiles(String userId,String auth);

    @Put("{userId}?access_token={auth}")
//    void updateProfiles(String auth, String userId, ProfileModel profile);
    void updateProfiles(String auth, String userId, ProfileDTO profile);
}
