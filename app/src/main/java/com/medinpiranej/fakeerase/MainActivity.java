package com.medinpiranej.fakeerase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    View decorView;
    ImageView animationContainer;
    ProgressBar progress;
    TextView text;

    int image1 = R.drawable.image1;
    int image2 = R.drawable.image2;

    Vibrator vibrator;

    int progressCount = 0;
    int textCount = 0;
    String finishText = "finishText";
    String texts[] = {
            "Erasing System Partition !",
            "Erasing app Settings !",
            "Erasing app Data !",
            "Erasing User Settings !",
            "Erasing User Data !",
            "Erasing User Directory !",
            "Wiping up System !",
            "Done wiping Device !",
            "Destroying android System !"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView  = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);
        animationContainer = (ImageView) findViewById(R.id.animationContainer);
        progress = (ProgressBar) findViewById(R.id.progress);
        text = (TextView) findViewById(R.id.text);
        changeImage(image2);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 800, 1000};
        vibrator.vibrate(pattern, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    };

    boolean finished = false;
    void changeImage(final int image){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!finished) {
                    if (image == image1) {
                        changeImage(image2);
                    } else {
                        changeImage(image1);
                    }

                    progressCount += 25;

                    progress.setProgress(progressCount);

                    if (progressCount >= 95) {
                        textCount++;
                        progressCount = 0;
                        if (textCount < texts.length) {
                            text.setText(texts[textCount].toUpperCase());
                        } else {
                            finished = true;
                        }
                    }
                    animationContainer.setImageResource(image);
                }
                else{
                    text.setText("Finished !");
                    vibrator.cancel();
                    Intent intent = new Intent(getApplicationContext(),ShowJokeActivity.class);
                    if(getIntent().getExtras() != null) {
                        intent.putExtras(getIntent().getExtras());
                    }
                    startActivity(intent);
                    finish();
                }
            }
        },200);
    }

    private void hideSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

}
