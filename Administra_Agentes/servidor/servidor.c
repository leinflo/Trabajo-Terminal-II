#include<pthread.h>
#include<baseDatos.h>
#include<stdio.h>
#include<stdlib.h>
#include"baseDatos.h"

int Conectar(char *direccionIP){

	int puede_conectar;

	puede_conectar=	PuedeConectarse(direccionIP);
	
	if(puede_conectar == 0){

	return 0;

	}
	else{
		
	return 1;
	
	}

}

void *funcionAgente(void *parametros){

	int a, puede_conectarse;

	char buffer[200],*hash;
	char dirIP[15];
	struct param *paramet;
	int opcion, resultado;

	//int socket = socket(AF_INET,SOCK_DGRAM,0);;	
		
	//printf("parametros recibidos \n");

	paramet = (struct param *)parametros;	
	int puerto;

	strcpy(buffer,paramet->mensaje);
	strcpy(dirIP,paramet->direccionIP);
	puerto = paramet->puerto;
	
	printf("Direccion IP %s buffer: %s puerto: %d \n",dirIP,paramet->mensaje,puerto);
	opcion = bandera(paramet->mensaje);

	switch(opcion){
	
	//primera vez que se conecta, se valida la IP y el 
	case 0:

	printf("caso 0\n");
	printf("buffer %s \n",buffer);

	hash=digesto(buffer);

	printf("Direccion IP %s hash: %s \n",dirIP,hash);

	resultado = ValidaEquipo(dirIP,hash,puerto);

	printf("resultado = %d \n",resultado);

	break;
	
	case 1:

	printf("caso 1\n");	

	puede_conectarse = Conectado(dirIP);//Conectar(direccionIP);	
	
	if(puede_conectarse == 0){
	
	printf("Agente conectado con éxito\n");	
	
	}
	else{

	printf("no se pudo conectar el agente\n");

	}

	break;

	//recibe información de procesos.
	case 2:
	
	printf("caso 2\n");

	AlmacenaProcesos(buffer,dirIP);
	
	break;

	//recibe información de renombre de archivos
	case 3:

	printf("caso 3\n");

	AlmacenaRenombre(buffer,dirIP);//caso 3

	break; 
	

	//recibe información de cambios en carpetas y archivos.
	case 4:

	printf("caso 4\n");

	AlmacenaCambio(buffer,dirIP);//caso 4


	break; 

	//recibe información de borrado de carpetas y archivos.
	case 5:
	printf("caso 5\n");

	AlmacenaBorrado(buffer,dirIP);// caso 5

	break;

	//recibe información de termino de proceso no permitido.
	case 6:

	printf("caso 6\n"); //caso 6

	AlmacenaFinProcesos(buffer,dirIP);

	break;

	//Agente desconectado
	case 7:

	printf("caso 7\n");
	//Desconectado(dirIP);//caso 7

	break;

	
	default:

	break;
	    
}
pthread_exit((void *)a);

}

void ValidaEquipoConectado(char* direccionIP, char *digesto,int puerto){

int validado=1;

validado = ValidaEquipo(direccionIP, digesto, puerto);

if(validado !=0){

	printf("No pudo ser validado el servidor %s con el digesto %s\n",direccionIP,digesto);
}
else{
printf("Validado exitosamente\n");
}

}

void *iniciaAlertador(void *ptr){
system("java -jar \"/home/daniel/NetBeansProjects/Alertas/dist/Alertas.jar\"");
printf("alertador iniciado\n");
}

