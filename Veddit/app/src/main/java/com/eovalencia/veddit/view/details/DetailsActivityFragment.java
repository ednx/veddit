package com.eovalencia.veddit.view.details;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eovalencia.veddit.R;
import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.view.subreddit.ISubRedditFragment;
import com.eovalencia.veddit.view.subreddit.SubRedditFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment implements IDetailsFragment.View{

    private static final String ARGUMENT_SUBREDDIT_ID = "SUBREDDIT_ID";

    private onSubRedditSelectedListener mListener;

    private IDetailsFragment.Presenter mDetailsPresenter;

    private TextView description;
    private TextView displayName;

    public DetailsActivityFragment() {
    }

    public static DetailsActivityFragment newInstance(String subRedditId){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_SUBREDDIT_ID, subRedditId);
        DetailsActivityFragment fragment = new DetailsActivityFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        description = (TextView) view.findViewById(R.id.place_detail);
        displayName = (TextView) view.findViewById(R.id.place_display_name);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailsPresenter.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DetailsActivityFragment.onSubRedditSelectedListener) context;
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
    public void setPresenter(IDetailsFragment.Presenter presenter) {
        if(presenter != null) mDetailsPresenter = presenter;
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
    public void displayFailedSearch() {

    }

    @Override
    public void displayNetworkError() {

    }

    @Override
    public void displayServerError() {
        Toast.makeText(getActivity(), "No se encontro la tarea seleccionada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToDetailSubreddit(SubReddit temporal) {
        mListener.setCollapsingToolbar(temporal.getTitle());
        mListener.setImageViewCollapsingToolbar(temporal.getIcon_img());
        description.setText(temporal.getDescription_long());
        displayName.setText(temporal.getDisplay_name());
        Toast.makeText(getActivity(), temporal.getUrl(), Toast.LENGTH_SHORT).show();
    }

    public interface onSubRedditSelectedListener{
        void showProgressDialog();
        void hideProgressDialog();
        void setCollapsingToolbar(String title);
        void setImageViewCollapsingToolbar(String url);
    }
}
