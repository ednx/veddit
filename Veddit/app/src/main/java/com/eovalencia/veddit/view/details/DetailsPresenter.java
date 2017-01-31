package com.eovalencia.veddit.view.details;

import android.content.Context;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.source.SubRedditDataSource;
import com.eovalencia.veddit.source.SubRedditRepository;
import com.eovalencia.veddit.source.db.SubRedditLocalDataSource;
import com.eovalencia.veddit.source.restApi.RedditApiAdapter;

import java.util.ArrayList;

/**
 * Created by Eovalencia on 30/01/17.
 */

public class DetailsPresenter implements IDetailsFragment.Presenter {

    private String mSubRedditId;
    private final IDetailsFragment.View mDetailsView;
    private final SubRedditRepository mSubRedditRepository;
    private Context context;

    public DetailsPresenter(String subRedditId, IDetailsFragment.View mSubRedditView, Context context) {
        this.mSubRedditId = subRedditId;
        this.mDetailsView = mSubRedditView;
        this.context = context;
        this.mSubRedditRepository  = SubRedditRepository.getInstance(RedditApiAdapter.getInstance(), SubRedditLocalDataSource.getInstance(context));
        mSubRedditView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if (mSubRedditId.isEmpty() || mSubRedditId == null) {
            mDetailsView.displayFailedSearch();
            return;
        }

        mDetailsView.showProgressBar();
        mSubRedditRepository.getSubReddit(mSubRedditId, new SubRedditDataSource.GetSubRedditCallBack() {
            @Override
            public void onFinished(SubReddit item) {
                mDetailsView.hideProgressBar();
                if (null == item) {
                    mDetailsView.displayServerError();
                } else {
                    showTask(item);
                }
            }

            @Override
            public void onFailedSearch() {
                mDetailsView.displayServerError();
            }

        });
    }

    private void showTask(SubReddit subReddit) {
        if(!subReddit.getId().isEmpty()){
            mDetailsView.goToDetailSubreddit(subReddit);
        }
    }
}
