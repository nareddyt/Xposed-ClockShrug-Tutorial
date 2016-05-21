package com.tejasvinareddy.xposed.tutorial;

import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
            throws Throwable {
        // Called every time a package is loaded
        if (!lpparam.packageName.equals("com.android.systemui")) {
            // We only want the system UI package
            return;
        }
        XposedBridge.log("[TUTORIAL] We are in SystemUI!");

        // Set up callback: Hook the updateClock method in the Clock class
        // CHECKME use of high priority
        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy."
                + "Clock", lpparam.classLoader, "updateClock", new
                XC_MethodHook(XCallback.PRIORITY_HIGHEST) {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        super.afterHookedMethod(param);

                        // Update the clock's text with a shrug
                        TextView tv = (TextView) param.thisObject;
                        String newText = tv.getText() + " ¯\\_(ツ)_/¯";
                        tv.setText(newText);
                        XposedBridge.log("[TUTORIAL] Set clock to " + newText);
                    }
                });

        XposedBridge.log("[TUTORIAL] Callback for Clock's updateClock method " +
                "is set up!");
    }
}
