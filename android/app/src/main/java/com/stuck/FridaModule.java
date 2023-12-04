package com.stuck;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.IntegrityManager;
import com.google.android.play.core.integrity.IntegrityManagerFactory;
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.android.play.core.integrity.IntegrityTokenResponse;

public class FridaModule extends ReactContextBaseJavaModule {
    FridaModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "frida";
    }
    @ReactMethod
    public void createCalendarEvent(Callback callback) {
        Log.d("CalendarModule", "Create event called with name: and location:");
        callback.invoke("data return from callback");
    }
//    @ReactMethod
//    public static String getLocalIpAddress() throws SocketException {
//        WifiManager wifiManager = (WifiManager) getReactApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipInt = wifiInfo.getIpAddress();
//        if (ipInt == 0) {
//            return null; // Not connected to Wi-Fi
//        }
//        return String.format("%d.%d.%d.%d",
//                (ipInt & 0xff), (ipInt >> 8 & 0xff), (ipInt >> 16 & 0xff), (ipInt >> 24 & 0xff));
//    }



    @ReactMethod
    public void requestIntegrityToken(String nonce,String cloud ,Callback callback) {
//        callback.invoke(Long.parseLong(cloud));
        IntegrityManager integrityManager = IntegrityManagerFactory.create(getReactApplicationContext());
        long num = Long.parseLong(cloud);
        integrityManager.requestIntegrityToken(
                        IntegrityTokenRequest.builder()
                                .setNonce(nonce)
                                .setCloudProjectNumber(num)
                                .build())
                .addOnCompleteListener(new OnCompleteListener() {

                    public void onComplete(Task task) {
                        if (task.isSuccessful()) {
                            IntegrityTokenResponse result = (IntegrityTokenResponse) task.getResult();
                            String integrityToken = result.token();
                            callback.invoke(integrityToken);
                        } else {
                            Exception exception = task.getException();
                            callback.invoke("MainActivity Failed to get integrity token"+ exception);
                        }
                    }
                });
    }

}