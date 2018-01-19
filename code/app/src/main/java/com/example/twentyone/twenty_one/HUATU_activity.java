package com.example.twentyone.twenty_one;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.twentyone.twenty_one.DBControl.CollectionWordManager;
import com.example.twentyone.twenty_one.DBControl.DBHelper;
import com.example.twentyone.twenty_one.Model.CollectionWord;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 *
 * @Author Annz_2
 *
 */
public class HUATU_activity extends AppCompatActivity {
    int count[] = new int[7];

    private SQLiteOpenHelper dbhelper;
    private LineChartView line_chart_view;
    private LineChartData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        dbhelper = new DBHelper(this);
        for(int i=0;i<7;i++)
            count[i]=0;

        line_chart_view = (LineChartView) findViewById(R.id.line_chart_view);
                List<Line> lines = initLine();
        data = initData(lines);

        line_chart_view.setLineChartData(data);

        Viewport viewport = initViewPort();
        line_chart_view.setMaximumViewport(viewport);
        line_chart_view.setCurrentViewport(viewport);
    }


    /**
     * 设置4个边距 
     */
    private Viewport initViewPort() {
        Viewport viewport = new Viewport();
        viewport.top = 100;
        viewport.bottom = 0;
        viewport.left = 1;
        viewport.right = 7;

        return viewport;
    }

    /**
     * 初始化线属性 
     *
     * @return
     */
    private List<Line> initLine() {
        List<CollectionWord> listword= CollectionWordManager.loadAllCollection(dbhelper.getReadableDatabase());
        List<Line> lineList = new ArrayList<>();

        List<PointValue> pointValueList = new ArrayList<>();
        int count = lineList.size();
        for(int i=0;i<lineList.size();i++)
        {
           String  a=listword.get(i).getLastReviewDate();
           int counts = listword.get(i).getTimes();
        }
        //shili
        PointValue pointValue1 = new PointValue(1,54);
        pointValueList.add(pointValue1);
        PointValue pointValue2 = new PointValue(2,35);
        pointValueList.add(pointValue2);
        PointValue pointValue3 = new PointValue(3,42);
        pointValueList.add(pointValue3);
        PointValue pointValue4 = new PointValue(4,69);
        pointValueList.add(pointValue4);
        PointValue pointValue5 = new PointValue(5,64);
        pointValueList.add(pointValue5);
        PointValue pointValue6 = new PointValue(6,31);
        pointValueList.add(pointValue6);
        PointValue pointValue7 = new PointValue(7,22);
        pointValueList.add(pointValue7);
        PointValue pointValue8 = new PointValue(8,65);
        pointValueList.add(pointValue8);


        Line line = new Line(pointValueList);
        line.setColor(getResources().getColor(R.color.colorAccent));
        line.setShape(ValueShape.CIRCLE);
        lineList.add(line);

        return lineList;
    }

    /**
     * 初始化数据 
     *
     * @return
     */
    private LineChartData initData(List<Line> lines) {

        LineChartData data = new LineChartData(lines);
        //初始化轴  
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("words");
        //前加字符  
//        axisX.setFormatter(new SimpleAxisValueFormatter().setPrependedText("aaaa".toCharArray()));  
        //后加字符  
//        axisX.setFormatter(new SimpleAxisValueFormatter().setAppendedText("aaaa".toCharArray()));  
//        axisX.setFormatter(new SimpleAxisValueFormatter());  
        axisY.setName("this_7day");
        //设置轴  
        data.setAxisYLeft(axisY);
        data.setAxisXBottom(axisX);
        //设置负值 设置为负无穷 默认为0  
//        data.setBaseValue(Float.NEGATIVE_INFINITY);  

        return data;
    }


}  