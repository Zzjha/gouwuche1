package soexample.umeng.com.zhujiahua.view;

public interface IView<T> {
    void success(T success);
    void error(T error);
}
