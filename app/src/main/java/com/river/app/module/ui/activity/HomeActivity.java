package com.river.app.module.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.river.app.R;
import com.river.app.base.BaseActivity;
import com.river.app.module.ui.fragment.MainFragment;
import com.river.app.util.ActivityUtils;

/**
 * Created by Administrator on 2017/3/24.
 */

public class HomeActivity extends BaseActivity {
  private MainFragment mainFragment;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setToolBar("新闻",-1);
     mainFragment =
        (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    if (mainFragment == null) {
      mainFragment = MainFragment.newInstance();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment,
          R.id.contentFrame);
    }
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }
}
