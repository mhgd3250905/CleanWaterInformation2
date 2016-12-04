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

import Adapter.RecyclerViewBaseAdapter;
import Adapter.V2exAdapter;
import DataBean.V2EXBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
public class V2EXFragment extends Fragment {
    @Bind(R.id.rv_huxiu)
    PullLoadMoreRecyclerView rvHuxiu;

    private String V2EX_URL = "https://www.v2ex.com/";

    private List<V2EXBean> mDataList = new ArrayList<V2EXBean>();
    private V2exAdapter adapter;
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
        adapter = new V2exAdapter(getContext(),mDataList);
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
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(V2EX_URL)
                .build();

        service = retrofit.create(WebService.class);

        getRxDate();
    }

    public void getRxDate(){
        service.getV2EXData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String,List<V2EXBean>>() {
                    @Override
                    public List<V2EXBean> call(String s) {
                        return mapV2EXBean(s);
                    }
                })
                .subscribe(new Subscriber<List<V2EXBean>>() {
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
                    public void onNext(List<V2EXBean> v2EXBeanList) {
                        LogUtils.Log(v2EXBeanList.size()+"");
                        adapter.replace(v2EXBeanList);

                    }
                });
    }

    public List<V2EXBean> mapV2EXBean(String jsonData){
        List<V2EXBean> needBeanList=new ArrayList<V2EXBean>();
        JSONArray arr = null;
        try {
            arr = new JSONArray(jsonData);
            for (int i = 0; i < arr.length(); i++) {
                V2EXBean v2EXBean =new V2EXBean();
                JSONObject temp = (JSONObject) arr.get(i);
                v2EXBean.setTitle(temp.getString("title"));
                v2EXBean.setContentURL(temp.getString("url"));
                needBeanList.add(v2EXBean);
            }
            return needBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
            return needBeanList;
        }
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
                getRxDate();
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
                itemIntent.putExtra("type", 3);
                itemIntent.setClass(getContext(), WebActivity.class);
                startActivity(itemIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
