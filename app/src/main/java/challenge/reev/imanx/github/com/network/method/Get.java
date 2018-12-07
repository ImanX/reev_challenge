package challenge.reev.imanx.github.com.network.method;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

public class Get extends Method {
    public Get(String url) {
        super(url);
    }

    public Get(@NonNull String url, @Nullable HashMap<String, String> header) {
        super(url, header);
    }
}
