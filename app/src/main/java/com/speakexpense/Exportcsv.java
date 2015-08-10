package com.speakexpense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class Exportcsv extends Activity {
    private final Context myContext = this;
    String filename;
    File file;
    FileWriter fw;

    public void exportcsv() {
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
