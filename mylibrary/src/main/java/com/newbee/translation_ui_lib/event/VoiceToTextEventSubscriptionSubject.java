package com.newbee.translation_ui_lib.event;

import java.util.ArrayList;
import java.util.List;

public class VoiceToTextEventSubscriptionSubject implements VoiceToTextEventSubject {

    private List<VoiceToTextEventObserver> observers;
    private static VoiceToTextEventSubscriptionSubject subscriptionSubject;

    private VoiceToTextEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static VoiceToTextEventSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (VoiceToTextEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new VoiceToTextEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }

    @Override
    public void attach(VoiceToTextEventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(VoiceToTextEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void isConnect() {
        for (VoiceToTextEventObserver observer : observers) {
            observer.isConnect();
        }
    }

    @Override
    public void isColse(String errStr) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.isColse(errStr);
        }
    }

    @Override
    public void startOk() {
        for (VoiceToTextEventObserver observer : observers) {
            observer.startOk();
        }
    }

    @Override
    public void getRecogingStr(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getRecogingStr(str);
        }
    }

    @Override
    public void getStatusFinishedStr(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getStatusFinishedStr(str);
        }
    }

    @Override
    public void getRecogingTranslationStr(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getRecogingTranslationStr(str);
        }
    }

    @Override
    public void onError(int code, String result) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.onError(code,result);
        }
    }



}
