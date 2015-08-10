package com.speakexpense;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Csvfiles extends Activity {
    private List<String> myList;
    Context context = this;
    File file;
    private Button home, id, dd, ud, help, settings,piechart;
    private Button viewdb, searchexp, viewcsv, bar, line;
    SimpleSideDrawer slide_me;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private static final String MEDIA_PATH = new String(Environment.getExternalStorageDirectory() + "/csvfiles/");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.csvfiles);
        getIntent();
        AdView mAdView = (AdView) findViewById(com.speakexpense.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.right_menu);
        lv = (ListView) findViewById(com.speakexpense.R.id.listView1);
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
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("csv files");
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
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Main.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, AddExpenses.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, DeleteExpenses.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, UpdateExpenses.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Main.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Set.class);
                Csvfiles.this.startActivity(intent);
            }
        });

        viewdb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Displaydatabase.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        searchexp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Searchform.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        viewcsv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Csvfiles.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        bar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Graph.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        piechart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Piechart.class);
                Csvfiles.this.startActivity(intent);
            }
        });
        line.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Csvfiles.this, Linechart.class);
                Csvfiles.this.startActivity(intent);
            }
        });

        myList = new ArrayList<String>();
        file = new File("/sdcard/csvfiles/");
        file.mkdirs();
        File directory = Environment.getExternalStorageDirectory();
        file = new File(directory + "/csvfiles");

        File list[] = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            myList.add(list[i].getName());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        lv.setOnItemClickListener(new OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view,
                                                              int position, long id) {
                                          Intent intent = new Intent();
                                          intent.setAction(android.content.Intent.ACTION_VIEW);
                                          File file = new File(MEDIA_PATH + myList.get(position));

                                          MimeTypeMap mime = MimeTypeMap.getSingleton();
                                          String ext = file.getName().substring(file.getName().indexOf(".") + 1);
                                          String type = mime.getMimeTypeFromExtension(ext);

                                          intent.setDataAndType(Uri.fromFile(file), type);
                                          startActivity(intent);

                                      }
                                  }
        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.speakexpense.R.menu.csvfiles, menu);

    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case com.speakexpense.R.id.delete:
                String filename = myList.get(info.position);
                String MEDIA_PATH = new String(Environment.getExternalStorageDirectory() + "/csvfiles/" + filename);
                File file = new File(MEDIA_PATH);
                file.delete();
                myList.remove(info.position);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
                return true;
            case com.speakexpense.R.id.share:
                String filename2 = myList.get(info.position);
                String MEDIA_PATH2 = new String(Environment.getExternalStorageDirectory() + "/csvfiles/" + filename2);
                File file2 = new File(MEDIA_PATH2);
                Uri u1 = null;
                u1 = Uri.fromFile(file2);
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("application/csv");
                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                startActivity(Intent.createChooser(sendIntent, "Share Via"));
                return true;
            case com.speakexpense.R.id.rename:
                final String filename1 = myList.get(info.position);
                final String sdcard = new String(Environment.getExternalStorageDirectory() + "/csvfiles/");
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Rename the File");
                alert.setMessage("Enter Name Here");
                final EditText input = new EditText(context);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String srt = input.getEditableText().toString();
                        File from = new File(sdcard, filename1);
                        File to = new File(sdcard, srt + ".csv");
                        from.renameTo(to);

                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }

}
