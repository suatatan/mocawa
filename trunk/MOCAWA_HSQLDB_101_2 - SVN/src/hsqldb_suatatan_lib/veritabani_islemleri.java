
/**
 * veritabani_islemleri.java
 * VERITABANI BAGLANTISI KURMA, CRUD ISLEMLERI VE KAPAMA GIBI GOREVLERI YAPAR
 * 3 MAYIS 2008 1: BAGLANTI KAPATMA FONKSIYONU YAZILDI
 * 3 MAYIS 2008 2: BAGLANTI KURMA ISLEMI YAZILDI
 * 4 MAYIS 2008 1: VERI TABANI ILE KUS FONKSIYONU YAZILDI
 * Created on 03 Mayıs 2008 Cumartesi, 01:50
 *
 */


package hsqldb_suatatan_lib;

import global.global_variables;
import java.sql.*;
import javax.swing.table.DefaultTableModel;




/**
 * 
 * @author suatatan
 */
public class veritabani_islemleri  implements global_variables {
    public Connection c;
    /**PREPARED STATEMENT VERI EKLEME ISLEMLERINDE KULLANILDIGINDAN CAGRILDIGI HER YER ICIN AYRI DEGER ALIR*/
    public PreparedStatement ps;
    /**STATEMENT DEGERI VERI LISTELEME ICIN KULLANILDIGINDAN BAGLANTIKUR() FONKSIYONUNDA OTOMATIKMEN TANIMLANIR*/
    public Statement s;
/**HSQLDB baglantisini hsqldb'e has SHUTDOWN sorgusu ile kapatir
 *
 */
    public void baglanti_kapat() throws SQLException, ClassNotFoundException
    {
        //@todo baglanti_kapat islemin basarisina gore boolean olarak durum firlatan fonksiyon yazilacak
        //veritabani yolu yapilandirma dosyasindan cagriliyor.
        baglanti_kur();
        int  sonuc_int=s.executeUpdate("SHUTDOWN");
        o.log("BAGLANTI KAPATILDI "+sonuc_int);
        s.close();
        c.close();
        
    }
    /**HSQLDB baglantisi kurarak komut almaya hazir hale getirir
     *
     */
    public void baglanti_kur() throws ClassNotFoundException, SQLException
    {
        
        //@todo baglanti_kur islemin basarisina gore boolean olarak durum firlatan fonksiyon yazilacak
        
        o.log("BAGLANTI KURULUYOR");
        String DBPATH=yapilandirma_dosyasindan.get("VERITABANI", "INI_DB_PATH");
        String DBDRIVER=yapilandirma_dosyasindan.get("VERITABANI", "INI_DRIVER");
       
        Class.forName(DBDRIVER);
        c = DriverManager.getConnection(DBPATH);
        
        /*
         *@todo bu hatayı coz:
         Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: 
         * org.hsqldb.jdbc.jdbcStatement cannot be cast to java.sql.PreparedStatement
*/
        s =    c.createStatement();
        
        o.log("DBPATH: "+DBPATH);
        o.log("DBDRIVER: "+DBDRIVER);
        
    }
    
    /**veritabanındaki tablolardan belirtileni, belirtilen tabloya döker sql sorgusu parametre olarak girili.
     * baglantikur komutu ile baglanttikapat komutunu tekrar kullanmaya gerek 
     * @param sorgulama_sql veritabanında istenen tablodan istenen siralama ve filtreleme ile veri ceker ornek olarak "SELECT * FROM SOCIOTAB ORDER BY SOYAD"
     * @param sutun_adedi  dokulecek tabloda kac sutun oldugu yazilir
     * @param sutun_basliklari sutunlarin baslilari [] olarak yazilir 
     * @param tablo_adi  verilerin bindirilecegi tablo adidir.
     *
     *sutun basligi orrengi:
     * mesela: String col[] = {
                "AD", "SOYAD", "GSM", "EPOSTA"
            }; denilip col [] olmadan degiskeni parameter kısmina yazilir
     */
    public void veri_dok(javax.swing.JTable  tablo_adi,String sorgulama_sql,int sutun_adedi,String sutun_basliklari []) throws ClassNotFoundException, SQLException
    {
        /*
         *@todo bu metot program icinde kullanilmamakata ancak ileride hsqldb kutuphasei olusturma ve cift boyutlu listeleme icin saklanmali
     */
        baglanti_kur();
        
        o.log("VERI DOKME ISLEMINE BASLANIYOR");
         System.out.println(sutun_basliklari[1]);   
        ResultSet RS_SonucVeriler;
        RS_SonucVeriler = s.executeQuery(sorgulama_sql);
       
        Object OBJ_SonucVeriler[][];
        OBJ_SonucVeriler = new Object[1200][sutun_adedi]; /*SONUC VERILERI BIR DIZEYE EKLENIYOR max teklif adedi 1200@todo max sutun sayisi ini den okunacak */
        int i=0;
       
        int j=1;
    
        while(RS_SonucVeriler.next())
        {
        
           
  
        i++;
            OBJ_SonucVeriler[i][1]=RS_SonucVeriler.getString(sutun_basliklari[1]);
//            OBJ_SonucVeriler[i][2]=RS_SonucVeriler.getString("TEKLIF_TO");
//            OBJ_SonucVeriler[i][3]=RS_SonucVeriler.getString(3);
//            OBJ_SonucVeriler[i][4]=RS_SonucVeriler.getString(4);
//            OBJ_SonucVeriler[i][5]=RS_SonucVeriler.getString(5);
//            OBJ_SonucVeriler[i][6]=RS_SonucVeriler.getString(6);
//            OBJ_SonucVeriler[i][7]=RS_SonucVeriler.getString(7);
            System.out.println("--------------------->"+RS_SonucVeriler.getString(sutun_basliklari[1]));
            
           
            
  
            
            
              
           
        }
        /**TABLO MODELI OLUSTURULUYOR*/
         javax.swing.table.TableModel jtm = new DefaultTableModel(OBJ_SonucVeriler, sutun_basliklari);
         tablo_adi.setModel(jtm);
         baglanti_kapat();
    }
    
    /**
     * @since  4 MAYIS 2008 VERITABANI ILE ILISKIYI TAMAMEN KESER. CRUD ISLEMLERINDEN SONRA AYNI KAPATMA IFADELERINI TOPLU CAGIRMAK ICIN YAZILDI. SON DEGISTIRME 4 MAYIS 2008*/
    public void veritabani_ile_kus() throws SQLException, ClassNotFoundException
    {
        //@todo bu fonskiyon hatali calisip exception firlatiyor-duzenlenene kadar pasif kalacak 4 MAYIS 2008-2  
        
        ps.close();
        s.close();
        c.close();
        baglanti_kapat();
    }
    
}
