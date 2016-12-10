package MyFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import Adapter.FenghuangAdapter;
import Adapter.ITHomeAdapter;
import Adapter.RecyclerViewBaseAdapter;
import DataBean.FenghuangBean;
import DataBean.FenghuangGson;
import DataBean.ITHomeBean;
import DataBean.ITHomeGson;
import DataBean.JsonFenghuang;
import DataBean.JsonITHome;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import skkk.cleanwaterinformation.R;
import skkk.cleanwaterinformation.WebActivity;

/**
 * Created by admin on 2016/11/28.
 */
/*
*
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/28$ 22:16$.
*/
public class FenghuangFragment extends Fragment{
    @Bind(R.id.rv_huxiu)
    PullLoadMoreRecyclerView rvHuxiu;

    //private String ITHOME_URL="http://wap.ithome.com/";

    private int page=0;
    private List<FenghuangBean> mDataList=new ArrayList<FenghuangBean>();
    private FenghuangAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bmob.initialize(getContext(), "9e16e39fa5374f446e5c928da0f83d62");
        View fenghuangView=inflater.inflate(R.layout.fragment_huxiu,container,false);
        ButterKnife.bind(this,fenghuangView);
        initUI();
        initData();
        initEvent();

        return fenghuangView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*
        ***************************************************
        * @方法 初始化UI
        * @参数
        * @返回值
        */
    private void initUI() {
        /* @描述 设置Adapter */
        adapter = new FenghuangAdapter(getContext(),mDataList);
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
        BmobQuery<JsonFenghuang> query = new BmobQuery<JsonFenghuang>();
        //查询playerName叫“比目”的数据
        query.setSkip(page*1);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        query.order("-num");
        //执行查询方法
        query.findObjects(new FindListener<JsonFenghuang>() {
            @Override
            public void done(List<JsonFenghuang> object, BmobException e) {
                if(e==null){
                    FenghuangGson fenghuangGson = new Gson().fromJson(object.get(0).getJsonData(), FenghuangGson.class);
                    List<FenghuangBean> data = fenghuangGson.getData();

                    //LogUtils.Log("查询成功：共"+data.size()+"条数据。");

                    for (int i = 0; i < data.size(); i++) {
                        mDataList.add(data.get(i));
                    }

                    adapter.append(data);

                    if (rvHuxiu.isRefresh() || rvHuxiu.isLoadMore()) {
                        rvHuxiu.setPullLoadMoreCompleted();
                    }


                } else {


                    if (rvHuxiu.isRefresh() || rvHuxiu.isLoadMore()) {
                        rvHuxiu.setPullLoadMoreCompleted();
                        Toast.makeText(getContext(), "获取失败，请检查网络...", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    /*
    ***************************************************
    * @方法 将map中获取的josnString转换为List<ITHomeBean>
    * @参数 String
    * @返回值 List<ITHomeBean>
    */
//    private List<ITHomeBean> getItHomeList(String s) {
//        List<ITHomeBean> needList=new ArrayList<ITHomeBean>();
//        Document doc = Jsoup.parse(s);
//        Elements li_eles = doc.select("li");
//        for (Element ele:li_eles){
//            ITHomeBean itHome=new ITHomeBean();
//            itHome.setTitle(ele.select("span.title").text());
//            itHome.setContentURL(ITHOME_URL+ele.select("a").attr("href"));
//            needList.add(itHome);
//        }
//        return needList;
//    }


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
                itemIntent.putExtra("type",3);
                itemIntent.setClass(getContext(), WebActivity.class);
                startActivity(itemIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
