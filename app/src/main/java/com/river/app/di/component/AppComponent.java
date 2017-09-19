package com.river.app.di.component;

import android.content.Context;
import com.river.app.di.module.ApiServiceModule;
import com.river.app.di.module.ApplicationModule;
import com.river.app.http.RetrofitManager;
import com.river.app.module.model.Remote.NewsRemoteDataSource;
import com.river.app.module.model.db.DBHelper;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface AppComponent{
    Context getContext();
    RetrofitManager getRetrofitManager();
    NewsRemoteDataSource getHealthRemoteDataSource();
    DBHelper getDBHelper();
}