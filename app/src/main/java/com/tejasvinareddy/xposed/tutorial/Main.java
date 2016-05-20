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
        XposedBridge.log("[TUTORIAL] Loaded app " + lpparam.packageName);

        // We only want the system UI package
        if (!lpparam.packageName.equals("com.android.systemui")) {
            return;
        }
        XposedBridge.log("[TUTORIAL] We are in SystemUI!");

        // Hook the updateClock method in the Clock class
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
    }
}
