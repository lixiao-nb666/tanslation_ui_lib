package com.newbee.translation_ui;

import android.content.Context;

import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;
import com.newbee.translation_ui_lib.bean.ShowInfoBean;


public class NewBeeSelectToAdapter extends BaseNewBeeSelectToAdapter {

    public NewBeeSelectToAdapter(Context context, String nowStr) {
            super(context,nowStr);
    }

    @Override
    public ShowInfoBean getShowInfo(Object object) {
        if(null==object){
            return null;
        }

        ShowInfoBean showInfoBean=new ShowInfoBean();

        showInfoBean.setTitle(object.toString());
        return showInfoBean;
    }




}