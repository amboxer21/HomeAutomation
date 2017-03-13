package com.light.automation;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.util.Log;

public class Client { 

  public static int dPort;

	public static String dAddr;
  public static String lState;

	public static Socket socket;
  public static OutputStream out;
	public static PrintWriter pWriter; 
 
  public Client(String addr, int port, String state) {
    lState = state;
    dPort  = port;
    dAddr  = addr;
  }

  public void sendDataWithString() {

    try {
      socket  = new Socket(dAddr, dPort);

      pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      pWriter.write(lState);

      pWriter.flush();
      pWriter.close();
      Log.d("light_state", "pWriter: " + pWriter);
    }
    catch(IOException e) {
      Log.d("light_state", "sendDataWithString() " + e.toString());
      e.printStackTrace();
    }

  }

}
