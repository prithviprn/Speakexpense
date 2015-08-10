package com.speakexpense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Yearlyexpenses extends Activity {
    DBHelper dbh;
    Cursor c;
    SimpleAdapter sa;
    private Button b1, b2, b3;
    SimpleSideDrawer slide_me;
    private Button home, id, dd, ud, help, settings;
    private Button b;
    String filename, symbol;
    private File file;
    FileWriter fw;
    private final Context myContext = this;
    int[] expenses = new int[12];
    private String[] months = new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private List<HashMap<String, String>> fillMaps;
    private String[] from = new String[]{"DATE", "COST"};
    private int[] to = new int[]{com.speakexpense.R.id.date, com.speakexpense.R.id.cost};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.yearlyexpenses);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.displaydata_rightmenu);
        b1 = (Button) findViewById(com.speakexpense.R.id.dailyexpenses);
        b2 = (Button) findViewById(com.speakexpense.R.id.monthlyexpenses);
        b3 = (Button) findViewById(com.speakexpense.R.id.yearlyexpenses);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        id = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Displaydatabase.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        b2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Monthlyexpenses.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        b3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Yearlyexpenses.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        ImageButton lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
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
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("Yearly expenses");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Main.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, AddExpenses.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, DeleteExpenses.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, UpdateExpenses.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Main.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Yearlyexpenses.this, Set.class);
                Yearlyexpenses.this.startActivity(intent);
            }
        });
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        b = (Button) findViewById(com.speakexpense.R.id.expcsv);
        dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        SharedPreferences pref = getSharedPreferences("mypref", MODE_PRIVATE);
        symbol = pref.getString("symbol", "$");
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 1; i < 13; i++) {
            c = db.rawQuery(
                    "SELECT SUM(COST) FROM Mydata WHERE strftime('%Y-%m', `DATE`) ='" + today.year + "-" + df.format(i) + "'", null);
            c.moveToFirst();
            int sum = c.getInt(0);
            expenses[i - 1] = sum;
        }
        fillMaps = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 12; i++) {
            String date = months[i] + "," + today.year;
            String cost = expenses[i] + symbol;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("DATE", date);
            map.put("COST", cost);
            fillMaps.add(map);
        }
        sa = new SimpleAdapter(this, fillMaps, com.speakexpense.R.layout.yearlyexpenses1, from, to);
        ListView lv = (ListView) findViewById(com.speakexpense.R.id.listview1);
        lv.setAdapter(sa);
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myContext);
                alertDialogBuilder.setTitle("File Name");
                final EditText input = new EditText(myContext);
                input.setHint("Enter File Name");
                alertDialogBuilder.setView(input);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    filename = input.getText().toString();
                                    file = new File("/sdcard/csvfiles/" + filename + ".csv");
                                    if (!file.exists()) {
                                        file.createNewFile();
                                        fw = new FileWriter(file, true);
                                        writeCsvHeader("DATE", "COST");
                                        fw.flush();
                                        for (int i = 0; i < 12; i++) {
                                            String date = months[i] + "-" + today.year;
                                            String cost = expenses[i] + "$";
                                            fw = new FileWriter(file, true);
                                            writeCsvData(date, cost);
                                            fw.flush();
                                            fw.close();
                                        }
                                        ;
                                    }
                                    Toast t = Toast.makeText(getApplicationContext(), "File Created", Toast.LENGTH_LONG);
                                    t.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }

    private void writeCsvHeader(String h, String h1) throws IOException {
        String line = String.format("%s,%s\n", h, h1);
        fw.write(line);
    }

    private void writeCsvData(String txt, String txt1) throws IOException {
        String line = String.format("%s,%s\n", txt, txt1);
        fw.write(line);
    }

}
