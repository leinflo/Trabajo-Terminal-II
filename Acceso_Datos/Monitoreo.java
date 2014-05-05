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
public class Monitoreo {
    Base_Datos DB = new Base_Datos();
    
    public List<Mon_proc> MonitoreoProcesos(){
    try{
    List<Mon_proc> list= new ArrayList<Mon_proc>();
        Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query = "select * from Mon_Proc";
    con = DB.conecta();
    
    ps = con.prepareStatement(query);
    
    rs = ps.executeQuery(query);
    
    while(rs.next()){
        
        list.add(new Mon_proc(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
    
    }
    return list;
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    public List<Mon_files> MonitoreoRutas(){
    try{
    List<Mon_files> list= new ArrayList<Mon_files>();
        Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query = "select * from Mon_files";
    con = DB.conecta();
    
    ps = con.prepareStatement(query);
    
    rs = ps.executeQuery(query);
    
    while(rs.next()){
        
        list.add(new Mon_files(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
    
    }
    return list;
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
        
    }
    
    
}
