package com.pekokari.wall_clock;

import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextClock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    ImageView logo;
    ImageView bar;
    Point point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fullscreen);
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        animationLogo();

        bar = (ImageView) findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                animationBar();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
        TextClock clock = findViewById(R.id.clock);
        TextClock date = findViewById(R.id.date);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Helvetica.ttc");
        clock.setTypeface(typeface);
        date.setTypeface(typeface);
    }

    private void animationLogo() {
        logo = (ImageView) findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);
        AlphaAnimation alphaFadeOut = new AlphaAnimation( 1.0f, 0.6f );
        alphaFadeOut.setInterpolator( new AccelerateInterpolator() );
        alphaFadeOut.setDuration( 4000 );
        alphaFadeOut.setFillAfter( true );
        alphaFadeOut.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation alphaFadeIn = new AlphaAnimation( 0.6f, 1.0f );
                alphaFadeIn.setInterpolator( new DecelerateInterpolator() );
                alphaFadeIn.setDuration( 4000 );
                alphaFadeIn.setFillAfter( true );
                alphaFadeIn.setAnimationListener( new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animationLogo();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                } );
                logo.startAnimation( alphaFadeIn );
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        } );
        logo.startAnimation( alphaFadeOut );
    }

    private void animationBar() {
        final DateFormat df = new SimpleDateFormat("s");
        final Date date = new Date(System.currentTimeMillis());
        int baseWidth = point.x / 60 * Integer.parseInt(df.format(date));
        bar.setLayoutParams(new FrameLayout.LayoutParams(68 + baseWidth ,5));
    }
}
