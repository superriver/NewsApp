package com.river.app.base;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface BasePresenter<T extends BaseView> {
 // void onDestroy();
 void start();
 void attachView(T view);
 void detachView();
}
