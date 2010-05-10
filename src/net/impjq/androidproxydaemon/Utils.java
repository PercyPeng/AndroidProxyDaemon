
package net.impjq.androidproxydaemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

/**
 * Define some common functions here.
 * 
 * @author Percy.Peng
 */
public class Utils {
    private static final boolean DEBUG_ENABLE = true;

    private static final String tag = "AndroidProxyDaemon.Utils";

    public static void log(String tag, String msg) {
        if (!DEBUG_ENABLE) {
            return;
        }

        if (null == tag) {
            log(msg);
            return;
        }

        Log.i(tag, ""+msg);
    }

    public static void log(String msg) {
        if (!DEBUG_ENABLE) {
            return;
        }

        Log.i(tag, ""+msg);
    }
    
    /**
     * Read data from InputStreamReader
     * 
     * @param isr InputStreamReader
     * @return The Data read from The InputStreamReader
     */
    public static String readFromInputStream(InputStreamReader isr) {
        String result = null;

        BufferedReader rd = new BufferedReader(isr);
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                result += line + '\n';
            }

            isr.close();
            rd.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

}
