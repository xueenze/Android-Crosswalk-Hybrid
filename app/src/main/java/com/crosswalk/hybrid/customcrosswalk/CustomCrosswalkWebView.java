package com.crosswalk.hybrid.customcrosswalk;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.WebSettings;

import com.crosswalk.hybrid.BuildConfig;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkSettingsInternal;
import org.xwalk.core.internal.XWalkViewBridge;

import java.lang.reflect.Method;

/**
 * Created by enzexue on 2018/12/20.
 */
public class CustomCrosswalkWebView extends XWalkView{

    private static final String TAG = "CustomCrosswalkWebView";

    public CustomCrosswalkWebView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init(context);
        }
    }

    public CustomCrosswalkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * 初始化webview
     */
    private void init(Context context) {
        // 判断是否启动远程调试
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, BuildConfig.DEBUG);
        // 是否允许通过file url加载的Javascript可以访问其他的源,包括其他的文件和http,https等其他的源
        XWalkPreferences.setValue(XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);

        setUserAgent(this, "custom-useragent");

        setAppCache(this);
        setOtherSettings(this);
    }

    /**
     * 设置User-agent
     *
     * @param view      Crosswalk view
     * @param userAgent Custom user agent
     */
    private void setUserAgent(XWalkView view, String userAgent) {
        try {
            final Method getBridgeMethod = XWalkView.class.getDeclaredMethod("getBridge");
            getBridgeMethod.setAccessible(true);
            final XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) getBridgeMethod.invoke(view);
            final XWalkSettingsInternal xWalkSettingsInternal = xWalkViewBridge.getSettings();
            xWalkSettingsInternal.setUserAgentString(userAgent);

            xWalkSettingsInternal.setAllowUniversalAccessFromFileURLs(true);
        } catch (Exception ignored) {

        }
    }

    /**
     * 设置缓存信息
     * @param view
     */
    private void setAppCache(XWalkView view) {
        try {
            final Method getBridgeMethod = XWalkView.class.getDeclaredMethod("getBridge");
            getBridgeMethod.setAccessible(true);
            final XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) getBridgeMethod.invoke(view);
            final XWalkSettingsInternal xWalkSettingsInternal = xWalkViewBridge.getSettings();
            xWalkSettingsInternal.setAllowUniversalAccessFromFileURLs(true);

            xWalkSettingsInternal.setDomStorageEnabled(true);
            xWalkSettingsInternal.setAppCacheEnabled(true);
            xWalkSettingsInternal.setAllowFileAccess(true);
            xWalkSettingsInternal.setDatabaseEnabled(true);
            xWalkSettingsInternal.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        } catch (Exception ignored) {

        }
    }

    /**
     * 设置其他配置
     * @param xWalkView
     */
    private void setOtherSettings(XWalkView xWalkView) {
        xWalkView.setBackgroundColor(Color.WHITE);
        xWalkView.setZOrderOnTop(true);

        XWalkSettings mWebSettings = xWalkView.getSettings();
        mWebSettings.setSupportZoom(true);//支持缩放
        mWebSettings.setBuiltInZoomControls(true);//可以任意缩放
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);//支持JS
    }

    /**
     * 返回上一页
     */
    public void goBack() {
        if (getNavigationHistory().canGoBack()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
        }
    }

    /**
     * 去下一页
     */
    public void goForward() {
        if (getNavigationHistory().canGoForward()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.FORWARD, 1);
        }
    }

    /**
     * 重新加载
     */
    public void reload() {
        reload(RELOAD_NORMAL);
    }

    @Override
    public void loadUrl(String url) {
        if (url != null && !url.isEmpty() && !url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        super.loadUrl(url);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}

