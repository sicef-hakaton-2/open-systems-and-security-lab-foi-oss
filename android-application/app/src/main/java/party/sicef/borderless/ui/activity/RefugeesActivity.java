package party.sicef.borderless.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import party.sicef.borderless.R;
import party.sicef.borderless.api.data.RefugeeProfile;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.RefugeesResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.RefugeesController;
import party.sicef.borderless.ui.adapter.RecyclerAdapter;
import party.sicef.borderless.ui.adapter.RecyclerAdapterRefugees;
import party.sicef.borderless.ui.base.DrawerActivity;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class RefugeesActivity extends DrawerActivity implements BaseController.OnDataReadListener {

    private List<RefugeeProfile> items;
    private RefugeesController controller;
    private RecyclerAdapterRefugees adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActivityLayout(R.layout.activity_refugees);
        setDrawerItemSelected(0);

        items=new ArrayList<>();
        controller= new RefugeesController(this);
        controller.setOnDataReadListener(this);
        controller.getRefugees();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDataReceive(BaseResponse response) {
        RefugeesResponse refugees=(RefugeesResponse) response;
        for(RefugeeProfile profile:refugees.getData())
             items.add(profile);
        adapter=new RecyclerAdapterRefugees(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
}
