package DataBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/21$ 21:29$.
*/
public class BaseBean extends BmobObject {
    public String title;
    public String contentURL;
    public String imgSrc;


    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
