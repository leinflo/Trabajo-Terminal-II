/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author DanielFlores
 */
public class Rutas {

    public List<String> path;
    public int id;
    public String rutas;
    private Base_Datos DB = new Base_Datos();
    
    public Rutas(){}
    
    public Rutas(int id,String rutas ){
    this.id = id;
    this.rutas = rutas;
    }
    
    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}
    
    public String getRutas(){return this.rutas;}
    public void setRutas(String rutas){this.rutas = rutas;}
    
    
    
    
    public List<String> obtieneRutas(){
try{
        String auxiliar;
        List<String> lista = new ArrayList<String>();
            Connection con;
            //             
            PreparedStatement ps;
            ResultSet rs;            
            con = DB.conecta();
            String query = "select ruta from cat_rutas";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            //System.out.println(rs.getString("nombre"));
                auxiliar =rs.getString("ruta");
                lista.add(auxiliar);                
            }
            //con.close();
            return lista;
            
}

catch(Exception e){
    e.printStackTrace();
    return null;
}
        
    }
    
public int insertaRuta(String ruta){
 
    try{
            Connection con;// 
            PreparedStatement ps;
            ResultSet rs;
            
            con = DB.conecta();
            
            String query = "Select count(*) from cat_rutas where ruta ='"+ruta+"'";
            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            if(rs.getInt(1) == 0){
                ps.close();
                query = "INSERT INTO cat_rutas VALUES (NULL,'"+ruta+"')";
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
    return 2;
}
    
    
}
    
    
}
