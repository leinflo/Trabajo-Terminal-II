/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

/**
 *
 * @author DanielFlores
 */
import Acceso_Datos.EliminaPerfil;
import Acceso_Datos.EliminaP;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class EliminaPerfilBean {
    
        private EliminaP eliminar = new EliminaP();
        
        int identificador;
        
        List<EliminaPerfil> elimina;        
        
        public EliminaPerfilBean(){
         elimina = eliminar.LlenaPerfiles();            
                       
        }
        
        public void setIdentificador(int identificador){this.identificador = identificador;}
        
        public int getIdentificador(){return this.identificador;}

        public void Eliminar(){
        int exito;
        System.out.println("Entro a eliminar");
        exito = eliminar.Elimina(identificador);
        
        if(exito == 0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "Perfil eliminado!"));
        }
        else{
            if(exito==1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "No existe el perfil"));
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "ERROR EN LA BASE"));
            }
        
        }
        }
        
        public List<EliminaPerfil> getElimina(){ return this.elimina;}
        
    
    
}
