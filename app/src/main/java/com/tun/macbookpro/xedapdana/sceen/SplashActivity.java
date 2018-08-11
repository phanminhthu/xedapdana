package com.tun.macbookpro.xedapdana.sceen;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.tun.macbookpro.xedapdana.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView myImgLogo;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(500);
        mAnimation.setRepeatCount(android.view.animation.Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        myImgLogo = (ImageView) findViewById(R.id.mImgLogo);
        myImgLogo.startAnimation(mAnimation);

        Runnable mActivityStarter = new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(in);
                    finish();
                }
            };
        handler.postDelayed(mActivityStarter, 3000);
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
}

