package com.eovalencia.veddit.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eovalencia.veddit.R;
import com.eovalencia.veddit.adapter.OnCarClickedListener;
import com.eovalencia.veddit.model.SubReddit;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


/**
 * Created by Eovalencia on 21/01/17.
 */

public class SubRedditViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView subscribers;
    private TextView date;

    Context context;


    public SubRedditViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.card_image);
        title = (TextView) itemView.findViewById(R.id.card_title);
        description = (TextView) itemView.findViewById(R.id.card_text);
        subscribers = (TextView) itemView.findViewById(R.id.card_subscribers);
        date = (TextView) itemView.findViewById(R.id.card_date);
    }

    public void bindToSubreddit(final SubReddit subreddit, final OnCarClickedListener onCarClickedListener) {
        title.setText(subreddit.getTitle());
        description.setText(subreddit.getDescription());
        subscribers.setText(new DecimalFormat("###,###.##").format(subreddit.getSubscriber()) + " Suscriptores");
        date.setText(new SimpleDateFormat("EEE, dd MMM yyyy").format(new Date(subreddit.getDate() * 1000)));
        if(subreddit.getIcon_img() != null && !subreddit.getIcon_img().isEmpty())
            this.setSubRedditImage(subreddit.getIcon_img());
        else
            this.setPlaceholderImage();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCarClickedListener.onCarCliked(subreddit);
            }
        });
    }

    private void setSubRedditImage(String urlImage){
        Picasso.with(itemView.getContext())
                .load(urlImage)
                .placeholder(R.drawable.subreddit_placeholder)
                .into(this.image);
    }

    private void setPlaceholderImage() {
        Picasso.with(itemView.getContext())
                .load(R.drawable.subreddit_placeholder)
                .into(this.image);
    }
}
