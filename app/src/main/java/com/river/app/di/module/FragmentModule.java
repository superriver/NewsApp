package com.river.app.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/6/16.
 */

@Module public class FragmentModule {
  private Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }

  @Provides protected Activity provideActivity() {
    return fragment.getActivity();
  }

}
