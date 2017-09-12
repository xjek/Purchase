package ru.groupstp.procurementcommission.connection;

import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import ru.groupstp.procurementcommission.BuildConfig;
import ru.groupstp.procurementcommission.connection.listener.BaseConnectionListener;

/**
 * Базовый класс с подключением
 * @param <T>
 */
public abstract class BaseConnection<T extends BaseConnectionListener> {

    private T mBaseConnectionListener;

    public BaseConnection(T listener) {
        mBaseConnectionListener = listener;
    }

    protected static final String BASE_URL = BuildConfig.BASE_URL;

    public static OkHttpClient sOkHttpClient = getUnsafeOkHttpClient();

    abstract public void connection(Object ... params);

    protected T getListener() {
        return mBaseConnectionListener;
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Observable<JSONObject> getObsDecode(JSONObject object) {
        return Observable.fromCallable(() -> {
            JSONObject message = object.getJSONObject("message");
            String data = message.getString("data");
            String resultData = new String(decompress(data));
            return new JSONObject(resultData);
        });
    }

    private byte[] decompress(String data) throws IOException, DataFormatException {
        byte[] b = Base64.decode(data.getBytes(), Base64.DEFAULT);
        Inflater inflater = new Inflater();
        inflater.setInput(b);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(b.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
}
