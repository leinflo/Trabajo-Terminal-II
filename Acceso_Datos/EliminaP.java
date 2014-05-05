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
public class EliminaP {
    
    Base_Datos DB = new Base_Datos();
    
    public int Elimina(int id){
       try{
        Connection con;
       PreparedStatement ps;
       String query;
       ResultSet rs;
       con = DB.conecta();
       query = "Select count(*) from cat_perfiles where id_perfil ="+id;
       ps = con.prepareStatement(query);
       rs = ps.executeQuery(query);
       rs.next();
       if(rs.getInt(1)==0){
       return 1;
       }
       else{
       ps.close();
       query ="Delete from cat_perfiles where id_perfil = " + id;
       ps  = con.prepareStatement(query);
       ps.execute(query);
       con.close();
       return 0;
       }
       }
       catch(Exception e){
       e.printStackTrace();
       return 3;
       }
    
    }
    
    public List<EliminaPerfil> LlenaPerfiles(){
        try{
            List<EliminaPerfil> list = new ArrayList<EliminaPerfil>();
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        con = DB.conecta();
        
        query = "select * from cat_perfiles";
        
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        
        while(rs.next()){
           System.out.println(rs.getInt(1));
            list.add(new EliminaPerfil(rs.getString(2),rs.getInt(1)));
            
        }
        return list;
        }
        catch(Exception e){
            e.printStackTrace();           
            return null;
        }
        
        }
    
}
