package com.pranay.helpers;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pranay.digitrss.HomeActivity;
import com.pranay.digitrss.R;
import com.pranay.models.FeedItem;
import com.pranay.views.WebViewFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pranay on 25/06/16.
 */
public class CurrentUpdatesAdapter extends RecyclerView.Adapter<CurrentUpdatesAdapter.ViewHolder> {
    ArrayList<String> mDataset;
    static ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();




    public CurrentUpdatesAdapter(ArrayList<FeedItem> xfeedItemArrayList){
        feedItemArrayList = xfeedItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

    static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView title;
        CardView cardView;
        TextView author;
        ImageView imageView;
        Context mContext;

        ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            title = (TextView) v.findViewById(R.id.title);
            author = (TextView) v.findViewById(R.id.author);
            imageView = (ImageView) v.findViewById(R.id.thumbnail);
            mContext = v.getContext();
            v.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String url = feedItemArrayList.get(position).getNavigationLink();
            Snackbar.make(view,"Hello",Snackbar.LENGTH_LONG).show();
            WebViewFragment webViewFragment = new WebViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("urlToLoad",url);
            webViewFragment.setArguments(bundle);
            ((HomeActivity)mContext).LoadFragment(webViewFragment);
        }
    }

}
