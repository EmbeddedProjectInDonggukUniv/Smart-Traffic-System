#include <EEPROM.h>
#include <MsTimer2.h>

//Crashed Car

#define Node_ID 91// 아두이노 ID 강성지(42) 고성욱(19) 진대한(46) 윤지현(72) 이준희 (91) 
#define Server_ID 248 // SM5 ID
#define Port_Num 7777 // 통신하기 위한 포트 넘버

int touchSensor = 12;  // 터치센서 핀 설정
int ledPin = 13;       // LED 핀 설정
int buzzer = 40;       // 부저 핀 설정
int pin_Trig = 2;
int pin_Echo = 3;

const int MOTOR_A_PINS[3]= {42, 44, 4};
const int MOTOR_B_PINS[3] = {46, 48, 5};

int motor_speed = 250;
int crashed = 0;
int nearby_crashed = 0;
uint16_t output;
 
uint8_t ID = 0;
uint32_t timer_check = 0;
 
uint8_t RX_flag = 0, TX_flag = 0, Timer_flag = 0; 
// RX_flag 데이터를 받을 수 있는지 여부
// TX는 데이터를 보낼 수 있는지 여부
 
uint8_t EEPROM_buf[2] = {0xAA, 0};
 
char RX_buf[17];
 
uint8_t TX_buf[17] = {0xA0, 0x0A, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0A, 0xA0};

int RX_count = 0;
 
void setup() {
  static int RX_count = 0;
  static char Check_buf[3] = {0, 0, 0};
  
  pinMode(pin_Trig,OUTPUT);
  pinMode(pin_Echo,INPUT);
  pinMode(buzzer, OUTPUT); // 피에조 부저
  
  Serial.begin(115200);
  Serial1.begin(9600);
  delay(50);
  
  timer_check = millis();
  
  Serial.println("Start Wifi Setting");
  
  Serial1.print("AT\r\n");
  delay(10);
  
  Serial1.print("AT+WAUTO=0,Jin\r\n"); 
  delay(10);
  
  Serial1.print("AT+NDHCP=0\r\n");
  delay(10);
 
  Serial1.print("AT+NAUTO=0,1,192.168.43.");
  Serial1.print(Server_ID);
  Serial1.print(",");
  Serial1.print(Port_Num);
  Serial1.print("\r\n");
  delay(10);
  
  Serial1.print("AT+NSET=192.168.43.");
  Serial1.print(Node_ID);
  Serial1.print(",255.255.255.0,192.168.43.1\r\n");
  delay(10);
  
  Serial1.print("AT&W0\r\n");
  delay(10);
  
  Serial1.print("ATC0\r\n");
  delay(10);
  Serial1.print("ATA\r\n");
  delay(10);
  
  Serial.println("Wifi Setting Finish");
  MsTimer2::set(200, TIMER_ISR);
  MsTimer2::start();
}
 
void loop() {
  uint16_t i;
  
  pinMode(ledPin, OUTPUT);  
  pinMode(touchSensor, INPUT);

  setForward();
  runMotor(motor_speed);
  
  int touchValue = digitalRead(touchSensor);

  if (touchValue == LOW || crashed == 1 || nearby_crashed == 1){ // 충돌 발생 시
    crashed = 1;
    motor_speed = 0;
    digitalWrite(ledPin, HIGH);// LED 켜짐
    digitalWrite(buzzer,HIGH);
    delay(100);
    digitalWrite(buzzer, LOW);
    delay(100);
  } else {                     // 터치 안됨
    digitalWrite(ledPin,LOW);   // LED 꺼짐
  }
  
  if(((timer_check+4000) < millis()) && (RX_flag == 0)) {
    if(RX_count < 1) {
      if(EEPROM.read(0) == 0xAA) {
        
        ID = EEPROM.read(1);
        RX_flag = 1;
        
        Serial.print("\n\rID : ");
        Serial.println(ID);
        TX_buf[3] = ID;
      } else {
        RX_flag = 2;
        
        Serial.println("\n\rWifi Connected Error");
        Serial.print("\n\rPlease reset the ADK-2560"); 
      }
    }
  }  
  if(Timer_flag && TX_flag) {

    TX_buf[4] = 1; // data[4]
    TX_buf[6] = crashed; // data[6]
    TX_buf[7] = 0; // data[7]
    TX_buf[8] = 8; // data[8]
    TX_buf[14] = TX_buf[2];
    
    for(i = 3; i < 14; i++) {
      TX_buf[14] += TX_buf[i];
    }
    
    Serial.println("\n\rTX Packet data");
    
    for(i = 0; i < 17; i++) {
      Serial.write(' ');
      Serial.print(TX_buf[i], HEX);
      Serial1.write(TX_buf[i]);
    }
    
    Timer_flag = 0;
  }
}
 
void serialEvent1(void) {
  static char Check_buf[4] = {0, 0, 0, };
  uint8_t i,check_sum = 0, RX_cnt = 0;
  if(RX_flag == 0) {
    char da = Serial1.read();
    Serial.write(da);
    
    Check_buf[0] = Check_buf[1];
    Check_buf[1] = Check_buf[2];
    Check_buf[2] = da;
    
    if((Check_buf[0] == 'A') && (Check_buf[1] == 'T') && (Check_buf[2] == 'A') && (RX_count == 0)) {
      RX_count = 1;
    } else if(RX_count == 4) {
      if(Check_buf[2] != ':') {
        ID = ID*10 + (Check_buf[2]-'0');
      } else {
        RX_count++;
      }
    } else if(RX_count == 5) {
      if(Check_buf[2] == ']') {
        if((Check_buf[0] == 'O') && (Check_buf[1] == 'K')) {
          RX_flag = 1;
          delay(1000);
          
          RX_cnt = Serial1.available();
          Serial.println(RX_cnt);
          
          while(1) {
            Serial.write(Serial1.read());
            RX_cnt--;
            
            if(RX_cnt == 0)
              break;
          }
    
          Serial.print("\n\rID : ");
          Serial.print(ID);
          
          EEPROM_buf[1] = ID;
          
          if(EEPROM.read(1) != ID) {
            for(i=0; i<2; i++) {
              EEPROM.write(i,EEPROM_buf[i]);
            }
          }
          
          TX_buf[3] = ID;
        } else {
          RX_flag = 2;
          
          Serial.print("\n\rWifi Connected Error");
          Serial.print("\n\rPlease reset the ADK-2560"); 
        }
      }
    } else if((Check_buf[2] == '.') && ((RX_count == 1) || (RX_count == 2) || (RX_count == 3))) {
      RX_count++;
    } 
    else if(RX_count == 1) {
      if(Check_buf[2] == ']') {
        if((Check_buf[0] == 'O') && (Check_buf[1] == 'R')) {
          RX_flag = 2;
          
          Serial.print("\n\rWifi Connected fail");
          Serial.print("\n\rPlease reset the ADK-2560"); 
        }
      }
    }
    
  } else if(RX_flag == 1) {
    if(Serial1.available() > 16) {
      Serial1.readBytes(RX_buf, 17);
      
      if(((uint8_t)RX_buf[0] == 0xA0) && ((uint8_t)RX_buf[1] == 0x0A)) {
        for(i=2; i<14; i++) {
          check_sum += (uint8_t)RX_buf[i];
        }
        
        if(check_sum == (uint8_t)RX_buf[14]) {
          Serial.println("\n\rRX Packet data");
          
          for(i=0; i<17; i++) {
            Serial.write(' ');
            Serial.print((uint8_t)RX_buf[i],HEX);
          }

          if((int)RX_buf[10] != 0) {// When car crash occurred
            nearby_crashed=1;
           }else{                      
            nearby_crashed=0;
           }
           
          TX_flag = RX_buf[4];
          
          if(!TX_flag) {
            for(i=4; i<14; i++) {
              TX_buf[i] = 0;
            }
            
            TX_buf[14] = TX_buf[2];
            
            TX_buf[4] = 1; // data[4]
            TX_buf[6] = crashed; // data[6]
            TX_buf[7] = 0; // data[7]
            TX_buf[8] = 8; // data[8]
            TX_buf[14] = TX_buf[2];
            
            for(i=3; i<14; i++) {
              TX_buf[14] += TX_buf[i];
            }
            
            Serial.println("\n\rTX Packet data");
            
            for(i=0; i<17; i++) {
              Serial.write(' ');
              Serial.print(TX_buf[i],HEX);
              Serial1.write(TX_buf[i]);
            }
          }
        }
      }
    }
  }
}
 
void serialEvent(void) {
  int i=0;
  Serial1.write(Serial.read());
}
void TIMER_ISR(void) {
  Timer_flag = 1;
}

long microsecondsToCentimeters(long microseconds)
{
  // The speed of sound is 340 m/s or 29 microseconds per centimeter.
  // The ping travels out and back, so to find the distance of the
  // object we take half of the distance travelled.
  return microseconds / 29 / 2;
}

void setForward() {
  digitalWrite(MOTOR_A_PINS[0], 1);
  digitalWrite(MOTOR_B_PINS[0], 1);
  digitalWrite(MOTOR_A_PINS[1], 0);
  digitalWrite(MOTOR_B_PINS[1], 0);
}

void setBackward() {
  digitalWrite(MOTOR_A_PINS[0], 0);
  digitalWrite(MOTOR_B_PINS[0], 0);
  digitalWrite(MOTOR_A_PINS[1], 1);
  digitalWrite(MOTOR_B_PINS[1], 1);
}

void runMotor(unsigned char speed) { // [0, 255]
  analogWrite(MOTOR_A_PINS[2], speed);
  analogWrite(MOTOR_B_PINS[2], speed);
}
