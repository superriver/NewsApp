package com.river.app.module.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.river.app.R;
import com.river.app.base.BaseActivity;
import com.river.app.bean.MessageEvent;
import com.river.app.bean.NewsListBean.NewsList.PageBean.ContentBean;
import com.river.app.util.ViewUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhou.widget.RichText;

import static com.river.app.R.id.iv_health_detail_photo;
import static com.river.app.R.id.tv_news_detail_body;
import static com.river.app.R.id.tv_news_detail_from;
import static com.river.app.R.id.tv_news_detail_title;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NewsDetailActivity extends BaseActivity {
  @BindView(iv_health_detail_photo) ImageView mImageView;
  @BindView(tv_news_detail_title) TextView mtitle;
  @BindView(tv_news_detail_from) TextView mFrom;
  @BindView(tv_news_detail_body) RichText mRichText;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(NewsDetailActivity.this);
    setToolBar("新闻详情", R.drawable.ic_menu_back);
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
      ViewUtil.showStatusBar(this);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onMessageEvent(MessageEvent event) {
     ContentBean contentBean =
        (ContentBean) event.getData();

    initData(contentBean);
  }

  private void initData(ContentBean data) {
    if (data != null) {
      if (data.imageurls.size() == 0) {
        mImageView.setImageResource(R.drawable.ic_loading);
      } else {
        Glide.with(this)
            .load(data.imageurls.get(0).url)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_fail)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(mImageView);
      }
      if (!TextUtils.isEmpty(data.content)) {
        mRichText.setText(data.content);
      }
      mFrom.setText(getString(R.string.from, data.source, data.pubDate));
  }
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_news_detail;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
