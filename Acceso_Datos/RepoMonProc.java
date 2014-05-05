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
public class RepoMonProc {
    public String agente, usuario, proceso, inicioProceso; 
    
    public RepoMonProc(String agente,String usuario, String proceso, String inicioProceso){
     
        this.agente= agente;
                this.usuario = usuario;
                        this.proceso = proceso;
                        this.inicioProceso = inicioProceso;
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

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getInicioProceso() {
        return inicioProceso;
    }

    public void setInicioProceso(String inicioProceso) {
        this.inicioProceso = inicioProceso;
    }
}
