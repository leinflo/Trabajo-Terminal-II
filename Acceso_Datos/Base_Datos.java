/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

/**
 *
 * @author DanielFlores
 */
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name="Base_datos")
@RequestScoped
public class Base_Datos {

public Connection open() {
        try {            
            String userName = "root";
            String password = "d4n13l.f";
            Connection con;
            // 
            String url = "jdbc:mysql://localhost:3306/Trabajo_Terminal_II_v3?zeroDateTimeBehavior=convertToNull";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, userName, password);
            return con;
        } catch (Exception e) {
            System.out.println("Not able to connect");
            return null;
        }
    }

public int Agrega_Equipo(String direccionIP){
try {            
            System.out.println("Entra a Agregar equipo");
            Connection con;
            //          
            PreparedStatement ps;
            ResultSet rs;

            con = this.conecta();
            
            String query = "select count(*) as existe from Equipos where Direccion_IP = '"+direccionIP+"'";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
             System.out.println("Existe: "+rs.getInt("existe"));
            if(rs.getInt("existe")==0){
            ps.close();    
            query = "insert into Equipos values(NULL,'" + direccionIP + "',SHA('"+direccionIP+"'),'NO','NO')";//query para insertar al equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            ps.execute(query);
            //ps.executeUpdate(query);
            
            System.out.println("Ejecutó insert");
            
            ps.close();
            return 0;
            
            }
            else{
            return 1;
            }
            
    
                 } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
            return 2;
        }


}

public int Modifica_Equipo(String direccionIP,int ID){
try {            

            Connection con;
            // 
            PreparedStatement ps;
            ResultSet rs;

            
            con = this.conecta();
            String query = "select count(*) as existe from Equipos where ID_equipo = " + ID ;//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            if(rs.getInt("existe")==1){
                query =  "Select count(*) from Equipos where direccion_IP='"+direccionIP+"'";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                if(rs.getInt(1)==0){
                
            ps.close();    
            query = "Update  Equipos set Direccion_IP ='"+direccionIP+"', digesto_de_verificacion =SHA('"+direccionIP+"') where ID_equipo ="+ID;//values(NULL,'" + direccionIP + "',SHA('"+direccionIP+"'),'NO','NO','NO')";//query para insertar al equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            ps.executeUpdate(query);
            ps.close();
            return 0;
            }
                else{
                return 1;
                }
            
            }
            else{
                if(rs.getInt("existe")<1){
                return 1;
                }
            return 3;
            }
                 } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
            return 2;
        }

}

public int identificador(String DireccionIP){
    try {            
            Connection con;
            // 
            PreparedStatement ps;
            ResultSet rs;
            con = this.conecta();
            String query = "select ID_equipo as existe from Equipos where Direccion_IP = '"+DireccionIP+"'";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();          
            return rs.getInt("ID_equipo");                        
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
            return 0;
        }

}

public ArrayList LlenarLista() {
    try {            
            Connection con;
            System.out.println("Entra al llenado de equipos");
            ArrayList Lista = new ArrayList();
            PreparedStatement ps;
            ResultSet rs;      
            con = this.conecta();                        
            String query = "select * from Equipos";//query para validar la existencia del equipo
            System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            System.out.println("antes del while");
            while(rs.next()){
                Equipos equipo = new Equipos(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));//,rs.getString(6));
            Lista.add(equipo);
            }
            return Lista;
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
            return null;
        } 
}

public Connection conecta(){
        try{
            String userName = "root";
            String password = "d4n13l.f";
            Connection con;
            // 
            String url = "jdbc:mysql://localhost:3306/Trabajo_Terminal_II?zeroDateTimeBehavior=convertToNull";
            PreparedStatement ps;
            ResultSet rs;
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, userName, password);
            return con;
        }
        catch(Exception e){
        e.printStackTrace();
        return null;
        }
}

}
