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
public class RepoMonFiles {
    public String agente, usuario, archivo, archivoDespues, momentoCambio, tipoCambio;

    public RepoMonFiles(String agente,String usuario,String archivo,String archivoDespues,String momentoCambio,String tipoCambio){
    this.agente = agente;
    this.usuario = usuario;
    this.archivo = archivo;
    this.archivoDespues = archivoDespues;
    this.momentoCambio  = momentoCambio;
    this.tipoCambio = tipoCambio;
    }
    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getArchivoDespues() {
        return archivoDespues;
    }

    public void setArchivoDespues(String archivoDespues) {
        this.archivoDespues = archivoDespues;
    }

    public String getMomentoCambio() {
        return momentoCambio;
    }

    public void setMomentoCambio(String momentoCambio) {
        this.momentoCambio = momentoCambio;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }
    
    
    
}
