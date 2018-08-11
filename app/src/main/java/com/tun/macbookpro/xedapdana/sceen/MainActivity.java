package com.tun.macbookpro.xedapdana.sceen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tun.macbookpro.xedapdana.R;
import com.tuyenmonkey.mkloader.MKLoader;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private MKLoader mLoader;
    private ImageView mImgBack,mImgNext,mImgNextGone,mImgShare,mImgBackGone;
    private LinearLayout mBack, mHome, mNext;
    private String myUrl = "http://xedapdana.com";
    private int mCheck;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.activity_main);
        serUpView();
        setUpWebView();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheck != 1 || mCheck == 4) {
                    mLoader.setVisibility(View.VISIBLE);
                }
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                }
            }
        });

        mImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "http://www.xedapdana.com";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DaNaZone");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheck != 1 || mCheck == 3) {
                    mLoader.setVisibility(View.VISIBLE);

                }
                if (myWebView.canGoForward()) {
                    myWebView.goForward();
                }
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWebView.loadUrl(myUrl);
                mLoader.setVisibility(View.VISIBLE);
            }
        });
    }

    private void serUpView() {
        mLoader = (MKLoader) findViewById(R.id.loader);
        mBack = (LinearLayout) findViewById(R.id.mBack);
        ImageView mImgHome = (ImageView) findViewById(R.id.mImgHome);
        mImgShare = (ImageView) findViewById(R.id.share);
        mImgNextGone = (ImageView) findViewById(R.id.mImgNextGone);
        mNext = (LinearLayout) findViewById(R.id.mNext);
        mHome = (LinearLayout) findViewById(R.id.mHome);
        mImgBackGone = (ImageView) findViewById(R.id.mImgBackGone);
        mImgNext = (ImageView) findViewById(R.id.mImgNext);
        myWebView = (WebView) findViewById(R.id.mWebView);
        mImgBack = (ImageView) findViewById(R.id.mImgBack);
    }

    private void setUpWebView() {
        myWebView.setWebViewClient(new MyBrWebView());
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.loadUrl(myUrl);
    }

    private class MyBrWebView extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            mLoader.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoader.setVisibility(View.GONE);
            if (!myWebView.canGoBack() && !myWebView.canGoForward()) {
                mImgBack.setVisibility(View.GONE);
                mImgNext.setVisibility(View.GONE);
                mImgBackGone.setVisibility(View.VISIBLE);
                mImgNextGone.setVisibility(View.VISIBLE);
                mCheck = 1;
            } else if (myWebView.canGoBack() && !myWebView.canGoForward()) {
                mImgBack.setVisibility(View.VISIBLE);
                mImgNext.setVisibility(View.GONE);
                mImgBackGone.setVisibility(View.GONE);
                mImgNextGone.setVisibility(View.VISIBLE);
                mCheck = 3;
            } else if (!myWebView.canGoBack() && myWebView.canGoForward()) {
                mImgBack.setVisibility(View.GONE);
                mImgNext.setVisibility(View.VISIBLE);
                mImgBackGone.setVisibility(View.VISIBLE);
                mImgNextGone.setVisibility(View.GONE);
                mCheck = 4;
            } else {
                mImgBack.setVisibility(View.VISIBLE);
                mImgNext.setVisibility(View.VISIBLE);
                mImgBackGone.setVisibility(View.GONE);
                mImgNextGone.setVisibility(View.GONE);
                mCheck = 2;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI(hasFocus);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void hideSystemUI(boolean hasFocus) {
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getColor(R.color.colorMain));
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
