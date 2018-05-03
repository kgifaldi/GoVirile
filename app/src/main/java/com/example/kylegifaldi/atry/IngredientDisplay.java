package com.example.kylegifaldi.atry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by kylegifaldi on 4/9/18.
 */

public class IngredientDisplay extends AppCompatActivity{

    private TextView ing_text;
    ProgressDialog progressDialog;
    AsyncTaskRunner runner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_display);

        LinearLayout ll;
        progressDialog = new ProgressDialog(IngredientDisplay.this);


        int i = 0;
        int cardHeight = 400;
        int tempId;

        ll = (LinearLayout) findViewById(R.id.ing_container);
        //String captured = "wheat\ngrain\nsalt\nsugar\nmalodextrin\nmsg\nfat\ntom sawyer\nnotre dame al mater\ntoo long\n too many cooks\none more text";



        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp_txt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 40, 0, 0); // TODO TOCHANGE


        CardView tmp = new CardView(this);
        tmp.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));

        tmp.setLayoutParams(lp);
        tmp.setClickable(true);
        //tmp.setMaxCardElevation(35);
        tmp.setCardElevation(15);
        tmp.setMinimumHeight(400);

        //tmp.setTransitionName(getResources().getString(R.string.transition_string));
        tmp.setTransitionName("Hello Kyle!");


        // add ripple effect to card!
        ColorStateList csl = ColorStateList.valueOf(getResources().getColor(R.color.colorSecondaryText));
        tempId = View.generateViewId();
        tmp.setId(tempId);
        RippleDrawable d = new RippleDrawable(csl, null, null);
        tmp.setForeground(d);


        // initialize TextView to place into Child Card
        TextView NameText = new TextView(this);
        NameText.setLayoutParams(lp);
        Intent myIntent = getIntent(); // gets the previously created intent
        String captured = myIntent.getStringExtra("capture_string"); // will return "FirstKeyValue"
        NameText.setText(captured);
        NameText.setTextSize(35);
        NameText.setPadding(350, 55, 0, 0); // TODO TOCHANGE
        NameText.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        int randomColor = MaterialColorPalette.getRandomColor("500");


        String letter = (NameText.getText().charAt(0)+"");
        //Intent temp_intent = getIntent();
        TextDrawable drbl = TextDrawable.builder().buildRound(letter, myIntent.getIntExtra("color", 0));
        ImageView ingImg = new ImageView(this);
        ingImg.setImageDrawable(drbl);
        int width = 350;
        int height = 350;
        ingImg.setPadding(50, 50, 50, 50);
        LinearLayout.LayoutParams prms = new LinearLayout.LayoutParams(width, height);
        ingImg.setLayoutParams(prms);

        tmp.addView(ingImg);
        tmp.addView(NameText);
        ll.addView(tmp);
        //enterReveal(ingImg);

        // Curl com.example.kylegifaldi.atry.Ingredient information and set TextView to result
        ing_text = findViewById(R.id.ing_text);
        System.out.println("WAT: ");
        System.out.println(captured);
        //final String temp = "http://www.cosmeticsinfo.org/ingredient/" + captured;
        captured = captured.replace(" ", "_");
        //final String temp = "https://en.wikipedia.org/w/index.php?action=raw&title=" + captured;
        final String temp = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + captured;
        String result = "Result: ";



        runner = new AsyncTaskRunner();
        //String sleepTime = time.getText().toString();
        runner.execute(temp);


        //new Thread(new Runnable() {
        //    public void run() {
        //        final String z = doInBackground(temp, ing_text);
        //    }
        //}).start();
        //ing_text.setText(result);






    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(IngredientDisplay.this,
                    "ProgressDialog",
                    "Wait...");
        }

        @Override
        protected void onPostExecute(String ans) {
            // execution of result of Long time consuming operation
            String result = ans;
            progressDialog.dismiss();

            if(result.toLowerCase().contains("extract"))
                result = result.toLowerCase().split(".*extract")[1];
            else
                result = "error: couldn't find additional data";

            ing_text.setText(result);


        }


        @Override
        protected String doInBackground(String... temp) {
            String result = "";
            try {
                URL url = new URL(temp[0]);
                System.out.println("YES: ");
                System.out.println(temp[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                StringBuilder strresult = new StringBuilder();
                try {
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    //InputStreamReader input = new InputStreamReader(conn.getInputStream());
                    InputStreamReader input = new InputStreamReader(url.openStream(), "UTF-8");
                    BufferedReader in = new BufferedReader(input);
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        strresult.append(inputLine);
                    in.close();

                } finally {
                    System.out.println("KYLE: ");
                    System.out.println(result);
                    result = strresult.toString();
                    conn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Search:");
            /*
            System.out.println(result);
            String parse1 = "";
            String parse2 = result;
            if(result.split("description").length > 1) {
                parse1 = result.split("description")[1];
                System.out.println("parse1: ");
                System.out.println(parse1);
                if(parse1.split("keywords").length > 1)
                    parse2 = parse1.split("keywords")[1];
                System.out.println("parse1:");
                System.out.println(parse1);
                System.out.println("parse2:");
                System.out.println(parse2);
            }
            else{
                parse1 = result + "0";
            }
*//*
        parse1 = parse1.split("/p>")[1].split("\"")[1];
        parse2 = parse2.split("<p")[1];
        if(parse2.contains("<a")){
            parse2 = parse2.replaceAll("<a.*a>", "");
        }
        *//*
            String fin = "Final: ";
            fin += parse1;
            fin += "\n";
            fin += parse2;
            if(fin.split("content=").length > 1)
                fin = fin.split("content=")[1];
            else
                fin += "1";
            if(fin.split("/>").length > 1)
                fin = fin.split("/>")[0];
            else
                fin += "2";
            System.out.println(fin);
            */
            //onPostExecute(fin);
            //onPostExecute(result);
            //ing_text.setText("help");
            return result;
        }









        //@Override
        //protected void onProgressUpdate(String text) {
        //    ing_text.setText(text);
       // }


    }

    @Override
    protected void onDestroy() {
        //super.onDestroy();
        progressDialog.dismiss();
        super.onDestroy();

    }
}
