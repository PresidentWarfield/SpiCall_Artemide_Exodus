package org.eclipse.paho.android.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class MqttAndroidClient
  extends BroadcastReceiver
  implements IMqttAsyncClient
{
  private static final int BIND_SERVICE_FLAG = 0;
  private static final String SERVICE_NAME = "org.eclipse.paho.android.service.MqttService";
  private static final ExecutorService pool = ;
  private volatile boolean bindedService = false;
  private MqttCallback callback;
  private String clientHandle;
  private final String clientId;
  private MqttConnectOptions connectOptions;
  private IMqttToken connectToken;
  private final Ack messageAck;
  private MqttService mqttService;
  private Context myContext;
  private MqttClientPersistence persistence = null;
  private volatile boolean receiverRegistered = false;
  private final String serverURI;
  private final MyServiceConnection serviceConnection = new MyServiceConnection(null);
  private final SparseArray<IMqttToken> tokenMap = new SparseArray();
  private int tokenNumber = 0;
  private MqttTraceHandler traceCallback;
  private boolean traceEnabled = false;
  
  public MqttAndroidClient(Context paramContext, String paramString1, String paramString2)
  {
    this(paramContext, paramString1, paramString2, null, Ack.AUTO_ACK);
  }
  
  public MqttAndroidClient(Context paramContext, String paramString1, String paramString2, Ack paramAck)
  {
    this(paramContext, paramString1, paramString2, null, paramAck);
  }
  
  public MqttAndroidClient(Context paramContext, String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
  {
    this(paramContext, paramString1, paramString2, paramMqttClientPersistence, Ack.AUTO_ACK);
  }
  
  public MqttAndroidClient(Context paramContext, String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence, Ack paramAck)
  {
    this.myContext = paramContext;
    this.serverURI = paramString1;
    this.clientId = paramString2;
    this.persistence = paramMqttClientPersistence;
    this.messageAck = paramAck;
  }
  
  private void connectAction(Bundle paramBundle)
  {
    IMqttToken localIMqttToken = this.connectToken;
    removeMqttToken(paramBundle);
    simpleAction(localIMqttToken, paramBundle);
  }
  
  private void connectExtendedAction(Bundle paramBundle)
  {
    if ((this.callback instanceof MqttCallbackExtended))
    {
      boolean bool = paramBundle.getBoolean("MqttService.reconnect", false);
      paramBundle = paramBundle.getString("MqttService.serverURI");
      ((MqttCallbackExtended)this.callback).connectComplete(bool, paramBundle);
    }
  }
  
  private void connectionLostAction(Bundle paramBundle)
  {
    if (this.callback != null)
    {
      paramBundle = (Exception)paramBundle.getSerializable("MqttService.exception");
      this.callback.connectionLost(paramBundle);
    }
  }
  
  private void disconnected(Bundle paramBundle)
  {
    this.clientHandle = null;
    paramBundle = removeMqttToken(paramBundle);
    if (paramBundle != null) {
      ((MqttTokenAndroid)paramBundle).notifyComplete();
    }
    paramBundle = this.callback;
    if (paramBundle != null) {
      paramBundle.connectionLost(null);
    }
  }
  
  private void doConnect()
  {
    if (this.clientHandle == null) {
      this.clientHandle = this.mqttService.getClient(this.serverURI, this.clientId, this.myContext.getApplicationInfo().packageName, this.persistence);
    }
    this.mqttService.setTraceEnabled(this.traceEnabled);
    this.mqttService.setTraceCallbackId(this.clientHandle);
    String str = storeToken(this.connectToken);
    try
    {
      this.mqttService.connect(this.clientHandle, this.connectOptions, null, str);
    }
    catch (MqttException localMqttException)
    {
      IMqttActionListener localIMqttActionListener = this.connectToken.getActionCallback();
      if (localIMqttActionListener != null) {
        localIMqttActionListener.onFailure(this.connectToken, localMqttException);
      }
    }
  }
  
  private IMqttToken getMqttToken(Bundle paramBundle)
  {
    try
    {
      paramBundle = paramBundle.getString("MqttService.activityToken");
      paramBundle = (IMqttToken)this.tokenMap.get(Integer.parseInt(paramBundle));
      return paramBundle;
    }
    finally
    {
      paramBundle = finally;
      throw paramBundle;
    }
  }
  
  private void messageArrivedAction(Bundle paramBundle)
  {
    String str1;
    String str2;
    if (this.callback != null)
    {
      str1 = paramBundle.getString("MqttService.messageId");
      str2 = paramBundle.getString("MqttService.destinationName");
      paramBundle = (ParcelableMqttMessage)paramBundle.getParcelable("MqttService.PARCEL");
    }
    try
    {
      if (this.messageAck == Ack.AUTO_ACK)
      {
        this.callback.messageArrived(str2, paramBundle);
        this.mqttService.acknowledgeMessageArrival(this.clientHandle, str1);
      }
      else
      {
        paramBundle.messageId = str1;
        this.callback.messageArrived(str2, paramBundle);
      }
      return;
    }
    catch (Exception paramBundle)
    {
      for (;;) {}
    }
  }
  
  private void messageDeliveredAction(Bundle paramBundle)
  {
    IMqttToken localIMqttToken = removeMqttToken(paramBundle);
    if ((localIMqttToken != null) && (this.callback != null) && ((Status)paramBundle.getSerializable("MqttService.callbackStatus") == Status.OK) && ((localIMqttToken instanceof IMqttDeliveryToken))) {
      this.callback.deliveryComplete((IMqttDeliveryToken)localIMqttToken);
    }
  }
  
  private void registerReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("MqttService.callbackToActivity.v0");
    LocalBroadcastManager.getInstance(this.myContext).registerReceiver(paramBroadcastReceiver, localIntentFilter);
    this.receiverRegistered = true;
  }
  
  private IMqttToken removeMqttToken(Bundle paramBundle)
  {
    try
    {
      paramBundle = paramBundle.getString("MqttService.activityToken");
      if (paramBundle != null)
      {
        int i = Integer.parseInt(paramBundle);
        paramBundle = (IMqttToken)this.tokenMap.get(i);
        this.tokenMap.delete(i);
        return paramBundle;
      }
      return null;
    }
    finally {}
  }
  
  private void sendAction(Bundle paramBundle)
  {
    simpleAction(getMqttToken(paramBundle), paramBundle);
  }
  
  private void simpleAction(IMqttToken paramIMqttToken, Bundle paramBundle)
  {
    if (paramIMqttToken != null)
    {
      if ((Status)paramBundle.getSerializable("MqttService.callbackStatus") == Status.OK)
      {
        ((MqttTokenAndroid)paramIMqttToken).notifyComplete();
      }
      else
      {
        paramBundle = (Exception)paramBundle.getSerializable("MqttService.exception");
        ((MqttTokenAndroid)paramIMqttToken).notifyFailure(paramBundle);
      }
    }
    else {
      this.mqttService.traceError("MqttService", "simpleAction : token is null");
    }
  }
  
  private String storeToken(IMqttToken paramIMqttToken)
  {
    try
    {
      this.tokenMap.put(this.tokenNumber, paramIMqttToken);
      int i = this.tokenNumber;
      this.tokenNumber = (i + 1);
      paramIMqttToken = Integer.toString(i);
      return paramIMqttToken;
    }
    finally
    {
      paramIMqttToken = finally;
      throw paramIMqttToken;
    }
  }
  
  private void subscribeAction(Bundle paramBundle)
  {
    simpleAction(removeMqttToken(paramBundle), paramBundle);
  }
  
  private void traceAction(Bundle paramBundle)
  {
    if (this.traceCallback != null)
    {
      String str1 = paramBundle.getString("MqttService.traceSeverity");
      String str2 = paramBundle.getString("MqttService.errorMessage");
      String str3 = paramBundle.getString("MqttService.traceTag");
      if ("debug".equals(str1))
      {
        this.traceCallback.traceDebug(str3, str2);
      }
      else if ("error".equals(str1))
      {
        this.traceCallback.traceError(str3, str2);
      }
      else
      {
        paramBundle = (Exception)paramBundle.getSerializable("MqttService.exception");
        this.traceCallback.traceException(str3, str2, paramBundle);
      }
    }
  }
  
  private void unSubscribeAction(Bundle paramBundle)
  {
    simpleAction(removeMqttToken(paramBundle), paramBundle);
  }
  
  public boolean acknowledgeMessage(String paramString)
  {
    Ack localAck1 = this.messageAck;
    Ack localAck2 = Ack.MANUAL_ACK;
    boolean bool = false;
    if (localAck1 == localAck2)
    {
      if (this.mqttService.acknowledgeMessageArrival(this.clientHandle, paramString) == Status.OK) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public void close()
  {
    MqttService localMqttService = this.mqttService;
    if (localMqttService != null)
    {
      if (this.clientHandle == null) {
        this.clientHandle = localMqttService.getClient(this.serverURI, this.clientId, this.myContext.getApplicationInfo().packageName, this.persistence);
      }
      this.mqttService.close(this.clientHandle);
    }
  }
  
  public IMqttToken connect()
  {
    return connect(null, null);
  }
  
  public IMqttToken connect(Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    return connect(new MqttConnectOptions(), paramObject, paramIMqttActionListener);
  }
  
  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions)
  {
    return connect(paramMqttConnectOptions, null, null);
  }
  
  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramObject = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener);
    this.connectOptions = paramMqttConnectOptions;
    this.connectToken = ((IMqttToken)paramObject);
    if (this.mqttService == null)
    {
      paramIMqttActionListener = new Intent();
      paramIMqttActionListener.setClassName(this.myContext, "org.eclipse.paho.android.service.MqttService");
      if (this.myContext.startService(paramIMqttActionListener) == null)
      {
        paramMqttConnectOptions = ((IMqttToken)paramObject).getActionCallback();
        if (paramMqttConnectOptions != null) {
          paramMqttConnectOptions.onFailure((IMqttToken)paramObject, new RuntimeException("cannot start service org.eclipse.paho.android.service.MqttService"));
        }
      }
      this.myContext.bindService(paramIMqttActionListener, this.serviceConnection, 1);
      if (!this.receiverRegistered) {
        registerReceiver(this);
      }
    }
    else
    {
      pool.execute(new Runnable()
      {
        public void run()
        {
          MqttAndroidClient.this.doConnect();
          if (!MqttAndroidClient.this.receiverRegistered)
          {
            MqttAndroidClient localMqttAndroidClient = MqttAndroidClient.this;
            localMqttAndroidClient.registerReceiver(localMqttAndroidClient);
          }
        }
      });
    }
    return (IMqttToken)paramObject;
  }
  
  public void deleteBufferedMessage(int paramInt)
  {
    this.mqttService.deleteBufferedMessage(this.clientHandle, paramInt);
  }
  
  public IMqttToken disconnect()
  {
    MqttTokenAndroid localMqttTokenAndroid = new MqttTokenAndroid(this, null, null);
    String str = storeToken(localMqttTokenAndroid);
    this.mqttService.disconnect(this.clientHandle, null, str);
    return localMqttTokenAndroid;
  }
  
  public IMqttToken disconnect(long paramLong)
  {
    MqttTokenAndroid localMqttTokenAndroid = new MqttTokenAndroid(this, null, null);
    String str = storeToken(localMqttTokenAndroid);
    this.mqttService.disconnect(this.clientHandle, paramLong, null, str);
    return localMqttTokenAndroid;
  }
  
  public IMqttToken disconnect(long paramLong, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramIMqttActionListener = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener);
    paramObject = storeToken(paramIMqttActionListener);
    this.mqttService.disconnect(this.clientHandle, paramLong, null, (String)paramObject);
    return paramIMqttActionListener;
  }
  
  public IMqttToken disconnect(Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramIMqttActionListener = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener);
    paramObject = storeToken(paramIMqttActionListener);
    this.mqttService.disconnect(this.clientHandle, null, (String)paramObject);
    return paramIMqttActionListener;
  }
  
  public void disconnectForcibly()
  {
    throw new UnsupportedOperationException();
  }
  
  public void disconnectForcibly(long paramLong)
  {
    throw new UnsupportedOperationException();
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2)
  {
    throw new UnsupportedOperationException();
  }
  
  public MqttMessage getBufferedMessage(int paramInt)
  {
    return this.mqttService.getBufferedMessage(this.clientHandle, paramInt);
  }
  
  public int getBufferedMessageCount()
  {
    return this.mqttService.getBufferedMessageCount(this.clientHandle);
  }
  
  public String getClientId()
  {
    return this.clientId;
  }
  
  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.mqttService.getPendingDeliveryTokens(this.clientHandle);
  }
  
  public SSLSocketFactory getSSLSocketFactory(InputStream paramInputStream, String paramString)
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance("BKS");
      localKeyStore.load(paramInputStream, paramString.toCharArray());
      paramInputStream = TrustManagerFactory.getInstance("X509");
      paramInputStream.init(localKeyStore);
      paramString = paramInputStream.getTrustManagers();
      paramInputStream = SSLContext.getInstance("TLSv1");
      paramInputStream.init(null, paramString, null);
      paramInputStream = paramInputStream.getSocketFactory();
      return paramInputStream;
    }
    catch (KeyManagementException paramInputStream) {}catch (NoSuchAlgorithmException paramInputStream) {}catch (IOException paramInputStream) {}catch (CertificateException paramInputStream) {}catch (KeyStoreException paramInputStream) {}
    throw new MqttSecurityException(paramInputStream);
  }
  
  public String getServerURI()
  {
    return this.serverURI;
  }
  
  public boolean isConnected()
  {
    String str = this.clientHandle;
    if (str != null)
    {
      MqttService localMqttService = this.mqttService;
      if ((localMqttService != null) && (localMqttService.isConnected(str))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public void messageArrivedComplete(int paramInt1, int paramInt2)
  {
    throw new UnsupportedOperationException();
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramContext = paramIntent.getExtras();
    paramIntent = paramContext.getString("MqttService.clientHandle");
    if ((paramIntent != null) && (paramIntent.equals(this.clientHandle)))
    {
      paramIntent = paramContext.getString("MqttService.callbackAction");
      if ("connect".equals(paramIntent)) {
        connectAction(paramContext);
      } else if ("connectExtended".equals(paramIntent)) {
        connectExtendedAction(paramContext);
      } else if ("messageArrived".equals(paramIntent)) {
        messageArrivedAction(paramContext);
      } else if ("subscribe".equals(paramIntent)) {
        subscribeAction(paramContext);
      } else if ("unsubscribe".equals(paramIntent)) {
        unSubscribeAction(paramContext);
      } else if ("send".equals(paramIntent)) {
        sendAction(paramContext);
      } else if ("messageDelivered".equals(paramIntent)) {
        messageDeliveredAction(paramContext);
      } else if ("onConnectionLost".equals(paramIntent)) {
        connectionLostAction(paramContext);
      } else if ("disconnect".equals(paramIntent)) {
        disconnected(paramContext);
      } else if ("trace".equals(paramIntent)) {
        traceAction(paramContext);
      } else {
        this.mqttService.traceError("MqttService", "Callback action doesn't exist.");
      }
      return;
    }
  }
  
  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage)
  {
    return publish(paramString, paramMqttMessage, null, null);
  }
  
  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramObject = new MqttDeliveryTokenAndroid(this, paramObject, paramIMqttActionListener, paramMqttMessage);
    paramIMqttActionListener = storeToken((IMqttToken)paramObject);
    ((MqttDeliveryTokenAndroid)paramObject).setDelegate(this.mqttService.publish(this.clientHandle, paramString, paramMqttMessage, null, paramIMqttActionListener));
    return (IMqttDeliveryToken)paramObject;
  }
  
  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    return publish(paramString, paramArrayOfByte, paramInt, paramBoolean, null, null);
  }
  
  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    MqttMessage localMqttMessage = new MqttMessage(paramArrayOfByte);
    localMqttMessage.setQos(paramInt);
    localMqttMessage.setRetained(paramBoolean);
    paramIMqttActionListener = new MqttDeliveryTokenAndroid(this, paramObject, paramIMqttActionListener, localMqttMessage);
    paramObject = storeToken(paramIMqttActionListener);
    paramIMqttActionListener.setDelegate(this.mqttService.publish(this.clientHandle, paramString, paramArrayOfByte, paramInt, paramBoolean, null, (String)paramObject));
    return paramIMqttActionListener;
  }
  
  public void registerResources(Context paramContext)
  {
    if (paramContext != null)
    {
      this.myContext = paramContext;
      if (!this.receiverRegistered) {
        registerReceiver(this);
      }
    }
  }
  
  public void setBufferOpts(DisconnectedBufferOptions paramDisconnectedBufferOptions)
  {
    this.mqttService.setBufferOpts(this.clientHandle, paramDisconnectedBufferOptions);
  }
  
  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.callback = paramMqttCallback;
  }
  
  public void setManualAcks(boolean paramBoolean)
  {
    throw new UnsupportedOperationException();
  }
  
  public void setTraceCallback(MqttTraceHandler paramMqttTraceHandler)
  {
    this.traceCallback = paramMqttTraceHandler;
  }
  
  public void setTraceEnabled(boolean paramBoolean)
  {
    this.traceEnabled = paramBoolean;
    MqttService localMqttService = this.mqttService;
    if (localMqttService != null) {
      localMqttService.setTraceEnabled(paramBoolean);
    }
  }
  
  public IMqttToken subscribe(String paramString, int paramInt)
  {
    return subscribe(paramString, paramInt, null, null);
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramObject = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener, new String[] { paramString });
    paramIMqttActionListener = storeToken((IMqttToken)paramObject);
    this.mqttService.subscribe(this.clientHandle, paramString, paramInt, null, paramIMqttActionListener);
    return (IMqttToken)paramObject;
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, paramObject, paramIMqttActionListener, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribe(paramString, paramInt, null, null, paramIMqttMessageListener);
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    return subscribe(paramArrayOfString, paramArrayOfInt, null, null);
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramIMqttActionListener = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener, paramArrayOfString);
    paramObject = storeToken(paramIMqttActionListener);
    this.mqttService.subscribe(this.clientHandle, paramArrayOfString, paramArrayOfInt, null, (String)paramObject);
    return paramIMqttActionListener;
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    paramObject = storeToken(new MqttTokenAndroid(this, paramObject, paramIMqttActionListener, paramArrayOfString));
    this.mqttService.subscribe(this.clientHandle, paramArrayOfString, paramArrayOfInt, null, (String)paramObject, paramArrayOfIMqttMessageListener);
    return null;
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    return subscribe(paramArrayOfString, paramArrayOfInt, null, null, paramArrayOfIMqttMessageListener);
  }
  
  public void unregisterResources()
  {
    if ((this.myContext != null) && (this.receiverRegistered)) {
      try
      {
        LocalBroadcastManager.getInstance(this.myContext).unregisterReceiver(this);
        this.receiverRegistered = false;
        if (this.bindedService) {}
        return;
      }
      finally
      {
        try
        {
          this.myContext.unbindService(this.serviceConnection);
          this.bindedService = false;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;) {}
        }
        localObject = finally;
      }
    }
  }
  
  public IMqttToken unsubscribe(String paramString)
  {
    return unsubscribe(paramString, null, null);
  }
  
  public IMqttToken unsubscribe(String paramString, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramObject = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener);
    paramIMqttActionListener = storeToken((IMqttToken)paramObject);
    this.mqttService.unsubscribe(this.clientHandle, paramString, null, paramIMqttActionListener);
    return (IMqttToken)paramObject;
  }
  
  public IMqttToken unsubscribe(String[] paramArrayOfString)
  {
    return unsubscribe(paramArrayOfString, null, null);
  }
  
  public IMqttToken unsubscribe(String[] paramArrayOfString, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramIMqttActionListener = new MqttTokenAndroid(this, paramObject, paramIMqttActionListener);
    paramObject = storeToken(paramIMqttActionListener);
    this.mqttService.unsubscribe(this.clientHandle, paramArrayOfString, null, (String)paramObject);
    return paramIMqttActionListener;
  }
  
  public static enum Ack
  {
    AUTO_ACK,  MANUAL_ACK;
    
    private Ack() {}
  }
  
  private final class MyServiceConnection
    implements ServiceConnection
  {
    private MyServiceConnection() {}
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      MqttAndroidClient.access$002(MqttAndroidClient.this, ((MqttServiceBinder)paramIBinder).getService());
      MqttAndroidClient.access$102(MqttAndroidClient.this, true);
      MqttAndroidClient.this.doConnect();
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      MqttAndroidClient.access$002(MqttAndroidClient.this, null);
    }
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttAndroidClient.class
 *
 * Reversed by:           J
 */