package com.newbee.translation_ui_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.newbee.translation_ui_lib.R;
import com.newbee.translation_ui_lib.bean.NewBeeRecogTextBean;
import com.newbee.translation_ui_lib.util.SetTextUtil;

import java.util.HashMap;
import java.util.Map;


public class NewBeeTextHistoryAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private LayoutInflater layoutInflater;
    private Map<String, NewBeeRecogTextBean> map=new HashMap<>();
    private boolean isInit;

    public NewBeeTextHistoryAdapter(Context context, ItemClick itemClick, boolean isInit) {
        layoutInflater=LayoutInflater.from(context);
        this.isInit=isInit;
    }


    public void setText(NewBeeRecogTextBean textBean){
        map.put(textBean.getIndex()+"",textBean);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.adapter_newbee_history_text, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int needP=map.size()-position-2;
        NewBeeRecogTextBean textBean=map.get(needP+"");
        if(null==textBean){
            return;
        }
        ViewHodler viewHodler= (ViewHodler) holder;
        SetTextUtil.setText(viewHodler.textyTV,textBean,isInit);
    }

    @Override
    public int getItemCount() {
        if(map.size()>0){
            return map.size()-1;
        }
        return 0;
    }


    class ViewHodler extends RecyclerView.ViewHolder {

        private TextView textyTV;

        public ViewHodler(View itemView) {
            super(itemView);
            textyTV=itemView.findViewById(R.id.tv_text);
        }
    }

    public interface ItemClick {

    }
}