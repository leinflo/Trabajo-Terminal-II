Administra_Agentes: Administra_Agentes.o registro_agentes.o baseDatos.o
	gcc Administra_Agentes.o registro_agentes.o baseDatos.o -o Administra_Agentes -L/usr/include/mysql -lmysqlclient 

Administra_Agentes.o: Administra_Agentes.c registro_agentes.h baseDatos.h
	gcc -c Administra_Agentes.c -o Administra_Agentes.o

registro_agentes.o: registro_agentes.c registro_agentes.h baseDatos.h
	gcc -c registro_agentes.c -o registro_agentes.o

baseDatos.o: baseDatos.c baseDatos.h
	gcc -c -o baseDatos.o $(mysql_config --cflags) baseDatos.c $(mysql_config --libs) -lssl

clean:
	rm *.o 
