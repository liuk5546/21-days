package com.example.twentyone.twenty_one.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twentyone.twenty_one.Model.HistoryWord;
import com.example.twentyone.twenty_one.R;

import java.util.List;

/**
 * Created by liuk on 2018/1/9.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private List<HistoryWord> mHistoryWord;

    public WordAdapter(List<HistoryWord> mHistoryWord) {
        this.mHistoryWord = mHistoryWord;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryWord historyWord = mHistoryWord.get(position);
        holder.wordTextView.setText(historyWord.getWord());
        holder.wordMeanTextView.setText(historyWord.getChineseWithPart());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里写当这个区域被点击的时候需要做的事情
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryWord.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView wordTextView;
        TextView wordMeanTextView;
        View parentView;
        public ViewHolder(View itemView) {
            super(itemView);
            wordTextView = (TextView) itemView.findViewById(R.id.word_textView);
            wordMeanTextView = (TextView) itemView.findViewById(R.id.word_mean_textView);
            parentView = itemView;
        }
    }

}
