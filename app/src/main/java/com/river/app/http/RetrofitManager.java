package com.river.app.http;

import com.river.app.base.BaseSchedulerTransformer;
import com.river.app.bean.NewsChannel;
import com.river.app.bean.NewsListBean;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2017/4/10.
 */

@Singleton public class RetrofitManager {

  private ApiService mService;
  // private static SparseArray<RetrofitManager> mRMInstance = new SparseArray<>(HostType.TYPE_COUNT);

  /**
   * 健康频道
   */
  @Inject public RetrofitManager(ApiService service) {
    mService = service;
  }

  //public Observable<NewsChannel> getHealthChannel( int appid,
  //    boolean draft, String timestamp, String sign) {
  //  return mService.getNewsChannel(appid,draft,timestamp,sign).compose(new BaseSchedulerTransformer<NewsChannel>());
  //}
  //
  ///**
  // * 健康知识列表
  // */
  //public Observable<NewsListBean> getHealthList(String page, String rows, String tid,int appid,
  //    boolean draft, String timestamp, String sign) {
  //  return mService.getNewsList(page, rows, tid,appid,draft,timestamp,sign).
  //     compose(new BaseSchedulerTransformer<NewsChannel>());
  //}
  //
  ///**
  // * 健康知识详情
  // */
  //public Observable<HealthDetail> getHealthDetail(String id,int appid,
  //    boolean draft, String timestamp, String sign) {
  //  return mService.getHealthDetail(id,appid,draft,timestamp,sign).
  //      compose(new BaseSchedulerTransformer<HealthDetail>());
  //}


  /**
   * 新闻频道
   */
  public Observable<NewsChannel> getNewsChannel(String appId, String timesamp, String sign) {
    return mService.getNewsChannel(appId, timesamp, sign).
    compose(new BaseSchedulerTransformer<NewsChannel>());
  }

  /**
   * 新闻列表
   */
  public Observable<NewsListBean> getNewsList(String channelId, String channelName, String maxResult,
      String needAllList, String needContent, String needHtml, String page, String appid,
      String timestamp, String title, String showapi_sign) {
    return mService.getNewsList(channelId, channelName, maxResult, needAllList, needContent,
        needHtml, page, appid, timestamp, title, showapi_sign).
    compose(new BaseSchedulerTransformer<NewsListBean>());
  }

}
