package com.speakexpense;

import java.sql.Date;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import com.google.android.gms.ads.InterstitialAd;
import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements TextToSpeech.OnInitListener {

    private static final int RESULT_SPEECH = 0;
    private static final int RESULT_SPEECH2 = 2;
    private static final int RESULT_SPEECH3 = 3;
    private static final int RESULT_SPEECH4 = 4;
    private static final int RESULT_SPEECH5 = 5;
    private static final int RESULT_SPEECH6 = 6;
    private static final int RESULT_SPEECH7 = 7;
    private static final int RESULT_SPEECH8 = 8;
    private static final int RESULT_SPEECH1 = 1;
    private static final int RESULT_SPEECH9 = 9;
    private static final int RESULT_SPEECH10 = 10;
    private static final int RESULT_SPEECH11 = 11;
    private static final int RESULT_SPEECH12 = 12;
    private static final int RESULT_SPEECH13 = 13;
    private static final int RESULT_SPEECH14 = 14;
    private static final int RESULT_SPEECH15 = 15;
    private static final int RESULT_SPEECH16 = 16;
    private static final int RESULT_SPEECH17 = 17;
    private static final int RESULT_SPEECH18 = 18;
    private static final int RESULT_SPEECH20 = 20;
    private static final int RESULT_SPEECH19 = 19;
    private static final int RESULT_SPEECH21 = 21;
    private static final int RESULT_SPEECH22 = 22;
    ImageButton ro, lo;
    public String txt, product_name, cost, txt11, txt12, txt13, txt16, txt19, oldproduct, place_name, date, day, items;
    public String newdate, query;
    public int daysminus;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13,b14;
    private TextView tv, tv1, tv2;
    private EditText et1, et2, et3;
    TextToSpeech tts;
    private ImageView iv, iv1, iv2;
    private int MY_DATA_CHECK_CODE = 0;
    private String mydate;
    private float exp = 0;

    SimpleSideDrawer slide_me;
    DatePickerDialog dpd;
    SimpleDateFormat sdf;
    View topLevelLayout;
    View mainlayout;
    String name, pitch, symbol, sprate;
    float pitch1;
    Cursor c;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    DBHelper dbh;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.sp2txt);
        AdView mAdView = (AdView) findViewById(com.speakexpense.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView mAdView1 = (AdView) findViewById(com.speakexpense.R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);
        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId("ca-app-pub-6154839855561418/4551064282");
        AdRequest adRequest3 = new AdRequest.Builder().build();

        interstitialAd.loadAd(adRequest3);
        getIntent();

        iv1 = (ImageView) findViewById(com.speakexpense.R.id.imagemain);
        iv2 = (ImageView) findViewById(com.speakexpense.R.id.imageset);
        b13 = (Button) findViewById(com.speakexpense.R.id.close);
        topLevelLayout = findViewById(com.speakexpense.R.id.top_layout);
        mainlayout = findViewById(com.speakexpense.R.id.mainlayout);
        if (isFirstTime()) {
            topLevelLayout.setVisibility(View.INVISIBLE);
            mainlayout.setVisibility(View.VISIBLE);

        }
        pref = getSharedPreferences("mypref", MODE_PRIVATE);
        name = pref.getString("name", "Guest");
        pitch = pref.getString("pitch", "1.0f");
        symbol=pref.getString("symbol", "$");
        sprate = pref.getString("sprate", "0.8f");
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.right_menu);
        b1 = (Button) findViewById(com.speakexpense.R.id.viewdb);
        b2 = (Button) findViewById(com.speakexpense.R.id.deletedata);
        b3 = (Button) findViewById(com.speakexpense.R.id.updatedata);
        b4 = (Button) findViewById(com.speakexpense.R.id.insertdata);
        b5 = (Button) findViewById(com.speakexpense.R.id.searchdb);
        b6 = (Button) findViewById(com.speakexpense.R.id.csvfiles);
        b7 = (Button) findViewById(com.speakexpense.R.id.home);
        b8 = (Button) findViewById(com.speakexpense.R.id.Settings);
        b9 = (Button) findViewById(com.speakexpense.R.id.help);
        b10 = (Button) findViewById(com.speakexpense.R.id.graph);
        b11 = (Button) findViewById(com.speakexpense.R.id.graph2);
        b12 = (Button) findViewById(com.speakexpense.R.id.help);
        b14= (Button) findViewById(com.speakexpense.R.id.graph3);
        et1 = (EditText) findViewById(com.speakexpense.R.id.editText1);
        et2 = (EditText) findViewById(com.speakexpense.R.id.editText2);
        et3 = (EditText) findViewById(com.speakexpense.R.id.editText3);
        tv1 = (TextView) findViewById(com.speakexpense.R.id.textView6);
        tv = (TextView) findViewById(com.speakexpense.R.id.textView2);
        iv = (ImageView) findViewById(com.speakexpense.R.id.imageView1);
        et1.setKeyListener(null);
        Calendar cl = Calendar.getInstance();
        int curyear = cl.get(Calendar.YEAR);
        int curmonth = cl.get(Calendar.MONTH) + 1;
        android.text.format.DateFormat df1 = new android.text.format.DateFormat();
        mydate = (String) df1.format("yyyy-MM-dd", new java.util.Date());
        DecimalFormat df = new DecimalFormat("00");
        dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        c = db.rawQuery(
                "SELECT SUM(COST) FROM Mydata WHERE strftime('%Y-%m', `DATE`) ='" + curyear + "-" + df.format(curmonth) + "'", null);
        c.moveToFirst();
        int sum = c.getInt(0);
        et2.setText(String.valueOf(sum) + symbol);
        c = db.rawQuery(
                "SELECT SUM(COST) FROM Mydata WHERE strftime('%Y', `DATE`) ='" + curyear + "'", null);
        c.moveToFirst();
        int sum1 = c.getInt(0);
        et3.setText(String.valueOf(sum1) + symbol);
        c = db.rawQuery(
                "SELECT SUM(COST) FROM Mydata WHERE DATE='" + mydate + "'", null);
        c.moveToFirst();
        int sum2 = c.getInt(0);
        et1.setText(String.valueOf(sum2) + symbol);
        tv.setText(name);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, Displaydatabase.class);
                Main.this.startActivity(intent);

            }
        });
        b2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, DeleteExpenses.class);
                Main.this.startActivity(intent);

            }
        });
        b3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, UpdateExpenses.class);
                Main.this.startActivity(intent);

            }
        });
        b4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, AddExpenses.class);
                Main.this.startActivity(intent);

            }
        });
        b5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Searchform.class);
                Main.this.startActivity(i);
            }
        });
        b6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Csvfiles.class);
                Main.this.startActivity(i);
            }
        });
        b7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Main.class);
                Main.this.startActivity(i);
            }
        });

        b8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Set.class);
                Main.this.startActivity(i);
            }
        });
        b10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(Main.this, Graph.class);
                Main.this.startActivity(i);
            }
        });
        b11.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(Main.this, Linechart.class);
                Main.this.startActivity(i);
            }
        });
        b12.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(Main.this, Help.class);
                Main.this.startActivity(i);
            }
        });
        b14.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(Main.this, Piechart.class);
                Main.this.startActivity(i);
            }
        });
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("Speak Expenses");
        ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
        ro.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleRightDrawer();
            }
        });
        lo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        android.text.format.DateFormat df2 = new android.text.format.DateFormat();
        mydate = (String) df2.format("yyyy-MM-dd", new java.util.Date());
        tts=new TextToSpeech(this,this);
        tts.setPitch(Float.parseFloat(pitch));
        tts.setSpeechRate(Float.parseFloat(sprate));


        iv.setBackgroundResource(com.speakexpense.R.drawable.anim);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent checkIntent = new Intent();
                checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");

                final AnimationDrawable animate =
                        (AnimationDrawable) iv.getBackground();
                animate.start();
                tts.speak("Welcome", TextToSpeech.QUEUE_FLUSH, null);
                tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                tts.speak(name, TextToSpeech.QUEUE_ADD, null);
                tts.playSilence(650, TextToSpeech.QUEUE_ADD, null);
                tts.speak("please Respond the place name", TextToSpeech.QUEUE_ADD, map);

                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onError(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {
                        animate.stop();
                        Intent intent = new Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Place Name?");
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                        try {

                            startActivityForResult(intent, RESULT_SPEECH1);


                        } catch (ActivityNotFoundException a) {
                            Toast t = Toast.makeText(getApplicationContext(),
                                    "Ops! Your device doesn't support Speech to Text",
                                    Toast.LENGTH_SHORT);
                            t.show();
                        }

                    }
                });

            }
        });

    }

    public void close() {

        final AnimationDrawable animate =
                (AnimationDrawable) iv.getBackground();
        animate.start();
        tts.speak("Thank You", TextToSpeech.QUEUE_FLUSH, null);
        animate.stop();

    }
    @Override
    public void onDestroy() {

        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();
        finish();
    }

    @Override
    public void onStop() {
        if (tts != null) {
            tts.stop();
        }
        super.onStop();
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.UK);
        }

    }


    private boolean isFirstTime() {

        pref = getSharedPreferences("mypref", MODE_PRIVATE);
        boolean ranBefore = pref.getBoolean("RanBefore", false);
        if (!ranBefore) {
            ed = pref.edit();
            ed.putBoolean("RanBefore", true);
            ed.commit();
            topLevelLayout.setVisibility(View.VISIBLE);
            mainlayout.setVisibility(View.INVISIBLE);
            iv1.setBackgroundResource(com.speakexpense.R.drawable.mainimage);
            iv2.setBackgroundResource(com.speakexpense.R.drawable.settings);
            b13.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    topLevelLayout.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Main.this, Set.class);
                    Main.this.startActivity(intent);
                    finish();
                    return false;
                }
            });
        }
        return ranBefore;


    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case RESULT_SPEECH: {

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt = text.get(0).toString();
                        String txt9 = txt.toLowerCase();
                        String[] str1 = {"add product", "update product", "delete product", "close", "generate report"};
                        if (Arrays.asList(str1).contains(txt9)) {
                            if (txt9.equals("generate report")) {
                                Intent intent = new Intent(Main.this, Graph.class);
                                Main.this.startActivity(intent);
                                Intent intent2 = new Intent(Main.this, Linechart.class);
                                Main.this.startActivity(intent2);
                            }
                            if (txt9.equals("delete product")) {
                                HashMap<String, String> map25 = new HashMap<String, String>();
                                map25.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("respond the product name you want to delete", TextToSpeech.QUEUE_FLUSH, map25);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId4) {

                                    }

                                    @Override
                                    public void onError(String utteranceId4) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId4) {
                                        animate.stop();

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH6);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                            }
                            if (txt9.equals("update product")) {
                                HashMap<String, String> map21 = new HashMap<String, String>();
                                map21.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("respond the product name you want to update", TextToSpeech.QUEUE_FLUSH, map21);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId4) {

                                    }

                                    @Override
                                    public void onError(String utteranceId4) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId4) {
                                        animate.stop();

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH3);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                            }

                            if (txt9.equals("add product")) {
                                HashMap<String, String> map41 = new HashMap<String, String>();
                                map41.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("to add new product dicate product first and cost next", TextToSpeech.QUEUE_FLUSH, map41);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId4) {

                                    }

                                    @Override
                                    public void onError(String utteranceId4) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId4) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name followed by Cost");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH8);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                            }
                            if (txt9.equals("close")) {
                                close();
                            }
                            if (txt9.equals("cancel")) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                                tts.speak("please Respond the place name", TextToSpeech.QUEUE_ADD, map);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId) {

                                    }

                                    @Override
                                    public void onError(String utteranceId) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId) {
                                        Intent intent = new Intent(
                                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Place Name?");
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        try {

                                            startActivityForResult(intent, RESULT_SPEECH1);


                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(),
                                                    "Ops! Your device doesn't support Speech to Text",
                                                    Toast.LENGTH_SHORT);
                                            t.show();
                                        }

                                    }
                                });
                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have the below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Add Product,Delete product,Update Product,Generate report");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            case RESULT_SPEECH1:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String placename1 = text.get(0).toString().toLowerCase();
                    String placename2=placename1.replaceAll("[^A-Za-z]", "");
                    place_name=placename2.replaceAll("\\s+", "");
                    if (place_name.equals("close")) {
                        close();
                    }
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tv1.setText(place_name);
                    tts.speak("the place name is " + place_name, TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to confirm", TextToSpeech.QUEUE_ADD, map);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {

                        }

                        @Override
                        public void onError(String utteranceId) {

                        }

                        @Override
                        public void onDone(String utteranceId) {
                            animate.stop();
                            Intent intent = new Intent(
                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {

                                startActivityForResult(intent, RESULT_SPEECH9);


                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(),
                                        "Ops! Your device doesn't support Speech to Text",
                                        Toast.LENGTH_SHORT);
                                t.show();
                            }

                        }
                    });

                    break;
                }
            case RESULT_SPEECH2: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    try {
                        String txt2 = text.get(0).toString();
                        String txt6 = txt2.toLowerCase();
                        String[] str = {"yes", "no", "finish", "close"};
                        if (Arrays.asList(str).contains(txt6)) {
                            if (txt6.equals("close")) {
                                close();
                            }
                            if (txt6.equals("finish")) {

                                HashMap<String, String> map3 = new HashMap<String, String>();
                                map3.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID3");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("data is succefully inserted", TextToSpeech.QUEUE_FLUSH, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("current expenses is" + exp + "dollars", TextToSpeech.QUEUE_ADD, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak(" respond close", TextToSpeech.QUEUE_ADD, map3);
                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId3) {

                                    }

                                    @Override
                                    public void onError(String utteranceId3) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId3) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "ADD PRODUCT,DELETE PRODUCT,UPDATE PRODUCT,CLOSE");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }


                            if (txt6.equals("no")) {
                                HashMap<String, String> map8 = new HashMap<String, String>();
                                map8.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map8);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH8);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }

                            if (txt6.equals("yes")) {
                                DBHelper dbh = new DBHelper(this);
                                dbh.getWritableDatabase();
                                dbh.insertdata(mydate, product_name, cost, place_name, txt);
                                Float ex = Float.parseFloat(cost);
                                exp = exp + ex;
                                HashMap<String, String> map6 = new HashMap<String, String>();
                                map6.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("dicatate next product or respond finish", TextToSpeech.QUEUE_FLUSH, map6);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name followed by Cost,FINISH");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH8);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                                break;

                            }


                        } else {
                            HashMap<String, String> map7 = new HashMap<String, String>();
                            map7.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you You have below options to choose", TextToSpeech.QUEUE_FLUSH, map7);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH2);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }
            case RESULT_SPEECH3: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt11 = text.get(0).toString();
                    if (txt11.equals("cancel")) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "what would you like to do?");
                        try {
                            startActivityForResult(intent, RESULT_SPEECH1);
                        } catch (ActivityNotFoundException a) {
                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                    HashMap<String, String> map22 = new HashMap<String, String>();
                    map22.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tv1.setText(txt11);
                    tts.speak("product name is" + txt11, TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to confirm", TextToSpeech.QUEUE_ADD, map22);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH10);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });

                }
                break;
            }
            case RESULT_SPEECH4: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        txt12 = text.get(0).toString();
                        if (txt12.equals("close")) {
                            close();
                        }
                        if (txt12.equals("cancel")) {
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "respond the product name?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH3);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                        txt13 = txt12.replaceAll("[^A-Za-z]+", "");
                        String txt14 = txt12.replaceAll("\\s+", "");
                        if (txt14.matches("[A-Za-z]+") == false) {
                            txt16 = txt12.replaceAll("[^\\d.]", "");
                            HashMap<String, String> map23 = new HashMap<String, String>();
                            map23.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID1");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            txt13.toLowerCase();
                            tv1.setText(txt13 + " " + txt16);
                            tts.speak("product is " + txt13, TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(650, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("and cost is" + txt16, TextToSpeech.QUEUE_ADD, null);
                            tts.playSilence(1000, TextToSpeech.QUEUE_ADD, null);

                            tts.speak("respond YES to Confirm ", TextToSpeech.QUEUE_ADD, map23);
                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId1) {

                                }

                                @Override
                                public void onError(String utteranceId1) {

                                }

                                @Override
                                public void onDone(String utteranceId1) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH5);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you please try again", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name followed by Cost");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH4);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }


            }
            case RESULT_SPEECH5: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt17 = text.get(0).toString();
                        String txt6 = txt17.toLowerCase();
                        String[] str = {"yes", "no", "close"};
                        if (Arrays.asList(str).contains(txt17)) {

                            if (txt6.equals("yes")) {
                                DBHelper dbh = new DBHelper(this);
                                SQLiteDatabase db = dbh.getReadableDatabase();
                                String query = "SELECT * FROM Mydata WHERE PRODUCT ='" + txt11.toLowerCase() + "' AND PLACE='" + place_name + "'";
                                Cursor c = db.rawQuery(query, null);
                                c.moveToFirst();
                                int rowcount = 1;
                                int rows = c.getCount();
                                if (rows == 0) {
                                    tts.speak("no items found", TextToSpeech.QUEUE_FLUSH, null);
                                } else if (rows > rowcount) {
                                    HashMap<String, String> map63 = new HashMap<String, String>();
                                    map63.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID1");
                                    tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                                    tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                    tts.speak("do you want to narrow the list", TextToSpeech.QUEUE_ADD, map63);
                                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                        @Override
                                        public void onStart(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onError(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onDone(String utteranceId) {
                                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                            try {
                                                startActivityForResult(intent, RESULT_SPEECH15);
                                            } catch (ActivityNotFoundException a) {
                                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                                t.show();
                                            }

                                        }
                                    });

                                } else {
                                    dbh.updatedata(txt11, mydate, txt13.toLowerCase(), txt16, place_name, txt12);
                                    final AnimationDrawable animate =
                                            (AnimationDrawable) iv.getBackground();
                                    animate.start();
                                    tts.speak("data is updated successfully", TextToSpeech.QUEUE_ADD, null);
                                    animate.stop();
                                    break;
                                }
                            }

                            if (txt6.equals("no")) {
                                HashMap<String, String> map8 = new HashMap<String, String>();
                                map8.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map8);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH4);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                            if (txt6.equals("close")) {
                                close();
                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }


                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH5);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            case RESULT_SPEECH6: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String txt45 = text.get(0).toString();
                    product_name = txt45.toLowerCase();
                    if (txt45.equals("close")) {
                        close();
                    }
                    if (txt45.equals("cancel")) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "what would you like to do?");
                        try {
                            startActivityForResult(intent, RESULT_SPEECH1);
                        } catch (ActivityNotFoundException a) {
                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    } else {
                        HashMap<String, String> map30 = new HashMap<String, String>();
                        map30.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        tv1.setText(product_name);
                        tts.speak("product name is" + product_name, TextToSpeech.QUEUE_FLUSH, null);
                        tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                        tts.speak("respond yes to confirm", TextToSpeech.QUEUE_ADD, map30);

                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId4) {

                            }

                            @Override
                            public void onError(String utteranceId4) {

                            }

                            @Override
                            public void onDone(String utteranceId4) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH7);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }

                            }
                        });
                    }
                }
                break;
            }

            case RESULT_SPEECH7: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt18 = text.get(0).toString();
                        String txt6 = txt18.toLowerCase();
                        String[] str = {"yes", "no", "close"};
                        if (Arrays.asList(str).contains(txt6)) {

                            if (txt6.equals("yes")) {

                                DBHelper dbh = new DBHelper(this);
                                SQLiteDatabase db = dbh.getReadableDatabase();
                                String query = "SELECT * FROM Mydata WHERE PRODUCT ='" + product_name.toLowerCase() + "' AND PLACE='" + place_name + "'";
                                Cursor c = db.rawQuery(query, null);
                                c.moveToFirst();
                                int rowcount = 1;
                                int rows = c.getCount();
                                if (rows == 0) {
                                    HashMap<String, String> map65 = new HashMap<String, String>();
                                    map65.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                                    tts.speak("cannot delete product does not exist", TextToSpeech.QUEUE_FLUSH, null);
                                    tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                    tts.speak("respond the product name again", TextToSpeech.QUEUE_ADD, map65);
                                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                        @Override
                                        public void onStart(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onError(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onDone(String utteranceId) {
                                            Intent intent = new Intent(
                                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                            try {

                                                startActivityForResult(intent, RESULT_SPEECH6);


                                            } catch (ActivityNotFoundException a) {
                                                Toast t = Toast.makeText(getApplicationContext(),
                                                        "Ops! Your device doesn't support Speech to Text",
                                                        Toast.LENGTH_SHORT);
                                                t.show();
                                            }
                                        }
                                    });
                                } else if (rows > rowcount) {
                                    HashMap<String, String> map31 = new HashMap<String, String>();
                                    map31.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                                    tts.speak("total" + rows + "items found for the product name" + product_name, TextToSpeech.QUEUE_FLUSH, null);
                                    tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                    tts.speak("do you want to narrow the list", TextToSpeech.QUEUE_ADD, map31);
                                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                        @Override
                                        public void onStart(String utteranceId) {

                                        }

                                        @Override
                                        public void onError(String utteranceId) {

                                        }

                                        @Override
                                        public void onDone(String utteranceId) {
                                            Intent intent = new Intent(
                                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                            try {

                                                startActivityForResult(intent, RESULT_SPEECH11);


                                            } catch (ActivityNotFoundException a) {
                                                Toast t = Toast.makeText(getApplicationContext(),
                                                        "Ops! Your device doesn't support Speech to Text",
                                                        Toast.LENGTH_SHORT);
                                                t.show();
                                            }

                                        }
                                    });

                                } else {
                                    db = dbh.getWritableDatabase();
                                    dbh.deletedata(product_name, cost, place_name, null);
                                    final AnimationDrawable animate =
                                            (AnimationDrawable) iv.getBackground();
                                    animate.start();
                                    tts.speak("data is deleted successfully", TextToSpeech.QUEUE_FLUSH, null);
                                    animate.stop();
                                }
                            }
                            if (txt6.equals("no")) {
                                HashMap<String, String> map26 = new HashMap<String, String>();
                                map26.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map26);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH6);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                            if (txt6.equals("close")) {
                                close();
                            }
                        } else {
                            HashMap<String, String> map31 = new HashMap<String, String>();
                            map31.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map31);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH7);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case RESULT_SPEECH8: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt = text.get(0).toString().toLowerCase();
                    if (txt.equals("close")) {
                        close();
                    } else if (txt.equals("cancel")) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "what would you like to do?");
                        try {
                            startActivityForResult(intent, RESULT_SPEECH1);
                        } catch (ActivityNotFoundException a) {
                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    } else if (txt.equals("finish")) {
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        HashMap<String, String> map3 = new HashMap<String, String>();
                        map3.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID3");
                        tts.speak("product is succefully inserted", TextToSpeech.QUEUE_FLUSH, null);
                        tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                        tts.speak("current expenses is" + exp + "dollars", TextToSpeech.QUEUE_ADD, null);
                        tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                        tts.speak(" respond close", TextToSpeech.QUEUE_ADD, map3);
                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId3) {

                            }

                            @Override
                            public void onError(String utteranceId3) {

                            }

                            @Override
                            public void onDone(String utteranceId3) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });

                    } else {

                        String pn1 = txt.replaceAll("[\\d.$]+", "");
                        String pn2 = pn1.replace("dollars", "");
                        product_name = pn2.replaceAll("\\s+", "");
                        String txt5 = txt.replaceAll("\\s+", "");
                        if (txt5.matches("[A-Za-z]+") == false) {
                            cost = txt.replaceAll("[^\\d.]", "");
                            HashMap<String, String> map1 = new HashMap<String, String>();
                            map1.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID1");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tv1.setText(txt);
                            tts.speak("product is " + product_name, TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(650, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("and cost is" + cost + "dollars", TextToSpeech.QUEUE_ADD, null);
                            tts.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("respond YES to Confirm ", TextToSpeech.QUEUE_ADD, map1);
                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId1) {

                                }

                                @Override
                                public void onError(String utteranceId1) {

                                }

                                @Override
                                public void onDone(String utteranceId1) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH2);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                        } else {
                            HashMap<String, String> map42 = new HashMap<String, String>();
                            map42.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you please try again", TextToSpeech.QUEUE_FLUSH, map42);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name followed by Cost,FINISH");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH8);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                        }

                    }

                }
                break;
            }
            case RESULT_SPEECH9: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt17 = text.get(0).toString();
                        String txt6 = txt17.toLowerCase();
                        String[] str = {"yes", "no", "close"};
                        if (Arrays.asList(str).contains(txt6)) {
                            if (txt6.equals("close")) {
                                close();
                            }
                            if (txt6.equals("yes")) {
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                HashMap<String, String> map30 = new HashMap<String, String>();
                                map30.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("what would you like to do", TextToSpeech.QUEUE_ADD, map30);
                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "ADD PRODUCT,DELETE PRODUCT,UPDATE PRODUCT,GENERATE REPORT");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                            if (txt6.equals("no")) {
                                HashMap<String, String> map8 = new HashMap<String, String>();
                                map8.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map8);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Place Name?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH1);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH9);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            case RESULT_SPEECH10: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt17 = text.get(0).toString();
                        String txt6 = txt17.toLowerCase();
                        String[] str = {"yes", "no", "close"};
                        if (Arrays.asList(str).contains(txt6)) {
                            if (txt6.equals("close")) {
                                close();
                            }
                            if (txt6.equals("yes")) {
                                DBHelper dbh = new DBHelper(this);
                                SQLiteDatabase db = dbh.getReadableDatabase();
                                String query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + txt11.toLowerCase() + "'";
                                Cursor c = db.rawQuery(query, null);
                                c.moveToFirst();
                                int rowcount = 1;
                                int rows = c.getCount();
                                if (rows == 0) {
                                    HashMap<String, String> map66 = new HashMap<String, String>();
                                    map66.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                    tts.speak("Product does not exist", TextToSpeech.QUEUE_FLUSH, null);
                                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                                    tts.speak("Respond the product name again", TextToSpeech.QUEUE_ADD, map66);
                                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                        @Override
                                        public void onStart(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onError(String utteranceId) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onDone(String utteranceId) {
                                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                            try {
                                                startActivityForResult(intent, RESULT_SPEECH3);
                                            } catch (ActivityNotFoundException a) {
                                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                                t.show();
                                            }
                                        }
                                    });
                                } else {
                                    final AnimationDrawable animate =
                                            (AnimationDrawable) iv.getBackground();
                                    animate.start();
                                    HashMap<String, String> map29 = new HashMap<String, String>();
                                    map29.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                    tts.speak("dictate new data", TextToSpeech.QUEUE_ADD, map29);
                                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                        @Override
                                        public void onStart(String utteranceId6) {

                                        }

                                        @Override
                                        public void onError(String utteranceId6) {

                                        }

                                        @Override
                                        public void onDone(String utteranceId6) {
                                            animate.stop();
                                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name followed by Cost");
                                            try {
                                                startActivityForResult(intent, RESULT_SPEECH4);
                                            } catch (ActivityNotFoundException a) {
                                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                                t.show();
                                            }
                                        }
                                    });

                                }
                            }
                            if (txt6.equals("no")) {
                                HashMap<String, String> map54 = new HashMap<String, String>();
                                map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();

                                tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH3);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH10);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            }
            case RESULT_SPEECH11: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt17 = text.get(0).toString();
                        String txt6 = txt17.toLowerCase();
                        String[] str = {"yes", "no", "cost", "days", "close"};
                        if (Arrays.asList(str).contains(txt6)) {
                            if (txt6.equals("yes")) {

                                HashMap<String, String> map32 = new HashMap<String, String>();
                                map32.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("do you want to narrow list by cost or days", TextToSpeech.QUEUE_ADD, map32);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "COST,DAYS");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH11);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });


                            }
                            if (txt6.equals("no")) {
                                HashMap<String, String> map54 = new HashMap<String, String>();
                                map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("choose from the list to delete item", TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    query = "SELECT * FROM Mydata WHERE PRODUCT = '" + product_name + "' and PLACE='" + place_name + "'";
                                    Intent i = new Intent(Main.this, Deletedata.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("query", query);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            if (txt6.equals("cost")) {
                                HashMap<String, String> map44 = new HashMap<String, String>();
                                map44.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("please respond the cost", TextToSpeech.QUEUE_FLUSH, map44);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId4) {

                                    }

                                    @Override
                                    public void onError(String utteranceId4) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId4) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "COST?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH12);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                            if (txt6.equals("days")) {
                                HashMap<String, String> map33 = new HashMap<String, String>();
                                map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH19);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                            }

                            if (txt6.equals("close")) {
                                close();
                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,COST,DAYS");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH11);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            case RESULT_SPEECH12: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt = text.get(0).toString().toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt)) {
                        if (txt.equals("close")) {
                            close();
                        }
                        if (txt.equals("yes")) {
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            String query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + product_name + "' AND COST ='" + cost + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("no products found with the cost" + cost, TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    query = "SELECT * FROM Mydata WHERE PRODUCT = '" + product_name + "' and PLACE='" + place_name + "'";
                                    Intent i = new Intent(Main.this, Deletedata.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("query", query);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            HashMap<String, String> map33 = new HashMap<String, String>();
                            map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("do you want to narrow list by days", TextToSpeech.QUEUE_ADD, map33);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH13);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                            break;

                        }
                        if (txt.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH12);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                            break;
                        }
                    }
                    cost = txt.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    map1.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID1");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tv1.setText(cost);
                    tts.speak("cost is" + cost, TextToSpeech.QUEUE_ADD, null);
                    tts.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond YES to Confirm ", TextToSpeech.QUEUE_ADD, map1);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId1) {

                        }

                        @Override
                        public void onError(String utteranceId1) {

                        }

                        @Override
                        public void onDone(String utteranceId1) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH12);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map42 = new HashMap<String, String>();
                    map42.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map42);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,COST?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH12);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });

                }

                break;
            }
            case RESULT_SPEECH13: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {

                        if (txt6.equals("yes")) {

                            HashMap<String, String> map33 = new HashMap<String, String>();
                            map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH13);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("choose from the list to delete an item ", TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Intent i = new Intent(Main.this, Deletedatabycost.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("place_name", place_name);
                                i.putExtra("cost", cost);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        if (txt6.equals("close")) {
                            close();
                        }
                    }
                    day = txt17.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map46 = new HashMap<String, String>();
                    map46.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    tv1.setText(day);
                    tts.speak("narrowing list by searching in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to continue", TextToSpeech.QUEUE_ADD, map46);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onDone(String utteranceId) {

                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH14);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,Number of Days?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH13);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                }


                break;
            }
            case RESULT_SPEECH14: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {

                        if (txt6.equals("yes")) {

                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            daysminus = Integer.parseInt(day);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                            cal.add(Calendar.DAY_OF_YEAR, -daysminus);
                            newdate = s.format(new Date(cal.getTimeInMillis()));
                            query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + product_name + "' AND COST ='" + cost + "' AND DATE BETWEEN '" + newdate + "' AND '" + mydate + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("choose from the list to delete an item ", TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    Intent i = new Intent(Main.this, Deletedatabycost.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("cost", cost);

                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            HashMap<String, String> map61 = new HashMap<String, String>();
                            map61.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("choose item you want to delete from the list", TextToSpeech.QUEUE_ADD, null);
                            try {
                                query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + product_name + "' AND COST ='" + cost + "' AND DATE BETWEEN '" + newdate + "' AND '" + mydate + "'";
                                Intent i = new Intent(Main.this, Deletedatabycostdate.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("place_name", place_name);
                                i.putExtra("cost", cost);
                                i.putExtra("newdate", newdate);
                                i.putExtra("mydate", mydate);
                                i.putExtra("query", query);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();

                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH13);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                        if (txt6.equals("close")) {
                            close();
                        }
                    } else {
                        HashMap<String, String> map4 = new HashMap<String, String>();
                        map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId4) {

                            }

                            @Override
                            public void onError(String utteranceId4) {

                            }

                            @Override
                            public void onDone(String utteranceId4) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH14);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });

                    }


                }
                break;
            }
            case RESULT_SPEECH15: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    try {
                        String txt17 = text.get(0).toString();
                        String txt6 = txt17.toLowerCase();
                        String[] str = {"yes", "no", "cost", "days", "close"};
                        if (Arrays.asList(str).contains(txt6)) {
                            if (txt6.equals("close")) {
                                close();
                            }
                            if (txt6.equals("yes")) {

                                HashMap<String, String> map32 = new HashMap<String, String>();
                                map32.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("do you want to narrow list by cost or days ", TextToSpeech.QUEUE_ADD, map32);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "COST,DAYS");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH15);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });


                            }
                            if (txt6.equals("no")) {
                                HashMap<String, String> map54 = new HashMap<String, String>();
                                map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("choose from the list to update an item", TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    Intent i = new Intent(Main.this, Updatedata.class);
                                    query = "SELECT * FROM Mydata WHERE PRODUCT = '" + txt11.toLowerCase() + "' and PLACE='" + place_name + "'";
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("cost", cost);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("txt11", txt11);
                                    i.putExtra("mydate", mydate);
                                    i.putExtra("txt12", txt12);
                                    i.putExtra("query", query);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                            if (txt6.equals("cost")) {
                                HashMap<String, String> map44 = new HashMap<String, String>();
                                map44.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                                final AnimationDrawable animate =
                                        (AnimationDrawable) iv.getBackground();
                                animate.start();
                                tts.speak("please respond the cost", TextToSpeech.QUEUE_FLUSH, map44);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId4) {

                                    }

                                    @Override
                                    public void onError(String utteranceId4) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId4) {
                                        animate.stop();
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "COST");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH16);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });

                            }
                            if (txt6.equals("days")) {
                                HashMap<String, String> map33 = new HashMap<String, String>();
                                map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH21);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                            }
                        } else {
                            HashMap<String, String> map4 = new HashMap<String, String>();
                            map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId4) {

                                }

                                @Override
                                public void onError(String utteranceId4) {

                                }

                                @Override
                                public void onDone(String utteranceId4) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,COST,DAYS");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH15);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            case RESULT_SPEECH16: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt = text.get(0).toString().toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt)) {
                        if (txt.equals("close")) {
                            close();
                        }
                        if (txt.equals("yes")) {
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            String query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + txt11 + "' AND COST ='" + cost + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("no products found with the cost" + cost, TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    Intent i = new Intent(Main.this, Updatedata.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("cost", cost);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("oldproduct", txt11);
                                    i.putExtra("mydate", mydate);
                                    i.putExtra("txt12", txt12);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                HashMap<String, String> map33 = new HashMap<String, String>();
                                map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("do you want to narrow list by days", TextToSpeech.QUEUE_ADD, map33);

                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String utteranceId6) {

                                    }

                                    @Override
                                    public void onError(String utteranceId6) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId6) {

                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                        try {
                                            startActivityForResult(intent, RESULT_SPEECH17);
                                        } catch (ActivityNotFoundException a) {
                                            Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    }
                                });
                                break;
                            }
                        }
                        if (txt.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "COST?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH16);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });
                            break;
                        }
                    }
                    cost = txt.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    map1.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID1");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tv1.setText(cost);
                    tts.speak("cost is" + cost, TextToSpeech.QUEUE_ADD, null);
                    tts.playSilence(1000, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond YES to Confirm ", TextToSpeech.QUEUE_ADD, map1);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId1) {

                        }

                        @Override
                        public void onError(String utteranceId1) {

                        }

                        @Override
                        public void onDone(String utteranceId1) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH16);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map42 = new HashMap<String, String>();
                    map42.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map42);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,COST?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH16);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });

                }

                break;
            }
            case RESULT_SPEECH17: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            HashMap<String, String> map33 = new HashMap<String, String>();
                            map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH17);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            tts.speak("choose from the list to update an item ", TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Intent i = new Intent(Main.this, Updatedatabycost.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("cost", cost);
                                i.putExtra("place_name", place_name);
                                i.putExtra("oldproduct", txt11);
                                i.putExtra("mydate", mydate);
                                i.putExtra("txt12", txt12);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    day = txt17.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map46 = new HashMap<String, String>();
                    map46.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    tv1.setText(day);
                    tts.speak("narrowing list by searching in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to continue", TextToSpeech.QUEUE_ADD, map46);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onDone(String utteranceId) {

                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH18);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,Number of Days?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH17);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                }


                break;
            }
            case RESULT_SPEECH18: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            daysminus = Integer.parseInt(day);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                            cal.add(Calendar.DAY_OF_YEAR, -daysminus);
                            newdate = s.format(new Date(cal.getTimeInMillis()));
                            query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + txt11 + "' AND COST ='" + cost + "' AND DATE BETWEEN '" + newdate + "' AND '" + mydate + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("no items found in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("choose from the list to update an item ", TextToSpeech.QUEUE_ADD, null);
                                try {
                                    Intent i = new Intent(Main.this, Updatedatabycost.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("cost", cost);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("oldproduct", txt11);
                                    i.putExtra("mydate", mydate);
                                    i.putExtra("txt12", txt12);

                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                HashMap<String, String> map61 = new HashMap<String, String>();
                                map61.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                                tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("choose item you want to update from the list", TextToSpeech.QUEUE_ADD, null);
                                try {
                                    Intent i = new Intent(Main.this, Updatedatabycostdate.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("cost", cost);
                                    i.putExtra("query", query);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("oldproduct", txt11);
                                    i.putExtra("mydate", mydate);
                                    i.putExtra("txt12", txt12);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();

                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH17);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } else {
                        HashMap<String, String> map4 = new HashMap<String, String>();
                        map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId4) {

                            }

                            @Override
                            public void onError(String utteranceId4) {

                            }

                            @Override
                            public void onDone(String utteranceId4) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH18);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });

                    }


                }
                break;
            }
            case RESULT_SPEECH19: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            HashMap<String, String> map33 = new HashMap<String, String>();
                            map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH19);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("choose from the list to delete an item ", TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Intent i = new Intent(Main.this, Deletedata.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("place_name", place_name);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    day = txt17.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map46 = new HashMap<String, String>();
                    map46.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    tts.speak("narrowing list by searching in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to continue", TextToSpeech.QUEUE_ADD, map46);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onDone(String utteranceId) {

                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH20);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,Number of Days?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH19);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                }


                break;
            }
            case RESULT_SPEECH20: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            daysminus = Integer.parseInt(day);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                            cal.add(Calendar.DAY_OF_YEAR, -daysminus);
                            newdate = s.format(new Date(cal.getTimeInMillis()));
                            query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + product_name + "' AND DATE BETWEEN '" + newdate + "' AND '" + mydate + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("no items found in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                                tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                                tts.speak("choose from the list to delete an item ", TextToSpeech.QUEUE_ADD, null);
                                try {
                                    Intent i = new Intent(Main.this, Deletedata.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("place_name", place_name);

                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            HashMap<String, String> map61 = new HashMap<String, String>();
                            map61.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("choose item you want to delete from the list", TextToSpeech.QUEUE_ADD, null);
                            try {
                                Intent i = new Intent(Main.this, Deletedatabycostdate.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("place_name", place_name);
                                i.putExtra("newdate", newdate);
                                i.putExtra("mydate", mydate);
                                i.putExtra("query", query);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();

                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH19);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } else {
                        HashMap<String, String> map4 = new HashMap<String, String>();
                        map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId4) {

                            }

                            @Override
                            public void onError(String utteranceId4) {

                            }

                            @Override
                            public void onDone(String utteranceId4) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH20);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });

                    }


                }
                break;
            }
            case RESULT_SPEECH21: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            HashMap<String, String> map33 = new HashMap<String, String>();
                            map33.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("respond the number of days you want to goback and search", TextToSpeech.QUEUE_FLUSH, map33);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH21);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");

                            tts.speak("choose from the list to update an item ", TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Intent i = new Intent(Main.this, Updatedata.class);
                                i.putExtra("product_name", product_name);

                                i.putExtra("place_name", place_name);
                                i.putExtra("oldproduct", txt11);
                                i.putExtra("mydate", mydate);
                                i.putExtra("txt12", txt12);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    day = txt17.replaceAll("[^\\d.]", "");
                    HashMap<String, String> map46 = new HashMap<String, String>();
                    map46.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    tv1.setText(day);
                    tts.speak("narrowing list by searching in last" + day + "days", TextToSpeech.QUEUE_FLUSH, null);
                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                    tts.speak("respond yes to continue", TextToSpeech.QUEUE_ADD, map46);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(String utteranceId) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onDone(String utteranceId) {

                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH22);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                } else {
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                    final AnimationDrawable animate =
                            (AnimationDrawable) iv.getBackground();
                    animate.start();
                    tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String utteranceId4) {

                        }

                        @Override
                        public void onError(String utteranceId4) {

                        }

                        @Override
                        public void onDone(String utteranceId4) {
                            animate.stop();
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO,Number of Days?");
                            try {
                                startActivityForResult(intent, RESULT_SPEECH21);
                            } catch (ActivityNotFoundException a) {
                                Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    });
                }


                break;
            }
            case RESULT_SPEECH22: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String txt17 = text.get(0).toString();
                    String txt6 = txt17.toLowerCase();
                    String[] str = {"yes", "no", "close"};
                    if (Arrays.asList(str).contains(txt6)) {
                        if (txt6.equals("close")) {
                            close();
                        }
                        if (txt6.equals("yes")) {

                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();
                            DBHelper dbh = new DBHelper(this);
                            SQLiteDatabase db = dbh.getReadableDatabase();
                            daysminus = Integer.parseInt(day);
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                            cal.add(Calendar.DAY_OF_YEAR, -daysminus);
                            newdate = s.format(new Date(cal.getTimeInMillis()));
                            query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND PRODUCT='" + product_name + "' AND DATE BETWEEN '" + newdate + "' AND '" + mydate + "'";
                            Cursor c = db.rawQuery(query, null);
                            c.moveToFirst();
                            int rowcount = 1;
                            int rows = c.getCount();
                            if (rows == 0) {
                                tts.speak("choose from the list to update an item ", TextToSpeech.QUEUE_FLUSH, null);
                                try {
                                    Intent i = new Intent(Main.this, Updatedata.class);
                                    i.putExtra("product_name", product_name);
                                    i.putExtra("query", query);
                                    i.putExtra("place_name", place_name);
                                    i.putExtra("oldproduct", txt11);
                                    i.putExtra("mydate", mydate);
                                    i.putExtra("txt12", txt12);
                                    Main.this.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            HashMap<String, String> map61 = new HashMap<String, String>();
                            map61.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            tts.speak("total" + rows + "items found", TextToSpeech.QUEUE_FLUSH, null);
                            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
                            tts.speak("choose item you want to update from the list", TextToSpeech.QUEUE_ADD, null);
                            try {
                                Intent i = new Intent(Main.this, Updatedatabycostdate.class);
                                i.putExtra("product_name", product_name);
                                i.putExtra("query", query);
                                i.putExtra("place_name", place_name);
                                i.putExtra("oldproduct", txt11);
                                i.putExtra("mydate", mydate);
                                i.putExtra("txt12", txt12);
                                Main.this.startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        if (txt6.equals("no")) {
                            HashMap<String, String> map54 = new HashMap<String, String>();
                            map54.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID6");
                            final AnimationDrawable animate =
                                    (AnimationDrawable) iv.getBackground();
                            animate.start();

                            tts.speak("cancelled dictate again", TextToSpeech.QUEUE_FLUSH, map54);

                            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                @Override
                                public void onStart(String utteranceId6) {

                                }

                                @Override
                                public void onError(String utteranceId6) {

                                }

                                @Override
                                public void onDone(String utteranceId6) {
                                    animate.stop();
                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Number of Days?");
                                    try {
                                        startActivityForResult(intent, RESULT_SPEECH21);
                                    } catch (ActivityNotFoundException a) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                }
                            });

                        }
                    } else {
                        HashMap<String, String> map4 = new HashMap<String, String>();
                        map4.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID4");
                        final AnimationDrawable animate =
                                (AnimationDrawable) iv.getBackground();
                        animate.start();
                        tts.speak("sorry didn't get you you have below options to choose", TextToSpeech.QUEUE_FLUSH, map4);

                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                            @Override
                            public void onStart(String utteranceId4) {

                            }

                            @Override
                            public void onError(String utteranceId4) {

                            }

                            @Override
                            public void onDone(String utteranceId4) {
                                animate.stop();
                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "YES,NO");
                                try {
                                    startActivityForResult(intent, RESULT_SPEECH22);
                                } catch (ActivityNotFoundException a) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });

                    }


                }
                break;
            }
        }


    }


}

