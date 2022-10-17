package com.legacy.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class NetworkService {
    public static boolean getMobileDataEnabled(Context context) throws Exception {
        ConnectivityManager mcm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class ownerClass = mcm.getClass();
        Method method = ownerClass.getMethod("getMobileDataEnabled");
        return (Boolean)method.invoke(mcm);
    }

    public static void setMobileDataEnabled(Context context) throws Exception {
        boolean isMobileDataEnable = false;
        try {
            isMobileDataEnable = getMobileDataEnabled(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        ConnectivityManager mcm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        Class ownerClass = mcm.getClass();
//        ownerClass.getMethod("setMobileDataEnabled",boolean.class).invoke(mcm, !isMobileDataEnable);

        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Method methodSet = Class.forName(tm.getClass().getName()).getDeclaredMethod( "setDataEnabled", Boolean.TYPE);
        methodSet.invoke(tm,!isMobileDataEnable);
    }

}
