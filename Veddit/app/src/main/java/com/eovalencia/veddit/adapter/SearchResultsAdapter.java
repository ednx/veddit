package com.eovalencia.veddit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eovalencia.veddit.R;
import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.adapter.viewholder.SubRedditViewHolder;

import java.util.ArrayList;

/**
 * Created by Eovalencia on 21/01/17.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SubRedditViewHolder> {

    private ArrayList<SubReddit> subreddits;
    private OnCarClickedListener onCarClickedListener;

    public SearchResultsAdapter(OnCarClickedListener onCarClickedListener) {
        this.subreddits = new ArrayList<SubReddit>(0);
        this.onCarClickedListener = onCarClickedListener;
    }

    @Override
    public SubRedditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_subreddit, parent, false);
        return new SubRedditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubRedditViewHolder holder, int position) {
        SubReddit subreddit =  subreddits.get(position);
        holder.bindToSubreddit(subreddit, onCarClickedListener);

    }

    @Override
    public int getItemCount() {
        return subreddits.isEmpty() ? 0 : subreddits.size();

    }

    public void replace(ArrayList<SubReddit> artists){
        this.subreddits = artists;
        notifyDataSetChanged();
    }

    /**
     * Delete all the items
     * */
    public void clear() {
        if (!subreddits.isEmpty()) {
            subreddits.clear();
            notifyDataSetChanged();
        }
    }

}
