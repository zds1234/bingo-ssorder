package com.eunut.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.eunut.sdk.R;
import com.eunut.widget.TopBar;

public class BrowserActivity extends Activity {

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String BASE_URL = "base_url";

    private WebView web_view;
    private View loading_indicator;
    private ImageView pre_arrow, next_arrow, close_button;
    private TopBar title_bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eunut_activity_browser);
        loading_indicator = findViewById(R.id.loading_indicator);
        pre_arrow = (ImageView) findViewById(R.id.pre_arrow);
        next_arrow = (ImageView) findViewById(R.id.next_arrow);
        close_button = (ImageView) findViewById(R.id.close_button);
        title_bar = (TopBar) findViewById(R.id.top_bar);
        pre_arrow.setOnClickListener(onClickListener);
        next_arrow.setOnClickListener(onClickListener);
        close_button.setOnClickListener(onClickListener);
        web_view = (WebView) findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        title_bar.setOnActionClickListener(new TopBar.ActionClickListener() {
            @Override
            public void onActionClick(View v, int position) {
                BrowserActivity.this.web_view.reload();
            }
        });
        title_bar.setTitle(getIntent().getStringExtra(TITLE));
        // 加载进度显示
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                loading_indicator.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                title_bar.setTitle(view.getTitle());
            }
        });
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url); // 用vew去加载网页，代替去呼叫浏览器
                return true; // return true是通知操作已做了，否则系统会跑去呼叫浏览器
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                updateHistoryUI();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String content = getIntent().getStringExtra(CONTENT);
        if (!TextUtils.isEmpty(content)) {
            if (content.startsWith("http")) {
                web_view.loadUrl(content);
            } else {
                web_view.loadDataWithBaseURL(
                    getIntent().getStringExtra(BASE_URL),
                    content, null, "UTF-8", null);
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.pre_arrow) {
                if (BrowserActivity.this.web_view.canGoBack()) BrowserActivity.this.web_view.goBack();
            } else if (id == R.id.next_arrow) {
                if (BrowserActivity.this.web_view.canGoForward()) BrowserActivity.this.web_view.goForward();
            } else if (id == R.id.close_button) {
                BrowserActivity.this.finish();
            }
        }
    };

    private void updateHistoryUI() {
        this.pre_arrow.setEnabled(this.web_view.canGoBack());
        this.next_arrow.setEnabled(this.web_view.canGoForward());
    }
}
