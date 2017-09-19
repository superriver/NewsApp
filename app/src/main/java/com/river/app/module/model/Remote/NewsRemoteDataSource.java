package com.river.app.module.model.Remote;

import com.river.app.base.BaseSubscriber;
import com.river.app.bean.NewsChannel;
import com.river.app.bean.NewsListBean;
import com.river.app.callback.RequestCallBack;
import com.river.app.http.RetrofitManager;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/7.
 */

public class NewsRemoteDataSource implements NewsDataSource {
  @Inject RetrofitManager mRetrofitManager;

  @Inject public NewsRemoteDataSource() {
  }

  //@Override
  //public Disposable requestHealthChannel(RequestCallBack<NewsChannel> callBack, int appid,
  //    boolean draft, String timestamp, String sign) {
  //  return mRetrofitManager.getHealthChannel(appid,draft,timestamp,sign).subscribeWith(new BaseSubscriber<>(callBack));
  //}
  //
  //@Override
  //public Disposable requestHealthList(RequestCallBack<NewsListBean> callBack, String page, String key,
  //    String tid,int appid, boolean draft, String timestamp, String sign) {
  //  return mRetrofitManager.getNewsList(page, key, tid,appid,draft,timestamp,sign)
  //      .subscribeWith(new BaseSubscriber<>(callBack));
  //}

  @Override
  public Disposable requestNewsList(RequestCallBack<NewsListBean> callBack, String channelId,
      String channelName, String maxResult, String needAllList, String needContent, String needHtml,
      String page, String appid, String timestamp, String title, String showapi_sign) {

    return mRetrofitManager.getNewsList(channelId, channelName, maxResult, needAllList, needContent,
        needHtml, page, appid, timestamp, title, showapi_sign)
        .subscribeWith(new BaseSubscriber<>(callBack));
  }

  @Override
  public Disposable requestNewsChannel(RequestCallBack<NewsChannel> callBack, String appid,
      String timestap, String sign) {

    return mRetrofitManager.getNewsChannel(appid, timestap, sign)
        .subscribeWith(new BaseSubscriber<>(callBack));
  }

 /* @Override public Disposable requestHealthDetail(RequestCallBack<HealthDetail> callBack, String id,int appid, boolean draft, String timestamp, String sign) {
    return mRetrofitManager.getHealthDetail(id,appid,draft,timestamp,sign).subscribeWith(new BaseSubscriber<>(callBack));
  }*/
}
