package com.crosswalk.hybrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crosswalk.hybrid.base.BaseFragment;
import com.crosswalk.hybrid.customcrosswalk.CustomCrosswalkResourceClient;
import com.crosswalk.hybrid.customcrosswalk.CustomCrosswalkUIClient;
import com.crosswalk.hybrid.customcrosswalk.CustomCrosswalkWebView;
import com.crosswalk.hybrid.util.Log;

import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkUpdater;

/**
 * Created by enzexue on 2018/8/10.
 */
public class XWalkCrossFragment extends BaseFragment implements XWalkInitializer.XWalkInitListener,
        XWalkUpdater.XWalkUpdateListener{

    private static final String TAG = "XWalkCrossFragment";

    private CustomCrosswalkWebView xWebView;
    private ProgressBar pbWebView;
    private TextView tvTitle;

    private XWalkInitializer xWalkInitializer;
    private XWalkUpdater xWalkUpdater;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setContentView(R.layout.x_walk_view_fragment);

        return view;//返回值必须为view
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isViewCreated = true;

        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    public void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {

            initView();
            initData();
            initEvent();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    public void initView() {
        xWebView = findView(R.id.x_walk_view);
        pbWebView = findView(R.id.pb_web_view);
        tvTitle = findView(R.id.tv_title);
    }

    public void initData() {
        xWebView.setVisibility(View.INVISIBLE);
    }

    public void initEvent() {
        xWalkInitializer = new XWalkInitializer(this, context);
        xWalkInitializer.initAsync();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (xWebView != null) {
            xWebView.pauseTimers();
            // xWebView.onHide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (xWebView != null) {
            xWebView.resumeTimers();
            // xWebView.onShow();
        }
    }

    @Override
    public void onDestroy() {
        if (xWebView != null) {
            // xWebView.clearCache(true);
            xWebView.onDestroy();
        }

        super.onDestroy();
    }

    @Override
    public void onNoDoubleClick(View view) {

    }

    /**
     * 创建一个Fragment实例
     * @return
     */
    public static XWalkCrossFragment createInstance(
            String baseUrl) {
        XWalkCrossFragment fragment = new XWalkCrossFragment();
        fragment.setBaseUrl(baseUrl);

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * 初始化crosswalk
     */
    public void initCrosswalk() {
        xWebView.setVisibility(View.VISIBLE);

        // 添加H5可调用的本地方法
        xWebView.addJavascriptInterface(this, "NativeInterface");

        xWebView.setResourceClient(new CustomCrosswalkResourceClient(xWebView, this.pbWebView));
        xWebView.setUIClient(new CustomCrosswalkUIClient(
                xWebView, this.pbWebView, this.tvTitle));

        xWebView.loadUrl(baseUrl);
    }

    //可调用的原生方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @org.xwalk.core.JavascriptInterface
    public void routeChange(String url) {

    }

    @org.xwalk.core.JavascriptInterface
    public void titleChange(final String title) {
        Log.i(TAG, baseUrl + " 设置当前页面Title为：" + title);
    }

    //可调用的原生方法 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //crosswalk相关方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onXWalkInitStarted() {
        Log.i(TAG, baseUrl + " Crosswalk开始初始化");
    }

    @Override
    public void onXWalkInitCancelled() {
        Log.i(TAG, baseUrl + " Crosswalk初始化取消");
    }

    @Override
    public void onXWalkInitFailed() {
        if (xWalkUpdater == null) {
            xWalkUpdater = new XWalkUpdater(this, context);
        }

        xWalkUpdater.updateXWalkRuntime();

        Log.i(TAG, baseUrl + " Crosswalk初始化失败");
    }

    @Override
    public void onXWalkInitCompleted() {
        Log.i(TAG, baseUrl + " Crosswalk初始化结束");

        initCrosswalk();
    }

    @Override
    public void onXWalkUpdateCancelled() {

    }

    //crosswalk相关方法 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
