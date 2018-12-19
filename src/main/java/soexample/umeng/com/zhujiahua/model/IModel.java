package soexample.umeng.com.zhujiahua.model;

import soexample.umeng.com.zhujiahua.callback.MyCallBack;

public interface IModel {
    void getData(String url, int type, MyCallBack myCallBack);
}
