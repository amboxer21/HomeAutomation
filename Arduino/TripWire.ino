void setup() {
  //pinMode(ledPin, INPUT);  
  Serial.begin(9600);  
}

void loop() {
  int state = analogRead(0);
  /*if(state < 1000) {
    Serial.print("Laser tripped!");
    Serial.print(analogRead(0));
    Serial.print("\n");
    delay(5000);
  }*/
  Serial.print(analogRead(0));
  Serial.print("\n");
  delay(2000);
}
