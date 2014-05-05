/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso_Datos;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;      
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DanielFlores
 */
public class EnvioAlertas {
    private Base_Datos DB = new Base_Datos();
    
    public void EnviaAlerta() {       
        try{
        int i=0, id_alerta_hist;
        //Base_Datos DB = new Base_Datos();
        Connection con;
        PreparedStatement ps ;
       ResultSet rs;
       String query = "select id_alerta_hist, correo_electronico, mf.*,mp.* from alertas_hist ah inner join Alertas a on a.id_alerta = ah.id_alerta left outer join Mon_files mf on mf.ID = ah.idmonfiles left outer join Mon_Proc mp on mp.ID = ah.idmonproc where ah.enviado=0 ";       
       con = DB.conecta();
       ps = con.prepareStatement(query);
       rs = ps.executeQuery(query);
       List<Integer> enviados = new ArrayList<Integer>();
       String para;
        String asunto = "ALERTA!";
        while(rs.next()){            
            id_alerta_hist = rs.getInt(1);
        para  = rs.getString(2);
        String mensaje = "<h1>Alerta " + id_alerta_hist +"</h1>";
        if(rs.getString(3)!=null){
        mensaje += "<h1>CAMBIO EN UN ARCHIVO O CARPETA</h1>";
        mensaje += "<p> Agente: "+rs.getString(4) +"</p>";
        mensaje += "<p> Usuario:"+rs.getString(5) +"</p>";
        mensaje += "<p> Archivo: "+rs.getString(6) +"</p>";
        if(rs.getString(7)!=null){
        mensaje += "<p> Archivo renombrado: "+rs.getString(7) +"</p>";
        }
        mensaje+= "<p>Tipo de cambio: "+rs.getString(8)+"</p>";
        mensaje+= "<p>Momento del cambio: "+rs.getString(9) +"</p>";        
        
        sendFromGMail(para,asunto,mensaje);
        enviados.add(id_alerta_hist);     
        i++;
        }

        
        else{
       mensaje += "<h1>Ejecución de un proceso no autorizado</h1>";
        mensaje += "<p> Agente: "+rs.getString(11) +"</p>";
        mensaje += "<p> Usuario:"+rs.getString(12) +"</p>";
        mensaje += "<p> Nombre del proceso: "+rs.getString(13) +"</p>";        
        mensaje+= "<p>Momento de la ejecución: "+rs.getString(14) +"</p>";   
        
        sendFromGMail(para,asunto,mensaje);
        enviados.add(id_alerta_hist);
        i++;
        System.out.println(i);
        }                
        }
        int b;
        for(b=0;b<i;b++){
            System.out.println("id_alertas_hist = "+enviados.get(b));
        query = "update alertas_hist set enviado = 1 where id_alerta_hist = "+enviados.get(b);
        ps = con.prepareStatement(query);        
        ps.execute(query);
        ps.close();
        }
        con.close();
        }
        catch(Exception e){
            e.printStackTrace();
    }
    }

    public void sendFromGMail(String para, String asunto, String mensaje) {
        try{
        //Propiedades de la conexión 
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            String query = "select * from configuracion"; 
            con = DB.conecta();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            Properties prop = new Properties();
            if(rs.next()){            
                System.out.println("Enviar mensaje");            
            prop.setProperty("mail.smtp.host",rs.getString(2));
            prop.setProperty("mail.smtp.starttls.enable","true");
            prop.setProperty("mail.smtp.port",rs.getString(3));
            prop.setProperty("mail.smtp.user",rs.getString(4));
            prop.setProperty("mail.smtp.auth","true");
            
            //Inicializar la sesión 
            
            Session sesion = Session.getDefaultInstance(prop);
            
            
            //el mensaje
            MimeMessage message = new MimeMessage(sesion);
            
            message.setFrom(new InternetAddress("alguien"));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            
            message.setSubject(asunto);
            
            message.setText(mensaje, "utf-8", "html");
            
            //Envio de mensaje
            
            Transport transporte = sesion.getTransport("smtp");
            
            transporte.connect(rs.getString(3), rs.getString(5));
            
            transporte.sendMessage(message, message.getAllRecipients());
            
            //cierre
            con.close();
            transporte.close();
            
            }                                                            
        }
        catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
    public int registraAlerta(String nombreAlerta, String correoDestino){
        try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        
        con = DB.conecta();
        query = "select count(*) from Alertas where nombre = '"+nombreAlerta+"'";
        
        ps =con.prepareStatement(query);
        
        rs = ps.executeQuery(query);
        
        rs.next();
        
        if(rs.getInt(1)==0){
        
            ps.close();
       
        query = "insert into Alertas values (NULL,'"+nombreAlerta+"','"+correoDestino+"',0)";
                
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
    
    public int ConfiguraServer(String host, String puerto, String usuario, String password){
        try{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;
        
        con = DB.conecta();
        
        query = "Select count(*) from configuracion";
        
        ps = con.prepareStatement(query);
        rs = ps.executeQuery(query);
        
        rs.next();
        if(rs.getInt(1)==0){
            ps.close();
        query = "Select count(*) from configuracion where smtp ='"+host+"' and correo_desde ='"+usuario+"'";
        
        ps = con.prepareStatement(query);
        
        rs = ps.executeQuery(query);
        
        rs.next();
        
        if(rs.getInt(1)==0){
        ps.close();
        
        query = "insert into configuracion values(NULL,'"+host+"','"+puerto+"','"+usuario+"','"+password+"')";
        
        ps = con.prepareStatement(query);
        
        ps.execute();            
            
        ps.close();    
        con.close();
        return 0;
        }
        else{            
        con.close();
        return 1;
        }
        }  
        else{
            ps.close();
            query = "update configuracion set smtp ='"+host+"', puerto='"+puerto+"', correo_desde ='"+usuario+"', password='"+password+"' where id_conf=1";
            ps = con.prepareStatement(query);
            ps.executeUpdate(query);
            
            ps.close();
            con.close();
            return 3;
            
        
        }
        }
        catch(Exception e){
        e.printStackTrace();
            return 2;
            
        }
    }
    
    public List<Alertas> llenaAlertas() {  
        try {            
            List<Alertas> list = new ArrayList<Alertas>();
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            String active;
            String query = "select * from Alertas";//query para validar la existencia del equipo
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            //System.out.println("antes del while");
            while(rs.next()){
               // System.out.println(rs.getInt(1));
                if(rs.getInt(4)==0){
                    active ="NO";                
                } 
                else{
                active ="SI";
                }
                    list.add(new Alertas(rs.getString(2),rs.getString(3),active));
            }
            ps.close();
            con.close();
            return list;
            
    } catch (Exception e) {
            System.out.println("hubo un error");
            e.printStackTrace();
            return null;
        }
        
    }
    
    public List<String> llenaEquipos(){
        try{
            List<String> lista = new ArrayList<String>();
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String query;        
        con = DB.conecta();
        query="select direccion_ip from Equipos";        
        ps = con.prepareStatement(query);        
        rs = ps.executeQuery(query);        
        while(rs.next()){            
            lista.add(rs.getString(1));        
        }        
        ps.close();
        con.close();
        return lista;                        
        }
        catch(Exception e){
            e.printStackTrace();
            return null;        
        } 
    }
    
    public Map<String,String> obtieneAlertas(){
        try{
            Connection con;
            Map<String,String>  alertas = new HashMap<String, String>(); 
            PreparedStatement ps;
            ResultSet rs;
            con = DB.conecta();
            
            String query = "Select nombre from Alertas";
            //System.out.println(query);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while(rs.next()){
              //  System.out.println(rs.getString(1));
            alertas.put(rs.getString(1), rs.getString(1));
            }
            return alertas;
        }
        catch(Exception e){
        e.printStackTrace();
        return null;
        }
        
    }
    
    public int Asigna(String alertas, List<String> equipos){
        try{
            System.out.print("Entra a metodo Asigna");
            Connection con;
            PreparedStatement ps;
            ResultSet rs;
            String query;
            int id_alerta;
            List<Integer> identificadores = new ArrayList<Integer>();
            Iterator<String> iteraequipos = equipos.iterator();
            
            con = DB.conecta();
            query = "Select count(*) from alerta_equipo where id_alerta in (select id_alerta from alertas where nombre='"+alertas+"')";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();            
            System.out.print(rs.getInt(1));
            if(rs.getInt(1)==0){
                ps.close();
                while(iteraequipos.hasNext()){
                query="select id_equipo from equipos where direccion_ip='"+iteraequipos.next()+"'";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery(query);
                rs.next();
                identificadores.add(rs.getInt(1));
                ps.close();
                }
            query="select id_alerta from alertas where nombre='"+alertas+"'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            rs.next();
            id_alerta = rs.getInt(1);
            ps.close();
            Iterator<Integer> ids = identificadores.iterator();
            
            while(ids.hasNext()){
            query = "insert into alerta_equipo values(NULL,"+id_alerta+","+ids.next()+")";
            ps = con.prepareStatement(query);
            ps.execute(query);
            ps.close();    
            }
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
            return 3;
        
        }            
    }
    
    
}
