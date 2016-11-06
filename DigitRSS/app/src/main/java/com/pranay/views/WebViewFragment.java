package com.pranay.views;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.pranay.digitrss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends android.webkit.WebViewFragment {
    WebView webView;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        webView = getWebView();
        Snackbar.make(container,"Hello",Snackbar.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_web_view, container, false);

    }



}
