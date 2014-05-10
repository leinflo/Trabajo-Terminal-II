#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#define PUERTO 5000

int main(int argc, char**argv)
{
   int sockfd,n;
   struct sockaddr_in servaddr,cliaddr;
   char sendline[200];
   //char recvline[1000];

   if (argc != 2)
   {
      printf("usage:  client <IP address>\n");
      exit(1);
   }

   sockfd=socket(AF_INET,SOCK_DGRAM,0);

   bzero(&servaddr,sizeof(servaddr));
   servaddr.sin_family = AF_INET;
   servaddr.sin_addr.s_addr=inet_addr(argv[1]);
   servaddr.sin_port=htons(PUERTO);

   //connect(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr));

printf("prueba de envio \n");
gets(sendline);

printf("buffer a mandar %s \n",sendline);

//n = send(sockfd,(char*)sendline,sizeof(sendline),0);
   //while (fgets(sendline, 10000,stdin) != NULL)
   //{
      n =sendto(sockfd,sendline,200,0,(struct sockaddr *)&servaddr,sizeof(servaddr));
	if(n<0){
	perror("hubo problemas al mandar el mensaje");
	}
	printf("%d\n",n);
      //n=recvfrom(sockfd,recvline,10000,0,NULL,NULL);
      //recvline[n]=0;
     // fputs(recvline,stdout);
   //}
}
