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
public class Procesos {

    public List<String> procesos;
    public String nombre;
    public int id;
    private Base_Datos DB = new Base_Datos();
    
    
    public Procesos(){}
    
    public Procesos(int id,String nombre){     
    this.id = id;
    this.nombre = nombre;
    }
    
    public String getNombre(){ return nombre;}
    public void setNombre(String nombre){ this.nombre = nombre;}
    
    public int getId(){return id;}
    public void setId(int id){ this.id = id;}
    
    
    public List<String> obtieneProcesos(){
try{
        String auxiliar;
        List<String> lista = new ArrayList<String>();
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            
            String query = "select nombre from cat_procesos";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
            //System.out.println(rs.getString("nombre"));
                auxiliar =rs.getString("nombre");
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
    
public int insertaProcesos(String nombre){
 
    try{
            Connection con;
            PreparedStatement ps;
            ResultSet rs;            
            con = DB.conecta();            
            String query="select count(*) from cat_procesos where nombre ='"+nombre+"'";
              // Declaramos el Iterador e imprimimos los Elementos del ArrayList            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);            
            rs.next();            
            if(rs.getInt(1)==0){
                        ps.close();
            query = "insert into cat_procesos values(NULL,'"+nombre+"')";//query para validar la existencia del equipo
            System.out.println(query);
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
