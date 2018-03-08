package com.sptas.sptasv2.BroadcastReceiver;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sptas.sptasv2.Student.Common.Common;

/**
 * Created by Na'im Mansor on 26-Feb-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        hadleNotification(remoteMessage.getNotification().getBody());
    }

    private void hadleNotification(String body) {
        Intent pushNotification = new Intent(Common.STR_PUSH);
        pushNotification.putExtra("message", body);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
}
