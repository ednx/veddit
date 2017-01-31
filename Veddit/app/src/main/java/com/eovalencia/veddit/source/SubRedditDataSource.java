package com.eovalencia.veddit.source;

import com.eovalencia.veddit.model.SubReddit;

import java.util.ArrayList;

public interface SubRedditDataSource {
    void getSubReddits(SubRedditDataSource.OnFinishedListener listener);
    void getSubReddit(String subRedditId, SubRedditDataSource.GetSubRedditCallBack listener);
    void saveSubReddit(SubReddit subReddit);
    void deleteAllSubReddit();

    interface OnFinishedListener{
        void onFinished(ArrayList<SubReddit> items);

        void onFailedSearch();
    }

    interface GetSubRedditCallBack{
        void onFinished(SubReddit item);
        void onFailedSearch();
    }
}
