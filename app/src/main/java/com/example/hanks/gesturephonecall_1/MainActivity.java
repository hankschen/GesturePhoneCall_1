package com.example.hanks.gesturephonecall_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GestureOverlayView overlayView;
    Button btnAddGesture, btnListGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
    }

    void findviews(){
        overlayView = (GestureOverlayView)findViewById(R.id.gestureOverlayView);
        overlayView.addOnGesturePerformedListener(performedListener);
        btnAddGesture = (Button)findViewById(R.id.button);
        btnListGesture = (Button)findViewById(R.id.button2);
    }

    GestureOverlayView.OnGesturePerformedListener performedListener = new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(path, "gesture.txt");
            GestureLibrary library = GestureLibraries.fromFile(file);
            ArrayList<Prediction> predictions = library.recognize(gesture);

            SharedPreferences phonebook = getSharedPreferences("phonebook", MODE_PRIVATE);

            String phoneNum = phonebook.getString(predictions.get(0).name, "");
            Uri uri = Uri.parse("tel:"+phoneNum);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    };

    public void addGesture(View view){
        Intent intent = new Intent(MainActivity.this,AddGestureActivity.class);
        startActivity(intent);
    }

    public void listGesture(View view){
        Intent intent = new Intent(MainActivity.this,ListGestureActivity.class);
        startActivity(intent);
    }
}
