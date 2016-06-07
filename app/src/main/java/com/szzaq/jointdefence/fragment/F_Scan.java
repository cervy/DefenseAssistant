package com.szzaq.jointdefence.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szzaq.jointdefence.CaptureActivity;
import com.szzaq.jointdefence.R;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class F_Scan extends android.app.Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_scan, container,
                false);

        view.findViewById(R.id.toScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CaptureActivity.class));
            }
        });
        return view;
    }

}
