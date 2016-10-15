package party.sicef.borderless.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ahuskano on 14.11.2015..
 */
public abstract class SplashAbstract extends AppCompatActivity {

    private final String TAG = SplashAbstract.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutRes());
        main();
    }

    public void main() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), getNextClassActivity());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        }, getSplashTime());
    }

    public abstract int provideLayoutRes();

    public abstract int getSplashTime();

    public abstract Class getNextClassActivity();
}
