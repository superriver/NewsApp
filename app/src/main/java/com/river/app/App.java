package com.river.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.river.app.di.component.AppComponent;
import com.river.app.di.component.DaggerAppComponent;
import com.river.app.di.module.ApiServiceModule;
import com.river.app.di.module.ApplicationModule;
import io.realm.Realm;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/9.
 */

public class App extends Application {
  private static Context mContext;
   private static AppComponent mAppComponent;
  private static App instance;
  private Set<Activity> mActivities;
  @Override public void onCreate() {
    super.onCreate();
    instance=this;
    mActivities = new HashSet<>();
    mAppComponent = DaggerAppComponent.builder()
        .applicationModule(new ApplicationModule(instance))
        .apiServiceModule(new ApiServiceModule())
        .build();
    Realm.init(getApplicationContext());
  }
  public synchronized static App getInstance(){
    return instance;
  }
  public void addActivity(Activity activity){
    mActivities.add(activity);
  }
  public void removeActivity(Activity activity){
    if(mActivities.contains(activity)){
      mActivities.remove(activity);
    }
  }
  public void clearActivity(){
    if(mActivities.size()>0){
      mActivities.clear();
    }
  }
  public static AppComponent getAppComponent(){
    return mAppComponent;
  }
  public static Context getContext(){
    return mContext;
  }
}
