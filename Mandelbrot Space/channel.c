#include "channel.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/types.h> // pour ftruncate
#include <sys/mman.h> // pour shm_open
#include <sys/stat.h> // for mode constants (shm_open et semaphore)
#include <fcntl.h> // for O_* constants (shm_open et semaphore)

#define BUFF_SIZE 256
#define NOM_ZONE_MEMOIRE "/shm_open_struct_channel_"
static int id_zone_memoire = 1;

 
struct channel{
  
  //pour tous canaux confondus
  int state; //0 ouvert, 1 fermé 
  int eltsize;
  int size; //nombre d elements maximal dans le channel
  pthread_mutex_t verrou;

  //pour les canaux asynchrones
  void** data; //contient les donnees
  int nbElements; //nombre d elements dans le channel
  int indiceSend;
  int indiceRecv;
  pthread_cond_t attenteLecture;
  pthread_cond_t attenteEcriture; //aussi present dans canaux synchrones

  //pour les canaux synchrones
  void * adresse;
  //utilisent aussi pthread_cond_t attenteEcriture
  pthread_cond_t attenteEcrivain;
  pthread_cond_t attenteLecteur;
  int ecrivain;
  int lecteur;
  sem_t semaphoreEcrivain;
  sem_t semaphoreLecteur;

  //pour les canaux globaux
  int flags;
  char nomZoneMemoire[BUFF_SIZE];  
  
};





  /*******************************************************/
 /**** DEBUT : FONCTIONS UTILITAIRES POUR LES CANAUX ****/
/*******************************************************/

void* checkMalloc(int size){
  void* monmalloc;
  if((monmalloc = malloc(size)) == NULL){
    exit(EXIT_FAILURE);
  }
  return monmalloc;
}

void mallocdata(struct channel *c){
  int i = 0;
  
  c -> data = checkMalloc(c -> eltsize * c -> size * sizeof(void*));
  while(i < c -> size){
    c -> data[i] = checkMalloc(c -> eltsize * sizeof(void**));
    i++;
  }
  memset(c -> data, 0, c -> eltsize * c -> size);
}

void freeChannel(struct channel *c){
  int i = 0;
  while(i < c -> size){
    free(c -> data[i]);
    i++;
  }
  free(c -> data);
}

void channel_destroy(struct channel *channel){

  channel->state = CLOSED;
  freeChannel(channel);

  if(channel -> flags == CHANNEL_PROCESS_SHARED)
    if(shm_unlink(channel -> nomZoneMemoire) == -1){
      perror("Erreur shm_unlink\n");
      exit(EXIT_FAILURE);
    }
  
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


  /*****************************************************/
 /**** FIN : FONCTIONS UTILITAIRES POUR LES CANAUX ****/
/*****************************************************/







  /****************************************************/
 /**** DEBUT : PARTIE POUR LA CREATION DES CANAUX ****/
/****************************************************/


//Fonction d'initialisation pour la creation de canaux globaux
struct channel *channel_create_global() {
  struct channel* c;
  int fd, rc;
  void* p;
  char tmp[BUFF_SIZE];

  /** On prévient le système que l'on désire un objet en mémoire partagée (sans précision sur ses caractéristiques) **/
  sprintf(tmp, "%s%d", NOM_ZONE_MEMOIRE, id_zone_memoire);
  fd = shm_open(tmp,
		O_CREAT | O_RDWR,
		S_IRUSR | S_IWUSR);

  if(fd == -1) {
    perror("Erreur shm_open\n");
    exit(EXIT_FAILURE);
  }
    
  /** On ajuste de manière précise la taille de notre fichier **/
  if( (rc = ftruncate(fd, sizeof(struct channel))) == -1) {
    perror("Erreur ftruncate\n");
    exit(EXIT_FAILURE);
  }
    
  /** Réservation de la mémoire virtuelle : mapping de notre fd **/
  p = mmap(NULL,
	   sizeof(struct channel),
	   PROT_READ | PROT_WRITE,
	   MAP_SHARED,
	   fd,
	   0);
  
  if(p == MAP_FAILED) {
    perror("Erreur mmap\n");
    exit(EXIT_FAILURE);
  }

  c = (struct channel*) p;

  return c;
}




struct channel *channel_create_async(int eltsize, int size, int flags){
  struct channel* c;

  if(flags == CHANNEL_PROCESS_SHARED){ 
    c = channel_create_global();

    //initialisation pour canaux globaux
    c -> flags = CHANNEL_PROCESS_SHARED;
    sprintf(c -> nomZoneMemoire, "%s%d", NOM_ZONE_MEMOIRE, id_zone_memoire++);
  }
  
  else
    c = checkMalloc(sizeof(struct channel));

  //initialisation pour tous canaux confondus
  c -> state = OPENED;
  c -> eltsize = eltsize;
  c -> size = size;
  pthread_mutex_init(&c -> verrou, NULL);

  //initialisation pour canaux asynchrones
  mallocdata(c);
  c -> nbElements = 0;
  c -> indiceSend = 0;
  c -> indiceRecv = 0;
  pthread_cond_init(&c -> attenteLecture, NULL);
  pthread_cond_init(&c -> attenteEcriture, NULL);

  return c;
}




struct channel *channel_create_sync(int eltsize, int flags){
  struct channel* c;

  if(flags == CHANNEL_PROCESS_SHARED){
    c = channel_create_global();

    //initialisation pour canaux globaux
    c -> flags = CHANNEL_PROCESS_SHARED;
    sprintf(c -> nomZoneMemoire, "%s%d", NOM_ZONE_MEMOIRE, id_zone_memoire++);
  }
  
  else
    c = checkMalloc(sizeof(struct channel));
  
  //initialisation pour tous canaux confondus
  c -> state = OPENED;
  c -> eltsize = eltsize;
  c -> size = 0; //car canal synchrone
  pthread_mutex_init(&c -> verrou, NULL);

  //initialisation pour canaux synchrones
  pthread_cond_init(&c -> attenteEcriture, NULL);
  pthread_cond_init(&c -> attenteEcrivain, NULL);
  pthread_cond_init(&c -> attenteLecteur, NULL);
  c -> ecrivain = 0;
  c -> lecteur = 0;
  sem_init(&c -> semaphoreEcrivain, 0, 1);
  sem_init(&c -> semaphoreLecteur, 0, 1);

  return c;
}




struct channel *channel_create(int eltsize, int size, int flags) {
  if(size <= 0)
    return channel_create_sync(eltsize, flags);
  else
    return channel_create_async(eltsize, size, flags);
}


  /**************************************************/
 /**** FIN : PARTIE POUR LA CREATION DES CANAUX ****/
/**************************************************/







  /*************************************************/
 /**** DEBUT : PARTIE POUR L'ENVOI DE DONNEES  ****/
/*************************************************/


int channel_send_async(struct channel *channel, const void *data){
  /*Si le canal est fermé, cette fonction retourne -1 et errno vaut EPIPE. Sinon, si le canal n’est pas
    plein (il contient moins de size éléments), cette fonction stocke la VALEUR sur laquelle pointe data
    dans le canal (la valeur, pas le pointeur), et retourne 1. Si le canal est plein, elle bloque le thread
    appelant jusqu’à ce qu’il y ait de la place.*/
  
  if(channel -> state == CLOSED){
    errno = EPIPE;
    return -1;
  }

  else{
    pthread_mutex_lock(&channel -> verrou);

    while(channel -> nbElements == channel -> size)
      pthread_cond_wait(&channel -> attenteEcriture, &channel -> verrou);

    memcpy(channel -> data + (((channel -> indiceSend) % channel -> size) * channel -> eltsize), data, channel -> eltsize);
    
    channel -> indiceSend ++;    
    channel -> nbElements ++;
    pthread_cond_broadcast(&channel -> attenteLecture);
    
    
    pthread_mutex_unlock(&channel->verrou);
  }

  return 1;
}






int channel_send_sync(struct channel *channel, const void *data){
  /*Si le canal est fermé, cette fonction retourne -1 et errno vaut EPIPE. Sinon, si le canal n’est pas
    plein (il contient moins de size éléments), cette fonction stocke la VALEUR sur laquelle pointe data
    dans le canal (la valeur, pas le pointeur), et retourne 1. Si le canal est plein, elle bloque le thread
    appelant jusqu’à ce qu’il y ait de la place.*/
  
  if(channel -> state == CLOSED){
    errno = EPIPE;
    return -1;
  }
  else{
    sem_wait(&channel -> semaphoreEcrivain);
    
    pthread_mutex_lock(&channel -> verrou);

    channel -> ecrivain = 1;
    
    if(channel -> lecteur ==  1)
      pthread_cond_signal(&channel -> attenteEcrivain);
    
    pthread_cond_wait(&channel -> attenteLecteur, &channel -> verrou);
    
    memcpy(channel -> adresse, data, channel -> eltsize);
    
    pthread_cond_signal(&channel -> attenteEcriture);
    
    channel -> ecrivain = 0;

    sem_post(&channel -> semaphoreEcrivain);

    pthread_mutex_unlock(&channel->verrou);
    
    
  }

  return 1;
}




int channel_send(struct channel *channel, const void *data){
  if(channel -> size == 0)
    return channel_send_sync(channel, data);
  else
    return channel_send_async(channel, data);
}


  /***********************************************/
 /**** FIN : PARTIE POUR L'ENVOI DE DONNEES  ****/
/***********************************************/








int channel_recv_async(struct channel *channel, void *data){
  /*Si le canal n’est pas vide, cette fonction défile un élément et le stocke à l’endroit où pointe data, et
    retourne 1. Si le canal est vide et fermé, elle retourne 0. Sinon, elle bloque le thread appelant jusqu’à
    ce que le canal ne soit plus vide ou soit fermé. Elle retourne -1 en cas d’erreur (et alors errno est
    positionné)*/

  pthread_mutex_lock(&channel->verrou);
  
  if(channel->nbElements >= 0 && channel -> nbElements <= channel -> size){ /* canal non vide */
 
    while(channel -> nbElements == 0)
      pthread_cond_wait(&channel -> attenteLecture, &channel -> verrou);

   
    memcpy(data, channel -> data + (((channel -> indiceRecv) % channel -> size) * channel -> eltsize), channel -> eltsize);
    channel -> indiceRecv ++;
    channel -> nbElements --;

    pthread_cond_broadcast(&channel -> attenteEcriture);

    pthread_mutex_unlock(&channel->verrou);
    
    return 1; 
  } 
  else if(channel -> nbElements == 0 && channel->state == CLOSED){/* canal vide et fermé */ 
    return 0; 
  } 
  else {
    //positionner errno en cas d'erreur et return -1
    
    errno = EINVAL;
    return -1;
  } 
  return 0;
}






int channel_recv_sync(struct channel *channel, void *data){
  /*Si le canal n’est pas vide, cette fonction défile un élément et le stocke à l’endroit où pointe data, et
    retourne 1. Si le canal est vide et fermé, elle retourne 0. Sinon, elle bloque le thread appelant jusqu’à
    ce que le canal ne soit plus vide ou soit fermé. Elle retourne -1 en cas d’erreur (et alors errno est
    positionné)*/
  
  if(channel->state == CLOSED){ /*canal fermé */
    return 0;
  } 
  else if(channel -> state == OPENED){
        
    sem_wait(&channel -> semaphoreLecteur);
    
    pthread_mutex_lock(&channel -> verrou);

    channel -> lecteur = 1;
    
    if(channel -> ecrivain == 0){
      pthread_cond_wait(&channel -> attenteEcrivain, &channel -> verrou);
    }

    channel -> adresse = data;
    
    if(channel -> adresse == MAP_FAILED){
      exit(EXIT_FAILURE);
    }
    
    pthread_cond_signal(&channel -> attenteLecteur);

    pthread_cond_wait(&channel -> attenteEcriture, &channel -> verrou);
    
    channel -> lecteur = 0;

    sem_post(&channel -> semaphoreLecteur);

    pthread_mutex_unlock(&channel -> verrou);

    return 1;
    
  }
  else{
    errno = EINVAL;
  }
  return -1;
}


int channel_recv(struct channel *channel, void *data){
  if(channel -> size == 0)
    return channel_recv_sync(channel, data);
  else
    return channel_recv_async(channel, data);
}




int channel_send_lots(struct channel *channel, const void **data, int nb){
  if(channel -> size == 0){
    printf("Communication par lots incompatible avec les cannaux synchrones \n");
    exit(EXIT_FAILURE);
  }
  int i;
  int rc;
  
  for(i = 0 ; i < nb ; i++){
    rc = channel_send_async(channel,data[i]);
    if( rc == -1){
      printf("[SENDL] Erreur au channel_sendl\n");
      exit(EXIT_FAILURE);
    }
  }
  return rc;
}

int channel_recv_lots(struct channel *channel, void **data, int nb){
  if(channel -> size == 0){
    printf("Communication par lots incompatible avec les cannaux synchrones \n");
    exit(EXIT_FAILURE);
  }
  int i;
  int rc = 0;
  
  for(i = 0 ; i < nb ; i++){
    rc = channel_recv_async(channel, data[i]);
    if( rc == -1 || rc == 0 ){
      printf("[SENDL] Erreur au channel_sendl\n");
      exit(EXIT_FAILURE);
    }
  }
  return rc;
}
