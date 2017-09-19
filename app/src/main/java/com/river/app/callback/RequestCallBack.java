package com.river.app.callback;

public interface RequestCallBack<T> {
  void requestBefore();
  void requestError(String msg);
  void requestComplete();
  void requestSuccess(T data);
}

