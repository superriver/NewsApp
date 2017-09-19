package com.river.app.di.module;

import com.river.app.App;
import com.river.app.http.Api;
import com.river.app.http.ApiService;
import com.river.app.http.RetrofitManager;
import com.river.app.util.NetUtil;
import com.socks.library.KLog;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.river.app.constant.Constants.CACHE_AGE_SEC;
import static com.river.app.constant.Constants.CACHE_STALE_SEC;

/**
 * Created by Administrator on 2017/5/9.
 */

@Module public class ApiServiceModule {

  @Singleton @Provides  OkHttpClient provideOkHttpClient() {
    Cache cache =
        new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 10 * 1024 * 1024);
    return new OkHttpClient.Builder().cache(cache)
        .addNetworkInterceptor(mCacheControlInterceptor)
        .addInterceptor(mLoggingInterceptor)
        .retryOnConnectionFailure(true)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .readTimeout(3, TimeUnit.SECONDS)
        .build();
  }

  @Singleton @Provides  Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(Api.NEWS_URL)
        .client(okHttpClient)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @Singleton @Provides  ApiService provideApiService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }

  @Singleton @Provides  RetrofitManager provideRetrofitManager(ApiService apiService) {
    return new RetrofitManager(apiService);
  }

  //配置缓存策略
  private Interceptor mCacheControlInterceptor = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      if (NetUtil.isConnected(App.getInstance().getApplicationContext())) {
        request = request.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public,max-age=" + CACHE_STALE_SEC)
            .build();
        Response response = chain.proceed(request);
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public,max-age=" + CACHE_STALE_SEC)
            .build();
      } else {
        request = request.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public,only-if-cached, max-stale=" + CACHE_AGE_SEC)
            .build();
        Response response = chain.proceed(request);
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public,only-if-cached, max-stale=" + CACHE_AGE_SEC)
            .build();
      }
    }
  };
  // 打印返回的json数据拦截器
  private Interceptor mLoggingInterceptor = new Interceptor() {
    @Override public Response intercept(Chain chain) throws IOException {

      Request request = chain.request();

      Request.Builder requestBuilder = request.newBuilder();
      requestBuilder.addHeader("User-Agent",
          "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
      request = requestBuilder.build();

      final Response response = chain.proceed(request);

      KLog.e("请求网址: \n"
          + request.url()
          + " \n "
          + "请求头部信息：\n"
          + request.headers()
          + "响应头部信息：\n"
          + response.headers());

      final ResponseBody responseBody = response.body();
      final long contentLength = responseBody.contentLength();

      BufferedSource source = responseBody.source();
      source.request(Long.MAX_VALUE); // Buffer the entire body.
      Buffer buffer = source.buffer();

      Charset charset = Charset.forName("UTF-8");
      MediaType contentType = responseBody.contentType();
      if (contentType != null) {
        try {
          charset = contentType.charset(charset);
        } catch (UnsupportedCharsetException e) {
          KLog.e("");
          KLog.e("Couldn't decode the response body; charset is likely malformed.");
          return response;
        }
      }

      if (contentLength != 0) {
        KLog.v(
            "--------------------------------------------开始打印返回数据----------------------------------------------------");
        KLog.json(buffer.clone().readString(charset));
        KLog.v(
            "--------------------------------------------结束打印返回数据----------------------------------------------------");
      }

      return response;
    }
  };
}
