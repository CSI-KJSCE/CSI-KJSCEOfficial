package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout rootLayout;
    CircleImageView profileImage;
    TextInputEditText nameText, sexText, svvText,emailText, phoneText;
    Button saveProfileBtn;
    SharedPreferences sf;
    Toolbar toolbar;
    private boolean editMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //prevent keybaord from popping up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rootLayout = findViewById(R.id.profile_root_layout);
        toolbar = findViewById(R.id.profile_toolbar);
        profileImage = findViewById(R.id.profile_pic_iv);
        nameText = findViewById(R.id.full_name_text);
        sexText = findViewById(R.id.sex_text);
        svvText = findViewById(R.id.svv_email_text);
        emailText = findViewById(R.id.personal_email_text);
        phoneText = findViewById(R.id.phone_number_text);
        saveProfileBtn = findViewById(R.id.profile_save_btn);

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

        if(!picUrl.contains("http")){
            if(picUrl.equalsIgnoreCase("female"))
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_female_avatar));
        }
        else {
            Glide
                    .with(this)
                    .load(picUrl)
                    .into(profileImage);
        }
        nameText.setText(fullname);
        sexText.setText(sex);
        svvText.setText(svvMail);
        emailText.setText(email);
        phoneText.setText(phone);
        saveProfileBtn.setVisibility(View.GONE);
        if(!Utils.isProfileComplete(this)){
            Snackbar.make(rootLayout,"Complete your profile to continue",Snackbar.LENGTH_SHORT)
                    .show();
            saveProfileBtn.setVisibility(View.VISIBLE);
        }
        saveProfileBtn.setOnClickListener(this);
        Utils.disableEditText(nameText);
        // disable editTexts
        if(editMode==false){
            Utils.disableEditText(svvText);
            Utils.disableEditText(emailText);
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
                if(nameText.getText().toString().isEmpty()) {
                    missing.append(" name |");
                }
                editor.putString("name",nameText.getText().toString());
                if(sexText.getText().toString().equals("NA")) {
                    missing.append(" sex |");
                }
                editor.putString("sex",sexText.getText().toString());
                if(svvText.getText().toString().contains("somaiya.edu")) {
                    editor.putString("svv_mail", svvText.getText().toString());
                }
                else {
                    missing.append(" svv mail |");
                }

                if(emailText.getText().toString().isEmpty() || emailText.getText().toString().contains("NA"))
                    missing.append(" personal email |");
                editor.putString("email",emailText.getText().toString());
                if(phoneText.getText().toString().isEmpty() ||  phoneText.getText().toString().contains("NA"))
                    missing.append(" phone |");
                editor.putString("phone",phoneText.getText().toString());
                editor.commit();
                editMode = true;
                if(missing.length()>0) {
                    Snackbar.make(rootLayout, "Please provide your" + missing, Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(this,"Profile saved!",Toast.LENGTH_SHORT)
                            .show();
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
        editMode = true;
        getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        if(sf.getBoolean("signed_in_with_svv",true)){
            Utils.enableEditText(emailText);
        } else {
            Utils.enableEditText(svvText);
        }
        Utils.enableEditText(sexText);
        Utils.enableEditText(phoneText);
        saveProfileBtn.setVisibility(View.VISIBLE);
        getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        sexText.requestFocus();
        // show keyboard
        InputMethodManager inputManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

        return super.onOptionsItemSelected(item);
    }
}
