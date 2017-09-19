package com.river.app.di.module;

import android.app.Activity;
import com.river.app.annotation.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/6/12.
 */

@Module
public class ActivityModule {
  private final Activity mActivity;
  public ActivityModule(Activity activity){
    mActivity = activity;
  }
  @Provides @ActivityScope public Activity provideActivity(){
    return mActivity;
  }
}
