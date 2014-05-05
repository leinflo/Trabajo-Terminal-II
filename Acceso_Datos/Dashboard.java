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
public class Dashboard {
    Base_Datos DB = new Base_Datos();
    
    
    
    
    public List<String> LlenaTopTenFiles(){
    try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        con = DB.conecta();
        
        List<String> direcciones = new ArrayList<String>();  
        query = "select  agente, count(*) as cuenta from Mon_files mf group by agente order by cuenta desc";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        //System.out.println(query);
        int i=0;
        while(rs.next()&& i<10){
            i++;
            direcciones.add(rs.getString(1));
            //System.out.println(rs.getString(1));     
        }
        con.close();
        return direcciones;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;

    }
    }
    
    public List<Integer> LlenaTopTenFilesCuenta(){
    try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        con = DB.conecta();
        int i=0;
        
        List<Integer> direcciones = new ArrayList<Integer>();  
        query = "select  agente, count(*) as cuenta from Mon_files mf group by agente order by cuenta desc";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        //System.out.println(query);
                       
        while(rs.next()&&i<10){
            direcciones.add(rs.getInt(2));
            //System.out.println(rs.getInt(2));
            //System.out.println(i);
            i++;
        }
        con.close();
        //System.out.println("regresa lista");
        return direcciones;
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("regresa null");
            return null;

    }
    }
    
    public List<String> LlenaTopTenProc(){
    try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        con = DB.conecta();
        
        List<String> direcciones = new ArrayList<String>();  
        query = "select  agente, count(*) as cuenta from Mon_Proc mf group by agente order by cuenta desc";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        //System.out.println(query);
        int i=0;
        while(rs.next()&& i<10){
            direcciones.add(rs.getString(1));
            //System.out.println(rs.getString(1));
        i++;
        }
        con.close();
        return direcciones;
        }
        catch(Exception e){
            return null;

    }
    }
    public List<Integer> LlenaTopTenProcCuenta(){
    try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        con = DB.conecta();
        
        List<Integer> direcciones = new ArrayList<Integer>();  
        query = "select  agente, count(*) as cuenta from Mon_Proc mf group by agente order by cuenta desc";
        //System.out.println(query);
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        
        int i=0;
        while(rs.next()&& i<10){
            direcciones.add(rs.getInt(2));
            i++;
        
        }
        con.close();
        return direcciones;
        }
        catch(Exception e){
            return null;

    }
    }
    
}
