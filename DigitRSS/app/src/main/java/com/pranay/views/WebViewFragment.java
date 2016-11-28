package com.pranay.views;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pranay.digitrss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {
    WebView webView;
    ViewGroup contentView;
    String urlToLoad = "";
    ProgressBar mProgressBar;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_web_view, container, false);
        Bundle bundle = getArguments();
        urlToLoad = bundle.getString("urlToLoad");

        initView(contentView);
        Snackbar.make(container, "Hello", Snackbar.LENGTH_LONG).show();
        return contentView;

    }

    public void initView(ViewGroup viewGroup) {

        webView = (WebView) viewGroup.findViewById(R.id.webView);
        mProgressBar = (ProgressBar) viewGroup.findViewById(R.id.progressBarForWebView);
        mProgressBar.setVisibility(View.VISIBLE);

        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(urlToLoad);
        webView.setWebViewClient(new Callback());


    }


    public class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return (true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }

    }

}
