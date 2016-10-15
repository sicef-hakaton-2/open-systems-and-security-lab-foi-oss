package party.sicef.borderless.ui.fragment;

import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import party.sicef.borderless.api.data.OfferData;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.OffersController;
import party.sicef.borderless.model.OfferModel;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.activity.MainActivity;
import party.sicef.borderless.ui.adapter.RecyclerAdapter;
import party.sicef.borderless.ui.adapter.RecyclerCardsAdapter;
import party.sicef.borderless.util.DBHelper;

/**
 * Created by Andro on 11/14/2015.
 */
public class CardRequestedFragment extends BaseCardFragment implements BaseController.OnDataReadListener {


    @Override
    protected void getItems() {
        items=new ArrayList<>();
        List<OfferModel> offers=new Select().from(OfferModel.class).where("status=?", 1).execute();
        for(OfferModel data:offers){
            RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400", DBHelper.convertOffer(data));
            items.add(item);
        }
        adapter=new RecyclerCardsAdapter(getActivity(),items, getLocation(),RecyclerCardsAdapter.TYPE_ALL);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            getItems();
    }


    @Override
    public void onDataReceive(BaseResponse response) {
        Log.d("test", "onDataReceive");
        items=new ArrayList<>();
        List<OfferModel> offers=new Select().from(OfferModel.class).where("status=?", 1).execute();
        if (offers.size() == 0) {
            txtEmptyView.setVisibility(View.VISIBLE);
        } else {
            txtEmptyView.setVisibility(View.GONE);
        }
        for(OfferModel data:offers){
            RecyclerItem item=new RecyclerItem("","https://placeholdit.imgix.net/~text?txtsize=68&txt=720%C3%97400&w=720&h=400", DBHelper.convertOffer(data));
            items.add(item);
        }
        adapter=new RecyclerCardsAdapter(getActivity(),items, getLocation(),RecyclerCardsAdapter.TYPE_ALL);
        recyclerView.setAdapter(adapter);
    }


}