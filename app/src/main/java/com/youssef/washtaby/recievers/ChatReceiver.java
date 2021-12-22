package com.youssef.washtaby.recievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.youssef.washtaby.Ui.Chat.ChatActivity;

public class ChatReceiver extends BroadcastReceiver {

    ChatActivity activity;
    @Override
    public void onReceive(Context context, Intent intent) {
        activity= (ChatActivity) context;
        activity.Getdata();
    }
}