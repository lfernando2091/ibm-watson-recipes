/*
 * Copyright Lyo(c) 2017
 *  Autorized Luis Fernando Hernández Méndez
 * signature
 * b56a75f4cd38f40f3a0f4ded26e3902fe8f172594c9d9fafe889a2f4f8f5145a
 */

package com.media.doopy.Post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PostMetod implements Serializable {
    static final long serialVersionUID = -7607619678367524000L;
    private static PostListener listener;
    private JSONObject jsonObj;
    private StringBuilder msg;
    private static PostMetod instance;

    public static PostMetod getInstance(PostListener list){
        if(instance == null)
            instance = new PostMetod();
        listener=list;
        return instance;
    }

    private PostMetod() {
    }

    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private HttpsURLConnection HttpsC(URL url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {}
            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {}
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {return new java.security.cert.X509Certificate[] {};}
        } };
        // Create the SSL connection
        SSLContext sc= SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        //establecemos la conexión con el destino
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setHostnameVerifier(DO_NOT_VERIFY);
        return urlConnection;
    }

    private HttpsURLConnection HttpsS(URL url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        // Create the SSL connection
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, null, new java.security.SecureRandom());
        urlConnection.setSSLSocketFactory(sc.getSocketFactory());
        return urlConnection;
    }

    public void Start(String urls, String[] key, String[] value) throws MalformedURLException {
        try {
            listener.onConnectionStarts();
            new DownloadRawData(urls).execute(createUrl(key,value));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void Gone(String urls, String[] key, String[] value,PostListener list) throws MalformedURLException {
        try {
            listener=list;
            listener.onConnectionStarts();
            setListenerData(DownloadRawData(urls,createUrl(key,value)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void Up(String urls,PostListener list,JSONObject jsonObject) throws MalformedURLException {
        listener=list;
        listener.onConnectionStarts();
        setListenerData(UpDownloadRawData(urls,jsonObject));
    }

    public void Up(String urls, PostListener list, File obj, String[] key, String[] value) throws MalformedURLException {
        listener=list;
        listener.onConnectionStarts();
        try {
            UpDownloadRawData(urls,obj,createUrl(key,value));
            //setListenerData(UpDownloadRawData(urls,obj,createUrl(key,value)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String createUrl(String[] key, String[] value) throws UnsupportedEncodingException {
        Uri.Builder builder = new Uri.Builder();
        if(key.length==value.length) for (int t=0; t<key.length; t++) builder.appendQueryParameter(key[t], value[t]);
        return builder.build().getEncodedQuery();
    }

    private String DownloadRawData(String urls,String link){
        try {
            URL url = new URL(urls);
            //establecemos la conexión con el destino //TODO camviar a HttpsS
            //HttpsURLConnection https = HttpsS(url);
            HttpURLConnection https = (HttpURLConnection) url.openConnection();
            https.setRequestMethod("POST");
            https.setDoInput(true);
            https.setDoOutput(true);
            OutputStream os = https.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(link);
            writer.flush();
            writer.close();
            os.close();
            https.connect();

            InputStream is = https.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) buffer.append(line + "\n");
            https.disconnect();
            reader.close();
            is.close();
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DownloadRawData extends AsyncTask<String, Void, String> {
        private String url;
        private DownloadRawData(String url) {
            this.url = url;
        }
        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(this.url);
                //establecemos la conexión con el destino //TODO camviar a HttpsS
                //HttpsURLConnection https = HttpsS(url);
                HttpURLConnection https = (HttpURLConnection) url.openConnection();
                https.setRequestMethod("POST");
                //https.setConnectTimeout(1000*10);
                https.setDoInput(true);
                https.setDoOutput(true);
                OutputStream os = https.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(link);
                writer.flush();
                writer.close();
                os.close();
                https.connect();

                InputStream is = https.getInputStream();
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) buffer.append(line).append("\n");
                https.disconnect();
                reader.close();
                is.close();
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String data) {
            setListenerData(data);
        }
    }

    private String UpDownloadRawData(String urls,JSONObject jsonObject){
        try {
            URL url = new URL(urls);
            //establecemos la conexión con el destino //TODO camviar a HttpsS
            //HttpsURLConnection https = HttpsS(url);
            HttpURLConnection https = (HttpURLConnection) url.openConnection();
            https.setRequestMethod("POST");
            https.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            https.setDoInput(true);
            https.setDoOutput(true);
            OutputStream os = https.getOutputStream();
            os.write(jsonObject.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            https.connect();

            InputStream is = https.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) buffer.append(line + "\n");
            https.disconnect();
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String UpDownloadRawData(String urls,File bitObject,String link){
        if(!bitObject.exists())return "";
        try {
            URL url = new URL(urls+"?"+link);
            //establecemos la conexión con el destino //TODO camviar a HttpsS
            //HttpsURLConnection https = HttpsS(url);
            HttpURLConnection https = (HttpURLConnection) url.openConnection();
            https.setRequestMethod("POST");
            https.setRequestProperty("Content-Type", "image/jpeg");
            https.setDoInput(true);
            https.setDoOutput(true);
            OutputStream os = https.getOutputStream();
            os.write(getBitmap(bitObject));
            os.flush();
            os.close();
            https.connect();

            InputStream is = https.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) buffer.append(line + "\n");
            https.disconnect();
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBitmap(File picturePath){
        if(!picturePath.exists())return new byte[0];
        Bitmap bm = BitmapFactory.decodeFile(picturePath.getPath());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        return bao.toByteArray();
    }

    private byte[] getByte(File file) {
        byte[] getBytes = {};
        try {
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }

    public static void setListenerData(String s) {
        listener.onDataReceived(s);
    }

}
