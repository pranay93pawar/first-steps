package com.pranay.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pranay.digitrss.HomeActivity;
import com.pranay.digitrss.R;
import com.pranay.models.FeedItem;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.pranay.digitrss.HomeActivity.webView;

/**
 * Created by pranay on 25/06/16.
 */
public class CurrentUpdatesAdapter extends RecyclerView.Adapter<CurrentUpdatesAdapter.ViewHolder> {
    ArrayList<String> mDataset;
    ArrayList<FeedItem> feedItemArrayList = new ArrayList<FeedItem>();




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
//        new AsyncTask<Void, Void, Void>() {
//            Bitmap bmp;
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    InputStream in = new URL(feedItemArrayList.get(position).getImageLink()).openStream();
//                    bmp = BitmapFactory.decodeStream(in);
//                } catch (Exception e) {
//                    // log error
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void result) {
//                if (bmp != null)
//                    holder.imageView.setImageBitmap(bmp);
//            }
//
//        }.execute();

        Picasso.with(holder.mContext).load(feedItemArrayList.get(position).getImageLink()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Clicked",Snackbar.LENGTH_LONG);
                String url = feedItemArrayList.get(position).getNavigationLink();
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItemArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
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

        }
    }

}
