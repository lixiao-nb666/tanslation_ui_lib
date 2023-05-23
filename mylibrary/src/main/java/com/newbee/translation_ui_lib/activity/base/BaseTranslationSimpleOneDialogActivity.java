package com.newbee.translation_ui_lib.activity.base;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.activity.util.ActivityManager;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;
import com.newbee.translation_ui_lib.R;
import com.newbee.translation_ui_lib.adapter.NewBeeTextHistoryAdapter;
import com.newbee.translation_ui_lib.bean.NewBeeRecogTextBean;
import com.newbee.translation_ui_lib.event.VoiceToTextEventObserver;
import com.newbee.translation_ui_lib.event.VoiceToTextEventSubscriptionSubject;
import com.newbee.translation_ui_lib.key.event.ActivityKeyDownListUtil;
import com.newbee.translation_ui_lib.key.event.KeyCodesEventType;
import com.newbee.translation_ui_lib.manager.NewBeeRecogTextManager;
import com.newbee.translation_ui_lib.process.BaseVoiceToTextProcess;
import com.newbee.translation_ui_lib.util.SetTextUtil;


public abstract class BaseTranslationSimpleOneDialogActivity extends BaseCompatActivity {
    public abstract BaseVoiceToTextProcess getVoiceToTextProcess();
    public abstract Class getFromLangActivity();
    public abstract Class getToLangActivity();
    public abstract String getFromLangStr();
    public abstract String getToLangStr();


    private TextView transTV,tsStatuTV;
    private boolean isTs;
    private Button toBT,fromBT;
    private RecyclerView historyRV;
    private NewBeeTextHistoryAdapter adapter;

    private VoiceToTextEventObserver voiceToTextEventObserver = new VoiceToTextEventObserver() {
        @Override
        public void getStatu(String str) {
            Message msg = new Message();
            msg.what = UpdateUiType.updateStatuStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void isConnect() {
            String str=useRsgetString(R.string.statu_net_ok);
            Message msg = new Message();
            msg.what = UpdateUiType.updateOkStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void isColse(String errStr) {
            String str=errStr;
            Message msg = new Message();
            msg.what = UpdateUiType.updateErrStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void startOk() {
            String str=useRsgetString(R.string.statu_start_ok);
            Message msg = new Message();
            msg.what = UpdateUiType.updateOkStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void getRecogingStr(String str) {
            if(TextUtils.isEmpty(str)){
                return;
            }
            NewBeeRecogTextManager.getInstance().setInitRecogingStr(str);
        }

        @Override
        public void getRecogFinishedStr(String str) {
            if(TextUtils.isEmpty(str)){
                return;
            }
            NewBeeRecogTextManager.getInstance().setInitFinshedStr(str);
        }

        @Override
        public void getTranslationStr(String str) {
            if(TextUtils.isEmpty(str)){
                return;
            }
            NewBeeRecogTextManager.getInstance().setTransStr(str);
            uiHandler.sendEmptyMessage(UpdateUiType.updateTextTransStr.ordinal());
        }

        @Override
        public void getRecogingAndTranslationStr(String recogStr, String tanslationStr) {

            NewBeeRecogTextManager.getInstance().setInitRecogingStr(recogStr);
            NewBeeRecogTextManager.getInstance().setTransStr(tanslationStr);
            uiHandler.sendEmptyMessage(UpdateUiType.updateTextTransStr.ordinal());
        }

        @Override
        public void getRecogFinshAndTranslationStr(String recogStr, String tanslationStr) {
            NewBeeRecogTextManager.getInstance().setInitFinshedStr(recogStr);
            NewBeeRecogTextManager.getInstance().setTransStr(tanslationStr);
            uiHandler.sendEmptyMessage(UpdateUiType.updateTextTransStr.ordinal());
        }


        @Override
        public void onError(int code, String result) {
            String str=code+" : "+result;
            Message msg = new Message();
            msg.what = UpdateUiType.updateErrStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }
    };

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String str;
            NewBeeRecogTextBean textBean;
            UpdateUiType uiType= UpdateUiType.values()[msg.what];
            switch (uiType) {
                case updateStatuStr:
                    isTs=true;
                    str = (String) msg.obj;
                    SetTextUtil.setText(tsStatuTV,str );
                    break;
                case updateOkStr:
                    str = (String) msg.obj;
                    SetTextUtil.setText(transTV,str,useRsgetColor(R.color.text_translation_over_color) );
                    if(isTs){
                        isTs=false;
                        SetTextUtil.setText(tsStatuTV,"" );
                    }
                    break;
                case updateErrStr:
                    str = (String) msg.obj;
                    SetTextUtil.setText(transTV,str,useRsgetColor(com.newbee.bulid_lib.R.color.red));
                    if(isTs){
                        isTs=false;
                        SetTextUtil.setText(tsStatuTV,"" );
                    }
                    break;
                case updateTextTransStr:
                    textBean = NewBeeRecogTextManager.getInstance().getNowTextBean();
                    if(null==textBean){
                        return;
                    }
                    SetTextUtil.setText(transTV,textBean,false ,useRsgetColor(com.newbee.bulid_lib.R.color.white),useRsgetColor(R.color.text_translation_over_color));
                    adapter.setText(textBean);
                    if(isTs){
                        isTs=false;
                        SetTextUtil.setText(tsStatuTV,"" );
                    }
                    break;

            }

        }
    };


    private SystemKeyEvent keyEventUtil = new SystemKeyEvent();
    private SystemKeyEventListen keyEventListen=new SystemKeyEventListen() {
        @Override
        public void nowCanDoEvent(int eventTypeInt) {
            KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
            switch (eventType) {
                case NONE:
                    break;
                case LEFT:
                    fromBT.setFocusable(true);
                    break;
                case RIGHT:
                    toBT.setFocusable(true);
                    break;
                case TOP:
                   int toTopIndex=getNowShowIndex()+1;
                    if(adapter.getItemCount()!=0&&toTopIndex<adapter.getItemCount()){
                        historyRV.smoothScrollToPosition(toTopIndex);
                    }
                    break;
                case DOWN:
                    int toDownIndex=getNowShowIndex()-1;
                    if(toDownIndex>=0&&adapter.getItemCount()!=0){
                        historyRV.smoothScrollToPosition(toDownIndex);
                    }
                    break;
                case BACK:
                    ActivityManager.getInstance().finishAllActivity();
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



    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_newbee_simple_one_dialog_recog;
    }


    private View.OnFocusChangeListener onFocusChangeListener=new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v instanceof Button){
                Button nowBt= (Button) v;
                if(hasFocus){
                    nowBt.setTextColor(context.getResources().getColor(com.newbee.bulid_lib.R.color.black));
                }else {
                    nowBt.setTextColor(context.getResources().getColor(R.color.text_translation_over_color));
                }
            }
        }
    };
    @Override
    public void initView() {
        transTV=findViewById(R.id.tv_tran);
        tsStatuTV=findViewById(R.id.tv_ts_statu);
        toBT=findViewById(R.id.bt_to);

        toBT.setOnFocusChangeListener(onFocusChangeListener);
        toBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BaseTranslationSimpleOneDialogActivity.this, getToLangActivity());
                startActivity(intent);

            }
        });
        fromBT= findViewById(R.id.bt_from);
        fromBT.setOnFocusChangeListener(onFocusChangeListener);
        fromBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BaseTranslationSimpleOneDialogActivity.this, getFromLangActivity());
                startActivity(intent);
            }
        });
        fromBT.setFocusable(true);
        //设置历史布局
        historyRV = findViewById(R.id.rv_history);
        initLM= new LinearLayoutManager(this);
        initLM.setOrientation(LinearLayoutManager.VERTICAL);
        initLM.setReverseLayout(true);
        historyRV.setLayoutManager(initLM);
        adapter = new NewBeeTextHistoryAdapter(this, null, false);
        historyRV.setAdapter(adapter);
        NewBeeRecogTextManager.getInstance().clear();
    }


    private LinearLayoutManager initLM;
    @Override
    public void initData() {
        keyEventUtil.setListen(keyEventListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.BACK.ordinal(), ActivityKeyDownListUtil.back());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.DOWN.ordinal(), ActivityKeyDownListUtil.toDownList());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList());
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {
        VoiceToTextEventSubscriptionSubject.getInstence().detach(voiceToTextEventObserver);
        getVoiceToTextProcess().close();
        keyEventUtil.close();
        uiHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void viewIsShow() {
        getVoiceToTextProcess().startReadAudio(this);
        VoiceToTextEventSubscriptionSubject.getInstence().attach(voiceToTextEventObserver);
        fromBT .setText(getFromLangStr());
        toBT.setText(getToLangStr());
        keyEventUtil.start();

    }

    @Override
    public void viewIsPause() {
        VoiceToTextEventSubscriptionSubject.getInstence().detach(voiceToTextEventObserver);
        getVoiceToTextProcess().pause();
        keyEventUtil.pause();
    }


    @Override
    public void changeConfig() {

    }


    private int useRsgetColor(int rsColor){
        return context.getApplicationContext().getResources().getColor(rsColor);
    }

    private String useRsgetString(int rsStr){
        return context.getApplicationContext().getResources().getString(rsStr);
    }

    public int getNowShowIndex() {
        int index = -1;
        int childCount =historyRV.getChildCount();
        if (childCount > 0) {
            View lastChild = historyRV.getChildAt(childCount - 1);
            index = historyRV.getChildAdapterPosition(lastChild);
        }
        return index;
    }
}
