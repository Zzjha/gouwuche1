package soexample.umeng.com.zhujiahua.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import soexample.umeng.com.zhujiahua.R;
import soexample.umeng.com.zhujiahua.bean.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News.DataBean> list;
    private Context context;

    public NewsAdapter(List<News.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.liuitem,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News.DataBean dataBean = list.get(i);

        //瀑布流：在加载布局时把参数2改成viewGroup
        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
        Random random = new Random();
        int height = random.nextInt(300)+300;
        params.height = height;
        viewHolder.itemView.setLayoutParams(params);

        Glide.with(context).load(dataBean.getPic_url()).into(viewHolder.images);
        viewHolder.title.setText(dataBean.getNews_title());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView images;
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
            title = itemView.findViewById(R.id.title);
        }
    }
}
