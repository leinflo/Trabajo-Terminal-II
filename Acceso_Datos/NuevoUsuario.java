/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DanielFlores
 */
public class NuevoUsuario {
    Base_Datos DB = new Base_Datos();
    public NuevoUsuario(){
    
    }
    
    
    public int AgregaUsuario(String nombre, String apPaterno, String apMaterno, String correo, String password){
        try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        System.out.println("entra al metodo agrefa usuario");
        con = DB.conecta();
        query = "select count(*) from usuarios where correo_electronico = '"+correo+"'";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        rs.next();
        if(rs.getInt(1)==0){
            ps.close();
            query = "insert into usuarios values (NULL,'"+nombre+"','"+apPaterno+"','"+apMaterno+"','"+correo+"','"+password+"')";
            ps = con.prepareStatement(query);
            ps.execute(query);
            con.close();
        return 0;
        }
        else{
        con.close();
        return 1;
        }             
        }
        catch(Exception e){
        e.printStackTrace();
        return 3;
        }
    }
    
    public int ElminaUsuario(String correo){
        try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        System.out.println("entra al metodo elimina usuario");
        con = DB.conecta();
        query = "select count(*) from usuarios where correo_electronico = '"+correo+"'";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        rs.next();
        if(rs.getInt(1)==1){
            ps.close();
            query = "delete from usuarios where correo_electronico ='"+correo+"'";
            ps = con.prepareStatement(query);
            ps.execute(query);
            con.close();
        return 0;
        }
        else{
        con.close();
        return 1;
        }             
        }
        catch(Exception e){
        e.printStackTrace();
        return 3;
        }
    }
    
    public List<Usuario> llenaUsuarios(){
    try{
        Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query = "select * from usuarios";
    con = DB.conecta();
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    
            List<Usuario> list = new ArrayList<Usuario>();
    while(rs.next()){
        list.add(new Usuario(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
    }            
            return list;    
    }
    catch(Exception e){
        e.printStackTrace();
        return null;    
    }       
    }
    
}
