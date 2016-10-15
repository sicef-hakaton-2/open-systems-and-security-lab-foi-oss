package party.sicef.borderless.ui.activity;

import party.sicef.borderless.R;
import party.sicef.borderless.ui.base.SplashAbstract;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class SplashActivity extends SplashAbstract {

    @Override
    public int provideLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public int getSplashTime() {
        return 1500;
    }

    @Override
    public Class getNextClassActivity() {

        return LogInActivity.class;
    }
}