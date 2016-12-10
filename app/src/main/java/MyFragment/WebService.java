package MyFragment;

import DataBean.PandaGsonBean;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
interface WebService {
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
}