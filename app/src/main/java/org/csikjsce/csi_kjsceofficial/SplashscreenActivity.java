package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashscreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final ImageView imageView = (ImageView) findViewById(R.id.splash_imageview);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fui_slide_in_right);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fui_slide_out_left);

        imageView.startAnimation(animation_2);


        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation_1);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_1);
                Log.e("splash","Here");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}





