
package net.impjq.androidproxydaemon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

/**
 * Use {@link java.net.Proxy#Proxy} to set proxy when use the
 * {@link java.net.URL} to openConnection. Start the function
 * {@link HttpProxyWithProxy#startTest()} to run the test.
 * 
 * @author Percy.Peng
 */
public class HttpProxyWithProxy {

    /**
     * Start the test here.
     */
    public static void startTest() {
        Utils.log("*********************Proxy.Type.HTTP******************************");
        Proxy.Type type = Proxy.Type.HTTP;
        String result = getHttpDataWithProxy(type);
        Utils.log(result);

        Utils.log("*********************Proxy.Type.SOCKS******************************");
        type = Proxy.Type.SOCKS;
        result = getHttpDataWithProxy(type);
        Utils.log(result);
    }

    /**
     * Set Proxy when use {@link java.net.URL#openConnection(Proxy)} to get the
     * data.
     */
    public static String getHttpDataWithProxy(Proxy.Type type) {
        // Set Http Proxy
        String result = null;
        String host = Const.proxyHost;
        int port = Const.proxyPort;
        SocketAddress socketAddress = new InetSocketAddress(host, port);

        // All have three Proxy Type
        Proxy proxy = new Proxy(type, socketAddress);

        try {
            URL url = new URL(Const.httpURL);
            URLConnection uc = url.openConnection(proxy);
            InputStreamReader iStreamReader = new InputStreamReader(uc.getInputStream());
            result = Utils.readFromInputStream(iStreamReader);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}
