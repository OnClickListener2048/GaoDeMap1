package anint.watson.com.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findView();
        init();
        webView.loadUrl("file:///android_asset/GaoDeMap.html");
    }

    private void init() {
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setUseWideViewPort(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setDisplayZoomControls(false);
        webViewSettings.setDomStorageEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        webViewSettings.setBuiltInZoomControls(true);
        webViewSettings.supportZoom();

    }

    private void findView() {
        webView = (WebView) findViewById(R.id.webview);
    }
}
