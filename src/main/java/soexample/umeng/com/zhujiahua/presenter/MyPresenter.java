package soexample.umeng.com.zhujiahua.presenter;

import soexample.umeng.com.zhujiahua.callback.MyCallBack;
import soexample.umeng.com.zhujiahua.model.MyModel;
import soexample.umeng.com.zhujiahua.view.IView;

public class MyPresenter implements IPresenter{

    private MyModel myModel;
    private IView iView;

    public MyPresenter(IView iView) {
        this.iView = iView;
        myModel = new MyModel();
    }

    @Override
    public void startRequest(String url, final int type) {
        myModel.getData(url, type, new MyCallBack() {
            @Override
            public void setSuccess(Object successData) {
                switch (type){
                    case 1:
                        iView.success(successData);
                        break;
                    case 2:
                        iView.success(successData);
                        break;
                }
            }

            @Override
            public void setError(Object errorData) {
                switch (type){
                    case 1:
                        iView.success(errorData);
                        break;
                    case 2:
                        iView.success(errorData);
                        break;
                }
            }
        });
    }
}
