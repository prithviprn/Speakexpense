package com.speakexpense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Monthlyexpenses extends Activity {
    private Spinner s1;
    private Button b, b1, b2, b3;
    SimpleSideDrawer slide_me;
    Cursor c;
    Context mycontext = this;
    FileWriter fw;
    List<HashMap<String, String>> fillMaps;
    String[] from = new String[]{"DATE", "PRODUCT", "COST", "PLACE"};
    int[] to = new int[]{com.speakexpense.R.id.item1, com.speakexpense.R.id.item2, com.speakexpense.R.id.item3, com.speakexpense.R.id.item4};
    SimpleAdapter sa;
    private File file;
    String filename;
    DBHelper dbh;
    private Button home, id, dd, ud, help, settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.monthlyexpenses);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.displaydata_rightmenu);
        b = (Button) findViewById(com.speakexpense.R.id.expcsv);
        b1 = (Button) findViewById(com.speakexpense.R.id.dailyexpenses);
        b2 = (Button) findViewById(com.speakexpense.R.id.monthlyexpenses);
        b3 = (Button) findViewById(com.speakexpense.R.id.yearlyexpenses);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        id = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);
        s1 = (Spinner) findViewById(com.speakexpense.R.id.spinner1);
        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Displaydatabase.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        b2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Monthlyexpenses.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        b3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Yearlyexpenses.class);
                Monthlyexpenses.this.startActivity(intent);
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
        title.setText("Monthly Expenses");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Main.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, AddExpenses.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, DeleteExpenses.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, UpdateExpenses.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Main.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Monthlyexpenses.this, Set.class);
                Monthlyexpenses.this.startActivity(intent);
            }
        });
        fillMaps = new ArrayList<HashMap<String, String>>();
        file = new File("/sdcard/csvfiles/");
        file.mkdir();
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mycontext);
                alertDialogBuilder.setTitle("File Name");
                final EditText input = new EditText(mycontext);
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
                                        writeCsvHeader("DATE", "PRODUCT", "COST", "PLACE");
                                        fw.flush();
                                        c.moveToFirst();
                                        if (c.isFirst()) {
                                            int i = 0;
                                            do {
                                                i++;
                                                String date1 = c.getString(1);
                                                String product1 = c.getString(2);
                                                String cost1 = c.getString(3);
                                                String place1 = c.getString(4);
                                                fw = new FileWriter(file, true);
                                                writeCsvData(date1, product1, cost1, place1);
                                                fw.flush();
                                                fw.close();
                                            } while (c.moveToNext());
                                        }
                                        Toast t = Toast.makeText(getApplicationContext(), "File Created", Toast.LENGTH_LONG);
                                        t.show();
                                    }
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


        s1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("January")) {
                    selectmonth("01");
                } else if (item.equals("February")) {
                    selectmonth("02");
                } else if (item.equals("March")) {
                    selectmonth("03");
                } else if (item.equals("April")) {
                    selectmonth("04");
                } else if (item.equals("May")) {
                    selectmonth("05");
                } else if (item.equals("June")) {
                    selectmonth("06");
                } else if (item.equals("July")) {
                    selectmonth("07");
                } else if (item.equals("August")) {
                    selectmonth("08");
                } else if (item.equals("September")) {
                    selectmonth("09");
                } else if (item.equals("October")) {
                    selectmonth("10");
                } else if (item.equals("November")) {
                    selectmonth("11");
                } else if (item.equals("December")) {
                    selectmonth("12");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(1);
            }
        });
    }

    public void selectmonth(String month) {
        fillMaps.clear();
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        dbh = new DBHelper(mycontext);
        SQLiteDatabase db = dbh.getReadableDatabase();
        c = db.rawQuery(
                "SELECT * FROM Mydata WHERE strftime('%Y-%m', `DATE`) ='" + today.year + "-" + month + "'", null);
        c.moveToFirst();
        if (c.isFirst()) {
            int i = 0;
            do {
                i++;
                String date1 = c.getString(1);
                String product1 = c.getString(2);
                String cost1 = c.getString(3);
                String place1 = c.getString(4);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("DATE", date1);
                map.put("PRODUCT", product1);
                map.put("COST", cost1);
                map.put("PLACE", place1);
                fillMaps.add(map);
            } while (c.moveToNext());
        }
        sa = new SimpleAdapter(this, fillMaps, com.speakexpense.R.layout.displaydata1, from, to);
        ListView lv = (ListView) findViewById(com.speakexpense.R.id.listview);
        lv.setAdapter(sa);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sa.notifyDataSetChanged();
                sa.notifyDataSetInvalidated();

            }
        });
    }

    private void writeCsvHeader(String h, String h1, String h2, String h3) throws IOException {
        String line = String.format("%s,%s,%s,%s\n", h, h1, h2, h3);
        fw.write(line);
    }

    private void writeCsvData(String txt, String txt1, String txt3, String txt4) throws IOException {
        String line = String.format("%s,%s,%s,%s\n", txt, txt1, txt3, txt4);
        fw.write(line);
    }
}