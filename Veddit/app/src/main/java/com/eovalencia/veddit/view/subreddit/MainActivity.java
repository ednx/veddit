package com.eovalencia.veddit.view.subreddit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.eovalencia.veddit.BaseActivity;
import com.eovalencia.veddit.R;
import com.eovalencia.veddit.view.details.DetailsActivity;

public class MainActivity extends BaseActivity implements SubRedditFragment.onSubRedditSelectedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        SubRedditFragment subRedditFragment = (SubRedditFragment) getSupportFragmentManager().findFragmentById(getContaintFragmentId());
        if(subRedditFragment == null){
            subRedditFragment = SubRedditFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(getContaintFragmentId(), subRedditFragment, "SubRedditFragment");
            transaction.commit();
        }

        SubRedditPresenter mSubRedditFragmentPresenter = new SubRedditPresenter(subRedditFragment, getApplicationContext());

        if (savedInstanceState != null) {

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about_me) {
            return true;
        }else if(id == R.id.action_exit){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSubRedditSelected(String id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_SUBREDDIT_ID, id);
        startActivity(intent);
    }
}
