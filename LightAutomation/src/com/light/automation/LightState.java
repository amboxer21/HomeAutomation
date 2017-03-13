package com.light.automation; 
 
public class LightState {
     
  int _id;
  int _state;

  String _ip;

  public LightState(){ }
     
  public LightState(int id, int state, String ip){
    this._id    = id;
    this._state = state;
    this._ip    = ip;
  }
     
  public int getState(){
    return this._state;
  }
     
  public void setState(int state){
    this._state = state;
  }

  public int getID(){
    return this._id;
  }

  public void setID(int id){
    this._id = id;
  }

  public String getIP(){
    return this._ip;
  }

  public void setIP(String ip){
    this._ip = ip;
  }

}
