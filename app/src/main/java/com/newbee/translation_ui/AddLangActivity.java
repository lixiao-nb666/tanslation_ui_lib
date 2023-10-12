package com.newbee.translation_ui;


import com.newbee.translation_ui_lib.activity.base.BaseAddOrDelectLangActivity;
import com.newbee.translation_ui_lib.activity.base.BaseSelectLangActivity;
import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;
import com.newbee.translation_ui_lib.bean.ShowInfoBean;

import java.util.ArrayList;
import java.util.List;

public class AddLangActivity extends BaseAddOrDelectLangActivity {


    @Override
    public List<Object> getList() {
        List<Object> list=new ArrayList<>();
        int size=30;
        for(int i = 0; i< size; i++){
            list.add("lixiao:"+i);
        }
        return list;
    }





    @Override
    public void nowSelectNeedTodo(int index,Object object) {

        showToast(object.toString());
        finish();
    }

    @Override
    public String activityTitleStr() {
        return "diao mao in the house";
    }

    @Override
    public String nowShareStr() {
        return "from";
    }



    @Override
    public Class returnToClass() {
        return aaaaaa.class;
    }

    @Override
    public ShowInfoBean getShowInfo(Object object) {
        if(null==object){
            return null;
        }
        ShowInfoBean showInfoBean=new ShowInfoBean();
        showInfoBean.setTitle(object.toString());
        showInfoBean.setContent(object.toString());
        return showInfoBean;
    }

}
