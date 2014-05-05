/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

/**
 *
 * @author DanielFlores
 */
import java.io.Serializable;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;
import Acceso_Datos.ModificaPerfil;
import Acceso_Datos.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
  
public class ModificaPerfilBean implements Serializable {  
  
    private List<String> selectedMovies;
    private List<String> selectedProcesos;
    private List<ModificaPerfil> profiles;
    private List<String> perfiles;
    private Iterator<String> profi;
    private Perfiles prof = new Perfiles();
    private String nombre;
    private Map<String,String> movies;  
    private Map<String,String> procesos;
    private ModificaPerfil modifica;
  
    public ModificaPerfilBean() {  
        //obtiene los nombres de los perfiles
        modifica = new ModificaPerfil();
        perfiles = prof.obtienePerfiles();
        profi = perfiles.iterator();
        
        //Llena el Arrego de perfiles
        
        profiles = new ArrayList<ModificaPerfil>();
        //public ModificaPerfil(String nombre,Map<String,String> procesos_actuales,Map<String,String> rutas_actuales,Map<String,String>procesosFaltantes,Map<String,String>rutasFaltantes)
        while(profi.hasNext()){            
            nombre = profi.next();
            profiles.add(new ModificaPerfil(nombre,prof.obtieneProcesosActuales(nombre),prof.obtieneRutasActuales(nombre),prof.obtieneProcesosFaltantes(nombre),prof.obtieneRutasFaltantes(nombre),prof.obtieneProcesos(nombre),prof.obtieneRutas(nombre)));            
        }           
    }  
  
    public List<ModificaPerfil> getProfiles() {  
        return profiles;  
    } 
    
    public List<String> getSelectedMovies() {  
        return selectedMovies;  
    }  
    public void setSelectedMovies(List<String> selectedMovies) {  
        this.selectedMovies = selectedMovies;  
    }  
  
    public Map<String, String> getMovies() {  
        return movies;  
    }    
    
    public List<String> getSelectedProcesos() {  
        return selectedProcesos;  
    }  
    public void setSelectedProcesos(List<String> selectedProcesos) {  
        this.selectedProcesos = selectedProcesos;  
    }  
  
    public Map<String, String> getProcesos() {  
        return procesos;  
    }
    
    
    public void obtieneProc(){
    System.out.println("Entra a obtener los procesos");
        if(selectedMovies.size()>1){
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR!", "La relación debe ser 1 a 1"));
                   
                }
        else{
        if(selectedMovies.size()<=0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", "Se debe elegir una dirección IP y un perfil"));
        }
        else{
            String perfil;
            Iterator<String> obtieneProcesos = selectedMovies.iterator();            
            perfil = obtieneProcesos.next();
            procesos = modifica.obtieneProcesos(perfil);            
        }
        }
    }
}
