package com.river.app.module.presenter;

import android.content.Context;
import com.river.app.bean.NewsListBean;
import com.river.app.callback.RequestCallBack;
import com.river.app.constant.DataType;
import com.river.app.http.Api;
import com.river.app.module.contract.NewsListContract;
import com.river.app.module.model.Remote.NewsRemoteDataSource;
import com.river.app.util.NetUtil;
import com.socks.library.KLog;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/7.
 */

public class NewsListPresenter implements NewsListContract.Presenter {

  private NewsRemoteDataSource mDataSource;
  private NewsListContract.View mView;
  private Disposable mDisposable;
  private int maxResult = 20;
  private int page = 1;
  private String mName;
  private String mId;
  private boolean isRefresh = true;
  @Inject Context mContext;
  @Inject public NewsListPresenter(NewsRemoteDataSource dataSource) {
    mDataSource = dataSource;
  }

  //@Inject void setupListeners() {
  //  mView.setPresenter(this);
  //}

  @Override public void requestList(String id) {
    if(!NetUtil.isConnected(mContext)){
      mView.getNewsList(null, "网络未连接？",isRefresh ? DataType.TYPE_REFRESH_FAILED : DataType.TYPE_LOADMORE_FAILED);
      return;
    }
    mId = id;

    mDisposable = mDataSource.requestNewsList(new RequestCallBack<NewsListBean>() {
      @Override public void requestBefore() {
        mView.showProgress();
      }

      @Override public void requestError(String msg) {
        mView.getNewsList(null, msg,isRefresh ? DataType.TYPE_REFRESH_FAILED : DataType.TYPE_LOADMORE_FAILED);
        mView.hideProgress();
        if(mDisposable.isDisposed()){
          mDisposable.dispose();
        }
      }

      @Override public void requestComplete() {
        mView.hideProgress();
        if(mDisposable.isDisposed()){
          mDisposable.dispose();
        }
      }

      @Override public void requestSuccess(NewsListBean data) {
        mView.getNewsList(data.showapi_res_body.pagebean.contentlist, null,
            isRefresh ? DataType.TYPE_REFRESH_SUCCESS : DataType.TYPE_LOADMORE_SUCCESS);
      }
    }, mId, null, String.valueOf(maxResult),  "1", "1", "0", String.valueOf(page),
        Api.SHOWAPI_APPID, null, null, Api.SHOWAPI_SIGN);
  }

  @Override public void refreshData() {
    isRefresh = true;
    page = 1;
    maxResult = 20;
    requestList(mId);
  }

  @Override public void loadMoreData() {
    KLog.d("huang","loadMoreData");
    isRefresh = false;
    page++;
    maxResult = 20;
    requestList(mId);
  }



  @Override public void attachView(NewsListContract.View view) {
    mView=view;
  }

  @Override public void detachView() {

  }
}
