package com.river.app.module.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.river.app.R;
import com.river.app.base.BaseFragment;
import com.river.app.bean.MessageEvent;
import com.river.app.bean.NewsListBean;
import com.river.app.bean.NewsListBean.NewsList.PageBean.ContentBean;
import com.river.app.callback.OnItemClickAdapter;
import com.river.app.callback.OnLoadMoreListener;
import com.river.app.constant.DataType;
import com.river.app.module.contract.NewsListContract;
import com.river.app.module.presenter.NewsListPresenter;
import com.river.app.module.ui.activity.NewsDetailActivity;
import com.river.app.module.ui.adapter.BaseRecyclerAdapter;
import com.river.app.module.ui.adapter.BaseRecyclerViewHolder;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/4/6.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter> implements NewsListContract.View {
  @BindView(R.id.content) RecyclerView mRecyclerView;
  @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
  @BindView(R.id.progress) ProgressBar mProgressBar;

  private BaseRecyclerAdapter<ContentBean> mAdapter;
  private String id;
  @Override protected void initInject() {
    getFragmentComponent().inject(this);
  }

  @Override protected void initData() {
    mPresenter.requestList(id);
  }

  @Override protected int getLayoutId() {
    return R.layout.content_view;
  }

  public static NewsListFragment newsInstance(String id) {
    NewsListFragment mainFragment = new NewsListFragment();
    Bundle bundle = new Bundle();
    bundle.putString("channelId", id);
    mainFragment.setArguments(bundle);
    return mainFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      id = getArguments().getString("channelId");
    }
  }
  //@Override public void setPresenter(NewsListContract.Presenter presenter) {
  //  //mPresenter=presenter;
  //}

  @Override public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgress() {
    mProgressBar.setVisibility(View.GONE);
  }

  @Override public void getNewsList(List<ContentBean> contents,String errorMsg,int type) {
    if(mAdapter==null){
      initDataList(contents);
    }
    switch (type){
      case DataType.TYPE_REFRESH_SUCCESS:
        mRefreshLayout.setRefreshing(false);
        mAdapter.setData(contents);
        mAdapter.enableLoadMore(true);
        break;
      case DataType.TYPE_LOADMORE_SUCCESS:
        mAdapter.loadMoreSuccess();
        //if (contents == null || contents.size() == 0) {
        //  mAdapter.enableLoadMore(null);
        //  Toast.makeText(mActivity, "全部加载完毕", Toast.LENGTH_SHORT).show();
        //  return;
        //}
        mAdapter.addMoreData(contents);
        break;
      case DataType.TYPE_REFRESH_FAILED:
        mAdapter.enableLoadMore(false);
        mAdapter.showEmptyView(true,errorMsg);
        mAdapter.notifyDataSetChanged();
        break;
      case DataType.TYPE_LOADMORE_FAILED:
        mAdapter.loadMoreFailed(errorMsg);
        break;

    }
    //mRefreshLayout.setRefreshing(true);

  }

  public void initDataList(final List<ContentBean> contents) {
    mAdapter = new BaseRecyclerAdapter<ContentBean>(getActivity(), contents) {
      @Override public int getItemLayoutId(int viewType) {
        return R.layout.item_knowledge;
      }

      @Override public void bindData(BaseRecyclerViewHolder holder, int position,  NewsListBean.NewsList.PageBean.ContentBean  data) {
        if (data.imageurls.size() == 0) {
          holder.getImageView(R.id.iv_health_summary_photo).setImageResource(R.drawable.ic_loading);
        } else {
          Glide.with(getActivity())
              .load(data.imageurls.get(0).url)
              .placeholder(R.drawable.ic_loading)
              .error(R.drawable.ic_fail)
              .diskCacheStrategy(DiskCacheStrategy.SOURCE)
              .into(holder.getImageView(R.id.iv_health_summary_photo));
        }
        holder.getTextView(R.id.tv_health_summary_title).setText(data.title);
        holder.getTextView(R.id.tv_health_summary_digest).setText(data.desc);
        holder.getTextView(R.id.tv_health_summary_ptime).setText(String.valueOf(data.pubDate));
      }
    };
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(new OnItemClickAdapter() {
      @Override public void onItemClick(View view, int position) {
        view = view.findViewById(R.id.iv_health_summary_photo);
        if (!TextUtils.isEmpty(mAdapter.getData().get(position).content)) {
          Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
          EventBus.getDefault().postSticky(new MessageEvent(position,mAdapter.getData().get(position)));
         // intent.putExtra("postId", mAdapter.getData().get(position).);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                view.findViewById(R.id.iv_health_summary_photo), "photos");
            getActivity().startActivity(intent, options.toBundle());
          } else {
            ActivityOptions options =
                ActivityOptions.makeScaleUpAnimation(view, view.getWidth(), view.getHeight(), 0, 0);
            getActivity().startActivity(intent, options.toBundle());
          }
        }
      }
    });


    mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
          mPresenter.refreshData();
      }
    });
    mAdapter.setOnLoadMoreListener(10,new OnLoadMoreListener() {
      @Override public void loadMore() {
        mPresenter.loadMoreData();
      }
    });
  }
}
