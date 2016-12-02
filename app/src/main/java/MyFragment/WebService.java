package MyFragment;

import DataBean.V2EXGsonBean;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    @GET("https://www.v2ex.com/api/topics/hot.json")
    Observable<V2EXGsonBean> getV2EXData();



}
