package com.newbee.translation_ui_lib.activity.base;

import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;
import com.newbee.translation_ui_lib.R;
import com.newbee.translation_ui_lib.adapter.BaseNewBeeSelectToAdapter;
import com.newbee.translation_ui_lib.key.event.ActivityKeyDownListUtil;
import com.newbee.translation_ui_lib.key.event.KeyCodesEventType;


import java.util.List;

public abstract class BaseSelectLangActivity extends BaseCompatActivity {

    public abstract List<Object> getList();

    public abstract void nowSelectNeedTodo(int index);

    public abstract String nowStr();

    public abstract BaseNewBeeSelectToAdapter getAdapter();


    private BaseNewBeeSelectToAdapter.ItemClick itemClick = new BaseNewBeeSelectToAdapter.ItemClick() {
        @Override
        public void nowSelect(int index) {
            nowSelectNeedTodo(index);
        }

        @Override
        public void nowNeedToPager(int index) {
            initRV.scrollToPosition(index);
        }
    };

    private RecyclerView initRV;


    private BaseNewBeeSelectToAdapter initAdapter;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_select_launcher;
    }

    @Override
    public void initView() {
        initRV = findViewById(R.id.rv_init);
        LinearLayoutManager initLM = new LinearLayoutManager(this);
        initLM.setOrientation(LinearLayoutManager.HORIZONTAL);

        initRV.setLayoutManager(initLM);
    }


    @Override
    public void initData() {
        initAdapter = getAdapter();
    }

    @Override
    public void initControl() {

        initAdapter.setClick(itemClick);
        initRV.setAdapter(initAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(initRV);

        keyEventUtil.setListen(keyEventListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.TOP.ordinal(), ActivityKeyDownListUtil.toTopList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.DOWN.ordinal(), ActivityKeyDownListUtil.toDownList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList());

    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }


    public int getNowShowIndex() {

        int index = -1;
        int childCount = initRV.getChildCount();
        if (childCount > 0) {
            View lastChild = initRV.getChildAt(childCount - 1);
            index = initRV.getChildAdapterPosition(lastChild);
        }
        return index;
    }

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
                    int toLeftIndex=getNowShowIndex()-1;
                    if(toLeftIndex>=0&&initAdapter.getItemCount()!=0){
                        initRV.scrollToPosition(toLeftIndex);
                    }
                    break;
                case RIGHT:
                case DOWN:
                    int toRightIndex=getNowShowIndex()+1;
                    if(initAdapter.getItemCount()!=0&&toRightIndex<initAdapter.getItemCount()){
                        initRV.scrollToPosition(toRightIndex);
                    }
                    break;


            }
        }
    };

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