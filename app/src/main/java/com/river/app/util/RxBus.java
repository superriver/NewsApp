package com.river.app.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RxBus {
  private final Subject mSubject;
  public RxBus(){
    mSubject = PublishSubject.create().toSerialized();
  }

  public static RxBus get(){
    return Holder.BUS;
  }
  public void post(Object obj){
    mSubject.onNext(obj);
  }

  public <T> Observable<T> toObservable(Class<T> tClass){
    return mSubject.ofType(tClass);
  }

  public <T> Observable<T> toObservable(){
    return  mSubject;
  }
  public boolean hasObservers(){
      return mSubject.hasObservers();
  }
  private static class Holder{
    private static final RxBus BUS=new RxBus();
  }
}
