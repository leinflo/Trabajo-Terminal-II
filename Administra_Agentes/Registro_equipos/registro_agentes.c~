#include<stdio.h>
#include<stdlib.h>
#include<baseDatos.h>
#include<sys/inet.h>
#include<string.h>


void Registra_nuevo(){
char DireccionIP[15];
int es_valido;
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");
printf("\t\t\n\nRegistra nuevo Agente\t\t\n\n");
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");

printf("\t\t\n\nPor favor ingrese la direccion IP del Agente\t\t\n");
printf("Nota: Solo acepta IPv4\n\n");
gets(DireccionIP);

es_valido = isIP_v4(DireccionIP);

if(es_valido!=0){
printf("La direccion IP ingresada no es válida solo se aceptan direcciones IPv4\t\t\n\n");
}
else{
void AlmacenaEquipoNuevo(DireccionIP);
}
}

int isIp_v4( char* ip){
        int num;
        int flag = 1;
        int counter=0;
        char* p = strtok(ip,".");

        while (p && flag ){
                num = atoi(p);

                if (num>=0 && num<=255 && (counter++<4)){
                        flag=1;
                        p=strtok(NULL,".");

                }
                else{
                        flag=0;
                        break;
                }
        }

        return flag && (counter==3);

}

void Obtiene_Digesto(){
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");
printf("\t\t\n\nObtener Digesto\t\t\n\n");
printf("\n\n\t\t-----------------------------------------------------------------\t\t\n\n");

printf("\t\t\n\nPor favor ingrese la direccion IP del Agente\t\t\n");
printf("Nota: Solo acepta IPv4\n\n");
gets(DireccionIP);
char DireccionIP[15];
char Digesto[22];

Digesto = ObtieneDigesto(DireccionIP);

if(Digesto !=NULL){
printf(" Digesto : %s ", Digesto);
}
else{
printf("No se pudo obtener el digesto de la IP %s", DireccionIP);
}
}
