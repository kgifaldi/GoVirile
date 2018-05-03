package com.example.kylegifaldi.atry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kylegifaldi on 4/22/18.
 */

public class Pop extends Activity{

    EditText tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width), (int)(height));
        tv = (EditText) findViewById(R.id.title);

        Button but = (Button)findViewById(R.id.save);
        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Pop.this, SavedIngredients.class);
                Bundle bundle = new Bundle();
                Intent j = getIntent();
                String cap = j.getStringExtra("captured");
                bundle.putString("captured", cap);
                String title = tv.getText().toString();
                System.out.println("yayaya: ");
                System.out.println(title);
                bundle.putString("title", title);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });
    }

}
