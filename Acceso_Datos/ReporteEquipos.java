/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author daniel
 */
public class ReporteEquipos {
    private final Base_Datos DB = new Base_Datos();
    
    public List<RepoMonFiles> llenaRepoMonFiles(String direccionIP){
            try{
                List<RepoMonFiles> list = new ArrayList<RepoMonFiles>();
                Connection con;
                ResultSet rs;
                PreparedStatement ps;
                String query;
                
                con = DB.conecta();
                query = "Select * from Mon_files where agente = '"+direccionIP+"'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                while(rs.next())
                {
                list.add(new RepoMonFiles(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(7),rs.getString(6)));
                }       
                con.close();
            return list;
            }
            catch(Exception e){
            e.printStackTrace();
                return null;
            }
    }
    
    public List<RepoMonProc>llenaRepoMonProc(String direccionIP){
        try{
                List<RepoMonProc> list = new ArrayList<RepoMonProc>();
                Connection con;
                ResultSet rs;
                PreparedStatement ps;
                String query;
                
                con = DB.conecta();
                query = "Select * from Mon_Proc where agente = '"+direccionIP+"'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                while(rs.next())
                {
                list.add(new RepoMonProc(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }          
                con.close();
            return list;
            }
            catch(Exception e){
            e.printStackTrace();
                return null;
            }
    
    }
    
    public Map<String,String>llenaEquipos(){
        try{
                Map<String,String> list = new HashMap<String,String>();
                Connection con;
                ResultSet rs;
                PreparedStatement ps;
                String query;
                
                con = DB.conecta();
                query = "Select Direccion_IP from Equipos where verificado ='SI' and conectado ='SI'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                while(rs.next())
                {
                list.put(rs.getString(1),rs.getString(1));
                }            
                con.close();
            return list;
            }
            catch(Exception e){
            e.printStackTrace();
                return null;
            }
    
    }
    
    
    
}
