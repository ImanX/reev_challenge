package challenge.reev.imanx.github.com.network;

import org.json.JSONObject;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public interface OnCallbackListener {
    void onSuccessResponse(JSONObject jsonObject, String content);

    void onFailureResponse(int httpCode, String dataError);

     void onFailureConnection();
}
