package com.example.raspberrypilockermobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    private Button unlock;
    private MjpegView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);
        view.setAdjustHeight(true);
        view.setAdjustWidth(true);
        view.setMode(MjpegView.MODE_FIT_WIDTH);
        view.setMsecWaitAfterReadImageError(10000);
        view.setUrl("http://192.168.1.110:8081");
        view.setRecycleBitmap(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        unlock = findViewById(R.id.unlock_button);
        unlock.setClickable(true);

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    URL url = new URL("http://192.168.1.110:9898/unlock");
                    URLConnection yc = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            yc.getInputStream()));

                    unlock.setClickable(false);
                    Thread.sleep(3000);
                    unlock.setClickable(true);

                    System.out.println(in.readLine());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        view.startStream();
        super.onResume();
    }

    @Override
    protected void onPause() {
        view.stopStream();
        super.onPause();
    }

    @Override
    protected void onStop() {
        view.stopStream();
        super.onStop();
    }
}
