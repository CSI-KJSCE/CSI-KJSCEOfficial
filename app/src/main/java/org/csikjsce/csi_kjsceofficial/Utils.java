package org.csikjsce.csi_kjsceofficial;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by sziraqui on 17/12/17.
 */

public class Utils {
    public static void openLinkInCustomTab(Context context, String url){
        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder chromeWebViewBuilder = new CustomTabsIntent.Builder();
        chromeWebViewBuilder.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
        chromeWebViewBuilder.setSecondaryToolbarColor(context.getResources().getColor(R.color.colorPrimaryDark));

        chromeWebViewBuilder.build().launchUrl(context, uri);
    }
    public static void onShareClick(Context context, String message, String appName) {

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);

       sendIntent.setPackage(appName);
       try {
           context.startActivity(sendIntent);
       } catch(ActivityNotFoundException e){
           Log.e("Social Share", "package does not exist");
           Toast.makeText(context, "Action not supported",Toast.LENGTH_SHORT);
       }

    }
}
