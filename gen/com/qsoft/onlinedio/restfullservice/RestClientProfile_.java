//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.restfullservice;

import java.util.Collections;
import java.util.HashMap;
import com.qsoft.onlinedio.database.dto.ProfileDTO;
import com.qsoft.onlinedio.restfullservice.container.ProfileContainer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClientProfile_
    implements RestClientProfile
{

    private RestTemplate restTemplate;
    private String rootUrl;

    public RestClientProfile_() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        rootUrl = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/";
    }

    @Override
    public ProfileContainer getProfiles(String userId, String auth) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("userId", userId);
        urlVariables.put("auth", auth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("{userId}?access_token={auth}"), HttpMethod.GET, requestEntity, ProfileContainer.class, urlVariables).getBody();
    }

    @Override
    public void updateProfiles(String auth, String userId, ProfileDTO profile) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("userId", userId);
        urlVariables.put("auth", auth);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ProfileDTO> requestEntity = new HttpEntity<ProfileDTO>(profile, httpHeaders);
        restTemplate.exchange(rootUrl.concat("{userId}?access_token={auth}"), HttpMethod.PUT, requestEntity, null, urlVariables);
    }

}