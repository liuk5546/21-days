package com.example.twentyone.twenty_one;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twentyone.twenty_one.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment {

    private Button searchButton;
    private EditText searchEditText;
    private View v;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);
        initView();
        // Inflate the layout for this fragment

        return v;

    }

    @Override
    public void initView() {
        searchButton = findButById(v,R.id.frag_search_tv);
        searchEditText = findEtbyId(v,R.id.frag_fa_et);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = searchEditText.getText().toString();
                Log.d("Intent",word);
                Intent intent = new Intent(v.getContext(),MeanActivity.class);
                intent.putExtra("word",word);
                if(isTextInRule(word)){
                    startActivity(intent);
                }else{
                    Toast.makeText(v.getContext(),"字符不合法",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 需要胖段word是否合法，未实现。
     * @param word
     * @return
     */
    private boolean isTextInRule(String word){

        return true;
    }

}
