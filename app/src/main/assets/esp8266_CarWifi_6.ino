/*
 *  Projeto desenvolvido para introduzir conceitos de 
 *  eletrônica, mecânica e programação
 * 
 *  Anderson S. Andrade
 *  Anderson Miguel de A. Andrade
 *  
 *  2017
 *  
 *  Configuração da IDE Arduino
 *  
 *  Placa NodeMCU 1.0
 *  80 MHz
 *  115200
 *  4M (3M SPIFFS
 *  
 */

#include <ESP8266WiFi.h>

//Inicializacao so servidor http na porta 80
WiFiServer server(80);

#define SSID "CARWIFI"
#define PASSWORD "car_Wifi"

IPAddress device_ip  (10, 0, 1, 1);
//IPAddress dns_ip     (  8,   8,   8,   8);
IPAddress gateway_ip (10, 0, 1, 1);
IPAddress subnet_mask(255, 255, 255,   0);

/*
As GPIOs abaixo, podem ser utilizadas normalmente:

OK D2  GPIO4
OK D1  GPIO5
OK D6  GPIO12
OK D7  GPIO13
OK D5  GPIO14

Essas GPIOs podem ser utilizadas para outras funções, mas não podem interferir durante o processo de boot, já que o modulo pode entrar em um estado não esperado.

D3  GPIO0 – Modo programação
OK D10 GPIO1 – UART0_TX
OK D4  GPIO2 – UART1_TX (log de boot)
OK D9  GPIO3 – UART0_RX
D8  GPIO15 – Modo da memória
D0  GPIO16 – XPD (parte do circuito de RTC) - Não usar com NodeMCU, chama WDT

A GPIO2, ao iniciar o módulo, envia um log do processo de boot, e não tem como desativar essa função, pelo menos até onde eu sei. Esse log pode atrapalhar um pouco se você estiver utilizando um relé, por exemplo.

GPIO16
A GPIO16 faz parte do circuito de RTC, sendo utilizada para “acordar” o módulo do modo deep-sleep, e não pode ser utilizada da mesma forma que as outras GPIOs.

Para o modo deep-sleep, a GPIO16 deve permanecer conectada no reset do módulo! Se quiser saber mais a respeito do modo deep-sleep, leia este artigo.

Também aciona em LOW o LED embutido no NodeMCU
*/

//D2    GPIO4   - OK - 1
//D1    GPIO5   - OK - 2
//D6    GPIO12  - OK - 3
//D7    GPIO13  - OK - 4
//D4    GPIO2   - OK - 5
//D5    GPIO14  - OK - 6
//D9    GPIO3   - OK - 7 - Não usar Serial RX0
//D10   GPIO1   - OK - 8 - Não usar Serial TX0

//Definição dos pinos
#define PIN_DIRECTION_LEFT        D1
#define PIN_DIRECTION_RIGHT       D2
#define PIN_DIRECTION_BACK        D6
#define PIN_DIRECTION_FRONT       D7
#define PIN_GYROFLEX              D4
#define PIN_BUZZER                D5
#define PIN_HEADLIGHT             D9
//#define PIN_??                D10

//Definição das strings http
#define STR_DIRECTION             "pin_direction"
#define STR_DIRECTION_LEFT_RIGHT  "pin_left_right" // l = left | 0 = stand by | r = right
#define STR_GYROFLEX              "pin_gyroflex"
#define STR_BUZZER                "pin_buzzer"
#define STR_HEADLIGHT             "pin_headlight"

//No ESP8266, é possível utilizar PWM em até 3 GPIO’s simultaneamente.
//D1, D2, D3, D4, D5, D6, D7, D8, RSV (PIN 12)
//Value constant
#define PWM_DIRECTION             0
#define PWM_DIRECTION_BACK        256
#define PWM_DIRECTION_FRONT_1     256
#define PWM_DIRECTION_FRONT_2     512
#define PWM_DIRECTION_FRONT_3     768
#define PWM_DIRECTION_FRONT_4     1023
#define PWM_DIRECTION_LEFT_RIGHT  768//512//256
#define PWM_TIME_LEFT_RIGHT       280
#define PWM_TIME_LEFT_RIGHT_RET   170

bool log_serial = false;
int direction_left_right = 0;     // -1 = left | 0 = front | 1 = right
void setup() {

    //log_serial = true;

    if (log_serial) {
      //Configura a serial para monitoramento físico
      Serial.begin(115200);
    }
    
    //Configura a GPIOs como saida
    pinMode(PIN_DIRECTION_LEFT, OUTPUT);
    pinMode(PIN_DIRECTION_RIGHT, OUTPUT);
    pinMode(PIN_DIRECTION_BACK, OUTPUT);
    pinMode(PIN_DIRECTION_FRONT, OUTPUT);
    pinMode(PIN_BUZZER, OUTPUT);
    pinMode(PIN_GYROFLEX, OUTPUT);
    if (!log_serial) {
      pinMode(PIN_HEADLIGHT, OUTPUT);
    }
    
    //Coloca a GPIOs em sinal logico baixo
    analogWrite(PIN_DIRECTION_LEFT, PWM_DIRECTION);
    analogWrite(PIN_DIRECTION_RIGHT, PWM_DIRECTION);
    analogWrite(PIN_DIRECTION_BACK, PWM_DIRECTION);
    analogWrite(PIN_DIRECTION_FRONT, PWM_DIRECTION);
    digitalWrite(PIN_BUZZER, LOW);
    digitalWrite(PIN_GYROFLEX, LOW);
    if (!log_serial) {
      digitalWrite(PIN_HEADLIGHT, LOW);
    }

    //Configure ip personalizado para o modo AP 
    WiFi.softAPConfig(device_ip, gateway_ip, subnet_mask);
    
    //Configura o SSID, PASSWORD e o modo AP
    WiFi.softAP(SSID, PASSWORD);

    //Log na serial ao se conectar
    logSerial("");
    logSerial("------------------------");
    logSerial("");
    logSerial("Conected");
    
    //
    WiFi.enableSTA(false);
    
    //
    //WiFi.persistent(true);

    //Inicia o webserver
    server.begin();

    logSerial("HTTP server started");
    logSerial("");
}

void loop() {

  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  //logSerial("new client");
  while(!client.available()){
    delay(1);
  }

  //logSerial("");
  String req = client.readStringUntil('\r');
  logSerial(req);
  client.flush();

  if (int pos = req.indexOf(STR_DIRECTION) != -1) {
    pin_direction(adjustVal(req, pos, 17));
  } else if (int pos = req.indexOf(STR_DIRECTION_LEFT_RIGHT) != -1) {
    pin_left_right(adjustValStr(req, pos, 18));
  } else if (int pos = req.indexOf(STR_GYROFLEX) != -1) {
    pin_gyroflex(adjustVal(req, pos, 16));    
  } else if (int pos = req.indexOf(STR_BUZZER) != -1) {
    pin_buzzer(adjustVal(req, pos, 14));
  } else if (int pos = req.indexOf(STR_HEADLIGHT) != -1) {
    pin_headlight(adjustVal(req, pos, 17));
  } else {
    logSerial("invalid request");
  }

  client.print(handleRoot());
  client.flush();
  client.stop();
  logSerial("Client disonnected");
}

void logSerial(String msg) {
  if (log_serial) {
    Serial.println(msg);
  }
}

String handleRoot() {
  String buf = "";
  buf += "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n";
  buf += "<html lang=\"en\"><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\"/>\r\n";
  buf += "<title>Car Wifi</title>";
  buf += "<style>.c{text-align: center;} div,input{padding:5px;font-size:1em;} input{width:80%;} body{text-align: center;font-family:verdana;} button{border:0;border-radius:0.3rem;background-color:#1fa3ec;color:#fff;line-height:2.4rem;font-size:1.2rem;width:100%;} .q{float: right;width: 64px;text-align: right;}</style>";
  buf += "</head>";
  buf += "<h4> Car Wifi - ESP8266 Web Server</h4>";
  //buf += "<div><h5>Left0:</h5><a href=\"pin_left0\"><button>PWM Off</button></a></div>";
  //buf += "<div><h5>Left1:</h5><a href=\"pin_left1\\" + String(speed_direction) + "\\\"""><button>PWM = " + String(speed_direction) + "</button></a></div>";
  buf += "<h6>EduMigRafa</h6>";
  buf += "</html>\n";

  return buf;
}

int adjustVal(String req, int pos, int lenght) {
  //logSerial("pos = " + String(pos));
  String str = req.substring(pos + lenght);
  //logSerial("Str = " + str);
  pos = str.indexOf("/");
  str = str.substring(0, pos);
  logSerial("Str2 = " + str);
  pos = str.toInt();
  
  return pos;
}

String adjustValStr(String req, int pos, int lenght) {
  //logSerial("pos = " + String(pos));
  String str = req.substring(pos + lenght);
  //logSerial("Str = " + str);
  pos = str.indexOf("/");
  str = str.substring(0, pos);
  logSerial("Str2 = " + str);

  return str;
}

void pin_direction(int value){
  //logSerial("pos = " + String(pos));

  int pin_back = 0;
  int pin_front = 0;
  switch(value) {
    case -1:
      pin_back = PWM_DIRECTION_BACK;
      pin_front = PWM_DIRECTION;
      break;
    case 0:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION;
      break;
    case 1:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION_FRONT_1;
      break;
    case 2:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION_FRONT_2;
      break;
    case 3:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION_FRONT_3;
      break;
    case 4:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION_FRONT_4;
      break;
    default:
      pin_back = PWM_DIRECTION;
      pin_front = PWM_DIRECTION;
      break;
  }

  if (pin_back == 0 & pin_front == 0) {
    logSerial("PIN_DIRECTION STOP");
    analogWrite(PIN_DIRECTION_BACK, pin_back);
    delay(1);
    analogWrite(PIN_DIRECTION_FRONT, pin_front);
  } else if (pin_back < pin_front) {
    logSerial("PIN_DIRECTION FRONT " + String(value));
    analogWrite(PIN_DIRECTION_BACK, pin_back);
    delay(1);
    analogWrite(PIN_DIRECTION_FRONT, pin_front);
  } else {
    logSerial("PIN_DIRECTION BACK");
    analogWrite(PIN_DIRECTION_FRONT, pin_front);
    delay(1);
    analogWrite(PIN_DIRECTION_BACK, pin_back);
  }

}

void pin_left_right(String value){
  // l = left | 0 = stand by | r = right
  
  int pin_left = 0;
  int pin_right = 0;
  if (value == "l") {
    pin_left = PWM_DIRECTION_LEFT_RIGHT;
    pin_right = PWM_DIRECTION;
  } else if (value == "0") {
    pin_left = PWM_DIRECTION;
    pin_right = PWM_DIRECTION;
  } else if (value == "r") {
    pin_left = PWM_DIRECTION;
    pin_right = PWM_DIRECTION_LEFT_RIGHT;
  } else {
    pin_left = PWM_DIRECTION;
    pin_right = PWM_DIRECTION;
  }

  if (pin_left == 0 & pin_right == 0) {
    logSerial("PIN_DIRECTION LEFT RIGTH STOP");
    reset_left_right();
  } else if (pin_left < pin_right) {
    logSerial("PIN_DIRECTION RIGHT " + String(value));
    right();
  } else {
    logSerial("PIN_DIRECTION LEFT");
    left();
  }
}

void reset_left_right() {
  if (direction_left_right != 0) {
    logSerial("reset left right begin");
    
    if (direction_left_right == -1) {
      analogWrite(PIN_DIRECTION_RIGHT, PWM_DIRECTION_LEFT_RIGHT);
      delay(PWM_TIME_LEFT_RIGHT_RET);
      analogWrite(PIN_DIRECTION_RIGHT, PWM_DIRECTION);
    }
    
    if (direction_left_right == 1) {
      analogWrite(PIN_DIRECTION_LEFT, PWM_DIRECTION_LEFT_RIGHT);
      delay(PWM_TIME_LEFT_RIGHT_RET);
      analogWrite(PIN_DIRECTION_LEFT, PWM_DIRECTION);
    }

    direction_left_right = 0;
    logSerial("reset left right end");
  }
}

void left() {
  if (direction_left_right != -1) {
    if (direction_left_right == 1) {
      reset_left_right();
    }
    logSerial("left begin");
    analogWrite(PIN_DIRECTION_LEFT, PWM_DIRECTION_LEFT_RIGHT);
    delay(PWM_TIME_LEFT_RIGHT);
    analogWrite(PIN_DIRECTION_LEFT, PWM_DIRECTION);
    direction_left_right = -1;
    logSerial("left end");
  }
}

void right() {
  if (direction_left_right != 1) {
    if (direction_left_right == -1) {
      reset_left_right();
    }
    logSerial("right begin");
    analogWrite(PIN_DIRECTION_RIGHT, PWM_DIRECTION_LEFT_RIGHT);
    delay(PWM_TIME_LEFT_RIGHT);
    analogWrite(PIN_DIRECTION_RIGHT, PWM_DIRECTION);
    direction_left_right = 1;
    logSerial("right end");
  }
}

void pin_gyroflex(int value) {

  switch(value) {
    case 0:
      digitalWrite(PIN_GYROFLEX, LOW);
      logSerial("PIN_GYROFLEX OFF");
      break;
    case 1:
      digitalWrite(PIN_GYROFLEX, HIGH);
      logSerial("PIN_GYROFLEX ON");
      break;
    default:
      digitalWrite(PIN_GYROFLEX, LOW);
      logSerial("PIN_GYROFLEX OFF");
      break;
  }
}

void pin_buzzer(int value) {

  switch(value) {
    case 0:
      digitalWrite(PIN_BUZZER, LOW);
      logSerial("PIN_BUZZER OFF");
      break;
    case 1:
      digitalWrite(PIN_BUZZER, HIGH);
      logSerial("PIN_BUZZER ON");
      break;
    default:
      digitalWrite(PIN_BUZZER, LOW);
      logSerial("PIN_BUZZER OFF");
      break;
  }
}

void pin_headlight(int value) {
  if (!log_serial) {
    switch(value) {
      case 0:
        digitalWrite(PIN_HEADLIGHT, LOW);
        logSerial("PIN_HEADLIGHT OFF");
        break;
      case 1:
        digitalWrite(PIN_HEADLIGHT, HIGH);
        logSerial("PIN_HEADLIGHT ON");
        break;
      default:
        digitalWrite(PIN_HEADLIGHT, LOW);
        logSerial("PIN_HEADLIGHT OFF");
        break;
    }
  }
}
