package com.eovalencia.veddit.view.subreddit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eovalencia.veddit.R;
import com.eovalencia.veddit.adapter.OnCarClickedListener;
import com.eovalencia.veddit.adapter.SearchResultsAdapter;
import com.eovalencia.veddit.model.SubReddit;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class SubRedditFragment extends Fragment implements ISubRedditFragment.View, OnCarClickedListener{

    private onSubRedditSelectedListener mListener;
    private SearchResultsAdapter mAdapter;
    RecyclerView mRecycler;
    private ISubRedditFragment.Presenter mSubRedditPresenter;

    public SubRedditFragment() {
    }

    public static SubRedditFragment newInstance() {
        return new SubRedditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new SearchResultsAdapter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecycler.setHasFixedSize(true);
        mSubRedditPresenter.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (onSubRedditSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onSubRedditSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setUpLinearLayoutManager() {
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mManager);
    }

    @Override
    public void setUpAdapter() {
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void setItems(ArrayList<SubReddit> subReddits) {
        mAdapter.replace(subReddits);
    }

    @Override
    public void displayFailedSearch() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayNetworkError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayServerError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ISubRedditFragment.Presenter presenter) {
        if(presenter != null) mSubRedditPresenter = presenter;
    }

    @Override
    public void showProgressBar() {
        mListener.showProgressDialog();
    }

    @Override
    public void hideProgressBar() {
        mListener.hideProgressDialog();
    }

    @Override
    public void goToDetailSubreddit(SubReddit temporal) {
        mListener.onSubRedditSelected(temporal.getId());
    }

    @Override
    public void onCarCliked(SubReddit subReddit) {
        mListener.onSubRedditSelected(String.valueOf(subReddit.getId()));
    }

    public interface onSubRedditSelectedListener{
        void onSubRedditSelected(String id);
        void showProgressDialog();
        void hideProgressDialog();
    }
}

