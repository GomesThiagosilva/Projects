const int led = 13;
const int button1 = 11;
const int button2 = 9;
bool estadoLed = LOW;

void setup() {
    pinMode(button1, INPUT_PULLUP); 
    pinMode(button2, INPUT_PULLUP); 
    pinMode(led, OUTPUT);                  
}

void loop() {
    if (digitalRead(button1) == LOW && digitalRead(button2) == LOW) {
        for (int i = 0; i < 5; i++) {
            estadoLed = !estadoLed;
            digitalWrite(led, estadoLed);
            delay(300);
            estadoLed = !estadoLed;
            digitalWrite(led, estadoLed);
            delay(300);
        }
        estadoLed = HIGH;
        digitalWrite(led, estadoLed);

        
        while (digitalRead(button1) == LOW && digitalRead(button2) == LOW);
    } else {
        estadoLed = LOW;
        digitalWrite(led, estadoLed);
    }

    delay(10);
}
