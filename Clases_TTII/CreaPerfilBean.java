/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

/**
 *
 * @author DanielFlores
 */
  
import java.util.ArrayList;  
import java.util.List;  
import Acceso_Datos.Procesos; 
import Acceso_Datos.Rutas; 
import Acceso_Datos.Perfiles;
import Acceso_Datos.Base_Datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

public class CreaPerfilBean {  
      
        private DualListModel<String> proce;
        private DualListModel<String> rutas;
        public String nombre, nombre2,respuestaActiva, numHits;
        private Perfiles perfil;
        private List<Procesos> procesos;
        private List<Rutas> paths;
        private Base_Datos DB = new Base_Datos();
        private creaXML xml = new creaXML();        
              
    public CreaPerfilBean() {  
        //Players  
        
        procesos = new ArrayList<Procesos>();
        llenaProcesos(procesos);
        paths= new ArrayList<Rutas>();
        llenaPaths(paths);
        
        //System.out.print(procesos);
        
        Procesos proc = new Procesos();
        List<String> nombres = proc.obtieneProcesos();
        
        List<String> source ;  
        List<String> target = new ArrayList<String>();  
        
        source = nombres;
        
        System.out.println("Se obtuvieron los procesos");
  
        Rutas ruta = new Rutas();
        
        List<String> path = ruta.obtieneRutas();
        
        List<String> pathsource ;  
        List<String> pathtarget = new ArrayList<String>();
        
        pathsource = path;
        
        
        rutas = new DualListModel<String>(pathsource,pathtarget);
        proce = new DualListModel<String>(source, target);  
        perfil = new Perfiles();

    }  
      
    public DualListModel<String> getProce() {  
        return proce;  
    }  
    public void setProce(DualListModel<String> procesos) {  
        this.proce = procesos;  
    }  
    
    public DualListModel<String> getRutas() {  
        return rutas;  
    }  
    public void setRutas(DualListModel<String> rutas) {  
        this.rutas = rutas;  
    } 
    
    public void setRespuestaActiva(String respuestaActiva){ this.respuestaActiva = respuestaActiva;}
    
    public String getRespuestaActiva(){return respuestaActiva;}
    
    public void setNombre(String nombre){ this.nombre = nombre;}
    
    public String getNombre(){return nombre;}
    
    public void setNumHits(String numHits){ this.numHits = numHits;}
    
    public String getNumHits(){return numHits;}
    
    public void setNombre2(String nombre2){ this.nombre2 = nombre2;}
    
    public String getNombre2(){return nombre2;}
           
    public void crearPerfil(){
        int agrega_proc, agrega_paths,inserta_nombre;
        if(nombre.isEmpty()||this.respuestaActiva.isEmpty()||this.numHits.isEmpty()){
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se requiere de un nombre, Respuesta Activa y número de Hits"));
        
        }
        else{
            System.out.println(respuestaActiva);
            if(respuestaActiva.equals("SI")||respuestaActiva.equals("NO")){
        try{
           int uno=  Integer.parseInt(numHits);
        inserta_nombre = perfil.insertaNombrePerfil(nombre,respuestaActiva,numHits);
        
        System.out.print(inserta_nombre);
        
        if(inserta_nombre == 0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "Perfil: "+nombre+" guardado!"));
        
        List<String> procesosAgregar = this.proce.getTarget();
        System.out.print(procesosAgregar);
        
        List<String> rutasAgregar = this.rutas.getTarget();
        System.out.print(rutasAgregar);
        
        String name = this.nombre;
        System.out.print(name);
        
        
        agrega_proc = perfil.agregaProcesos(name,procesosAgregar);
        
        
        if(agrega_proc == 0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "Procesos almacenados!"));
        }
        else{
        if(agrega_proc == 1){
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "El perfil : "+name+" ya existe!"));
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Hubo un problema"));
        }
        }
        
        agrega_paths = perfil.agregaRutas(nombre, rutasAgregar);
        if(agrega_paths == 0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "Rutas almacenadas!"));
        }
        else{
        if(agrega_paths == 1){
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "El perfil : "+name+" ya existe!"));
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Hubo un problema"));
        }
        }
        xml.creaXML(nombre,this.respuestaActiva,this.numHits,rutasAgregar, procesosAgregar);
        
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Perfil: "+nombre+" ya existe!"));
        }
        }
        catch(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "El número de hits debe ser un número"));
        }
        
        }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "La respuesta Activa solo puede ser 'SI' o 'NO'"));
            }
        }
    
    }
    
    
    private void llenaProcesos(List<Procesos> list) {  
        try {                        
            Connection con;                        
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "select * from cat_procesos";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            System.out.println("antes del while");
            while(rs.next()){
                //System.out.println(rs.getInt(1)+" "+rs.getString(2));
            list.add(new Procesos(rs.getInt(1),rs.getString(2)));
            }
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
        }
        }
    
    private void llenaPaths(List<Rutas> list) {  
        try {            
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "select * from cat_rutas";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            System.out.println("antes del while");
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2));
            list.add(new Rutas(rs.getInt(1),rs.getString(2)));
            }
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
        }
        }
    
    
     public List<Procesos> getProcesos() {  
        return procesos;  
    } 
     
     public List<Rutas> getPaths() {  
        return paths;  
    }
     
    
    public void insertaNuevo(){
        int inserta;
        Procesos proc = new Procesos();
        if(nombre2.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "No debe estar vacio"));
        }
        else{
        inserta = proc.insertaProcesos(nombre2);
        
        if(inserta==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!", "Proceso: "+nombre2+" agregado!"));
        }
        else{if(inserta ==1){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Proceso: "+nombre2+" ya existe!"));
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Hubo un error!!"));
        }
        
        }
        }
    }
    
    public void insertaRuta(){
        int inserta;
        Rutas path = new Rutas();
        if(nombre2.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "No debe estar vacio"));
        }
        else{
        inserta = path.insertaRuta(nombre2);
        
        if(inserta==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!", "Proceso: "+nombre2+" agregado!"));
        }
        else{if(inserta ==1){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Proceso: "+nombre2+" ya existe!"));
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Hubo un error!!"));
        }
        
        }
        }
    }     
}
