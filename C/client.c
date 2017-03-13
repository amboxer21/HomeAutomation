#include <stdio.h> 
#include <string.h> 
#include <strings.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <sys/socket.h> 
#include <sys/types.h> 
#include <netdb.h> 
#include <netinet/in.h> 
#include <arpa/inet.h>
#include <errno.h>

#define BUFFER_SIZE 256

int main(int argc, char **argv) {  

  int debug;
  char inBuffer[BUFFER_SIZE];
  char outBuffer[BUFFER_SIZE];
  struct hostent *server; 
  struct sockaddr_in serv_addr/*, cli_addr*/;

	if((argc < 3) && (argc > 4)) {
	fprintf(stderr, "Ussage: %s + IP Address + port No. Append a 1 to turn verbose on.\n", argv[0]);
	exit(EXIT_FAILURE);
	}

	if(argc == 4) {

    if(strncmp(argv[3], "onn", 3) == 0) {
      snprintf(outBuffer, 4, "onn");
      printf("buffer: %s\n", outBuffer);
    }

    else if(strncmp(argv[3], "off", 3) == 0) {
      snprintf(outBuffer, 4, "off");
      printf("buffer: %s\n", outBuffer);
    }

    else {
      printf("%s is not a known command\n", argv[3]);
      exit(0);
    }

  }

  int sockfd = socket(AF_INET, SOCK_STREAM, 0); 
	if(sockfd < 0) {
	  printf("SOCKET(-1) error ---> %s.\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

  int yes = 1; 
  setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int));

  bzero((char *) &serv_addr, sizeof(serv_addr)); 
  server = gethostbyname(argv[1]); 
	if(server == NULL) {
	  fprintf(stderr, "No such host.\n");
	  printf("%s\n", strerror(errno)); 
	  exit(EXIT_FAILURE);
	}

  int portno = atoi(argv[2]);
	if(portno < 0) {
	  printf("PORTNO(0) error ---> %s.\n", strerror(errno));
	}

  serv_addr.sin_family = AF_INET;
  memcpy(&serv_addr.sin_addr.s_addr, server->h_addr, server->h_length); 
  serv_addr.sin_port = htons(portno);

  int connector = connect(sockfd, (const struct sockaddr *)&serv_addr, sizeof(serv_addr));
	if(connector < 0) { 
	  fprintf(stderr, "%s. CONNECT()\n", strerror(errno));
	  exit(EXIT_FAILURE);
	}

  else {
		printf("Made a connection to %s\n", inet_ntoa(serv_addr.sin_addr)); 
	}

ssize_t bytes_written = write(sockfd, outBuffer, strlen(outBuffer));
bzero(outBuffer, sizeof(outBuffer));
close(sockfd);

return 0;
}
