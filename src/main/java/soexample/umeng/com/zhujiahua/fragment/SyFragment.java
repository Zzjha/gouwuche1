package soexample.umeng.com.zhujiahua.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.zhujiahua.R;
import soexample.umeng.com.zhujiahua.adapter.NewsAdapter;
import soexample.umeng.com.zhujiahua.bean.News;
import soexample.umeng.com.zhujiahua.presenter.MyPresenter;
import soexample.umeng.com.zhujiahua.view.IView;

public class SyFragment<T> extends Fragment implements IView<T> {

    private String url = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private EditText et;
    private FlyBanner banner;
    private RecyclerView recy;
    private List<News.DataBean> list = new ArrayList<>();
    private NewsAdapter adapter;
    private MyPresenter myPresenter;
    private String[] imgUrl = {"http://www.zhaoapi.cn/images/quarter/ad1.png","http://www.zhaoapi.cn/images/quarter/ad2.png",
                            "http://www.zhaoapi.cn/images/quarter/ad3.png","http://www.zhaoapi.cn/images/quarter/ad4.png"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sy, container, false);
        initView(view);
        initNetBanner(view);
        myPresenter = new MyPresenter(this);
        adapter = new NewsAdapter(list,getActivity());
        recy.setAdapter(adapter);
        myPresenter.startRequest(url,1);
        return view;
    }

    private void initView(View view) {
        et = view.findViewById(R.id.et);
        recy = view.findViewById(R.id.recy);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recy.setLayoutManager(manager);
    }

    //轮播图
    private void initNetBanner(View v) {
        banner = v.findViewById(R.id.banner);
        List<String> imgesUrl = new ArrayList<>();
        for (int i = 0; i < imgUrl.length; i++) {
            imgesUrl.add(imgUrl[i]);
        }
        banner.setImagesUrl(imgesUrl);
    }


    //IView
    @Override
    public void success(T success) {
        News news = (News) success;
        list.addAll(news.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error(T error) {

    }
}
