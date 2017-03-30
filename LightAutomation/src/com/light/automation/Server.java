package com.light.automation;
 
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;

import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import android.app.Service;
import android.app.Activity;

import android.os.Bundle;
import android.os.Message;
import android.os.IBinder;
import android.os.Handler;
import android.os.Messenger;

import android.content.Intent;
import android.content.Context;

import android.util.Log;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
 
public class Server extends Service {

  String response = "";
	public  static String mIP = "";
  public  static DatabaseHandler db;
	public  static final int MSG_STRING = 0;
  private static ServerSocket serverSocket; 
 
  private static Intent intent;
  private static int bytesRead;
  byte[] buffer = new byte[1024];
  public  static Handler handler = new Handler();
  public  static LightAutomation lightAutomation = new LightAutomation();

  final Messenger mMessenger = new Messenger(new IncomingHandler());

  class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 0:
         	mIP = msg.obj.toString(); 
        break;
        default:
          super.handleMessage(msg);
      }
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
  }

	@Override
	public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
	}

  @Override
  public int onStartCommand(Intent intent, int flag, int startId) {

		startListenForTCP(intent);
		Log.d("serverService", "Service started");
    return START_STICKY;
  }


	public void startListenForTCP(Intent intent) {

    Thread TCPListenerThread = new Thread(new Runnable() {
      @Override
      public void run() {

        BufferedReader in;
        Boolean run = true;
        String serverMessage = null;

        try {
          serverSocket = new ServerSocket(8080);
          serverSocket.setReuseAddress(true);
          db = new DatabaseHandler(getApplicationContext());		
          while(true) {
            Socket client = serverSocket.accept();
            try{
              in = new BufferedReader(new InputStreamReader(client.getInputStream()));
              serverMessage = in.readLine();
              if(serverMessage.equals("onn")) {
                Log.i("serverService","onn");
                db.updateLightState(new LightState(1, 1, mIP));
                sendSig("onn");
              }
              else if(serverMessage.equals("off")) {
                Log.i("serverService","off");
                db.updateLightState(new LightState(1, 0, mIP));
                sendSig("off");
              }
              Log.i("serverService","" + serverMessage);
            } 
            catch(UnknownHostException e) {
              Log.e("serverService error","" + e.toString());
            } 
            catch(IOException e) {
              Log.e("serverService error","" + e.toString());
            } 
            finally {
              db.close();
              client.close();
            }
          }
        } 
        catch (IOException e) {
          Log.e("serverService error","" + e.toString());
          e.printStackTrace();
        }
      }
    });
  
    TCPListenerThread.start();
  }

  public void sendSig(String sig) {
    Log.d("serverService", "sig: " + sig);
    intent = new Intent("toggle");
    intent.putExtra("button", sig);     
    sendBroadcast(intent);
  }

}
