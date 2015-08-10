package com.speakexpense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Deletedata extends Activity {
    private static final int RESULT_SPEECH = 0;
    private final Context myContext = this;
    private TextToSpeech tts;
    private Button home, id, dd, ud, help, settings;
    SimpleSideDrawer slide_me;
    private int MY_DATA_CHECK_CODE = 0;
    String cost, date, place_name, product_name, txt19, query;
    SimpleAdapter sa;
    private Button b;
    private File file;
    String filename;
    FileWriter fw;
    String[] from = new String[]{"DATE", "PRODUCT", "COST", "PLACE"};
    int[] to = new int[]{com.speakexpense.R.id.item1, com.speakexpense.R.id.item2, com.speakexpense.R.id.item3, com.speakexpense.R.id.item4};
    DBHelper dbh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.displaydata);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.right_menu);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        id = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        ImageButton lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
        lo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        ro.setVisibility(View.INVISIBLE);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("delete expenses");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, Main.class);
                Deletedata.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, AddExpenses.class);
                Deletedata.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, DeleteExpenses.class);
                Deletedata.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, UpdateExpenses.class);
                Deletedata.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, Main.class);
                Deletedata.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deletedata.this, Set.class);
                Deletedata.this.startActivity(intent);
            }
        });
        tts = new TextToSpeech(this, new OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                }

            }
        });
        tts.setPitch(0.2f);
        tts.setSpeechRate(0.5f);
        b = (Button) findViewById(com.speakexpense.R.id.expcsv);
        txt19 = getIntent().getExtras().getString("txt19");
        product_name = getIntent().getExtras().getString("product_name");
        cost = getIntent().getExtras().getString("cost");
        date = getIntent().getExtras().getString("date");
        place_name = getIntent().getExtras().getString("place_name");
        query = getIntent().getExtras().getString("query");
        final List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        final Cursor c = db.rawQuery(query, null);
        int rows = c.getCount();
        if (rows == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "no items found", Toast.LENGTH_LONG);
            t.show();
        }
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
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                fillMaps.remove(position);
                sa.notifyDataSetChanged();
                sa.notifyDataSetInvalidated();
                c.moveToPosition(position);
                int rowid = c.getInt(c.getColumnIndex("id"));
                dbh.deletedata1(rowid);
                Toast t = Toast.makeText(getApplicationContext(), "deleted successfully", Toast.LENGTH_SHORT);
                t.show();
            }
        });
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

    private void writeCsvHeader(String h, String h1, String h2, String h3) throws IOException {
        String line = String.format("%s,%s,%s,%s\n", h, h1, h2, h3);
        fw.write(line);
    }

    private void writeCsvData(String txt, String txt1, String txt3, String txt4) throws IOException {
        String line = String.format("%s,%s,%s,%s\n", txt, txt1, txt3, txt4);
        fw.write(line);
    }
}
