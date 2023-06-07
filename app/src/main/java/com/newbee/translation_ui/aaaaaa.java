package com.newbee.translation_ui;

import com.newbee.translation_ui_lib.activity.base.BaseTranslationSimpleOneDialogActivity;
import com.newbee.translation_ui_lib.event.VoiceToTextEventSubscriptionSubject;
import com.newbee.translation_ui_lib.process.BaseVoiceToTextProcess;

public class aaaaaa extends BaseTranslationSimpleOneDialogActivity {
    @Override
    public BaseVoiceToTextProcess getVoiceToTextProcess() {
        return new Process();
    }

    @Override
    public Class getFromLangActivity() {
        return FromLangActivity.class;
    }

    @Override
    public Class getToLangActivity() {
        return FromLangActivity.class;
    }

    @Override
    public String getFromLangStr() {
        return "from 11";
    }

    @Override
    public String getToLangStr() {
        return "to 22";
    }

    @Override
    public void viewIsShow() {
        super.viewIsShow();
        VoiceToTextEventSubscriptionSubject.getInstence().getRecogingStr("123");
        VoiceToTextEventSubscriptionSubject.getInstence().getTranslationStr("fjklsdjflsalfjklsad");

        basehandler.postDelayed(runnable,1000);
    }

    private int index;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(index>=10){
                VoiceToTextEventSubscriptionSubject.getInstence().getStatu("kankandaojishi");
                setAfterTimeToFinsh(10*1000);
                index=0;
                basehandler.postDelayed(runnable,9*1000);
            }else {

                index++;
                VoiceToTextEventSubscriptionSubject.getInstence().getRecogFinshAndTranslationStr("1244","xiao ge da shuai bi  !"+index);
                basehandler.postDelayed(runnable,1000);
            }
        }
    };
}
