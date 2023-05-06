package com.example.iotapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.iotapp.R;


public class Fragment3 extends Fragment {
    WebView webview;
    String httpLink = "http://192.168.0.166:5000/";


    public Fragment3() {




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        webview = view.findViewById(R.id.webview1);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(httpLink);

        return view;

    }
}