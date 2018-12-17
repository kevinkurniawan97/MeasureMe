#define SONAR_NUM       20 // Number of sensors.
#define MAX_DISTANCE    98

byte trigPins[] = {2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40};
byte echoPins[] = {3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41};

#define SENSORTIMEOUT   30000 // in microseconds
#define NUMSENSORS      (sizeof(echoPins))

int shoulder=0, shoulderIndex=0;
int chest=0, chestIndex=0;
int waist=0, waistIndex=0;

void setup() {
  Serial.begin(9600);
  for (int i=0; i<NUMSENSORS; i++) {
    pinMode(trigPins[i], OUTPUT);
    pinMode(echoPins[i], INPUT);
  }
}

void loop() {
  if(Serial.available())  
  {  
   int c = Serial.read();  
   if(c == 1) { // measure mode 1
    measureShoulder();
   } else if(c == 2) { // measure mode 2
    measureChestWaist();
   } else if(c == 3) { // measure mode 3
    measureChestWaist();
    reset();
   }
  }
}

void measureShoulder() { 
  int tempVal[5], tempIndex[5];
  
  for(int j=0; j<5; j++) {
    tempVal[j]=0;
    tempIndex[j]=0;
    shoulder=0;
    bool detect=false;
    
    while(tempVal[j]==0) {
      for(int i=0; i<8; i++) {
        long width=0;
        
        do {
          long distance1=0;
          long distance2=0;
          while(distance1==0) {
            int tempDistance=measure(i);
  
            if(detect==true) {
              if(tempDistance<=(0.75*MAX_DISTANCE)) distance1=tempDistance;
              else distance1=0;
            } else {
              if(tempDistance<=MAX_DISTANCE) distance1=tempDistance;
              else distance1=0;
            }
            delay(10);
          } // end while distance1
          
         while(distance2==0) {
            int tempDistance=measure(i+10);
          
            if(detect==true) {
              if(tempDistance<=(0.75*MAX_DISTANCE)) distance2=tempDistance;
              else distance2=0;
            } else {
              if(tempDistance<=MAX_DISTANCE) distance2=tempDistance;
              else distance2=0;
            }
            delay(10);
         }// end while distance2
         
         width=99-distance1-distance2; 
        } while (width<=0 && detect==true); // do ... while
        
        if(width > 0) detect=true;
        
        if(width>=27 && width<=52) {

          tempVal[j]=width;
          tempIndex[j]=i;
          break;
          
        } // end if
      } // end for i
    } // end while
  } // end for j
  
  shoulder=removeOutlier(tempVal);
  shoulderIndex=removeOutlier(tempIndex);
  
  if(shoulder==0) {
    shoulder=tempVal[2];
  }
  if(shoulderIndex==0) {
    shoulderIndex=tempIndex[2];
  }
  
  byte value = shoulder & 255;
  Serial.write(value);
  chestIndex=shoulderIndex+1;
  waistIndex=shoulderIndex+2;
}

void measureChestWaist() {  
  int chestIndex=5, waistIndex=6;
  int tempValChest[5], tempValWaist[5];
  
  for (int j=0; j<5; j++) {
    for (int i=chestIndex; i<=waistIndex; i++) {
      long width=0;
      do {
        long distance1=0;
        long distance2=0;
        while(distance1==0) {
          int tempDistance=measure(i);
          
          if(tempDistance<=(0.75*MAX_DISTANCE)) distance1=tempDistance;
          else distance1=0;
          
          delay(10);
       } // end while distance1

       while(distance2==0) {
          int tempDistance=measure(i+10);
        
          if(tempDistance<=(0.75*MAX_DISTANCE)) distance2=tempDistance;
          else distance2=0;

          delay(10);
       } // end while distance2
       width=99-distance1-distance2; 
      } while (width<=0); // do ... while
      
      if(i==chestIndex) {
       tempValChest[j]=width;
      } else if(i==waistIndex) {
       tempValWaist[j]=width;
      }
    } // end for i
  
  } // end for j
  chest=removeOutlier(tempValChest);
  waist=removeOutlier(tempValWaist);

  if(chest==0) chest=tempValChest[2];
  if(waist==0) waist=tempValWaist[2];

  if(chest<13 || waist<11) {
    measureChestWaist();
  }
  else {
    byte value = chest & 255;
    Serial.write(value);
    delay(50);
    
    value = waist & 255;
    Serial.write(value);
  }
}

int measure(int i) {
  // Clear the trigPin and echoPin
  digitalWrite(echoPins[i], LOW);
  digitalWrite(trigPins[i], LOW);
  delayMicroseconds(500);
  
  // Send trigger
  digitalWrite(trigPins[i], HIGH);
  delayMicroseconds(50);
  digitalWrite(trigPins[i], LOW);
  
  // Return the sound wave travel time in microseconds
  long duration = pulseIn(echoPins[i], HIGH);
  int distance = duration/58.2;
  
  return distance;
}

int calculateMode(int value[]) {
  int f[99];
  int max=1;
  int mode=0;
  for(int i=0; i<99; i++){
    f[i]=0;
  }
  for(int i=0; i<(sizeof(value)/sizeof(value[0])); i++){
    f[value[i]-1]+=1;
  }
  for(int i=0; i<99; i++){
    if(f[i]>max) {
      max=f[i];
      mode=i+1;
    }
  }
  return mode;
}

void reset() {
  shoulder=0;
  shoulderIndex=0;
  chest=0;
  chestIndex=0;
  waist=0;
  waistIndex=0;
}

int *sort(int data[]) 
{
int n = 5;
  int count=0;
  int temp;
  bool swap=true;
  while(swap) {
    swap=false;
    count++;
    for(int i=0; i<(n-count); i++) {
      if (data[i+1] < data[i]) {
        temp=data[i];
        data[i]=data[i + 1];
        data[i + 1]=temp;
        swap=true;
      }       
    }
  }
  return data;
}

int median(int l, int r)
{
    int n = r - l + 1;
    n = (n + 1) / 2 - 1;
    return n + l;
}

int quartile(int data[], int q)
{
    int *sortedData = sort(data);

    int n = 5;
 
    // Index of median of entire data
    int mid_index = median(0, n-1);
 
    // Median of first half
    int Q1 = sortedData[median(0, mid_index)];
 
    // Median of second half
    int Q3 = sortedData[median(mid_index + 1, n-1)];

    if(q==1) return Q1; // Q1
    else if(q==2) return Q3; // Q3
}

int removeOutlier(int data[]) {
   int Q1 = quartile(data, 1);
   int Q3 = quartile(data, 2);
   int IQR = Q3-Q1;
   double TLow = Q1-(1.5*IQR);
   double THigh = Q3+(1.5*IQR);

   int newData[5];
   int j=0;
   for(int i=0; i<5; i++) {
    if(data[i]>=TLow && data[i]<=THigh) {
      newData[j]=data[i];
      j++;
    }
   }

   int sum=0;
   for(int i=0; i<j; i++) {
    sum += newData[i];
   }
   
   return sum/j;   
}

