package com.newbee.translation_ui_lib.process;

import android.content.Context;

public interface BaseVoiceToTextProcess {

    public void startReadAudio(Context context);

    public void pause();

    public void close();
}
