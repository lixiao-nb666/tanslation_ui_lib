package com.newbee.translation_ui_lib.app;

import com.newbee.alarm_lib.manager.TaoziTimeClockManager;
import com.newbee.alarm_lib.service.TaoziTimeClockServiceDao;
import com.newbee.bulid_lib.mybase.appliction.BaseApplication;

public class BaseTranslationApp extends BaseApplication {

    private TaoziTimeClockServiceDao serviceDao;

    @Override
    protected void init() {
        //初始化时间监听控件
        TaoziTimeClockManager.getInstance();
        serviceDao=new TaoziTimeClockServiceDao(this,null);
        serviceDao.startServiceIsBind();
    }

    @Override
    protected void needClear(String str) {

    }

    @Override
    protected void close() {
        if(null!=serviceDao){
            //里面包含了清空时间控件所以不用动
            serviceDao.stopServiceIsBind();
        }
    }
}
