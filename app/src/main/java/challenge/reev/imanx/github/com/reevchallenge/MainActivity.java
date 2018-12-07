package challenge.reev.imanx.github.com.reevchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import challenge.reev.imanx.github.com.network.OnCallbackListener;
import challenge.reev.imanx.github.com.reevchallenge.models.Reev;
import challenge.reev.imanx.github.com.reevchallenge.views.OnDoCommandListener;
import challenge.reev.imanx.github.com.reevchallenge.views.ReevSurfaceView;

public class MainActivity extends AppCompatActivity {

    private ReevSurfaceView table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.table = findViewById(R.id.table);
        this.table.setListener(commandListener);
        new ReevController(this).getCommand(callbackListener);
    }


    private OnCallbackListener callbackListener = new OnCallbackListener() {
        @Override
        public void onSuccessResponse(JSONObject jsonObject, String content) {
            final Reev reev = new GsonBuilder().create().fromJson(content, Reev.class);


            table.post(new Runnable() {
                @Override
                public void run() {
                    table.start(reev.startPosition, reev.weirs);
                }
            });


            table.doCommands(reev.getCommands());

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
            Log.i("TAG", "onMove: " + moveCommand + " pos: " + position.getCaption());
        }

        @Override
        public void onCrash(Reev.Position position) {
            VibrationProvider.make(getApplication(), 1000);
            Log.i("TAG", "onCrash: " + position.getCaption());
        }
    };
}
