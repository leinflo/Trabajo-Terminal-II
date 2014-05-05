/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class Activa_Desactiva {
    
    Base_Datos DB = new Base_Datos();
    
    public Map<String,String> obtieneActivas(){
        try{
            Connection con;
            Map<String,String>  perfiles = new HashMap<String, String>(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "Select nombre from Alertas where activa=1";
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
    
    public Map<String,String> obtieneInactivas(){
        try{
            Connection con;
            Map<String,String>  perfiles = new HashMap<String, String>(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "Select nombre from Alertas where activa=0";
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            perfiles.put(rs.getString(1), rs.getString(1));
            }
            con.close();
            return perfiles;
        }
        catch(Exception e){
        e.printStackTrace();
        return null;
        }
    }
    
    public int ActivarDesactivar(List<String> selectedActivas,List<String>selectedInactivas){
        try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query,query2;
        query ="Update Alertas set activa = 1 where nombre='";
        query2 = "update Alertas set activa = 0 where nombre ='";
        con = DB.conecta();
        int numActivas, numInactivas,i,j;
        numActivas = selectedActivas.size();
        numInactivas = selectedInactivas.size();
        if(numActivas!=0  || numInactivas!=0){
            if(numActivas>0){
            for(i=0;i<numActivas;i++){
            query2 += selectedActivas.get(i)+"'";
            ps = con.prepareStatement(query2);
            ps.execute(query2);
            ps.close();
            }
            }
            else{
                if(numInactivas>0){
                for(i=0;i<numInactivas;i++){
                    query += selectedInactivas.get(i)+"'";
            ps = con.prepareStatement(query);
            ps.execute(query);
            ps.close();
                }
                }
                else{
                for(i=0;i<numActivas;i++){
                    query2 += selectedActivas.get(i)+"'";
            ps = con.prepareStatement(query2);
            ps.execute(query2);
            ps.close();
                }
                for(i=0;i<numInactivas;i++){
                query2 += selectedInactivas.get(i)+"'";
            ps = con.prepareStatement(query);
            ps.execute(query);
            ps.close();
                }
                }
            }
            con.close();
            return 0;
        }
        else{
        return 1;
        }
        }
        
        catch(Exception e){
        e.printStackTrace();
        return 2;
        }
        
        
        
        
        }
}
