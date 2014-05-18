/* librerías que usaremos */
/*Primero debemos verificar que existan las librerias. con el comando 
$mysql_config --libs
si no se instala con el comando
sudo apt-get install libmysqlclient-dev

una vez instalado configuramos con los comandos
$mysql_config --cflags
$mysql_config --libs

*/
/*Para compilar  $gcc -o Archivo_de_salida $(mysql_config --cflags) Archivo.c $(mysql_config --libs)	*/

#include <mysql/mysql.h> /* libreria que nos permite hacer el uso de las conexiones y consultas con MySQL */
#include <string.h>	

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>

//#include <openssl/sha.h>//libreria para obtener el digesto 

/*
funcion para insertar en la base de datos los valores que envia el agente
*/

int AlmacenaProcesos(char *cadena,char *direccionIP){

	//caso 2 Almacena el inicio de ejecución de los procesos
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	char *ptr;
	char *aux[5];
	int corre =0;	

	char *la_prueba;
	la_prueba = cadena;
	
	int tamdatos;
	tamdatos = sizeof(la_prueba);
	//printf("conexion realizada con exito \n");
	if(tamdatos<20){
	return 3;
	}
	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
		return 2;
	}
	//printf("conexion realizada con exito \n");
	
	//formato de entrada
	//numero_opcion;nombre del usuario;nombre del proceso;fecha inicio del proceso;.
	int cuenta =0;
	ptr = strtok( la_prueba,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	//printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL && corre<4){    // Posteriores llamadas
     	//printf( "%s\n", ptr );
	aux[corre]=ptr;
	//printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}
	
	//printf("termina while\n");
/*
ID int auto_increment primary key not null,
agente varchar(15),
user varchar(50),
nombre_proc varchar(100),
inicio_proc varchar(100),
fin_proc varchar(100)
*/

	strcpy(query,"insert into Mon_Proc values (NULL,'");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"','");
	strcat(query,aux[1]);//nombre del usuario formato Dominio/usuario
	strcat(query,"','");
	strcat(query,aux[2]);//nombre del proceso
	strcat(query,"','");
	strcat(query,aux[3]);//inicio del proceso
	strcat(query,"',NULL");//fin del proceo en null	
	strcat(query,")");
	
	//printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 2;//exit(1);
	}

	//if (mysql_query(conn,"select * from Mon_Proc"))
	//{ /* definicion de la consulta y el origen de la conexion */
	//	fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	//}

	//res = mysql_use_result(conn);
	//printf("ID\tDireccionIP\t\tusuario\t\tnombre proceso\t\t fecha inicio\n");
	//while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
	//	printf("%s\t%s\t%s\t%s\t%s \n", row[0],row[1],row[2],row[3],row[4]); /* la variable row se convierte en un	 arreglo por el numero de campos que hay en la tabla */

	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
	return 0;
}

int AlmacenaRenombre(char *cadena,char *direccionIP){

	//Caso 3 almacena renombre de archivos y carpetas

	

	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	char *ptr;
	char *aux[6];
	int corre =0;	

	char *la_prueba;
	la_prueba = cadena;

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
	return 2;		
	//exit(1);
	}
	//printf("conexion realizada con exito \n");

	int tamdatos;
	tamdatos = sizeof(la_prueba);
	//printf("conexion realizada con exito \n");
	if(tamdatos<20){
	return 3;
	}
	//formato de entrada	
	//numero_opcion;nombre del usuario;ruta completa del archivo antes ;ruta completa del archivo despues;RENOMBRE;momento en el que registro el evento.
	
	ptr = strtok( la_prueba,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	//printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL && corre<6){    // Posteriores llamadas
     	//printf( "%s\n", ptr );
	aux[corre]=ptr;
	//printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}
	
	//printf("termina while\n");
/*
create table Mon_files(
ID int auto_increment primary key not null,
agente varchar(15),
user varchar(50),
archivo varchar(200),
archivo_despues varchar(200),
tipo_cambio varchar(50),
momento_de_cambio varchar(50)
);
*/

	strcpy(query,"insert into Mon_files values (NULL,'");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"','");
	strcat(query,aux[1]);//nombre del usuario formato Dominio/usuario
	strcat(query,"','");
	strcat(query,aux[2]);//ruta completa del archivo antes 
	strcat(query,"','");
	strcat(query,aux[3]);//ruta completa del archivo despues 
	strcat(query,"','");
	strcat(query,aux[4]);//tipo del cambio
	strcat(query,"','");
	strcat(query,aux[5]);//momento del cambio
	strcat(query,"')");
	
	//printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
	return 2;		
	//exit(1);
	}

	/*if (mysql_query(conn,"select * from Mon_Files"))
	{ // definicion de la consulta y el origen de la conexion 
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	printf("ID\tM\t\tedad\n");
	while ((row = mysql_fetch_row(res)) != NULL) // recorrer la variable res con todos los registros obtenidos para su uso 
		printf("%s\t%s\t%s \n", row[0],row[1],row[2]); // la variable row se convierte en un arreglo por el numero de campos que hay en la tabla
	*/
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
return 0;
}

int AlmacenaCambio(char *cadena,char *direccionIP){

	//Caso 4 almacena cambio en archivos y carpetas
	 

	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	char *ptr;
	char *aux[4];
	//char nombreuser[50],ruta[300],tipocambio[15],momentocambio[50];
	int corre =0, tamdatos;	

	char *la_prueba;
	la_prueba = cadena;
	tamdatos = sizeof(la_prueba);
	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		return 2;//exit(1);
	}
	//printf("conexion realizada con exito \n");
	if(tamdatos<20){
	return 3;
	}
	//formato de entrada	
	//numero_opcion;nombre del usuario;ruta completa del archivo;tipo de cambio;momento en el que registro el evento.
	
	ptr = strtok( la_prueba,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	//printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL &&corre<5){    // Posteriores llamadas
     	//printf( "%s\n", ptr );
	aux[corre]=ptr;
	//printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}
	//strcpy(nombreuser,aux[1]);
	//strcpy(ruta,aux[2]);
	//strcpy(tipocambio,aux[3]);
	//strcpy(momentocambio,aux[4]);
	//printf("termina while\n");
/*
create table Mon_files(
ID int auto_increment primary key not null,
agente varchar(15),
user varchar(50),
archivo varchar(200), 
archivo_despues varchar(200),
tipo_cambio varchar(50),
momento_de_cambio varchar(50)
);
*/

	strcpy(query,"insert into Mon_files values (NULL,'");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"','");
	strcat(query,aux[1]);	
	//strcat(query,aux[1]);//nombre del usuario formato Dominio/usuario
	strcat(query,"','");
	strcat(query,aux[2]);//ruta completa del archivo antes 
	strcat(query,"',NULL,'");
	strcat(query,aux[3]);//tipo de cambio
	strcat(query,"','");
	strcat(query,aux[4]);//momento del cambio
	strcat(query,"')");
	
	// 	printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 2;//exit(1);
	}

	/*if (mysql_query(conn,"select * from Mon_Files"))
	{ // definicion de la consulta y el origen de la conexion 
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	printf("ID\tM\t\tedad\n");
	while ((row = mysql_fetch_row(res)) != NULL) // recorrer la variable res con todos los registros obtenidos para su uso 
		printf("%s\t%s\t%s \n", row[0],row[1],row[2]); // la variable row se convierte en un arreglo por el numero de campos que hay en la tabla
	*/
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
	return 0;
}


int AlmacenaBorrado(char *cadena,char *direccionIP){

	//Caso 5 almacena borrado de archivos y carpetas
	

	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	char *ptr;
	char *aux[4];
	int corre =0;	

	char *la_prueba;
	la_prueba = cadena;

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		return 2;//exit(1);
	}
	//la_prueba = cadena;
	int tamdatos;
	tamdatos = sizeof(la_prueba);
	//printf("conexion realizada con exito \n");
	if(tamdatos<20){
	return 3;
	}
	//formato de entrada	
	//numero_opcion;nombre del usuario;ruta completa del archivo;tipo de cambio;momento en el que registro el evento.
	
	ptr = strtok( la_prueba,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	//printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL &&corre<=4	){    // Posteriores llamadas
     	//printf( "%s\n", ptr );
	aux[corre]=ptr;
	//printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}
	
	//printf("termina while\n");
/*
create table Mon_files(
ID int auto_increment primary key not null,
agente varchar(15),
user varchar(50),
archivo varchar(200),
archivo_despues varchar(200),
tipo_cambio varchar(50),
momento_de_cambio varchar(50)
);
*/

	strcpy(query,"insert into Mon_files values (NULL,'");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"','");
	strcat(query,aux[1]);//nombre del usuario formato Dominio/usuario
	strcat(query,"','");
	strcat(query,aux[2]);//ruta completa del archivo antes 
	strcat(query,"',NULL,'");
	strcat(query,aux[3]);//tipo de cambio
	strcat(query,"','");
	strcat(query,aux[4]);//momento del cambio
	strcat(query,"')");
	
	//printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 2;//exit(1);
	}

	/*if (mysql_query(conn,"select * from Mon_Files"))
	{ // definicion de la consulta y el origen de la conexion 
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	printf("ID\tM\t\tedad\n");
	while ((row = mysql_fetch_row(res)) != NULL) // recorrer la variable res con todos los registros obtenidos para su uso 
		printf("%s\t%s\t%s \n", row[0],row[1],row[2]); // la variable row se convierte en un arreglo por el numero de campos que hay en la tabla
	*/
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
return 0;
}

int AlmacenaFinProcesos(char *cadena, char *direccionIP){

	//Caso 6 almacena el fin de ejecucion de un proceso
	

	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	char *ptr;
	char *aux[5];
	int corre =0;	

	char *la_prueba;
	la_prueba = cadena;

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		return 2;//exit(1);
	}
	//printf("conexion realizada con exito \n");

	//formato de entrada	
	//numero_opcion;nombre del usuario;nombre del proceso;momento en el que inicio;momento en el que termino.
	int tamdatos;
	tamdatos = sizeof(la_prueba);
	//printf("conexion realizada con exito \n");
	if(tamdatos<20){
	return 3;
	}
	ptr = strtok( la_prueba,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL && corre<=5){    // Posteriores llamadas
     	printf( "%s\n", ptr );
	aux[corre]=ptr;
	printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}
	
	printf("termina while\n");
/*
ID int auto_increment primary key not null,
agente varchar(15),
user varchar(50),
nombre_proc varchar(100),
inicio_proc varchar(100),
fin_proc varchar(100)
*/

	strcpy(query,"UPDATE Mon_Proc set fin_proc ='");
	strcat(query,aux[4]);//momento en el que termina
	strcat(query,"' where agente = '");
	strcat(query,direccionIP);
	strcat(query,"' and user = '");
	strcat(query,aux[1]);//nombre del usuario formato Dominio/usuario 
	strcat(query,"' and nombre_proc ='");
	strcat(query,aux[2]);//nombre del proceso
	strcat(query,"'and inicio_proc ='");
	strcat(query,aux[3]);//fecha inicio
	strcat(query,"'");
	
	printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 2;//exit(1);
	}

	/*if (mysql_query(conn,"select * from Mon_Files"))
	{ // definicion de la consulta y el origen de la conexion 
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	printf("ID\tM\t\tedad\n");
	while ((row = mysql_fetch_row(res)) != NULL) // recorrer la variable res con todos los registros obtenidos para su uso 
		printf("%s\t%s\t%s \n", row[0],row[1],row[2]); // la variable row se convierte en un arreglo por el numero de campos que hay en la tabla
	*/
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
	return 0;
}

void AlmacenaEquipoNuevo(char *direccionIP){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];
	char dir[16];	

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
	}
	//printf("conexion realizada con exito \n");
	//printf("%s",direccionIP);

	strcpy(dir,direccionIP);
	
	//printf("%s\n",dir);
	

/*
create table Equipos(
ID_equipo int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2),
respuesta_activa varchar(2)
);
*/

	strcpy(query,"select count(*) as existe from Equipos where Direccion_IP = '");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'");

	//printf("%s\n",query);

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n	", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	row = mysql_fetch_row(res); /* recorrer la variable res con todos los registros obtenidos para su uso **/

	//printf("%d ",atoi(row[0]));

	int i;
	if (atoi(row[0]) < 1){
	/*unsigned char aux[SHA_DIGEST_LENGTH];
	//unsigned char digesto[SHA_DIGEST_LENGTH];
	SHA(direccionIP,sizeof(direccionIP)-1,aux);
	for(i=0;i<SHA_DIGEST_LENGTH;i++)	
	printf("%02x",aux[i]);

	printf("\n");*/
	//strcpy(digesto,"12345689");

	strcpy(query,"insert into Equipos values (NULL,'");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"',sha('");
	strcat(query,direccionIP);//nombre del usuario formato Dominio/usuario
	strcat(query,"'),'NO','NO')");
	mysql_free_result(res);
	//	printf("%s\n",query);
	/* enviar consulta SQL */
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	if (mysql_query(conn,"select * from Equipos"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	}

	res = mysql_use_result(conn);
	printf("\t\nAgente Registrado con exito\t\n");
	printf("ID\tEquipo\t\tdigesto\t\n");
	while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
		printf("%s\t%s\t%s \n", row[0],row[1],row[2]); /* la variable row se convierte en un arreglo por el numero de campos que hay en la tabla */

	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
}
else
printf("el agente %s ya está registrado\n",direccionIP);
}

int ValidaEquipo(char *direccionIP, char *digesto,int puerto){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[300];
	char dirIP[15];
	char hash[40];	

	//printf("puerto %d \n",puerto);
	char nombre_archivo[100], profile[1048576];	

	//nombre del archivo a enviar por el socket al agente


	int tamano = 0, enviado;

	
	int sock;
	sock =  socket(AF_INET,SOCK_DGRAM,0);
	struct sockaddr_in servaddr;

	//bzero(&servaddr,sizeof(servaddr));
   	servaddr.sin_family = AF_INET;
   	servaddr.sin_addr.s_addr=inet_addr(direccionIP);
   	servaddr.sin_port=htons(puerto);

	FILE *perfil;



	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
	return 1; 
	}
	//printf("conexion realizada con exito \n");
	
	strcpy(dirIP,direccionIP);
	strcpy(hash,digesto);	
	//printf("DireccionIP: %s \n",direccionIP);
	//printf("Hash %s \n",digesto);

/*
create table Equipos(
ID int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2)
);
*/

	strcpy(query,"select count(*) as existe from Equipos where Direccion_IP = '");
	//printf("longitud %s \n ",query);	
	strcat(query,direccionIP);//direccion IP

	strcat(query,"'");
	
	
	//printf("%s \n",query);

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	return 1;
	}

	res = mysql_use_result(conn);
	row = mysql_fetch_row(res); /* recorrer la variable res con todos los registros obtenidos para su uso **/

	if (atoi(row[0]) == 1){

	mysql_free_result(res);
	
	strcpy(query,"update Equipos set verificado= 'SI' where Direccion_IP ='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'and digesto_de_verificacion ='");
	strcat(query,hash);//nombre del usuario formato Dominio/usuario
	strcat(query,"'");
	
	//printf("%s\n",query);
	/* enviar consulta SQL regresa 0 si se ejecuta correctamente, otro numero si se ejecuta mal*/
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 1;
		//exit(1);
	}

	if (mysql_query(conn,"select * from Equipos"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	return 1;
	}

	res = mysql_use_result(conn);
	printf("ID\tEquipo\t\tdigesto\tverificado\n");
	while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
		printf("%s\t%s\t%s\t%s \n", row[0],row[1],row[2],row[3]); /* la variable row se convierte en un arreglo por el numero de campos que hay en la tabla */

//printf("termina el while \n");
	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
//printf("se libera el query\n");
	strcpy(query,"select count(*) from perfil_equipo where id_equipo in(select id_equipo from Equipos where direccion_ip='");
//printf("paso el strcpy \n");
	strcat(query,direccionIP);//direccion IP
//printf("paso el primer strcat\n");
	strcat(query,"')");
//printf("pasa el segundo strcat\n");
//	printf("%s \n",query);
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 1;
		//exit(1);
	}
//printf("regreso el query\n");
	res = mysql_use_result(conn);

	row= mysql_fetch_row(res);

	if(atoi(row[0])<1){
	//printf("entra a 0\n");
	strcpy(profile,"No");
	
	enviado = sendto(sock,profile,2, 0, (struct sockaddr *)&servaddr,sizeof(servaddr));

	printf("enviado: %d %s \n",enviado,profile);

	return 2;
	}
	else {
	
	
	mysql_free_result(res);
	strcpy(query,"select perfil from cat_perfiles where id_perfil in (select id_perfil from perfil_equipo where id_equipo in(select id_equipo from Equipos where direccion_ip='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'))");
	
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 1;
		//exit(1);
	}
	res = mysql_use_result(conn);

	row= mysql_fetch_row(res);	
	
	//printf("%s \n",row[0]);	
	
	strcpy(nombre_archivo,"/home/daniel/Escritorio/perfiles/");
	strcat(nombre_archivo,row[0]);
	strcat(nombre_archivo,".xml");

	//printf("%s \n",nombre_archivo);

	perfil = fopen(nombre_archivo,"r");

	while(!feof(perfil)){
	profile[tamano] = fgetc(perfil);
	tamano++;
	}
	
	enviado = sendto(sock,profile,tamano-1,0,(struct sockaddr *)&servaddr,sizeof(servaddr));

	//printf("bytes enviados %d \n",enviado);
	//printf("%s \n",profile);	
	fclose(perfil);
	close(sock);	
	
	}
	
	mysql_close(conn);
//printf("conexiones cerradas");
	return 0;
	}
	else{
	printf("\t\t\n\nEl agente no está registrado\t\t\n\n");
	return 1;
	}
}



int Conectado(char *direccionIP){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
	return 1;
	}
	printf("conexion realizada con exito \n");
/*
create table Equipos(
ID int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2)
);
*/


	strcpy(query,"Select count(*) as existe from Equipos where Direccion_IP ='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'and verificado = 'SI'");

	//printf("%s \n",query);

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 1;
		//exit(1);
	}

	res = mysql_use_result(conn);	

	row = mysql_fetch_row(res);
	
	if(atoi(row[0])==1){	

	mysql_free_result(res);

	strcpy(query,"update Equipos set conectado= 'SI' where Direccion_IP = '");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'");



	printf("%s\n",query);
	/* enviar consulta SQL regresa 0 si se ejecuta correctamente, otro numero si se ejecuta mal*/
	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return 1;
		//exit(1);
	}

	if (mysql_query(conn,"select * from Equipos"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	return 1;
	}

	res = mysql_use_result(conn);
	printf("ID\tEquipo\t\tdigesto\tverificado\tverificado\n");
	while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
		printf("%s\t%s\t%s\t%s \t%s\n", row[0],row[1],row[2],row[3],row[4]); /* la variable row se convierte en un arreglo por el numero de campos que hay en la tabla */

	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);
	return 0;
	}
	else{
	
	printf("La direccion IP no existe o no está validada\n");
	return	1;
	}

}

int PuedeConectarse(char *direccionIP){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		return 1;//exit(1);
	}
	printf("conexion realizada con exito \n");
/*
create table Equipos(
ID int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2)
);
*/


	strcpy(query,"Select count(*) as existe from Equipos where Direccion_IP ='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"',and verificado = 'SI'");

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	return 1;
	}

	res = mysql_use_result(conn);	

	row = mysql_fetch_row(res);
	
	if(atoi(row[0])==1){	

	mysql_free_result(res);

	mysql_close(conn);
	return 0;
	}
	else{
	mysql_free_result(res);
	mysql_close(conn);	
	printf("La direccion IP no existe o no está validada\n");
	return 1;
	}

}


void ObtieneDigesto(char *direccionIP){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	
	char digesto[23];

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
		return;
	}
	//printf("conexion realizada con exito \n");

/*
create table Equipos(
ID int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2)
);
*/
	strcpy(query,"Select count(*) as existe from Equipos where Direccion_IP ='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'");

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		return;
		//exit(1);
	}

	res = mysql_use_result(conn);	

	row = mysql_fetch_row(res);

	
	
	if(atoi(row[0])==1){

	mysql_free_result(res); //libera la consulta que se realizó para verificar si la IP existe en la base de datos.
	strcpy(query,"Select digesto_de_verificacion from Equipos where Direccion_IP ='");
	strcat(query,direccionIP);//direccion IP
	strcat(query,"'");
	
	//printf("%s\n",query);

	if (mysql_query(conn,query))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//digesto = "error"; //no reg	rese null
		return; 
	}	
	
	res = mysql_use_result(conn);

	//printf("resultado \n");	
	int i;
	//printf("%d \n",mysql_num_fields(res));


	while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
		printf("Digesto de la direccion IP %s  es : %s\t \n",direccionIP,row[0]);
	
	//printf("regresa las columnas");

	mysql_free_result(res);
	mysql_close(conn);

	}
	else
	{

	printf("La direccion IP %s no está registrada \t \n",direccionIP);	
	
	}


}

void Consulta_Agentes(){
	MYSQL *conn; /* variable de conexión para MySQL */
	MYSQL_RES *res; /* variable que contendra el res	ultado de la consuta */
	MYSQL_ROW row; /* variable que contendra los campos por cada registro consultado */
	char *server = "localhost"; /*direccion del servidor 127.0.0.1, localhost o direccion ip */
	char *user = "root"; /*usuario para consultar la base de datos */
	char *password = "d4n13l.f"; /* contraseña para el usuario en cuestion */
	char *database = "Trabajo_Terminal_II"; /*nombre de la base de datos a consultar */
	conn = mysql_init(NULL); /*inicializacion a nula la conexión */
	char query[200];	
	
	char digesto[21];

	/* conectar a la base de datos */
	if (!mysql_real_connect(conn, server, user, password, database, 0, NULL, 0))
	{ /* definir los parámetros de la conexión antes establecidos */
		fprintf(stderr, "%s\n", mysql_error(conn)); /* si hay un error definir cual fue dicho error */
		//exit(1);
	return;
	}
	printf("conexion realizada con exito \n");

	if (mysql_query(conn,"select * from Equipos"))
	{ /* definicion de la consulta y el origen de la conexion */
		fprintf(stderr, "%s\n", mysql_error(conn));
		//exit(1);
	return;
	}

/*
create table Equipos(
ID int auto_increment primary key not null,
Direccion_IP varchar(15),
digesto_de_verificacion varchar(32),
verificado varchar(2),
conectado varchar(2)
);
*/


	res = mysql_use_result(conn);
	printf("ID\tDireccion IP\t\tDigesto de verificacion\t\tVerificado\t\tConectado\n");
	while ((row = mysql_fetch_row(res)) != NULL) /* recorrer la variable res con todos los registros obtenidos para su uso */
		printf("%s\t%s\t%s\t%s\t%s \n", row[0],row[1],row[2],row[3],row[4]); /* la variable row se convierte en un arreglo por el numero de campos que hay en la tabla */

	/* se libera la variable res y se cierra la conexión */
	mysql_free_result(res);
	mysql_close(conn);


}

int isIpv4(char ip[15]){
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


int bandera(char *cadena){

	char *ptr;
	char *aux[3];
	int corre =0;
	ptr = strtok( cadena,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
/*	corre++;
  	printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL ){    // Posteriores llamadas
     	printf( "%s\n", ptr );
	aux[corre]=ptr;
	printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;
	}*/

return atoi(aux[0]);

}

char *digesto(char *cadena){

	printf("Entra a la funcion para regresar el digesto de la cadena\n");
	char *ptr;
	char *aux[3];
	int corre =0;
	printf("cadena %s \n",cadena);
	ptr = strtok( cadena,";" );    // Primera llamada => Primer token
	aux[0]= ptr;
	corre++;
  	printf( "%s\n", ptr );
   	while( (ptr = strtok( NULL,";" )) != NULL&&corre<2 ){    // Posteriores llamadas
     	printf( "%s\n", ptr );
	aux[corre]=ptr;
	printf("corre en pos %d dice %s \n",corre,aux[corre]);
	corre++;	
	}

	printf("regresa aux en posicion 1 %s \n",aux[1]);
return aux[1];

}

	
