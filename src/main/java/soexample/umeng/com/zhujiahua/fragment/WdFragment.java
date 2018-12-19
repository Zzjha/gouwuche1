package soexample.umeng.com.zhujiahua.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.zhujiahua.R;
import soexample.umeng.com.zhujiahua.adapter.FoodsAdapter;
import soexample.umeng.com.zhujiahua.bean.Foods;
import soexample.umeng.com.zhujiahua.presenter.MyPresenter;
import soexample.umeng.com.zhujiahua.view.IView;

public class WdFragment<T> extends Fragment implements IView<T> {

    private String url = "http://www.wanandroid.com/tools/mockapi/6523/restaurant-list";
    private ExpandableListView elv;
    private CheckBox quan;
    private TextView sl;
    private TextView js;
    private List<Foods.DataBean> list = new ArrayList<>();
    private FoodsAdapter adapter;
    private MyPresenter myPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wd, container, false);
        initView(view);
        myPresenter = new MyPresenter(this);
        myPresenter.startRequest(url,2);
        elv.setGroupIndicator(null);  //去掉箭头

        //底部全选
        quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllChecked = adapter.isAllChecked();
                adapter.checkAll(!isAllChecked);
                adapter.notifyDataSetChanged();
                flushBottomLayout();
            }
        });

        return view;
    }

    private void initView(View view) {
        elv = view.findViewById(R.id.elv);
        quan = view.findViewById(R.id.quan);
        sl = view.findViewById(R.id.sl);
        js = view.findViewById(R.id.js);
        //去掉小箭头
        elv.setGroupIndicator(null);
    }


    //IView
    @Override
    public void success(T success) {
        Foods foods = (Foods) success;
        list.addAll(foods.getData());
        adapter = new FoodsAdapter(list,getActivity());
        elv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //调用接口回调的方法
        //接口回调  调用方法
        adapter.setCallBack(new FoodsAdapter.CallBack() {
            //group
            @Override
            public void setGroupChecked(int groupPosition) {
                boolean isGroupCheck = adapter.isGroupChecked(groupPosition);
                adapter.gruopCheckboxChecked(groupPosition,!isGroupCheck);
                adapter.notifyDataSetChanged();
                flushBottomLayout();
            }

            //child
            @Override
            public void setChildChecked(int groupPosition, int childPosition) {
                boolean childChecked = adapter.isChildChecked(groupPosition, childPosition);
                adapter.setChildCheckboxChecked(groupPosition,childPosition,!childChecked);
                adapter.notifyDataSetChanged();
                flushBottomLayout();
            }

            //加减
            @Override
            public void setJiaJian(int groupPosition, int childPosition, int count) {
                adapter.setCounts(groupPosition,childPosition,count);
                adapter.notifyDataSetChanged();
                flushBottomLayout();
            }
        });
    }
    @Override
    public void error(T error) {

    }



    //底部刷新方法
    private void flushBottomLayout() {
        boolean isGroupCheck = adapter.isAllChecked();
        quan.setChecked(isGroupCheck);
        float sumPrice = adapter.getsumPrice();
        int sumCount = adapter.getsumCount();
        sl.setText("数量：" + sumCount);
        js.setText("去结算: "+ sumPrice);
    }
}
