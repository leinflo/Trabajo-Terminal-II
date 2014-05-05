/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

/**
 *
 * @author DanielFlores
 */
import Acceso_Datos.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
public class NuevoUsuarioBean {
    NuevoUsuario user = new NuevoUsuario();
    List<Usuario> usuario;
    String nombre, apPaterno, apMaterno, correoElectronico,password; 
    
    
    public NuevoUsuarioBean(){
        usuario = user.llenaUsuarios();
    }
    
    public List<Usuario> getUsuario(){
    return this.usuario;
    }
    public String getNombre(){return this.nombre;}
    public String getApPaterno(){return this.apPaterno;}
    public String getApMaterno(){return this.apMaterno;}
    public String getCorreoElectronico(){return this.correoElectronico;}
    public String getPassword(){return this.password;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setApPAterno(String apPaterno){ this.apPaterno = apPaterno;}
    public void setApMaterno(String apMaterno){ this.apMaterno = apMaterno;}
    public void setCorreoElectronico(String correoElectronico){this.correoElectronico= correoElectronico;}
    public void setPassword(String password){this.password = password;}
    
    public void Agregar(){
        int exito;
        if(!nombre.isEmpty()&& !apPaterno.isEmpty()&& !apMaterno.isEmpty()&&!correoElectronico.isEmpty()&&!password.isEmpty()){
        System.out.println("entra al metodo");
        exito = user.AgregaUsuario(nombre, apPaterno, apMaterno, correoElectronico, password);
        if(exito ==0){
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!", "Se agregó el usuario "+correoElectronico));
        }
        else{
        if(exito==1){
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "El usuario "+correoElectronico+" ya existe!"));
        }
        else{
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Error en la base de datos"));
        }
        }
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!", "Todos los campos son requeridos"));
        }
        
    }
    
}
