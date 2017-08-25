package org.csikjsce.csi_kjsceofficial;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class Register_webview extends AppCompatActivity {

    private WebView registerweb;
    private FrameLayout framelayout;
    private ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_webview);
        framelayout=(FrameLayout)findViewById(R.id.framelayout_register);
        bar=(ProgressBar)findViewById(R.id.progressBar2);
        registerweb=(WebView)findViewById(R.id.register_webview);
        bar.setMax(100);


        registerweb.setWebViewClient(new HelpClient());
        registerweb.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView view,int progress){
                framelayout.setVisibility(View.VISIBLE);
                bar.setProgress(progress);
                setTitle("Loading...");
                if(progress==100)
                {

                    framelayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view,progress);

            }
        });
        registerweb.getSettings().setJavaScriptEnabled(true);
        registerweb.setVerticalScrollBarEnabled(false);
        registerweb.loadUrl("https://www.youtube.com/watch?v=Sv6dMFF_yts");
        bar.setProgress(0);

    }

    private class HelpClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl("https://www.youtube.com/watch?v=Sv6dMFF_yts");

            framelayout.setVisibility(View.VISIBLE);

            return true;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(registerweb.canGoBack()){

                registerweb.goBack();
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }
}
