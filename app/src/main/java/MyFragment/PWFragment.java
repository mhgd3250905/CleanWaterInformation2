package MyFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.HuxiuAdapter;
import Adapter.RecyclerViewBaseAdapter;
import DataBean.HXGsonBean;
import DataBean.HuXiuBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import skkk.cleanwaterinformation.R;
import skkk.cleanwaterinformation.WebActivity;

/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：展示虎嗅网的Fragment
* 作    者：ksheng
* 时    间：2016/11/21$ 21:16$.
*/
public class PWFragment extends Fragment {
    @Bind(R.id.rv_huxiu)
    PullLoadMoreRecyclerView rvHuxiu;


    private int page=0;
    private List<HuXiuBean> mDataList=new ArrayList<HuXiuBean>();
    private HuxiuAdapter adapter;
    private Retrofit retrofit;

    private static final String BASE_HX_URL="https://api.bmob.cn/";
    private WebService service;
    private Object HXData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bmob.initialize(getContext(), "9e16e39fa5374f446e5c928da0f83d62");
        View huxiuView=inflater.inflate(R.layout.fragment_huxiu,container,false);
        ButterKnife.bind(this,huxiuView);
        initUI();
        initData();
        initEvent();

        return huxiuView;
    }
    

    /*
    ***************************************************
    * @方法 初始化UI
    * @参数
    * @返回值
    */
    private void initUI() {
        /* @描述 设置Adapter */
        adapter = new HuxiuAdapter(getContext(),mDataList);
        /* @描述 布局 */
        rvHuxiu.setLinearLayout();
        /* @描述 设置间距 */
        SpacesItemDecoration mDecoration = new SpacesItemDecoration(3);
        /* @描述 添加间距 */
        rvHuxiu.addItemDecoration(mDecoration);
        /* @描述 设置基本动画 */
        rvHuxiu.setItemAnimator(new DefaultItemAnimator());
        /* @描述 rvNoteList */
        rvHuxiu.setAdapter(adapter);
    }

    /*
    ***************************************************
    * @方法 获取数据
    * @参数
    * @返回值
    */
    private void initData() {
        //新的配置
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(BASE_HX_URL)
                .build();

        service = retrofit.create(WebService.class);

        insertHXData(service);

    }

    
    /*
    ***************************************************
    * @方法 设置监听事件
    * @参数
    * @返回值
    */
    private void initEvent() {
        rvHuxiu.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "上拉加载更多", Toast.LENGTH_SHORT).show();
                page++;
                initData();
            }
        });

        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent itemIntent=new Intent();
                itemIntent.putExtra("url",mDataList.get(position).getContentURL());
                itemIntent.putExtra("title",mDataList.get(position).getTitle());
                itemIntent.setClass(getContext(), WebActivity.class);
                startActivity(itemIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public void insertHXData(WebService service) {
        service.getHXGsonData("PWBean","20")
                .subscribeOn(Schedulers.io())
                .map(new Func1<HXGsonBean, List<HuXiuBean>>() {
                    @Override
                    public List<HuXiuBean> call(HXGsonBean hxGsonBean) {

                        List<HXGsonBean.ResultsBean> results = hxGsonBean.getResults();
                        List<HuXiuBean> responses=new ArrayList<HuXiuBean>();
                        for (HXGsonBean.ResultsBean resultsBean:results){
                            HuXiuBean huXiuBean=new HuXiuBean();
                            huXiuBean.setTitle(resultsBean.getTitle());
                            huXiuBean.setImgSrc(resultsBean.getImgUrl());
                            huXiuBean.setContentURL(resultsBean.getContentUrl());
                            responses.add(huXiuBean);
                        }
                        return responses;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HuXiuBean>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.Log("completed");
                        if (rvHuxiu.isRefresh()) {
                            rvHuxiu.setPullLoadMoreCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<HuXiuBean> huXiuList) {
                        LogUtils.Log(huXiuList.size()+"");
                        adapter.append(huXiuList);
                    }
                });
    }
}
