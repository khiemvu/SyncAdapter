package com.qsoft.onlinedio.authenticate;

import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.qsoft.onlinedio.util.Encripiton.encryption;

/**
 * User: khiemvx
 * Date: 10/29/13
 */
public class ParseToServerAuthenticate implements ServerAuthenticate
{
    private final String TAG = "ParseToServerAuthenticate";
    private final static String APP_ID = "iRnc8I1X0du5q6HrJtZW0a5DlB0JcpOQbjA6chha";
    private final static String REST_API_KEY = "tv1xCdYKTwI3p205KHCn1yWpbVj2OHldV9cPZuNZ";

    public static List<Header> getAppParseComHeaders() {
        List<Header> ret = new ArrayList<Header>();
        ret.add(new BasicHeader("X-Parse-Application-Id", APP_ID));
        ret.add(new BasicHeader("X-Parse-REST-API-Key", REST_API_KEY));
        return ret;
    }

    @Override
    public String userSignUp(String name, String email, String pass, String authType) throws Exception
    {

        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("X-Parse-Application-Id", "XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
        httpPost.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");
        httpPost.addHeader("Content-Type", "application/json");

        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        HttpEntity entity = new StringEntity(user);
        httpPost.setEntity(entity);

        String authtoken = null;
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 201)
            {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user[" + error.code + "] - " + error.error);
            }


            User createdUser = new Gson().fromJson(responseString, User.class);

            authtoken = createdUser.getAccess_token();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return authtoken;
    }

    @Override
    public User userSignIn(String user, String pass, String authType) throws Exception
    {
        String _pass = encryption(pass);
        Log.d("udini", "userSignIn");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        URL urlReal = new URL(url);

        JSONObject jo = new JSONObject();
        jo.put("username", user);
        jo.put("password", _pass);
        jo.put("grant_type", "password");
        jo.put("client_id", "123456789");


        HttpPost httpPost = new HttpPost(urlReal.toURI());
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));

        //Execute Post
        String responseString = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
        Log.d(TAG, responseString);
        User loggedUser = new Gson().fromJson(responseString, User.class);
        Log.d(TAG, loggedUser + "");

        return loggedUser;
    }


    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }


}
