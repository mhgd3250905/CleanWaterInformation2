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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.PandaAdapter;
import Adapter.RecyclerViewBaseAdapter;
import Adapter.V2exAdapter;
import DataBean.PandaBean;
import DataBean.PandaGsonBean;
import DataBean.V2EXBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
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
public class PandaFragment extends Fragment {
    @Bind(R.id.rv_huxiu)
    PullLoadMoreRecyclerView rvHuxiu;

    private String PANDA_URL = "http://api.m.panda.tv/";

    private List<PandaBean> mDataList = new ArrayList<PandaBean>();
    private PandaAdapter adapter;
    private WebService service;
    private Retrofit retrofit;


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
        adapter = new PandaAdapter(getContext(),mDataList);
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
                .baseUrl(PANDA_URL)
                .build();

        service = retrofit.create(WebService.class);

        getPandaData();
    }

    public void getPandaData(){

        service.getPandaList("lol",1+"",10+"","h5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PandaGsonBean,List<PandaBean>>() {
                    @Override
                    public List<PandaBean> call(PandaGsonBean pandaGsonBean) {
                        return mapPandaBean(pandaGsonBean);
                    }
                })
                .subscribe(new Subscriber<List<PandaBean>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.Log("completed");
                        if (rvHuxiu.isRefresh()) {
                            rvHuxiu.setPullLoadMoreCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.Log("Error  "+e.toString());
                    }

                    @Override
                    public void onNext(List<PandaBean> v2EXBeanList) {
                        LogUtils.Log(v2EXBeanList.size()+"");
                        adapter.replace(v2EXBeanList);

                    }
                });
    }

    public List<PandaBean> mapPandaBean(PandaGsonBean pandaGsonBean){
        List<PandaGsonBean.DataBean.ItemsBean> items = pandaGsonBean.getData().getItems();
        List<PandaBean> needList=new ArrayList<PandaBean>();
        for (int i = 0; i < items.size(); i++) {
            PandaGsonBean.DataBean.ItemsBean itemsBean = items.get(i);
            PandaBean need=new PandaBean();
            need.setTitle(itemsBean.getName());
            need.setContentURL("https://m.panda.tv/room.html?roomid="+itemsBean.getId());
            need.setImgSrc(itemsBean.getPictures().getImg());
            needList.add(need);
        }
        return needList;
    }


    /*
    ***************************************************
    * @方法 设置监听事件
    * @参数
    * @返回值
    */
    private void initEvent() {
        rvHuxiu.setPushRefreshEnable(false);
        rvHuxiu.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getPandaData();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "上拉加载更多", Toast.LENGTH_SHORT).show();
                initData();
            }
        });

        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent itemIntent = new Intent();
                itemIntent.putExtra("url", mDataList.get(position).getContentURL());
                itemIntent.putExtra("title", mDataList.get(position).getTitle());
                itemIntent.putExtra("type", 4);
                itemIntent.setClass(getContext(), WebActivity.class);
                startActivity(itemIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
