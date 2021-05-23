package com.github.chagall.notificationlistenerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.app.NotificationManager;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * MIT License
 *
 *  Copyright (c) 2016 Fábio Alves Martins Pereira (Chagall)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class NotificationListenerExampleService extends NotificationListenerService {

    /*
            These are the package names of the apps. for which we want to
            listen the notifications
         */
    private ClickBroadcastReceiver clickBroadcastReceiver;

    public NotificationListenerExampleService() {
//        clickBroadcastReceiver = new ClickBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.github.chagall.notificationlistenerexample");
//        registerReceiver(clickBroadcastReceiver,intentFilter);
        System.out.println("Im in constructor. Am I working?");
    }

    public void callIntent() {
        Intent intent = new  Intent("com.github.chagall.notificationlistenerexample");
        intent.putExtra("Notification", getNotifications());
        sendBroadcast(intent);
    }

    public static final String NAME = "NAME";
    public static final String AGE = "AGE";
    private String name;
    private int age;

    private static final class ApplicationPackageNames {
        public static final String FACEBOOK_PACK_NAME = "com.facebook.katana";
        public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
        public static final String INSTAGRAM_PACK_NAME = "com.instagram.android";
    }

    /*
        These are the return codes we use in the method which intercepts
        the notifications, to decide whether we should do something or not
     */
    public static final class InterceptedNotificationCode {
        public static final int FACEBOOK_CODE = 1;
        public static final int WHATSAPP_CODE = 2;
        public static final int INSTAGRAM_CODE = 3;
        public static final int OTHER_NOTIFICATIONS_CODE = 4; // We ignore all notification with code == 4
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        int notificationCode = matchNotificationCode(sbn);
//        System.out.println(sbn.getId());
//        System.out.println(sbn.getNotification().getSmallIcon());
//        System.out.println(sbn.getNotification().tickerText);
//        System.out.println(sbn.getPackageName());
//        System.out.println("****************************************************");
//        //System.out.println(Arrays.toString(this.getActiveNotifications()));
//        StatusBarNotification[] noty = this.getActiveNotifications();
//        for (StatusBarNotification statusBarNotification : noty) {
//            System.out.println("-----x---------x----------x-----");
//            //arrayPackageName.add(statusBarNotification.getPackageName().toString());
//            //arrayNotification.add((String) statusBarNotification.getNotification().tickerText);
//            //System.out.println(statusBarNotification.getNotification().tickerText);
//        }
        System.out.println("onNotificationPosted!!!");
        Intent intent = new  Intent("com.github.chagall.notificationlistenerexample");
        intent.putExtra("Notification", getNotifications());
        sendBroadcast(intent);
    }
//    public CharSequence getNotificationString() {
//        StatusBarNotification[] activeNotifications = this.getActiveNotifications();
//        for (StatusBarNotification statusBarNotification : activeNotifications) {
//            System.out.println("-----x---------x----------x-----");
//            //arrayPackageName.add(statusBarNotification.getPackageName().toString());
//            //arrayNotification.add((String) statusBarNotification.getNotification().tickerText);
//            statusBarNotification.getNotification().tickerText;
//        }
//    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        int notificationCode = matchNotificationCode(sbn);

        if(notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {

            StatusBarNotification[] activeNotifications = this.getActiveNotifications();

            if(activeNotifications != null && activeNotifications.length > 0) {
                for (int i = 0; i < activeNotifications.length; i++) {
                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
                        Intent intent = new  Intent("com.github.chagall.notificationlistenerexample");
                        intent.putExtra("Notification Code", getNotifications());
                        sendBroadcast(intent);
                        break;
                    }
                }
            }
        }
    }
    public ArrayList<ArrayList> getNotifications() {
            //int notificationCode = matchNotificationCode(sbn);
        ArrayList<String> arrayNotification = new ArrayList<String>();
        ArrayList<String> arrayPackageName = new ArrayList<String>();
        ArrayList<ArrayList> arrayNotificationAll = new ArrayList<>();
//        System.out.println(sbn.getId());
//        System.out.println(sbn.getNotification().getSmallIcon());
//        System.out.println(sbn.getNotification().tickerText);
//        System.out.println(sbn.getPackageName());
            //System.out.println("****************************************************");
            //System.out.println(Arrays.toString(this.getActiveNotifications()));
        StatusBarNotification[] activeNotifications = this.getActiveNotifications();
        for (StatusBarNotification sbn : activeNotifications) {
            //System.out.println("-----x---------x----------x-----");
            arrayPackageName.add(sbn.getPackageName().toString());
            arrayNotification.add((String) sbn.getNotification().tickerText);
            //System.out.println("Working!");
        }
        arrayNotificationAll.add(arrayPackageName);
        arrayNotificationAll.add(arrayNotification);
            //System.out.println(arrayNotification);
        return arrayNotificationAll;
    }
    // INTENT CLASS
    public class ClickBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int receivedClick = intent.getIntExtra("Clicked",0);
            System.out.println(receivedClick);
            System.out.println("In receive intent!");
            //changeInterceptedNotificationImage(receivedNotificationCode);
        }
    }

    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
//        System.out.println("Working");
//        //System.out.println(sbn.getNotification());
//        System.out.println(sbn.describeContents());
        if(packageName.equals(ApplicationPackageNames.FACEBOOK_PACK_NAME)
                || packageName.equals(ApplicationPackageNames.FACEBOOK_MESSENGER_PACK_NAME)){
            return(InterceptedNotificationCode.FACEBOOK_CODE);
        }
        else if(packageName.equals(ApplicationPackageNames.INSTAGRAM_PACK_NAME)){
            return(InterceptedNotificationCode.INSTAGRAM_CODE);
        }
        else if(packageName.equals(ApplicationPackageNames.WHATSAPP_PACK_NAME)){
            return(InterceptedNotificationCode.WHATSAPP_CODE);
        }
        else{
            return(InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }
}
