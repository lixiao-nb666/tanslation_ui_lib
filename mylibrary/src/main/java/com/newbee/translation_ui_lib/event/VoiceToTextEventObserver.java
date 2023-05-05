package com.newbee.translation_ui_lib.event;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface VoiceToTextEventObserver {

    public void getStatu(String str);

    public void isConnect();



    public void isColse(String errStr);

    public void startOk();

    public void getRecogingStr(String str);

    public void getRecogFinishedStr(String str);

    public void getTranslationStr(String str);

    public void getRecogingAndTranslationStr(String recogStr,String tanslationStr);

    public void getRecogFinshAndTranslationStr(String recogStr,String tanslationStr);

    public void onError(int code, String result);
}
