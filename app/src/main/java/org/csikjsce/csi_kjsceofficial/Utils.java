package org.csikjsce.csi_kjsceofficial;

import android.content.Context;
import org.json.JSONObject;

/**
 * Created by sziraqui on 16/7/17.
 */

public class Utils {
    private static Context context;
    String response =new String();
    public Utils(Context context){
        context = this.context;
    }
    public  JSONObject fetchData(String tablename) throws org.json.JSONException{


        switch(tablename){
            case "event_list":
                response = context.getString(R.string.demo_event_list);
                break;
            case "event_details":
                response = context.getString(R.string.demo_event_details);
                break;
            case "feedback":
                response = context.getString(R.string.demo_feedback);
                break;
            case "councils":
                response = context.getString(R.string.demo_councils);
                break;
            case "council_members":
                response = context.getString(R.string.demo_council_members);
                break;
        }
        return new JSONObject(response);
    }
}
