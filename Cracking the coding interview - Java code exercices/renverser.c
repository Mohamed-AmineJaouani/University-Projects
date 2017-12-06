#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]){
  if(argc != 2){
    printf("Entrez un mot en argument\n");
    return EXIT_FAILURE;
  }
  else{
    char* s = argv[1];
    char* reverse = malloc(strlen(s)+1);
    int i;
    reverse[0] = '\0';
    for(i = strlen(s) ; i >= 0 ; i--)
      strncat(reverse,&s[i],1);
    
    printf("%s\n",reverse);
  }
  return EXIT_SUCCESS;
}
