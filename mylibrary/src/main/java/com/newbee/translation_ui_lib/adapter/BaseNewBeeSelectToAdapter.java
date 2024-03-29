package com.newbee.translation_ui_lib.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newbee.translation_ui_lib.R;
import com.newbee.translation_ui_lib.bean.ShowInfoBean;


import java.util.List;

public abstract class BaseNewBeeSelectToAdapter extends RecyclerView.Adapter {
    public static final String tag =  "lixiaoaaa>>>>";
    private LayoutInflater layoutInflater;
    private List<Object> list;
    private String nowStr;
    private ItemClick itemClick;
    public BaseNewBeeSelectToAdapter(Context context, String nowStr) {
        layoutInflater=LayoutInflater.from(context);
        this.nowStr=nowStr;
    }

    public void setList(  List<Object> list){
        this.list=list;
    }

    public void setClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }


    public Object getData(int index){
        try {
            return list.get(index);
        }catch (Exception e){}
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.adapter_newbee_select, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        return viewHodler;
    }

    private RadioButton checkBT;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final ViewHodler viewHodler= (ViewHodler) holder;
        final Object object=list.get(position);
        ShowInfoBean showInfoBean=getShowInfo(object);
        if(null==showInfoBean){
            return;
        }
        viewHodler.titleTV.setText(showInfoBean.getTitle());
        if(TextUtils.isEmpty(showInfoBean.getContent())){
            viewHodler.contentTV.setText("");
        }else {
            if(showInfoBean.isHideContentTitle()){
                viewHodler.contentTV.setText(showInfoBean.getContent());
            }else {
                viewHodler.contentTV.setText(viewHodler.contentTV.getContext().getApplicationContext().getResources().getText(R.string.item_content_head)+showInfoBean.getContent());
            }
        }
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=itemClick){
                    itemClick.nowSelect(position,object);
                }
            }
        };
        viewHodler.contentLL.setOnClickListener(onClickListener);
        viewHodler.titleTV.setOnClickListener(onClickListener);
        viewHodler.contentTV.setOnClickListener(onClickListener);
        if(position==0){
            viewHodler.toLeftIV.setVisibility(View.GONE);
            viewHodler.toRightIV.setVisibility(View.VISIBLE);
        }else if(position==(getItemCount()-1)){
            viewHodler.toLeftIV.setVisibility(View.VISIBLE);
            viewHodler.toRightIV.setVisibility(View.GONE);
        }else {
            viewHodler.toLeftIV.setVisibility(View.VISIBLE);
            viewHodler.toRightIV.setVisibility(View.VISIBLE);
        }
        int nowPagerShow=position+1;
        viewHodler.pagerTV.setText(nowPagerShow+"/"+list.size());
        viewHodler.toLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=position-1;
                if(index>=0&&getItemCount()!=0){
                    if(null!=itemClick){
                        itemClick.nowNeedToPager(index);
                    }
                }
            }
        });
        viewHodler.toRightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=position+1;
                if(getItemCount()!=0&&index<getItemCount()){
                    if(null!=itemClick){
                        itemClick.nowNeedToPager(index);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView toLeftIV,toRightIV;
        private LinearLayout contentLL;
        private TextView titleTV,contentTV,pagerTV;

        public ViewHodler(View itemView) {
            super(itemView);
            toLeftIV=itemView.findViewById(R.id.iv_to_left);
            toRightIV=itemView.findViewById(R.id.iv_to_right);
            contentLL=itemView.findViewById(R.id.ll_content);
            titleTV=itemView.findViewById(R.id.tv_title);
            contentTV=itemView.findViewById(R.id.tv_content);
            pagerTV=itemView.findViewById(R.id.tv_pager);
        }
    }

    public interface ItemClick {
        public void nowSelect(int index,Object obj);

        public void nowNeedToPager(int index);
    }


    public abstract ShowInfoBean getShowInfo(Object obj);


}