package com.example.broadcastreceiver2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

        @Override
        protected void onDestroy(){
            super.onDestroy();
            unregisterReceiver(networkChangeReceiver);

        }

        class NetworkChangeReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectionManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

                NetworkInfo.State state = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                NetworkInfo.State state1 = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

                if (networkInfo != null && networkInfo.isAvailable()) {
//                  Toast.makeText(context, "network is available",
//                            Toast.LENGTH_SHORT).show();
                    if(state == NetworkInfo.State.CONNECTED){
                    Log.d("MainActivity","WIFI网络已连接");
                    }
                    else if(state1 == NetworkInfo.State.CONNECTED) {
                        Log.d("MainActivity","移动网络已连接");
                    }

                }

                if(networkInfo== null){
//                    Toast.makeText(context, "network is unavailable",
//                            Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity","网络连接已断开");
                }

            }

        }

}
