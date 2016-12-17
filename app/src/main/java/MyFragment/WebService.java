package MyFragment;

import DataBean.HXContentBean;
import DataBean.HXGsonBean;
import DataBean.PandaGsonBean;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/12/2.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/12/2$ 21:48$.
*/
public interface WebService {
    /* @描述 V2EX */
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("api/topics/hot.json")
    Observable<String> getV2EXData();


    /*熊猫TV爬虫*/
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("ajax_get_live_list_by_cate") //pagenum //__plat  lol/1/10/h5
    Observable<PandaGsonBean> getPandaList(@Query("cate")String cate, @Query("pageno")String pageno,
                                           @Query("pagenum")String pagenum,@Query("__plat") String __plat);


//    https://api.bmob.cn/1/classes/TableName
    @Headers({
            "X-Bmob-Application-Id: 9e16e39fa5374f446e5c928da0f83d62",
            "X-Bmob-REST-API-Key: 42db163cd4c45884279b914e1c2a4e75",
            "Content-Type: application/json"
    })
    @GET("1/classes/{tableName}")
    Observable<HXGsonBean> getHXGsonData(@Path("tableName")String tableName,@Query("limit")String limit);


//    https://api.bmob.cn/1/classes/GameScore/e1kXT22L
    @Headers({
            "X-Bmob-Application-Id: 9e16e39fa5374f446e5c928da0f83d62",
            "X-Bmob-REST-API-Key: 42db163cd4c45884279b914e1c2a4e75",
            "Content-Type: application/json"
    })
    @GET("1/classes/{tableName}/{id}")
    Observable<HXContentBean> getContentData(@Path("tableName")String tableName, @Path("id")String id);
}