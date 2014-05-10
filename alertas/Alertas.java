/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alertas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.*; 
import javax.mail.*; 
import javax.mail.internet.*;
/**
 *
 * @author daniel
 */
public class Alertas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {       
        try{
            //System.out.println("Empieza el Alertador");
        int i=0, id_alerta_hist;
        Base_Datos DB = new Base_Datos();
        Connection con;
        PreparedStatement ps ;
       ResultSet rs;
       
       String query = "select id_alerta_hist, correo_electronico, mf.*,mp.* from alertas_hist ah inner join Alertas a on a.id_alerta = ah.id_alerta left outer join Mon_files mf on mf.ID = ah.idmonfiles left outer join Mon_Proc mp on mp.ID = ah.idmonproc where ah.enviado=0 ";       
       while(true){
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
        //System.out.println(i);
        }                
        }
        int b;
        for(b=0;b<i;b++){
            //System.out.println("id_alertas_hist = "+enviados.get(b));
        query = "update alertas_hist set enviado = 1 where id_alerta_hist = "+enviados.get(b);
        ps = con.prepareStatement(query);        
        ps.execute(query);
        ps.close();
        }
        con.close();
       }
        }
        catch(Exception e){
            e.printStackTrace();
    }
    }

    public static void sendFromGMail(String para, String asunto, String mensaje) {
        try{
        //Propiedades de la conexión 
            //System.out.println("Enviar mensaje");
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.host","smtp.gmail.com");
            prop.setProperty("mail.smtp.starttls.enable","true");
            prop.setProperty("mail.smtp.port","587");
            prop.setProperty("mail.smtp.user","flores.herrera.daniel@gmail.com");
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
            
            transporte.connect("flores.herrera.daniel@gmail.com", "nesssamus");
            
            transporte.sendMessage(message, message.getAllRecipients());
            
            //cierre
            
            transporte.close();
            
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        
        }
    }

    private static class Base_Datos {

        public Base_Datos() {
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
}
