package org.csikjsce.csi_kjsceofficial;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class CSIInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = CSIInstanceIdService.class.getSimpleName();
    public CSIInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId
                .getInstance()
                .getToken();
        Log.d(TAG,"User token: "+token);
        super.onTokenRefresh();
    }
}
