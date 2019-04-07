package com.app.system.common;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.d;
import android.support.v7.app.d.a;
import android.telephony.TelephonyManager;
import com.android.system.AmbientService;
import com.android.system.CoreService;
import com.app.system.common.entity.DeviceInfo.MacAddress;
import com.google.gson.f;
import com.google.gson.g;
import com.security.d.a;
import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class h
{
  private static String a;
  
  public static long a()
  {
    return new Date().getTime() / 1000L;
  }
  
  public static String a(Context paramContext)
  {
    try
    {
      if (a == null)
      {
        paramContext = paramContext.getSharedPreferences("PREF_UNIQUE_ID", 0);
        a = paramContext.getString("PREF_UNIQUE_ID", null);
        if (a == null)
        {
          a = UUID.randomUUID().toString();
          paramContext = paramContext.edit();
          paramContext.putString("PREF_UNIQUE_ID", a);
          paramContext.commit();
        }
      }
      paramContext = a;
      return paramContext;
    }
    finally {}
  }
  
  public static String a(File paramFile)
  {
    if (paramFile == null) {
      return "(null)";
    }
    String str = paramFile.getName();
    int i = str.lastIndexOf('.');
    paramFile = str;
    if (i != -1) {
      paramFile = str.substring(0, i);
    }
    return paramFile;
  }
  
  public static String a(String paramString)
  {
    return paramString;
  }
  
  public static void a(Context paramContext, String paramString) {}
  
  public static void a(Context paramContext, String paramString1, String paramString2, DialogInterface.OnClickListener paramOnClickListener)
  {
    paramContext = new d.a(paramContext);
    paramContext.a(paramString1);
    paramContext.b(paramString2);
    paramContext.a("OK", paramOnClickListener);
    paramContext.a(false);
    paramContext.b().show();
  }
  
  public static f b()
  {
    return new g().a().b();
  }
  
  public static boolean b(Context paramContext)
  {
    return b(c(paramContext));
  }
  
  public static boolean b(String paramString)
  {
    if ((paramString != null) && (paramString.length() == 15))
    {
      for (int i = 0; i < 15; i++) {
        if (!Character.isDigit(paramString.charAt(i))) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static String c(Context paramContext)
  {
    Object localObject = paramContext.getSharedPreferences("pref", 0);
    String str = ((SharedPreferences)localObject).getString("target-device-id", "");
    if (b(str)) {
      return str;
    }
    try
    {
      if (com.security.b.a(paramContext, "android.permission.READ_PHONE_STATE", "getIMEI@common/Utility.java"))
      {
        str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
        if (b(str))
        {
          localObject = ((SharedPreferences)localObject).edit();
          ((SharedPreferences.Editor)localObject).putString("target-device-id", str);
          ((SharedPreferences.Editor)localObject).commit();
          return str;
        }
      }
    }
    catch (Exception localException)
    {
      a.a("Utility", localException.getMessage(), new Object[0]);
    }
    return a(paramContext);
  }
  
  public static String d(Context paramContext)
  {
    String str1 = "";
    String str2 = str1;
    String str3;
    try
    {
      if (com.security.b.a(paramContext, "android.permission.READ_PHONE_STATE", "getMyPhoneNumber@Utility.java")) {
        str2 = ((TelephonyManager)paramContext.getSystemService("phone")).getLine1Number();
      }
    }
    catch (Exception localException)
    {
      paramContext = new StringBuilder();
      paramContext.append("GetPhoneNumber Error: ");
      paramContext.append(localException.getMessage());
      a.a("Utility", paramContext.toString(), new Object[0]);
      str3 = str1;
    }
    return str3;
  }
  
  public static boolean e(Context paramContext)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
    while (localIterator.hasNext())
    {
      paramContext = (ActivityManager.RunningServiceInfo)localIterator.next();
      if (CoreService.class.getName().equals(paramContext.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean f(Context paramContext)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
    while (localIterator.hasNext())
    {
      paramContext = (ActivityManager.RunningServiceInfo)localIterator.next();
      if (AmbientService.class.getName().equals(paramContext.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
  
  public static class a
  {
    public static ArrayList<DeviceInfo.MacAddress> a()
    {
      localArrayList = new ArrayList();
      try
      {
        Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
        while (localEnumeration.hasMoreElements())
        {
          NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
          Object localObject = localNetworkInterface.getHardwareAddress();
          if (localObject != null)
          {
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            int i = localObject.length;
            for (int j = 0; j < i; j++) {
              localStringBuilder.append(String.format("%02X:", new Object[] { Byte.valueOf(localObject[j]) }));
            }
            if (localStringBuilder.length() > 0) {
              localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
            }
            localObject = new com/app/system/common/entity/DeviceInfo$MacAddress;
            ((DeviceInfo.MacAddress)localObject).<init>(localNetworkInterface.getDisplayName(), localStringBuilder.toString());
            localArrayList.add(localObject);
          }
        }
        return localArrayList;
      }
      catch (SocketException localSocketException)
      {
        a.a("Utility.Net", "getMacAddresses", new Object[] { localSocketException });
      }
    }
    
    public static String b()
    {
      try
      {
        Object localObject = com.app.system.common.d.a.a.b.a("https://ipinfo.io/ip");
        if (localObject != null)
        {
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("getIPAddress: httpGet result=");
          localStringBuilder.append(localObject);
          a.d("Utility.Net", localStringBuilder.toString(), new Object[0]);
          localObject = ((Response)localObject).body().string().trim();
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("Indirizzo IP: ");
          localStringBuilder.append((String)localObject);
          a.d("Utility.Net", localStringBuilder.toString(), new Object[0]);
          return (String)localObject;
        }
      }
      catch (Exception localException)
      {
        a.a("Utility.Net", "getIPAddress", new Object[] { localException });
      }
      return null;
    }
  }
}


/* Location:              ~/com/app/system/common/h.class
 *
 * Reversed by:           J
 */