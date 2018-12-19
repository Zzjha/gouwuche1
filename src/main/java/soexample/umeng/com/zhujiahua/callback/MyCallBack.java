package soexample.umeng.com.zhujiahua.callback;

public interface MyCallBack<T> {
    void setSuccess(T successData);
    void setError(T errorData);
}
