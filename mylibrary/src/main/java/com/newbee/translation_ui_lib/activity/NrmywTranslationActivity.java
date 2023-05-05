//package com.newbee.translation_ui_lib.activity;
//
//
//
//import com.newbee.translation_ui_lib.activity.base.BaseTranslationSimpleOneDialogActivity;
//
//
//public class NrmywTranslationActivity extends BaseTranslationSimpleOneDialogActivity {
//
//
//    private MsgProcess msgProcess;
//
//    @Override
//    public BaseVoiceToTextProcess getVoiceToTextProcess() {
//        if(null==msgProcess){
//            synchronized (NrmywTranslationActivity.class){
//                if(null==msgProcess){
//                    msgProcess=new MsgProcess(this);
//                }
//            }
//        }
//        return msgProcess;
//    }
//
//    @Override
//    public Class getFromLangActivity() {
//        return FromLangActivity.class;
//    }
//
//    @Override
//    public Class getToLangActivity() {
//        return ToLangActivity.class;
//    }
//
//
//}
