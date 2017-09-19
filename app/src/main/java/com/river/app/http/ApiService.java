package com.river.app.http;

import com.river.app.bean.NewsChannel;
import com.river.app.bean.NewsListBean;
import io.reactivex.Observable;
import javax.inject.Singleton;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/9/9.
 */
@Singleton
public interface ApiService {

  /**
   * 新闻频道请求api
   */
  @GET("109-34") Observable<NewsChannel> getNewsChannel(@Query("showapi_appid") String appId,
      @Query("showapi_timestamp") String timestamp, @Query("showapi_sign") String sign);

  /**
   * 新闻列表
   */
  @GET("109-35") Observable<NewsListBean> getNewsList(@Query("channelId") String channelId,
      @Query("channelName") String channelName, @Query("maxResult") String maxResult,
      @Query("needAllList") String needAllList, @Query("needContent") String needContent,
      @Query("needHtml") String needHtml, @Query("page") String page,
      @Query("showapi_appid") String appid, @Query("showapi_timestamp") String timestamp,
      @Query("title") String title, @Query("showapi_sign") String showapi_sign);
}
