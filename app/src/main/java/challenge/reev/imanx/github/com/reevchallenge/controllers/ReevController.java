package challenge.reev.imanx.github.com.reevchallenge.controllers;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;

import challenge.reev.imanx.github.com.network.OnCallbackListener;
import challenge.reev.imanx.github.com.network.Request;
import challenge.reev.imanx.github.com.network.method.Post;
import challenge.reev.imanx.github.com.reevchallenge.models.Reev;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public class ReevController {
    private HashMap<String, String> params = new HashMap<>();
    private Context                 context;

    public ReevController(Context context) {
        this.context = context;
        params.put("rover_id", "12856482");
    }

    public void getCommand(OnCallbackListener listener) {
        new Request(context, new Post("https://roverapi.reev.ca", params)).get(listener);

    }

}
