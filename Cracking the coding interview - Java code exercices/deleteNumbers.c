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
    char* r;
    int i;
    int size = 0;

    for(i = 0 ; i < strlen(s) ; i++)
      if(s[i] >= '0' && s[i] <= '9')
	size++;
    
    r = malloc(size);
    r[0] = '\0';
    for(i = 0 ; i < strlen(s) ; i++)
      if(s[i] >= '0' && s[i] <= '9')
	strncat(r, &s[i], 1);
    printf("%s\n",r);
  }
  return EXIT_SUCCESS;
}
