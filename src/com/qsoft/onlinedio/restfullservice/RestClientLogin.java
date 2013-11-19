package com.qsoft.onlinedio.restfullservice;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.onlinedio.restfullservice.container.LoginContainer;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * User: khiemvx
 * Date: 11/15/13
 */
@Rest(rootUrl="http://192.168.1.222/testing/ica467/trunk/public/auth-rest",converters={MappingJacksonHttpMessageConverter.class})
public interface RestClientLogin
{
    @Post("")
    @Accept(MediaType.APPLICATION_JSON)
    LoginContainer login(LoginContainer loginContainer);
}
