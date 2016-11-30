package DataBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/11/23.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/23$ 23:23$.
*/
public class ITHomeContentBean extends BmobObject {
    public String key;
    public String content;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
