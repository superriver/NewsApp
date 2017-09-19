package com.river.app.module.model.db;

import com.river.app.bean.ChannelBean;
import com.river.app.bean.ChannelManagerBean;
import com.river.app.bean.NewsChannel;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/4.
 */

public class DBHelper implements BaseDao {
  private static final String DB_NAME = "myRealm.realm";
  private RealmList<ChannelBean> mList;
  private Realm mRealm;

  @Inject
  public DBHelper() {
    mRealm = Realm.getInstance(new RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .name(DB_NAME)
        .build());
  }
  @Override public void saveChannelList( final NewsChannel channel) {
    mList = new RealmList<>();
    for(NewsChannel.NewsChannelBody.ChannelListBean bean:channel.res_body.channelList){
      mList.add(new ChannelBean(bean.channelId,bean.name));
    }

    final ChannelManagerBean bean = new ChannelManagerBean(mList);
    ChannelManagerBean healthChannel = mRealm.where(ChannelManagerBean.class).findFirst();
    if(healthChannel!=null) {
      healthChannel.deleteFromRealm();
    }
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        mRealm.copyToRealm(bean);
      }
    });
  }

  @Override public ChannelManagerBean getChannelList() {
    ChannelManagerBean healthChannel = mRealm.where(ChannelManagerBean.class).findFirst();
    if(null==healthChannel){
      return null;
    }
    return mRealm.copyFromRealm(healthChannel);
  }
}
