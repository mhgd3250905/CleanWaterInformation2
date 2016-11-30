package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import skkk.cleanwaterinformation.R;

/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/21$ 21:26$.
*/
public class HuxiuViewHolder extends RecyclerViewHolderBase{
    @Bind(R.id.iv_item)
    public ImageView ivItem;
    @Bind(R.id.tv_item)
    public TextView tvItem;

    public HuxiuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
