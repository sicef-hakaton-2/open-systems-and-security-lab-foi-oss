package party.sicef.borderless.ui.activity;

import party.sicef.borderless.ui.base.WebViewActivity;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Created by Andro on 11/15/2015.
 */
public class NewsReaderActivity extends WebViewActivity {

    @Override
    protected String getUrl() {
        return PreferencesManager.getGcmUrl(this);
    }

}
