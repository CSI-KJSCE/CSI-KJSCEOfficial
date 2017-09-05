package org.csikjsce.csi_kjsceofficial;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sumit on 5/9/17.
 */

public class EurekaFragment extends android.support.v4.app.Fragment{
    private View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.eureka_fragment, container, false);
        ((MainActivity)getActivity()).setActionBarTitle("Eureka");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
