package org.csikjsce.csi_kjsceofficial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.IOException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGNIN = 007;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignin;
    private LinearLayout parentLayout;
    Snackbar mysnackbar;
    private ProgressDialog mProgressDialog;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        try {
            saveLogcatToFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parentLayout = (LinearLayout)findViewById(R.id.login_parent_layout);
        btnSignin = (SignInButton)findViewById(R.id.btn_sign_in);
        btnSignin.setOnClickListener(this);

        checkConnection();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        String message = "";
        int color= Color.RED;

        if (isConnected) {
            //do nothing
        } else {
            message = getString(R.string.msg_no_internet);

            Snackbar snackbar = Snackbar
                    .make(parentLayout, message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
       super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGNIN);
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }
    private void revokeAccess(){
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }
    private void handleSignInResult(GoogleSignInResult result){

        Log.d(TAG,"handleSignInResult():"+result.isSuccess());
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String pname = account.getDisplayName();
            String emailid = account.getEmail();
            Uri pic_uri = account.getPhotoUrl();
            String pic_url;
            if(pic_uri==null)
                pic_url = "file:///android_asset/raw/alien_face.png";
            else pic_url = pic_uri.toString();
            SharedPreferences userInfo = context.getSharedPreferences(getString(R.string.USER_INFO),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();
            editor.putString("name",pname);
            editor.putString("email",emailid);
            editor.putString("pic_url",pic_url);
            editor.commit();
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_sign_in:
                signIn();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGNIN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if(pendingResult.isDone()){
            Log.d(TAG,"onStart(): Cached sign in");
            GoogleSignInResult result = pendingResult.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"onConnectionFailed: "+connectionResult);
    }
    private void updateUI(boolean isSignedIn){
        if(isSignedIn){
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else{
            Snackbar.make(parentLayout,"Not signed in",Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Authenticating...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
    public static void saveLogcatToFile(Context context) throws IOException {
        String fileName = "logcat_"+System.currentTimeMillis()+".txt";
        File outputFile = new File(context.getExternalCacheDir(),fileName);
        @SuppressWarnings("unused")
        Process process = Runtime.getRuntime().exec("logcat -df "+outputFile.getAbsolutePath());
    }
}
