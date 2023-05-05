package com.newbee.translation_ui_lib.event;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface VoiceToTextEventObserver {

    public void isConnect();

    public void isColse(String errStr);

   public void startOk();

    public void getRecogingStr(String str);

    public void getStatusFinishedStr(String str);

    public void getRecogingTranslationStr(String str);

    public void onError(int code, String result);
}
