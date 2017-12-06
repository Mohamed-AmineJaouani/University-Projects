#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char* argv[]){
    long long n;
    if(argc != 2){
      printf("Enter a binary number: ");
      return EXIT_FAILURE;
    }
    else{
      sscanf(argv[1], "%lld", &n);
      int decimal = 0, i = 0, retenue = 0;
      while(n != 0){
        retenue = n%10;
        n /= 10;
        decimal += retenue*pow(2,i);
        i++;
      }
      printf("%d\n", decimal);
    }
    return 0;
}
