package com.anna.pdf;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * description:PDF 预览
 * time: 2023/3/7 4:11 PM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
public class PdfPreviewActivity extends AppCompatActivity {
    private static final String CODING = "UTF-8";
    private RelativeLayout mNavigation;
    private ImageView mIvClose;
    private TextView mTvTitle;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anna_pdf_preview_activity);

        mNavigation = findViewById(R.id.anna_layout_navigation);
        mIvClose = findViewById(R.id.anna_iv_close);
        mTvTitle = findViewById(R.id.anna_tv_title);
        mWebView = findViewById(R.id.anna_webview);
        mIvClose.setOnClickListener(v -> finish());
        //导航是否显示
        boolean needNavigation = getIntent().getExtras().getBoolean("needNavigation", true);
        findViewById(R.id.anna_layout_navigation).setVisibility(needNavigation ? View.VISIBLE : View.GONE);
        if (needNavigation) {
            //导航背景颜色
            String navigationColor = getIntent().getExtras().getString("navigationColor", "#FFFFFFFF");
            mNavigation.setBackgroundColor(Color.parseColor(navigationColor));
            //页面title
            String titleText = getIntent().getExtras().getString("titleText", "");
            mTvTitle.setText(titleText);
            //title 颜色
            String titleColor = getIntent().getExtras().getString("titleColor", "#FF000000");
            mTvTitle.setTextColor(Color.parseColor(titleColor));
            //返回按钮颜色
            boolean blackBack = getIntent().getExtras().getBoolean("blackBack", true);
            mIvClose.setImageResource(blackBack ? R.drawable.anna_ic_arrow_back_left : R.drawable.anna_ic_arrow_white_left);
        }
        initWebView();
        mWebView.loadUrl("file:///android_asset/pdf/index.html?" + getPath());
        MsgLog.d("==加载地址：" + mWebView.getUrl());
    }

    public void initWebView() {
        // 屏蔽页面垂直方向滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        // 屏蔽页面水平方向滚动条
        mWebView.setHorizontalScrollBarEnabled(false);

        WebSettings webSettings = mWebView.getSettings();
        //获取默认的 UserAgent
        String ua = webSettings.getUserAgentString();
        // 设置 UserAgent 后缀为应用名称
        webSettings.setUserAgentString(ua + ";" + "loop-consumer");
        // 设置WebView支持<meta>标签的viewport属性
        webSettings.setUseWideViewPort(true);
        // 设置字体缩放
        webSettings.setTextZoom(100);
        //设置运行加载js
        webSettings.setJavaScriptEnabled(true);
        // 允许js弹出窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置编码
        webSettings.setDefaultTextEncodingName(CODING);
        //设置支持DomStorage 开启dom缓存
        webSettings.setDomStorageEnabled(true);
        // 实现8倍缓存
//        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        // 开启Application Cache功能
//        webSettings.setAppCacheEnabled(true);
        //取得缓存路径
        String appCachePath = getCacheDir().getAbsolutePath();
        //设置路径
        //API 19 deprecated
        webSettings.setDatabasePath(appCachePath);
        // 设置Application caches缓存目录
//        webSettings.setAppCachePath(appCachePath);
        //是否启用数据库
        webSettings.setDatabaseEnabled(true);
        //设置存储模式 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(appCachePath);
        //设置不支持字体缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件，默认是true。如果不想显示放大和缩小按钮，将该属性设置为false即可
        webSettings.setDisplayZoomControls(false);
        //设置允许
        webSettings.setGeolocationEnabled(true);
        // 禁止长按事件
//        webView.setOnLongClickListener(view -> true);
        //google审核下面三个配置必须是false,配置 如果是release包就配置false 其他都是true
//        boolean isRelease = TextUtils.equals("release", BuildConfig.BUILD_TYPE);
        boolean isRelease = true;
        // 设置在WebView内部是否允许通过file url加载的 Js代码读取其他的本地文件
        // Android 4.1前默认允许，4.1后默认禁止
        webSettings.setAllowFileAccessFromFileURLs(isRelease);
        // 设置WebView内部是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)
        // Android 4.1前默认允许，4.1后默认禁止
        webSettings.setAllowUniversalAccessFromFileURLs(isRelease);
        // 设置在WebView内部是否允许访问文件，默认允许访问。
        webSettings.setAllowFileAccess(isRelease);
        // 设置在WebView内部是否允许访问ContentProvider，默认允许访问。
        webSettings.setAllowContentAccess(isRelease);
    }

    /**
     * @return pdf 加载路径
     */
    public String getPath() {
        // content://media/external_primary/downloads/680
//        return getIntent().getExtras().getString("path");
//        return "file:///android_asset/example.pdf";
//        return "/storage/emulated/0/Download/4100000013_1678180470372.pdf";
//        return "https://www.cfca.com.cn/upload/20211117flsm.pdf";
        return getIntent().getExtras().getString("path");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            //移除WebView
            if (mWebView != null) {
                mWebView.stopLoading();
                mWebView.removeAllViews();
                mWebView.setTag(null);
                mWebView.clearHistory();
                mWebView.onPause();
                mWebView.destroy();
            }
        }
    }
}
