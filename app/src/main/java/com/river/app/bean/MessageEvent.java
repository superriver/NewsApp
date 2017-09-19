package com.river.app.bean;

/**
 * Created by Administrator on 2017/1/17.
 */

public class MessageEvent {
  private int position;
  private Object data;

  public MessageEvent(int position,Object data) {
    this.position = position;
    this.data = data;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
