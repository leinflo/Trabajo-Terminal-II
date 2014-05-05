/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases_TTII;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author DanielFlores
 */
public class creaXML {
    
      private final static String[] porDefecto;
  
  static{
        porDefecto = new String[33];
        porDefecto[0]="Idle";
porDefecto[1]="System";
porDefecto[2]="smss";
porDefecto[3]="csrss";
porDefecto[4]="wininit";
porDefecto[5]="winlogon";
porDefecto[6]="services";
porDefecto[7]="lsass";
porDefecto[8]="lsm";
porDefecto[9]="svchost";
porDefecto[10]="audiodg";
porDefecto[11]="svchost";
porDefecto[12]="spoolsv";
porDefecto[13]="vmtoolsd";
porDefecto[14]="taskhost";
porDefecto[15]="dwm";
porDefecto[16]="explorer";
porDefecto[17]="TPAutoConnSvc";
porDefecto[18]="dllhost";
porDefecto[19]="TPAutoConnect";
porDefecto[20]="conhost";
porDefecto[21]="SearchIndexer";
porDefecto[22]="SearchProtocolHost";
porDefecto[23]="WmiPrvSE";
porDefecto[24]="SearchFilterHost";
porDefecto[25]="msdtc";
porDefecto[26]="sppsvc";
porDefecto[27]="mscorsvw";
porDefecto[28]="VSSVC";
porDefecto[29]="WMIADAP";
porDefecto[30]="WmiPrvSE";
porDefecto[31]="SearchProtocolHost";
porDefecto[32]="tasklist";        
}
    
    public void creaXML(String nombrePerfil,String respuestaActiva,String numHits,List<String> paths,List<String> proc) {
 
      try {
 
          Iterator<String> itera = paths.iterator();
          Iterator<String> iterar = proc.iterator();
          
        Element agente = new Element("Agente");
        Document doc = new Document(agente);
 //       doc.setRootElement(agente);
 
        Element servidor = new Element("servidor");
        servidor.addContent(new Element("conectado").setText("1"));
        servidor.addContent(new Element("digesto").setText("cambiar"));
        servidor.addContent(new Element("direccionip").setText("cambiar"));
        
 
        doc.getRootElement().addContent(servidor);
        
        System.out.printf("agrego los elementos digesto y direccionIP");
 
        Element perfil = new Element("perfil");
        perfil.addContent(new Element("nombre").setText(nombrePerfil));
        perfil.addContent(new Element("respuesta_activa").setText(respuestaActiva));
        perfil.addContent(new Element("hits_a_carpetas").setText(numHits));
        
        Element rutas = new Element("rutas");
        while(itera.hasNext()){
            rutas.addContent(new Element("path").setText(itera.next()));
        }
        perfil.addContent(rutas);
        
        Element programas = new Element("programas");
        int i;
        for(i=0;i<porDefecto.length;i++){
            programas.addContent(new Element("programa").setText(porDefecto[i]));            
        }
        
        while(iterar.hasNext()){
            programas.addContent(new Element("programa").setText(iterar.next()));
        }
        
        
        perfil.addContent(programas);
         doc.getRootElement().addContent(perfil);
        // new XMLOutputter().output(doc, System.out);
        XMLOutputter xmlOutput = new XMLOutputter();
 
        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter("/home/daniel/Escritorio/perfiles/"+nombrePerfil+".xml"));
 
        System.out.println("Perfil Guardado!");
      } catch (IOException io) {
        System.out.println(io.getMessage());
      }
    }
    
}
