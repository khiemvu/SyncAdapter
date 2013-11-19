package com.qsoft.onlinedio.syncadapter;

import android.util.Log;
import com.google.gson.Gson;
import com.qsoft.onlinedio.authenticate.ParseToServerAuthenticate;
import com.qsoft.onlinedio.database.entity.ProfileModel;
import com.qsoft.onlinedio.database.entity.ProfileModelContract;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
* User: khiemvx
* Date: 11/5/13
*/
public class ProfileParseToServer
{
    String TAG = "ProfileParseToServer";

    public ProfileModel getShows(String userId, String auth) throws Exception
    {

        Log.d(TAG, "getShows auth[" + auth + "]");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/" + userId + "?access_token=" + auth;

        HttpGet httpGet = new HttpGet(url);

        try
        {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            Log.d(TAG, "getShows> Response= " + responseString);
            if (responseString.equals("cannot access my apis"))
            {
                // Todo refresh token
                Log.i(TAG,"Token is invalid");
            }
            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK)
            {
                ParseToServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseToServerAuthenticate.ParseComError.class);
                throw new Exception("Error retrieving profile [" + error.code + "] - " + error.error);
            }

            ProfileModels profileModels = new Gson().fromJson(responseString, ProfileModels.class);
            return profileModels.data;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new ProfileModel();
    }

    public void putShow(String authtoken, String userId, ProfileModel profile) throws Exception
    {
        Log.d(TAG, "putShow [" + profile.getDisplay_name() + "]");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/" + userId + "?access_token=" + authtoken;

        HttpPut httpPut = new HttpPut(new URI(url));
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair(ProfileModelContract.DISPLAY_NAME, profile.getDisplay_name()));
        pairs.add(new BasicNameValuePair(ProfileModelContract.FULL_NAME, profile.getFull_name()));
        pairs.add(new BasicNameValuePair(ProfileModelContract.PHONE, profile.getPhone()));
        pairs.add(new BasicNameValuePair(ProfileModelContract.BIRTHDAY, profile.getBirthday()));
        pairs.add(new BasicNameValuePair(ProfileModelContract.GENDER, profile.getGender()+""));
        pairs.add(new BasicNameValuePair(ProfileModelContract.COUNTRY_ID, profile.getCountry_id()));
        pairs.add(new BasicNameValuePair(ProfileModelContract.DESCRIPTION, profile.getDescription()));

        httpPut.setEntity(new UrlEncodedFormEntity(pairs));
        try
        {
            HttpResponse response = httpClient.execute(httpPut);
            String responseString = EntityUtils.toString(response.getEntity());
            Log.d(TAG, "Response string = " + responseString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class ProfileModels implements Serializable
    {
        int code;
        String status;
        ProfileModel data;
    }

    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }
}