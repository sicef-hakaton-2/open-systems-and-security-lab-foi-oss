package party.sicef.borderless.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;

import party.sicef.borderless.R;
import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.data.NewsData;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.activity.ItemDetailsActivity;

/**
 * Created by Andro on 11/14/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Activity activity;
    private NewsData[] items;

    public RecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public RecyclerAdapter(Activity activity, NewsData[] items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Load image
        Picasso.with(activity)
                .load(RestAPI.web + items[position].getImage())
                .into(holder.image);

        // Display title
        holder.title.setText(items[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView image;
        TextView title;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card);
            image = (ImageView) view.findViewById(R.id.image_details);
            title = (TextView) view.findViewById(R.id.title);

            cardView.setOnClickListener(cardClickListener);
        }

        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ItemDetailsActivity.class);

                intent.putExtra(ItemDetailsActivity.TAG_IMAGE_DETAILS, items[getAdapterPosition()].getImage());
                intent.putExtra(ItemDetailsActivity.TAG_TITLE_DETAILS, items[getAdapterPosition()].getTitle());
                intent.putExtra(ItemDetailsActivity.TAG_ID, items[getAdapterPosition()].getId());

                if (Build.VERSION.SDK_INT > 21) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity, image, "item_image");
                    activity.startActivity(intent, options.toBundle());

                } else {
                    activity.startActivity(intent);
                }
            }
        };

    }

}
