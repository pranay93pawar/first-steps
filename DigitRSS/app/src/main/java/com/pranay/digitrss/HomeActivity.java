package com.pranay.digitrss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

import com.pranay.helpers.CurrentUpdatesAdapter;
import com.pranay.helpers.Utils;
import com.pranay.models.FeedItem;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();
    ArrayList<String> countries = new ArrayList<>();
    CurrentUpdatesAdapter mAdapter;
    public static WebView webView;
    public static MyOnclickListener myOnclickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Digit");
        //toolbar.setLogo(R.mipmap.logo);
        toolbar.setSubtitle("RSS Feed");
        setSupportActionBar(toolbar);

        initView();

        prepareFeed();

    }

    public void prepareFeed(){


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                feedItemArrayList = Utils.getFeedList();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                setAdapter();
            }
        };

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public void initView(){

        myOnclickListener = new MyOnclickListener();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new Callback());

    }


    public void setAdapter(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new CurrentUpdatesAdapter(feedItemArrayList);
        recyclerView.setAdapter(mAdapter);
    }

    public class MyOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
           int position = recyclerView.getChildLayoutPosition(view);
            String url = feedItemArrayList.get(position).getNavigationLink();
            webView.loadUrl(url);
        }
    }

    public class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return (true);
        }

    }
}
