
package net.impjq.androidproxydaemon;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AndroidProxyDaemon extends Activity {
    /** Called when the activity is first created. */
    private ProxyHandler mProxyHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mProxyHandler = new ProxyHandler();

        mProxyHandler.sendEmptyMessage(PROXY_WITH_PROXY);
        
        //mProxyHandler.sendEmptyMessage(PROXY_WITH_PROPERTY);
        
    }

    public static final int PROXY_WITH_PROPERTY = 0;

    public static final int PROXY_WITH_PROXY = PROXY_WITH_PROPERTY + 1;

    class ProxyHandler extends Handler {
        public void handleMessage(Message message) {
            int what = message.what;

            switch (what) {
                case PROXY_WITH_PROPERTY:
                    HttpProxyWithProperty.startTest();

                    break;

                case PROXY_WITH_PROXY:
                    HttpProxyWithProxy.startTest();

                    break;

                default:
                    break;
            }
        }
    }
}
