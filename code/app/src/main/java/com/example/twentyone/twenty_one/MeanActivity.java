package com.example.twentyone.twenty_one;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twentyone.twenty_one.DBControl.CollectionWordManager;
import com.example.twentyone.twenty_one.DBControl.DBHelper;
import com.example.twentyone.twenty_one.Model.CollectionWord;
import com.example.twentyone.twenty_one.Model.SearchResult;
import com.example.twentyone.twenty_one.base.BaseActivity;

import java.io.IOException;

public class MeanActivity extends BaseActivity {

    private ImageView ukVoice,usVoice,englishStar;
    private MediaPlayer voicePlayer = new MediaPlayer();
    private Uri uri;
    private String word; //要查询的单词
    private TextView englishName,ukDuYin,usDuYin,shiYi;//英语单词，英读音，美读音
    private ImageButton backButton;//返回按钮
    private Button reviewButton;//复习按钮
    private SQLiteOpenHelper dbhelper;
    private CollectionWord cw;
    private SearchResult rs = new SearchResult();
    public static final String TAG = "Mean";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);
        //动态申请权限
        if(ContextCompat.checkSelfPermission(MeanActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MeanActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        Intent intent = getIntent();
        word = intent.getStringExtra("word");

        voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                Toast.makeText(MeanActivity.this,"正在播放",Toast.LENGTH_SHORT);
            }
        });
        //数据库
        dbhelper = new DBHelper(this);
        setSearchResult();
        initView();
    }

    @Override
    public void initView() {

        /**
         * 网络请求然后把东西都填入相应的位置
         */
        englishStar = findImageViewById(R.id.act_english_star);
        ukDuYin = findTextViewById(R.id.act_english_ukduyin);
        usDuYin = findTextViewById(R.id.act_english_usduyin);
        shiYi = findTextViewById(R.id.act_english_shiyi);
        reviewButton = findButById(R.id.act_review_button);

        /**
         * 收藏控制
         * 如果点击图案会改变
         * 如果已经收藏第一次就要去改变他的图案
         */
        cw = CollectionWordManager.findByWord(word,dbhelper.getReadableDatabase());
        if (cw.getId()!=-1){
            englishStar.setImageResource(R.drawable.staron);
            reviewButton.setVisibility(View.VISIBLE);
            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CollectionWordManager.review(cw,dbhelper.getWritableDatabase());
                }
            });
        }else{
            reviewButton.setVisibility(View.INVISIBLE);
        }
        englishStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cw.getId()!=-1){
                    //如果存在就删除并且改星星
                    CollectionWordManager.deleteByWord(word,dbhelper.getWritableDatabase());
                   englishStar.setImageResource(R.drawable.staroff);
                }else {
                    //如果不存在就插入
                    CollectionWordManager.insert(rs,dbhelper.getWritableDatabase());
                    englishStar.setImageResource(R.drawable.staron);
                }
            }
        });
        englishName = findTextViewById(R.id.act_english_name);
        englishName.setText(word);
        ukVoice = findImageViewById(R.id.act_english_ukvoice);
        ukVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    voicePlayer.setDataSource("http://res.iciba.com/resource/amp3/0/0/34/d1/34d1f91fb2e514b8576fab1a75a89a6b.mp3");
                    voicePlayer.prepareAsync();
                    Toast.makeText(MeanActivity.this,"准备播放",Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"音频输入错误");
                }
            }
        });
        voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                Log.d("Voice","播放");
            }
        });
        usVoice = findImageViewById(R.id.act_english_usvoice);
        usVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    voicePlayer.setDataSource(MeanActivity.this,uri);
//                    voicePlayer.prepare();
//                    voicePlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e(TAG,"音频输入错误");
//                }
            }
        });
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(voicePlayer!=null){
            voicePlayer.stop();
            voicePlayer.release();
        }
    }

    /**
     * 初始化搜索结果，就是把搜索到的单词结果放到SearchResult里面
     * 未实现
     */
    private void setSearchResult(){
        rs.setKey(word);
        rs.setAcceptation("n.去，走");
    }
}
