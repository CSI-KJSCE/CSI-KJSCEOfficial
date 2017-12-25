package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sumit on 5/9/17.
 */

public class EurekaFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View view;
    TextView eureka_para_tv;
    String shareAppName;
    ImageView fbIcon, instaIcon, whatsappIcon, shareIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eureka_fragment, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_eureka));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eureka_para_tv = view.findViewById(R.id.eureka_textview);
        fbIcon = view.findViewById(R.id.facebook_share);
        instaIcon = view.findViewById(R.id.instagram_share);
        whatsappIcon = view.findViewById(R.id.whatsapp_share);
        shareIcon = view.findViewById(R.id.general_share);

        fbIcon.setOnClickListener(this);
        instaIcon.setOnClickListener(this);
        whatsappIcon.setOnClickListener(this);
        shareIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.facebook_share:
                shareAppName = "com.facebook.katana";
                Utils.onShareClick(getContext(), eureka_para_tv.getText().toString(), shareAppName);
                break;
            case R.id.instagram_share:
                shareAppName = "com.instagram.android";
                Utils.onShareClick(getContext(), eureka_para_tv.getText().toString(), shareAppName);
                break;
            case R.id.whatsapp_share:
                shareAppName = "com.whatsapp";
                Utils.onShareClick(getContext(),eureka_para_tv.getText().toString(), shareAppName);
                break;
            case R.id.general_share:
                intent = new Intent();
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, eureka_para_tv.getText().toString());
                startActivity(intent);
                break;
        }

    }
}
