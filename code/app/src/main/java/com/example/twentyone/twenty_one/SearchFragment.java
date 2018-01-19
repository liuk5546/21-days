package com.example.twentyone.twenty_one;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twentyone.twenty_one.DBControl.DBHelper;
import com.example.twentyone.twenty_one.DBControl.HistoryWordManager;
import com.example.twentyone.twenty_one.Model.CollectionWord;
import com.example.twentyone.twenty_one.Util.WordAdapter;
import com.example.twentyone.twenty_one.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment {

    private Button searchButton;
    private EditText searchEditText;
    private View v;
    private List<CollectionWord> mHistoryWordList = new ArrayList<>();
    private Context mcontext;
    private RecyclerView wordRecyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DBHelper dbHelper;
    public SearchFragment() {
        mcontext = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_search, container, false);
        dbHelper = new DBHelper(v.getContext());
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
                if(word.equals("")) {
                    //不变应万变
                }
                else {
                    Log.d("Intent", word);
                    Intent intent = new Intent(v.getContext(), MeanActivity.class);
                    intent.putExtra("word", word);
                    if (isTextInRule(word)) {
                        HistoryWordManager.insert(word, dbHelper.getWritableDatabase());
                        startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "字符不合法", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initWord();
        wordRecyclerView = v.findViewById(R.id.shou_chang_recycle_view);
        layoutManager = new LinearLayoutManager(mcontext);
        wordRecyclerView.setLayoutManager(layoutManager);
        WordAdapter wordAdapter = new WordAdapter(mHistoryWordList);
        wordRecyclerView.setAdapter(wordAdapter);
        swipeRefreshLayout = v.findViewById(R.id.refreshword);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadRecycleView();
//                Toast.makeText(mcontext,"刷新",Toast.LENGTH_LONG).show();
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
    private void reloadRecycleView(){
        initWord();
        WordAdapter wordAdapter = new WordAdapter(mHistoryWordList);
        wordRecyclerView.setAdapter(wordAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }
    private void initWord(){
        mHistoryWordList = HistoryWordManager.loadAllCollection(dbHelper.getReadableDatabase());
    }
}
