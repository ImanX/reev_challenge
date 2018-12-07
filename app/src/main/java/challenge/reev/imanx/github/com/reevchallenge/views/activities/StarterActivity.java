package challenge.reev.imanx.github.com.reevchallenge.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import challenge.reev.imanx.github.com.reevchallenge.R;
import challenge.reev.imanx.github.com.reevchallenge.databinding.ActivityStarterBinding;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public class StarterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStarterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_starter);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StarterActivity.this, MainActivity.class));
            }
        });
    }
}
