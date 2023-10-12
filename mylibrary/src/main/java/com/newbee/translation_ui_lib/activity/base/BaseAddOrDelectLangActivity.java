package com.newbee.translation_ui_lib.activity.base;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.newbee.bulid_lib.mybase.LG;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;
import com.newbee.translation_ui_lib.R;

import com.newbee.translation_ui_lib.bean.ShowInfoBean;
import com.newbee.translation_ui_lib.key.event.ActivityKeyDownListUtil;
import com.newbee.translation_ui_lib.key.event.KeyCodesEventType;

import java.util.List;

public abstract class BaseAddOrDelectLangActivity extends BaseCompatActivity {




    public abstract List<Object> getList();

    public abstract void nowSelectNeedTodo(int index,Object object);

    public abstract String activityTitleStr();

    public abstract String nowShareStr();

    public abstract Class returnToClass();

    public abstract ShowInfoBean getShowInfo(Object obj);

    private TextView activityTitleTV;
    private TextView lastContentTV,nowContentTV,nextContentTV;
    private TextView pagerTV;
    private List<Object>list;

    private SystemKeyEvent keyEventUtil = new SystemKeyEvent();
    private SystemKeyEventListen keyEventListen=new SystemKeyEventListen() {
        @Override
        public void nowCanDoEvent(int eventTypeInt) {
            KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
            switch (eventType) {
                case NONE:
                    break;
                case LEFT:
                case TOP:
                    setToLast();
                    break;
                case RIGHT:
                case DOWN:
                    setToNext();
                    break;
                case QUE:
                    nowSelect();
                    break;
                case BACK:
                    ActivityManager.getInstance().finishAllActivity();
                    toActivity(returnToClass());
                    break;
            }
        }
    };


    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_add_or_delect_launcher;
    }

    @Override
    public void initView() {
        activityTitleTV=findViewById(R.id.tv_activity_title);
        lastContentTV=findViewById(R.id.tv_last_content);
        nowContentTV=findViewById(R.id.tv_now_content);
        nextContentTV=findViewById(R.id.tv_next_content);
        pagerTV=findViewById(R.id.tv_pager);

    }

    private void setToLast(){
        index--;
        if (index < 0) {
            index = list.size() - 1;
        }
        MyShare.getInstance().putString(getShareStr(),index+"");
        showContent();
    }

    private void setToNext(){
        index++;
        if (index >= list.size()) {
            index = 0;
        }
        MyShare.getInstance().putString(getShareStr(),index+"");
        showContent();
    }

    private void nowSelect(){
        int nowIndex=index;
        if(nowIndex<list.size()&&nowIndex>=0){
            //处理中间的事件
            nowSelectNeedTodo(index,list.get(index));
            //选中之后，下次位置恢复到0
            MyShare.getInstance().putString(getShareStr(),0+"");
        }
    }

    @Override
    public void initData() {
        list=getList();
        try {
            String shareIndexStr=MyShare.getInstance().getString(getShareStr());
            if(!TextUtils.isEmpty(shareIndexStr)){
                index=Integer.valueOf(shareIndexStr);
            }
        }catch (Exception e){

        }
        showContent();
    }

    @Override
    public void initControl() {
        activityTitleTV.setText(activityTitleStr());
        nowContentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowSelect();
            }
        });
        findViewById(R.id.iv_to_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToLast();
            }
        });
        findViewById(R.id.iv_to_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToNext();
            }
        });
        keyEventUtil.setListen(keyEventListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.TOP.ordinal(), ActivityKeyDownListUtil.toTopList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.DOWN.ordinal(), ActivityKeyDownListUtil.toDownList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.BACK.ordinal(), ActivityKeyDownListUtil.back());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk1());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk2());
    }


    @Override
    public void closeActivity() {
        keyEventUtil.close();

    }

    @Override
    public void viewIsShow() {
        keyEventUtil.start();
    }

    @Override
    public void viewIsPause() {
        keyEventUtil.pause();
    }

    @Override
    public void changeConfig() {

    }
    private String getShareStr(){
        return this.getClass().getSimpleName()+nowShareStr();
    }


    private void showContent(){
        if(null==list||list.size()==0){
            nowContentTV.setText("");
            lastContentTV.setText("");
            nextContentTV.setText("");
            pagerTV.setText("");
        }else {
            showNow();
            showLast();
            showNext();
        }
    }

    private int index;
    private void showNow(){
        int nowIndex=index;
        if(nowIndex<list.size()&&nowIndex>=0){
            //处理中间的事件
            ShowInfoBean nowShowInfoBean=getShowInfo(list.get(nowIndex));
            nowContentTV.setText(nowShowInfoBean.getContent());
            pagerTV.setText((index+1)+"/"+list.size());
        }else {
            nowContentTV.setText("");
        }
    }
    private void showLast(){
        int lastIndex=index-1;
        if(lastIndex<list.size()&&lastIndex>=0){
            //处理左边的事件
            ShowInfoBean nowShowInfoBean=getShowInfo(list.get(lastIndex));
            lastContentTV.setText(nowShowInfoBean.getContent());
        }else {
            lastContentTV.setText("");
        }
    }

    private void showNext(){
        int nextIndex=index+1;
        if(nextIndex<list.size()&&nextIndex>=0){
            //处理右边的事件
            ShowInfoBean nowShowInfoBean=getShowInfo(list.get(nextIndex));
            nextContentTV.setText(nowShowInfoBean.getContent());
        }else {
            nextContentTV.setText("");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyEventUtil.nowClickKeyCode(event)) {
            return  true ;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 截获按键事件.发给ScanGunKeyEventHelper
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (keyEventUtil.nowClickKeyCode(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
