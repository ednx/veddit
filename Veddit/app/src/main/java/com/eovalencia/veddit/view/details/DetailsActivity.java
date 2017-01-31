package com.eovalencia.veddit.view.details;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.eovalencia.veddit.BaseActivity;
import com.eovalencia.veddit.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends BaseActivity implements DetailsActivityFragment.onSubRedditSelectedListener{

    public static final String EXTRA_SUBREDDIT_ID = "SUBREDDIT_ID";
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imageView = (ImageView) findViewById(R.id.image);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        String subRedditId = getIntent().getStringExtra(EXTRA_SUBREDDIT_ID);

        DetailsActivityFragment detailsActivityFragment = (DetailsActivityFragment) getSupportFragmentManager()
                .findFragmentById(getContaintFragmentId());

        if (detailsActivityFragment == null) {
            detailsActivityFragment = DetailsActivityFragment.newInstance(subRedditId);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(getContaintFragmentId(), detailsActivityFragment, "DetailsActivityFragment");
            transaction.commit();
        }

        new DetailsPresenter(subRedditId, detailsActivityFragment, getApplicationContext());

    }

    public void setCollapsingToolbar(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void setImageViewCollapsingToolbar(String url) {
        if(url != null && !url.isEmpty()){
            setSubRedditImage(url);
        }else{
            setPlaceholderImage();
        }
    }

    private void setSubRedditImage(String urlImage){
        Picasso.with(this)
                .load(urlImage)
                .placeholder(R.drawable.a)
                .into(this.imageView);
    }

    private void setPlaceholderImage() {
        Picasso.with(this)
                .load(R.drawable.a)
                .into(this.imageView);
    }
}
