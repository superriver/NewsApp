package com.river.app.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.river.app.R;
import com.river.app.base.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

import static com.river.app.R.id.rg_items;
import static com.river.app.R.id.vp_content;

/**
 * Created by Administrator on 2017/3/27.
 */

public class HorizontalScrollMenu extends LinearLayout {
  private MyViewPager mVpContent;
  private BaseAdapter mAdapter;
  private RadioGroup mRadioGroup;
  private ColorStateList mColors;
  private Context mContext;
  private int mBackgroundResId;
  private int mPaddingLeft = 40;
  private int mPaddingTop = 20;
  private int mPaddingRight = 40;
  private int mPaddingBottom = 20;
  private HorizontalScrollView mScrollView;
  private List<String> mItems; // 菜单名
  private boolean[] mVisitStatus; // 菜单访问状态
  private List<RadioButton> rb_items = new ArrayList<>();
  private List<Fragment> mPagers; // 内容页

  public HorizontalScrollMenu(Context context) {
    this(context, null);
  }

  public HorizontalScrollMenu(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  @TargetApi(Build.VERSION_CODES.N)
  public HorizontalScrollMenu(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, this, true);
    mRadioGroup = (RadioGroup) v.findViewById(rg_items);
    mScrollView = (HorizontalScrollView) v.findViewById(R.id.scrollView);
    mBackgroundResId = R.drawable.bg_rb_checked;
    mVpContent= (MyViewPager) v.findViewById(vp_content);
    mColors=getResources().getColorStateList(R.color.selector_menu_item_text);
  }

  public void setAdapter(BaseAdapter adapter) {
    if (null != adapter) {
      adapter.setHorizontalScrollMenu(this);
      mAdapter = adapter;
      initView(adapter);
    }

  }

  private void initView(BaseAdapter adapter) {
    if (null == adapter) {
      return;
    }
    mItems = mAdapter.getMenuItems();
    mVisitStatus = new boolean[mItems.size()];
    initMenuItems(mItems);
    mPagers=mAdapter.getContentViews();
    initContentView(mPagers);
  }

  private void initContentView(List<Fragment> fragments) {
    if (null == fragments || 0 == fragments.size())
    {
      return;
    }
    mVpContent.setAdapter(new MyViewPagerAdapter(mAdapter.getFm(),mPagers));
    mVpContent.addOnPageChangeListener(mPageListener);
  }

  /**
   * 初始化菜单项
   */
  private void initMenuItems(List<String> items) {
    if (null != items && 0 != items.size()) {
      mRadioGroup.setOnCheckedChangeListener(mItemListener);
      mRadioGroup.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
      for (String item : items) {
        RadioButton rb_item =
            (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);
        rb_item.setTextColor(mColors);
        rb_item.setText(item);

        rb_item.setGravity(Gravity.CENTER);
        rb_item.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        mRadioGroup.addView(rb_item);
        rb_items.add(rb_item);
      }
      rb_items.get(0).setChecked(true);
    }
  }

  /**
   * 设置菜单项状态背景
   */
  public void setCheckedBackground(int resId) {
    mBackgroundResId = resId;
  }

  private boolean mSwiped;
  /**
   * 菜单项切换监听器
   */
  private RadioGroup.OnCheckedChangeListener mItemListener =
      new RadioGroup.OnCheckedChangeListener() {
        /**
         * <p>Called when the checked radio button has changed. When the
         * selection is cleared, checkedId is -1.</p>
         *
         * @param group the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
          RadioButton btn = (RadioButton) group.findViewById(checkedId);
          setMenuItemsNullBackground();
          btn.setBackgroundResource(mBackgroundResId);
          btn.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
          int position = 0;
          for (int i = 0; i < rb_items.size(); i++) {
            if (rb_items.get(i) == btn) {
              position = i;
            }
          }
          mVpContent.setCurrentItem(position,mSwiped);
          moveItemToCenter(btn);
          mAdapter.onPageChanged(position,mVisitStatus[position]);
          mVisitStatus[position] = true;
        }
      };

  /**
   * 内容页切换监听器
   */

  public ViewPager.OnPageChangeListener mPageListener=new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageSelected(int position) {
        rb_items.get(position).setChecked(true);
    }

    @Override public void onPageScrollStateChanged(int state) {

    }
  };
  /**
   * 将菜单项尽量移至中央位置
   */
  private void moveItemToCenter(RadioButton rb) {
    DisplayMetrics dm = getResources().getDisplayMetrics();
    int screenWidth = dm.widthPixels;
    int[] locations = new int[2];
    rb.getLocationInWindow(locations);
    int rbWidth = rb.getWidth();
    mScrollView.smoothScrollBy((locations[0] + rbWidth / 2 - screenWidth / 2), 0);
  }

  private void setMenuItemsNullBackground() {
    if (null != mRadioGroup) {
      for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
        View v = mRadioGroup.getChildAt(i);
        v.setBackgroundResource(android.R.color.transparent);
      }
    }
  }

  /**
   * 当数据集改变通知视图重绘
   */
  public void notifyDataSetChanged(BaseAdapter adapter) {
    mRadioGroup.removeAllViews();
    rb_items.clear();
    initView(adapter);
  }

  /**
   * 视图页的适配器
   *
   * @author Administrator
   */
  static class MyViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> pagers;
    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> pagers) {
      super(fm);
      this.pagers=pagers;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override public Fragment getItem(int position) {
      return pagers.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override public int getCount() {
      return pagers.size();
    }


  }
  public void setSwiped(boolean swiped)
  {
    mSwiped = swiped;
    mVpContent.setSwiped(swiped);
  }

}
