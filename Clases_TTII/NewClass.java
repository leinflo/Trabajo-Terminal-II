package Clases_TTII;
import javax.activation.*; 
import javax.mail.*; 
import javax.mail.internet.*;
import java.util.Properties;

public class NewClass 
{

private static String USER_NAME = "flores.herrera.daniel";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "nesssamus"; // GMail password
    private static String RECIPIENT = "agdanihe@hotmail.com";

    public static void main(String[] args) {       
        String para = "Java send mail example";
        String asunto = "Welcome to JavaMail!";
        String mensaje ="kiubo!!!";

        sendFromGMail(para,asunto,mensaje);
    }

    public static void sendFromGMail(String para, String asunto, String mensaje) {
        try{
        //Propiedades de la conexión 
            System.out.println("Enviar mensaje");
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
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("alejandroberman@hotmail.com"));
            
            message.setSubject(asunto);
            
            message.setText(mensaje);
            
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

}