/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Acceso_Datos;

/**
 *
 * @author daniel
 */
public class RepoProfile {
    String nombre, correo, mon_filesAgente, mon_filesCambio, mon_ProcAgente,mon_procInicio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMon_filesAgente() {
        return mon_filesAgente;
    }

    public void setMon_filesAgente(String mon_filesAgente) {
        this.mon_filesAgente = mon_filesAgente;
    }

    public String getMon_filesCambio() {
        return mon_filesCambio;
    }

    public void setMon_filesCambio(String mon_filesCambio) {
        this.mon_filesCambio = mon_filesCambio;
    }

    public String getMon_ProcAgente() {
        return mon_ProcAgente;
    }

    public void setMon_ProcAgente(String mon_ProcAgente) {
        this.mon_ProcAgente = mon_ProcAgente;
    }

    public String getMon_procInicio() {
        return mon_procInicio;
    }

    public void setMon_procInicio(String mon_procInicio) {
        this.mon_procInicio = mon_procInicio;
    }

    public RepoProfile(String nombre,String correo,String mon_filesAgente,String mon_filesCambio,String mon_ProcAgente,String mon_procInicio){
    this.nombre =nombre;
    this.correo = correo;
    this.mon_filesAgente = mon_filesAgente;
    this.mon_filesCambio = mon_filesCambio;
    this.mon_ProcAgente = mon_ProcAgente;
    this.mon_procInicio = mon_procInicio;
    }
    
}
