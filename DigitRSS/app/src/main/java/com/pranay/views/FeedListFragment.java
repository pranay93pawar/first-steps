package com.pranay.views;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pranay.digitrss.R;
import com.pranay.helpers.CurrentUpdatesAdapter;
import com.pranay.helpers.Utils;
import com.pranay.models.FeedItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();
    ArrayList<String> countries = new ArrayList<>();
    CurrentUpdatesAdapter mAdapter;
    ViewGroup contentView;
    ProgressBar mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public FeedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_list, container, false);
        initView(contentView);

        prepareFeed();

        return contentView;
    }


    public void prepareFeed(){


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                feedItemArrayList = Utils.getFeedList(getContext());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                feedItemArrayList = Utils.getFeedItemsFromDB(getContext());
                setAdapter(feedItemArrayList);
                mProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public void initView(ViewGroup viewGroup){

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView1);
        mProgressBar = (ProgressBar) viewGroup.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) viewGroup.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareFeed();
            }


        });


    }


    public void setAdapter(ArrayList<FeedItem> feedItems){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CurrentUpdatesAdapter(feedItems);
        recyclerView.setAdapter(mAdapter);
    }


}
