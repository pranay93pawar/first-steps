package com.pranay.digitrss;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pranay.views.FeedListFragment;

public class HomeActivity extends AppCompatActivity{

    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar.setTitle("Digit");
        //toolbar.setLogo(R.mipmap.logo);
        toolbar.setSubtitle("RSS Feed");
        setSupportActionBar(toolbar);

        collapsingToolbarLayout.setTitle("Digit RSS Feed");
        //collapsingToolbarLayout.setExpandedTitleGravity(View.TEXT_ALIGNMENT_GRAVITY);


        initView();
        setView();
    }

    public void initView(){


    }

    public void setView(){
        FeedListFragment feedListFragment = new FeedListFragment();
        LoadFragment(feedListFragment);
    }

    public void LoadFragment(Fragment fragmentToLoad){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.contentFragment,fragmentToLoad);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.setExpanded(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.setExpanded(true);
    }
}
