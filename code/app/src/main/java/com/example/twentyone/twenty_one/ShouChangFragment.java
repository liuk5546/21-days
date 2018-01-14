package com.example.twentyone.twenty_one;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.twentyone.twenty_one.Model.HistoryWord;
import com.example.twentyone.twenty_one.Util.WordAdapter;
import com.example.twentyone.twenty_one.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShouChangFragment extends BaseFragment {
    private List<HistoryWord> mHistoryWordList = new ArrayList<>();
    private View v;
    private Context mcontext;
    private RecyclerView wordRecyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    public ShouChangFragment(){
        mcontext = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shou_chang, container, false);
        initView();
        /**
         * 这里读取数据库里的收藏来重新载入ListView
         */
        return v;
    }


    @Override
    public void initView() {
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
                Toast.makeText(mcontext,"刷新",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 这里是一个接口将数据封装成List传入就可以生成RecycleView。
     * @param changeList
     */
    private void reloadRecycleView(List<HistoryWord> changeList){
        mHistoryWordList = changeList;
        WordAdapter wordAdapter = new WordAdapter(mHistoryWordList);
        wordRecyclerView.setAdapter(wordAdapter);
    }
    private void initWord(){
        HistoryWord historyWord = new HistoryWord();
        historyWord.setWord("apple");
        historyWord.setChineseWithPart("n. 苹果");
        mHistoryWordList.add(historyWord);
    }
}
