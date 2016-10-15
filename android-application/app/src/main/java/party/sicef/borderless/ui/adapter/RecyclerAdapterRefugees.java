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
import party.sicef.borderless.api.data.RefugeeProfile;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.activity.ItemDetailsActivity;
import party.sicef.borderless.util.AppConstants;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class RecyclerAdapterRefugees extends RecyclerView.Adapter<RecyclerAdapterRefugees.ViewHolder> {

    private Activity activity;
    private List<RefugeeProfile> items;

    public RecyclerAdapterRefugees(Activity activity) {
        this.activity = activity;
    }

    public RecyclerAdapterRefugees(Activity activity, List<RefugeeProfile> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_refugee, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getFirstName()+" "+items.get(position).getLastName());
        holder.birthDay.setText(items.get(position).getBirthDate());
        Picasso.with(activity)
                .load(RestAPI.web + items.get(position).getProfileImage())
                .fit()
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView name;
        TextView birthDay;
        ImageView profile;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card);
            birthDay = (TextView) view.findViewById(R.id.birthDate);
            name = (TextView) view.findViewById(R.id.title);
            profile = (ImageView) view.findViewById(R.id.profile);

            cardView.setOnClickListener(cardClickListener);
        }

        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

    }

}
