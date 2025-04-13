const int ledBlue   = 9;
const int ledRed    = 11;
const int ledYellow = 10;
const int ledGreen  = 7;

const int buttonStart = 4;
const int buttonBlue = 3;
const int buttonRed = 0;
const int buttonYellow = 1;
const int buttonGreen = 2;

const int listaLed[] = {ledBlue, ledRed, ledYellow, ledGreen};

void setup() {
  pinMode(ledBlue, OUTPUT);
  pinMode(ledRed, OUTPUT);
  pinMode(ledYellow, OUTPUT);
  pinMode(ledGreen, OUTPUT);

  pinMode(buttonStart, INPUT_PULLUP);
  pinMode(buttonBlue, INPUT_PULLUP);
  pinMode(buttonRed, INPUT_PULLUP);
  pinMode(buttonYellow, INPUT_PULLUP);
  pinMode(buttonGreen, INPUT_PULLUP);

  randomSeed(analogRead(0));
}

void loop() {
  if (digitalRead(buttonStart) == LOW) {
    delay(300);
    while(digitalRead(buttonStart) == LOW);
    genius();
  }
}

void genius() {
  int time = 800;
  int level[100];

  for (int i = 0; i < 100; i++) {
   
    if (i % 3 == 0) {
      if (time > 300) time -= 100;            
    }

      level[i] = random(0, 4);

    for (int j = 0; j < i; j++) {
      digitalWrite(listaLed[level[j]], HIGH);
      delay(time);
      digitalWrite(listaLed[level[j]], LOW);
      delay(400);
    }


    int acertou = analyzer(level, i);
    if (!acertou) return;
  }
}

int analyzer(int list[], int size) {
  for (int i = 0; i < size; i++) {
    int numberB = -1;

    while (numberB == -1) {
      if (digitalRead(buttonBlue) == LOW) {
        numberB = 0;
      } else if (digitalRead(buttonRed) == LOW) {
        numberB = 1;
      } else if (digitalRead(buttonYellow) == LOW) {
        numberB = 2;
      } else if (digitalRead(buttonGreen) == LOW) {
        numberB = 3;
      }
    }

    digitalWrite(listaLed[numberB], HIGH);
    delay(300);
    digitalWrite(listaLed[numberB], LOW);
    while (digitalRead(buttonBlue) == LOW || digitalRead(buttonRed) == LOW ||
           digitalRead(buttonYellow) == LOW || digitalRead(buttonGreen) == LOW);

    delay(300);

    if (numberB != list[i]) {
 
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 4; k++) digitalWrite(listaLed[k], HIGH);
        delay(300);
        for (int k = 0; k < 4; k++) digitalWrite(listaLed[k], LOW);
        delay(300);
      }
      return 0;
    }
  }

  return 1;
}