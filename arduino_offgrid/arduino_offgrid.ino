//pins that control the lights
#define pinLight1 5
#define pinLight2 6
#define pinLight3 9
#define pinLight4 10

/**
 * Variables containing the lights' status
 * HIGH - light is on
 * LOW - light is off
 */ 
boolean l1,l2,l3,l4;

/**
 * @brief
 * Funcion responsible for the setup of the arduino
 *  - Inicializes the Serial Port at 9600 bauds
 *  - Incializes the control pins as OUTPUT pins
 *  - Sets the lights' variables to LOW
 */
void setup() {
  Serial.begin(9600);

  pinMode(pinLight1,OUTPUT);
  pinMode(pinLight2,OUTPUT);
  pinMode(pinLight3,OUTPUT);
  pinMode(pinLight4,OUTPUT);
  
  l1 = l2 = l3 = l4 = LOW;
}

/**
 * @brief
 * Funcion that controles the status of the lights according to the value passed    
 * 
 * @param
 * bit 0 of x represents the status of the light 1
 * bit 1 of x represents the status of the light 2
 * bit 2 of x represents the status of the light 3
 * bit 3 of x represents the status of the light 4
 * 
 * 1 - HIGH
 * 0 - LOW
 */
void updateCtrl(int x){
 switch (x) {
    case 0:
      l1 = l2 = l3 = l4 = LOW;
      break;
    case 1:
      l1 = HIGH;
      l2 = l3 = l4 = LOW;
      break;
    case 2:
      l2 = HIGH;
      l1 = l3 = l4 = LOW;
      break;
    case 3:
      l1 = l2 = HIGH;
      l3 = l4 = LOW;
      break;
    case 4:
      l3 = HIGH;
      l1 = l2 = l4 = LOW;
      break;
    case 5:
      l1 = l3 = HIGH;
      l2 = l4 = LOW;
      break;
    case 6:
      l2 = l3 = HIGH;
      l1 = l4 = LOW;
      break;
    case 7:
      l1 = l2 = l3 = HIGH;
      l4 = LOW;
      break;
    case 8:
      l4 = HIGH;
      l1 = l2 = l3 = LOW;
      break;
    case 9:
      l1 = l4 = HIGH;
      l2 = l3 = LOW;
      break;
    case 10:
      l2 = l4 = HIGH;
      l1 = l3 = LOW;
      break;
    case 11:
      l1 = l2 = l4 = HIGH;
      l3 = LOW;
      break;
    case 12:
      l3 = l4 = HIGH;
      l1 = l2 = LOW;
      break;
    case 13:
      l1 = l3 = l4 = HIGH;
      l2 = LOW;
      break;
    case 14:
      l2 = l3 = l4 = HIGH;
      l1 = LOW;
      break;
    case 15:
      l1 = l2 = l3 = l4 = HIGH;
      break;
    default: 
      break;
  }
}

/**
 * @brief
 * Funcion responsible for receiving the integer from the Java app and calling the function updateCtrl
 */
void loop() {
  digitalWrite(pinLight1,l1);
  digitalWrite(pinLight2,l2);
  digitalWrite(pinLight3,l3);
  digitalWrite(pinLight4,l4);
  
  if(Serial.available() > 0)
    updateCtrl(Serial.read());
}
