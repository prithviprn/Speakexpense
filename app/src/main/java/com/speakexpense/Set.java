package com.speakexpense;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.navdrawer.SimpleSideDrawer;

import java.util.Currency;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Set extends FragmentActivity {
    TextToSpeech tts;
    private EditText et1, et2;
    String pitch, sprate;
    SimpleSideDrawer slide_me;
    private Button b1, b2;
    private Button home, idd, dd, ud, help, settings,wallet;
    RadioGroup rg;
    Spinner s1;
    String name, symbol;
    int id;
    SharedPreferences pref;
    SharedPreferences.Editor ed;
    String currency, selectedcountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.set);
        getIntent();
        AdView mAdView = (AdView) findViewById(com.speakexpense.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        s1 = (Spinner) findViewById(com.speakexpense.R.id.spinner1);
        b1 = (Button) findViewById(com.speakexpense.R.id.button1);
        b2 = (Button) findViewById(com.speakexpense.R.id.button2);
        et1 = (EditText) findViewById(com.speakexpense.R.id.myname);
        et2 = (EditText) findViewById(com.speakexpense.R.id.country);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        idd = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);

        rg = (RadioGroup) findViewById(com.speakexpense.R.id.radioGroup1);
        pref = getSharedPreferences("mypref", MODE_PRIVATE);
        name = pref.getString("name", "Guest");
        selectedcountry = pref.getString("selectedcountry", "Select Country");
        String ss = pref.getString("spselection", "");
        for (int i = 0; i < 3; i++)
            if (ss.equals(s1.getItemAtPosition(i).toString())) {
                s1.setSelection(i);
                break;
            }
        if (pref.getInt("checked", 0) != 0) {

            rg.check(pref.getInt("checked", 0));

        }
        if (!name.equals("")) {
            et1.setText(name);
        }
        if (!selectedcountry.equals("")) {
            et2.setText(selectedcountry);
        }
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("Setttings");
        ImageButton lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        lo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        ro.setVisibility(View.INVISIBLE);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, Main.class);
                Set.this.startActivity(intent);
            }
        });
        idd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, AddExpenses.class);
                Set.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, DeleteExpenses.class);
                Set.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, UpdateExpenses.class);
                Set.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, Help.class);
                Set.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set.this, Set.class);
                Set.this.startActivity(intent);
            }
        });

        s1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("High")) {
                    sprate = "1.1f";
                } else if (item.equals("Moderate")) {
                    sprate = "0.8f";
                } else if (item.equals("Low")) {
                    sprate = "0.5f";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(1);
            }
        });
        et2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        Currency c = Currency.getInstance(new Locale("en", code));
                        symbol = c.getSymbol(new Locale("en", code));
                        currency = c.toString();
                        et2.setText(name + ":  " + code + ":  " + currency);
                        selectedcountry = et2.getText().toString();
                        picker.dismiss();

                    }
                });

            }
        });

        rg.setSelected(true);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == com.speakexpense.R.id.female) {
                    rg.check(checkedId);
                    pitch = "1.0f";
                } else if (checkedId == com.speakexpense.R.id.male) {
                    pitch = "0.5f";
                    rg.check(checkedId);

                } else if (checkedId == com.speakexpense.R.id.robot) {
                    pitch = "0.2f";
                    rg.check(checkedId);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id = rg.getCheckedRadioButtonId();
                getName();
                getA();
                getsprate();
                ed = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                ed.putString("name", name);
                ed.putString("pitch", pitch);
                ed.putString("sprate", sprate);
                ed.putString("symbol", symbol);
                ed.putInt("checked", id);
                ed.putString("spselection", s1.getSelectedItem().toString());
                ed.putString("selectedcountry", selectedcountry);
                ed.apply();

                Intent i = new Intent(Set.this, Main.class);
                Set.this.startActivity(i);
            }
        });
        b2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Set.this, Main.class);
                Set.this.startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public String getName() {
        name = et1.getEditableText().toString();
        return name;
    }

    public String getA() {
        return pitch;
    }

    public String getsprate() {
        return sprate;
    }
}
