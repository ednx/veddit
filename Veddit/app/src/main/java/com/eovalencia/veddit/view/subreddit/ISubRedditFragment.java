package com.eovalencia.veddit.view.subreddit;

import com.eovalencia.veddit.model.SubReddit;

import java.util.ArrayList;

/**
 * Created by Eovalencia on 21/01/17.
 */

public interface ISubRedditFragment {

    interface View{

        void setPresenter(Presenter presenter);

        void showProgressBar();

        void hideProgressBar();

        void setUpLinearLayoutManager();

        void setUpAdapter();

        void setItems(ArrayList<SubReddit> subReddits);

        void displayFailedSearch();

        void displayNetworkError();

        void displayServerError();

        void goToDetailSubreddit(SubReddit temporal);
    }

    interface Presenter{

        void openSubRedditDetails(SubReddit subReddit);

        void start();
    }


}
