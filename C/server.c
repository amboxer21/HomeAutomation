#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <errno.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netdb.h>

#define BUFFER_SIZE 4096

int main(int argc, char *argv[]) {

  struct hostent *server;
  struct sockaddr_in serv_addr, cli_addr;

	if((argc < 3) || (argc > 4)) {
	  printf("USAGE: %s + IP Addr + Portno.\n", argv[0]);
	  exit(EXIT_FAILURE);
	}

  int sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if(sockfd < 0) {
	  printf("SOCKET(-1) error --> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

		else if(sockfd) {
	 	 do {
	 	  {
	 	    printf("Waiting for a connection...\n");
	 	  }
	 	 } while(!accept);
		}

  int yes = 1;
  setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int));

  server = gethostbyname(argv[1]);
	if(server == NULL) {
	  printf("GETHOSTBYNAME(NULL) error --> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

  int portno = atoi(argv[2]);
  if(portno < 0) {
    printf("ATOI(-1) error --> %s.\n", strerror(errno));
    exit(EXIT_FAILURE);
  }

  bzero((char *)&serv_addr, sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;
  memcpy(&serv_addr.sin_addr.s_addr, server->h_addr, server->h_length);
  serv_addr.sin_port = htons(portno);

  int binder = bind(sockfd, (const struct sockaddr *)&serv_addr, sizeof(serv_addr));
	if(binder < 0) {
	  printf("BIND(-1) error --> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

  int listener = listen(sockfd, 20);
	if(listener < 0) {
	  printf("LISTEN(-1) error --> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

  socklen_t clilen;
  clilen = sizeof(cli_addr);

  int newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
	if(newsockfd < 0) {
	  printf("ACCEPT(-1) error --> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

for(;;) {

  char buffer[BUFFER_SIZE];
  ssize_t bytes_read = recv(newsockfd, buffer, 3, 0);

  if(bytes_read) {

    printf("Client: %s\n", buffer);

    const char message[] = "Message recieved.\n";
    ssize_t bytes_written = write(newsockfd, message, strlen(message));
	  if(bytes_written < 0) {
	    printf("SEND(-1) error --> %s.\n", strerror(errno));
	    exit(EXIT_FAILURE);
	  }

    if(bytes_written) {
      printf("Message sent!\n");
      exit(0);
    }
  }

  if(bytes_read < 0) {
    printf("READ(-1) error --> %s.\n", strerror(errno));
    exit(EXIT_FAILURE);
  }

  if(bytes_read == 0) {
    printf("READ(0) error --> %s.\n", strerror(errno));
    exit(0);
  }

}

close(sockfd);
close(newsockfd);

return 0;
}
