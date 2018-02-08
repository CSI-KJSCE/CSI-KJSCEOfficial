package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup rootLayout;
    FloatingActionButton profileImage;
    TextInputEditText nameText, svvText, emailText, phoneText;
    Button saveProfileBtn;
    RadioGroup genderSelector;
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

        editMode = getIntent().getBooleanExtra(getString(R.string.pref_key_edit_mode),false);

        rootLayout = findViewById(R.id.profile_root_layout);
        toolbar = findViewById(R.id.toolbar);
        profileImage = findViewById(R.id.profile_pic_fab);
        nameText = findViewById(R.id.full_name_text);
        genderSelector = findViewById(R.id.person_gender_radio_grp);
        svvText = findViewById(R.id.svv_email_text);
        emailText = findViewById(R.id.personal_email_text);
        phoneText = findViewById(R.id.phone_number_text);
        saveProfileBtn = findViewById(R.id.profile_save_btn);

        Glide.with(this)
                .load(getResources().getDrawable(R.drawable.bg_csi_poster))
                .into((ImageView)findViewById(R.id.coverImageView));

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.profile_collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.fui_transparent));
        collapsingToolbar.setCollapsedTitleTypeface(ResourcesCompat
                .getFont(this, R.font.titilium_regular));
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
        String fullname = sf.getString(getString(R.string.pref_key_name),"CSI Fan");
        String sex = sf.getString(getString(R.string.pref_key_sex),"");
        String svvMail = sf.getString(getString(R.string.pref_key_svv_mail),"");
        String email = sf.getString(getString(R.string.pref_key_email),"");
        String phone = sf.getString(getString(R.string.pref_key_phone),"");
        String picUrl = sf.getString(getString(R.string.pref_key_pic_url),"default");
        boolean signedWithSvv = sf.getBoolean(getString(R.string.pref_key_signed_in_with_svv),false);

        if(!URLUtil.isValidUrl(picUrl)){
            if(picUrl.equalsIgnoreCase(getString(R.string.female)))
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_female_avatar));
        }
        else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_default_male_avatar);
            requestOptions.error(R.drawable.ic_default_male_avatar);
            Glide
                    .with(this)
                    .setDefaultRequestOptions(requestOptions)
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
        if(sex.equalsIgnoreCase(getString(R.string.female)))
            ((AppCompatRadioButton)findViewById(R.id.sex_female_radio_btn)).setChecked(true);
        else ((AppCompatRadioButton)findViewById(R.id.sex_male_radio_btn)).setChecked(true);
        svvText.setText(svvMail);
        emailText.setText(email);
        phoneText.setText(phone);
       // saveProfileBtn.setVisibility(View.GONE);
        saveProfileBtn.setBackground(getResources().getDrawable(R.drawable.button_light_gray));
        if(!Utils.isProfileComplete(this)){
            Snackbar.make(rootLayout,getString(R.string.kindly_complete_your_profile),Snackbar.LENGTH_SHORT)
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
                if(genderSelector.getCheckedRadioButtonId() == R.id.sex_female_radio_btn) {
                    editor.putString("sex",getString(R.string.female));
                }
                else {
                    editor.putString("sex", getString(R.string.male));
                }
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
            if(sf.getBoolean(getString(R.string.pref_key_signed_in_with_svv),true)){
               if(!emailText.getText().toString().contains("@"))
                    emailText.setText("");
                Utils.enableEditText(emailText);
            } else {
                if(!svvText.getText().toString().contains("@"))
                    svvText.setText("");
                Utils.enableEditText(svvText);
            }

            if(phoneText.getText().toString().equals("NA"))
                phoneText.setText("");
            Utils.enableEditText(phoneText);
           // saveProfileBtn.setVisibility(View.VISIBLE);
            saveProfileBtn.setBackground(getResources().getDrawable(R.drawable.button_primary_gradient));
            saveProfileBtn.setOnClickListener(this);
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);

            // show keyboard
            InputMethodManager inputManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        }

        return super.onOptionsItemSelected(item);
    }
}
