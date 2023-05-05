package com.newbee.translation_ui_lib.manager;

import android.text.TextUtils;
import com.newbee.translation_ui_lib.bean.NewBeeRecogTextBean;
import java.util.ArrayList;
import java.util.List;

public class NewBeeRecogTextManager {

    private static NewBeeRecogTextManager newBeeRecogTextManager;
    private List<NewBeeRecogTextBean> list=new ArrayList<>();


    private NewBeeRecogTextManager(){}

    public static NewBeeRecogTextManager getInstance(){
        if(null==newBeeRecogTextManager){
            synchronized (NewBeeRecogTextManager.class){
                if(null==newBeeRecogTextManager){
                    newBeeRecogTextManager=new NewBeeRecogTextManager();
                }
            }
        }
        return newBeeRecogTextManager;
    }

    public NewBeeRecogTextBean setInitRecogingStr(String str){

        if(list.size()==0){
           addTextBean();
        }else {
            NewBeeRecogTextBean  newBeeRecogTextBean =list.get(list.size()-1);
            if(!TextUtils.isEmpty(newBeeRecogTextBean.getRecogFinshedStr())){
                addTextBean();
            }
        }
        NewBeeRecogTextBean nowTextBean=list.get(list.size()-1);
        nowTextBean.setRecogingStr(str);
        return nowTextBean;

    }

    public NewBeeRecogTextBean setInitFinshedStr(String str){
        if(list.size()==0){
            addTextBean();
        }else {
            NewBeeRecogTextBean  newBeeRecogTextBean =list.get(list.size()-1);
            if(!TextUtils.isEmpty(newBeeRecogTextBean.getRecogFinshedStr())){
                addTextBean();
            }
        }
        NewBeeRecogTextBean nowTextBean=list.get(list.size()-1);
        nowTextBean.setRecogFinshedStr(str);
        return nowTextBean;
    }

    public NewBeeRecogTextBean setTransStr(String str){
        try {
            NewBeeRecogTextBean  newBeeRecogTextBean =list.get(list.size()-1);
            if(TextUtils.isEmpty(newBeeRecogTextBean.getRecogFinshedStr())){
                newBeeRecogTextBean.setTransStr(str);
            }else {
                newBeeRecogTextBean.setTranFinshStr(str);
            }
            return newBeeRecogTextBean;
        }catch (Exception e){
            return null;
        }
    }

    public NewBeeRecogTextBean getNowTextBean(){
        try {
            NewBeeRecogTextBean  newBeeRecogTextBean =list.get(list.size()-1);
            return newBeeRecogTextBean;
        }catch (Exception e){
            return null;
        }


    }



    private void addTextBean(){
            if(list.size()==0){
                list.add(new NewBeeRecogTextBean());
            }else {
                NewBeeRecogTextBean lastBean=list.get(list.size()-1);
                list.add(new NewBeeRecogTextBean(lastBean.getIndex()+1));
            }


    }
}
