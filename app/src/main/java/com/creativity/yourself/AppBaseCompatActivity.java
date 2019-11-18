package com.creativity.yourself;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AppBaseCompatActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private NetworkChangeReceiver networkChangeReceiver;

    public static boolean isOnline() {
        return NetworkUtil.getConnectivityStatusString(context) != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        context = this;
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    abstract public void onNetworkStatusChange(boolean isOnline);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
