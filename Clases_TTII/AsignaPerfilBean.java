/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

import java.io.Serializable;  
import java.util.List;  
import java.util.Map; 
import Acceso_Datos.AsignaPerfil;
import Acceso_Datos.Base_Datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author DanielFlores
 */
public class AsignaPerfilBean implements Serializable{
    
    private List<String> selectedMovies;  
    private List<String> selectedProfiles;  
    private Map<String,String> movies, profiles;
    private AsignaPerfil asigna;
    private List<AsignaPerfil> equiposPerfiles;
    private Base_Datos DB = new Base_Datos();
  
    public AsignaPerfilBean() {  
        asigna = new AsignaPerfil();
        movies = asigna.obtieneDireccionesIP();
        profiles = asigna.obtienePerfiles();
        equiposPerfiles = new ArrayList<AsignaPerfil>();
        this.llenaEquiposPerfiles(equiposPerfiles);
        
        
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
    
    public List<String> getSelectedProfiles() {  
        return selectedProfiles;  
    }  
    public void setSelectedProfiles(List<String> selectedProfiles) {  
        this.selectedProfiles = selectedProfiles;  
    }  
  
    public Map<String, String> getProfiles() {  
        return profiles;  
    }
    
    public List<AsignaPerfil> getEquiposPerfiles() {  
        return equiposPerfiles;  
    } 
    
    
    private void llenaEquiposPerfiles(List<AsignaPerfil> list) {  
        try {    
            Connection con;
            con = DB.conecta();
            PreparedStatement ps;
            ResultSet rs;
            String query = "select e.Direccion_IP, cp.perfil from Equipos e inner join perfil_equipo pe on e.id_equipo = pe.id_equipo inner join cat_perfiles cp on pe.id_perfil = cp.id_perfil;";//query para validar la existencia del equipo
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            //System.out.println("antes del while");
            while(rs.next()){
                System.out.println(rs.getInt(1));
            list.add(new AsignaPerfil(rs.getString(1),rs.getString(2)));
            }
            
    } catch (Exception e) {
            //System.out.println("hubo un error");
            e.printStackTrace();
        }
        
    }
    
    public void asignaPerfil(){
    
        if(selectedMovies.size()>1 || selectedProfiles.size()>1){
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ERROR!", "La relación debe ser 1 a 1"));
                   
                }
        else{
        if(selectedMovies.size()<=0 || selectedProfiles.size()<=0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR", "Se debe elegir una dirección IP y un perfil"));
        }
        else{
            int exito;
            String dirIP, perfil;
            Iterator<String> obtieneIP = selectedMovies.iterator();
            Iterator<String> obtienePerfil = selectedProfiles.iterator();
            dirIP = obtieneIP.next();
            perfil=obtienePerfil.next();
            exito = asigna.asignaProfile(dirIP,perfil );
            
            if(exito == 0 ){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito!", "Se asignó el perfil "+perfil+"a la dirección IP "+dirIP));
            }
            else{
            if(exito == 1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "La relación "+perfil+" a la dirección IP "+dirIP+" ya existe!"));
            }
            else{
                if(exito == 4){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "La dirección "+ dirIP+" ya tiene un perfil asignado")); 
                }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Hubo un error!"));
                }
            }
            }
            
            
        }
        }
    }
    
}
