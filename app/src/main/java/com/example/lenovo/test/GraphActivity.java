package com.example.lenovo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart=findViewById(R.id.line_chart);
        LineDataSet lineDataSet= new LineDataSet(dataValues1(),"Data Set 1");
        ArrayList<LineDataSet> dataSets= new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData();
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(10, 20));
        dataVals.add(new Entry(10, 20));
        dataVals.add(new Entry(5, 16));
        dataVals.add(new Entry(20, 5));
        dataVals.add(new Entry(1, 10));
        return dataVals;
    }
}