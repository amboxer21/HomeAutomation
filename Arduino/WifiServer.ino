#include <SPI.h>
#include <ESP8266WiFi.h>

boolean alreadyConnected = false;

WiFiServer server(9486);

char ssid[]     = "ESSID";
char password[] = "PASSWORD";

void setup() {
  Serial.begin(115200);
  pinMode(16, OUTPUT);

  IPAddress ip(192, 168, 1, 176);
  IPAddress gateway(192, 168, 1, 1);
  IPAddress subnet(255, 255, 255, 0);

  WiFi.config(ip, gateway, subnet);
  WiFi.begin(ssid, password);
  
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  delay(1000);
  server.begin();
  Serial.print("Server started\n");

}

void loop() {
  
  WiFiClient client = server.available();

  if(!alreadyConnected) {
    client.flush();    
    Serial.print("Client connected.\n");
    alreadyConnected = true;
  }

    char buf[256];
    client.readBytes(buf, 3);
            
    if(strncmp(buf, "onn", 3) == 0) {
      Serial.print("Turning light on.\n");
      client.flush();
      digitalWrite(16, HIGH);
      for( int i = 0; i < sizeof(buf);  ++i ) {
        buf[i] = (char)0;
      }
    }
    else if(strncmp(buf, "off", 3) == 0) {
      Serial.print("Turning light off.\n");
      client.flush();
      digitalWrite(16, LOW);
      for( int i = 0; i < sizeof(buf);  ++i ) {
        buf[i] = (char)0;
      }
    }    
    else if(strlen(buf) > 0){
      Serial.print("Not a known command.\n");
      client.flush();
      for( int i = 0; i < sizeof(buf);  ++i ) {
        buf[i] = (char)0;
      }    
    }
}
