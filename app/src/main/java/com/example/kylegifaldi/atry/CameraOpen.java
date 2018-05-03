package com.example.kylegifaldi.atry;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class CameraOpen extends AppCompatActivity {
    private static final String TAG = "CAMERAOPEN";
    final int cameraPermissions = 1001;
    SurfaceView cameraView;
    TextView textView;
    CameraSource mCameraSource;
    Button capture_button;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case cameraPermissions: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        mCameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_open);

        Context context = getApplicationContext();
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        capture_button = (Button) findViewById(R.id.capture_button);
        final StringBuilder stringBuilder = new StringBuilder();


        capture_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //textView.setText(stringBuilder.toString());
                Intent captureIntent = new Intent(CameraOpen.this, CaptureDisplay.class);

                captureIntent.putExtra("captured", stringBuilder.toString());
                captureIntent.putExtra("show_check", "1");
                //String captured = "Ingredients:water,glycerin,citric acid,beeswax,sorbitol";

                //cameraIntent.putExtra("captured", captured);
                startActivity(captureIntent);
                finish();
                //Intent myIntent = getIntent(); // gets the previously created intent
                //String firstKeyName = myIntent.getStringExtra("firstKeyName"); // will return "FirstKeyValue"

                //Intent cameraIntent = new Intent(MainActivity.this, CameraOpen.class);
                //startActivity(cameraIntent);
            }
        });


        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, "Error, low storage", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Error, low storage");
            }
        }

        mCameraSource =
                new CameraSource.Builder(getApplicationContext(), textRecognizer)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setAutoFocusEnabled(true)
                        .setRequestedPreviewSize(768, 1024)
                        .setRequestedFps(1.0f)
                        .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CameraOpen.this,
                                new String[] {Manifest.permission.CAMERA},
                                cameraPermissions);
                        return;
                    }
                    mCameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mCameraSource.stop();

            }
        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

/*
            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if(items.size() != 0)
                {
                    Log.d("Size of items", "size of items: " + Float.toString(items.size()));
                    textView.post(new Runnable() {
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            boolean foundIngredients = false;
                            for(int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                Log.d("ITEM " + Float.toString(i), item.getValue());
                                for (Text line: item.getComponents()) {
                                    Log.d("ELEM", line.getValue() );
                                    if(foundIngredients == false) {
                                        if(line.getValue().toLowerCase().contains("ingredients")) {
                                            foundIngredients = true;
                                        }
                                    }
                                    if(foundIngredients == true) {
                                        for (Text elem : line.getComponents()) {
                                            if(!elem.getValue().toLowerCase().contains("ingredients")) {
                                                stringBuilder.append(elem.getValue() + " ");
                                            }
                                        }
                                    }
                                }
                            }
                            if(stringBuilder.toString() != "") {
                                textView.setText("Potential Ingredients: " + stringBuilder.toString());
                                Log.d("STRINGBUILT", stringBuilder.toString());
                            }
                        }
                    });
                }
            }
        });

    }
}
*/




            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if(items.size() != 0)
                {
                    textView.post(new Runnable() {
                        public void run() {
                            //StringBuilder stringBuilder = new StringBuilder();
                            Text ingredientsElem = null;
                            for(int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                for (Text line: item.getComponents()) {
                                    for (Text elem: line.getComponents()) {
                                        if(elem.getValue().toLowerCase().contains("ingredients")) {
                                            ingredientsElem = line;
                                            break;
                                        }
                                    }
                                    if(ingredientsElem != null) {
                                        stringBuilder.append(item.getValue());
                                        stringBuilder.append("\n");
                                    }
                                }
                            }
                            if(ingredientsElem != null) {
                                textView.setText(stringBuilder.toString());
                            }

                        }
                    });
                }
            }
        });

    }
}
