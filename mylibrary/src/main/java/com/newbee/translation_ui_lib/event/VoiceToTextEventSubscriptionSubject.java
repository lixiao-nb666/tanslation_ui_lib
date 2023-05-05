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
    public void getStatu(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getStatu(str);
        }
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
    public void getRecogFinishedStr(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getRecogFinishedStr(str);
        }
    }

    @Override
    public void getTranslationStr(String str) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getTranslationStr(str);
        }
    }

    @Override
    public void getRecogingAndTranslationStr(String recogStr, String tanslationStr) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getRecogingAndTranslationStr(recogStr,tanslationStr);
        }
    }

    @Override
    public void getRecogFinshAndTranslationStr(String recogStr, String tanslationStr) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.getRecogFinshAndTranslationStr(recogStr,tanslationStr);
        }
    }



    @Override
    public void onError(int code, String result) {
        for (VoiceToTextEventObserver observer : observers) {
            observer.onError(code,result);
        }
    }



}
