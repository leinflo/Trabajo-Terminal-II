/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;

import Acceso_Datos.*;
import java.util.List;
/**
 *
 * @author DanielFlores
 */
public class MonitoreoBean {
    
    List<Mon_files> monitoreoFiles;
    List<Mon_proc> monitoreoProc;
    Monitoreo monitor = new Monitoreo();
    
    public MonitoreoBean(){
    this.monitoreoFiles = monitor.MonitoreoRutas();
    this.monitoreoProc = monitor.MonitoreoProcesos();
    }
    
    public List<Mon_files> getMonitoreoFiles(){return this.monitoreoFiles;}
    public List<Mon_proc> getMonitoreoProc(){return this.monitoreoProc;}
    
    
    
    
}
