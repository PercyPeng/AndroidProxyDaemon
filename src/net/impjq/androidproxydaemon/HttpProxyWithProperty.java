
package net.impjq.androidproxydaemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import net.impjq.androidproxydaemon.Utils.*;

/**
 * Show how to set Http(s) Proxy. Run the function
 * {@link HttpProxyWithProperty#startTest} to start the test.
 * 
 * @author Percy.Peng
 * @see #setProxy()
 * @see #getHttpData(String)
 * @see #getHttpsData(String)
 */
public class HttpProxyWithProperty {

    /**
     * Start the test here.
     */
    public static void startTest() {
        System.out.println("main");
        setProxyWithProperty();

        String httpHtml = getHttpData(Const.httpURL);
        String httpsHtml = getHttpsData(Const.httpsURL);

        System.out.println(httpHtml);
        System.out.println(httpsHtml);
    }

    /**
     * Set Proxy here,including http proxy and https proxy
     */
    public static void setProxyWithProperty() {
        // Set Http Proxy
        System.getProperties().setProperty("http.proxyHost", Const.proxyHost);
        System.getProperties().setProperty("http.proxyPort", ""+Const.proxyPort);

        // Set Https Proxy
        // FIXME Should import the certificates to avoid the trust problem.
        // To avoid the certification problems,just trust all the certs.
        // SSLUtilities.trustAllHttpsCertificates();
        System.getProperties().setProperty("https.proxyHost", Const.proxyHost);
        System.getProperties().setProperty("https.proxyPort", ""+Const.proxyPort);
    }

    /**
     * Post a data to the url
     * 
     * @param urlString a URL String
     * @param data The data to be posted
     */
    private static void post(String urlString, String data) {
        try { // Construct data
            String encodedData = URLEncoder.encode(data, "UTF-8");

            // Send data
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(encodedData);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
    }

    private static String LOGTAG = "UserAgent";

    private static String UA = "Mozilla/5.0 (MOT-XT800/TITA_M2_15.10.1:U:Android/2.0.1:480*320;CTC/2.0) AppleWebKit/528.5+(KHTML,like Gecko) Version/3.1.2 Mobile/5G77 Safari/525.20.1";

    // private static String URL = "http://10.234.63.196/portal/wap";

    /**
     * Get data(Html) from a http url
     * 
     * @param url A url String
     * @return The contents for the url or null if failed
     */
    public static String getHttpData(String urlString) {
        String result = null;

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            // conn.setRequestProperty("User-Agent", UA);
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            result = Utils.readFromInputStream(isr);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Get data(Html) from a https url
     * 
     * @param urlString A URL String
     * @return The Data from the urlString or null if failed
     */
    public static String getHttpsData(String urlString) {
        String result = null;
        URL url = null;

        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                // TODO Auto-generated method stub
                // System.out.println("Warning: URL Host: " + arg0 + " vs. " +
                // arg1.getPeerHost());
                return true;
            }
        };

        try {
            url = new java.net.URL(urlString);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("Get data Failed:" + urlString);
            return result;
        }

        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        HttpsURLConnection conn;

        try {
            conn = (javax.net.ssl.HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            result = Utils.readFromInputStream(isr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

}
