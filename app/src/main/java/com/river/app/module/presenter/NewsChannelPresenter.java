package com.river.app.module.presenter;

import android.content.Context;
import com.river.app.bean.ChannelManager;
import com.river.app.bean.NewsChannel;
import com.river.app.callback.RequestCallBack;
import com.river.app.http.Api;
import com.river.app.module.contract.NewsChannelContract;
import com.river.app.module.model.Remote.NewsRemoteDataSource;
import com.river.app.module.model.db.DBHelper;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/7.
 */

public class NewsChannelPresenter implements NewsChannelContract.Presenter {
  private NewsRemoteDataSource mDataSource;
  private NewsChannelContract.View mView;
  private Disposable mDisposable;
  private boolean isRefresh = true;
  private DBHelper mDBHelper;
  @Inject Context mContext;
  @Inject public NewsChannelPresenter(NewsRemoteDataSource dataSource,DBHelper dbHelper) {
    mDBHelper = dbHelper;
    mDataSource = dataSource;
  }
  //@Inject
  //void setupListeners() {
  //  mView.setPresenter(this);
  //}
  @Override public void loadData() {

   ChannelManager channel = mDBHelper.getChannelList();
    if(channel!=null){
      mView.updateTabFromDB(channel.getChannels());
    }else {
      mDisposable = mDataSource.requestNewsChannel(new RequestCallBack<NewsChannel>() {
        @Override public void requestBefore() {

        }
        @Override public void requestError(String msg) {
          if(mDisposable.isDisposed()){
            mDisposable.dispose();
          }
        }

        @Override public void requestComplete() {
          if(mDisposable.isDisposed()){
            mDisposable.dispose();
          }

        }
        @Override public void requestSuccess(NewsChannel data) {
          mView.updateTab(data.res_body.channelList);
          mDBHelper.saveChannelList(data);
        }
      }, Api.SHOWAPI_APPID,null,Api.SHOWAPI_SIGN);
    }
    //mRepository.getDatas(new DataSource.LoadDataCallback() {
    //  @Override public void onDataLoaded(List datas) {
    //    mView.updateTab(datas);
    //  }
    //
    //  @Override public void onDataNotAvailable() {
    //
    //  }
    //});

  }

  @Override public void attachView(NewsChannelContract.View view) {
    mView = view;
  }
  @Override public void detachView() {

  }
}
