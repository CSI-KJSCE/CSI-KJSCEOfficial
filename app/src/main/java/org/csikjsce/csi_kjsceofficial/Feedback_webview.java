package org.csikjsce.csi_kjsceofficial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class Feedback_webview extends AppCompatActivity {
    private static final String urladd ="https://www.google.co.in/";
    private WebView webView;
    private FrameLayout framelayout;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_webview);
        framelayout =(FrameLayout)findViewById(R.id.framelayout_feedback);
        progressBar=(ProgressBar)findViewById(R.id.progressBarfeedback);

        progressBar.setMax(100);
        webView=(WebView)findViewById(R.id.feedback_webview);

        webView.setWebViewClient(new HelpClient());
        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView view,int progress){
                framelayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                setTitle("Loading...");
                if(progress==100)
                {

                    framelayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view,progress);

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(urladd);
        progressBar.setProgress(0);

    }

    private class HelpClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            framelayout.setVisibility(View.VISIBLE);

            return true;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){

                webView.goBack();
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }
}
