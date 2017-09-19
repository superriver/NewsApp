package com.river.app.base;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/10.
 */

public class BaseSchedulerTransformer<T> implements ObservableTransformer<T,T> {
  /**
   * Applies a function to the upstream Observable and returns an ObservableSource with
   * optionally different element type.
   *
   * @param upstream the upstream Observable instance
   * @return the transformed ObservableSource instance
   */
  @Override public ObservableSource<T> apply(Observable<T> upstream) {
  return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
  }

}
