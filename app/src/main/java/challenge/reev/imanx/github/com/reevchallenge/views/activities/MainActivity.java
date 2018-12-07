package challenge.reev.imanx.github.com.reevchallenge.views.activities;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import challenge.reev.imanx.github.com.network.OnCallbackListener;
import challenge.reev.imanx.github.com.reevchallenge.R;
import challenge.reev.imanx.github.com.reevchallenge.VibrationProvider;
import challenge.reev.imanx.github.com.reevchallenge.controllers.ReevController;
import challenge.reev.imanx.github.com.reevchallenge.databinding.ActivityMainBinding;
import challenge.reev.imanx.github.com.reevchallenge.models.Reev;
import challenge.reev.imanx.github.com.reevchallenge.views.surfaceView.OnDoCommandListener;
import challenge.reev.imanx.github.com.reevchallenge.views.surfaceView.ReevSurfaceView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProgressDialog      dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Resolving data from 'Reev.ca' ...");
        dialog.show();


        new ReevController(this).getCommand(callbackListener);
    }


    private OnCallbackListener callbackListener = new OnCallbackListener() {
        @Override
        public void onSuccessResponse(JSONObject jsonObject, String content) {
            final Reev reev = new GsonBuilder().create().fromJson(content, Reev.class);
            dialog.dismiss();
            binding.surface.post(new Runnable() {
                @Override
                public void run() {
                    binding.surface.start(reev.startPosition, reev.weirs, commandListener);

                }
            });

            binding.surface.doCommands(reev.getCommands());

        }

        @Override
        public void onFailureResponse(int httpCode, String dataError) {

        }

        @Override
        public void onFailureConnection() {

        }
    };


    private OnDoCommandListener commandListener = new OnDoCommandListener() {
        @Override
        public void onMove(char moveCommand, Reev.Position position) {
            String log = String.format("Reev moved to %s in Position %s", moveCommand, position.getCaption());
            Log.i("TAG", "onMove: " + log);
        }

        @Override
        public void onCrash(Reev.Position position) {
            VibrationProvider.make(getApplication(), 1000);
            Log.i("TAG", "onCrash: " + position.getCaption());
        }
    };
}
