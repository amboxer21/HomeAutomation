package com.light.automation;

import java.util.List;
import java.util.Arrays;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import android.util.Log;
import java.io.IOException;
import android.app.Activity;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import android.widget.Toast;
import android.widget.Button;
import android.widget.ToggleButton;

import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ServiceConnection;
import android.content.BroadcastReceiver;

public class LightAutomation extends Activity {

  private static ToggleButton toggle;

  private static String ip;

  private boolean mBound;
  private boolean lightsOn = false;

  private Messenger mService = null;
  private static Message mIP;
  public  static final int MSG_STRING = 0;

  public  static Client client; 
  public  static DatabaseHandler db;
  private static SendPostRequest sendPostRequest;

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  public void onResume() {
    super.onResume();
    if(db.getLightStateHelper().equals("1")) {
      toggle.setChecked(true);
    }
    else {
      toggle.setChecked(false);
    }
  }  

	@Override
  public void onCreate(Bundle savedInstanceState) {

  	super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    bindService(new Intent(this, Server.class), mConnection,
      Context.BIND_AUTO_CREATE);

    LightReceiver lightReceiver = new LightReceiver();
    registerReceiver(lightReceiver, new IntentFilter("toggle"));

    Intent serviceIntent = new Intent(this, Server.class);
    startService(serviceIntent);

    toggle = (ToggleButton)findViewById(R.id.toggle);
    db     = new DatabaseHandler(this);

    Toast.makeText(getApplicationContext(), getIPAddress(), Toast.LENGTH_LONG).show();    

    if(db.getLightStateCount() == 0) {
			Log.d("light_state onCreate()", "db.getLightStateCount() == 0");
      db.addLightState(new LightState(1, 0, getIPAddress()));
    }
    if(db.getLightStateCount() > 0 && db.getLightStateHelper().equals("1")) {
			Log.d("light_state onCreate()", "db.getLightStateCount() > 0 && db.getLightStateHelper().equals(1)");
      toggle.setChecked(true);
    }
    
    try {
      mIP = Message.obtain(null, Server.MSG_STRING, getIPAddress());
      mService.send(mIP);
    }
    catch(Exception e) {
      Log.e("light_state", "Exception(1) => " + e.toString());
      e.printStackTrace();
    }

    toggle.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View view) {
		    try {
          if(db.getLightStateHelper().equals("0")) {
            //Process process = Runtime.getRuntime().exec(new String[] { "su", "-c", "/data/local/client 192.168.1.177 9486 off"});
            /*client = new Client("192.168.1.177", 9486, "onn"); 
            client.sendDataWithString();*/
            //Toast.makeText(getApplicationContext(), "" + db.getLightStateHelper(), Toast.LENGTH_LONG).show();
						Log.d("light_state", "db.getLightStateHelper().equals(0)");
            toggle.setChecked(true);
            sendPostRequest.post(true, getIPAddress(), getApplicationContext());
            mIP = Message.obtain(null, Server.MSG_STRING, getIPAddress());
            mService.send(mIP);
            db.updateLightState(new LightState(1, 1, getIPAddress()));
            db.close();
          } 
          else if(db.getLightStateHelper().equals("1")) {
            //Process process = Runtime.getRuntime().exec(new String[] { "su", "-c", "/data/local/client 192.168.1.177 9486 on"});
            /*client = new Client("192.168.1.177", 9486, "off"); 
            client.sendDataWithString();*/
            //Toast.makeText(getApplicationContext(), "" + db.getLightStateHelper(), Toast.LENGTH_LONG).show();
						Log.d("light_state", "db.getLightStateHelper().equals(1)");
            toggle.setChecked(false);
            sendPostRequest.post(false, getIPAddress(), getApplicationContext());
            mIP = Message.obtain(null, Server.MSG_STRING, getIPAddress());
            mService.send(mIP);
            db.updateLightState(new LightState(1, 0, getIPAddress()));
            db.close();
          }
        }
		    catch(Exception e) {
          Log.e("light_state", "Exception(2) => " + e.toString());
			    e.printStackTrace();
		    }
      }

    });

  }

  private ServiceConnection mConnection = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mService = null;
      mBound   = false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      mBound   = true;
    }
  };

  public String getIPAddress() {
    WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
    ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    return ip;
  }

  public void updateButton(String state) {

    toggle = (ToggleButton)findViewById(R.id.toggle);

    if(state.equals("onn")) {
      toggle.setChecked(true);
    }
    else {
      toggle.setChecked(false);
    }
  }

  private class LightReceiver extends BroadcastReceiver {
 
    @Override
    public void onReceive(Context context, Intent intent) {

      String data = intent.getStringExtra("button");
      Log.d("light_state onRecieve()","data: " + data);
      if(data != null) {
        if(data.equals("onn")) {
          updateButton("onn");
        }
        else if(data.equals("off")) {
          updateButton("off");
        }
      }
    }
  }
  
}
