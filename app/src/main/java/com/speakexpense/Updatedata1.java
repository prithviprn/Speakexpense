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
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Updatedata1 extends Activity {
    Context myContext = this;
    String product_name, cost, place_name;
    SimpleAdapter sa;
    private Button b;
    private File file;
    String filename;
    private Button home, id, dd, ud, help, settings,wallet;
    SimpleSideDrawer slide_me;
    FileWriter fw;
    String[] from = new String[]{"DATE", "PRODUCT", "COST", "PLACE"};
    int[] to = new int[]{com.speakexpense.R.id.item1, com.speakexpense.R.id.item2, com.speakexpense.R.id.item3, com.speakexpense.R.id.item4};

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
        lo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);
        ro.setVisibility(View.INVISIBLE);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("update expenses");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, Main.class);
                Updatedata1.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, AddExpenses.class);
                Updatedata1.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, DeleteExpenses.class);
                Updatedata1.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, UpdateExpenses.class);
                Updatedata1.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, Main.class);
                Updatedata1.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updatedata1.this, Set.class);
                Updatedata1.this.startActivity(intent);
            }
        });

        final String query = getIntent().getExtras().getString("query");
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        DBHelper dbh = new DBHelper(this);
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
        b = (Button) findViewById(com.speakexpense.R.id.expcsv);
        b.setOnClickListener(new View.OnClickListener() {

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
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(myContext);
                alert1.setTitle("Update Data");
                final EditText input1 = new EditText(myContext);
                final EditText input2 = new EditText(myContext);
                final EditText input3 = new EditText(myContext);
                input1.setHint("Enter Product Name");
                input2.setHint("Enter Cost");
                input3.setHint("Enter Place Name");
                alert1.setMessage("enter the new data");
                LinearLayout ll = new LinearLayout(myContext);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(input1);
                ll.addView(input2);
                ll.addView(input3);
                alert1.setView(ll);
                alert1.setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        c.moveToPosition(position);
                        int rowid = c.getInt(c.getColumnIndex("id"));
                        product_name = input1.getEditableText().toString();
                        cost = input2.getEditableText().toString();
                        place_name = input3.getEditableText().toString();
                        if (product_name.equals("") && cost.equals("") && place_name.equals("")) {
                            Toast t = Toast.makeText(getApplicationContext(), "please enter any one of the field", Toast.LENGTH_LONG);
                            t.show();
                        } else if (cost.equals("") && place_name.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updateproduct(rowid, product_name.toLowerCase());
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else if (product_name.equals("") && place_name.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updatecost(rowid, cost);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else if (product_name.equals("") && cost.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updateplace(rowid, place_name);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else if (product_name.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updatecostplace(rowid, cost, place_name);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else if (cost.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updateproductplace(rowid, product_name, place_name);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else if (place_name.equals("")) {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updateproductcost(rowid, product_name, cost);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        } else {
                            DBHelper dbh = new DBHelper(myContext);
                            dbh.updateall(rowid, product_name, cost, place_name);
                            Toast t = Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                });
                alert1.setNegativeButton("Cancel", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog1 = alert1.create();
                alertDialog1.show();

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
