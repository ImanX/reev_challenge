package challenge.reev.imanx.github.com.reevchallenge.views.activities;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

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
    private Toolbar             toolbar;

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
            dialog.dismiss();
            showToast(String.format("%s: %s", httpCode, dataError));
        }

        @Override
        public void onFailureConnection() {
            dialog.dismiss();
            showToast("Connection Failure");
        }
    };


    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private OnDoCommandListener commandListener = new OnDoCommandListener() {
        @Override
        public void onMove(char moveCommand, Reev.Position position) {
            String log = String.format("Reev Moved to %s in Position %s", moveCommand, position.getCaption());
            getSupportActionBar().setTitle(log);
        }

        @Override
        public void onCrash(Reev.Position position) {
            VibrationProvider.make(getApplication(), 1000);
            String log = String.format("Reev Crashed in %s Position", position.getCaption());
            getSupportActionBar().setTitle(log);
        }

        @Override
        public void onFinish(Reev.Position position) {
            String log = String.format("Reev Finished in %s Position", position.getCaption());
            getSupportActionBar().setTitle(log);
        }
    };
}
