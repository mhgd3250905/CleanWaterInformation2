package skkk.cleanwaterinformation;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import DataBean.HXContentBean;
import MyFragment.WebService;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class WebActivity extends AppCompatActivity {


    @Bind(R.id.tb_web)
    Toolbar mTbWeb;
    @Bind(R.id.wv_web)
    WebView mWvWeb;

    private String id=null;
    private String className=null;
    private String title=null;
    private int type;
    private Retrofit retrofit;
    private WebService service;

    private static final String BASE_HX_URL="https://api.bmob.cn/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        className=intent.getStringExtra("className");
        title=intent.getStringExtra("title");
        initUI();
        initData();
    }

    private void initData() {
        getContentHtml(id,className);
    }

    public void getContentHtml(String id,String className){
        //新的配置
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(BASE_HX_URL)
                .build();

        service = retrofit.create(WebService.class);

        service.getContentData(className,id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HXContentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HXContentBean hxContentBean) {
                        mWvWeb.loadDataWithBaseURL(null,hxContentBean.getContentHtml(), "text/html", "utf-8", null);
                    }
                });
//        switch (type){
//            case 0:
//                LogUtils.Log("获取type：   "+type);
//                BmobQuery<HuXiuContentBean> huxiuQuery = new BmobQuery<HuXiuContentBean>();
//                huxiuQuery.addWhereEqualTo("key",key)
//                        .findObjects(new FindListener<HuXiuContentBean>() {
//                            @Override
//                            public void done(List<HuXiuContentBean> object, BmobException e) {
//                                if (e == null&&object.size()==1) {
//                                    //LogUtils.Log("查询到的内容"+object.get(0).getContent());
//                                    content = object.get(0).getContent();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mWvWeb.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//                                        }
//                                    });
//                                } else {
//                                    LogUtils.Log("查询内容失败");
//                                }
//                            }
//                        });
//                break;
//            case 1:
//                LogUtils.Log("获取type：   "+type);
//                BmobQuery<ITHomeContentBean> itHomeQuery = new BmobQuery<ITHomeContentBean>();
//                itHomeQuery.addWhereEqualTo("key",key)
//                        .findObjects(new FindListener<ITHomeContentBean>() {
//                            @Override
//                            public void done(List<ITHomeContentBean> object, BmobException e) {
//                                if (e == null&&object.size()==1) {
//                                    //LogUtils.Log("查询到的内容"+object.get(0).getContent());
//                                    content = object.get(0).getContent();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mWvWeb.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//                                        }
//                                    });
//                                } else {
//                                    LogUtils.Log("查询内容失败");
//                                }
//                            }
//                        });
//                break;
//            case 2:
//                LogUtils.Log("获取type：   "+type);
//                BmobQuery<BaijiaContentBean> baijiaQuery = new BmobQuery<BaijiaContentBean>();
//                baijiaQuery.addWhereEqualTo("key",key)
//                        .findObjects(new FindListener<BaijiaContentBean>() {
//                            @Override
//                            public void done(List<BaijiaContentBean> object, BmobException e) {
//                                if (e == null&&object.size()==1) {
//                                    //LogUtils.Log("查询到的内容"+object.get(0).getContent());
//                                    content = object.get(0).getContent();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mWvWeb.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//                                        }
//                                    });
//                                } else {
//                                    LogUtils.Log("查询内容失败");
//                                }
//                            }
//                        });
//                break;
//
//            case 3:
//                LogUtils.Log("获取type：   "+type);
//                BmobQuery<FenghuangContentBean> fenghuangQuery = new BmobQuery<FenghuangContentBean>();
//                fenghuangQuery.addWhereEqualTo("key",key)
//                        .findObjects(new FindListener<FenghuangContentBean>() {
//                            @Override
//                            public void done(List<FenghuangContentBean> object, BmobException e) {
//                                if (e == null&&object.size()==1) {
//                                    //LogUtils.Log("查询到的内容"+object.get(0).getContent());
//                                    content = object.get(0).getContent();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mWvWeb.loadDataWithBaseURL(null,content, "text/html", "utf-8", null);
//                                        }
//                                    });
//                                } else {
//                                    LogUtils.Log("查询内容失败");
//                                }
//                            }
//                        });
//                break;
//
//            case 4:
//                LogUtils.Log("获取type：   "+type);
//                mWvWeb.loadUrl(url);
//                break;
//
//
//        }

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

        mWvWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWvWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }
        });
        mWvWeb.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });

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
