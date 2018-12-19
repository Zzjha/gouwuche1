package soexample.umeng.com.zhujiahua.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp {
    private OkHttpClient okHttpClient;

    public OkHttp() {
        //日志拦截器
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20,TimeUnit.MINUTES)
                .readTimeout(20,TimeUnit.MINUTES)
                .callTimeout(20,TimeUnit.MINUTES)
                .build();
    }

    public static OkHttp getInstance(){
        return OkHolder.OkHttp;
    }

    static class OkHolder{
        private static final OkHttp OkHttp = new OkHttp();
    }

    //异步GET
    public void getAsync(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
