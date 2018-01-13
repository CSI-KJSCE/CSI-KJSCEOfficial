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

import org.csikjsce.csi_kjsceofficial.POJO.Notification;


public class SplashscreenActivity extends Activity {
    public final String TAG = SplashscreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        try {
            if (getIntent().getExtras().getString(getString(R.string.notif_key_id)) != null) {
                handleNotif();
            }
        } catch (NullPointerException npe) {
           Log.d(TAG,npe.getLocalizedMessage());
        }

        final ImageView imageView = findViewById(R.id.splash_imageview);
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
    public void handleNotif(){

        int id = 0;
        try {
            id = Integer.parseInt(getIntent().getExtras().getString(getString(R.string.notif_key_id)));
        } catch (NumberFormatException npe) {
            Log.d(TAG, npe.getLocalizedMessage());
            return;
        }
        String title = getIntent().getExtras().getString(getString(R.string.notif_key_title));
        String desc = getIntent().getExtras().getString(getString(R.string.notif_key_desc));
        String time = getIntent().getExtras().getString(getString(R.string.notif_key_time));
        String extraUrl = getIntent().getExtras().getString(getString(R.string.notif_key_extra_url));

        int type;
        try {
            type = Integer.parseInt(getIntent().getExtras().getString(getString(R.string.notif_key_type)));
        } catch(NumberFormatException nfe) {
            type = 0;
            Log.d(TAG,nfe.getLocalizedMessage());
        }
        int eventid = 0;
        try {
            eventid = Integer.parseInt(getIntent().getExtras().getString(getString(R.string.notif_key_event_id)));
        } catch (NumberFormatException nfe) {
            eventid = -1;
            Log.d(TAG,nfe.getLocalizedMessage());
        }

        Notification notification = new Notification(
                id,
                time,
                title,
                desc,
                extraUrl,
                type,
                eventid,
                Notification.NOT_READ
        );
        DatabaseHelper db = new DatabaseHelper(this);
        db.insertNotification(notification);

        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}





