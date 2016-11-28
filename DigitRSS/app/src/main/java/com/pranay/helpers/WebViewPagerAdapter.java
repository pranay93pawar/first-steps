package com.pranay.helpers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pranay.models.FeedItem;
import com.pranay.views.WebViewFragment;

import java.util.ArrayList;

/**
 * Created by LT-145-PRANAY-PAWAR on 08-Nov-16.
 */

public class WebViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<FeedItem> mfeedItemArrayList;

    public WebViewPagerAdapter(FragmentManager fm, ArrayList<FeedItem> feedItemArrayList) {
        super(fm);
        mfeedItemArrayList = feedItemArrayList;
    }

    @Override
    public Fragment getItem(int position) {

        String url = mfeedItemArrayList.get(position).getNavigationLink();

        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("urlToLoad", url);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @Override
    public int getCount() {
        return mfeedItemArrayList.size();
    }
}
