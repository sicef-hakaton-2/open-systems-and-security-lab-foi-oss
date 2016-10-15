package party.sicef.borderless.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;
import party.sicef.borderless.R;
import party.sicef.borderless.api.data.NewsData;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.NewsReponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.NewsController;
import party.sicef.borderless.model.RecyclerItem;
import party.sicef.borderless.ui.adapter.RecyclerAdapter;
import party.sicef.borderless.ui.base.DrawerActivity;

/**
 * Created by Andro on 11/14/2015.
 */
public class MainActivity extends DrawerActivity implements BaseController.OnDataReadListener{

    private NewsController controller;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActivityLayout(R.layout.activity_main);
        setDrawerItemSelected(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabClickListener);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        controller=new NewsController(this);
        controller.setOnDataReadListener(this);
        controller.getNews();
    }

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };

    @Override
    public void onDataReceive(BaseResponse response) {
        NewsReponse news=(NewsReponse) response;
        NewsData[] data = news.getData();
        recyclerView.setAdapter(new SlideInBottomAnimationAdapter(new RecyclerAdapter(this, data)));
    }
}
