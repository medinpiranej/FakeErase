package com.medinpiranej.fakeerase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by medin on 1.9.2016.
 */
public class StartActivity extends Activity {

    Button selectImageBtn;
    Button launchBtn;

    EditText jokeText;
    EditText timeText;
    Spinner timeSpinner;

    ImageView image;

    Intent launchIntent;
    Uri imageUri;

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // Toast.makeText(getApplicationContext(),"Document loaded",Toast.LENGTH_SHORT).show();
            imageUri = data.getData();
            try {
                image.setImageBitmap(getBitmapFromUri(imageUri));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);

        setContentView(R.layout.activity_start);

        selectImageBtn = (Button) findViewById(R.id.selectImageBtn);
        launchBtn = (Button) findViewById(R.id.launchBtn);

        jokeText = (EditText) findViewById(R.id.jokeText);
        timeText = (EditText) findViewById(R.id.timeText);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        image = (ImageView) findViewById(R.id.image);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = 0;

                try{
                    time = Integer.parseInt(timeText.getText().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                time = time * 1000;
                for (int i = 0 ; i < timeSpinner.getSelectedItemPosition();i++){
                    time = time * 60;
                }

                launchIntent = new Intent(getApplicationContext(),MainActivity.class);
                if(jokeText.getText().length() > 0) {
                    launchIntent.putExtra("joke_text", jokeText.getText());
                }
                if(imageUri != null ) {
                    launchIntent.putExtra("joke_image",imageUri.toString());
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(launchIntent);
                    }
                },time);

                if(time > 0) {
                    Toast.makeText(getApplicationContext(), "All Ok ! press home button to hide app !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    };

}
