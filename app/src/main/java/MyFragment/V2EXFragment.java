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

import Adapter.RecyclerViewBaseAdapter;
import Adapter.V2exAdapter;
import DataBean.V2EXBean;
import DataBean.V2EXGsonBean;
import butterknife.Bind;
import butterknife.ButterKnife;
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
public class V2EXFragment extends Fragment {
    @Bind(R.id.rv_huxiu)
    PullLoadMoreRecyclerView rvHuxiu;

    private String V2EX_URL = "https://www.v2ex.com/api/topics/hot.json";

    private int page = 0;
    private List<V2EXBean> mDataList = new ArrayList<V2EXBean>();
    private V2exAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v2exView = inflater.inflate(R.layout.fragment_huxiu, container, false);
        ButterKnife.bind(this, v2exView);
        initUI();
        initData();
        initEvent();

        return v2exView;
    }


    /*
    ***************************************************
    * @方法 初始化UI
    * @参数
    * @返回值
    */
    private void initUI() {
        /* @描述 设置Adapter */
        adapter = new V2exAdapter(getContext(), mDataList);
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
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(V2EX_URL)
                .build();

        WebService service = retrofit.create(WebService.class);

        service.getV2EXData()
                .observeOn(Schedulers.io())
                .map(new Func1<V2EXGsonBean, V2EXBean>() {
                    @Override
                    public V2EXBean call(V2EXGsonBean v2EXGsonBean) {
                        return mapV2EXBean(v2EXGsonBean);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<V2EXBean>() {
                    @Override
                    public void onCompleted() {
                        adapter.append(mDataList);
                        if (rvHuxiu.isLoadMore()) {
                            rvHuxiu.setPullLoadMoreCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(V2EXBean v2EXBean) {
                        mDataList.add(v2EXBean);
                    }
                });
    }

    public V2EXBean mapV2EXBean(V2EXGsonBean v2EXGsonBean){
        V2EXBean needBean=new V2EXBean();
        needBean.setTitle(v2EXGsonBean.getTitle());
        needBean.setContentURL(v2EXGsonBean.getUrl());
        return needBean;
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
                Intent itemIntent = new Intent();
                itemIntent.putExtra("url", mDataList.get(position).getContentURL());
                itemIntent.putExtra("title", mDataList.get(position).getTitle());
                itemIntent.putExtra("type", 0);
                itemIntent.setClass(getContext(), WebActivity.class);
                startActivity(itemIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
