package party.sicef.borderless.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import party.sicef.borderless.api.data.OfferData;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.OffersController;
import party.sicef.borderless.controller.RequestsController;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.adapter.RecyclerCardsAdapter;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Created by Andro on 11/14/2015.
 */
public class CardAcceptedFragment extends BaseCardFragment implements BaseController.OnDataReadListener{

    @Override
    protected void getItems() {
        if(PreferencesManager.getRole(getActivity()).equals(AppConstants.ROLE_REFUGEE)) {
            if (controllerOffer == null) {
                controllerOffer = new OffersController(getActivity());
                controllerOffer.setOnDataReadListener(this);
            }
            controllerOffer.getMyOffers();
        }else{
            if (controllerRequest == null) {
                controllerRequest = new RequestsController(getActivity());
                controllerRequest.setOnDataReadListener(this);
            }
            controllerRequest.getMyRequests();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            getItems();
    }

    @Override
    public void onDataReceive(BaseResponse response) {

        /*
        items=new ArrayList<>();
        List<OfferModel> offers=new Select().from(OfferModel.class).where("status=?", 2).execute();
        for(OfferModel data:offers){
            RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400",data);
            items.add(item);
        }
        */
        OffersResponse offers=(OffersResponse) response;
        items=new ArrayList<>();
        if (offers.getData().length == 0) {
            txtEmptyView.setVisibility(View.VISIBLE);
        } else {
            txtEmptyView.setVisibility(View.GONE);
            for(OfferData data:offers.getData()){
                RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400",data);
                items.add(item);
            }
        }
        adapter=new RecyclerCardsAdapter(getActivity(),items,getLocation(),RecyclerCardsAdapter.TYPE_ACCEPTED);
        recyclerView.setAdapter(adapter);
    }

}
