/*
 * BU DOSYANIN AMACI HER SEFERINDE CAGIRLACAK PROSEDURLERI TEK SEFERDE CAGIRMAK VE IMPLEMENT EDILEN TUM CLASSLARDA TEK
 * SEFERDE KULLANMAKTIR. 
 * 
 */

package global;

import ch.ubique.inieditor.IniEditor;

/**
 *
 * @author suatatan
 */
public interface global_variables {

    
    /**<i>INI DOSYASINA ERISIM CAGIRMA</i>*/
    IniEditor yapilandirma_dosyasindan = new IniEditor();
    /**<i>LOGLAYICI CAGIRMA ERISIR</i>*/
    hsqldb_suatatan_lib.logger o=new hsqldb_suatatan_lib.logger();
    /**<i>VERITABANI ISLEMCISINE ERISIR</i>*/
    hsqldb_suatatan_lib.veritabani_islemleri db=new hsqldb_suatatan_lib.veritabani_islemleri();
    /**<i>MOCAWA PROGRAMINA HAS VERITABANI ISLEM FONKSIYONLARI KULLANIR<i>*/
    mocawa_hsqldb_101_2.mocawa_ozel_db_islemleri  op=new mocawa_hsqldb_101_2.mocawa_ozel_db_islemleri();
    /**<i>keymakere erisir</i>*/
    hsqldb_suatatan_lib.keymaker key= new hsqldb_suatatan_lib.keymaker();
    /**<i>teklif hesaplama modulunceki componentlere erisi</i>*/
    mocawa_hsqldb_101_2.index in= new mocawa_hsqldb_101_2.index();
    /**<i>anaekran componentlerineerisir</i>*/
    mocawa_hsqldb_101_2.anaekran ana= new mocawa_hsqldb_101_2.anaekran();
    /**<i>hata ekranini acar</i>*/
    mocawa_hsqldb_101_2.hataekrani hata=new mocawa_hsqldb_101_2.hataekrani();
    /**<i>TARAYICI KONTROLU</i>*/
    tarayici_kontrolu.tarayici_ackapa browser=new tarayici_kontrolu.tarayici_ackapa();
     /**<i>LİSANS KONTROLU</i>*/
    licencor.lisans_kontrol lis=new licencor.lisans_kontrol();
    licencor.serialekrani serialekrani= new licencor.serialekrani();
    
    
    
}
