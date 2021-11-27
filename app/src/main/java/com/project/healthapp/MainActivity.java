package com.project.healthapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private LineChart sys_linechart, dia_linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sys_linechart = findViewById(R.id.sys_line_view);
        dia_linechart = findViewById(R.id.dia_Line_View);

        setupSYSGraphs();
        setupDIAGraph();
    }

    private void setupSYSGraphs() {
        //New empty data point containers for charts
        List<Entry> sys_values = new ArrayList<Entry>();

        //Declaring new data point entries for chart and adding them to container
        Entry entry = new Entry(1f, 160);
        sys_values.add(entry);
        Entry entry_1 = new Entry(2f, 125);
        sys_values.add(entry_1);
        Entry entry_2 = new Entry(3f, 123);
        sys_values.add(entry_2);
        Entry entry_3 = new Entry(4f, 155);
        sys_values.add(entry_3);
        Entry entry_4 = new Entry(5f, 150);
        sys_values.add(entry_4);
        Entry entry_5 = new Entry(6f, 139);
        sys_values.add(entry_5);

        //Adding Entries to DataSet
        LineDataSet set = new LineDataSet(sys_values, "");
        set.setColor(Color.argb(100, 101, 71, 143));
        set.setDrawCircles(true);
        set.setDrawCircleHole(true);
        set.setCircleColor(Color.argb(100, 101, 71, 143));
        set.setCircleHoleColor(Color.WHITE);

        //Adding DataSet to Line to Display on Chart
        LineData data = new LineData(set);
        sys_linechart.setData(data);

        //Disabling the Entry Labels
        data.setDrawValues(false);

        //Disabling Line Description
        Description description = sys_linechart.getDescription();
        description.setEnabled(false);

        //Disabling Legend
        Legend legend = sys_linechart.getLegend();
        legend.setEnabled(false);

        //Disabling the X-Axis
        XAxis xaxis = sys_linechart.getXAxis();
        xaxis.setAxisMinimum(-(1/64));
        xaxis.setEnabled(false);

        //Y-Axis Styling
        YAxis yAxis = sys_linechart.getAxisLeft();
        yAxis.setTextColor(Color.argb(100, 211, 211, 211));// setting text color
        yAxis.setTextSize(10f); // setting text size
        yAxis.setGranularity(1f); // setting interval to 1
        yAxis.setDrawAxisLine(false); // disable axis line
        yAxis.enableGridDashedLine(8f, 16f, 0f); // setting dashed line background
        yAxis.setGridColor(Color.argb(100, 211, 211, 211));
        sys_linechart.getAxisRight().setEnabled(false); // disable right y-axis

        //Refresh the Graph
        sys_linechart.invalidate();
    }

    private void setupDIAGraph() {
        //New empty data point containers for charts
        List<Entry> dia_values = new ArrayList<Entry>();

        //Declaring new data point entries for chart and adding them to container
        Entry entry = new Entry(1f, 160);
        dia_values.add(entry);
        Entry entry_1 = new Entry(2f, 125);
        dia_values.add(entry_1);
        Entry entry_2 = new Entry(3f, 123);
        dia_values.add(entry_2);
        Entry entry_3 = new Entry(4f, 155);
        dia_values.add(entry_3);
        Entry entry_4 = new Entry(5f, 150);
        dia_values.add(entry_4);
        Entry entry_5 = new Entry(6f, 139);
        dia_values.add(entry_5);

        //Adding Entries to DataSet
        LineDataSet set = new LineDataSet(dia_values, "");
        set.setColor(Color.argb(100, 101, 71, 143));
        set.setDrawCircles(true);
        set.setDrawCircleHole(true);
        set.setCircleColor(Color.argb(100, 101, 71, 143));
        set.setCircleHoleColor(Color.WHITE);

        //Adding DataSet to Line to Display on Chart
        LineData data = new LineData(set);
        dia_linechart.setData(data);

        //Disabling the Entry Labels
        data.setDrawValues(false);

        //Disabling Line Description
        Description description = dia_linechart.getDescription();
        description.setEnabled(false);

        //Disabling Legend
        Legend legend = dia_linechart.getLegend();
        legend.setEnabled(false);

        //Disabling the X-Axis
        XAxis xaxis = dia_linechart.getXAxis();
        xaxis.setAxisMinimum(-(1/64));
        xaxis.setEnabled(false);

        //Y-Axis Styling
        YAxis yAxis = dia_linechart.getAxisLeft();
        yAxis.setTextColor(Color.argb(100, 211, 211, 211));// setting text color
        yAxis.setTextSize(10f); // setting text size
        yAxis.setGranularity(1f); // setting interval to 1
        yAxis.setDrawAxisLine(false); // disable axis line
        yAxis.enableGridDashedLine(8f, 16f, 0f); // setting dashed line background
        yAxis.setGridColor(Color.argb(100, 211, 211, 211));
        dia_linechart.getAxisRight().setEnabled(false); // disable right y-axis

        //Refresh the Graph
        dia_linechart.invalidate();
    }
}