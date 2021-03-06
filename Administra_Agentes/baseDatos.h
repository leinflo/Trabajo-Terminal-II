#ifndef PARAM_H
#define PARAM_H

typedef struct param{
	char mensaje[400];
	char direccionIP[15];
	int puerto;
	int data;
}PARAM;

#endif

/*Funciones para validar en la base de datos conexiones*/
int ValidaEquipo(char *direccionIP, char *digesto,int puerto);//caso 0
int Conectado(char *direccionIP);//caso 1
int AlmacenaProcesos(char *cadena,char *direccionIP);//caso 2
int AlmacenaRenombre(char *cadena,char *direccionIP);//caso 3
int AlmacenaCambio(char *cadena,char *direccionIP);//caso 4
int AlmacenaBorrado(char *cadena,char *direccionIP);// caso 5
int AlmacenaFinProcesos(char *cadena, char *direccionIP);//caso 6
//void Desconectado(char *direccionIP);//caso 7

/*Funciones usadas para el administrador de agentes*/
void AlmacenaEquipoNuevo(char *direccionIP);
int PuedeConectarse(char *direccionIP);
void ObtieneDigesto(char *direccionIP);
void Consulta_Agentes();
int isIpv4(char ip[15]);

/*funciones varias*/
char *digesto(char *cadena);
int bandera(char *cadena);

