#ifndef PARAM_H
#define PARAM_H

typedef struct param{
	char mensaje[200];
	char direccionIP[15];
}PARAM;

#endif

/*Funciones para validar en la base de datos conexiones*/
int ValidaEquipo(char *direccionIP, char *digesto);//caso 0
int Conectado(char *direccionIP);//caso 1
void AlmacenaProcesos(char *cadena,char *direccionIP);//caso 2
void AlmacenaRenombre(char *cadena,char *direccionIP);//caso 3
void AlmacenaCambio(char *cadena,char *direccionIP);//caso 4
void AlmacenaBorrado(char *cadena,char *direccionIP);// caso 5
void AlmacenaFinProcesos(char *cadena, char *direccionIP);//caso 6
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

