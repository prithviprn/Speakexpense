package com.speakexpense;

import java.text.DecimalFormat;
import org.achartengine.ChartFactory;
import org.achartengine.model.*;
import org.achartengine.renderer.*;
import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Piechart extends Activity {
    DBHelper dbh;
    Cursor c;
    private Button home, id, dd, ud, help, settings,wallet;

    SimpleSideDrawer slide_me;
    int[] expenses = new int[12];
    int gy=0;
    private View mChart;
    private String[] mMonth = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    private Button expbyplace,expbymonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.graph);
        getIntent();
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(R.layout.piechart_right_menu);
        home = (Button) findViewById(com.speakexpense.R.id.home);
        id = (Button) findViewById(com.speakexpense.R.id.insertdata);
        dd = (Button) findViewById(com.speakexpense.R.id.deletedata);
        ud = (Button) findViewById(com.speakexpense.R.id.updatedata);
        help = (Button) findViewById(com.speakexpense.R.id.help);
        settings = (Button) findViewById(com.speakexpense.R.id.Settings);
        expbymonth= (Button) findViewById(com.speakexpense.R.id.expbymonth);
        expbyplace= (Button) findViewById(com.speakexpense.R.id.expbyplace);
        expbyplace.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, Piechartbyplace.class);
                Piechart.this.startActivity(intent);
            }
        });
        expbymonth.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, Piechart.class);
                Piechart.this.startActivity(intent);
            }
        });
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(com.speakexpense.R.layout.main_actionbar, null);
        TextView title= (TextView) mCustomView.findViewById(R.id.title);
        title.setText("Pie chart");
        ImageButton lo = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsleft);
        ImageButton ro = (ImageButton) mCustomView.findViewById(com.speakexpense.R.id.optionsright);

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
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, Main.class);
                Piechart.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, AddExpenses.class);
                Piechart.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, DeleteExpenses.class);
                Piechart.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, UpdateExpenses.class);
                Piechart.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, Main.class);
                Piechart.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Piechart.this, Set.class);
                Piechart.this.startActivity(intent);
            }
        });

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 1; i < 13; i++) {
            c = db.rawQuery(
                    "SELECT SUM(COST) FROM Mydata WHERE strftime('%Y-%m', `DATE`) ='" + today.year + "-" + df.format(i) + "'", null);
            c.moveToFirst();
            int sum = c.getInt(0);
            expenses[i - 1] = sum;
        }
        int[] colors = { Color.rgb(219,136,144), Color.rgb(179,48,61), Color.rgb(97,25,32), Color.rgb(232,128,192),Color.rgb(179,4,111),Color.rgb(177,104,204),Color.rgb(87,5,117),Color.rgb(118,101,201)
        ,Color.rgb(51,196,165),Color.rgb(8,82,66),Color.rgb(113,224,99),Color.rgb(209,214,69)};
        int gy=expenses[0];
        for(int i=0;i<expenses.length;i++) {
            if (expenses[i] > gy) {
                gy = expenses[i];
            }
        }
// Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(
                " Expenses");
        for (int i = 0; i < expenses.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(mMonth[i], expenses[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < expenses.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
//Adding colors to the chart
            defaultRenderer.setBackgroundColor(Color.BLACK);
            defaultRenderer.setApplyBackgroundColor(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer
                .setChartTitle("Expenses");
        defaultRenderer.setChartTitleTextSize(100);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLegendTextSize(50);
        defaultRenderer.setBackgroundColor(Color.WHITE);
        defaultRenderer.setFitLegend(true);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLabelsTextSize(50);
        defaultRenderer.setLegendHeight(50);
        defaultRenderer.setMargins(new int[]{100, 100, 100, 100});
        defaultRenderer.setDisplayValues(true);
        // this part is used to display graph on the xml
        // Creating an intent to plot bar chart using dataset and
        // multipleRenderer
        // Intent intent = ChartFactory.getPieChartIntent(getBaseContext(),
        // distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        // startActivity(intent);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.graphchart);
        // remove any views before u paint the chart
        chartContainer.removeAllViews();
        // drawing pie chart
        mChart = ChartFactory.getPieChartView(Piechart.this,
                distributionSeries, defaultRenderer);
        // adding the view to the linearlayout
        chartContainer.addView(mChart);


    }

}
