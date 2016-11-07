package com.pranay.views;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    public void initView(ViewGroup viewGroup){

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView1);

    }


    public void setAdapter(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new CurrentUpdatesAdapter(feedItemArrayList);
        recyclerView.setAdapter(mAdapter);
    }


}
