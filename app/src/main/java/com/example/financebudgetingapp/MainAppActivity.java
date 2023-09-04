package com.example.financebudgetingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class MainAppActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 1000; // 2 seconds
    private static final int PROGRESS_INTERVAL = 20; // Update progress every 50 milliseconds

    private ProgressBar progressBar;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize the progressBar by finding it from the layout XML
        progressBar = findViewById(R.id.loadingProgressBar);

        // Use a handler to delay the start of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateProgressBar();
                // Start the main activity
                //Intent intent = new Intent(MainAppActivity.this, MainActivity.class);
                //startActivity(intent);
                //finish(); // Close the splash activity to prevent going back to it with the back button
            }
        }, SPLASH_DURATION);
    }

    private void updateProgressBar() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    // Update the progress bar on the UI thread
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        // Sleep for a while to simulate loading
                        Thread.sleep(PROGRESS_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // When the loading is complete, start the main activity
                Intent intent = new Intent(MainAppActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity to prevent going back to it with the back button
            }
        }).start();
    }
}
