package soexample.umeng.com.zhujiahua.model;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import soexample.umeng.com.zhujiahua.bean.Foods;
import soexample.umeng.com.zhujiahua.bean.News;
import soexample.umeng.com.zhujiahua.callback.MyCallBack;
import soexample.umeng.com.zhujiahua.utils.OkHttp;

public class MyModel implements IModel {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;
            Gson gson = new Gson();
            switch (type){
                case 1:
                    News news = gson.fromJson(s, News.class);
                    myCallBack.setSuccess(news);
                    break;
                case 2:
                    Foods foods = gson.fromJson(s, Foods.class);
                    myCallBack.setSuccess(foods);
                    break;
            }
        }
    };

    private int type;
    private MyCallBack myCallBack;

    @Override
    public void getData(String url, int type, MyCallBack myCallBack) {
        this.type = type;
        this.myCallBack = myCallBack;
        OkHttp.getInstance().getAsync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0,response.body().string()));
            }
        });
    }
}
