/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanielFlores
 */
public class Perfiles {
    
    List<String> perfiles;
    private Base_Datos DB = new Base_Datos();
    
    private List<String> procesos;
    private List<String> rutas;
    
    
    public int insertaNombrePerfil(String nombre,String respuestaActiva,String numhits){
 
    try{
        int respActiva,hits;
        if(respuestaActiva == "NO"){
            respActiva = 0;
        
        }
        else{
        respActiva=1;
        }
        hits = Integer.parseInt(numhits);
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query="Select count(*) from cat_perfiles where perfil ='"+nombre+"'";
              // Declaramos el Iterador e imprimimos los Elementos del ArrayList
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            if(rs.getInt(1)== 0){
                ps.close();
                query = "INSERT INTO cat_perfiles VALUES (NULL,'"+nombre+"',"+respActiva+","+hits+")";
                System.out.println(query);
                ps = con.prepareStatement(query);
                ps.execute(query);
                ps.close();
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
    
    public List<String> obtienePerfiles(){
    try{
            perfiles = new ArrayList<String>();
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();            
            String query="Select * from cat_perfiles";
              // Declaramos el Iterador e imprimimos los Elementos del ArrayList
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
                
            perfiles.add(rs.getString(2));    
            
            }
            
            con.close();
            return perfiles;
            
            
    }
    catch(Exception e){
        e.printStackTrace();
        return null;
    }           
        }
    
    public int agregaProcesos(String nombrePerfil, List<String> procesos){
        try{
            System.out.println(nombrePerfil);
            Connection con;
            int idPerfil;
            List<Integer> idProcesos = new ArrayList<Integer>();
            Iterator<String>  iteratorProcesos= procesos.iterator();
            PreparedStatement ps;
            ResultSet rs;
            
            con = DB.conecta();
            
            String query = "Select count(*) from cat_perfiles where perfil = '"+nombrePerfil+"'";
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            if(rs.getInt(1)==1){
                ps.close();
                query = "Select * from cat_perfiles where perfil = '"+nombrePerfil+"'";
                 ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                idPerfil = rs.getInt(1);
                ps.close();
                while (iteratorProcesos.hasNext()) {
                    query = "select * from cat_procesos where nombre='" + iteratorProcesos.next() + "'";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery(query);
                    rs.next();
                    idProcesos.add(rs.getInt(1));
                    ps.close();
                    
                }
                Iterator<Integer>  iteratorid = idProcesos.iterator();
                
                while(iteratorid.hasNext()){
                
                    query = "INSERT INTO perfil_procesos values(NULL,"+idPerfil+","+iteratorid.next()+")";
                    ps = con.prepareStatement(query);
                    ps.execute(query);
                    ps.close();
                }
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
    
    public int agregaRutas(String nombrePerfil, List<String> rutas){
        try{
            Connection con;
            int idPerfil;
            List<Integer> idRutas = new ArrayList<Integer>();
            Iterator<String>  iteratorRutas= rutas.iterator(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String query = "Select count(*) from cat_perfiles where perfil = '"+nombrePerfil+"'";
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            if(rs.getInt(1)==1){
                ps.close();
                query = "Select * from cat_perfiles where perfil = '"+nombrePerfil+"'";
                 ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                idPerfil = rs.getInt(1);
                ps.close();
                while (iteratorRutas.hasNext()) {
                    query = "select * from cat_rutas where ruta='" + iteratorRutas.next() + "'";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery(query);
                    rs.next();
                    idRutas.add(rs.getInt(1));
                    ps.close();
                    
                }
                Iterator<Integer>  iteratorid = idRutas.iterator();
                
                while(iteratorid.hasNext()){
                
                    query = "INSERT INTO perfil_rutas values(NULL,"+idPerfil+","+iteratorid.next()+")";
                    ps = con.prepareStatement(query);
                    ps.execute(query);
                    ps.close();
                }
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
    
    public Map<String,String> obtieneProcesosActuales(String nombre){
    try{
        Map<String,String> procesosActuales = new HashMap<String,String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select cpr.nombre from cat_perfiles cp ";
    query = query + "inner join perfil_procesos pp "; 
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query +"inner join cat_procesos cpr ";
    query = query +"on cpr.id_proceso = pp.id_proceso ";
    query = query +"where perfil ='"+nombre+"'";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        procesosActuales.put(rs.getString(1),rs.getString(1));
    }
    return procesosActuales;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    public Map<String,String> obtieneProcesosFaltantes(String nombre){
    try{
        Map<String,String> procesosFaltantes = new HashMap<String,String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select nombre from cat_procesos where nombre not in(select cpr.nombre from cat_perfiles cp ";
    query = query + "inner join perfil_procesos pp ";
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query + "inner join cat_procesos cpr ";
    query = query + "on cpr.id_proceso = pp.id_proceso ";
    query = query + "where perfil ='"+nombre+"')";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        procesosFaltantes.put(rs.getString(1),rs.getString(1));
    }
    return procesosFaltantes;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    
    public Map<String,String> obtieneRutasActuales(String nombre){
    try{
        Map<String,String> procesosActuales = new HashMap<String,String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select cpr.ruta from cat_perfiles cp ";
    query = query + "inner join perfil_rutas pp "; 
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query +"inner join cat_rutas cpr ";
    query = query +"on cpr.id_ruta = pp.id_ruta ";
    query = query +"where perfil ='"+nombre+"'";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        procesosActuales.put(rs.getString(1),rs.getString(1));
    }
    return procesosActuales;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    public Map<String,String> obtieneRutasFaltantes(String nombre){
    try{
        Map<String,String> procesosFaltantes = new HashMap<String,String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select ruta from cat_rutas where ruta not in(select cpr.ruta from cat_perfiles cp ";
    query = query + "inner join perfil_rutas pp ";
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query + "inner join cat_rutas cpr ";
    query = query + "on cpr.id_ruta = pp.id_ruta ";
    query = query + "where perfil ='"+nombre+"')";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        procesosFaltantes.put(rs.getString(1),rs.getString(1));
    }
    return procesosFaltantes;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    public List<String> obtieneRutas(String nombre){
    try{
        List<String> rutasActuales = new ArrayList<String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select cpr.ruta from cat_perfiles cp ";
    query = query + "inner join perfil_rutas pp "; 
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query +"inner join cat_rutas cpr ";
    query = query +"on cpr.id_ruta = pp.id_ruta ";
    query = query +"where perfil ='"+nombre+"'";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        rutasActuales.add(rs.getString(1));
    }
    return rutasActuales;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
    
    public List<String> obtieneProcesos(String nombre){
    try{
        List<String> procesosActuales = new ArrayList<String>();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String query;
    con = DB.conecta();
    query = "select cpr.nombre from cat_perfiles cp ";
    query = query + "inner join perfil_procesos pp "; 
    query = query + "on pp.id_perfil = cp.id_perfil ";
    query = query +"inner join cat_procesos cpr ";
    query = query +"on cpr.id_proceso = pp.id_proceso ";
    query = query +"where perfil ='"+nombre+"'";
    ps = con.prepareStatement(query);
    rs = ps.executeQuery(query);
    while(rs.next()){
        procesosActuales.add(rs.getString(1));
    }
    return procesosActuales;
    
    }
    catch(Exception e){
    e.printStackTrace();
    return null;
    }
    }
    
}
