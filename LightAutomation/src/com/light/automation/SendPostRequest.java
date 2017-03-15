package com.light.automation;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;

public class SendPostRequest {

  public static void post(boolean state, String ip, Context context) {
    try {
      Map<String, String> choice = new HashMap<String, String>();
      if(state) {
        choice.put("state", "1");
        choice.put("ip", ip);
      }
      else {
        choice.put("state", "0");
        choice.put("ip", ip);
      }
      String json = new GsonBuilder().create().toJson(choice, Map.class);
      makeRequest("http://192.168.1.9:3000", json, context);
    }
    catch(Exception e) {
      e.printStackTrace();
      Log.d("light_state","" + e.toString());
    }
  }

  public static HttpResponse makeRequest(String uri, String json, Context context) {
    try {
      HttpPost httpPost = new HttpPost(uri);
      httpPost.setEntity(new StringEntity(json));
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");
      Log.d("light_state","HttpResponse");
      return new DefaultHttpClient().execute(httpPost);
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      Log.d("light_state","" + e.toString());
    }
    catch (ClientProtocolException e) {
      e.printStackTrace();
      Log.d("light_state","" + e.toString());
    }
    catch(org.apache.http.conn.HttpHostConnectException e) {
      e.printStackTrace();
      Log.d("light_state","" + e.toString());
      Toast.makeText(context, "Cannot connect to server!", Toast.LENGTH_LONG).show();
      return null;
    }
    catch (IOException e) {
      e.printStackTrace();
      Log.d("light_state","" + e.toString());
    }

  return null;
  }

}
