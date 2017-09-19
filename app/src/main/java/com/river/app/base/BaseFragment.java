package com.river.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.river.app.App;
import com.river.app.di.component.DaggerFragmentComponent;
import com.river.app.di.component.FragmentComponent;
import com.river.app.di.module.FragmentModule;
import com.socks.library.KLog;
import javax.inject.Inject;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{
  protected BaseActivity mActivity;
  protected View mFragmentRootView;
  public Unbinder unbinder;
  protected SparseArray<View> mViews;
  @Inject protected T mPresenter;
  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.mActivity = (BaseActivity) context;
    mViews=new SparseArray<>();
  }
  protected abstract void initInject() ;
  protected FragmentComponent getFragmentComponent(){
    return DaggerFragmentComponent.builder()
        .appComponent(App.getAppComponent())
        .fragmentModule(getFragmentModule())
        .build();
  }

  protected FragmentModule getFragmentModule(){
    return new FragmentModule(this);
  }

 // protected abstract void setupFragmentComponent(AppComponent appComponent);
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    initInject();
    KLog.d("huang","onCreateView "+mPresenter);
    mPresenter.attachView(this);
    mFragmentRootView=mViews.get(getLayoutId());
    if(mFragmentRootView==null) {
      mFragmentRootView = inflater.inflate(getLayoutId(), container, false);
      mViews.put(getLayoutId(),mFragmentRootView);
      unbinder = ButterKnife.bind(this, mFragmentRootView);
    }
    //setupFragmentComponent(AppApplication.getInstance().getAppComponent());
    return mFragmentRootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
      initData();
  }


  //protected abstract void initRecycleView();

  protected abstract void initData();
  protected abstract int getLayoutId();

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override public void hideProgress() {

  }

  @Override public void showProgress() {

  }
}
