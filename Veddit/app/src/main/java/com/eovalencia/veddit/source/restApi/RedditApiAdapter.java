package com.eovalencia.veddit.source.restApi;

import android.support.annotation.NonNull;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.source.SubRedditDataSource;
import com.eovalencia.veddit.source.restApi.deserialize.RedditDeserializer;
import com.eovalencia.veddit.source.restApi.model.SubRedditResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eovalencia on 22/01/17.
 */

public class RedditApiAdapter implements SubRedditDataSource{

    private static RedditApiAdapter INSTANCE = null;

    private RedditApiAdapter() {}

    public static RedditApiAdapter getInstance(){
        if(INSTANCE == null){
            synchronized (RedditApiAdapter.class) {
                if(INSTANCE == null)
                    INSTANCE = new RedditApiAdapter();
            }
        }
        return INSTANCE;
    }
    private RedditApiEndPoint connectionApiRestReddit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RedditApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        return retrofit.create(RedditApiEndPoint.class);
    }

    @NonNull
    private Gson converterGsonDeserialize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SubRedditResponse.class, new RedditDeserializer());
        return gsonBuilder.create();
    }

    @Override
    public void getSubReddits(final SubRedditDataSource.OnFinishedListener listener) {
        Gson gson = converterGsonDeserialize();
        RedditApiEndPoint redditApiEndPoint = connectionApiRestReddit(gson);
        Call<SubRedditResponse> subRedditResponseCall = redditApiEndPoint.getSubReddits();
        subRedditResponseCall.enqueue(new Callback<SubRedditResponse>() {
            @Override
            public void onResponse(Call<SubRedditResponse> call, Response<SubRedditResponse> response) {
                SubRedditResponse subRedditResponse = response.body();
                if(response.isSuccessful() && response.body() != null){
                    subRedditResponse.getSubRedditsResponse().isEmpty();
                    ArrayList<SubReddit> subReddits = subRedditResponse.getSubRedditsResponse();
                    listener.onFinished(subReddits);
                }
            }

            @Override
            public void onFailure(Call<SubRedditResponse> call, Throwable t) {
                listener.onFailedSearch();
            }
        });
    }

    @Override
    public void getSubReddit(String subRedditId, SubRedditDataSource.GetSubRedditCallBack listener) {

    }

    @Override
    public void saveSubReddit(SubReddit subReddit) {

    }

    @Override
    public void deleteAllSubReddit() {

    }
}
