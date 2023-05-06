package com.example.iotapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iotapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class Fragment2 extends Fragment {
    public GraphView humidity;
    public LineGraphSeries seriesHumidity = new LineGraphSeries<DataPoint>();
    public TextView textView2;

    public Fragment2() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        textView2 = view.findViewById(R.id.txtHumidity);

        humidity = view.findViewById(R.id.graph2);
        humidity.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        humidity.getGridLabelRenderer().setGridColor(Color.WHITE);
        humidity.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        humidity.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        humidity.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        humidity.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);


        return view;
    }
    public void setTextView(String text) {
        textView2.setText("Humidity: " + text + "%");
    }
    public void drawGraph(LineGraphSeries lineGraphSeries) {
        humidity.addSeries(lineGraphSeries);
        humidity.onDataChanged(true, true);
    }



}