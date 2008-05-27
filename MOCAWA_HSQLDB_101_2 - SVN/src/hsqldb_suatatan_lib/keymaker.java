/*
 * Keymaker.java
 * ISTENEN OZELLIKLERDE VE VERITABANLARINDA KULLANILMAK UZERE PRIMARY KEY'LER OLUSTURUR.
 */

package hsqldb_suatatan_lib;

import java.util.Calendar;

/**
 *
 * @author Suat ATAN
 */
public class keymaker {

 /**
 * FONKSIYONUN CALISTIGI ANDAKI ZAMANI ESAS ALAN VE SORTING ISLEMLERINDE TARIHI OLARAK SORTING YAPAN ANAHTARIR
 * STRING OLARAK VERI FIRLATIR. FORMAT: YYYY.A.G.S.T.D=20081211210006
 */
    public String tarihi_key()
    {
        Calendar c = Calendar.getInstance();
        String Y=String.valueOf(c.get(Calendar.YEAR));
        String A=String.valueOf(c.get(Calendar.MONTH));
        String G=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String S=String.valueOf(c.get(Calendar.HOUR));
        String D=String.valueOf(c.get(Calendar.MINUTE));
        String s=String.valueOf(c.get(Calendar.SECOND));
        String teklif_kodu=Y+A+G+S+D+S;
        return teklif_kodu;
        
    }
    
    
}
