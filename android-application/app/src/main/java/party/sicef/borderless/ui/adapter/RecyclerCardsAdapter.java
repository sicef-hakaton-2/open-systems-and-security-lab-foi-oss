package party.sicef.borderless.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import party.sicef.borderless.R;
import party.sicef.borderless.api.data.ContactData;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.activity.ContactActivity;
import party.sicef.borderless.ui.activity.ItemDetailsActivity;
import party.sicef.borderless.ui.activity.MapsActivity;
import party.sicef.borderless.util.DistanceHelper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by Andro on 11/15/2015.
 */
public class RecyclerCardsAdapter extends RecyclerView.Adapter<RecyclerCardsAdapter.ViewHolder> {

    private Activity activity;
    private Location location;
    private List<RecyclerItem> items;

    private int type;

    public static int TYPE_ALL=1;
    public static int TYPE_ACCEPTED=2;

    public RecyclerCardsAdapter(Activity activity, Location location) {
        this.activity = activity;
        this.location = location;
    }

    public RecyclerCardsAdapter(Activity activity, List<RecyclerItem> items, Location location, int type) {
        this.activity = activity;
        this.items = items;
        this.location = location;
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_needs, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String distance = getDistance(items.get(position).getOffer().getLatitude(), items.get(position).getOffer().getLongitude()) + "KM";
        items.get(position).distance = distance;
        holder.distance.setText(distance);
        holder.title.setText(items.get(position).getOffer().getTitle());
        holder.details.setText(items.get(position).getOffer().getDescription());


    }

    private String getDistance(String lat, String lon) {
        if(location!=null) {
            float lat1 = Float.valueOf(lat);
            float lng1 = Float.valueOf(lon);
            float lat2 = (float) location.getLatitude();
            float lng2 = (float) location.getLongitude();
            return String.format("%.2f", DistanceHelper.getDistance(lat1, lng1, lat2, lng2));
        }
        else
            return "0";
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView distance;
        TextView title;
        TextView details;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card);
            distance = (TextView) view.findViewById(R.id.text_distance);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.details);

            cardView.setOnClickListener(cardClickListener);
        }

        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "TYPE: " + type);
                ContactData contact=items.get(getAdapterPosition()).getOffer().getContact();
                if (contact==null || type!=TYPE_ACCEPTED) {
                    Log.d("test", "prvi ");
                    Intent intent = new Intent(activity, MapsActivity.class);

                    intent.putExtra(MapsActivity.KEY_LAT, items.get(getAdapterPosition()).getOffer().getLatitude());
                    intent.putExtra(MapsActivity.KEY_LON, items.get(getAdapterPosition()).getOffer().getLongitude());
                    intent.putExtra(MapsActivity.KEY_DISTANCE, items.get(getAdapterPosition()).distance);
                    intent.putExtra(MapsActivity.KEY_TITLE, items.get(getAdapterPosition()).getOffer().getTitle());
                    intent.putExtra(MapsActivity.KEY_DESCRIPTION, items.get(getAdapterPosition()).getOffer().getTitle());

                    if (Build.VERSION.SDK_INT > 21) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity,
                                        new Pair<View, String>(cardView, "item_card"),
                                        new Pair<View, String>(distance, "item_distance"),
                                        new Pair<View, String>(title, "item_title"),
                                        new Pair<View, String>(details, "item_details"));
                        activity.startActivity(intent, options.toBundle());

                    } else {
                        activity.startActivity(intent);
                    }
                }else if(contact!=null) {
                    Log.d("test", "drugi ");
                    Intent intent = new Intent(activity, ContactActivity.class);

                    intent.putExtra(ContactActivity.KEY_NAME, items.get(getAdapterPosition()).getOffer().getContact().getFirstName());
                    intent.putExtra(ContactActivity.KEY_LASTNAME, items.get(getAdapterPosition()).getOffer().getContact().getLastName());
                    intent.putExtra(ContactActivity.KEY_COUNTRY, items.get(getAdapterPosition()).getOffer().getContact().getCountry());
                    intent.putExtra(ContactActivity.KEY_birthDate, items.get(getAdapterPosition()).getOffer().getContact().getBirthDate());
                    intent.putExtra(ContactActivity.KEY_IMAGE, items.get(getAdapterPosition()).getOffer().getContact().getProfileImage());
                    intent.putExtra(ContactActivity.KEY_EMAIL, items.get(getAdapterPosition()).getOffer().getContact().getEmail());
                    activity.startActivity(intent);
                }
                    /*
                    if (Build.VERSION.SDK_INT > 21) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity,
                                        new Pair<View, String>(cardView, "item_card"),
                                        new Pair<View, String>(distance, "item_distance"),
                                        new Pair<View, String>(title, "item_title"),
                                        new Pair<View, String>(details, "item_details"));
                        activity.startActivity(intent, options.toBundle());

                    }*/

            }


        };

    }

}
