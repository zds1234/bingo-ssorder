package jtemp.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Http {

    static final int TIMEOUT = 300000;
    public static final String CHARSET = "UTF-8";

    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String httpGetContent(String url, String encoding) throws Exception {
        HttpResponse data = httpGet(url);
        return data.isSuccess() ? new String(data.getData(), encoding) : null;
    }

    public static HttpResponse httpGet(String url) throws Exception {
        return httpGet(url, new HashMap<String, String>());
    }

    public static HttpResponse httpGet(String url, Map<String, String> header) throws Exception {
        return http(HttpMethod.GET, url, null, header, TIMEOUT);
    }

    public static HttpResponse httpPost(String url, byte[] data) throws Exception {
        return httpPost(url, data, new HashMap<String, String>());
    }

    public static HttpResponse httpPost(String url, byte[] data, Map<String, String> header) throws Exception {
        return http(HttpMethod.POST, url, data, header, TIMEOUT);
    }

    public static HttpResponse http(HttpMethod method, String url, byte[] data, Map<String, String> header) throws Exception {
        return http(method, url, data, header, TIMEOUT);
    }

    /**
     * @param url
     * @param data
     * @param timeout
     * @return
     * @throws IOException
     * @throws MalformedURLException
     */
    public static HttpResponse http(HttpMethod method, String url, byte[] data, Map<String, String> header, int timeout) throws Exception {
        HttpURLConnection conn = null;
        if (url.startsWith("http://")) {
            conn = (HttpURLConnection) new URL(url).openConnection();
        } else if (url.startsWith("https://")) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) new URL(url).openConnection();
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn = httpsConn;
        } else {
            throw new RuntimeException("invalidate url :" + url);
        }
        final HttpURLConnection finalConn = conn;
        try {
            finalConn.setRequestMethod(method.name());
            finalConn.setDoOutput(true);
            finalConn.setDoInput(true);
            finalConn.setUseCaches(false);
            for (Map.Entry<String, String> entry : header.entrySet()) {
                finalConn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            finalConn.connect();
            if (data != null) {
                finalConn.getOutputStream().write(data);
            }
            int responseCode = finalConn.getResponseCode();
            byte[] responseData = null;
            try {
                InputStream is = finalConn.getInputStream();
                responseData = toByteArray(is);
            } catch (Exception e) {
                responseData = null;
            }
            boolean success = false;
            if (finalConn.getResponseCode() == 200) {
                success = true;
            }
            return new HttpResponse(responseCode, success, responseData);
        } finally {
            finalConn.disconnect();
        }
    }

    private static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = -1;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}

