package com.newbee.translation_ui_lib.util;

import android.text.TextUtils;
import android.widget.TextView;
import com.newbee.translation_ui_lib.bean.NewBeeRecogTextBean;


public class SetTextUtil {

        public static void setText(TextView textView, NewBeeRecogTextBean textBean, boolean isInit){
            if(isInit){
                if(TextUtils.isEmpty(textBean.getRecogFinshedStr())){
                    textView.setText(textBean.getRecogingStr());
                }else {
                    textView.setText(textBean.getRecogFinshedStr());
                }
            }else {
                if(TextUtils.isEmpty(textBean.getTranFinshStr())){
                    textView.setText(textBean.getTransStr());
                }else {
                    textView.setText(textBean.getTranFinshStr());
                }
            }
        }


    public static void setText(TextView textView, NewBeeRecogTextBean  textBean, boolean isInit,int defColor,int finshColor){
        if(isInit){
            if(TextUtils.isEmpty(textBean.getRecogFinshedStr())){
                textView.setTextColor(defColor);
                textView.setText(textBean.getRecogingStr());
            }else {
                textView.setTextColor(finshColor);
                textView.setText(textBean.getRecogFinshedStr());
            }
        }else {
            if(TextUtils.isEmpty(textBean.getTranFinshStr())){
                textView.setTextColor(defColor);
                textView.setText(textBean.getTransStr());
            }else {
                textView.setTextColor(finshColor);
                textView.setText(textBean.getTranFinshStr());
            }
        }
    }


    public static void setText(TextView textView, String str,int defColor){
        textView.setTextColor(defColor);
        textView.setText(str);
    }

}
