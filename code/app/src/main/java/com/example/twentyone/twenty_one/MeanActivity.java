package com.example.twentyone.twenty_one;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twentyone.twenty_one.base.BaseActivity;

import java.io.IOException;

public class MeanActivity extends BaseActivity {

    private ImageView ukVoice,usVoice;
    private MediaPlayer voicePlayer = new MediaPlayer();
    private Uri uri;
    private String word; //要查询的单词
    private TextView englishName;//英语单词
    private ImageButton backButton;//返回按钮
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
        initView();
    }

    @Override
    public void initView() {
        englishName = findTextViewById(R.id.act_english_name);
        englishName.setText(word);
        ukVoice = findImageViewById(R.id.act_english_ukvoice);
        ukVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uri = Uri.parse("http://res.iciba.com/resource/amp3/0/0/34/d1/34d1f91fb2e514b8576fab1a75a89a6b.mp3");
                    voicePlayer.setDataSource(MeanActivity.this,uri);
                    voicePlayer.prepare();
                    Toast.makeText(MeanActivity.this,"准备播放",Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"音频输入错误");
                }
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
}
