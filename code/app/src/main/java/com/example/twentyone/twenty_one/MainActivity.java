package com.example.twentyone.twenty_one;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twentyone.twenty_one.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout lin[];
    private ViewPager viewpager;
    private ImageView imgs[];
    private TextView tvs[];
    private int imgId[] = new int[]{R.drawable.tuijianon, R.drawable.faxianon, R.drawable.wodeon, R.drawable.tuijianoff, R.drawable.faxianoff, R.drawable.wodeoff};
    private List<Fragment> fragments = new ArrayList<>();
    //private Resources resource = (Resources) getBaseContext().getResources();
    private MyPagerAdapter myPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments.add(new TuiJianFragment());
        fragments.add(new SearchFragment());
        fragments.add(new MyFragment());
        initView();
        for (LinearLayout linx:lin
             ) {
            linx.setOnClickListener(this);
        }
    }

    @Override
    public void initView() {
        LinearLayout lin1 = findLinById(R.id.act_main_lin1);
        LinearLayout lin2 = findLinById(R.id.act_main_lin2);
        LinearLayout lin3 = findLinById(R.id.act_main_lin3);
        ImageView img1 = findImageViewById(R.id.act_main_img1);
        ImageView img2 = findImageViewById(R.id.act_main_img2);
        ImageView img3 = findImageViewById(R.id.act_main_img3);
        TextView tv1 = findTextViewById(R.id.act_main_tv1);
        TextView tv2 = findTextViewById(R.id.act_main_tv2);
        TextView tv3 = findTextViewById(R.id.act_main_tv3);
        viewpager = findViewById(R.id.act_main_vp);
        imgs = new ImageView[]{img1, img2, img3};
        tvs = new TextView[]{tv1, tv2, tv3};
        lin = new LinearLayout[]{lin1,lin2,lin3};
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myPagerAdapter);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setCurrentItem(1);
        setItem(1);
        /**
         * 设置ViewPager的滑动事件
         */
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 设置监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_lin1:
                setItem(0);
                viewpager.setCurrentItem(0);
                break;
            case R.id.act_main_lin2:
                setItem(1);
                viewpager.setCurrentItem(1);
                break;
            case R.id.act_main_lin3:
                setItem(2);
                viewpager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 这个函数是用来更换地步方框图片和文字的颜色
     * @param index
     */
    private void setItem(int index) {
        for (int i = 0; i < 3; i++) {
            if (i == index) {
                imgs[i].setImageResource(imgId[i]);
                tvs[i].setTextColor(Color.parseColor("#D74B25"));
            } else {
                imgs[i].setImageResource(imgId[i + 3]);
                tvs[i].setTextColor(Color.parseColor("#515151"));
            }
        }
    }

    /**
     * 这个是ViewPage的适配器
     */
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
