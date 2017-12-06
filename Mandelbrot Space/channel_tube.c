#include "channel.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define BUFF_SIZE 256
#define PREFIXE_NOM_FIFO "channel_"
#define SUFFIXE_NOM_FIFO ".fifo"
static int id_nom_fifo = 1;


struct channel{
  char nom[BUFF_SIZE]; //nom du tube qui contient les données
  int descripteur[2];
  int state; //0 ouvert, 1 fermé 
  
  int eltsize; //taille d'un element 
  int size; //nombre d elements maximal dans le channel
  int nbElements; //nombre d elements dans le channel
  
  pthread_mutex_t verrou;
  pthread_cond_t attenteLecture;
  pthread_cond_t attenteEcriture;
};

void* checkMalloc(int size){
  void* monmalloc;
  if((monmalloc = malloc(size)) == NULL){
    exit(EXIT_FAILURE);
  }
  return monmalloc;
}


void freeChannel(struct channel *c){
  printf("free channel\n");
  close(c -> descripteur[0]);
  close(c -> descripteur[1]);
}



struct channel *channel_create(int eltsize, int size, int flags){
  if(size == 0 || flags == CHANNEL_PROCESS_SHARED){
    errno = ENOSYS;
    return NULL;
  }

  struct channel* c = checkMalloc(sizeof(struct channel));

  c -> eltsize = eltsize;
  c -> size = size;
  sprintf(c -> nom, "%s%d%s", PREFIXE_NOM_FIFO, id_nom_fifo++, SUFFIXE_NOM_FIFO);

  if(mkfifo(c -> nom, S_IRWXU) != 0){
    //traiter les erreurs
    if(errno != EEXIST){
      perror("[channel_create] Erreur au mkfifo\n");
      exit(EXIT_FAILURE);
    }
  }
  //0 : ecriture
  //1 : lecture
  
  
  //le open bloque car open bloque si personne n'ouvre de lautre coté en lecture
  // il faudrait que le premier create on ouvre dabord en ecriture puis en lecture
  // et le deuxieme create on ouvre dabord en lecture puis en ecriture
  
  
  if((c -> descripteur[0] = open(c -> nom, O_RDWR)) == -1){ //O_WRONLY
    //traiter les erreurs
    perror("[channel_create] Erreur au 1er open\n");
    exit(EXIT_FAILURE);
  }

  if((c -> descripteur[1] = open(c -> nom, O_RDONLY)) == -1){// O_RDONLY
    //traiter les erreurs
    perror("[channel_create] Erreur au 2eme open\n");
    exit(EXIT_FAILURE);
  }
  
  c -> state = OPENED;

  c -> nbElements = 0;
  
  pthread_cond_init(&c -> attenteLecture, NULL);
  pthread_cond_init(&c -> attenteEcriture, NULL);

  pthread_mutex_init(&c -> verrou, NULL);
  
  return c;
}


void channel_destroy(struct channel *channel){

  channel->state = CLOSED;
  freeChannel(channel);
  channel = NULL;
  printf("Channel destroy\n");
}


int channel_close(struct channel *channel){
  // Cette fonction retourne 1 en cas de succès, 0 si le canal était déjà fermé, et -1 en cas d’erreur (et alors errno est positionné).
  // on ne peut que lire sur ce cannal, on ne peut plus ajouter de choses, il n'est pas détruit
  printf("Channel close\n");
  if(channel->state == CLOSED)
    return 0;
  else if(channel->state == OPENED){
    channel->state = CLOSED;
    return 1;
  }
  else{
    errno = EINVAL; //pas sûr, a modifier peut etre
    return -1;
  }
  
}


int channel_send(struct channel *channel, const void *data){
  /*Si le canal est fermé, cette fonction retourne -1 et errno vaut EPIPE. Sinon, si le canal n’est pas
    plein (il contient moins de size éléments), cette fonction stocke la VALEUR sur laquelle pointe data
    dans le canal (la valeur, pas le pointeur), et retourne 1. Si le canal est plein, elle bloque le thread
    appelant jusqu’à ce qu’il y ait de la place.*/
  // printf("On envoi un element de taille %d dans un emplacement de taille %d \n",sizeof(data),channel -> eltsize);

  if(channel -> state == CLOSED){
    errno = EPIPE;
    return -1;
  }
  else{
    pthread_mutex_lock(&channel -> verrou);

    channel -> nbElements ++;
    
    
    if(write(channel -> descripteur[0], data, channel -> eltsize) <= 0)
      printf("[SEND] Erreur au write\n");

    pthread_cond_broadcast(&channel -> attenteLecture);
    pthread_mutex_unlock(&channel->verrou);
  }

  return 1;
}


int channel_recv(struct channel *channel, void *data){
  /*Si le canal n’est pas vide, cette fonction défile un élément et le stocke à l’endroit où pointe data, et
    retourne 1. Si le canal est vide et fermé, elle retourne 0. Sinon, elle bloque le thread appelant jusqu’à
    ce que le canal ne soit plus vide ou soit fermé. Elle retourne -1 en cas d’erreur (et alors errno est
    positionné)*/

  pthread_mutex_lock(&channel->verrou);
  
  if(channel->nbElements >= 0 && channel -> nbElements <= channel -> size){ /* canal non vide */
    //defile un element et le stock dans le channel 
    while(channel -> nbElements == 0)
      pthread_cond_wait(&channel -> attenteLecture, &channel -> verrou);
    

    channel -> nbElements --;

    if(read(channel -> descripteur[0], data, channel -> eltsize) <= 0)
      printf("[RECV] Erreur au read\n");

    pthread_mutex_unlock(&channel->verrou);
    return 1;
  } 
  else if(channel -> nbElements == 0 && channel -> state == CLOSED){/* canal vide et fermé */ 
    return 0; 
  } 
  else {
    //positionner errno en cas d'erreur et return -1
    errno = EINVAL; //p.e a changer
    return -1;
  } 
  return 0;
}
