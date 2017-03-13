#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {0xDE,0xAD,0xBE,0xEF,0xFE,0xED};

IPAddress ip(192,168,1,177);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

EthernetServer server(9486);

void setup() {  
  Ethernet.begin(mac, ip, gateway, subnet);
  Serial.begin(9600);
  pinMode(8, OUTPUT);
}

void loop() {
  
  EthernetClient client = server.available();
    if (client.available() > 0) {
      
      char buf[256];
      client.readBytes(buf,3);
      
      if(strncmp(buf, "on", 2) == 0) {
        Serial.print("Turning light on.");
        server.write("Turning light on.\n");
        client.flush();
        digitalWrite(8, HIGH);
      }
      else if (strncmp(buf, "off", 3) == 0) {
        Serial.print("Turning light off.");
        server.write("Turning light off.\n");
        client.flush();
        digitalWrite(8, HIGH);
      }
      else {
        Serial.print("Not a known command.");
        server.write("Not a known command.\n");
        client.flush();
      }
    }
}
