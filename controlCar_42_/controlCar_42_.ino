/*
 * 전방의 사물을 인식하여 10cm 이내로 사물이 감지되면 감속되고
 * 3cm 이내로 사물이 감지되면 정지하면서 LED가 점등된다.
 * 사물이 멀어지면 다시 전진한다.
 */
int trigPin = 2;
int echoPin = 3;

int ledPin = 13; // LED 핀 설정

const int MOTOR_A_PINS[3]= {42, 44, 4};
const int MOTOR_B_PINS[3] = {46, 48, 5};

int motor_speed = 250;

void setup()
{
  int i;
  Serial.begin(9600);
  pinMode(trigPin,OUTPUT); // 센서 Trig 핀
  pinMode(echoPin,INPUT); // 센서 Echo 핀

  pinMode(ledPin, OUTPUT); // LED 핀
  
  for (i = 0; i < 3; ++i) {
    pinMode(MOTOR_A_PINS[i], OUTPUT); // 0
    pinMode(MOTOR_B_PINS[i], OUTPUT);
  }
}

void loop(){
  long duration, cm;
  int i;

  digitalWrite(trigPin,HIGH); // 센서에 Trig 신호 입력
  delayMicroseconds(10); // 10us 정도 유지
  digitalWrite(trigPin,LOW); // Trig 신호 off

  duration = pulseIn(echoPin,HIGH); // Echo pin: HIGH->Low 간격을 측정
  cm = duration/29/2; // 거리(cm)로 변환
  Serial.print(cm);
  Serial.print("cm");
  Serial.println();

  
  
  if(cm >= 10){
    setForward();
    runMotor(250);
    motor_speed = 250;
    //delay(500);
  }
  if(3 < cm || cm < 10){
    motor_speed = 150;
    while(1){
      digitalWrite(ledPin, HIGH);// LED 켜짐
      
      runMotor(motor_speed);
      
      digitalWrite(trigPin,HIGH); // 센서에 Trig 신호 입력
      delayMicroseconds(10); // 10us 정도 유지
      digitalWrite(trigPin,LOW); // Trig 신호 off
  
      duration = pulseIn(echoPin,HIGH); // Echo pin: HIGH->Low 간격을 측정
      cm = duration/29/2; // 거리(cm)로 변환
      Serial.print(cm);
      Serial.print("cm");
      if(cm > 10){
        break;
      }
      if(cm <= 3){
        motor_speed = 0;
      }
    }
  }
  if(cm <= 3){
    while(1){
      digitalWrite(ledPin, HIGH);// LED 켜짐
      runMotor(0);
      digitalWrite(trigPin,HIGH); // 센서에 Trig 신호 입력
      delayMicroseconds(10); // 10us 정도 유지
      digitalWrite(trigPin,LOW); // Trig 신호 off
  
      duration = pulseIn(echoPin,HIGH); // Echo pin: HIGH->Low 간격을 측정
      cm = duration/29/2; // 거리(cm)로 변환
      Serial.print(cm);
      Serial.print("cm");
      if(cm > 10){
        break;
      }
    }
  }
  digitalWrite(ledPin,LOW);   // LED 꺼짐
}

void setForward() {
  digitalWrite(MOTOR_A_PINS[0], 1); // HIGH
  digitalWrite(MOTOR_B_PINS[0], 1); // HIGH
  
  digitalWrite(MOTOR_A_PINS[1], 0); // LOW
  digitalWrite(MOTOR_B_PINS[1], 0); // LOW
}

void setBackward() {
  digitalWrite(MOTOR_A_PINS[0], 0); // LOW                                                                                                          
  digitalWrite(MOTOR_B_PINS[0], 0); // LOW
  
  digitalWrite(MOTOR_A_PINS[1], 1); // HIGH
  digitalWrite(MOTOR_B_PINS[1], 1); // HIGH
}

void runMotor(unsigned char speed) { // [0, 255]
  Serial.println(speed);
  analogWrite(MOTOR_A_PINS[2], speed);
  analogWrite(MOTOR_B_PINS[2], speed);
}
