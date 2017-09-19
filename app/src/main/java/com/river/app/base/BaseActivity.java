package com.river.app.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.river.app.App;
import com.river.app.R;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected Unbinder mUnbinder;
  // protected abstract void initView();
  protected int mToolbarTitle;
  /**
   * 将代理类通用行为抽出来
   */
  //protected T mPresenter;
  // 跳转的类
  protected Class mClass;

  //protected abstract void setupActivityComponent(AppComponent appComponent);
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    App.getInstance().addActivity(this);
    mUnbinder = ButterKnife.bind(this);
    // ActivityManager.getInstance().addActivity(this);
    //避免重复添加Fragment
    //initView();
    ///setupActivityComponent(AppApplication.getInstance().getAppComponent());
    //initToolBar();
  }
  //protected abstract void initInject() ;
  //protected ActivityComponent getActivityComponent(){
  //  return  DaggerActivityComponent.builder()
  //      .appComponent(App.getAppComponent())
  //      .activityModule(getActivityModule())
  //      .build();
  //}


  //protected ActivityModule getActivityModule(){
  //  return new ActivityModule(this);
  //}

  public void setToolBar(String title,int resId) {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      toolbar.setContentInsetStartWithNavigation(0);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
      getSupportActionBar().setTitle(title);
      if(resId!=-1){
        setToolbarIndicator(resId);
      }
    }
  }

  protected void setToolbarIndicator(int resId) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setHomeAsUpIndicator(resId);
    }
  }

  protected abstract int getLayoutId();

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == keyCode) {
      if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
        finish();
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override public void onBackPressed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      super.onBackPressed();
    } else {
      finish();
      overridePendingTransition(0, 0);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    //ActivityManager.getInstance().finishActivity(this);
    if (null != mUnbinder) {
      mUnbinder.unbind();
    }
  }
  @Override public boolean onOptionsItemSelected(MenuItem item) {
     if (item.getItemId() == android.R.id.home) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        finishAfterTransition();
      } else {
        finish();
      }
    } return true;
  }

}