package com.creativity.yourself;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private AppBaseCompatActivity appBaseCompatActivity;

    public NetworkChangeReceiver() {
    }

    public NetworkChangeReceiver(AppBaseCompatActivity appBaseCompatActivity) {
        this.appBaseCompatActivity = appBaseCompatActivity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            appBaseCompatActivity.onNetworkStatusChange(NetworkUtil.getConnectivityStatusBoolean(appBaseCompatActivity));
        }
    }
}