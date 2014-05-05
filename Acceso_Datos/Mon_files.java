/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

/**
 *
 * @author DanielFlores
 */
public class Mon_files {
    String agente, user, archivo, archivoDespues, tipoCambio, momentoCambio;
    
    public Mon_files(){}
    public Mon_files(String agente,String user,String archivo,String archivoDespues,String tipoCambio,String momentoCambio){
    this.agente = agente;
    this.user = user;
    this.archivo = archivo;
    this.archivoDespues = archivoDespues;
    this.tipoCambio = tipoCambio;
    this.momentoCambio = momentoCambio;
    
    }
    
    public String getAgente(){return this.agente;}
    public String getUser(){return this.user;}
    public String getArchivo(){return this.archivo;}
    public String getArchivoDespues(){return this.archivoDespues;}
    public String getTipoCambio(){return this.tipoCambio;}
    public String getMomentoCambio (){return this.momentoCambio;}
    
}
