package dimogdroid.com.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dimogdroid.com.util.CustomLog;


public class Conexion {
    
    public static final int POST_TYPE = 1;
    public static final int GET_TYPE = 2;
    
    private static final String TIEMPO = "http://www.tiempo.es/";




    private static final String HOME = "http://www.tiempo.es/sevilla-sevilla-es0se0093.html";
    //private static final String HOME2 = "";


    String url;


    public Conexion() {

        url=HOME;
        
    }

    private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

    public static void checkConnection(Context context) throws ConnectionException {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!networkInfo.isConnected()) {
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        if (!networkInfo.isConnected()) {
            throw new ConnectionException(ConnectionException.ConnectionError.NO_CONNECTION);
        }
    }



    @SuppressWarnings("deprecation")
    public InputStream conectar(Context context, String urlActiva) throws ConnectionException {
        
        checkConnection(context);
        
        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        int timeoutConnection = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        DefaultHttpClient client = new DefaultHttpClient(httpParameters);
        
        HttpRequestBase method = null;
        try {
            //method = new HttpPost(url);
            method = new HttpGet(urlActiva);
        } catch (IllegalArgumentException e) {
            CustomLog.error("conectar", e.getMessage());
        }
        
        InputStream result = null;
        if (method != null) {
            try {
                HttpResponse response = client.execute(method);
                StatusLine status = response.getStatusLine();
                int codigo = status.getStatusCode();
                CustomLog.debug("conectar", "Status code: " + codigo);
                if (codigo == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        try {
                            result = entity.getContent();
                        } catch (IOException e) {
                            CustomLog.error("conectar", e.getMessage());
                            throw new ConnectionException(ConnectionException.ConnectionError.READING_ERROR);
                        }
                    } else {
                        CustomLog.error("conectar", status.getReasonPhrase());
                        throw new ConnectionException(ConnectionException.ConnectionError.NO_DATA);
                    }
                } else {
                    throw new ConnectionException(ConnectionException.ConnectionError.getErrorByCode(codigo));
                }
            } catch (IOException e) {
                CustomLog.error("conectar", "Error: " + e.getMessage()); // Conexion rechazada
                throw new ConnectionException(ConnectionException.ConnectionError.CONNECTION_REJECTED);
            }
        }
        return result;

    }

}