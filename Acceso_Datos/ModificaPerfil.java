/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author DanielFlores
 */
public class ModificaPerfil {
    private String nombre;
    private List<String> selectedprocesosActuales,selectedrutasActuales,selectedprocesosFaltantes,selectedrutasFaltantes,actualProc,actualPath;
    private Map<String,String> procesosActuales, rutasActuales,procesosFaltantes,rutasFaltantes;
    private Acceso_Datos.Base_Datos DB = new Acceso_Datos.Base_Datos();
    private List<Modifica> modifica;
    
    public ModificaPerfil(){}
    
    
    public ModificaPerfil(String nombre,Map<String,String> procesos_actuales,Map<String,String> rutas_actuales,Map<String,String>procesosFaltantes,Map<String,String>rutasFaltantes,List<String> actualProc,List<String>actualPath){
        this.nombre =nombre;
        this.procesosActuales=procesos_actuales;
        this.rutasActuales=rutas_actuales;
        this.procesosFaltantes = procesosFaltantes;
        this.rutasFaltantes = rutasFaltantes;        
        this.actualProc = actualProc;
        this.actualPath =actualPath;
    }
    
    
    public void setNombre(String nombre){this.nombre = nombre;}
    
    public String getNombre(){return nombre;}
    
    public void setProcesosActuales(Map<String,String> procesosActuales){this.procesosActuales = procesosActuales;}
    
    public Map<String,String> getProcesosActuales(){return procesosActuales;}
    
    public void setRutasActuales(Map<String,String> rutasActuales){this.rutasActuales = rutasActuales;}
    
    public Map<String,String> getRutasActuales(){return rutasActuales;}
    
    public void setProcesosFaltantes(Map<String,String> procesosFaltantes){this.procesosFaltantes = procesosFaltantes;}
    
    public Map<String,String> getProcesosFaltantes(){return procesosFaltantes;}
    
    public void setRutasFaltantes(Map<String,String> rutasFaltantes){this.rutasFaltantes = rutasFaltantes;}
    
    public Map<String,String> getRutasFaltantes(){return rutasFaltantes;}
    
    public void setActualProc(List<String> actualProc){this.actualProc = actualProc;}
    
    public List<String> getActualProc(){return actualProc;}
    
    public void setActualPathMap(List<String> actualPath){this.actualPath = actualPath;}
    
    public List<String> getActualPath(){return actualPath;}
    
    public List<String> getSelectedprocesosActuales() {  
        return selectedprocesosActuales;  
    }  
    public void setSelectedprocesosActuales(List<String> selectedprocesosActuales) {  
        this.selectedprocesosActuales = selectedprocesosActuales;  
    } 
    
    public List<String> getSelectedprocesosFaltantes() {  
        return selectedprocesosFaltantes;  
    }  
    public void setSelectedprocesosFaltantes(List<String> selectedprocesosFaltantes) {  
        this.selectedprocesosFaltantes = selectedprocesosFaltantes;  
    }
    
    public List<String> getSelectedrutasActuales() {  
        return selectedrutasActuales;  
    }  
    public void setSelectedrutasActuales(List<String> selectedrutasActuales) {  
        this.selectedrutasActuales = selectedrutasActuales;  
    }
    
    public List<String> getSelectedrutasFaltantes() {  
        return selectedrutasFaltantes;  
    }  
    public void setSelectedrutasFaltantes(List<String> selectedrutasFaltantes) {  
        this.selectedrutasFaltantes = selectedrutasFaltantes;  
    }

    
    
    public Map<String,String> obtienePerfiles(){
        try{
            Acceso_Datos.Base_Datos DB = new Acceso_Datos.Base_Datos();
            Connection con = DB.conecta();
            PreparedStatement ps;
            ResultSet rs;
            Map<String,String>  perfiles = new HashMap<String, String>(); 
            String query = "Select perfil from cat_perfiles";
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            perfiles.put(rs.getString(1), rs.getString(1));
            }
            return perfiles;
        }
        catch(Exception e){
        e.printStackTrace();
        return null;
        }
    }
    
    public Map<String,String> obtieneProcesos(String perfil){
            try{
            Acceso_Datos.Base_Datos DB = new Acceso_Datos.Base_Datos();
            Connection con = DB.conecta();
            PreparedStatement ps;
            ResultSet rs;
            Map<String,String> proc = new HashMap<String,String>();
            String query;
            query = "select cpr.nombre from cat_perfiles cp inner join perfil_procesos pp on cp.id_perfil = pp.id_perfil inner join  cat_procesos cpr on cpr.id_proceso = pp.id_proceso where cp.perfil='"+perfil+"'";
            
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            proc.put(rs.getString(1),rs.getString(1));
            }
            ps.close();
            con.close();
            return proc;
            }
            catch(Exception e){
            e.printStackTrace();
            return null;
            }
    }
    
    public int ModificaProcesosAgregar(){ 
        try{
        Iterator<String> iteraProc = this.selectedprocesosFaltantes.iterator();
        String proceso, query;
        int id_perfil, id_proceso;
        Connection con = DB.conecta();          
        query = "select id_perfil from cat_perfiles where perfil='"+nombre+"'";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);
        rs.next();
        id_perfil = rs.getInt(1); 
        ps.close();
        
        if(this.selectedprocesosFaltantes.size()>0){
            while(iteraProc.hasNext()){
                
                proceso = iteraProc.next();
                query = "select id_proceso from cat_procesos where nombre = '"+proceso+"'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                id_proceso = rs.getInt(1);
                ps.close();
                query = "insert into perfil_procesos values (NULL,"+id_perfil+","+id_proceso+")";
                System.out.println(query);  
                ps = con.prepareStatement(query);
                ps.execute();
                ps.close();
            //    con.close();
            
            }
            con.close();
            return 0;
        }
        else{
        con.close();
        return 0;
        }
        }
        catch(Exception e){
        e.printStackTrace();
        return 1;
        }                          
    }
    
    public int ModificaProcesosEliminar(){ 
        try{
        Iterator<String> iteraProc = this.selectedprocesosActuales.iterator();
        String proceso, query;
        Connection con = DB.conecta();
        PreparedStatement ps;       
        
        if(this.selectedprocesosActuales.size()>0){
            while(iteraProc.hasNext()){
                proceso = iteraProc.next();
                query = "delete from perfil_procesos where id_proceso in";
                query =query+"(select id_proceso from cat_procesos where nombre ='"+proceso+"')"; 
                query =query +"and id_perfil in (select id_perfil from cat_perfiles where perfil='"+this.nombre+"')";
                System.out.println(query);  
                ps = con.prepareStatement(query);
                ps.execute();
          //      ps.close();
            }
            con.close();
            return 0;
        }
        else{
            con.close();
        return 0;
        }        
        }
        catch(Exception e){
        e.printStackTrace();
        return 1;
        }
        
               
    }
    
    
    public int ModificaRutasAgregar(){
    try{
        Iterator<String> iteraPath = this.selectedrutasFaltantes.iterator();
        String proceso, query;
        int id_perfil, id_ruta;
        Connection con = DB.conecta();          
        query = "select id_perfil from cat_perfiles where perfil='"+nombre+"'";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);
        rs.next();
        id_perfil = rs.getInt(1); 
        ps.close();
        
        if(this.selectedrutasFaltantes.size()>0){
            while(iteraPath.hasNext()){
                
                proceso = iteraPath.next();
                query = "select id_ruta from cat_rutas where ruta = '"+proceso+"'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                id_ruta = rs.getInt(1);
                ps.close();
                query = "insert into perfil_rutas values (NULL,"+id_perfil+","+id_ruta+")";
                System.out.println(query);  
                ps = con.prepareStatement(query);
                ps.execute();
                ps.close();
        //        con.close();
            
            }
            con.close();
            return 0;
        }
        else{
        con.close();
        return 0;
        }
        }
        catch(Exception e){
        e.printStackTrace();
        return 1;
        }
    }
    
    public int ModificRutasEliminar(){
    try{
        Iterator<String> iteraPath = this.selectedrutasActuales.iterator();
        String ruta, query;
        Connection con = DB.conecta();
        PreparedStatement ps;       
        
        if(this.selectedrutasActuales.size()>0){
            while(iteraPath.hasNext()){
                ruta = iteraPath.next();
                query = "delete from perfil_rutas where id_ruta in";
                query =query+"(select id_ruta from cat_rutas where ruta ='"+ruta+"')"; 
                query =query +"and id_perfil in (select id_perfil from cat_perfiles where perfil='"+this.nombre+"')";
                System.out.println(query);  
                ps = con.prepareStatement(query);
                ps.execute();
                ps.close();
                //con.close();
            }
            con.close();
            return 0;
        }
        else{
            con.close();
        return 0;
        }        
        }
        catch(Exception e){
        e.printStackTrace();
        return 1;
        }
    }
    
    public void ModificaPerfil(){
    int exitoAgregaProc,exitoEliminaProc, exitoAgregaPath, exitoEliminaPath;
    
    exitoAgregaProc = this.ModificaProcesosAgregar();
    exitoEliminaProc = this.ModificaProcesosEliminar();
    exitoAgregaPath = this.ModificaRutasAgregar();
    exitoEliminaPath = this.ModificRutasEliminar();
   
    if(exitoAgregaProc == 0 && exitoEliminaProc ==0 && exitoAgregaPath ==0&& exitoEliminaPath==0){
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!", "El perfil "+ nombre+" fue modificado!")); 
    
    }
    else{
       
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "El perfil "+ nombre+"no pudo modificarse")); 
    }
    
    
    
    }
    
}
