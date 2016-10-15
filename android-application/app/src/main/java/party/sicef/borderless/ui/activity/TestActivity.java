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
import party.sicef.borderless.ui.adapter.RecyclerAdapterRefugees;
import party.sicef.borderless.ui.base.DrawerActivity;

/**
 * Created by Andro on 11/14/2015.
 */
public class TestActivity extends DrawerActivity implements BaseController.OnDataReadListener{

    private List<RefugeeProfile> items;
    private RefugeesController controller;
    private RecyclerAdapterRefugees adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refugees);
        setDrawerItemSelected(0);

        items=new ArrayList<>();
        controller= new RefugeesController(this);
        controller.setOnDataReadListener(this);
        controller.getRefugees();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapterRefugees(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onDataReceive(BaseResponse response) {
        RefugeesResponse refugees=(RefugeesResponse) response;
        for(RefugeeProfile profile:refugees.getData())
            items.add(profile);

    }

}
