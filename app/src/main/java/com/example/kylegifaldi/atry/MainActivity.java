package com.example.kylegifaldi.atry;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View tempId = findViewById(R.id.saved_button);

        setCardListener(tempId);


        View cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent cameraIntent = new Intent(MainActivity.this, CaptureDisplay.class);
                Intent cameraIntent = new Intent(MainActivity.this, CameraOpen.class);
                cameraIntent.putExtra("show_check", "1");
                //String captured = "Ingredients:water,glycerin,citric acid,beeswax,sorbitol";

                //cameraIntent.putExtra("captured", captured);
                startActivity(cameraIntent);
            }
        });

    }




    void setCardListener(View cardId){

        View card = (View) findViewById(cardId.getId());


        card.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SavedIngredients.class);
                startActivity(intent);
            }

        });

    }

}
