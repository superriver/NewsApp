package com.river.app.base;

import android.util.Log;
import com.river.app.callback.RequestCallBack;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Administrator on 2017/4/10.
 */

public class BaseSubscriber<T> extends DisposableObserver<T> {
  private RequestCallBack<T> mRequestCallback;

  public BaseSubscriber(RequestCallBack<T> requestCallback) {
    mRequestCallback = requestCallback;
  }

  @Override protected void onStart() {
    super.onStart();
    if(mRequestCallback!=null){
      mRequestCallback.requestBefore();
    }
  }

  @Override public void onNext(T value) {
    Log.d("huang", "onNext" + value.toString());
    mRequestCallback.requestSuccess(value);
  }

  @Override public void onError(Throwable e) {
    Log.d("huang", "onError" + e.toString());
    mRequestCallback.requestError(e.toString());
  }

  @Override public void onComplete() {
      mRequestCallback.requestComplete();
  }
}
