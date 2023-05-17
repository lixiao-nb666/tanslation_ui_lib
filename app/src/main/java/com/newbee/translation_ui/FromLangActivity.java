package com.newbee.translation_ui;


import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;
import com.newbee.translation_ui_lib.activity.base.BaseSelectLangActivity;
import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;
import java.util.ArrayList;
import java.util.List;

public class FromLangActivity extends BaseSelectLangActivity {


    @Override
    public List<Object> getList() {
        List<Object> list=new ArrayList<>();
        int size=30;
        for(int i = 0; i< size; i++){
            list.add("lixiao:"+i);
        }

//        list.add("numb is ok:"+(YdLangType.values().length==numb));
//        Log.i("kankanduibudui","numb is ok:"+(YdLangType.values().length==list.size()));
        return list;
    }





    @Override
    public void nowSelectNeedTodo(int index,Object object) {

        showToast(object.toString());
        finish();
    }

    @Override
    public String nowStr() {
        return "from";
    }

    @Override
    public BaseNewBeeSelectToAdapter getAdapter() {
        return new NewBeeSelectToAdapter(context,"");
    }

    @Override
    public Class backToActivity() {
        return aaaaaa.class;
    }
}
