package com.newbee.translation_ui_lib.key.event;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ActivityKeyDownListUtil {

    //往右
    public static List<Integer> toTopList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);
        return needKeyCodes;
    }

    public static List<Integer> toDownList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_DOWN);
        return needKeyCodes;
    }

    //往左
    public static List<Integer> toLeftList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_LEFT);
        return needKeyCodes;
    }



    //往右
    public static List<Integer> toRightList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_RIGHT);
        return needKeyCodes;
    }





}
