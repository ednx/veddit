package com.eovalencia.veddit.source;

import android.util.Log;

import com.eovalencia.veddit.model.SubReddit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SubRedditRepository implements SubRedditDataSource {

    private static SubRedditRepository INSTANCE = null;

    private Map<String, SubReddit> mCachedSubReddits;

    private boolean mCacheIsDirty = false;

    private final SubRedditDataSource mSubRedditRemoteDataSource;

    private final SubRedditDataSource mSubRedditLocalDataSource;

    private SubRedditRepository(SubRedditDataSource subRedditRemoteDataSource, SubRedditDataSource subRedditLocalDataSource) {
        this.mSubRedditRemoteDataSource = subRedditRemoteDataSource;
        this.mSubRedditLocalDataSource = subRedditLocalDataSource;
    }

    public static SubRedditRepository getInstance(SubRedditDataSource subRedditRemoteDataSource, SubRedditDataSource subRedditLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (SubRedditRepository.class) {
                if (INSTANCE == null)
                    INSTANCE = new SubRedditRepository(subRedditRemoteDataSource, subRedditLocalDataSource);
            }
        }
        return INSTANCE;
    }

    @Override
    public void getSubReddits(final SubRedditDataSource.OnFinishedListener listener) {
        if (mCachedSubReddits != null && !mCacheIsDirty) {
            listener.onFinished(new ArrayList<>(mCachedSubReddits.values()));
            return;
        }
        if (mCacheIsDirty) {
            getTasksFromRemoteDataSource(listener);
        } else {
            Log.e("no Network","Entro");
            mSubRedditLocalDataSource.getSubReddits(new OnFinishedListener() {
                @Override
                public void onFinished(ArrayList<SubReddit> items) {
                    Log.e("no network onFinished","Entro");
                    refreshCache(items);
                    listener.onFinished(new ArrayList<>(mCachedSubReddits.values()));
                }

                @Override
                public void onFailedSearch() {
                    listener.onFailedSearch();
                }
            });
        }

    }

    private void getTasksFromRemoteDataSource(final OnFinishedListener listener) {
        Log.e("getTasksFromRemoteData","Entro");
        mSubRedditRemoteDataSource.getSubReddits(new OnFinishedListener() {
            @Override
            public void onFinished(ArrayList<SubReddit> items) {
                refreshCache(items);
                refreshLocalDataSource(items);
                listener.onFinished(new ArrayList<>(mCachedSubReddits.values()));
            }

            @Override
            public void onFailedSearch() {
                listener.onFailedSearch();
            }
        });
    }

    private void refreshCache(ArrayList<SubReddit> subReddits) {
        if (mCachedSubReddits == null) {
            mCachedSubReddits = new LinkedHashMap<>();
        }
        mCachedSubReddits.clear();
        for (SubReddit subReddit : subReddits) {
            mCachedSubReddits.put(subReddit.getId(), subReddit);
        }
        mCacheIsDirty = false;
    }


    private void refreshLocalDataSource(ArrayList<SubReddit> subReddits) {
        mSubRedditLocalDataSource.deleteAllSubReddit();
        for (SubReddit subReddit : subReddits) {
            mSubRedditLocalDataSource.saveSubReddit(subReddit);
        }
    }


    @Override
    public void getSubReddit(final String subRedditId, final SubRedditDataSource.GetSubRedditCallBack listener) {
        SubReddit cachedSubReddit = getSubRedditWithId(subRedditId);
        if (cachedSubReddit != null) {
            listener.onFinished(cachedSubReddit);
            return;
        }

        mSubRedditLocalDataSource.getSubReddit(subRedditId, new GetSubRedditCallBack() {
            @Override
            public void onFinished(SubReddit item) {
                if(mCachedSubReddits == null){
                    mCachedSubReddits = new LinkedHashMap<String, SubReddit>();
                }
                mCachedSubReddits.put(item.getId(), item);
                listener.onFinished(item);
            }

            @Override
            public void onFailedSearch() {
                mSubRedditRemoteDataSource.getSubReddit(subRedditId, new GetSubRedditCallBack() {
                    @Override
                    public void onFinished(SubReddit item) {
                        if(mCachedSubReddits == null){
                            mCachedSubReddits = new LinkedHashMap<String, SubReddit>();
                        }
                        mCachedSubReddits.put(item.getId(), item);
                        listener.onFinished(item);
                    }

                    @Override
                    public void onFailedSearch() {
                        listener.onFailedSearch();
                    }
                });
            }
        });
    }

    @Override
    public void saveSubReddit(SubReddit subReddit) {

    }

    @Override
    public void deleteAllSubReddit() {
        mSubRedditRemoteDataSource.deleteAllSubReddit();
        mSubRedditLocalDataSource.deleteAllSubReddit();

        if (mCachedSubReddits == null) {
            mCachedSubReddits = new LinkedHashMap<>();
        }
        mCachedSubReddits.clear();
    }

    public void refreshSubReddit() {
        mCacheIsDirty = true;
    }

    private SubReddit getSubRedditWithId(String id) {
        if (id != null) {
            if (mCachedSubReddits == null || mCachedSubReddits.isEmpty()) {
                return null;
            } else {
                return mCachedSubReddits.get(id);
            }
        }
        return null;
    }


}
