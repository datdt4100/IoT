package com.example.iotapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.iotapp.controller.DatabaseHelper;
import com.example.iotapp.controller.MQTTHelper;
import com.example.iotapp.model.TemperatureData;
import com.example.iotapp.view.Fragment1;
import com.example.iotapp.view.Fragment2;
import com.example.iotapp.view.Fragment3;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;
    ProgressBar progressBar;
    TextView textView1;
//    TextView textView2;
//    TextView textView3;
    Switch switch1;
    Switch switch2;
    boolean dontSend;
    Spinner spinner;
    Button buttonTemperature;
    Button buttonHumidity;
    Button camera;
    TextView textTemperature;
    LineGraphSeries seriesTemperature = new LineGraphSeries<DataPoint>();
    LineGraphSeries seriesHumidity = new LineGraphSeries<DataPoint>();

    FragmentManager fragmentManager = getSupportFragmentManager();

    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

    ListView listView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar51);
        textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);
        switch1 = findViewById(R.id.switch1);
//        switch2 = findViewById(R.id.switch2);
//        GraphView temperature;

        listView = findViewById(R.id.listView1);

        spinner = findViewById(R.id.spinner1);
        String[] items = new String[]{"5s", "15s", "30s", "45s"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

//
//        humidity.getGridLabelRenderer().setHorizontalLabelsVisible(false);
//        humidity.getGridLabelRenderer().setGridColor(Color.WHITE);
//        humidity.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
//        humidity.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
//        humidity.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
//        humidity.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);


        buttonTemperature = findViewById(R.id.button3);

        buttonTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, Fragment1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        buttonHumidity = findViewById(R.id.button4);
        buttonHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, Fragment2.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        camera = findViewById(R.id.button5);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, Fragment3.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });


        startMQTT();
        switch1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        timer1.start();
                        if (isChecked == true) {

                            sendDataMQTT("minhtrung181/feeds/button", "ON");

                        } else if (isChecked == false) {

                            sendDataMQTT("minhtrung181/feeds/button", "OFF");
                        }
                    }
                }
        );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem == "5s") {
                    sendDataMQTT("minhtrung181/feeds/period", "5");
                }
                else if (selectedItem == "15s") {
                    sendDataMQTT("minhtrung181/feeds/period", "15");
                }
                else if (selectedItem == "30s") {
                    sendDataMQTT("minhtrung181/feeds/period", "30");
                }
                else if (selectedItem == "45s") {
                    sendDataMQTT("minhtrung181/feeds/period", "45");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing

            }
        });


    }

    CountDownTimer timer1 = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
        sendDataMQTT("minhtrung181/feeds/ping", "1");

        }

        @Override
        public void onFinish() {
            switch1.setChecked(false);
            textView1.setText("Fail to check the sensor !");
        }

    };



    public Date convertLocalDateTime (LocalDateTime y){
        Instant instant = y.toInstant(ZoneOffset.of("+07:00"));
        Date out = Date.from(instant);
        return out;
    }
    public void checkList() {

        ArrayAdapter<TemperatureData> adapter = new ArrayAdapter<TemperatureData>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getTemperature());
        listView.setAdapter(adapter);
    }


    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }
    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                textView1.setText("Connect complete");
                progressBar.setIndeterminate(false);
                progressBar.setMax(1);
                progressBar.setProgress(1);

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if (topic.contains("nhietdo")){

//                    textView2.setText("Temperature: " + message.toString());
                    Fragment1 fragment1 = (Fragment1) fragmentManager.findFragmentById(R.id.fragmentContainerView2);

                    //set value display
                    fragment1.setTextView(message.toString());

                    //draw graph
                    double x = Double.parseDouble(message.toString());
                    LocalDateTime time = LocalDateTime.now();
                    Date y = convertLocalDateTime(time);
                    seriesTemperature.appendData(new DataPoint(y,x), true, 10, true);
                    fragment1.drawGraph(seriesTemperature);

                    //save value to database
                    String date = y.toString();
                    TemperatureData tempeData = new TemperatureData(x, date);
                    databaseHelper.addOne(tempeData);
                    checkList();


                }

                else if (topic.contains("humidity")) {
                    Fragment2 fragment2 = (Fragment2) fragmentManager.findFragmentById(R.id.fragmentContainerView2);
                    fragment2.setTextView(message.toString());
                    double x = Double.parseDouble(message.toString());
                    LocalDateTime time = LocalDateTime.now();
                    Date y = convertLocalDateTime(time);
                    seriesHumidity.appendData(new DataPoint(y,x), true, 10, true);
                    fragment2.drawGraph(seriesHumidity);
                }

                else if (topic.contains("ping")){
                    textView1.setText("Connect complete");
                    timer1.cancel();
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

}