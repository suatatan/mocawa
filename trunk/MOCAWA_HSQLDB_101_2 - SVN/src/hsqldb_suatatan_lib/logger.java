

/**
 * logger.java
 * LOGGER'IN AMACI PROGRAM CALISIRKEN OLAN TUM ISLEMLERI LOGLAMASIDIR
 * AMAC:2 SYSTEM.OUT.PRINTLN ISLEMI LOG() FONKSIYONU ICINDE OTOMATIKMAN YAPILIR
 * 3 MAYIS 2008 1: LOG DOSYASINA ISLEM TARIHINI DE EKLEYECEK EKLENTI YAZILDI
 * 3 MAYIS 2008 2: LOG DOSYASINA P HTML TAGINI OTOMATIK EKLEYEYECEK KOD YAZILDI
 * Created on 03 Mayıs 2008 Cumartesi, 01:50
 */


package hsqldb_suatatan_lib;
import global.global_variables;
import java.io.*;
import java.util.Calendar;

public class logger implements global_variables
{

    
    public logger()
    {
    }

    public void log(Exception ex) {
       System.out.println(ex);
    }

    /** 
     * log olarak kaydedilecek bilgi logtxt parameteresine yazılır,log dosyası ise default olarak 
     @param logtxt
     */
    public void log (String logtxt) 
    {
        try
        {
            
            
            yapilandirma_dosyasindan.load("beyin\\yapilandirma_dosyasi.ini");
            
            
            RandomAccessFile ekleme = new RandomAccessFile(yapilandirma_dosyasindan.get("LOGGER", "INI_LOG_FILE"), "rw");
            String satir = "<p>"+logtxt+islem_tarihi()+"</p>";
            ekleme.seek(ekleme.length());
            ekleme.writeBytes(satir);
            ekleme.writeByte(10);
            ekleme.close();
            System.out.println((new StringBuilder()).append("<p>-----Log:").append(satir).append("</p>").toString());
        }
        catch(FileNotFoundException excep)
        {
            System.out.println("HATA:LOG DOSYASI KAYIP HATA SONUCU:"+excep);
            
        }
        catch(IOException excep)
        {
            System.out.println("Bir \"exception\" olustu ...");
        }
    }
    
    /** 
     * calistigi andaki islem tarihini string olarak dondurur
     *
     */
    
   public String islem_tarihi()
   {
        String STR_ISLEM_TARIHI;
        Calendar c = Calendar.getInstance();
        String Y=String.valueOf(c.get(Calendar.YEAR));
        String A=String.valueOf(c.get(Calendar.MONTH));
        String G=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String S=String.valueOf(c.get(Calendar.HOUR));
        String D=String.valueOf(c.get(Calendar.MINUTE));
        String s=String.valueOf(c.get(Calendar.SECOND));
        STR_ISLEM_TARIHI="-->ISLEM TARIHI VE SAATI:"+G+"."+A+"."+Y+"-"+S+":"+D+":"+s;
        return STR_ISLEM_TARIHI;
        
        
   }
    
    
    
    
}