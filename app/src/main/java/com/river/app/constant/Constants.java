package com.river.app.constant;

/**
 * Created by Administrator on 2017/6/21.
 */

public class Constants {
  // 设缓存有效期为两天
  public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
  // 30秒内直接读缓存
  public static final long CACHE_AGE_SEC = 0;
}
