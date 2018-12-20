package com.crosswalk.hybrid.customcrosswalk;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crosswalk.hybrid.util.Log;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/12/20.
 */
public class CustomCrosswalkUIClient extends XWalkUIClient {
    @SuppressWarnings("all")
    private static final String TAG = "CustomCrosswalkUIClient";

    private ProgressBar pbWebView;
    private TextView tvTitle;

    public CustomCrosswalkUIClient(XWalkView view, ProgressBar pbWebView, TextView tvTitle) {
        super(view);

        this.pbWebView = pbWebView;
        this.tvTitle = tvTitle;
    }

    // To prevent alerts from the websites
    @Override
    public boolean onJavascriptModalDialog(XWalkView view, XWalkUIClient.JavascriptMessageType type, String url, String message,
                                           String defaultValue, XWalkJavascriptResult result) {
        result.cancel();
        return true;
    }

    /**
     * console输出时回调
     * @param view
     * @param message
     * @param lineNumber
     * @param sourceId
     * @param messageType
     * @return
     */
    @Override
    public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId,
                                    ConsoleMessageType messageType) {

        Log.i(TAG, message + " " + lineNumber + " " + sourceId + " " + messageType.name());
        return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
    }

    /**
     * 网页开始加载时调用
     * @param xWebView
     * @param url
     */
    @Override
    public void onPageLoadStarted(XWalkView xWebView, String url) {
        super.onPageLoadStarted(xWebView, url);

        // pbWebView.setVisibility(View.VISIBLE);

        Log.i(TAG, url + " is loading");
    }

    /**
     * 网页加载停止后调用
     * @param xWebView
     * @param url
     * @param status
     */
    @Override
    public void onPageLoadStopped(XWalkView xWebView, String url, LoadStatus status) {
        super.onPageLoadStopped(xWebView, url, status);

        pbWebView.setVisibility(View.INVISIBLE);

        Log.i(TAG, url + " is fully loaded");
    }

    /**
     * 获取网页Title时回调
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(XWalkView view, String title) {
        super.onReceivedTitle(view, title);

        tvTitle.setText(title);
    }
}
