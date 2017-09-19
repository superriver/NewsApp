package com.river.app.module.model.Remote;

import com.river.app.bean.NewsChannel;
import com.river.app.bean.NewsListBean;
import com.river.app.callback.RequestCallBack;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/4/7.
 */

public interface NewsDataSource {

  Disposable requestNewsChannel(RequestCallBack<NewsChannel> callBack,String appid, String timestap,
      String sign);

  Disposable requestNewsList (RequestCallBack<NewsListBean> callBack, String channelId,
      String channelName, String maxResult, String needAllList, String needContent, String needHtml,
      String page, String appid, String timestamp, String title,String showapi_sign);

  //Disposable requestHealthDetail(RequestCallBack<HealthDetail> callBack,String id,int appid, boolean draft, String timestamp, String sign);
}
