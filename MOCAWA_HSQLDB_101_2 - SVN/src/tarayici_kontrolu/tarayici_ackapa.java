//TARAYICI KONTROL KUTUPHANESI 
//SUAT ATAN TARAFINDAN YAZILDI
package tarayici_kontrolu;

import java.io.IOException;
import java.io.PrintStream;

public class tarayici_ackapa
{

    private static final String WIN_ID = "Windows";
    private static final String WIN_PATH = "rundll32";
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
    private static final String UNIX_PATH = "netscape";
    private static final String UNIX_FLAG = "-remote openURL";

    public tarayici_ackapa()
    {
    }
/**
 * 
 * 
 * ISTENEN WEB SAYFASINA ISLETIM SISTEMI VARSAYILAN TARAYICISI ILE GIDER
 * @param url gidilmek istenen sitedir
 */
    

    public static void internet_adresi_ac(String url)
    {
        boolean windows = isWindowsPlatform();
        String cmd = null;
        try
        {
            if(windows)
            {
                cmd = (new StringBuilder()).append("rundll32 url.dll,FileProtocolHandler ").append(url).toString();
                Process p = Runtime.getRuntime().exec(cmd);
            } else
            {
                cmd = (new StringBuilder()).append("netscape -remote openURL(").append(url).append(")").toString();
                Process p = Runtime.getRuntime().exec(cmd);
                try
                {
                    int exitCode = p.waitFor();
                    if(exitCode != 0)
                    {
                        cmd = (new StringBuilder()).append("netscape ").append(url).toString();
                        p = Runtime.getRuntime().exec(cmd);
                    }
                }
                catch(InterruptedException x)
                {
                    System.err.println((new StringBuilder()).append("Error bringing up browser, cmd='").append(cmd).append("'").toString());
                    System.err.println((new StringBuilder()).append("Caught: ").append(x).toString());
                }
            }
        }
        catch(IOException x)
        {
            System.err.println((new StringBuilder()).append("Could not invoke browser, command=").append(cmd).toString());
            System.err.println((new StringBuilder()).append("Caught: ").append(x).toString());
        }
    }

    public static boolean isWindowsPlatform()
    {
        String os = System.getProperty("os.name");
        return os != null && os.startsWith("Windows");
    }
}
