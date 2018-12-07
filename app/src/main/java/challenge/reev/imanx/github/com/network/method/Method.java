package challenge.reev.imanx.github.com.network.method;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Method {
    private String                  url;
    private HashMap<String, String> header;

    public Method(@NonNull String url, @Nullable HashMap<String, String> header) {
        this.url = url;
        this.header = header;
    }


    public Method(String url) {
        this(url, null);
    }

    public String getUrl() {
        return url;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public okhttp3.Request getRequest() {


        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.url(url);

        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.addHeader("Accept", "application/json");
        return builder.build();
    }
}
