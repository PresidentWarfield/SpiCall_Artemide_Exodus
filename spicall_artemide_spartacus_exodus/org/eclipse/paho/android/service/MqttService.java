package org.eclipse.paho.android.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.content.LocalBroadcastManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@SuppressLint({"Registered"})
public class MqttService
  extends Service
  implements MqttTraceHandler
{
  static final String TAG = "MqttService";
  private volatile boolean backgroundDataEnabled = true;
  private BackgroundDataPreferenceReceiver backgroundDataPreferenceMonitor;
  private Map<String, MqttConnection> connections = new ConcurrentHashMap();
  MessageStore messageStore;
  private MqttServiceBinder mqttServiceBinder;
  private NetworkConnectionIntentReceiver networkConnectionMonitor;
  private String traceCallbackId;
  private boolean traceEnabled = false;
  
  private MqttConnection getConnection(String paramString)
  {
    paramString = (MqttConnection)this.connections.get(paramString);
    if (paramString != null) {
      return paramString;
    }
    throw new IllegalArgumentException("Invalid ClientHandle");
  }
  
  private void notifyClientsOffline()
  {
    Iterator localIterator = this.connections.values().iterator();
    while (localIterator.hasNext()) {
      ((MqttConnection)localIterator.next()).offline();
    }
  }
  
  private void registerBroadcastReceivers()
  {
    if (this.networkConnectionMonitor == null)
    {
      this.networkConnectionMonitor = new NetworkConnectionIntentReceiver(null);
      registerReceiver(this.networkConnectionMonitor, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
    if (Build.VERSION.SDK_INT < 14)
    {
      this.backgroundDataEnabled = ((ConnectivityManager)getSystemService("connectivity")).getBackgroundDataSetting();
      if (this.backgroundDataPreferenceMonitor == null)
      {
        this.backgroundDataPreferenceMonitor = new BackgroundDataPreferenceReceiver(null);
        registerReceiver(this.backgroundDataPreferenceMonitor, new IntentFilter("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"));
      }
    }
  }
  
  private void traceCallback(String paramString1, String paramString2, String paramString3)
  {
    if ((this.traceCallbackId != null) && (this.traceEnabled))
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("MqttService.callbackAction", "trace");
      localBundle.putString("MqttService.traceSeverity", paramString1);
      localBundle.putString("MqttService.traceTag", paramString2);
      localBundle.putString("MqttService.errorMessage", paramString3);
      callbackToActivity(this.traceCallbackId, Status.ERROR, localBundle);
    }
  }
  
  private void unregisterBroadcastReceivers()
  {
    Object localObject = this.networkConnectionMonitor;
    if (localObject != null)
    {
      unregisterReceiver((BroadcastReceiver)localObject);
      this.networkConnectionMonitor = null;
    }
    if (Build.VERSION.SDK_INT < 14)
    {
      localObject = this.backgroundDataPreferenceMonitor;
      if (localObject != null) {
        unregisterReceiver((BroadcastReceiver)localObject);
      }
    }
  }
  
  public Status acknowledgeMessageArrival(String paramString1, String paramString2)
  {
    if (this.messageStore.discardArrived(paramString1, paramString2)) {
      return Status.OK;
    }
    return Status.ERROR;
  }
  
  void callbackToActivity(String paramString, Status paramStatus, Bundle paramBundle)
  {
    Intent localIntent = new Intent("MqttService.callbackToActivity.v0");
    if (paramString != null) {
      localIntent.putExtra("MqttService.clientHandle", paramString);
    }
    localIntent.putExtra("MqttService.callbackStatus", paramStatus);
    if (paramBundle != null) {
      localIntent.putExtras(paramBundle);
    }
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
  }
  
  public void close(String paramString)
  {
    getConnection(paramString).close();
  }
  
  public void connect(String paramString1, MqttConnectOptions paramMqttConnectOptions, String paramString2, String paramString3)
  {
    getConnection(paramString1).connect(paramMqttConnectOptions, null, paramString3);
  }
  
  public void deleteBufferedMessage(String paramString, int paramInt)
  {
    getConnection(paramString).deleteBufferedMessage(paramInt);
  }
  
  public void disconnect(String paramString1, long paramLong, String paramString2, String paramString3)
  {
    getConnection(paramString1).disconnect(paramLong, paramString2, paramString3);
    this.connections.remove(paramString1);
    stopSelf();
  }
  
  public void disconnect(String paramString1, String paramString2, String paramString3)
  {
    getConnection(paramString1).disconnect(paramString2, paramString3);
    this.connections.remove(paramString1);
    stopSelf();
  }
  
  public MqttMessage getBufferedMessage(String paramString, int paramInt)
  {
    return getConnection(paramString).getBufferedMessage(paramInt);
  }
  
  public int getBufferedMessageCount(String paramString)
  {
    return getConnection(paramString).getBufferedMessageCount();
  }
  
  public String getClient(String paramString1, String paramString2, String paramString3, MqttClientPersistence paramMqttClientPersistence)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(":");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(":");
    localStringBuilder.append(paramString3);
    paramString3 = localStringBuilder.toString();
    if (!this.connections.containsKey(paramString3))
    {
      paramString1 = new MqttConnection(this, paramString1, paramString2, paramMqttClientPersistence, paramString3);
      this.connections.put(paramString3, paramString1);
    }
    return paramString3;
  }
  
  public IMqttDeliveryToken[] getPendingDeliveryTokens(String paramString)
  {
    return getConnection(paramString).getPendingDeliveryTokens();
  }
  
  public boolean isConnected(String paramString)
  {
    return getConnection(paramString).isConnected();
  }
  
  public boolean isOnline()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected()) && (this.backgroundDataEnabled);
  }
  
  public boolean isTraceEnabled()
  {
    return this.traceEnabled;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    paramIntent = paramIntent.getStringExtra("MqttService.activityToken");
    this.mqttServiceBinder.setActivityToken(paramIntent);
    return this.mqttServiceBinder;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mqttServiceBinder = new MqttServiceBinder(this);
    this.messageStore = new DatabaseMessageStore(this, this);
  }
  
  public void onDestroy()
  {
    Object localObject = this.connections.values().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((MqttConnection)((Iterator)localObject).next()).disconnect(null, null);
    }
    if (this.mqttServiceBinder != null) {
      this.mqttServiceBinder = null;
    }
    unregisterBroadcastReceivers();
    localObject = this.messageStore;
    if (localObject != null) {
      ((MessageStore)localObject).close();
    }
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    registerBroadcastReceivers();
    return 1;
  }
  
  public IMqttDeliveryToken publish(String paramString1, String paramString2, MqttMessage paramMqttMessage, String paramString3, String paramString4)
  {
    return getConnection(paramString1).publish(paramString2, paramMqttMessage, paramString3, paramString4);
  }
  
  public IMqttDeliveryToken publish(String paramString1, String paramString2, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, String paramString3, String paramString4)
  {
    return getConnection(paramString1).publish(paramString2, paramArrayOfByte, paramInt, paramBoolean, paramString3, paramString4);
  }
  
  void reconnect()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Reconnect to server, client size=");
    ((StringBuilder)localObject).append(this.connections.size());
    traceDebug("MqttService", ((StringBuilder)localObject).toString());
    Iterator localIterator = this.connections.values().iterator();
    while (localIterator.hasNext())
    {
      localObject = (MqttConnection)localIterator.next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(((MqttConnection)localObject).getClientId());
      localStringBuilder.append('/');
      localStringBuilder.append(((MqttConnection)localObject).getServerURI());
      traceDebug("Reconnect Client:", localStringBuilder.toString());
      if (isOnline()) {
        ((MqttConnection)localObject).reconnect();
      }
    }
  }
  
  public void setBufferOpts(String paramString, DisconnectedBufferOptions paramDisconnectedBufferOptions)
  {
    getConnection(paramString).setBufferOpts(paramDisconnectedBufferOptions);
  }
  
  public void setTraceCallbackId(String paramString)
  {
    this.traceCallbackId = paramString;
  }
  
  public void setTraceEnabled(boolean paramBoolean)
  {
    this.traceEnabled = paramBoolean;
  }
  
  public void subscribe(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    getConnection(paramString1).subscribe(paramString2, paramInt, paramString3, paramString4);
  }
  
  public void subscribe(String paramString1, String[] paramArrayOfString, int[] paramArrayOfInt, String paramString2, String paramString3)
  {
    getConnection(paramString1).subscribe(paramArrayOfString, paramArrayOfInt, paramString2, paramString3);
  }
  
  public void subscribe(String paramString1, String[] paramArrayOfString, int[] paramArrayOfInt, String paramString2, String paramString3, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    getConnection(paramString1).subscribe(paramArrayOfString, paramArrayOfInt, paramString2, paramString3, paramArrayOfIMqttMessageListener);
  }
  
  public void traceDebug(String paramString1, String paramString2)
  {
    traceCallback("debug", paramString1, paramString2);
  }
  
  public void traceError(String paramString1, String paramString2)
  {
    traceCallback("error", paramString1, paramString2);
  }
  
  public void traceException(String paramString1, String paramString2, Exception paramException)
  {
    if (this.traceCallbackId != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("MqttService.callbackAction", "trace");
      localBundle.putString("MqttService.traceSeverity", "exception");
      localBundle.putString("MqttService.errorMessage", paramString2);
      localBundle.putSerializable("MqttService.exception", paramException);
      localBundle.putString("MqttService.traceTag", paramString1);
      callbackToActivity(this.traceCallbackId, Status.ERROR, localBundle);
    }
  }
  
  public void unsubscribe(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    getConnection(paramString1).unsubscribe(paramString2, paramString3, paramString4);
  }
  
  public void unsubscribe(String paramString1, String[] paramArrayOfString, String paramString2, String paramString3)
  {
    getConnection(paramString1).unsubscribe(paramArrayOfString, paramString2, paramString3);
  }
  
  private class BackgroundDataPreferenceReceiver
    extends BroadcastReceiver
  {
    private BackgroundDataPreferenceReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      paramContext = (ConnectivityManager)MqttService.this.getSystemService("connectivity");
      MqttService.this.traceDebug("MqttService", "Reconnect since BroadcastReceiver.");
      if (paramContext.getBackgroundDataSetting())
      {
        if (!MqttService.this.backgroundDataEnabled)
        {
          MqttService.access$302(MqttService.this, true);
          MqttService.this.reconnect();
        }
      }
      else
      {
        MqttService.access$302(MqttService.this, false);
        MqttService.this.notifyClientsOffline();
      }
    }
  }
  
  private class NetworkConnectionIntentReceiver
    extends BroadcastReceiver
  {
    private NetworkConnectionIntentReceiver() {}
    
    @SuppressLint({"Wakelock"})
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      MqttService.this.traceDebug("MqttService", "Internal network status receive.");
      paramContext = ((PowerManager)MqttService.this.getSystemService("power")).newWakeLock(1, "MQTT");
      paramContext.acquire();
      MqttService.this.traceDebug("MqttService", "Reconnect for Network recovery.");
      if (MqttService.this.isOnline())
      {
        MqttService.this.traceDebug("MqttService", "Online,reconnect.");
        MqttService.this.reconnect();
      }
      else
      {
        MqttService.this.notifyClientsOffline();
      }
      paramContext.release();
    }
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttService.class
 *
 * Reversed by:           J
 */