package com.zhuanghongji.flowlayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhuanghongji.flowlayout.fragment.EventTestFragment;
import com.zhuanghongji.flowlayout.fragment.LimitSelectedFragment;
import com.zhuanghongji.flowlayout.fragment.ScrollViewTestFragment;
import com.zhuanghongji.flowlayout.fragment.SimpleFragment;
import com.zhuanghongji.flowlayout.fragment.TestFragment;
import com.zhuanghongji.flowlayout.fragment.SingleChooseFragment;
import com.zhuanghongji.flowlayout.view.FlowLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private FlowLayout mFlowLayout;

    private String[] mTabTitles = new String[]
            {"单选","多选", "限制3个", "点击事件测试", "视图滚动测试"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFlowLayout = (FlowLayout) findViewById(R.id.view_flow_layout);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new SingleChooseFragment();
                    case 1:
                        return new SimpleFragment();
                    case 2:
                        return new LimitSelectedFragment();
                    case 3:
                        return new EventTestFragment();
                    case 4:
                        return new ScrollViewTestFragment();
                    default:
                        return new SingleChooseFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles[position];
            }

            @Override
            public int getCount() {
                return mTabTitles.length;
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
