package com.speakexpense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Searchform extends Activity implements OnClickListener {
    private EditText et1, et2, et3, et4;
    private Button b,b2,b3,speakproduct,speakcost,speakplace;
    private static final int RESULT_SPEECH = 0;
    private static final int RESULT_SPEECH1 = 1;
    private static final int RESULT_SPEECH2 = 2;
    private String voicetxt,voicetxt1,voicetxt2;
    private Button home, id, dd, ud, help, settings,piechart;
    private Button viewdb, searchexp, viewcsv, bar, line;
    SimpleSideDrawer slide_me;
    String mydate, product_name, place_name, cost, query, date;
    DatePickerDialog dpd;
    SimpleDateFormat sdf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.searchform);
        AdView mAdView = (AdView) findViewById(com.speakexpense.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.right_menu);
        et1 = (EditText) findViewById(com.speakexpense.R.id.editText1);
        et2 = (EditText) findViewById(com.speakexpense.R.id.editText2);
        et3 = (EditText) findViewById(com.speakexpense.R.id.editText3);
        et4 = (EditText) findViewById(com.speakexpense.R.id.editText4);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        id = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);
        piechart= (Button) findViewById(R.id.graph3);

        viewdb = (Button) findViewById(com.speakexpense.R.id.viewdb);
        searchexp = (Button) findViewById(com.speakexpense.R.id.searchdb);
        viewcsv = (Button) findViewById(com.speakexpense.R.id.csvfiles);
        bar = (Button) findViewById(com.speakexpense.R.id.graph);
        line = (Button) findViewById(com.speakexpense.R.id.graph2);
        b = (Button) findViewById(com.speakexpense.R.id.button1);
        b2= (Button) findViewById(R.id.clearfields);
        b3= (Button) findViewById(R.id.cleardatefield);
        speakproduct= (Button) findViewById(R.id.speakproduct);
        speakcost= (Button) findViewById(R.id.speakcost);
        speakplace= (Button) findViewById(R.id.speakplace);
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        et1.setInputType(InputType.TYPE_NULL);
        et1.requestFocus();
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("search Expenses");
        ImageButton lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
        lo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        ro.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleRightDrawer();
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
            }
        });
        speakproduct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Product Name?");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                try {

                    startActivityForResult(intent, RESULT_SPEECH);


                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Ops! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        speakcost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Cost?");
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
        speakplace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Place Name?");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                try {

                    startActivityForResult(intent, RESULT_SPEECH2);


                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Ops! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                et1.setText("");
            }
        });
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Main.class);
                Searchform.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, AddExpenses.class);
                Searchform.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, DeleteExpenses.class);
                Searchform.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, UpdateExpenses.class);
                Searchform.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Main.class);
                Searchform.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Set.class);
                Searchform.this.startActivity(intent);
            }
        });

        viewdb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Displaydatabase.class);
                Searchform.this.startActivity(intent);
            }
        });
        searchexp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Searchform.class);
                Searchform.this.startActivity(intent);
            }
        });
        viewcsv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Csvfiles.class);
                Searchform.this.startActivity(intent);
            }
        });
        bar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Graph.class);
                Searchform.this.startActivity(intent);
            }
        });
        line.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Linechart.class);
                Searchform.this.startActivity(intent);
            }
        });
        piechart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchform.this, Piechart.class);
                Searchform.this.startActivity(intent);
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        dpd = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et1.setText(sdf.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        et1.setOnClickListener(this);
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String prdname= et2.getEditableText().toString();
                String prdname2=prdname.replaceAll("[^A-Za-z]", "");
                String placename=et4.getEditableText().toString();
                product_name=prdname2.replaceAll("\\s+", "");
                String pn2=placename.replaceAll("[^A-Za-z]", "");
                cost = et3.getEditableText().toString();
                place_name=pn2.replaceAll("\\s+", "");
                date = et1.getEditableText().toString();
                if(cost.matches("\\d+(\\.\\d{1,2})?")==false && cost.matches("")==false)
                {
                        speakcost.setVisibility(View.INVISIBLE);
                        et3.setError("please enter the cost correctly");

                }
                else if (date.equals("") && product_name.equals("") && place_name.equals("") && cost.equals("")) {
                    Toast t = Toast.makeText(getApplicationContext(), "please enter any one of the field to search", Toast.LENGTH_LONG);
                    t.show();
                } else if (product_name.equals("") && place_name.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' ORDER BY DATE DESC";

                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);

                } else if (date.equals("") && place_name.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PRODUCT = '" + product_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("") && product_name.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PLACE = '" + place_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("") && product_name.equals("") && place_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("") && product_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PLACE='" + place_name + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("") && place_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PRODUCT='" + product_name + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PRODUCT='" + product_name + "' AND PLACE = '" + place_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (product_name.equals("") && place_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (product_name.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND PLACE = '" + place_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (place_name.equals("") && cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PRODUCT='" + product_name + "' AND DATE = '" + date + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (date.equals("")) {
                    query = "SELECT * FROM Mydata WHERE PRODUCT='" + product_name + "' AND PLACE='" + place_name + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (product_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND PLACE='" + place_name + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (place_name.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND PRODUCT='" + product_name + "' AND COST = '" + cost + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else if (cost.equals("")) {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND PRODUCT='" + product_name + "' AND PLACE='" + place_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                } else {
                    query = "SELECT * FROM Mydata WHERE DATE='" + date + "' AND PRODUCT = '" + product_name + "' AND COST='" + cost + "' AND PLACE='" + place_name + "' ORDER BY DATE DESC";
                    Intent i = new Intent(Searchform.this, Searchdatabase.class);
                    i.putExtra("query", query);
                    Searchform.this.startActivity(i);
                }
            }
        });

    }

    public void onClick(View view) {
        if (view == et1) {
            dpd.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voicetxt = text.get(0).toString().toLowerCase();
                    et2.setText(voicetxt);
                    break;
                }
            }
            case RESULT_SPEECH1: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voicetxt1 = text.get(0).toString().toLowerCase();
                    String costfield=voicetxt1.replaceAll("[^\\d.]", "");
                    et3.setText(costfield);
                    break;
                }
            }
            case RESULT_SPEECH2: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voicetxt2 = text.get(0).toString().toLowerCase();
                    et4.setText(voicetxt2);
                    break;
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.speakexpense.R.menu.displaydata, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
