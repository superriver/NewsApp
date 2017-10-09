package com.river.app.module.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import butterknife.BindView;
import com.river.app.R;
import com.river.app.base.BaseAdapter;
import com.river.app.base.BaseFragment;
import com.river.app.bean.ChannelBean;
import com.river.app.bean.NewsChannel.NewsChannelBody.ChannelListBean;
import com.river.app.module.contract.NewsChannelContract;
import com.river.app.module.presenter.NewsChannelPresenter;
import com.river.app.widget.HorizontalScrollMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * Created by Administrator on 2017/4/6.
 */

public class MainFragment extends BaseFragment<NewsChannelPresenter> implements NewsChannelContract.View {
  @BindView(R.id.hsm_container) HorizontalScrollMenu hsm_container;
  private List<String> channelNames = new ArrayList<>();
  private List<String> channelId = new ArrayList<>();
  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Override protected void initInject() {
    getFragmentComponent().inject(this);
  }
  @Override protected void initData() {
    mPresenter.loadData();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_health_main;
  }


  //@Override public void setPresenter(NewsChannelContract.Presenter presenter) {
  //   // mPresenter=presenter;
  //}

  @Override public void updateTab(List<ChannelListBean> channels) {

    for (ChannelListBean channel : channels) {
      channelNames.add(channel.name);
      channelId.add(channel.channelId);
    }
    hsm_container.setAdapter(new MenuAdapter(getChildFragmentManager(), channelNames,channelId));
  }

  @Override public void updateTabFromDB(List<ChannelBean> channels) {
    for (ChannelBean channel : channels) {
      channelNames.add(channel.getName());
      channelId.add(channel.getId());
    }
    hsm_container.setAdapter(new MenuAdapter(getChildFragmentManager(), channelNames,channelId));
  }

  public class MenuAdapter extends BaseAdapter {
    List<String> channelNames;
    List<String> channelId;
    private FragmentManager fm;
    Map<List<String>,List<String>> maps = new HashMap<>();
    public MenuAdapter(FragmentManager fm, List<String> channelNames,List<String> channelId) {
      this.fm = fm;
      this.channelNames = channelNames;
      this.channelId=channelId;
      maps.put(channelNames,channelId);
    }

    public FragmentManager getFm() {
      return fm;
    }

    @Override public List<String> getMenuItems() {
      return channelNames;
    }

    @Override public List<Fragment> getContentViews() {
      List<Fragment> fragment = new ArrayList<>();
      for (String id : channelId) {
        fragment.add(NewsListFragment.newsInstance(id));
      }
      return fragment;
    }

    @Override public void onPageChanged(int position, boolean visitStatus) {
      // notifyDataSetChanged();
    }
  }


}
