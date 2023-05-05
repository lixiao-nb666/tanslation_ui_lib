package com.newbee.translation_ui_lib.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class NewBeeRecogTextBean implements Serializable {
    private int index;
    private String recogingStr;
    private String recogFinshedStr;
    private String transStr;
    private String tranFinshStr;

    public NewBeeRecogTextBean() {

    }

    public NewBeeRecogTextBean(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getRecogingStr() {
        return recogingStr;
    }

    public void setRecogingStr(String recogingStr) {

        this.recogingStr = recogingStr.trim();
    }

    public String getRecogFinshedStr() {
        return recogFinshedStr;
    }

    public void setRecogFinshedStr(String recogFinshedStr) {


        this.recogFinshedStr = recogFinshedStr;
        if(!TextUtils.isEmpty(this.recogingStr)){
            this.recogFinshedStr.trim();
        }
    }

    public String getTransStr() {
        return transStr;
    }

    public void setTransStr(String transStr) {
        this.transStr = transStr.trim();
    }

    public String getTranFinshStr() {
        return tranFinshStr;
    }

    public void setTranFinshStr(String tranFinshStr) {
        this.tranFinshStr = tranFinshStr.trim();
    }

    @Override
    public String toString() {
        return "NewBeeRecogTextBean{" +
                "index=" + index +
                ", recogingStr='" + recogingStr + '\'' +
                ", recogFinshedStr='" + recogFinshedStr + '\'' +
                ", transStr='" + transStr + '\'' +
                ", tranFinshStr='" + tranFinshStr + '\'' +
                '}';
    }
}
