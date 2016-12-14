package com.pranay.views;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pranay.digitrss.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
    ViewGroup contentView;
    TabLayout tabLayout;
    ViewPager dashBoardViewPager;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = (ViewGroup) inflater.inflate(R.layout.fragment_tab, container, false);
        initView(contentView);
        return contentView;
    }


    public void initView(ViewGroup viewGroup){
        tabLayout = (TabLayout) viewGroup.findViewById(R.id.tabs);
        dashBoardViewPager = (ViewPager) viewGroup.findViewById(R.id.dashBoardViewPager);
        tabLayout.setupWithViewPager(dashBoardViewPager);
        setViewPager(dashBoardViewPager);
    }

    public void setViewPager(ViewPager viewPager){

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(new FeedListFragment(),"Card");
        pagerAdapter.addFragment(new FeedTileFragment(),"Tile");
        pagerAdapter.addFragment(new FeedCardFragment(),"List");
        viewPager.setAdapter(pagerAdapter);

    }


    class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragmentList = new ArrayList<Fragment>();
        List<String> mFragmentTitleList = new ArrayList<String>();

        public PagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
