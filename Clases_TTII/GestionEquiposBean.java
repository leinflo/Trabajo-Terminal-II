/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

import Acceso_Datos.Base_Datos;
import Acceso_Datos.Equipos;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author DanielFlores
 */
import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


public class GestionEquiposBean implements Serializable{
    
    String direccionIP;
    int id;
    Base_Datos DB = new Base_Datos();
    List<Equipos> list;
    ResultSet rs;
    
    
    private List<Equipos> equipos;
    
    public GestionEquiposBean(){
/*        HttpSession session = request.getSession(false);// don't create if it doesn't exist
if(session != null && !session.isNew()) {
    chain.doFilter(request, response);
} else {
    response.sendRedirect("/login.jsp");
}*/

        equipos = new ArrayList<Equipos>();
        llenaEquipos(equipos);
        
    }
    
    public String getDireccionIP(){return direccionIP;}
    
    public void setDireccionIP(String direccionIP){this.direccionIP=direccionIP;}
    
    public int getId(){return id;}
    
    public void setId(int id){this.id=id;}
    
    private void llenaEquipos(List<Equipos> list) {  
        try {            
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "select * from Equipos";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            System.out.println("antes del while");
            while(rs.next()){
                System.out.println(rs.getInt(1));
            list.add(new Equipos(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));//,rs.getString(6)));
            }
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
        }
        
    }
    public List<Equipos> getEquipos() {  
        return equipos;  
    } 
    
    
    public void registraNuevo(){
        try{
            DB = new Base_Datos();
            int ejecutado = 3, ipValida = 3;
          String dirIP;
          dirIP=this.getDireccionIP();
          String[] tokens = dirIP.split("\\."); 
            if (tokens.length != 4) {
                ipValida = 1;
            } else {
                for (String str : tokens) {
                    int i = Integer.parseInt(str);
                    if ((i < 0) || (i > 255)) {
                        ipValida = 1;
                        break;
                    } else {
                        ipValida = 0;
                    }
                }

            }
            System.out.println(ipValida);
            if(ipValida==0){
                System.out.println("Entra para agregar un equipo: " +direccionIP);
                ejecutado = DB.Agrega_Equipo(direccionIP);
            }
            else{
                
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Direccion IP invalida")); 
            
            }
        System.out.println("Dentro del Bean: "+direccionIP);
        
        System.out.println("Ejecutado: "+ ejecutado);
        
        if(ejecutado == 0){
        //return "exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "Direccion IP: "+dirIP+" guardada!"));
            //qFacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dirección IP " + dirIP + " guardada con éxito !")); 
        }
        else{
            if(ejecutado == 1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!","Dirección IP " + dirIP + " ya está registrada !"));
            }        
    else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Dirección IP " + dirIP + " No se registró !"));
            
            }
        //return "fracaso";
        }
        }
        catch(Exception e){
        e.printStackTrace();
        //return "fracaso";
        }
    }
    
    
    public void modifica(){
        try{
        int ejecutado=5,ipValida=3;
        DB = new Base_Datos();
        String dirIP;
          dirIP=this.getDireccionIP();
          String[] tokens = dirIP.split("\\."); 
            if (tokens.length != 4) {
                ipValida = 1;
            } else {
                for (String str : tokens) {
                    int i = Integer.parseInt(str);
                    if ((i < 0) || (i > 255)) {
                        ipValida = 1;
                        break;
                    } else {
                        ipValida = 0;
                    }
                }

            }
        
        if(ipValida==0){
        ejecutado = DB.Modifica_Equipo(direccionIP,id);
        }
        else{
                
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Direccion IP invalida")); 
            
            }
        
        if(ejecutado == 0){
        //return "exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Éxito!", "ID: "+id+" Actualizado!"));
            //qFacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dirección IP " + dirIP + " guardada con éxito !")); 
        }
        else{
            if(ejecutado == 1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!","ID: " + id + " no está registrado o la direccion IP "+dirIP +" ya existe!"));
            }        
    else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","ID " + id + " No se modificó!"));
            
            }
        //return "fracaso";
        
        }
        }
        catch(Exception e){
        e.printStackTrace();
        }
        
      }

    
    public void ModificaUsuario(ActionEvent event){
    try{
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect( "/ModificaEquipo.xhtml" );
}catch(  Exception e ){
    e.printStackTrace();
    System.out.println( "Me voy al carajo, no funciona esta redireccion" );
}
        
    }
    
    public void addInfo(ActionEvent actionEvent) {  
          
    } 
    
}
