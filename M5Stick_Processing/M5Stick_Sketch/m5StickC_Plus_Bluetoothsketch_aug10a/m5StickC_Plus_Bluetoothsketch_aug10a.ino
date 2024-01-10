#include <M5StickCPlus.h>
#include "BluetoothSerial.h"

BluetoothSerial bts;

float accX = 0.0f;
float accY = 0.0f;
float accZ = 0.0f;
float x = 120.0f;
float v = 0.0f;

void setup() {
  M5.begin();
  M5.IMU.Init();
  M5.Lcd.setRotation(1);
  bts.begin("M5StickC");
}

void loop() {
  M5.update();
  M5.IMU.getAccelData(&accX, &accY, &accZ);
  v += accY;
  x += v;
  if(x>=230.0) { v=0.0; x=230.0; }
  if(x<=10.0)  { v=0.0; x=10.0; }
  M5.Lcd.fillScreen(BLACK);
  M5.Lcd.fillCircle((int)x, 68, 10, YELLOW);
  bts.println((int)x);
  // Serial.println((int)x);
  delay(20);
}