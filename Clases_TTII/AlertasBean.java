/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;
  
import Acceso_Datos.EnvioAlertas;
import Acceso_Datos.Alertas;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

/**
 *
 * @author DanielFlores
 */
public class AlertasBean {
    
    private String host,puerto,usuario,password,nombreAlerta,correoElectronico;
    public List<Alertas> alerts;
    private EnvioAlertas envia = new EnvioAlertas();
    private DualListModel<String> equipos;
    private List<String> selectedNombreAlerts;
    private Map<String,String> nombreAlerts;
    public int identificador;
    //public AlertasBean(){}
    
    
    
    public AlertasBean(){
    //alerts = new ArrayList<Alertas>();
    alerts = envia.llenaAlertas();
    //System.out.println(envia);
    List<String> source  = envia.llenaEquipos();  
        List<String> target = new ArrayList<String>();
        
        
    equipos = new DualListModel<String>(source,target);
    
    nombreAlerts = envia.obtieneAlertas();
    
    }
    
    public List<Alertas> getAlerts(){return this.alerts;}
    
    public Map<String,String> getNombreAlerts(){return this.nombreAlerts;}
    
    public void setSelectedNombreAlerts(List<String> selectedNombreAlerts) {  
        this.selectedNombreAlerts = selectedNombreAlerts;  
    }
    
    public List<String> getSelectedNombreAlerts() {  
        return selectedNombreAlerts;  
    }  
    
     public DualListModel<String> getEquipos() {  
        return equipos;  
    } 
     
     public void setEquipos(DualListModel<String> equipos) {  
         this.equipos = equipos;  
    } 
    
    public String getHost(){return host;}
    public void setHost(String host){this.host = host;}
    
    public String getPuerto(){return puerto;}
    public void setPuerto( String puerto ){this.puerto = puerto;}
    
    public String getUsuario(){return this.usuario;}
    public void setUsuario(String usuario){ this.usuario = usuario;}
    
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    
    public String getNombreAlerta(){return this.nombreAlerta;}
    public void setNombreAlerta(String nombreAlerta){this.nombreAlerta = nombreAlerta;}
    
    public String getCorreoElectronico(){return this.correoElectronico;}
    public void setCorreoElectronico(String correoElectronico){this.correoElectronico = correoElectronico;}
    
    public int getIdentificador(){return this.identificador;}
    public void setIdentificador(int identificador){this.identificador = identificador;}

    
    public void Configura(){
        int exito;
        
        exito= envia.ConfiguraServer(host, puerto, usuario, password);
        
        if(exito == 0){
        System.out.println("Se configuró el server");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!","El servidor SMTP "+host+" ha sido registrado!")); 
        
        }
        else{
        if(exito == 1){
        
            System.out.println("El server ya existe");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","El servidor SMTP "+host+" con el usuario "+usuario+" ya existe!")); 
        }
        else{
            if(exito==3){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!","El servidor SMTP "+host+" con el usuario "+usuario+" se ha actualizado!")); 
            }
            else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!","Hubo un error!!!")); 
            }
        }
        }
        
    }
    
    public void Registra(){
        
        int exito;
        System.out.println("Entro al metodo");
        if(nombreAlerta.isEmpty()||correoElectronico.isEmpty()){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Los campos deben ser llenados")); 
        return;
        }
        exito = envia.registraAlerta(nombreAlerta,correoElectronico);
        
        if(exito == 0){
            System.out.println("Se registro la Alerta ");
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!","La alerta "+nombreAlerta+" quedó registrada!!!")); 
                              
        }
        else{
        if(exito==1){
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","La alerta "+nombreAlerta+" ya existe")); 
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ADVERTENCIA!","Hubo un error")); 
        }
        }
    }
    
    public void Asigna(){
        
        int exito;
        if(!this.selectedNombreAlerts.isEmpty()){
            if(this.selectedNombreAlerts.size()>1){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Se debe elegir solo una alerta")); 
            }
            else{
            if(!this.equipos.getTarget().isEmpty()){
                System.out.print(this.selectedNombreAlerts.get(0));
                exito = envia.Asigna(this.selectedNombreAlerts.get(0), this.equipos.getTarget());                
                if(exito==0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!","Se asignaron los equipos a la alerta "+this.selectedNombreAlerts.get(0))); 
                }else{
                    if(exito==1){
                        
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!","La alerta "+this.selectedNombreAlerts.get(0)+" ya tiene equipos asignados")); 
                    }
                    else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!","Hubo un error!")); 
                    }
                
                }
                
            
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Se debe elegir al menos un equipo")); 
            }
            }
        
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Se debe seleccionar una alerta")); 
        }
        
    }  
    
    public void Modifica(){
        Acceso_Datos.Alertas alert = new Acceso_Datos.Alertas();
        int exito;
        exito = alert.ModificaAlerta(this.nombreAlerta, this.correoElectronico);
        if(!this.correoElectronico.isEmpty()||!this.nombreAlerta.isEmpty()){
        if(exito==0){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO!","Se modifico exitosamente la alerta "+nombreAlerta));
        }
        else{
        if(exito==1){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Alerta "+nombreAlerta+" inválido"));
        }
        else{
        if(exito==2){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","el correo electrónico "+correoElectronico+" ya está registrado en otra alerta"));
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR!","Hubo un error de conexión con la base"));
        }
        }
            }
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!","Se deben llenar ambos campos"));
        }
         
        }
}
