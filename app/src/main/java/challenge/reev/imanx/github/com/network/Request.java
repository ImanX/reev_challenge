package challenge.reev.imanx.github.com.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import challenge.reev.imanx.github.com.network.method.Method;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ImanX.
 * MyZarinPal-Android-V4 | Copyrights 2018 ZarinPal Crop.
 */

public class Request {

    private Context              context;
    private Method               method;
    private OkHttpClient.Builder client;

    public Request(Context context, Method method) {
        this.context = context;
        this.method = method;
        this.client = new OkHttpClient().newBuilder();
    }

    public Request setTimeout(int sec) {
        this.client.connectTimeout(sec, TimeUnit.SECONDS);
        return this;
    }


    private boolean hasConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }

        return networkInfo.isAvailable() && networkInfo.isConnected();
    }


    public void get(final OnCallbackListener listener) {

        if (!hasConnection()) {
            listener.onFailureConnection();
            return;
        }


        this.client.build()
                .newCall(method.getRequest())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onFailureResponse(0, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.body() == null) {
                            return;
                        }

                        String body = response.body().string();


                        if (!response.isSuccessful()) {
                            listener.onFailureResponse(response.code(), body);
                            return;
                        }


                        try {
                            listener.onSuccessResponse(new JSONObject(body), body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}


