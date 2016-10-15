package party.sicef.borderless.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import party.sicef.borderless.R;
import party.sicef.borderless.api.data.OfferData;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.OffersController;
import party.sicef.borderless.controller.RequestsController;
import party.sicef.borderless.model.OfferModel;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.adapter.RecyclerCardsAdapter;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by Andro on 11/14/2015.
 */
public class CardNeedsFragment extends BaseCardFragment implements BaseController.OnDataReadListener {

    TextView tvEmpty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmpty = (TextView) view.findViewById(R.id.text_empty);

        // enable swipe to remove
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                // TODO move item to another list depending on direction
                final RecyclerItem item = items.get(position);
                if (direction == ItemTouchHelper.LEFT) {
                    //viewHolder.itemView.setBackgroundColor(getActivity().getResources().getColor(R.color.red_Swipe));
                    if(item!=null) {

                        OfferData model = item.getOffer();
                        OfferModel fromDB=new Select().from(OfferModel.class).where("globalId=?",model.getId()).executeSingle();
                        if(fromDB!=null) {
                            fromDB.setStatus(-1);
                            fromDB.save();
                        }
                    }

                } else if (direction == ItemTouchHelper.RIGHT) {
                    if(item!=null) {
                        OfferData model = item.getOffer();
                        if(model!=null) {
                            if(PreferencesManager.getRole(getActivity()).equals(AppConstants.ROLE_REFUGEE))
                                 controllerOffer.sendOffer(model.getId());
                            else
                                controllerRequest.sendRequest(model.getId());
                            OfferModel fromDB=new Select().from(OfferModel.class).where("globalId=?",model.getId()).executeSingle();
                            if(fromDB!=null) {
                                fromDB.setStatus(1);
                                fromDB.save();
                            }
                        }
                    }
                }

                // remove question from list and notify adapter
                items.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };

        // attach ItemTouchHelper to Recycler
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(PreferencesManager.getRole(getActivity()).equals(AppConstants.ROLE_REFUGEE)) {
            controllerOffer = new OffersController(getActivity());
            controllerOffer.setOnDataReadListener(this);
        }else{
            controllerRequest = new RequestsController(getActivity());
            controllerRequest.setOnDataReadListener(this);

        }

    }

    @Override
    protected void getItems() {
        if(PreferencesManager.getRole(getActivity()).equals(AppConstants.ROLE_REFUGEE)) {
            if (controllerOffer == null) {
                controllerOffer = new OffersController(getActivity());
                controllerOffer.setOnDataReadListener(this);
            }
            controllerOffer.getOffers();
        }else{
            if (controllerRequest == null) {
                controllerRequest = new RequestsController(getActivity());
                controllerRequest.setOnDataReadListener(this);
            }
            controllerRequest.getRequests();
        }
    }

    @Override
    public void onDataReceive(BaseResponse response) {
        OffersResponse offers=(OffersResponse) response;
        items=new ArrayList<>();
        for(OfferData data:offers.getData()){
            RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400",data);
            items.add(item);
        }

        /*
        items=new ArrayList<>();
        List<OfferModel> offers=new Select().from(OfferModel.class).where("status=?", 0).execute();
        Location location = getLocation();
        for(OfferModel data:offers){
            if (data.getLatitude() != null && data.getLongitude() != null && location != null) {
                if (DistanceHelper.isClose(getActivity(),
                        Float.valueOf(data.getLatitude()),
                        Float.valueOf(data.getLongitude()),
                        (float) location.getLatitude(),
                        (float) location.getLongitude())) {
                    RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400",data);
                    items.add(item);
                }
            }
        }
        adapter=new RecyclerCardsAdapter(getActivity(),items, getLocation());
        */
        adapter=new RecyclerCardsAdapter(getActivity(),items,getLocation(),RecyclerCardsAdapter.TYPE_ALL);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tutorial, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_tutorial){
            new MaterialShowcaseView.Builder(getActivity())
                    .setTarget(tvEmpty)
                    .setDismissText(R.string.tut_01_accept)
                    .setContentText(R.string.tut_01)
                    .setDelay(500)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
