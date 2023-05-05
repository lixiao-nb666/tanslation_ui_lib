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

    public void getStatu(String str);

    public void isConnect();

    public void isColse(String errStr);

    public  void startOk();

    public void getRecogingStr(String str);

    public void getRecogFinishedStr(String str);

    public void getTranslationStr(String str);

    public void getRecogingAndTranslationStr(String recogStr,String tanslationStr);

    public void getRecogFinshAndTranslationStr(String recogStr,String tanslationStr);

    public void onError(int code, String result);
}
