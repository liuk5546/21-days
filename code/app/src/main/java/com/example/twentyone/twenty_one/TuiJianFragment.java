package com.example.twentyone.twenty_one;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twentyone.twenty_one.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TuiJianFragment extends BaseFragment {


    public TuiJianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tui_jian, container, false);
    }

    @Override
    public void initView() {

    }
}