/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

/**
 *
 * @author DanielFlores
 */

public class Equipos {
    int id;
    String equipo,digesto,verificado,conectado;//,respuestaActiva;
    //List<String> equipos;
    
    public Equipos(){}
    
    public Equipos(int id, String equipo,String digesto, String verificado, String conectado){
        this.id = id;
        this.equipo =equipo;
        this.digesto =digesto;
        this.verificado = verificado;
        this.conectado = conectado;
       // this.respuestaActiva = respuestaActiva;
    }
    
    public int getId(){return id;}
    public String getEquipo(){return equipo;}
    public String getDigesto(){return digesto;}
    public String getVerificado(){return verificado;}
    public String getConectado(){return conectado;}
    //public String getRespuestaActiva(){return respuestaActiva;}
    
    public void setId(int id){ this.id = id;}
    public void setEquipo(String equipo){this.equipo = equipo;}
    public void setDigesto(String digesto){this.digesto = digesto;}
    public void setVerificado(String verificado){this.verificado = verificado;}
    public void setConectado(String conectado){this.conectado = conectado;}
    //public void setRespuestaActiva(String respuestaActiva){this.respuestaActiva = respuestaActiva;}
     
    
}
