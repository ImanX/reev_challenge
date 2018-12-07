package challenge.reev.imanx.github.com.network.method;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import challenge.reev.imanx.github.com.network.Request;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Post extends Method {

    private String                  json;
    private HashMap<String, String> params;

    public Post(@NonNull String url, JSONObject json) {
        super(url);
        this.json = json.toString();
    }

    public Post(@NonNull String url, @Nullable HashMap<String, String> header, JSONObject json) {
        super(url, header);
        this.json = json.toString();
    }

    public Post(@NonNull String url, @Nullable HashMap<String, String> header, Object object) {
        super(url, header);
        this.json = new GsonBuilder().create().toJson(object);
    }

    public Post(@NonNull String url, Object object) {
        super(url, null);
        this.json = new GsonBuilder().create().toJson(object);
    }

    public Post(@NonNull String url, HashMap<String, String> params) {
        this(url, (Object) null);
        this.params = params;

    }


    @Override
    public okhttp3.Request getRequest() {
        okhttp3.Request request = super.getRequest();
        RequestBody     body    = null;
        if (params == null) {
            MediaType type = MediaType.parse("application/json; charset=utf-8");
            body = RequestBody.create(type, json);
        } else {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(entry.getKey(), entry.getValue()).build();
            }
        }


        return request.newBuilder().post(body).build();

    }
}
