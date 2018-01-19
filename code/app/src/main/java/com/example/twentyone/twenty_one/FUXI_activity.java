package com.example.twentyone.twenty_one;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twentyone.twenty_one.DBControl.CollectionWordManager;
import com.example.twentyone.twenty_one.DBControl.DBHelper;
import com.example.twentyone.twenty_one.Model.CollectionWord;
import com.example.twentyone.twenty_one.Model.SearchResult;
import com.example.twentyone.twenty_one.url_manage.Document_manage;

import java.util.List;

/**
 * Created by Annz_2 on 2018/1/19.
 */

public class FUXI_activity  extends Activity {
    private String count;
    private String thiswords;
    private SQLiteOpenHelper dbhelper;
    private int page;
    private TextView fuxi_name;
    private TextView fuxiex;
    private List<CollectionWord> listword;
    protected void onCreate(Bundle savedInstanceState) {
        page =0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuxi_main);
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        dbhelper = new DBHelper(this);
       listword= CollectionWordManager.loadAllCollection(dbhelper.getReadableDatabase());
       init(page);

    }
    public void fuxi_return(View v)
    {
        Intent intent =new Intent(FUXI_activity.this, MainActivity.class);
        startActivity(intent);
    }
    public void fuxu_next(View v)
    {
        init(this.page);
    }
    public void init(int page)
    {
        fuxi_name= findViewById(R.id.FUXI_WORD);
        fuxiex=findViewById(R.id.THE_EXAMPLE);
        try{
        thiswords = listword.get(page).getWord();
        count=""+listword.get(page).getTimes();
        }
        catch (Exception e)
        {
            Toast.makeText(FUXI_activity.this, "输出已经是最后一页了!", Toast.LENGTH_LONG).show();
        }
        String namemean="";
        String examples = "";
        Document_manage docm = new Document_manage();
        SearchResult rs = docm.getEnglishkey_result(thiswords);
        namemean = rs.getKey()+"(该单词的复习次数:"+count+")"+"\n";
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
        fuxi_name.setText(namemean);
        fuxiex.setText(examples);
       this.page++;
    }
}
