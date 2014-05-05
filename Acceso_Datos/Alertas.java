/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author DanielFlores
 */
public class Alertas {
    String nombre, correoElectronico, activa;
    Base_Datos DB = new Base_Datos();
    
    public Alertas(){}
    
    public Alertas(String nombre, String correoElectronico, String activa){
    this.nombre = nombre;
    this.correoElectronico = correoElectronico;
    this.activa = activa;
    }
    
    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}
    
    public String getCorreoElectronico(){return this.correoElectronico;}
    public void setCorreoElectronico(String correoElectronico){this.correoElectronico =  correoElectronico;}
    
    public String getActiva(){return this.activa;}
    public void setActiva(String activa){this.activa = activa;}               
    
    public int ModificaAlerta(String nombre, String direccionNueva){
    try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        query ="select count(*) from Alertas where nombre ='"+nombre+"'";
        con = DB.conecta();
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        
        rs.next();
        if(rs.getInt(1)==0){
            con.close();
        return 1;
        }
        else{
        ps.close();
        query = "select count(*) from Alertas where correo_electronico ='"+direccionNueva+"'";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        rs.next();
        if(rs.getInt(1)>0){
            con.close();
        return 2;
        }
        else{
            ps.close();
        query = "update Alertas set correo_electronico = '"+direccionNueva+"' where nombre ='"+nombre+"'";
        ps = con.prepareStatement(query);
        ps.execute(query);
        ps.close();
        con.close();
        return 0;
        }

        } 
    }
    catch(Exception e){
    e.printStackTrace();
    return 3;
    }
    }
    
}
