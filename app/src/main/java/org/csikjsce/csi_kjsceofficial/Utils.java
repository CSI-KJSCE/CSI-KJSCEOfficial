package org.csikjsce.csi_kjsceofficial;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by sziraqui on 17/12/17.
 */

public class Utils {
    public static void openLinkInCustomTab(Context context, String url){
        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder chromeWebViewBuilder = new CustomTabsIntent.Builder();
        chromeWebViewBuilder.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
        chromeWebViewBuilder.setSecondaryToolbarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        chromeWebViewBuilder.setShowTitle(true);

        chromeWebViewBuilder.build().launchUrl(context, uri);
    }
    public static void onShareClick(Context context, String message, String appName) {

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        // message might be in html form, so convert back to plain text
        message = Html.fromHtml(message).toString();
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        if(!appName.isEmpty())
            sendIntent.setPackage(appName);
       try {
           context.startActivity(sendIntent);
       } catch(ActivityNotFoundException e){
           Log.e("Social Share", "package does not exist");
           Toast.makeText(context, "Action not supported",Toast.LENGTH_SHORT).show();
       }

    }
    public static boolean isProfileComplete(Context context){
        SharedPreferences sf = context.getSharedPreferences(context.getString(R.string.USER_INFO),MODE_PRIVATE);

        String fullname = sf.getString(context.getString(R.string.pref_key_name),"NA");
        String sex = sf.getString(context.getString(R.string.pref_key_sex),"NA");
        String svvMail = sf.getString(context.getString(R.string.pref_key_svv_mail),"NA");
        String email = sf.getString(context.getString(R.string.pref_key_email),"NA");
        String phone = sf.getString(context.getString(R.string.pref_key_phone),"NA");

        if(fullname.contentEquals("NA") ||
                sex.equals("NA") ||
                svvMail.equals("NA") ||
                email.equals("NA") ||
                phone.equals("NA"))
            return false;
        else return true;
    }
    public static void disableEditText(EditText editText){
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setFocusableInTouchMode(false);
    }
    public static void enableEditText(EditText editText){
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setFocusableInTouchMode(true);
    }
    public static String strToDate(String eventdt){
        Date date = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            date = sm.parse(eventdt);
        } catch (ParseException e) {
            e.printStackTrace();
            return eventdt;
        }
        SimpleDateFormat newFormat = new  SimpleDateFormat("EEE, d MMM ''yy",Locale.ENGLISH);
        return newFormat.format(date).toString();
    }
}
