package com.legacy.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class AirplaneModeService {
    public static boolean run(Context context) {
        boolean isEnabled = isAirplaneModeOn(context);
        // Toggle airplane mode.
        setSettings(context, isEnabled?1:0);
        // Post an intent to reload.
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !isEnabled);
        context.sendBroadcast(intent);
        return true;
    }
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }
    public static void setSettings(Context context, int value) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.putInt(
                    context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, value);
        } else {
            Settings.Global.putInt(
                    context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, value);
        }
    }
}
