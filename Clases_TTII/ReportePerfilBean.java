/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases_TTII;

import Acceso_Datos.RepoMonFiles;
import Acceso_Datos.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
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
public class ReportePerfilBean {
    public List<RepoProfile> reportePerfil;
    public List<String>selectedPerfil;
    public Map<String,String> perfil;
    public ReportePerfilBean(){
        //selectedAgente = new ArrayList<String>();
    perfil = repPerfil.llenaPerfiles();
        
        
    }

    public List<String> getSelectedPerfil() {
        return selectedPerfil;
    }

    public void setSelectedPerfil(List<String> selectedPerfil) {
        this.selectedPerfil = selectedPerfil;
    }

    public Map<String, String> getPerfil() {
        return perfil;
    }

    public void setPerfil(Map<String, String> perfil) {
        this.perfil = perfil;
    }
    private final ReportePerfil repPerfil = new ReportePerfil();
    
    public void PDFMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedPerfil.isEmpty()){
            if(selectedPerfil.size()==1){
                //System.out.println(selectedAgente.get(0));
           reportePerfil = this.repPerfil.llenaRepoProfile(selectedPerfil.get(0));
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
    
    public void DOCMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedPerfil.isEmpty()){
            if(selectedPerfil.size()==1){
                //System.out.println(selectedAgente.get(0));
           reportePerfil = this.repPerfil.llenaRepoProfile(selectedPerfil.get(0));
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
    
    public void XLSMonProc(ActionEvent actionEvent) throws JRException, IOException{
        if(!selectedPerfil.isEmpty()){
            if(selectedPerfil.size()==1){
                System.out.println(selectedPerfil.get(0));
           reportePerfil = this.repPerfil.llenaRepoProfile(selectedPerfil.get(0));
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
    
    JasperPrint jasperPrint;
    public void crea() throws JRException{
      // System.out.println("entra a crea");
//       System.out.println(reporteMonProc);
       //System.out.println("reporte mon proc");
       //System.out.println(reporteMonFiles);
       //System.out.println("reporte mon files");
       
    if(reportePerfil!=null){
        System.out.print("antes del collect");
       JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reportePerfil);
       //JRBeanCollectionDataSource beancollect = new JRBeanCollectionDataSource(reporteMonFiles);
        System.out.println("a JasperPrint");
         jasperPrint = JasperFillManager.fillReport("/home/daniel/Documentos/reportes/report3.jasper", new HashMap(),beancollect);   
        
        }
        else{
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR!", "Error al crear el Reporte de monitoreo de archivos"));
            }
    }
}
