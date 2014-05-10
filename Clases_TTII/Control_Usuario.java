/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

import Acceso_Datos.Base_Datos;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.sql.*;
    
/**
 *
 * @author DanielFlores
 */
//@Resource(name="jdbc/TTII")
@SuppressWarnings("serial")
public class Control_Usuario {
    
    //private final HttpServletRequest httpServletRequest;
      //private final FacesContext faceContext;
public LoginBean usuario;
public String user, contrasena;
String exito;
private DataSource ds;
private Base_Datos DB = new Base_Datos();
    
    public Control_Usuario(){
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        /*usuario = new LoginBean();*/
    }    
    
    public LoginBean getUsuario(){
        return usuario;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    
    public void setUsuario(LoginBean usuario){
        this.usuario = usuario;
    }
    
    public String getExito(){
        return exito;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getContrasena(){
        return contrasena;
    }
    
    public void setExito(String exito){
        
        this.exito = exito;
    
    }
    
    public String verificaUser() {
        try {
            PreparedStatement ps;
            ResultSet rs;
            String query;
            Connection con;
            con = DB.conecta();
            query = "select count(*) as existe from usuarios where correo_electronico='"+user+"' and password = '"+contrasena+"'";
            ps = con.prepareStatement(query);
            
            rs = ps.executeQuery(query);
            //System.out.println("antes del while");
            //System.out.println(user);

            rs.next();
            //System.out.println(rs.getInt("existe"));
            if(rs.getInt("existe") == 1){
                //System.out.println("regresa exito");
                httpServletRequest.getSession().setAttribute("sessionUsuario",user);
                      
                ps.close();
                con.close();
                // httpServletRequest.getSession().setAttribute("sessionUsuario", user);
                return "exito";
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!","¡Usuario o contraseña invalidos!"));
                    exito= "fracaso";
                    //System.out.println("regresa fracaso");
                    ps.close();
                    con.close();
                return "fracaso";
            }
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("Not able to connect");
            exito = "no fracaso";
            System.out.println("regresa no fracaso");
            //return "no fracaso";
            return "fracaso";
        }
    }
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    public void redirecciona(){
            
           try{
            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            faceContext =  FacesContext.getCurrentInstance();
            httpServletRequest =(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("sessionUsuario")==null){
                ec.redirect(ec.getRequestContextPath()+"/faces/index.xhtml");
            }
            else{
            user = httpServletRequest.getSession().getAttribute("sessionUsuario").toString();
            }
           }
           catch(Exception e){
            e.printStackTrace();  
        }
        }
    
    public void logout(ActionEvent ae)throws IOException{
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath()+"/faces/index.xhtml");
                
    }
}