CFLAGS+= -g -Wall

conexion: conexion.o ../baseDatos.o servidor.o
	gcc $(CFLAGS) conexion.o  ../baseDatos.o servidor.o -o conexion -L/usr/include/mysql -lmysqlclient -lpthread

servidor.o: servidor.c servidor.h ../baseDatos.h
	gcc $(CFLAGS) -c servidor.c -I../ -o servidor.o

conexion.o: conexion.c servidor.h ../baseDatos.h
	gcc $(CFLAGS) -c conexion.c -I../ -o conexion.o -lpthread
 
clean:
	rm *.o
