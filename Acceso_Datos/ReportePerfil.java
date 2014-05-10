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

/**
 *
 * @author daniel
 */
public class ReportePerfil {
    private final Base_Datos DB = new Base_Datos();
    
    public List<RepoProfile> llenaRepoProfile(String nombre){
            try{
                List<RepoProfile> list = new ArrayList<RepoProfile>();
                Connection con;
                ResultSet rs;
                PreparedStatement ps;
                String query;
                
                con = DB.conecta();
                query = "select nombre, correo_electronico,mf.agente,"
                        + "mf.tipo_cambio,mp.agente, mp.inicio_Proc from alertas_hist "
                        + "ah inner join Alertas a "
                        + "on a.id_alerta = ah.id_alerta"
                        + " left outer join Mon_files mf"
                        + " on mf.ID = ah.idmonfiles"
                        + " left outer join Mon_Proc mp"
                        + " on mp.ID = ah.idmonproc"
                        + " where mf.agente in "
                        + "(select e.Direccion_IP "
                        + "from cat_perfiles cp "
                        + "inner join perfil_equipo pe "
                        + "on pe.ID_perfil = cp.ID_perfil "
                        + "inner join Equipos e "
                        + "on e.ID_equipo = pe.ID_equipo "
                        + "where cp.perfil = '"+nombre+"')"
                        + " and mp.agente in (select e.Direccion_IP "
                        + "from cat_perfiles cp "
                        + "inner join perfil_equipo pe"
                        + " on pe.ID_perfil = cp.ID_perfil "
                        + "inner join Equipos e"
                        + " on e.ID_equipo = pe.ID_equipo"
                        + " where cp.perfil = '"+nombre+"')";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                while(rs.next())
                {
                list.add(new RepoProfile(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
                }       
                con.close();
            return list;
            }
            catch(Exception e){
            e.printStackTrace();
                return null;
            }
    }
    
    public Map<String,String>llenaPerfiles(){
        try{
                Map<String,String> list = new HashMap<String,String>();
                Connection con;
                ResultSet rs;
                PreparedStatement ps;
                String query;
                
                con = DB.conecta();
                query = "Select perfil from cat_perfiles";
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
