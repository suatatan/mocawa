/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package licencor;

import ch.ubique.inieditor.IniEditor;
import java.io.IOException;

/**
 *
 * @author Suat ATAN
 */
public class lisans_kontrol {
    
    public boolean program_lisansli_mi() throws IOException

    {
        boolean t;
        
            IniEditor i = new IniEditor();
            i.load("beyin\\mocawa.dll");
            if(i.get("mocawasys", "code").equals("ok")){
                
               t=true; 
            }
            else
            {
                t=false;
            }
            
        return t;
    }
    

}
