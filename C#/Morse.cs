const int Ledred = 13;
char list[] = {'-','.','-','.','j','-','-','-','-','j','-','j','.','.','-','j','-','.','-','.','j','.','-','j'};
int size = 24;


void setup()
{
  pinMode(Ledred, OUTPUT);
}

void loop()
{
  for(int i=0;i <= size;i++){
    if(list[i] == '-'){
      trace();
    }
    if(list[i] == '.'){
      point();
    }
    else{
      interval();
    }
   }
}

void interval(){
  digitalWrite(Ledred,LOW);
  delay(20);
}

void point(){
  digitalWrite(Ledred,HIGH);
  delay(20);
}
void trace(){
  digitalWrite(Ledred,HIGH);
  delay(20);
}