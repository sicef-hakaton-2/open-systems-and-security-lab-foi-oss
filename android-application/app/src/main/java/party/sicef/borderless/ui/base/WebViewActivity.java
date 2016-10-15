package party.sicef.borderless.ui.base;

import android.os.Bundle;
import android.webkit.WebView;

import party.sicef.borderless.R;

/**
 * Created by Andro on 11/15/2015.
 */
public abstract class WebViewActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateActivityLayout(R.layout.activity_news_reader);
        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(getUrl());
    }

    protected abstract String getUrl();

}
