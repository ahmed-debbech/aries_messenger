package com.ahmeddebbech.aries_messenger.util;

import android.content.Context;

import com.ahmeddebbech.aries_messenger.R;

public class FlagResolver {
    public static String toAvailabilityStatusText(Context ctx, int av){
        String aa = "";
        switch(av){
            case 1:
                aa = ctx.getResources().getString(R.string.online_status);
                break;
            case 2:
                aa = ctx.getResources().getString(R.string.offline_status);
                break;
        }
        return aa;
    }
}
