package soexample.umeng.com.zhujiahua.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import soexample.umeng.com.zhujiahua.R;
import soexample.umeng.com.zhujiahua.bean.Foods;
import soexample.umeng.com.zhujiahua.weight.MyView;

public class FoodsAdapter extends BaseExpandableListAdapter {

    private List<Foods.DataBean> list;
    private Context context;

    public FoodsAdapter(List<Foods.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSpus().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = View.inflate(context, R.layout.group_item, null);
            groupHolder.group_checkbox = convertView.findViewById(R.id.group_checkbox);
            groupHolder.group_name = convertView.findViewById(R.id.group_name);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.group_name.setText(list.get(groupPosition).getName() + "");


        Boolean boo = isGroupChecked(groupPosition);
        groupHolder.group_checkbox.setChecked(boo);
        //group的复选框的点击事件
        groupHolder.group_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null){
                    callBack.setGroupChecked(groupPosition);
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(context, R.layout.child_item, null);
            childHolder.child_checkbox = convertView.findViewById(R.id.child_checkbox);
            childHolder.child_price = convertView.findViewById(R.id.child_price);
            childHolder.child_name = convertView.findViewById(R.id.child_name);
            childHolder.child_picUrl = convertView.findViewById(R.id.child_picUrl);

            //自定义view
            childHolder.myView = convertView.findViewById(R.id.myview);


            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        Foods.DataBean.SpusBean spusBean = list.get(groupPosition).getSpus().get(childPosition);
        Glide.with(context).load(spusBean.getPic_url()).into(childHolder.child_picUrl);
        childHolder.child_name.setText(spusBean.getName() + "");
        childHolder.child_price.setText(spusBean.getSkus().get(0).getPrice() + "");
        childHolder.myView.setCount(spusBean.getPraise_num());

        childHolder.child_checkbox.setChecked(spusBean.isChildChecked());
        childHolder.child_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null){
                    callBack.setChildChecked(groupPosition,childPosition);
                }
            }
        });
        childHolder.myView.setOnCountChange(new MyView.OnCountChange() {
            @Override
            public void setCount(int count) {
                if(callBack!=null){
                    callBack.setJiaJian(groupPosition,childPosition,count);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class GroupHolder {
        private CheckBox group_checkbox;
        private TextView group_name;
    }
    class ChildHolder {
        private CheckBox child_checkbox;
        private TextView child_name;
        private ImageView child_picUrl;
        private TextView child_price;
        private MyView myView;
    }


    //判断小组是否被选中
    //【当group的CheckBox点击时,child的CheckBox全部被选中】
    public boolean isGroupChecked(int groupPosition){
        boolean b = true;
        Foods.DataBean dataBean = list.get(groupPosition);
        List<Foods.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size(); i++) {
            Foods.DataBean.SpusBean spusBean = spus.get(i);
            if(!spusBean.isChildChecked()){
                return false;
            }
        }
        return b;
    }
    //点击group的复选框，所有child的复选框被选中（group的全选、反选）
    public void gruopCheckboxChecked(int groupPosition,boolean isGroupCheck){
        Foods.DataBean dataBean = list.get(groupPosition);
        List<Foods.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size(); i++) {
            Foods.DataBean.SpusBean spusBean = spus.get(i);
            spusBean.setChildChecked(isGroupCheck);
        }
    }


    //查看当前商品是否被选中
    public boolean isChildChecked(int groupPosition,int childPosition){
        Foods.DataBean dataBean = list.get(groupPosition);
        Foods.DataBean.SpusBean spusBean = dataBean.getSpus().get(childPosition);
        if(spusBean.isChildChecked()){
            return true;
        }
        return false;
    }
    //点击child的复选框进行复制
    public void setChildCheckboxChecked(int groupPosition,int childPosition,boolean childChecked){
        Foods.DataBean.SpusBean spusBean = list.get(groupPosition).getSpus().get(childPosition);
        spusBean.setChildChecked(childChecked);
    }


    //商品数量进行赋值
    public void setCounts(int groupPosition,int childPosition,int count){
        Foods.DataBean.SpusBean spusBean = list.get(groupPosition).getSpus().get(childPosition);
        spusBean.setPraise_num(count);
    }


    //底部 全选
    public boolean isAllChecked(){
        boolean b = true;
        for (int i = 0; i < list.size(); i++) {
            Foods.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                Foods.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if(!spusBean.isChildChecked()){
                    b = false;
                }
            }
        }
        return b;
    }
    public void checkAll(boolean isAllChecked){
        for (int i = 0; i < list.size(); i++) {
            Foods.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                Foods.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                spusBean.setChildChecked(isAllChecked);
            }
        }
    }


    //计算所有商品的价格
    public float getsumPrice(){
        float sumPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            Foods.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                Foods.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if(spusBean.isChildChecked()){
                    sumPrice += spusBean.getPraise_num()*Float.parseFloat(spusBean.getSkus().get(0).getPrice());
                }
            }
        }
        return sumPrice;
    }
    //就算所有商品的数量
    public int getsumCount(){
        int sumCount = 0;
        for (int i = 0; i < list.size(); i++) {
            Foods.DataBean dataBean = list.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                Foods.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if(spusBean.isChildChecked()){
                    sumCount += spusBean.getPraise_num();
                }
            }
        }
        return sumCount;
    }



    //接口回调【点击group的复选框、child的复选框都需要刷新，所以要做成接口回调】
    public interface CallBack{
        void setGroupChecked(int groupPosition);
        void setChildChecked(int groupPosition,int childPosition);
        void setJiaJian(int groupPosition,int childPosition,int count);
    }
    private CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }
}
