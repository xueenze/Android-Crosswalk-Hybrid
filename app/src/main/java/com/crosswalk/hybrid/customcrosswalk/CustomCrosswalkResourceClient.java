package com.crosswalk.hybrid.customcrosswalk;

import android.net.http.SslError;
import android.webkit.ValueCallback;
import android.widget.ProgressBar;

import com.crosswalk.hybrid.util.Log;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

/**
 * Created by enzexue on 2018/12/20.
 */
public class CustomCrosswalkResourceClient extends XWalkResourceClient {

    private static final String TAG = "CustomCrosswalkResourceClient";

    private ProgressBar pbWebView;

    public CustomCrosswalkResourceClient(XWalkView view, ProgressBar pbWebView) {
        super(view);

        this.pbWebView = pbWebView;
    }

    /**
     * 资源加载进度回调
     * @param view
     * @param progressInPercent
     */
    @Override
    public void onProgressChanged(XWalkView view, int progressInPercent) {
        super.onProgressChanged(view, progressInPercent);

        pbWebView.setProgress(progressInPercent);
    }

    /**
     * 资源加载异常回调
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        super.onReceivedLoadError(view, errorCode, description, failingUrl);

    }

    /**
     * 证书异常回调
     * @param view
     * @param callback
     * @param error
     */
    @Override
    public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
        super.onReceivedSslError(view, callback, error);
    }

    @Override
    public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view,
                                                               XWalkWebResourceRequest request) {
        return super.shouldInterceptLoadRequest(view, request);
    }

    /**
     * 防止链接被其他浏览器打开
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
        Log.i(TAG, url);

        view.loadUrl(url);

        return true;
    }
}
