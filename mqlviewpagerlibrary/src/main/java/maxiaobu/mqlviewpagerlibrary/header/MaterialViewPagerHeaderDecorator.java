package maxiaobu.mqlviewpagerlibrary.header;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import maxiaobu.mqlviewpagerlibrary.MaterialViewPagerAnimator;
import maxiaobu.mqlviewpagerlibrary.MaterialViewPagerHelper;
import maxiaobu.mqlviewpagerlibrary.Utils;


/**
    *Created by 马小布 on 2017/1/1.
    *introduction: 真他娘的不知道说点啥
    *email:maxiaobu1999@163.com
    *功能：
    *伪码：
    *待完成：
*/
public class MaterialViewPagerHeaderDecorator extends RecyclerView.ItemDecoration {

    boolean registered = false;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
        final Context context = recyclerView.getContext();

        if(!registered) {
            MaterialViewPagerHelper.registerRecyclerView(context, recyclerView);
            registered = true;
        }

        int headerCells = 1;

        //don't work with stagged
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            headerCells = gridLayoutManager.getSpanCount();
        }

        MaterialViewPagerAnimator animator = MaterialViewPagerHelper.getAnimator(context);
        if (animator != null) {
            if (holder.getAdapterPosition() < headerCells) {
                outRect.top = Math.round(Utils.dpToPx(animator.getHeaderHeight() + 10, context));
            }
        }
    }
}
