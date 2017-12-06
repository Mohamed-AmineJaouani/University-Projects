struct channel;

/* flags */
#define CHANNEL_PROCESS_SHARED 1
#define OPENED 0
#define CLOSED 1

struct channel *channel_create(int eltsize, int size, int flags);
void channel_destroy(struct channel *channel);
int channel_send(struct channel *channel, const void *data);
int channel_close(struct channel *channel);
int channel_recv(struct channel *channel, void *data);
int channel_send_lots(struct channel *channel, const void **data, int nb);
int channel_recv_lots(struct channel *channel, void **data, int nb);
