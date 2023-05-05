package com.newbee.translation_ui_lib.activity.base;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;
import com.newbee.translation_ui_lib.R;
import com.newbee.translation_ui_lib.adapter.NewBeeTextHistoryAdapter;
import com.newbee.translation_ui_lib.bean.NewBeeRecogTextBean;
import com.newbee.translation_ui_lib.event.VoiceToTextEventObserver;
import com.newbee.translation_ui_lib.event.VoiceToTextEventSubscriptionSubject;
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


    private TextView transTV;
    private Button toBT,fromBT;
    private RecyclerView historyRV;
    private NewBeeTextHistoryAdapter adapter;

    private VoiceToTextEventObserver voiceToTextEventObserver = new VoiceToTextEventObserver() {
        @Override
        public void isConnect() {

            String str="net isConnect!";
            Message msg = new Message();
            msg.what = UpdateUiType.updateStatuStr.ordinal();
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

            String str="Start Ok,Please speak!";
            Message msg = new Message();
            msg.what = UpdateUiType.updateStatuStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
        }

        @Override
        public void getRecogingStr(String str) {

            if(TextUtils.isEmpty(str)){
                return;
            }
            Message msg = new Message();
            msg.what = UpdateUiType.updateRecogingStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);
//
//          NewBeeRecogTextBean  textBean = NewBeeRecogTextManager.getInstance().setInitRecogingStr(str);
//            if (null == textBean) {
//                return;
//            }
//            SetTextUtil.setText(initTV,textBean,true);
//            TextTransUtil.getInstance().getTextTrans(str, textBean.getIndex());
        }

        @Override
        public void getStatusFinishedStr(String str) {

            if(TextUtils.isEmpty(str)){
                return;
            }
            Message msg = new Message();
            msg.what = UpdateUiType.updateRecogFinshStr.ordinal();
            msg.obj = str;
            uiHandler.sendMessage(msg);

//            NewBeeRecogTextBean  textBean = NewBeeRecogTextManager.getInstance().setInitRecogingStr(str);
//            if (null == textBean) {
//                return;
//            }
//            SetTextUtil.setText(initTV,textBean,true);
//            TextTransUtil.getInstance().getTextTrans(str, textBean.getIndex());
        }

        @Override
        public void getRecogingTranslationStr(String str) {
            if(TextUtils.isEmpty(str)){
                return;
            }
            Message msg=new Message();
            msg.what= UpdateUiType.updateTextTransStr.ordinal();
            msg.arg1=index;
            msg.obj=str;
            uiHandler.sendMessage(msg);
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
                    str = (String) msg.obj;
                    SetTextUtil.setText(transTV,str, R.color.text_translation_over_color);
                    break;
                case updateErrStr:
                    str = (String) msg.obj;
                    SetTextUtil.setText(transTV,str,context.getApplicationContext().getResources().getColor(com.newbee.bulid_lib.R.color.red));
                    break;
                case updateRecogingStr:
                    str = (String) msg.obj;
                    textBean = NewBeeRecogTextManager.getInstance().setInitRecogingStr(str);
                    if (null == textBean) {
                        return;
                    }
                    break;
                case updateRecogFinshStr:
                    str = (String) msg.obj;
                    textBean = NewBeeRecogTextManager.getInstance().setInitFinshedStr(str);
                    if (null == textBean) {
                        return;
                    }
                    break;
                case updateTextTransStr:
                    str = (String) msg.obj;
                    int index=msg.arg1;
                    textBean = NewBeeRecogTextManager.getInstance().setTransStr(str, index);
                    SetTextUtil.setText(transTV,textBean,false , context.getApplicationContext().getResources().getColor(com.newbee.bulid_lib.R.color.white),context.getApplicationContext().getResources().getColor(R.color.text_translation_over_color));
                    adapter.setText(textBean);
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
                    if(adapter.getItemCount()<=0){
                        return;
                    }

                    getNowIndex();
                    index++;
                    if (index >= adapter.getItemCount()) {
                        index = adapter.getItemCount() - 1;
                    }
                    historyRV.smoothScrollToPosition(index);
                    break;
                case DOWN:
                    if(adapter.getItemCount()<=0){
                        return;
                    }
                    getNowIndex();
                    index--;
                    if (index < 0) {
                        index = 0;
                    }
                    historyRV.smoothScrollToPosition(index);
                    break;

            }
        }
    };


    private int index;
    private void getNowIndex() {
        if (historyRV != null && historyRV.getChildCount() > 0) {
            try {
                index = ((RecyclerView.LayoutParams) historyRV.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
                index= initLM.findLastCompletelyVisibleItemPosition();
                return ;
            } catch (Exception e) {

            }
        }
        return;

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



    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_newbee_simple_one_dialog_recog_2;
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
    }


    private LinearLayoutManager initLM;
    @Override
    public void initData() {
        keyEventUtil.setListen(keyEventListen);
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.TOP.ordinal(), ActivityKeyDownListUtil.toTopList());
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



}
