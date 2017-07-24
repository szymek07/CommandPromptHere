package com.intellij.plugin.cph;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class Notificator {

    public static void notifyInfo(String information) {
        Notifications.Bus.notify(new Notification("cph", CommandPromptHereAction.PLUGIN_NAME,
                information, NotificationType.INFORMATION));
    }

    public static void notifyError(String information) {
        Notifications.Bus.notify(new Notification("cph", CommandPromptHereAction.PLUGIN_NAME,
                information, NotificationType.ERROR));
    }

}
