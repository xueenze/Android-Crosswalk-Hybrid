package com.crosswalk.hybrid.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crosswalk.hybrid.util.Log;

import java.util.Calendar;

/**
 * Created by enzexue on 2018/12/20.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BaseFragment";

    /**
     * 添加该Fragment的Activity
     *
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Fragment全局视图
     *
     * @must 非abstract子类的onCreateView中return view;
     * @warn 不能在子类中创建
     */
    protected View view = null;
    /**
     * 布局解释器
     *
     * @warn 不能在子类中创建
     */
    protected LayoutInflater inflater = null;
    /**
     * 添加这个Fragment视图的布局
     *
     * @warn 不能在子类中创建
     */
    @Nullable
    protected ViewGroup container = null;

    /**
     * @must 在非abstract子类的onCreateView中super.onCreateView且return view;
     */
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = (BaseActivity) getActivity();

        this.inflater = inflater;
        this.container = container;

        return view;
    }

    /**
     * 设置界面布局
     *
     * @param layoutResID
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }

    /**
     * 设置界面布局
     *
     * @param v
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v) {
        setContentView(v, null);
    }

    /**
     * 设置界面布局
     *
     * @param v
     * @param params
     * @warn 最多调用一次
     * @use 在onCreateView后调用
     */
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
    }


    /**
     * 可用于 打开activity与fragment，fragment与fragment之间的通讯（传值）等
     */
    protected Bundle argument = null;
    /**
     * 可用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;

    /**
     * 通过id查找并获取控件，使用时不需要强转
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int id) {
        return (V) view.findViewById(id);
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findView(int id, View.OnClickListener l) {
        V v = findView(id);
        v.setOnClickListener(l);
        return v;
    }

    /**
     * 通过id查找并获取控件，使用时不需要强转
     *
     * @param id
     * @return
     * @warn 调用前必须调用setContentView
     */
    public <V extends View> V findViewById(int id) {
        return findView(id);
    }

    /**
     * 通过id查找并获取控件，并setOnClickListener
     *
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        return findView(id, l);
    }


    public Intent getIntent() {
        return context.getIntent();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "\n onResume <<<<<<<<<<<<<<<<<<<<<<<");
        super.onResume();
        Log.d(TAG, "onResume >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "\n onPause <<<<<<<<<<<<<<<<<<<<<<<");
        super.onPause();
        Log.d(TAG, "onPause >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    /**
     * 销毁并回收内存
     *
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "\n onDestroy <<<<<<<<<<<<<<<<<<<<<<<");
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }

        super.onDestroy();

        view = null;
        inflater = null;
        container = null;

        intent = null;
        argument = null;

        context = null;

        Log.d(TAG, "onDestroy >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    // 防止按钮多次点击 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final int MIN_CLICK_DELAY_TIME = 500;   //点击时间间隔
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if( (currentTime - lastClickTime) > MIN_CLICK_DELAY_TIME ){
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }

    public abstract void onNoDoubleClick(View view);

    // 防止按钮多次点击 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

