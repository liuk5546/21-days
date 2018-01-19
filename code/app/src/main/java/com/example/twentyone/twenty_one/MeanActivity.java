package com.example.twentyone.twenty_one;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.twentyone.twenty_one.Util.Time_contrl;
import com.example.twentyone.twenty_one.base.BaseActivity;
import com.example.twentyone.twenty_one.url_manage.Document_manage;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MeanActivity extends BaseActivity {
    private  int count;
    private TextView trush;
    private ImageView ukVoice,usVoice,englishStar;
    private MediaPlayer voicePlayer = new MediaPlayer();
    private Uri uri;
    private TextView examples;
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
        count =1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        //动态申请权限
        if(ContextCompat.checkSelfPermission(MeanActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MeanActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        Intent intent = getIntent();
        word = intent.getStringExtra("word");
        word = word.toLowerCase();//统一输入小写

        BmobQuery<word> query = new BmobQuery<word>();
        query.addWhereEqualTo("word", word);
        query.setLimit(1);//主键只有一条
        query.findObjects(new FindListener<word>() {
            @Override
            public void done(List<word> object, BmobException e) {
                if(e==null){
                    count=object.get(0).getCount()+1;
                    String objectid = object.get(0).getObjectId();
                    word wd = new word();
                    wd.setCount(count);
                    wd.update(objectid, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i("稳","更新成功");
                            }else{
                                Log.i("CNM","更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }else{
                    word p2 = new word();
                    p2.setCount(count);
                    p2.setWord(word);
                    p2.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                            }else{
                                Log.d("81行","创建数据失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });




        voicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                Toast.makeText(MeanActivity.this,"正在播放",Toast.LENGTH_SHORT);
            }
        });
        //数据库
        dbhelper = new DBHelper(this);
        this.rs=new Document_manage().getEnglishkey_result(word);
        if(rs == null) {
            initView("wrong");
        }
        else {
            setSearchResult();
            initView();
        }
    }
    public void initWrongview()
    {
         englishName = findTextViewById(R.id.act_english_name);
         englishName.setText("您输入的单词不合法，请检查单词正确性");
    }

    @Override
    public void initView() {
        String means="";
        String example="\n";
        String teb = "\n";

        /**
         * 中文意思英文意思读音案例句子
         */
        examples = findTextViewById(R.id.act_english_shiyi);
        englishStar = findImageViewById(R.id.act_english_star);
        ukDuYin = findTextViewById(R.id.act_english_ukduyin);
        usDuYin = findTextViewById(R.id.act_english_usduyin);
        shiYi = findTextViewById(R.id.act_english_shiyi);
        reviewButton = findButById(R.id.act_review_button);
        for(int j=0;j<rs.getAcceptation().size();j++)
        {
            int i = j+1;
            means +=i+":"+rs.getAcceptation().get(j)+teb;
        }
        for(int j = 0;j<rs.getExample().size();j++)
        {
            int i = j +1;
            example += "例句"+i+":"+rs.getExample().get(j).getEnglish_example()+teb;
            example +="英译:"+rs.getExample().get(j).getChinese_example()+teb;
        }
        ukDuYin.setText("英式发音");
        usDuYin.setText("美式发音");
        shiYi.setText(means+teb+example);

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
                    if(new Time_contrl().getSecond()%2==0) {
                        voicePlayer.reset();
                        voicePlayer.setDataSource(rs.getUK_sound());
                        voicePlayer.prepareAsync();
                    }
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
                try {
                    if(new Time_contrl().getSecond()%2==0) {
                    voicePlayer.reset();
                    voicePlayer.setDataSource(rs.getUs_sound());
                    voicePlayer.prepareAsync();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   Log.e(TAG,"音频输入错误");
               }
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
        List res = rs.getAcceptation();
        rs.setKey(word);
        rs.setAcceptation(res);
    }
    public void initView(String a ) {
      String msg="您输入的单词不符合规范，请重新输入";
        englishName = findTextViewById(R.id.act_english_name);
        examples = findTextViewById(R.id.act_english_shiyi);
        englishStar = findImageViewById(R.id.act_english_star);
        ukDuYin = findTextViewById(R.id.act_english_ukduyin);
        usDuYin = findTextViewById(R.id.act_english_usduyin);
        shiYi = findTextViewById(R.id.act_english_shiyi);
        reviewButton = findButById(R.id.act_review_button);
        englishStar.setVisibility(View.GONE);
        ukDuYin.setVisibility(View.GONE);
        usDuYin.setVisibility(View.GONE);
        reviewButton.setVisibility(View.GONE);
        englishName.setText(msg);
        ukVoice = findImageViewById(R.id.act_english_ukvoice);
        usVoice=findImageViewById(R.id.act_english_usvoice);
        ukVoice.setVisibility(View.GONE);usVoice.setVisibility(View.GONE);
        trush = findTextViewById(R.id.trush);
        trush.setVisibility(View.GONE);

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
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
