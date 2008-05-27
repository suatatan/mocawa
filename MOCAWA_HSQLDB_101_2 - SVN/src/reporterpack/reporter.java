/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reporterpack;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sf.fjreport.FJReport;
import net.sf.fjreport.control.PrintAction;

/**
 *
 * @author Suat ATAN
 */
public class reporter extends JFrame implements global.global_variables  {

    public reporter(String rapor_metni) {
        FJReport report = new FJReport();
        report.loadReport("beyin\\mocrep.xml");
        report.setValue("header1", "MOCAWA APPLICATION REPORT");
        report.setValue("header2", "TEKLIF LISTESI");
//        
      
        report.setValue("RAPOR_GOVDESI",rapor_metni);
        
     
        
        report.setValue("footer2", "Mocawa Programı Cem Ambalaj Şirketi için özel olarak geliştirilmiştir.");
        report.setValue("footer1","www.suatatan.com");
        
        report.setState(FJReport.EDIT_STATE); 
        report.firstPage();
        
        JScrollPane sc = new JScrollPane(report); 
  JPanel p = report.getEditToolBarPane(); 
    JButton btnPrint = new JButton(new PrintAction(report)); 
  btnPrint.setPreferredSize(new Dimension(28, 28)); 
  p.add(btnPrint); 
  add(p, "North"); 
  add(sc, "Center"); 
   
  setDefaultCloseOperation(EXIT_ON_CLOSE); 
  setSize(700, 600); 
  setLocationRelativeTo(null); 
  setVisible(true); 
        
        
    }

    public static void main(String[] args) {
        
       
            new reporter();
        
        
    }

    private reporter() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

 

    

    

   
}
