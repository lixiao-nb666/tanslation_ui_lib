package com.newbee.translation_ui_lib.event;


public interface VoiceToTextEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(VoiceToTextEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(VoiceToTextEventObserver observer);

    public void isConnect();

    public void isColse(String errStr);

    public  void startOk();

    public void getRecogingStr(String str);

    public  void getStatusFinishedStr(String str);

    public  void getRecogingTranslationStr(String str);

    public void onError(int code, String result);
}
