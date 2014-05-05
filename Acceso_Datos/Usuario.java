/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

/**
 *
 * @author DanielFlores
 */
public class Usuario {
    String nombre, apPaterno, apMaterno, correoElectronico,password;
    
    public Usuario(){}
    public Usuario(String nombre,String apPaterno,String apMaterno, String correoElectronico){
    this.nombre =nombre;
    this.apPaterno =apPaterno; 
    this.apMaterno = apMaterno;
    this.correoElectronico = correoElectronico;
    }
    
    public String getNombre(){return this.nombre;}
    public String getApPAterno(){return this.apPaterno;}
    public String getApMaterno(){return this.apMaterno;}
    public String getCorreoElectronico(){return this.correoElectronico;}
    public String getPassword(){return this.password;}
    
    
    
}
