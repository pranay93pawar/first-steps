package com.pranay.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pranay.digitrss.R;
import com.pranay.helpers.WebViewPagerAdapter;
import com.pranay.models.FeedItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewPagerFragment extends Fragment {
    private int position;
    private ArrayList<FeedItem> feedItemArrayList ;
    ViewPager viewPager;

    public WebViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view_pager, container, false);

        initView(v);
        setAdapter();
        return v;
    }

    public void initView(View v){

        viewPager = (ViewPager) v.findViewById(R.id.pager_web_view);
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        feedItemArrayList = bundle.getParcelableArrayList("data");
    }

    public void setAdapter(){
        WebViewPagerAdapter webViewPagerAdapter = new WebViewPagerAdapter(getFragmentManager(),feedItemArrayList);
        viewPager.setAdapter(webViewPagerAdapter);
        viewPager.setCurrentItem(position);
    }

}
