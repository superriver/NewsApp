package com.river.app.di.component;

import com.river.app.annotation.ActivityScope;
import com.river.app.di.module.ActivityModule;
import com.river.app.module.ui.activity.HomeActivity;
import com.river.app.module.ui.activity.NewsDetailActivity;
import dagger.Component;

/**
 * Created by Administrator on 2017/6/12.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,modules ={ActivityModule.class})
public interface ActivityComponent {
  void inject(HomeActivity homeActivity);
  void inject(NewsDetailActivity healthDetailActivity);
}
