package Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import DataBean.BaijiaBean;
import ViewHolder.HuxiuViewHolder;
import ViewHolder.RecyclerViewHolderBase;
import skkk.cleanwaterinformation.R;

/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/21$ 21:24$.
*/
public class BaijiaAdapter extends RecyclerViewBaseAdapter<BaijiaBean>{
    private Context context;
    private LayoutInflater inflater;

    public BaijiaAdapter(Context context, List<BaijiaBean> mItemDataList) {
        super(mItemDataList);
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int i, List<BaijiaBean> mItemDataList) {
        //向下转型为子类
        HuxiuViewHolder holder= (HuxiuViewHolder) viewHolder;
        holder.tvItem.setText(mItemDataList.get(i).getTitle());
        if (TextUtils.isEmpty(mItemDataList.get(i).getImgSrc())) {
            holder.ivItem.setVisibility(View.GONE);
        }else {
            Glide.with(context)
                    .load(mItemDataList.get(i).getImgSrc())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivItem);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_information, viewGroup,false);
        return view;
    }

    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new HuxiuViewHolder(view);
    }
}
