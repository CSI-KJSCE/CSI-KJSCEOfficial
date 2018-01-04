package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup rootLayout;
    FloatingActionButton profileImage;
    TextInputEditText nameText, sexText, svvText,emailText, phoneText;
    Button saveProfileBtn;
    SharedPreferences sf;
    Toolbar toolbar;
    private boolean editMode;
    RoundedBitmapDrawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //prevent keybaord from popping up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        editMode = getIntent().getBooleanExtra("edit_mode",false);

        rootLayout = findViewById(R.id.profile_root_layout);
        toolbar = findViewById(R.id.profile_toolbar);
        profileImage = findViewById(R.id.profile_pic_fab);
        nameText = findViewById(R.id.full_name_text);
        sexText = findViewById(R.id.sex_text);
        svvText = findViewById(R.id.svv_email_text);
        emailText = findViewById(R.id.personal_email_text);
        phoneText = findViewById(R.id.phone_number_text);
        saveProfileBtn = findViewById(R.id.profile_save_btn);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.profile_collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.fui_transparent));
        setSupportActionBar(toolbar);

        final Drawable uparrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(uparrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sf = getSharedPreferences(getString(R.string.USER_INFO),MODE_PRIVATE);
        String fullname = sf.getString("name","CSI Fan");
        String sex = sf.getString("sex","NA");
        String svvMail = sf.getString("svv_mail","NA");
        String email = sf.getString("email","NA");
        String phone = sf.getString("phone","NA");
        String picUrl = sf.getString("pic_url","default");
        boolean signedWithSvv = sf.getBoolean("signed_in_with_svv",false);

        if(!picUrl.contains("http")){
            if(picUrl.equalsIgnoreCase("female"))
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_female_avatar));
        }
        else {

            Glide
                    .with(this)
                    .asBitmap()
                    .load(picUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            drawable.setCircular(true);
                            profileImage.setImageDrawable(drawable);
                        }
                    });
        }
        nameText.setText(fullname);
        sexText.setText(sex);
        svvText.setText(svvMail);
        emailText.setText(email);
        phoneText.setText(phone);
       // saveProfileBtn.setVisibility(View.GONE);
        saveProfileBtn.setBackground(getResources().getDrawable(R.drawable.button_light_gray));
        if(!Utils.isProfileComplete(this)){
            Snackbar.make(rootLayout,"Complete your profile to continue",Snackbar.LENGTH_SHORT)
                    .show();
            //saveProfileBtn.setVisibility(View.VISIBLE);
            saveProfileBtn.setBackground(getResources().getDrawable(R.drawable.button_primary_gradient));
            saveProfileBtn.setOnClickListener(this);
        }

        Utils.disableEditText(nameText);
        if(signedWithSvv)
            Utils.disableEditText(svvText);
        else
            Utils.disableEditText(emailText);
        // disable editTexts
        if(editMode==false){
            Utils.disableEditText(emailText);
            Utils.disableEditText(svvText);
            Utils.disableEditText(sexText);
            Utils.disableEditText(phoneText);
        }


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.profile_save_btn:
                StringBuilder missing = new StringBuilder();
                SharedPreferences.Editor editor = sf.edit();
                if(nameText.getText().toString().length() <3) {
                    missing.append(" name |");
                }
                editor.putString("name",nameText.getText().toString());
                if(sexText.getText().toString().length()<3) {
                    missing.append(" sex |");
                }
                editor.putString("sex",sexText.getText().toString());
                if(!svvText.getText().toString().contains("@somaiya.edu")) {
                    missing.append(" svv mail |");
                }
                editor.putString("svv_mail", svvText.getText().toString());
                if(emailText.getText().toString().isEmpty() || !emailText.getText().toString().contains("@"))
                    missing.append(" personal email |");
                editor.putString("email",emailText.getText().toString());
                if(phoneText.getText().toString().isEmpty() ||  phoneText.getText().toString().length()<10)
                    missing.append(" phone |");
                editor.putString("phone",phoneText.getText().toString());

                if(missing.length()>0) {
                    Snackbar.make(rootLayout, "Please provide your" + missing, Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    editor.commit();
                    Toast.makeText(this,"Profile saved!",Toast.LENGTH_SHORT)
                            .show();
                    editMode = true;
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.profile_edit_menu){
            editMode = true;
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
            if(sf.getBoolean("signed_in_with_svv",true)){
               if(!emailText.getText().toString().contains("@"))
                    emailText.setText("");
                Utils.enableEditText(emailText);
            } else {
                if(!svvText.getText().toString().contains("@"))
                    svvText.setText("");
                Utils.enableEditText(svvText);
            }
            if(sexText.getText().toString().equals("NA"))
                sexText.setText("");
            Utils.enableEditText(sexText);
            if(phoneText.getText().toString().equals("NA"))
                phoneText.setText("");
            Utils.enableEditText(phoneText);
           // saveProfileBtn.setVisibility(View.VISIBLE);
            saveProfileBtn.setBackground(getResources().getDrawable(R.drawable.button_primary_gradient));
            saveProfileBtn.setOnClickListener(this);
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
            sexText.requestFocus();
            // show keyboard
            InputMethodManager inputManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        }

        return super.onOptionsItemSelected(item);
    }
}
