package com.medinpiranej.fakeerase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by medin on 1.9.2016.
 */
public class ShowJokeActivity extends Activity {

    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_joke);

        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Uri imageUri;
            String jokeText;
            try {
                imageUri = Uri.parse(bundle.getString("joke_image"));
                image.setImageBitmap(getBitmapFromUri(imageUri));
            }
            catch (Exception e){
                imageUri = null;
                e.printStackTrace();
            }

            if(imageUri == null){
                image.setImageResource(R.drawable.joke_image);
                image.setScaleType(ImageView.ScaleType.CENTER);
            }

            try {
                jokeText = bundle.getString("joke_text");
            }
            catch (Exception e){
                jokeText = null;
                e.printStackTrace();
            }

            if(jokeText != null){
                text.setText(jokeText);
            }
            else{
                text.setText("Keep Calm ! This is just a JOKE \uD83D\uDE1C !");
            }

        }
        else{
            image.setImageResource(R.drawable.joke_image);
            image.setScaleType(ImageView.ScaleType.CENTER);
            text.setText("Keep Calm ! This is just a JOKE \uD83D\uDE1C !");
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}