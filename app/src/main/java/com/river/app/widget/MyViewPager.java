package com.river.app.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/3/30.
 */

public class MyViewPager extends ViewPager {
  private boolean mSwiped = true; // 是否可滑动
  public MyViewPager(Context context) {
    super(context);
  }

  public MyViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public void setSwiped(boolean swiped)
  {
    mSwiped = swiped;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev)
  {
    // TODO Auto-generated method stub
    if (mSwiped)
    {
      return super.onInterceptTouchEvent(ev);
    } else
    {
      return false;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    // TODO Auto-generated method stub
    if ((mSwiped))
    {
      return super.onTouchEvent(event);
    } else
    {
      return true;
    }
  }
}
