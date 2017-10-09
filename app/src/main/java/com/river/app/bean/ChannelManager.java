package com.river.app.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ChannelManager extends RealmObject {
 private RealmList<ChannelBean> channels;
  public ChannelManager(){

  }
public ChannelManager( RealmList<ChannelBean> channels){
  this.channels=channels;
}
  public RealmList<ChannelBean> getChannels() {
    return channels;
  }

  public void setChannels(RealmList<ChannelBean> channels) {
    this.channels = channels;
  }
}
