#include<stdio.h>
#include<stdlib.h>
#include"baseDatos.h"
//#include<sys/inet.h>
#include<string.h>


void Registra_nuevo(){
char DireccionIP[15];
//int es_valido;
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");
printf("\t\t\n\nRegistra nuevo Agente\t\t\n\n");
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");

printf("\t\t\n\nPor favor ingrese la direccion IP del Agente\t\t\n");
printf("Nota: Solo acepta IPv4\n\n");
scanf("%s",DireccionIP);//gets(DireccionIP);

/*es_valido = isIPv4(DireccionIP);

if(es_valido!=0){
printf("La direccion IP ingresada no es válida solo se aceptan direcciones IPv4\t\t\n\n");
}
else{
AlmacenaEquipoNuevo(DireccionIP);
}*/
	char dir[16];
	int num;
        int flag = 1;
        int counter=0;
	strcpy(dir,DireccionIP);
        char* p = strtok(DireccionIP,".");
	//printf("%d ", strlen(p));
	//if(strlen(p)==15){
        while (p && flag ){
                num = atoi(p);
		//printf("%d\n",num);
                if (num>=0 && num<=255 && (counter++<4)){
                        flag=1;
                        p=strtok(NULL,".");
		//printf("flag %d\n",flag);

                }
                else{
                        flag=0;
                        break;
                }
        }
	//}
	//else
	//flag=0;

//printf("%d",flag);
if(flag!=1){
printf("La direccion IP ingresada no es válida solo se aceptan direcciones IPv4\t\t\n\n");
}
else{
printf("direccion %s",dir);
AlmacenaEquipoNuevo(dir);
}
}

void Obtiene_Digesto(){
char DireccionIP[15];
printf("\n\n\t\t---------------------------------------------------------\t\t\n\n");
printf("\t\t\n\nObtener Digesto\t\t\n\n");
printf("\n\n\t\n---------------------------------------------------------\t\t\n\n");

printf("\t\t\n\nPor favor ingrese la direccion IP del Agente\t\t\n");
printf("Nota: Solo acepta IPv4\n\n");
scanf("%s",DireccionIP);
char Digesto[22];

ObtieneDigesto(DireccionIP);

/*if(Digesto !=NULL){
printf(" Digesto : %s ", Digesto);
}
else{
printf("No se pudo obtener el digesto de la IP %s", DireccionIP);
}*/
}
