package skkk.cleanwaterinformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.List;

import DataBean.HuXiuContentBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class WebActivity extends AppCompatActivity {


    @Bind(R.id.tb_web)
    Toolbar mTbWeb;
    @Bind(R.id.wv_web)
    WebView mWvWeb;

    private String url=null;
    private String title=null;
    private String content=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        title=intent.getStringExtra("title");
        initUI();
        initData();
    }

    private void initData() {
        getContentHtml(url);
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext(url);
//                subscriber.onCompleted();
//            }
//        });
//
//        Subscriber<String> subscriber=new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                LogUtils.Log("完成");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                LogUtils.Log(s);
//                mWvWeb.loadDataWithBaseURL(null,s, "text/html", "utf-8", null);
//            }
//        };

//        observable
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return getContentHtml(s);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
    }


    public void getContentHtml(String key){
        BmobQuery<HuXiuContentBean> query = new BmobQuery<HuXiuContentBean>();
        query.addWhereEqualTo("key",key)
                .findObjects(new FindListener<HuXiuContentBean>() {
                    @Override
                    public void done(List<HuXiuContentBean> object, BmobException e) {
                        if (e == null&&object.size()==1) {
                            LogUtils.Log("查询到的内容"+object.get(0).getContent());
                            content = object.get(0).getContent();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWvWeb.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
                                }
                            });
                        } else {
                            LogUtils.Log("查询内容失败");
                        }
                    }
                });
        LogUtils.Log("返回的内容"+content);
    }

    private void initUI() {
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        mTbWeb.setTitle(title);
        setSupportActionBar(mTbWeb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTbWeb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        mWvWeb.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
        mWvWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }
        });
//        mWvWeb.setWebViewClient(new WebViewClient() {
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //handler.cancel(); // Android默认的处理方式
//                handler.proceed();  // 接受所有网站的证书
//                //handleMessage(Message msg); // 进行其他处理
//            }
//        });

        setSettings(mWvWeb.getSettings());

    }



    //设置websetting
    private void setSettings(WebSettings setting) {
        setting.setDefaultTextEncodingName("utf-8");//设置webview的默认编码格式
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);
        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 全屏显示
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
//        if (Build.VERSION.SDK_INT >= 21) {
//            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==event.KEYCODE_BACK&&mWvWeb.canGoBack()){
            mWvWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
