package party.sicef.borderless.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import party.sicef.borderless.R;
import party.sicef.borderless.ui.activity.CardsActivity;
import party.sicef.borderless.ui.activity.IdentificationActivity;
import party.sicef.borderless.ui.activity.InfoActivity;
import party.sicef.borderless.ui.activity.LegalActivity;
import party.sicef.borderless.ui.activity.MainActivity;
import party.sicef.borderless.ui.activity.RefugeesActivity;
import party.sicef.borderless.ui.activity.SettingsActivity;
import party.sicef.borderless.ui.activity.TestActivity;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Abstract activity that contains DrawerLayout. Any activity that needs to have visible drawer needs
 * to extend this class.
 */
public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected ActionBarDrawerToggle toggle;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    private Toolbar toolbar;
    private volatile int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView tvUsername = (TextView) headerView.findViewById(R.id.username);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.email);

        tvUsername.setText(PreferencesManager.getUsername(this));
        tvEmail.setText(PreferencesManager.getEmail(this));

        inflateMenu();
    }

    private void inflateMenu(){
        String role=PreferencesManager.getRole(this);
        if(role.equals(AppConstants.ROLE_AUTHORITY)){
            navigationView.inflateMenu(R.menu.authority_main_drawer);
        }else if(role.equals(AppConstants.ROLE_CITIZEN)){
            navigationView.inflateMenu(R.menu.citizen_main_drawer);
        }else{
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * This method needs to be called from every activity that implements this class so it can add it's custom
     * view inside.
     * @param layout resource layout for activity
     */
    protected void inflateActivityLayout(int layout) {
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.activity_frame);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(layout, null, false);
        frameLayout.addView(activityView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // if item is already checked, don't restart activity
        if (!item.isChecked()) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            switch (id) {
                case R.id.nav_refugees: {
                    intent.setClass(DrawerActivity.this, RefugeesActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.nav_newsfeed: {
                    intent.setClass(DrawerActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.nav_donations:
                    intent.setClass(DrawerActivity.this, CardsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_tips:
                    intent.setClass(DrawerActivity.this, InfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_legal:
                    intent.setClass(DrawerActivity.this, LegalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_identification:
                    intent.setClass(DrawerActivity.this, IdentificationActivity.class);
                    startActivity(intent);
                    break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Set selected item for on drawer. Every activity in drawer must call this method
     * @param position
     */
    protected void setDrawerItemSelected(int position) {
        navigationView.getMenu().getItem(position).setChecked(true);
        selectedItem = position;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        } else if (id == R.id.logout) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected Toolbar getToolbar() {
        return this.toolbar;
    }

}