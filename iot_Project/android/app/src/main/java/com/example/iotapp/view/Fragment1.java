package com.example.iotapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iotapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Fragment1 extends Fragment {
     GraphView temperature;
     LineGraphSeries seriesTemperature = new LineGraphSeries<DataPoint>();
     TextView textView1;



    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        textView1 = view.findViewById(R.id.txtTemperature);
        temperature = view.findViewById(R.id.graph1);
        temperature.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        temperature.getGridLabelRenderer().setGridColor(Color.WHITE);
        temperature.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        temperature.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        temperature.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        temperature.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    public void setTextView(String text) {
        textView1.setText("Temperature: " + text + "℃");
    }
    public void drawGraph(LineGraphSeries lineGraphSeries) {
        temperature.addSeries(lineGraphSeries);
        temperature.onDataChanged(true, true);
    }
}