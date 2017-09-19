package com.river.app.module.contract;

import com.river.app.base.BasePresenter;
import com.river.app.base.BaseView;
import com.river.app.bean.NewsListBean;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public interface NewsListContract {
  interface View extends BaseView {
    void getNewsList(List<NewsListBean.ShowapiResBodyBean.PageBean.ContentBean> contents,String msg,int type);
  }
  interface Presenter extends BasePresenter<View>{
    void requestList(String id);
    void refreshData();
    void loadMoreData();
  }
}
