package org.csikjsce.csi_kjsceofficial;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Feedback_webview extends Activity {

    WebView feedbackweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_webview);

        feedbackweb=(WebView)findViewById(R.id.feedback_webview);
        feedbackweb.loadUrl("https://www.youtube.com/watch?v=Sv6dMFF_yts");
        feedbackweb.getSettings().setJavaScriptEnabled(true);
        feedbackweb.setWebViewClient(new WebViewClient());
    }

}
