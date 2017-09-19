package com.river.app.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/14.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
  //集合类，layout里包含的View,以view的id作为key，value是view对象
  protected SparseArray<View> mViews;
  private Context mContext;
  public BaseViewHolder(View itemView,Context ct) {
    super(itemView);
    mContext=ct;
    mViews= new SparseArray<>();
  }
  private <T extends View> T findViewById(int viewId) {
    View view = mViews.get(viewId);
    if(view==null){
      view=itemView.findViewById(viewId);
      mViews.put(viewId,view);
    }
    return (T) view;
  }

  public View getView(int viewId) {
    return findViewById(viewId);
  }

  public TextView getTextView(int viewId){
    return  (TextView) getView(viewId);
  }

  public ImageView getImageView(int viewId) {
    return (ImageView) getView(viewId);
  }
}
