package com.eovalencia.veddit.view.details;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.view.subreddit.ISubRedditFragment;

import java.util.ArrayList;

/**
 * Created by Eovalencia on 30/01/17.
 */

public interface IDetailsFragment {

    interface View{

        void setPresenter(IDetailsFragment.Presenter presenter);

        void showProgressBar();

        void hideProgressBar();

        void displayFailedSearch();

        void displayNetworkError();

        void displayServerError();

        void goToDetailSubreddit(SubReddit temporal);
    }

    interface Presenter{

        void start();

    }
}
