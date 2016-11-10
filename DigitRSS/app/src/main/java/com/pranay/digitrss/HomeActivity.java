package com.pranay.digitrss;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pranay.views.FeedListFragment;

public class HomeActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("About")){
            Toast.makeText(HomeActivity.this,"Clicked on About",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
