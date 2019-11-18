package com.creativity.yourself;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkUtil {

    static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    private final static int TYPE_NOT_CONNECTED = 0;
    private final static int TYPE_WIFI = 1;
    private final static int TYPE_MOBILE = 2;
    private static final int NETWORK_STAUS_WIFI = 1;
    private static final int NETWORK_STATUS_MOBILE = 2;

    private static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    static int getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        int status;
        switch (conn) {
            case NetworkUtil.TYPE_WIFI:
                status = NETWORK_STAUS_WIFI;
                break;
            case NetworkUtil.TYPE_MOBILE:
                status = NETWORK_STATUS_MOBILE;
                break;
            default:
                status = NETWORK_STATUS_NOT_CONNECTED;
                break;
        }
        return status;
    }

    static boolean getConnectivityStatusBoolean(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        boolean status;
        switch (conn) {
            case NetworkUtil.TYPE_WIFI:
                status = true;
                break;
            case NetworkUtil.TYPE_MOBILE:
                status = true;
                break;
            default:
                status = false;
                break;
        }
        return status;
    }
}