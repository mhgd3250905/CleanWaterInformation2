package DataBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/11/26.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/26$ 14:24$.
*/
public class JsonBaijia extends BmobObject {
    public String spiderTime;
    public String jsonData;
    public long num;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getSpiderTime() {
        return spiderTime;
    }

    public void setSpiderTime(String spiderTime) {
        this.spiderTime = spiderTime;
    }
}
