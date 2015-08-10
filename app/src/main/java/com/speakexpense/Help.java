package com.speakexpense;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.navdrawer.SimpleSideDrawer;


public class Help extends Activity {
    SimpleSideDrawer slide_me;
    private Button home, id, dd, ud, help, settings;
    private final Context myContext = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.layout_help);
        getIntent();
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(R.layout.left_menu);
        home = (Button) findViewById(R.id.home);
        id = (Button) findViewById(R.id.insertdata);
        dd = (Button) findViewById(R.id.deletedata);
        ud = (Button) findViewById(R.id.updatedata);
        help = (Button) findViewById(R.id.help);
        settings = (Button) findViewById(R.id.Settings);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("HELP");
        ImageButton lo = (ImageButton) mCustomView.findViewById(R.id.optionsleft);
        ImageButton ro = (ImageButton) mCustomView.findViewById(R.id.optionsright);
        ro.setVisibility(View.INVISIBLE);
        lo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                slide_me.toggleLeftDrawer();

            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, Main.class);
                Help.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, Help.class);
                Help.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, DeleteExpenses.class);
                Help.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, UpdateExpenses.class);
                Help.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, Help.class);
                Help.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, Set.class);
                Help.this.startActivity(intent);
            }
        });
    }
}
