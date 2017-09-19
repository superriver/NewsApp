package com.river.app.module.model;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Resource<T> {
  private String statue;
  private T data;
  private String msg;

  public Resource(String statue,T data,String msg){
      this.statue=statue;
    this.data=data;
    this.msg=msg;
  }
  public static <T> Resource<T> success(T data){
    return new Resource<>("success",data,null);

  }
  public static <T> Resource<T> error(T data,String msg){
    return new Resource<>("error",data,msg);
  }
  public static <T> Resource<T> loading(T data){
    return new Resource<>("loading",data,null);
  }
}
