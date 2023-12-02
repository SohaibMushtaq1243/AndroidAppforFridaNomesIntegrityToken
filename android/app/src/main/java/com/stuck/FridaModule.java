package com.stuck;

import android.util.Log;

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
    @ReactMethod
    public void requestIntegrityToken(String nonce,Callback callback) {
        IntegrityManager integrityManager = IntegrityManagerFactory.create(getReactApplicationContext());

        integrityManager.requestIntegrityToken(
                        IntegrityTokenRequest.builder()
                                .setNonce(nonce)
                                .setCloudProjectNumber(2131889799)
                                .build())
                .addOnCompleteListener(new OnCompleteListener() {

                    public void onComplete(Task task) {
                        if (task.isSuccessful()) {
                            IntegrityTokenResponse result = (IntegrityTokenResponse) task.getResult();
                            String integrityToken = result.token();
                            callback.invoke("MainActivity Integrity Token: " + integrityToken);
                        } else {
                            Exception exception = task.getException();
                            callback.invoke("MainActivity Failed to get integrity token"+ exception);
                        }
                    }
                });
    }

}