package com.eovalencia.veddit.source.restApi;

import com.eovalencia.veddit.source.restApi.model.Constants;
import com.eovalencia.veddit.source.restApi.model.SubRedditResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Eovalencia on 22/01/17.
 */

public interface RedditApiEndPoint {

    @GET(RedditApiConstants.GET_SUBREDDIT)
    Call<SubRedditResponse> getSubReddits();
}
