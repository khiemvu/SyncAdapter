//package com.qsoft.onlinedio.syncadapter;
//
//import android.util.Log;
//import com.google.gson.Gson;
//import com.qsoft.onlinedio.authenticate.ParseToServerAuthenticate;
//import com.qsoft.onlinedio.database.entity.HomeModel;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.net.HttpURLConnection;
//import java.util.ArrayList;
//
///**
//* User: khiemvx
//* Date: 10/31/13
//*/
//public class HomeFeedParseToServer
//{
//    private String TAG = this.getClass().getSimpleName();
//    public ArrayList<HomeModel> getShows(String auth) throws Exception {
//
//        Log.d(TAG, "getShows auth[" + auth + "]");
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest?access_token="+auth;
//
//        HttpGet httpGet = new HttpGet(url);
//
//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//
//            String responseString = EntityUtils.toString(response.getEntity());
//            Log.d(TAG, "getShows> Response= " + responseString);
//            if(responseString.equals("cannot access my apis")){
//                Log.i(TAG, "auth_token is invalid");
//            }
//            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
//                ParseToServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseToServerAuthenticate.ParseComError.class);
//                throw new Exception("Error retrieving home feeds ["+error.code+"] - " + error.error);
//            }
//
//            HomeModels homeModels = new Gson().fromJson(responseString, HomeModels.class);
//            return homeModels.data;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<HomeModel>();
//    }
//
//
//    private class HomeModels implements Serializable
//    {
//        int code;
//        String status;
//        ArrayList<HomeModel> data;
//    }
//}