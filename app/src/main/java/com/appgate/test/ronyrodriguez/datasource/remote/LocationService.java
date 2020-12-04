package com.appgate.test.ronyrodriguez.datasource.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationService {

    private Context mContext;
    private HttpClient mClient;
    private String mAction;
    private RemoteListener remoteListener;

    public void callService(RemoteListener remoteListener, double latitude, double longitude) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String resultjson = getJSON(latitude, longitude);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Timezone timezone = gson.fromJson(resultjson, Timezone.class);
                        remoteListener.onGetLocation(timezone);
                    }
                });
            }
        });
    }

    private String getJSON(double latitude, double longitude) {
        HttpURLConnection httpUrlConnection = null;
        try {
            String urlpath = "http://api.geonames.org/timezoneJSON?formatted=true&lat=" + String.valueOf(latitude) + "&lng=" + String.valueOf(longitude) + "&username=qa_mobile_easy&style=full";
            URL u = new URL(urlpath);
            httpUrlConnection = (HttpURLConnection) u.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Content-length", "0");
            httpUrlConnection.connect();
            int status = httpUrlConnection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (Exception ex) {
        }
        return "";
    }

    public interface RemoteListener {
        void onGetLocation(Timezone timezone);
    }
}
