/**
 * @author suatatan
 * mocawa_ozel_db_islemleri.java
 * MOCAWA PROGRAMININ OZEL OLARAK KULLANDIGI VERITABANI ISLEM FONKSIYONLARI ICIN YAZILDI
 * 4 MAYIS 2008 1: teklif_listele() fonksiyonu basarili olarak tamamlandi harici bir dosyadan  
 * op.teklif_listele("SELECT * FROM TEKLIFLER", TABLO1); denilerek (op ozel cagirma ks.) TABLO1'E EXCEPTION OLMAKSIZIN 
 * VERILER YUKLENDI
 * 4 MAYIS 2008 2: secili_kayit() fonksiyonu yazip calistirildi/her tablo icin gecerli
 * 4 MAYIS 2008 3: kayit_sil() fonksiyonu yazilip calistirildi/her tablo icin gecerli
 * 5 MAYIS 2008 1: ebat_listele() fonksiyonu yazılıp calistridili
 * 5 MAYIS 2008 2: ebat_ekle() fonksiyonu yazılıp calistridili
 * 7 MAYIS 2008 1: GENEL SILME FONKSIYONU ILE EBAT SILME ISLEMI BASARILDI 
 * 7 MAYIS 2008 2: teklif_guncelle() FONKSIYONU YAZILDI
 *@see veritabani_islemleri.java
 *
 */
package mocawa_hsqldb_101_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.swing.table.DefaultTableModel;

public class mocawa_ozel_db_islemleri implements global.global_variables {

    /**OBJ nesnesi verilerin gecici tamponlanmasi icin olusturuldu*/
    public Object OBJ[][];
    public int satir_adedi;

    /**
     * Bu fonksiyon teklifler veritabanindaki verileri parameterede adi gecen tabloya istenen sql sorgusu ile listeler
     * @param sorgu_sql teklifler veritabanındaki verileri listelemek icin kullanılacak sorgudur. ornek: SELECT * FROM TEKLIFLER ORDER BY FAX
     * @param tablo_adi fonksiyon cagrilirken verinin bindirilecegi tablonun adidir. ornek: TABLO1
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>op.teklif_listele("SELECT * FROM TEKLIFLER", TABLO1);</i>
     * <p>TEKLIFLER VERITABANI KAYIP DURUMUNDA YENISI ICIN TEKLIFLER.grab dosyası mevcuttur</p>
     * 
     */
    public int satir_adedi(String sorgu_sql) throws ClassNotFoundException, SQLException
    {
        db.baglanti_kur();
        ResultSet RS = db.s.executeQuery(sorgu_sql);
        
       
        satir_adedi = 1;
       
        while(RS.next())
        {
            satir_adedi++;
            
           
        }
        return satir_adedi;
    }
    
    
    public void teklif_listele(String sorgu_sql, javax.swing.JTable tablo_adi) throws ClassNotFoundException, SQLException {
      
        db.baglanti_kur();
        ResultSet RS = db.s.executeQuery(sorgu_sql);
        
       
        
       
        int satiradedi=satir_adedi(sorgu_sql);
        System.out.println("TOPLAM SATIR ADEDI: "+satiradedi);
        
        OBJ = new Object[satiradedi][7];
        
        
        
        String sutun_basliklari[] = {"TEKLIFNO", "FİRMA", "SON GEÇERLİLİK TARİHİ", "YETKİLİ", "TELEFON", "FAX", "TEKLİF TARİHİ"};

        int i = -1;//tabo en ust satirdan baslar
        while (RS.next()) {
           
 i++;
            OBJ[i][0] = RS.getString(1);
            OBJ[i][1] = RS.getString(2);
            OBJ[i][2] = RS.getString(3);
            OBJ[i][3] = RS.getString(4);
            OBJ[i][4] = RS.getString(5);
            OBJ[i][5] = RS.getString(6);
            OBJ[i][6] = RS.getString(7);
            
            
            
        }

        /**Asagidaki islem verinin tabloya bindirilmesi surecidir.*/
        javax.swing.table.TableModel jtm = new DefaultTableModel(OBJ, sutun_basliklari);
        tablo_adi.setModel(jtm);
        
        /**Asagidaki islem once resultsetin sonra baglantinin en son ise veritabaninin kapatilmasi islemidir.*/
        RS.close();
        db.c.close();
        db.baglanti_kapat();
    }
    
    
    
    
    
    
    

    /**
     * bu fonksiyon, disaridan enumeration olarak gelen teklif verilerini TEKLIFLER tablosuna munferit olarak ekler. baglantiyi otomatik olarak kapatir. eklenen teklifin tabloda hemen gozukmesi icin teklif_listele komutunun bu komuttan sonra tekrar cagrilmasi gerekir.
     * @param teklif_bilgileri : bir teklif girdisine ait sirali tum verileri icerir.
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>Vector teklif = new Vector();<br/>
    
    teklif.addElement("333");<br/>
    teklif.addElement("TEKLIF_TO SUATATAN");<br/>
    teklif.addElement("SON GECERLILIK 12/12/2007");<br/>
    teklif.addElement("YETKILI SUAT ATAN");<br/>
    teklif.addElement("TELEFON 04325512361");<br/>
    teklif.addElement("FAX: 04322166620");<br/>
    teklif.addElement(null);//tarih girmedik<br/>
    Enumeration teklif_bilgileri = teklif.elements();<br/>
    op.teklif_ekle(teklif_bilgileri);<br/>
     * </i>
     * oncelikle bir vektor tanimlanarak veriler ekleniyor. sonra vektor enumeration olarak paketleniyor en sonunda
     * teklif verilerini iceren bu enumeration teklif_ekle fonksiyonuna yollanarak verinin  veritabanina islenmesi saglaniyor.
     *
     * @since 4 MAYIS 2008 17:10
     * */
    public void teklif_ekle(Enumeration teklif_bilgileri) throws ClassNotFoundException, SQLException {
        /**asagida tanimlanan i  for dongusunde enumeration cinsinden toplu gelen teklif_bilgileri verisini for dongusu icinde prepared statement cinsindekips'ye execution oncesinde yukler*/
        int i = 1;


        db.baglanti_kur();
        String veri_ekleme_sql = "INSERT INTO TEKLIFLER " +
                "(" +
                "TEKLIFNO," +
                "TEKLIF_TO," +
                "SON_GECERLILIK_TARIHI," +
                "YETKILI," +
                "TELEFON," +
                "FAX," +
                "TEKLIF_TARIHI" +
                ")" +
                " VALUES (?,?,?,?,?,?,?)";
        db.ps = db.c.prepareStatement(veri_ekleme_sql);


        while (teklif_bilgileri.hasMoreElements()) {
          
            db.ps.setObject(i, (String) teklif_bilgileri.nextElement());
 i++; 
 System.out.println("iiiiiii> "+i);

        }

        db.ps.executeUpdate();

        /**Asagidaki islem once resultsetin sonra baglantinin en son ise veritabaninin kapatilmasi islemidir.*/
       
        db.ps.close();
        db.c.close();
        db.baglanti_kapat();


    }

    /**
     * TABLO UZERINDE GERCEKLESEN HERHANGI BIR OLAY ILE SECILI SATIRIN ISTENEN SUTUNUNDAKI VERIYE ERISIR. SONUCU STRING OLARAK FIRLATIR
     * @param secim_yapilacak_tablo_adi : secim yapilacak tablo adidir
     * @param secilen_tablo_kolon_no : secilen kaydin istene sutunundaki veri 0, 1 onun yanindaki kolan .... olarak devam eder
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>System.out.println("SECILI KAYIT:"+op.secili_kayit(TABLO1, 0));</i><br>
     * Yukarıdaki ornek fonksiyon TABLO1'de secilen kaydin 0 nolu yani ilk sutununun icerigini okur. genellikle primary key ilk sutunda 
     * oldugundan ilk sutun okumalarinda bu fonksiyon kullanilabilir.
     * 7 MAYIS 2008: getValueAt esasına dayali olarak secilm yapilmasi hususunda degisliklik yapildi.
     * @since 8 MAYIS 2008
     * 
     */
    
    public String secili_kayit(javax.swing.JTable secim_yapilacak_tablo_adi,int secilen_tablo_kolon_no) {
         
        
  
    int r = secim_yapilacak_tablo_adi.getSelectedRow();
    String secili_veri=(String) secim_yapilacak_tablo_adi.getValueAt(r, secilen_tablo_kolon_no);
    System.out.println("Row:" + r);
    
        return secili_veri;
    }
    
    /**
     *Istenen tablo'ya bindirilmis verilerden ilgili tablodan istenen filtre ozelligindeki veriyi siler
     * @param silme_islemi_yapilacak_veritabani_tablosu veritabaninda silme isleminin yapicagi veritabaninin adidir
     * @param primary_key_adi  tekil silme islemine esas benzersiz primary_key'in sutun basligi nedir
     * @param silinecek_kaydin_anahtari bu parameterenin esit oldugu primary_key'i barindiran kayit silir
     * <br/>
     * Yukarıdaki 3 parametre tek basina ornegin DELETR FROM <b>TEKLIFLER</b> WHERE <b>TEKLIFNO</b> = <b>12345</b>
     * sorgusunu olusturur. burada koyu puntoyla gosteriken TEKLIFLER adi 1.parametre olarak, TEKLIFNO 2.parametere 12345 ise 3 parametre olarak yazilirsa sorgu tamamlanir.
     * 3. parametreyi secili_kayit metodu ile jtablodan da secebilrisiniz.
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>op.kayit_sil("TEKLIFLER", "TEKLIFNO", op.secili_kayit(TABLO1, 0));
     * yukaridaki fonksiyon TABLO1'e yuklenmis TEKLIFLER tablosunun TEKLIFNO'su secili kaydin 0.sutunundaki degerine esit olan veriyi siler. sonra teklif_listele komutu pesisira cagrilarak degisiklik aninda jtabloya yabsir.
     * </i>
     * @since  4 MAYIS 2008
     */
    public void kayit_sil(String silme_islemi_yapilacak_veritabani_tablosu,String primary_key_adi,String silinecek_kaydin_anahtari) throws SQLException, ClassNotFoundException
            
    {   String silme_sql="DELETE FROM "+silme_islemi_yapilacak_veritabani_tablosu+" WHERE "+primary_key_adi+"=?";
        
        db.baglanti_kur();
        db.ps=db.c.prepareStatement(silme_sql);
        db.ps.setObject(1, silinecek_kaydin_anahtari);
        db.ps.executeUpdate();
        db.ps.close();
        db.c.close();
        db.baglanti_kapat();
        
    }
    
  /**
   * 
   * DISARIDAN GELEN BILGILERI ESKI VERILERIN UZERINE YAZAR PRIMARY KEY'I ESAS ALIR
   * @param teklif_bilgileri_ve_guncellenecek_teklif_anahtari en son elemanı güncellenecek teklif anahtari geri kalanlar ise
   * guncellenecek teklife ait veriler olmak uzere enumeration cinsinden toplu veri.
   * @throws java.lang.ClassNotFoundException
   * @throws java.sql.SQLException
   * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
   * public void TEKLIF_GUNCELLE() throws ClassNotFoundException, SQLException {<br/>
        Vector teklif = new Vector();<br/>
       
        teklif.addElement(TXT_TEKLIF_TO.getText());<br/>
        teklif.addElement(TXT_SON_GECERLILIK_TARIHI.getText());<br/>
        teklif.addElement(TXT_YETKILI.getText());<br/>
        teklif.addElement(TXT_TELEFON.getText());<br/>
        teklif.addElement(TXT_FAX.getText());<br/>
        teklif.addElement(TXT_TEKLIF_TARIHI.getText());<br/>
        
        teklif.addElement(TXT_TEKLIFNO.getText()); //PRIMARY KEY OLARAK EKLENDI<br/>
        Enumeration teklif_bilgileri = teklif.elements();<br/>
        op.teklif_guncelle(teklif_bilgileri);<br/>
        LB_TEKLIF_OLUSTURMA_STATUS.setText("Teklif Güncellendi");<br/>
        DIA_TEKLIF_EDITORU.setVisible(false);<br/>

    }

   */
    
    public void teklif_guncelle(Enumeration teklif_bilgileri_ve_guncellenecek_teklif_anahtari ) throws ClassNotFoundException, SQLException
    {
        int i=1;
        db.baglanti_kur();
        db.ps=db.c.prepareStatement("UPDATE TEKLIFLER " +
                "SET " +
                "TEKLIF_TO=? ," +
                "SON_GECERLILIK_TARIHI=? ," +
                "YETKILI=? ," +
                "TELEFON=?  ," +
                "FAX=? ," +
                "TEKLIF_TARIHI=? " +
                "WHERE " +
                "TEKLIFNO=?");
while (teklif_bilgileri_ve_guncellenecek_teklif_anahtari.hasMoreElements()) {
          
            db.ps.setObject(i, (String) teklif_bilgileri_ve_guncellenecek_teklif_anahtari.nextElement());
 i++; 

        }

       db.ps.executeUpdate();

        /**Asagidaki islem once resultsetin sonra baglantinin en son ise veritabaninin kapatilmasi islemidir.*/
       
        db.ps.close();
        db.c.close();
        db.baglanti_kapat();     
        
        
    }
    
    public void tablo_resetle(String tabloadi) throws ClassNotFoundException, SQLException
    {
        
        //TODO FONKSIYON HATALI-DUZENLENECEK
        db.baglanti_kur();
        db.s=db.c.createStatement();
        db.s.executeQuery("TRUNCATE "+tabloadi);
        db.s.close();
        db.c.close();
        db.baglanti_kapat();
        System.out.println(tabloadi + " bosaltildi");
        
    }
    
    
    
    
   //------------------------------------------------------------------------------------------------------------
   //BURAYA KADARKI FONKSIYONLAR TEKLIFLER  TABLOSUNU VE HER IKI TABLO ICIN OLAN SILMEVE SECME ISLEMLERINI ILGILENDIRIR, BUNDAN SONRAKILER ISE EBATLAR TABLOSU VEBAGLI ISLEMLERDIR 
   //------------------------------------------------------------------------------------------------------------ 
    
    /**
     * Bu fonksiyon tekliflistesi veritabanindaki verileri parameterede adi gecen tabloya istenen sql sorgusu ile listeler
     * @param sorgu_sql tekliflistesi veritabanındaki verileri listelemek icin kullanılacak sorgudur. ornek: SELECT * FROM TEKLIFLISTESI ORDER BY EN
     * @param tablo_adi fonksiyon cagrilirken verinin bindirilecegi tablonun adidir. ornek: TABLO1
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>op.ebat_listele("SELECT * FROM TEKLIFLISTESI", TABLO1);</i>
     * <p>TEKLIFLISTESU VERITABANI KAYIP DURUMUNDA YENISI ICIN TEKLIFLER.grab dosyası mevcuttur</p>
     * 
     */
   public void ebat_listele(String sorgu_sql, javax.swing.JTable tablo_adi) throws ClassNotFoundException, SQLException {
       
       int satiradedi=satir_adedi(sorgu_sql);
        db.baglanti_kur();
        ResultSet RS = db.s.executeQuery(sorgu_sql);

        OBJ = new Object[satiradedi][11];
        String sutun_basliklari[] = {"TEKLIFNO", "BOY", "EN", "YUKSEKLIK", "DALGA", "KALITE", "SAFIAENI","FIYATSAFIAENI","SAFIABOYU","BIRIMFIYAT","EBAT_SERI_NO"};

        int i = 0;
        while (RS.next()) {
            i++;

            OBJ[i][0] = RS.getString(1);
            OBJ[i][1] = RS.getString(2);
            OBJ[i][2] = RS.getString(3);
            OBJ[i][3] = RS.getString(4);
            OBJ[i][4] = RS.getString(5);
            OBJ[i][5] = RS.getString(6);
            OBJ[i][6] = RS.getString(7);
            OBJ[i][7] = RS.getString(8);
            OBJ[i][8] = RS.getString(9);
            OBJ[i][9] = RS.getString(10);
            OBJ[i][10] = RS.getString(11);
            

        }

        /**Asagidaki islem verinin tabloya bindirilmesi surecidir.*/
        javax.swing.table.TableModel jtm = new DefaultTableModel(OBJ, sutun_basliklari);
        tablo_adi.setModel(jtm);
        
        /**Asagidaki islem once resultsetin sonra baglantinin en son ise veritabaninin kapatilmasi islemidir.*/
        RS.close();
        db.c.close();
        db.baglanti_kapat();
    }
   
   
    /**
     * bu fonksiyon, disaridan enumeration olarak gelen teklif verilerini TEKLIFLER tablosuna munferit olarak ekler. baglantiyi otomatik olarak kapatir. eklenen teklifin tabloda hemen gozukmesi icin teklif_listele komutunun bu komuttan sonra tekrar cagrilmasi gerekir.
     * @param ebat_bilgileri : bir teklif girdisine ait sirali tum verileri icerir.
     * <h2>BU FONKSIYONUN KULLANIMINA ORNEK</h2>
     * <i>Vector ebat = new Vector();<br/>
    
    ebat.addElement("333");<br/>
    ebat.addElement("boy SUATATAN");<br/>
    ebat.addElement("en GECERLILIK 12/12/2007");<br/>
    ebat.addElement("yks  SUAT ATAN");<br/>
    ebat.addElement("dlg 04325512361");<br/>
    ebat.addElement("kalite 04322166620");<br/>
    ebat.addElement(null);//tarih girmedik<br/>
     * "
     * "
     * "
     * " 
    Enumeration ebat_bilgileri = ebat.elements();<br/>
    op.ebat_ekle(teklif_bilgileri);<br/>
     * </i>
     * oncelikle bir vektor tanimlanarak veriler ekleniyor. sonra vektor enumeration olarak paketleniyor en sonunda
     * teklif verilerini iceren bu enumeration ebat_ekle fonksiyonuna yollanarak verinin  veritabanina islenmesi saglaniyor.
     *
     * @since 5 MAYIS 2008 23:15
     * */
   
   public void ebat_ekle(Enumeration ebat_bilgileri) throws ClassNotFoundException, SQLException
   {
       int i=0;
       db.baglanti_kur();
        String veri_ekleme_sql = "INSERT INTO TEKLIFLISTESI " +
                "(" +
                "TEKLIFNO," +
                "BOY," +
                "EN," +
                "YUKSEKLIK,"+
                "DALGA," +
                "KALITE," +
                "SAFIAENI," +
                "FIYATSAFIAENI," +
                "SAFIABOYU," +
                "BIRIMFIYAT," +
                "EBAT_SERI_NO" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        db.ps = db.c.prepareStatement(veri_ekleme_sql);
       while (ebat_bilgileri.hasMoreElements()) {
            i++;
            db.ps.setObject(i, (String) ebat_bilgileri.nextElement());


        }

        db.ps.executeUpdate();

        /**Asagidaki islem once resultsetin sonra baglantinin en son ise veritabaninin kapatilmasi islemidir.*/
       
        db.ps.close();
        db.c.close();
        db.baglanti_kapat();
        
       
       
   }
   
    
    
    
}


