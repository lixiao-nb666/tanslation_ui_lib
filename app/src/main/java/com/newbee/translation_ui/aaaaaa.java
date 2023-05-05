package com.newbee.translation_ui;

import com.newbee.translation_ui_lib.activity.base.BaseTranslationSimpleOneDialogActivity;
import com.newbee.translation_ui_lib.process.BaseVoiceToTextProcess;

public class aaaaaa extends BaseTranslationSimpleOneDialogActivity {
    @Override
    public BaseVoiceToTextProcess getVoiceToTextProcess() {
        return new Process();
    }

    @Override
    public Class getFromLangActivity() {
        return null;
    }

    @Override
    public Class getToLangActivity() {
        return null;
    }

    @Override
    public String getFromLangStr() {
        return null;
    }

    @Override
    public String getToLangStr() {
        return null;
    }
}
