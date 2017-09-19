package com.river.app.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.river.app.widget.HorizontalScrollMenu;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public abstract class BaseAdapter {
  private HorizontalScrollMenu mHorizontalScrollMenu;
  public abstract List<String> getMenuItems();
  public abstract List<Fragment> getContentViews();
  public abstract FragmentManager getFm();
  public abstract void onPageChanged(int position,boolean visitStatus);

  public void setHorizontalScrollMenu(HorizontalScrollMenu horizontalScrollMenu)
  {
    mHorizontalScrollMenu=horizontalScrollMenu;
  }

  public void notifyDataSetChanged()
  {
    mHorizontalScrollMenu.notifyDataSetChanged(this);
  }
}
