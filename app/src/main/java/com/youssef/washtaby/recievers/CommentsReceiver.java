package com.youssef.washtaby.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.youssef.washtaby.Ui.Comments.CommentActivity;

public class CommentsReceiver extends BroadcastReceiver {
    CommentActivity activity;

    @Override
    public void onReceive(Context context, Intent intent) {
        activity = (CommentActivity) context;
        activity.getData();
    }
}