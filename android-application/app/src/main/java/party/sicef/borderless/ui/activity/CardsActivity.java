package party.sicef.borderless.ui.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import party.sicef.borderless.R;
import party.sicef.borderless.ui.base.DrawerActivity;
import party.sicef.borderless.ui.fragment.CardAcceptedFragment;
import party.sicef.borderless.ui.fragment.CardNeedsFragment;
import party.sicef.borderless.ui.fragment.CardRequestedFragment;
import party.sicef.borderless.ui.util.NonSwipeableViewPager;

/**
 * Created by Andro on 11/14/2015.
 */
public class CardsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActivityLayout(R.layout.activity_cards);

        // remove shadow from toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.appbar_layout).setElevation(0);
        }

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        NonSwipeableViewPager pager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        pager.setSwipe(false);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);


    }

    public Location getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // create criteria for location to get best location provider
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);
        // get best provider for given criteria and use it to get current location
        String provider = lm.getBestProvider(criteria, true);
        return lm.getLastKnownLocation(provider);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new CardNeedsFragment ();
                case 1:
                    return new CardAcceptedFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fragment_needs_title);

                case 1:
                    return getString(R.string.fragment_accepted_title);

                default:
                    // error
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
