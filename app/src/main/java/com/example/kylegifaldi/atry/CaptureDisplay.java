package com.example.kylegifaldi.atry;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.graphics.drawable.RippleDrawable;
import com.amulyakhare.textdrawable.TextDrawable;


import org.w3c.dom.Text;

import java.util.ArrayList;



public class CaptureDisplay extends AppCompatActivity {



    public static String curr_name;
    public static int curr_color;
    String original_captured;
    CoordinatorLayout ll;
    String captured = "";

    public void animateIntent(View v, String ing, int color){

        Intent intent = new Intent(CaptureDisplay.this, IngredientDisplay.class);
        //startActivity(intent);
        String transitionName = getString(R.string.transition_string);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CaptureDisplay.this, v, transitionName);
        intent.putExtra("capture_string", ing);

        intent.putExtra("color", color);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }


    void setCardListener(final int cardId, final int color, final TextView name_view){

        View card = (View) findViewById(cardId);
        final String ingName = (String) name_view.getText();
        card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                CardView ll = (CardView) view;
                System.out.println("CHILDRENCOUNT");
                System.out.println(ll.getChildCount());
                final ImageView iv = (ImageView) ll.getChildAt(0);
                TextDrawable drbl = TextDrawable.builder().buildRound(ingName.substring(0, 1), color);
                iv.setImageDrawable(drbl);

                final TextView tv = (TextView) ll.getChildAt(1);
                final EditText et = (EditText) ll.getChildAt(2);
                Button but = (Button) ll.getChildAt(3);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv.setText(et.getText());
                        tv.setVisibility(View.VISIBLE);
                        //et.setBackgroundColor(getResources().getColor(R.color.clear));
                        //tv.setBackgroundColor(getResources().getColor(R.color.clear));
                        et.setVisibility(View.INVISIBLE);
                        view.setVisibility(View.INVISIBLE);

                    }
                });
                et.setVisibility(View.VISIBLE);
                tv.setVisibility(View.INVISIBLE);
                but.setVisibility(View.VISIBLE);

                //System.out.println(tv1.getText());

                //TextView tv2 = (TextView) ll.getChildAt(2);
                return false;
            }
        });

        //card.on

        card.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                curr_color = color;
                curr_name = ingName;
                //PublicData.child_obj = child_obj;
                //PublicData.selected_child = child_obj;
                animateIntent(v, (String) name_view.getText().toString(), color);
                //finish();
            }

        });

    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_display);
        //Intent myIntent = getIntent(); // gets the previously created intent
        //String captured = myIntent.getStringExtra("capture_string"); // will return "FirstKeyValue"
        //captured = "wheat\ngrain\nsalt\nsugar\nmalodextrin\nmsg\nfat\ntom sawyer\nnotre dame al mater\ntoo long\n too many cooks\none more text";
        captured = "Ingredients:water,glycerin,citric acid,beeswax,sorbitol";
        captured = getIntent().getStringExtra("captured");
        FloatingActionButton fab = findViewById(R.id.fab);
        int i = 0;
        int cardHeight = 400;
        int tempId;

        ll = (CoordinatorLayout) findViewById(R.id.ing_list);
        System.out.println("HH: ");
        System.out.println(captured);
        original_captured = captured;
        if(captured.toLowerCase().contains("ingredients:"))
            captured = captured.toLowerCase().split(".*ingredients:")[1];
        int marg = 0;
        ArrayList<Integer> card_ids = new ArrayList<>();
        String sc = getIntent().getStringExtra("show_check");
        System.out.println("jaja2:");
        System.out.println(sc + "");
        String zero = "0";
        if(zero.equals(sc)){
            fab.setVisibility(View.INVISIBLE);
            System.out.println("jajaj:");
            fab.clearAnimation();

        }
        else{
            fab.setVisibility(View.VISIBLE);
            fab.clearAnimation();
        }
        for (String t: captured.split(",")){

            String s = t.trim();

            CardView tmp = new CardView(this);

            //CardView tv = (TextView)findViewById(R.id.my_text_view);

            CardView.LayoutParams lp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, marg*420, 0, 0); // TODO TOCHANGE
            lp.height = 400;
            tmp.setLayoutParams(lp);


            tmp.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));

            tmp.setClickable(true);
            //tmp.setMaxCardElevation(35);
            tmp.setCardElevation(15);
            //tmp.setMinimumHeight(400);

            tmp.setTransitionName(getResources().getString(R.string.transition_string));


            // add ripple effect to card!
            ColorStateList csl = ColorStateList.valueOf(getResources().getColor(R.color.colorSecondaryText));
            tempId = View.generateViewId();
            tmp.setId(tempId);
            card_ids.add(tempId);
            RippleDrawable d = new RippleDrawable(csl, null, null);
            tmp.setForeground(d);




            // initialize TextView to place into Child Card
            TextView NameText = new TextView(this);
            EditText EditText = new EditText(this);
            Button but = new Button(this);
            but.setText("Done");
            //but.setLayoutParams (new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(800, 250,0,0);
            but.setLayoutParams(llp);

            EditText.setVisibility(View.INVISIBLE);
            //NameText.setLayoutParams(lp);
            Character o = s.charAt(0);
            String set_t = s.substring(1);
            NameText.setText(s);
            EditText.setText(s);
            NameText.setTextSize(35);
            EditText.setTextSize(35);
            NameText.setPadding(350, 55, 0, 0); // TODO TOCHANGE
            EditText.setPadding(350, 55, 0, 0); // TODO TOCHANGE

            NameText.setTextColor(getResources().getColor(R.color.colorSecondaryText));NameText.setPadding(350, 55, 0, 0); // TODO TOCHANGE
            EditText.setTextColor(getResources().getColor(R.color.colorSecondaryText));
            int randomColor = MaterialColorPalette.getRandomColor("500");


            String letter = (o+"");


            TextDrawable drbl = TextDrawable.builder().buildRound(letter, randomColor);
            ImageView ingImg = new ImageView(this);
            ingImg.setImageDrawable(drbl);

            int width = 350;
            int height = 350;
            int pad = 50;
            ingImg.setPadding(pad, pad, pad, pad);
            LinearLayout.LayoutParams prms = new LinearLayout.LayoutParams(width, height);
            ingImg.setLayoutParams(prms);

            tmp.addView(ingImg);
            tmp.addView(NameText);
            tmp.addView(EditText);
            tmp.addView(but);
            but.setVisibility(View.INVISIBLE);

            ll.addView(tmp);

            enterReveal(ingImg);

            //CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)tmp.getLayoutParams();
            //params.setMargins(0, 0, 0, marg*(-50)); //substitute parameters for left, top, right, bottom
            //tmp.setLayoutParams(params);


            setCardListener(tempId, randomColor, NameText);
            implementSwipeDismiss(tempId, card_ids, t);
            marg ++;

        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CaptureDisplay.this, Pop.class);
                i.putExtra("captured", original_captured);
                startActivity(i);
                finish();
            }
        });



    }



    private void implementSwipeDismiss(final int card_id, final ArrayList<Integer> card_ids, final String set_t) {

        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);//Swipe direction i.e any direction, here you can put any direction LEFT or RIGHT
        CardView cv = findViewById(card_id);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) cv.getLayoutParams();

        layoutParams.setBehavior(swipeDismissBehavior);//set swipe behaviour to Coordinator layout
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                boolean change = false;
                int remove = -1;
                for(int i = 0; i < card_ids.size(); i++){
                    if(change){
                        CardView tmp = (CardView)findViewById(card_ids.get(i));
                        //CardView tmp = (CardView) view;
                        CardView.MarginLayoutParams lp = (CardView.MarginLayoutParams) tmp.getLayoutParams();
                        int tm = lp.topMargin;
                        lp.setMargins(0, tm-420, 0, 0); // TODO TOCHANGE
                        tmp.setLayoutParams(lp);
                        tmp.requestLayout();
                        //ll.addView(tmp);


                        CardView tv = (CardView)findViewById(card_ids.get(i));
                        //CardView.LayoutParams params = (CardView.LayoutParams)tv.getLayoutParams();
                        //setMargs(tv, 0, -420, 0, 0); //substitute parameters for left, top, right, bottom
                        //tv.setLayoutParams(params);
                    }
                    if(card_ids.get(i) == card_id){
                        change = true;
                        remove = i;
                        CardView tmp = (CardView)findViewById(card_ids.get(i));
                        //CardView tmp = (CardView) view;
                        CardView.MarginLayoutParams lp = (CardView.MarginLayoutParams) tmp.getLayoutParams();
                        int tm = lp.topMargin;
                        lp.setMargins(0, -2999, 0, 0); // TODO TOCHANGE
                        tmp.setLayoutParams(lp);
                        tmp.requestLayout();


                    }

                }
                card_ids.remove(remove);
                System.out.println("rachel try to remove:");
                System.out.println(set_t);
                System.out.println("rachel before:");

                System.out.println(original_captured);
                original_captured = original_captured.toLowerCase();
                if(original_captured.contains(set_t.toLowerCase() + ",")) {
                    System.out.println("removing: ");
                    System.out.println(set_t + ",");
                    original_captured = original_captured.replace(set_t.toLowerCase() + ",", "");
                }
                else if(original_captured.contains(set_t.toLowerCase())) {
                    System.out.println("removing: ");
                    System.out.println(set_t);
                    original_captured = original_captured.replace(set_t.toLowerCase(), "");
                }
                else{
                    System.out.println("removing error: ");
                    System.out.println(set_t);
                    System.out.println("not found in ");
                    System.out.println(original_captured);

                }
                System.out.println("rachel after:");
                System.out.println(original_captured);

            }

            @Override
            public void onDragStateChanged(int state) { }
        });






    }


    void enterReveal(final View v){

        v.post(new Runnable() {
            @Override
            public void run() {

                int cx = v.getMeasuredWidth() / 2;
                int cy = v.getMeasuredHeight() / 2;

                int finRad = Math.max(v.getWidth(), v.getHeight()) / 2;

                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finRad);
                v.setVisibility(View.VISIBLE);
                anim.setDuration(500);

                anim.start();

            }
        });
    }

    public static void setMargs (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }




}