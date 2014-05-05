/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

/**
 *
 * @author DanielFlores
 */
public class Mon_proc {
    String agente, user,nombreProc, inicioProc;
    
    public Mon_proc(){}
    public Mon_proc(String agente, String user, String nombreProc, String inicioProc){
    this.agente = agente;
    this.user = user;
    this.nombreProc = nombreProc;
    this.inicioProc = inicioProc;
    
    }
    
    public String getAgente(){return this.agente;}
    public String getUser(){return this.user;}
    public String getNombreProc(){return this.nombreProc;}
    public String getInicioProc(){return this.inicioProc;}
            
    
    
    
}
