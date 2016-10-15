package party.sicef.borderless.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import party.sicef.borderless.R;
import party.sicef.borderless.controller.OffersController;
import party.sicef.borderless.controller.RequestsController;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.activity.CardsActivity;
import party.sicef.borderless.ui.adapter.RecyclerCardsAdapter;

/**
 * Created by Andro on 11/14/2015.
 */
public abstract class BaseCardFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerCardsAdapter adapter;
    protected List<RecyclerItem> items;
    protected OffersController controllerOffer;
    protected RequestsController controllerRequest;
    protected TextView txtEmptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_needs, container, false);

        txtEmptyView = (TextView) view.findViewById(R.id.text_empty);

        items=new ArrayList<>();
        adapter = new RecyclerCardsAdapter(getActivity(),items, getLocation(),RecyclerCardsAdapter.TYPE_ALL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        getItems();

        return view;
    }

    protected abstract void getItems();

    protected Location getLocation() {
        return ((CardsActivity)getActivity()).getCurrentLocation();
    }

}
