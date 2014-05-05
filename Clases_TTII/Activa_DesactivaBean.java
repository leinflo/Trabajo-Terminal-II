/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_TTII;

import java.util.List;
import java.util.Map;
import Acceso_Datos.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author daniel
 */
public class Activa_DesactivaBean {
 
    private final Map<String,String> activas, inactivas;
    private List<String> selectedActivas, selectedInactivas;
    private final Activa_Desactiva activa_desac = new Activa_Desactiva();
    
    
    public Activa_DesactivaBean(){
   
    activas = activa_desac.obtieneActivas();
    inactivas = activa_desac.obtieneInactivas();
    
    
    }
    public List<String> getSelectedActivas() {  
        return this.selectedActivas;  
    }  
    public void setSelectedActivas(List<String> selectedActivas) {  
        this.selectedActivas = selectedActivas;  
    }  
  
    public Map<String, String> getActivas() {  
        return this.activas;  
    }
    
        public List<String> getSelectedInactivas() {  
        return this.selectedInactivas;  
    }  
    public void setSelectedInactivas(List<String> selectedInactivas) {  
        this.selectedInactivas = selectedInactivas;  
    }  
  
    public Map<String, String> getInactivas() {  
        return this.inactivas;  
    }
    
    /**
     *
     * @param actionEvent
     */
    public void Activa(ActionEvent actionEvent){  
        int exito;
        System.out.println("Entra a metodo");
        if(selectedActivas.isEmpty() && selectedInactivas.isEmpty()){  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Se debe elegir almentos una alerta activa o una alerta inactiva"));   
    }
        else{  
        exito = activa_desac.ActivarDesactivar(selectedActivas, selectedInactivas);
        if(exito==0){  
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!", "Se Activaron/Desactivaron las alertas correctamente!"));
    }
        else{  
        if(exito==1){  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Se debe elegir almenos una alerta inactiva o una alerta activa"));
    }else{  
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"FATAL!", "Hubo un error en la base"));
    } 
    }
    }
         
    }
}
