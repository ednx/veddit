package com.eovalencia.veddit.view.subreddit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.source.SubRedditDataSource;
import com.eovalencia.veddit.source.SubRedditRepository;
import com.eovalencia.veddit.source.db.SubRedditLocalDataSource;
import com.eovalencia.veddit.source.restApi.RedditApiAdapter;

import java.util.ArrayList;

public class SubRedditPresenter implements ISubRedditFragment.Presenter {

    private boolean mFirstLoad = true;
    private final ISubRedditFragment.View mSubRedditView;
    private final SubRedditRepository mSubRedditRepository;
    private Context context;

    public SubRedditPresenter(ISubRedditFragment.View mSubRedditView, Context context) {
        this.mSubRedditView = mSubRedditView;
        this.context = context;
        mSubRedditView.setPresenter(this);
        mSubRedditRepository  = SubRedditRepository.getInstance(RedditApiAdapter.getInstance(), SubRedditLocalDataSource.getInstance(context));
    }

    @Override
    public void openSubRedditDetails(SubReddit subReddit) {

    }

    @Override
    public void start() {
        if(mSubRedditView != null){
            mSubRedditView.setUpLinearLayoutManager();
            mSubRedditView.setUpAdapter();
        }
        loadSubReddit(false);
    }

    public void loadSubReddit(boolean forceUpdate){
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI){
            mSubRedditView.showProgressBar();
        }
        if(forceUpdate && isOnline()){
            mSubRedditRepository.refreshSubReddit();
        }

        mSubRedditRepository.getSubReddits(new SubRedditDataSource.OnFinishedListener(){

            @Override
            public void onFinished(ArrayList<SubReddit> items) {
                processResult(items);
                mSubRedditView.hideProgressBar();
            }

            @Override
            public void onFailedSearch() {
                mSubRedditView.hideProgressBar();
                mSubRedditView.displayFailedSearch();
            }
        });
    }

    private void processResult(ArrayList<SubReddit> items){
        if (items.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            //processEmptyTasks();
        } else {
            // Show the list of tasks
            //mTasksView.showTasks(tasks);
            // Set the filter label's text.
            // showFilterLabel();
        }
        if(this.mSubRedditView != null){
            mSubRedditView.setItems(items);
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

}
