package com.pranay.views;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
public class FeedCardFragment extends Fragment {

    FeedCardFragment.ListAdapter mAdapter;
    ViewGroup contentView;
    ProgressBar mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();

    public FeedCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_list, container, false);
        initView(contentView);

        setAdapter();

        return contentView;
    }


    public void initView(ViewGroup viewGroup) {

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView1);
        mProgressBar = (ProgressBar) viewGroup.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) viewGroup.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new AsyncTask<Object, Void, Object>() {

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        feedItemArrayList = Utils.getFeedList(getContext());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        setAdapter();
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

        });

    }

    public void setAdapter() {

        feedItemArrayList = Utils.getFeedItemsFromDB(getContext());

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new FeedCardFragment.ListAdapter(feedItemArrayList);
        recyclerView.setAdapter(mAdapter);

        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    class ListAdapter extends RecyclerView.Adapter<FeedCardFragment.ListAdapter.ViewHolder> {
        ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();
        ArrayList<String> mDataset;


        public ListAdapter(ArrayList<FeedItem> xfeedItemArrayList) {
            feedItemArrayList = xfeedItemArrayList;
        }

        @Override
        public FeedCardFragment.ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_list, parent, false);

            return new FeedCardFragment.ListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FeedCardFragment.ListAdapter.ViewHolder holder, final int position) {
            holder.title.setText(feedItemArrayList.get(position).getTitle());
            holder.author.setText(feedItemArrayList.get(position).getPostAuthor());
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
            TextView author;
            ImageView imageView;
            Context mContext;

            ViewHolder(View v) {
                super(v);
                title = (TextView) v.findViewById(R.id.listTitle);
                imageView = (ImageView) v.findViewById(R.id.listImageView);
                author = (TextView) v.findViewById(R.id.listAuthor);
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
