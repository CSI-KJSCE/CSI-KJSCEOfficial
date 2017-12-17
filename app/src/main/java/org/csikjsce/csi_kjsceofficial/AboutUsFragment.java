package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutUsFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = AboutUsFragment.class.getSimpleName();
    String pageUrl;
    private View view ;
        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_aboutus, container, false);
            ((MainActivity)getActivity()).setActionBarTitle(getString(R.string.title_about_us));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView fbPageIcon = view.findViewById(R.id.facebook_page);
        ImageView instaPageIcon = view.findViewById(R.id.instagram_page);
        ImageView githubPageIcon = view.findViewById(R.id.github_page);

        fbPageIcon.setOnClickListener(this);
        instaPageIcon.setOnClickListener(this);
        githubPageIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.facebook_page:
                pageUrl = getResources().getString(R.string.facebook_page_link);
                break;
            case R.id.instagram_page:
                pageUrl = getResources().getString(R.string.instagram_page_link);
                break;
            case R.id.github_page:
                pageUrl = getResources().getString(R.string.github_page_link);
                break;
        }
        Utils.openLinkInCustomTab(getContext(),pageUrl);
    }

}