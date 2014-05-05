/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_TTII;

import Acceso_Datos.RepoMonFiles;
import Acceso_Datos.RepoMonProc;
import Acceso_Datos.ReporteEquipos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author daniel
 */
@ManagedBean
public class ReporteEquiposBean {
    public List<RepoMonFiles> reporteMonFiles;
    public List<RepoMonProc> reporteMonProc;
    public List<String> selectedAgente;

    public List<String> getSelectedAgente() {
        return selectedAgente;
    }

    public void setSelectedAgente(List<String> selectedAgente) {
        this.selectedAgente = selectedAgente;
    }
    public Map<String,String> agente;

    public Map<String, String> getAgente() {
        return agente;
    }
    
    private ReporteEquipos repEquipos = new ReporteEquipos();
    
    public ReporteEquiposBean(){
        //selectedAgente = new ArrayList<String>();
    agente = repEquipos.llenaEquipos();
        
        
    }

    public List<RepoMonFiles> getReporteMonFiles() {
        return reporteMonFiles;
    }

    public void setReporteMonFiles(List<RepoMonFiles> reporteMonFiles) {
        this.reporteMonFiles = reporteMonFiles;
    }

    public List<RepoMonProc> getReporteMonProc() {
        return reporteMonProc;
    }

    public void setReporteMonProc(List<RepoMonProc> reporteMonProc) {
        this.reporteMonProc = reporteMonProc;
    }
    
    public void PDFMonFiles(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonFiles = this.repEquipos.llenaRepoMonFiles(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.crea();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.pdf");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JasperExportManager.exportReportToPdfStream(jasperPrint, servletoutput);
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }        
    }
    
    public void DOCMonFiles(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonFiles = this.repEquipos.llenaRepoMonFiles(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.crea();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.docx");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JRDocxExporter exportaDoc = new JRDocxExporter();
           exportaDoc.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
           exportaDoc.setParameter(JRExporterParameter.OUTPUT_STREAM,servletoutput);
           exportaDoc.exportReport();
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }        
    }
    
    public void XLSMonFiles(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonFiles = this.repEquipos.llenaRepoMonFiles(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.crea();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.xlsx");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JRXlsxExporter xlsExport = new JRXlsxExporter();
           xlsExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExport.setParameter(JRExporterParameter.OUTPUT_STREAM, servletoutput);
            xlsExport.exportReport();
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }        
    }
    
    public void PDFMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonProc = this.repEquipos.llenaRepoMonProc(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.creaProc();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.pdf");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JasperExportManager.exportReportToPdfStream(jasperPrint, servletoutput);
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }      
    }
    
    public void DOCMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonProc = this.repEquipos.llenaRepoMonProc(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.creaProc();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.docx");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JRDocxExporter exportaDoc = new JRDocxExporter();
           exportaDoc.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
           exportaDoc.setParameter(JRExporterParameter.OUTPUT_STREAM,servletoutput);
           exportaDoc.exportReport();
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }        
    }
    
    public void XLSMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedAgente.isEmpty()){
            if(selectedAgente.size()==1){
                //System.out.println(selectedAgente.get(0));
           reporteMonProc = this.repEquipos.llenaRepoMonProc(selectedAgente.get(0));
           //System.out.println(reporteMonFiles);
           this.creaProc();
           HttpServletResponse httpservletresponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
           httpservletresponse.addHeader("Content-disposition","attachment; filename=report.xlsx");
           ServletOutputStream servletoutput = httpservletresponse.getOutputStream();
           JRXlsxExporter xlsExport = new JRXlsxExporter();
           xlsExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExport.setParameter(JRExporterParameter.OUTPUT_STREAM, servletoutput);
            xlsExport.exportReport();
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA!", "Solo se puede elegir un equipo"));
            }
            }
        else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Se debe elegir un equipo."));
        }        
    }
    
    JasperPrint jasperPrint;
    public void crea() throws JRException{
      // System.out.println("entra a crea");
//       System.out.println(reporteMonProc);
       //System.out.println("reporte mon proc");
       //System.out.println(reporteMonFiles);
       //System.out.println("reporte mon files");
       
    if(reporteMonFiles!=null){
        System.out.print("antes del collect");
       JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reporteMonFiles);
       //JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reporteMonFiles);
        System.out.println("a JasperPrint");
         jasperPrint = JasperFillManager.fillReport("/home/daniel/Documentos/reportes/report1.jasper", new HashMap(),beancollect);   
        
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Error al crear el Reporte de monitoreo de archivos"));
            }
    }
    
    public void creaProc() throws JRException{
      // System.out.println("entra a crea");
//       System.out.println(reporteMonProc);
       //System.out.println("reporte mon proc");
       //System.out.println(reporteMonFiles);
       //System.out.println("reporte mon files");
       
    if(reporteMonProc!=null){
        System.out.print("antes del collect");
       JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reporteMonProc);
       //JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reporteMonFiles);
        System.out.println("a JasperPrint");
         jasperPrint = JasperFillManager.fillReport("/home/daniel/Documentos/reportes/report1.jasper", new HashMap(),beancollect);   
        
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Error al crear el Reporte de monitoreo de archivos"));
            }
    }
    
}
