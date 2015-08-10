package com.speakexpense;

import java.text.DecimalFormat;
import java.util.Collections;
import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import java.util.*;
import com.navdrawer.SimpleSideDrawer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Graph extends Activity {
    DBHelper dbh;
    Cursor c;
    private Button home, id, dd, ud, help, settings,piechart;
    private Button viewdb, searchexp, viewcsv, bar, line;
    SimpleSideDrawer slide_me;
    int[] expenses = new int[12];
    int gy=0;
    private View mChart;
    private String[] mMonth = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.speakexpense.R.layout.graph);
        getIntent();
        slide_me = new SimpleSideDrawer(this);
        slide_me.setLeftBehindContentView(com.speakexpense.R.layout.left_menu);
        slide_me.setRightBehindContentView(com.speakexpense.R.layout.right_menu);
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
        title.setText("bar chart");
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
                Intent intent = new Intent(Graph.this, Main.class);
                Graph.this.startActivity(intent);
            }
        });
        id.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, AddExpenses.class);
                Graph.this.startActivity(intent);
            }
        });
        dd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, DeleteExpenses.class);
                Graph.this.startActivity(intent);
            }
        });
        ud.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, UpdateExpenses.class);
                Graph.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Main.class);
                Graph.this.startActivity(intent);
            }
        });
        settings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Set.class);
                Graph.this.startActivity(intent);
            }
        });

        viewdb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Displaydatabase.class);
                Graph.this.startActivity(intent);
            }
        });
        searchexp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Searchform.class);
                Graph.this.startActivity(intent);
            }
        });
        viewcsv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Csvfiles.class);
                Graph.this.startActivity(intent);
            }
        });
        bar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Graph.class);
                Graph.this.startActivity(intent);
            }
        });
        line.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Linechart.class);
                Graph.this.startActivity(intent);
            }
        });
        piechart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, Piechart.class);
                Graph.this.startActivity(intent);
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
        int[] x = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
           int gy=expenses[0];
        for(int i=0;i<expenses.length;i++) {
            if (expenses[i] > gy) {
                gy = expenses[i];
            }
        }

        XYSeries expenseseries = new XYSeries("Expenses");
        for (int i = 0; i < x.length; i++) {
            expenseseries.add(i, expenses[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(expenseseries);
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.GREEN);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Expense Chart");
        multiRenderer.setXTitle("Year " + today.year);
        multiRenderer.setYTitle("Amount");
        //setting text size of the title
        multiRenderer.setChartTitleTextSize(50);
        //setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(30);
        //setting text size of the graph lable
        multiRenderer.setLabelsTextSize(30);
        multiRenderer.setBarWidth(60);
        multiRenderer.setLegendTextSize(30);
        multiRenderer.setPointSize(20);
        //setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(true);
        //setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, true);
        //setting click false on graph
        multiRenderer.setClickEnabled(false);
        //setting zoom to false on both axis
        multiRenderer.setZoomEnabled(true, true);
        //setting lines to display on y axis
        multiRenderer.setShowGridY(false);
        //setting lines to display on x axis
        multiRenderer.setShowGridX(false);
        //setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
        //setting displaying line on grid
        multiRenderer.setShowGrid(false);
        //setting zoom to false
        multiRenderer.setZoomEnabled(true);
        multiRenderer.setDisplayValues(true);
        //setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(true);
        //setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
        //setting to in scroll to false
        multiRenderer.setInScroll(false);
        //setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(gy);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
        //setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.WHITE);
        //Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(com.speakexpense.R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        //setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{50, 50, 50, 50});
        for (int i = 0; i < x.length; i++) {
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }
        multiRenderer.addSeriesRenderer(expenseRenderer);
        //this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(com.speakexpense.R.id.graphchart);
        //remove any views before u paint the chart
        chartContainer.removeAllViews();
        //drawing bar chart
        mChart = ChartFactory.getBarChartView(Graph.this, dataset, multiRenderer, org.achartengine.chart.BarChart.Type.DEFAULT);
        //adding the view to the linearlayout
        chartContainer.addView(mChart);
    }

}
