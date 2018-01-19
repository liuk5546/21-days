package com.example.twentyone.twenty_one;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twentyone.twenty_one.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {
    private Button bt1_fuxi;
    private Button bt2_tubiao;
    private Button bt3_toutoujici;
    private View v;



    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        return v;
    }

    @Override
    public void initView() {
        bt1_fuxi=findButById(v,R.id.angry_btn_fuxi);
        bt2_tubiao=findButById(v,R.id.angry_btn_jiyi);
        bt3_toutoujici=findButById(v,R.id.angry_btn_toutou);
        bt1_fuxi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), FUXI_activity.class);
                startActivity(intent);
            }
        });
        bt2_tubiao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), HUATU_activity.class);
                startActivity(intent);
            }
        });
        bt3_toutoujici.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), JINEWCI_activity.class);
                startActivity(intent);
            }
        });

    }

}
