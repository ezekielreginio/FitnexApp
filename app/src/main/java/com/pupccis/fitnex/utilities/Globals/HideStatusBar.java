package com.pupccis.fitnex.utilities.Globals;

import android.view.Window;
import android.view.WindowManager;

public class HideStatusBar {

    public HideStatusBar(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
