package com.pranay.views;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranay.digitrss.HomeActivity;
import com.pranay.digitrss.R;
import com.pranay.helpers.Utils;
import com.pranay.models.FeedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedTileFragment extends Fragment {

    ArrayList<String> countries = new ArrayList<>();
    TileAdapter mAdapter;
    ViewGroup contentView;
    ProgressBar mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();

    public FeedTileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_list, container, false);
        initView(contentView);

        prepareFeed();

        return contentView;
    }


    public void prepareFeed() {


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


    public void initView(ViewGroup viewGroup) {

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

    public void setAdapter(ArrayList<FeedItem> feedItems) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new TileAdapter(feedItems);
        recyclerView.setAdapter(mAdapter);
    }


    class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder> {
        ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();
        ArrayList<String> mDataset;


        public TileAdapter(ArrayList<FeedItem> xfeedItemArrayList) {
            feedItemArrayList = xfeedItemArrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_tile, parent, false);

            return new TileAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.title.setText(feedItemArrayList.get(position).getTitle());
            Picasso.with(holder.mContext).load(feedItemArrayList.get(position).getImageLink()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return feedItemArrayList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;
            ImageView imageView;
            Context mContext;

            ViewHolder(View v) {
                super(v);
                title = (TextView) v.findViewById(R.id.titleText);
                imageView = (ImageView) v.findViewById(R.id.imageView3);
                mContext = v.getContext();
                v.setOnClickListener(this);

            }


            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                String url = feedItemArrayList.get(position).getNavigationLink();
                Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG).show();

                WebViewPagerFragment webViewPagerFragment = new WebViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putParcelableArrayList("data", feedItemArrayList);
                webViewPagerFragment.setArguments(bundle);
                ((HomeActivity) mContext).LoadFragment(webViewPagerFragment);


            }
        }

    }
}