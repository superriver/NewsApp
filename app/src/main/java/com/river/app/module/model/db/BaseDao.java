package com.river.app.module.model.db;

import com.river.app.bean.ChannelManagerBean;
import com.river.app.bean.NewsChannel;

/**
 * Created by Administrator on 2017/7/5.
 */

public interface BaseDao {
    void saveChannelList(NewsChannel channel);
    ChannelManagerBean getChannelList();
}
