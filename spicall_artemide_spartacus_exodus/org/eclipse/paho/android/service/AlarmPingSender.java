package org.eclipse.paho.android.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.security.d.a;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;

class AlarmPingSender
  implements MqttPingSender
{
  private static final String TAG = "AlarmPingSender";
  private BroadcastReceiver alarmReceiver;
  private ClientComms comms;
  private volatile boolean hasStarted = false;
  private PendingIntent pendingIntent;
  private MqttService service;
  private AlarmPingSender that;
  
  public AlarmPingSender(MqttService paramMqttService)
  {
    if (paramMqttService != null)
    {
      this.service = paramMqttService;
      this.that = this;
      return;
    }
    throw new IllegalArgumentException("Neither service nor client can be null.");
  }
  
  public void init(ClientComms paramClientComms)
  {
    this.comms = paramClientComms;
    this.alarmReceiver = new AlarmReceiver();
  }
  
  public void schedule(long paramLong)
  {
    long l = System.currentTimeMillis() + paramLong;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Schedule next alarm at ");
    ((StringBuilder)localObject).append(l);
    a.d("AlarmPingSender", ((StringBuilder)localObject).toString(), new Object[0]);
    localObject = (AlarmManager)this.service.getSystemService("alarm");
    StringBuilder localStringBuilder;
    if (Build.VERSION.SDK_INT >= 23)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Alarm scheule using setExactAndAllowWhileIdle, next: ");
      localStringBuilder.append(paramLong);
      a.d("AlarmPingSender", localStringBuilder.toString(), new Object[0]);
      ((AlarmManager)localObject).setExactAndAllowWhileIdle(0, l, this.pendingIntent);
    }
    else if (Build.VERSION.SDK_INT >= 19)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Alarm scheule using setExact, delay: ");
      localStringBuilder.append(paramLong);
      a.d("AlarmPingSender", localStringBuilder.toString(), new Object[0]);
      ((AlarmManager)localObject).setExact(0, l, this.pendingIntent);
    }
    else
    {
      ((AlarmManager)localObject).set(0, l, this.pendingIntent);
    }
  }
  
  public void start()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("MqttService.pingSender.");
    ((StringBuilder)localObject).append(this.comms.getClient().getClientId());
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Register alarmreceiver to MqttService");
    localStringBuilder.append((String)localObject);
    a.d("AlarmPingSender", localStringBuilder.toString(), new Object[0]);
    this.service.registerReceiver(this.alarmReceiver, new IntentFilter((String)localObject));
    this.pendingIntent = PendingIntent.getBroadcast(this.service, 0, new Intent((String)localObject), 134217728);
    schedule(this.comms.getKeepAlive());
    this.hasStarted = true;
  }
  
  public void stop()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unregister alarmreceiver to MqttService");
    localStringBuilder.append(this.comms.getClient().getClientId());
    a.d("AlarmPingSender", localStringBuilder.toString(), new Object[0]);
    if (this.hasStarted)
    {
      if (this.pendingIntent != null) {
        ((AlarmManager)this.service.getSystemService("alarm")).cancel(this.pendingIntent);
      }
      this.hasStarted = false;
    }
    try
    {
      this.service.unregisterReceiver(this.alarmReceiver);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  class AlarmReceiver
    extends BroadcastReceiver
  {
    private final String wakeLockTag;
    private PowerManager.WakeLock wakelock;
    
    AlarmReceiver()
    {
      this$1 = new StringBuilder();
      AlarmPingSender.this.append("MqttService.client.");
      AlarmPingSender.this.append(AlarmPingSender.access$000(AlarmPingSender.this).comms.getClient().getClientId());
      this.wakeLockTag = AlarmPingSender.this.toString();
    }
    
    @SuppressLint({"Wakelock"})
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      paramContext = new StringBuilder();
      paramContext.append("Sending Ping at:");
      paramContext.append(System.currentTimeMillis());
      a.d("AlarmPingSender", paramContext.toString(), new Object[0]);
      this.wakelock = ((PowerManager)AlarmPingSender.this.service.getSystemService("power")).newWakeLock(1, this.wakeLockTag);
      this.wakelock.acquire();
      if ((AlarmPingSender.this.comms.checkForActivity(new IMqttActionListener()
      {
        public void onFailure(IMqttToken paramAnonymousIMqttToken, Throwable paramAnonymousThrowable)
        {
          paramAnonymousIMqttToken = new StringBuilder();
          paramAnonymousIMqttToken.append("Failure. Release lock(");
          paramAnonymousIMqttToken.append(AlarmPingSender.AlarmReceiver.this.wakeLockTag);
          paramAnonymousIMqttToken.append("):");
          paramAnonymousIMqttToken.append(System.currentTimeMillis());
          a.d("AlarmPingSender", paramAnonymousIMqttToken.toString(), new Object[0]);
          AlarmPingSender.AlarmReceiver.this.wakelock.release();
        }
        
        public void onSuccess(IMqttToken paramAnonymousIMqttToken)
        {
          paramAnonymousIMqttToken = new StringBuilder();
          paramAnonymousIMqttToken.append("Success. Release lock(");
          paramAnonymousIMqttToken.append(AlarmPingSender.AlarmReceiver.this.wakeLockTag);
          paramAnonymousIMqttToken.append("):");
          paramAnonymousIMqttToken.append(System.currentTimeMillis());
          a.d("AlarmPingSender", paramAnonymousIMqttToken.toString(), new Object[0]);
          AlarmPingSender.AlarmReceiver.this.wakelock.release();
        }
      }) == null) && (this.wakelock.isHeld())) {
        this.wakelock.release();
      }
    }
  }
}


/* Location:              ~/org/eclipse/paho/android/service/AlarmPingSender.class
 *
 * Reversed by:           J
 */