package com.crosswalk.hybrid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crosswalk.hybrid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enzexue on 2018/8/8.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private static final String[] TAB_NAMES = {"1", "2", "3"};
    private static final int[] TAB_DEFAULT_ICON = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    private static final int[] TAB_SELECTED_ICON = {R.drawable.ic_launcher_round, R.drawable.ic_launcher_round, R.drawable.ic_launcher_round};

    private XWalkCrossFragment fragmentTabMain;
    private XWalkCrossFragment fragmentTabFunds;
    private XWalkCrossFragment fragmentTabMy;
    private List<XWalkCrossFragment> fragments;

    private CustomViewPager viewPager;
    private TabLayout tabLayout;

    private FragmentStatePagerAdapter adapter;

    /**
     * 启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setBackgroundDrawable(null);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initView() {
        tabLayout = findView(R.id.tab_layout);
        viewPager = findView(R.id.frame_main_tab_container);

        fragmentTabMain = XWalkCrossFragment.createInstance("https://www.baidu.com/");
        fragmentTabFunds = XWalkCrossFragment.createInstance("https://www.baidu.com/");
        fragmentTabMy = XWalkCrossFragment.createInstance("https://www.baidu.com/");
    }

    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(fragmentTabMain);
        fragments.add(fragmentTabFunds);
        fragments.add(fragmentTabMy);

        viewPager.setOffscreenPageLimit(fragments.size() + 1);
        viewPager.setNoScroll(true); // 禁止左右滑动

        adapter = new FragmentStatePagerAdapter(fragmentManager){

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.main_tab_item);//给每一个tab设置view

            TextView textView = tab.getCustomView().findViewById(R.id.tv_main_tab_item);
            ImageView imageView = tab.getCustomView().findViewById(R.id.iv_main_tab_item);

            textView.setText(TAB_NAMES[i]);//设置tab上的文字

            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                textView.setSelected(true);
                textView.setTextColor(getResources().getColor(R.color.red));
                imageView.setBackgroundResource(TAB_SELECTED_ICON[i]);
                continue;
            }

            imageView.setBackgroundResource(TAB_DEFAULT_ICON[i]);
        }
    }

    public void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tv_main_tab_item);
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_main_tab_item);

                textView.setSelected(true);
                textView.setTextColor(getResources().getColor(R.color.red));
                imageView.setBackgroundResource(TAB_SELECTED_ICON[tab.getPosition()]);

                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tv_main_tab_item);
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_main_tab_item);

                textView.setSelected(false);
                textView.setTextColor(getResources().getColor(R.color.gray_1));
                imageView.setBackgroundResource(TAB_DEFAULT_ICON[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }
        });
    }

    @Override
    public void onNoDoubleClick(View view) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

