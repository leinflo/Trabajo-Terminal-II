/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DanielFlores
 */
public class AsignaPerfil {
    public String equipo, perfil;
    private Base_Datos DB = new Base_Datos();
    
    public AsignaPerfil(){}
    
    public AsignaPerfil(String equipo, String perfil){
    this.equipo = equipo;
    this.perfil = perfil;
    }
    
    public String getEquipo(){return equipo;}
    public void setEquipo(String equipo){this.equipo = equipo;}
    
    public String getPerfil(){return perfil;}
    public void setPerfil(String perfil){this.perfil = perfil;}
    
    public Map<String,String> obtieneDireccionesIP(){
        try{
            Connection con;
            Map<String,String>  direcciones = new HashMap<String, String>(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            
            String query = "Select Direccion_IP from Equipos";
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            direcciones.put(rs.getString(1), rs.getString(1));
            }
            return direcciones;
        }
        catch(Exception e){
        e.printStackTrace();
        return null;
        }
        
    }
    
    public Map<String,String> obtienePerfiles(){
        try{
            Connection con;
            Map<String,String>  perfiles = new HashMap<String, String>(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "Select perfil from cat_perfiles";
            //System.out.println(query);
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
    
    public int asignaProfile(String direccionIP,String perfil){
        
         try{
             int id_equipo, id_perfil;
            Connection con;

            PreparedStatement ps;
            ResultSet rs;

            con = DB.conecta();
            
            String query = "select count(*) from Equipos e inner join perfil_equipo pe on e.id_equipo = pe.id_equipo inner join cat_perfiles cp on pe.id_perfil = cp.id_perfil where e.Direccion_IP='"+direccionIP+"' and cp.perfil='"+perfil+"';";
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            //System.out.print(rs.getInt(1));
            if(rs.getInt(1)==0){
                ps.close();
                query="select e.id_equipo from Equipos e where e.Direccion_IP='"+direccionIP+"'";
                ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
                id_equipo = rs.getInt(1);
                ps.close();
                query ="select count(*) from perfil_equipo where id_equipo = "+id_equipo;
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                if(rs.getInt(1)>0){
                    return 4;
                }
                
                query = "select cp.id_perfil from cat_perfiles cp where cp.perfil='"+perfil+"'";
                //System.out.println(query);                
                ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            
            id_perfil = rs.getInt(1);
            ps.close();
            query = "insert into perfil_equipo values (NULL,"+id_perfil+","+id_equipo+");";
            ps = con.prepareStatement(query);
            ps.execute(query);
            ps.close();
            con.close();
            return 0;
            }
            else{
            if(rs.getInt(1)>0){
                con.close();
                return 1;
            }
            else{
            return 3;
            }
            }
            
        }
        catch(Exception e){
        e.printStackTrace();
        return 2;
        }
    
    }
}
