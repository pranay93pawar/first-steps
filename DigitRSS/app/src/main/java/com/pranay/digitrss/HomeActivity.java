package com.pranay.digitrss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pranay.views.FeedListFragment;

public class HomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Digit");
        //toolbar.setLogo(R.mipmap.logo);
        toolbar.setSubtitle("RSS Feed");
        setSupportActionBar(toolbar);

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
}
