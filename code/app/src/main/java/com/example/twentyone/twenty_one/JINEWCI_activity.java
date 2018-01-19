package com.example.twentyone.twenty_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twentyone.twenty_one.Model.SearchResult;
import com.example.twentyone.twenty_one.url_manage.Document_manage;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Annz_2 on 2018/1/19.
 */

public class JINEWCI_activity extends Activity {
    private TextView nameandmean;
    private TextView example;
    private List<word> wordList;
    private int page = 0;
    private String thiswodrd;
    private Button nextb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_main);
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        BmobQuery<word> query = new BmobQuery<word>();
        query.setLimit(30);//30个最热单词
        query.order("-count");
        query.findObjects(new FindListener<word>() {
            @Override
            public void done(List<word> object, BmobException e) {
                if(e==null){
                    wordList = object;
                    thiswodrd = object.get(0).getWord();
                }else{

                }
            }
        });
        nameandmean = findViewById(R.id.new_wd_namemean);
        example = findViewById(R.id.new_word_example);
        nextb =findViewById(R.id.next_new);


    }
    public void init(String word)
    {
        String namemean="";
        String examples = "";
        Document_manage docm = new Document_manage();
        SearchResult rs = docm.getEnglishkey_result(word);
        namemean = rs.getKey()+"\n";
        for(int i=0;i<rs.getAcceptation().size();i++)
        {
            int j = i+1;
            namemean +=j+":"+rs.getAcceptation().get(i)+"\n";
        }
        for(int j = 0;j<rs.getExample().size();j++)
        {
            int i = j +1;
            examples += "例句"+i+":"+rs.getExample().get(j).getEnglish_example()+"\n";
            examples +="英译:"+rs.getExample().get(j).getChinese_example()+"\n";
        }
        nameandmean.setText(namemean);
        example.setText(examples);

    }
    public void new_return(View v)
    {
        Intent intent =new Intent(JINEWCI_activity.this, MainActivity.class);
        startActivity(intent);
    }
    public void new_next(View v)
    {
       nextb.setText("下一个！");
       try {
           thiswodrd = wordList.get(page).getWord();
       }
       catch(Exception e )
       {
           //输出已经是最后一页了
           Toast.makeText(JINEWCI_activity.this, "输出已经是最后一页了!", Toast.LENGTH_LONG).show();
       }
       init(thiswodrd);
        page++;
    }

}
