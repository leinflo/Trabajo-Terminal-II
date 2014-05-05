/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import Acceso_Datos.Dashboard;

/**
 *
 * @author DanielFlores
 */
public class DashboardBean implements Serializable {  
  
    private DashboardModel model;  
    private CartesianChartModel categoryModel, procModel; 
    //private Dashboard estadisticas = new Dashboard();
    
     //private final HttpServletRequest httpServletRequest;
    //private final FacesContext faceContext;
      
    public DashboardBean() {  
        
        createCategoryModel();
        ModeloProcesos();
        
        
    }  
      
    public void handleReorder(DashboardReorderEvent event) {  
        FacesMessage message = new FacesMessage();  
        message.setSeverity(FacesMessage.SEVERITY_INFO);  
        message.setSummary("Reordered: " + event.getWidgetId());  
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());  
          
        addMessage(message);  
    }  
      
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
      
    public DashboardModel getModel() {  
        return model;  
    }  
    
    public CartesianChartModel getCategoryModel() {  
        return categoryModel;  
    }  
    
    public CartesianChartModel getProcModel() {  
        return procModel;  
    }  
    
    private void createCategoryModel() {  
        categoryModel = new CartesianChartModel();  
        Dashboard estadisticas = new Dashboard();
  
        ChartSeries direcciones = new ChartSeries();  
        direcciones.setLabel("Top 10 equipos FileSystem");  
        List<String> equipo;//= new ArrayList<String>(); 
        List<Integer> cuentaEquipo;// = new ArrayList<Integer>();    
        //System.out.println("crea");
        
        equipo = estadisticas.LlenaTopTenFiles();
        cuentaEquipo = estadisticas.LlenaTopTenFilesCuenta();
       // System.out.println(equipo);
        //System.out.println(cuentaEquipo);
        //System.out.println("llena");
        
        Iterator<Integer> cuentaEquipos = cuentaEquipo.iterator();
        Iterator<String> equipos = equipo.iterator();
        //System.out.print(equipos);
        //System.out.print(cuentaEquipos);
        String IP;
        int cuenta;
        while(equipos.hasNext()){
            IP = equipos.next();
            cuenta = cuentaEquipos.next();
        //System.out.print(IP);
        //System.out.print(cuenta);
            
            direcciones.set(IP,cuenta);        
        }                
        //System.out.println("termina el while");
        categoryModel.addSeries(direcciones);                  
    }
    
    private void ModeloProcesos() {  
        procModel = new CartesianChartModel();  
        Dashboard statistics = new Dashboard();
  
        ChartSeries procesos = new ChartSeries();  
        procesos.setLabel("Top 10 equipos Procesos");  
        List<String> equipo;//= new ArrayList<String>(); 
        List<Integer> cuentaEquipo;// = new ArrayList<Integer>();    
        //System.out.println("crea");
        
        equipo = statistics.LlenaTopTenProc();
        cuentaEquipo = statistics.LlenaTopTenProcCuenta();
//        System.out.println(equipo);
  //      System.out.println(cuentaEquipo);
        //System.out.println("llena");
        
        Iterator<Integer> cuentaEquipos = cuentaEquipo.iterator();
        Iterator<String> equipos = equipo.iterator();
        //System.out.print(equipos);
        //System.out.print(cuentaEquipos);
        String IP;
        int cuenta;
        while(equipos.hasNext()){
            IP = equipos.next();
            cuenta= cuentaEquipos.next();
        //System.out.print(IP);
        //System.out.print(cuenta);
            procesos.set(IP,cuenta);        
        }                
        
        procModel.addSeries(procesos);                  
    }
} 
