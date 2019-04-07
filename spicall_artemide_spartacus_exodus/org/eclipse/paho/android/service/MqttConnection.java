package org.eclipse.paho.android.service;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.security.d.a;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

class MqttConnection
  implements MqttCallbackExtended
{
  private static final String NOT_CONNECTED = "not connected";
  private static final String TAG = "MqttConnection";
  private AlarmPingSender alarmPingSender = null;
  private DisconnectedBufferOptions bufferOpts = null;
  private boolean cleanSession = true;
  private String clientHandle;
  private String clientId;
  private MqttConnectOptions connectOptions;
  private volatile boolean disconnected = true;
  private volatile boolean isConnecting = false;
  private MqttAsyncClient myClient = null;
  private MqttClientPersistence persistence = null;
  private String reconnectActivityToken = null;
  private Map<IMqttDeliveryToken, String> savedActivityTokens = new HashMap();
  private Map<IMqttDeliveryToken, String> savedInvocationContexts = new HashMap();
  private Map<IMqttDeliveryToken, MqttMessage> savedSentMessages = new HashMap();
  private Map<IMqttDeliveryToken, String> savedTopics = new HashMap();
  private String serverURI;
  private MqttService service = null;
  private String wakeLockTag = null;
  private PowerManager.WakeLock wakelock = null;
  
  MqttConnection(MqttService paramMqttService, String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence, String paramString3)
  {
    this.serverURI = paramString1;
    this.service = paramMqttService;
    this.clientId = paramString2;
    this.persistence = paramMqttClientPersistence;
    this.clientHandle = paramString3;
    paramMqttService = new StringBuilder(getClass().getCanonicalName());
    paramMqttService.append(" ");
    paramMqttService.append(paramString2);
    paramMqttService.append(" ");
    paramMqttService.append("on host ");
    paramMqttService.append(paramString1);
    this.wakeLockTag = paramMqttService.toString();
  }
  
  private void acquireWakeLock()
  {
    if (this.wakelock == null) {
      this.wakelock = ((PowerManager)this.service.getSystemService("power")).newWakeLock(1, this.wakeLockTag);
    }
    this.wakelock.acquire();
  }
  
  private void deliverBacklog()
  {
    Iterator localIterator = this.service.messageStore.getAllArrivedMessages(this.clientHandle);
    while (localIterator.hasNext())
    {
      Object localObject = (MessageStore.StoredMessage)localIterator.next();
      localObject = messageToBundle(((MessageStore.StoredMessage)localObject).getMessageId(), ((MessageStore.StoredMessage)localObject).getTopic(), ((MessageStore.StoredMessage)localObject).getMessage());
      ((Bundle)localObject).putString("MqttService.callbackAction", "messageArrived");
      this.service.callbackToActivity(this.clientHandle, Status.OK, (Bundle)localObject);
    }
  }
  
  private void doAfterConnectFail(Bundle paramBundle)
  {
    acquireWakeLock();
    this.disconnected = true;
    setConnectingState(false);
    this.service.callbackToActivity(this.clientHandle, Status.ERROR, paramBundle);
    releaseWakeLock();
  }
  
  private void doAfterConnectSuccess(Bundle paramBundle)
  {
    acquireWakeLock();
    this.service.callbackToActivity(this.clientHandle, Status.OK, paramBundle);
    deliverBacklog();
    setConnectingState(false);
    this.disconnected = false;
    releaseWakeLock();
  }
  
  private void handleException(Bundle paramBundle, Exception paramException)
  {
    paramBundle.putString("MqttService.errorMessage", paramException.getLocalizedMessage());
    paramBundle.putSerializable("MqttService.exception", paramException);
    this.service.callbackToActivity(this.clientHandle, Status.ERROR, paramBundle);
  }
  
  private Bundle messageToBundle(String paramString1, String paramString2, MqttMessage paramMqttMessage)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.messageId", paramString1);
    localBundle.putString("MqttService.destinationName", paramString2);
    localBundle.putParcelable("MqttService.PARCEL", new ParcelableMqttMessage(paramMqttMessage));
    return localBundle;
  }
  
  private void releaseWakeLock()
  {
    PowerManager.WakeLock localWakeLock = this.wakelock;
    if ((localWakeLock != null) && (localWakeLock.isHeld())) {
      this.wakelock.release();
    }
  }
  
  private void setConnectingState(boolean paramBoolean)
  {
    try
    {
      this.isConnecting = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private void storeSendDetails(String paramString1, MqttMessage paramMqttMessage, IMqttDeliveryToken paramIMqttDeliveryToken, String paramString2, String paramString3)
  {
    this.savedTopics.put(paramIMqttDeliveryToken, paramString1);
    this.savedSentMessages.put(paramIMqttDeliveryToken, paramMqttMessage);
    this.savedActivityTokens.put(paramIMqttDeliveryToken, paramString3);
    this.savedInvocationContexts.put(paramIMqttDeliveryToken, paramString2);
  }
  
  void close()
  {
    this.service.traceDebug("MqttConnection", "close()");
    try
    {
      if (this.myClient != null) {
        this.myClient.close();
      }
    }
    catch (MqttException localMqttException)
    {
      handleException(new Bundle(), localMqttException);
    }
  }
  
  public void connect(MqttConnectOptions paramMqttConnectOptions, String paramString1, String paramString2)
  {
    this.connectOptions = paramMqttConnectOptions;
    this.reconnectActivityToken = paramString2;
    if (paramMqttConnectOptions != null) {
      this.cleanSession = paramMqttConnectOptions.isCleanSession();
    }
    if (this.connectOptions.isCleanSession()) {
      this.service.messageStore.clearArrivedMessages(this.clientHandle);
    }
    Object localObject = this.service;
    paramMqttConnectOptions = new StringBuilder();
    paramMqttConnectOptions.append("Connecting {");
    paramMqttConnectOptions.append(this.serverURI);
    paramMqttConnectOptions.append("} as {");
    paramMqttConnectOptions.append(this.clientId);
    paramMqttConnectOptions.append("}");
    ((MqttService)localObject).traceDebug("MqttConnection", paramMqttConnectOptions.toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.activityToken", paramString2);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString1);
    ((Bundle)localObject).putString("MqttService.callbackAction", "connect");
    try
    {
      if (this.persistence == null)
      {
        paramString2 = this.service.getExternalFilesDir("MqttConnection");
        paramMqttConnectOptions = paramString2;
        if (paramString2 == null)
        {
          paramString2 = this.service.getDir("MqttConnection", 0);
          paramMqttConnectOptions = paramString2;
          if (paramString2 == null)
          {
            ((Bundle)localObject).putString("MqttService.errorMessage", "Error! No external and internal storage available");
            paramMqttConnectOptions = new org/eclipse/paho/client/mqttv3/MqttPersistenceException;
            paramMqttConnectOptions.<init>();
            ((Bundle)localObject).putSerializable("MqttService.exception", paramMqttConnectOptions);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
            return;
          }
        }
        paramString2 = new org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence;
        paramString2.<init>(paramMqttConnectOptions.getAbsolutePath());
        this.persistence = paramString2;
      }
      paramMqttConnectOptions = new org/eclipse/paho/android/service/MqttConnection$1;
      paramMqttConnectOptions.<init>(this, (Bundle)localObject, (Bundle)localObject);
      if (this.myClient != null)
      {
        if (this.isConnecting)
        {
          this.service.traceDebug("MqttConnection", "myClient != null and the client is connecting. Connect return directly.");
          paramString1 = this.service;
          paramMqttConnectOptions = new java/lang/StringBuilder;
          paramMqttConnectOptions.<init>();
          paramMqttConnectOptions.append("Connect return:isConnecting:");
          paramMqttConnectOptions.append(this.isConnecting);
          paramMqttConnectOptions.append(".disconnected:");
          paramMqttConnectOptions.append(this.disconnected);
          paramString1.traceDebug("MqttConnection", paramMqttConnectOptions.toString());
        }
        else if (!this.disconnected)
        {
          this.service.traceDebug("MqttConnection", "myClient != null and the client is connected and notify!");
          doAfterConnectSuccess((Bundle)localObject);
        }
        else
        {
          this.service.traceDebug("MqttConnection", "myClient != null and the client is not connected");
          this.service.traceDebug("MqttConnection", "Do Real connect!");
          setConnectingState(true);
          this.myClient.connect(this.connectOptions, paramString1, paramMqttConnectOptions);
        }
      }
      else
      {
        paramString2 = new org/eclipse/paho/android/service/AlarmPingSender;
        paramString2.<init>(this.service);
        this.alarmPingSender = paramString2;
        paramString2 = new org/eclipse/paho/client/mqttv3/MqttAsyncClient;
        paramString2.<init>(this.serverURI, this.clientId, this.persistence, this.alarmPingSender);
        this.myClient = paramString2;
        this.myClient.setCallback(this);
        this.service.traceDebug("MqttConnection", "Do Real connect!");
        setConnectingState(true);
        this.myClient.connect(this.connectOptions, paramString1, paramMqttConnectOptions);
      }
    }
    catch (Exception paramString1)
    {
      paramMqttConnectOptions = this.service;
      paramString2 = new StringBuilder();
      paramString2.append("Exception occurred attempting to connect: ");
      paramString2.append(paramString1.getMessage());
      paramMqttConnectOptions.traceError("MqttConnection", paramString2.toString());
      setConnectingState(false);
      handleException((Bundle)localObject, paramString1);
    }
  }
  
  public void connectComplete(boolean paramBoolean, String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.callbackAction", "connectExtended");
    localBundle.putBoolean("MqttService.reconnect", paramBoolean);
    localBundle.putString("MqttService.serverURI", paramString);
    this.service.callbackToActivity(this.clientHandle, Status.OK, localBundle);
  }
  
  public void connectionLost(Throwable paramThrowable)
  {
    Object localObject1 = this.service;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("connectionLost(");
    ((StringBuilder)localObject2).append(paramThrowable.getMessage());
    ((StringBuilder)localObject2).append(")");
    ((MqttService)localObject1).traceDebug("MqttConnection", ((StringBuilder)localObject2).toString());
    this.disconnected = true;
    try
    {
      if (!this.connectOptions.isAutomaticReconnect())
      {
        localObject1 = this.myClient;
        localObject2 = new org/eclipse/paho/android/service/MqttConnection$2;
        ((2)localObject2).<init>(this);
        ((MqttAsyncClient)localObject1).disconnect(null, (IMqttActionListener)localObject2);
      }
      else
      {
        this.alarmPingSender.schedule(100L);
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localObject2 = new Bundle();
    ((Bundle)localObject2).putString("MqttService.callbackAction", "onConnectionLost");
    if (paramThrowable != null)
    {
      ((Bundle)localObject2).putString("MqttService.errorMessage", paramThrowable.getMessage());
      if ((paramThrowable instanceof MqttException)) {
        ((Bundle)localObject2).putSerializable("MqttService.exception", paramThrowable);
      }
      ((Bundle)localObject2).putString("MqttService.exceptionStack", Log.getStackTraceString(paramThrowable));
    }
    this.service.callbackToActivity(this.clientHandle, Status.OK, (Bundle)localObject2);
    releaseWakeLock();
  }
  
  public void deleteBufferedMessage(int paramInt)
  {
    this.myClient.deleteBufferedMessage(paramInt);
  }
  
  public void deliveryComplete(IMqttDeliveryToken paramIMqttDeliveryToken)
  {
    Object localObject1 = this.service;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("deliveryComplete(");
    ((StringBuilder)localObject2).append(paramIMqttDeliveryToken);
    ((StringBuilder)localObject2).append(")");
    ((MqttService)localObject1).traceDebug("MqttConnection", ((StringBuilder)localObject2).toString());
    MqttMessage localMqttMessage = (MqttMessage)this.savedSentMessages.remove(paramIMqttDeliveryToken);
    if (localMqttMessage != null)
    {
      localObject1 = (String)this.savedTopics.remove(paramIMqttDeliveryToken);
      localObject2 = (String)this.savedActivityTokens.remove(paramIMqttDeliveryToken);
      paramIMqttDeliveryToken = (String)this.savedInvocationContexts.remove(paramIMqttDeliveryToken);
      localObject1 = messageToBundle(null, (String)localObject1, localMqttMessage);
      if (localObject2 != null)
      {
        ((Bundle)localObject1).putString("MqttService.callbackAction", "send");
        ((Bundle)localObject1).putString("MqttService.activityToken", (String)localObject2);
        ((Bundle)localObject1).putString("MqttService.invocationContext", paramIMqttDeliveryToken);
        this.service.callbackToActivity(this.clientHandle, Status.OK, (Bundle)localObject1);
      }
      ((Bundle)localObject1).putString("MqttService.callbackAction", "messageDelivered");
      this.service.callbackToActivity(this.clientHandle, Status.OK, (Bundle)localObject1);
    }
  }
  
  void disconnect(long paramLong, String paramString1, String paramString2)
  {
    this.service.traceDebug("MqttConnection", "disconnect()");
    this.disconnected = true;
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.activityToken", paramString2);
    localBundle.putString("MqttService.invocationContext", paramString1);
    localBundle.putString("MqttService.callbackAction", "disconnect");
    paramString2 = this.myClient;
    if ((paramString2 != null) && (paramString2.isConnected()))
    {
      paramString2 = new MqttConnectionListener(localBundle, null);
      try
      {
        this.myClient.disconnect(paramLong, paramString1, paramString2);
      }
      catch (Exception paramString1)
      {
        handleException(localBundle, paramString1);
      }
    }
    else
    {
      localBundle.putString("MqttService.errorMessage", "not connected");
      this.service.traceError("disconnect", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, localBundle);
    }
    paramString1 = this.connectOptions;
    if ((paramString1 != null) && (paramString1.isCleanSession())) {
      this.service.messageStore.clearArrivedMessages(this.clientHandle);
    }
    releaseWakeLock();
  }
  
  void disconnect(String paramString1, String paramString2)
  {
    this.service.traceDebug("MqttConnection", "disconnect()");
    this.disconnected = true;
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.activityToken", paramString2);
    localBundle.putString("MqttService.invocationContext", paramString1);
    localBundle.putString("MqttService.callbackAction", "disconnect");
    paramString2 = this.myClient;
    if ((paramString2 != null) && (paramString2.isConnected()))
    {
      paramString2 = new MqttConnectionListener(localBundle, null);
      try
      {
        this.myClient.disconnect(paramString1, paramString2);
      }
      catch (Exception paramString1)
      {
        handleException(localBundle, paramString1);
      }
    }
    else
    {
      localBundle.putString("MqttService.errorMessage", "not connected");
      this.service.traceError("disconnect", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, localBundle);
    }
    paramString1 = this.connectOptions;
    if ((paramString1 != null) && (paramString1.isCleanSession())) {
      this.service.messageStore.clearArrivedMessages(this.clientHandle);
    }
    releaseWakeLock();
  }
  
  public MqttMessage getBufferedMessage(int paramInt)
  {
    return this.myClient.getBufferedMessage(paramInt);
  }
  
  public int getBufferedMessageCount()
  {
    return this.myClient.getBufferedMessageCount();
  }
  
  public String getClientHandle()
  {
    return this.clientHandle;
  }
  
  public String getClientId()
  {
    return this.clientId;
  }
  
  public MqttConnectOptions getConnectOptions()
  {
    return this.connectOptions;
  }
  
  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.myClient.getPendingDeliveryTokens();
  }
  
  public String getServerURI()
  {
    return this.serverURI;
  }
  
  public boolean isConnected()
  {
    MqttAsyncClient localMqttAsyncClient = this.myClient;
    boolean bool;
    if ((localMqttAsyncClient != null) && (localMqttAsyncClient.isConnected())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void messageArrived(String paramString, MqttMessage paramMqttMessage)
  {
    MqttService localMqttService = this.service;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("messageArrived(");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(",{");
    ((StringBuilder)localObject).append(paramMqttMessage.toString());
    ((StringBuilder)localObject).append("})");
    localMqttService.traceDebug("MqttConnection", ((StringBuilder)localObject).toString());
    localObject = this.service.messageStore.storeArrived(this.clientHandle, paramString, paramMqttMessage);
    paramString = messageToBundle((String)localObject, paramString, paramMqttMessage);
    paramString.putString("MqttService.callbackAction", "messageArrived");
    paramString.putString("MqttService.messageId", (String)localObject);
    this.service.callbackToActivity(this.clientHandle, Status.OK, paramString);
  }
  
  void offline()
  {
    if ((!this.disconnected) && (!this.cleanSession)) {
      connectionLost(new Exception("Android offline"));
    }
  }
  
  public IMqttDeliveryToken publish(String paramString1, MqttMessage paramMqttMessage, String paramString2, String paramString3)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.callbackAction", "send");
    localBundle.putString("MqttService.activityToken", paramString3);
    localBundle.putString("MqttService.invocationContext", paramString2);
    MqttAsyncClient localMqttAsyncClient = this.myClient;
    Object localObject1 = null;
    Object localObject2 = null;
    DisconnectedBufferOptions localDisconnectedBufferOptions = null;
    if ((localMqttAsyncClient != null) && (localMqttAsyncClient.isConnected()))
    {
      localObject2 = new MqttConnectionListener(localBundle, null);
      try
      {
        localObject2 = this.myClient.publish(paramString1, paramMqttMessage, paramString2, (IMqttActionListener)localObject2);
        try
        {
          storeSendDetails(paramString1, paramMqttMessage, (IMqttDeliveryToken)localObject2, paramString2, paramString3);
          paramString1 = (String)localObject2;
        }
        catch (Exception paramMqttMessage)
        {
          paramString1 = (String)localObject2;
        }
        handleException(localBundle, paramMqttMessage);
      }
      catch (Exception paramMqttMessage)
      {
        paramString1 = localDisconnectedBufferOptions;
      }
    }
    else
    {
      if (this.myClient != null)
      {
        localDisconnectedBufferOptions = this.bufferOpts;
        if ((localDisconnectedBufferOptions != null) && (localDisconnectedBufferOptions.isBufferEnabled()))
        {
          localObject2 = new MqttConnectionListener(localBundle, null);
          try
          {
            localObject2 = this.myClient.publish(paramString1, paramMqttMessage, paramString2, (IMqttActionListener)localObject2);
            try
            {
              storeSendDetails(paramString1, paramMqttMessage, (IMqttDeliveryToken)localObject2, paramString2, paramString3);
              paramString1 = (String)localObject2;
            }
            catch (Exception paramMqttMessage)
            {
              paramString1 = (String)localObject2;
            }
            handleException(localBundle, paramMqttMessage);
          }
          catch (Exception paramMqttMessage)
          {
            paramString1 = (String)localObject1;
          }
          return paramString1;
        }
      }
      a.c("MqttConnection", "Client is not connected, so not sending message", new Object[0]);
      localBundle.putString("MqttService.errorMessage", "not connected");
      this.service.traceError("send", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, localBundle);
      paramString1 = (String)localObject2;
    }
    return paramString1;
  }
  
  public IMqttDeliveryToken publish(String paramString1, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, String paramString2, String paramString3)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("MqttService.callbackAction", "send");
    localBundle.putString("MqttService.activityToken", paramString3);
    localBundle.putString("MqttService.invocationContext", paramString2);
    Object localObject1 = this.myClient;
    MqttMessage localMqttMessage = null;
    Object localObject2 = null;
    if ((localObject1 != null) && (((MqttAsyncClient)localObject1).isConnected()))
    {
      localObject1 = new MqttConnectionListener(localBundle, null);
      try
      {
        localMqttMessage = new org/eclipse/paho/client/mqttv3/MqttMessage;
        localMqttMessage.<init>(paramArrayOfByte);
        localMqttMessage.setQos(paramInt);
        localMqttMessage.setRetained(paramBoolean);
        paramArrayOfByte = this.myClient.publish(paramString1, paramArrayOfByte, paramInt, paramBoolean, paramString2, (IMqttActionListener)localObject1);
        try
        {
          storeSendDetails(paramString1, localMqttMessage, paramArrayOfByte, paramString2, paramString3);
          paramString1 = paramArrayOfByte;
        }
        catch (Exception paramString1)
        {
          paramString2 = paramString1;
          paramString1 = paramArrayOfByte;
        }
        handleException(localBundle, paramString2);
      }
      catch (Exception paramArrayOfByte)
      {
        paramString1 = (String)localObject2;
        paramString2 = paramArrayOfByte;
      }
    }
    else
    {
      localBundle.putString("MqttService.errorMessage", "not connected");
      this.service.traceError("send", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, localBundle);
      paramString1 = localMqttMessage;
    }
    return paramString1;
  }
  
  void reconnect()
  {
    try
    {
      if (this.myClient == null)
      {
        this.service.traceError("MqttConnection", "Reconnect myClient = null. Will not do reconnect");
        return;
      }
      if (this.isConnecting)
      {
        this.service.traceDebug("MqttConnection", "The client is connecting. Reconnect return directly.");
        return;
      }
      if (!this.service.isOnline())
      {
        this.service.traceDebug("MqttConnection", "The network is not reachable. Will not do reconnect");
        return;
      }
      Object localObject1;
      Object localObject3;
      if (this.connectOptions.isAutomaticReconnect())
      {
        a.c("MqttConnection", "Requesting Automatic reconnect using New Java AC", new Object[0]);
        localObject1 = new android/os/Bundle;
        ((Bundle)localObject1).<init>();
        ((Bundle)localObject1).putString("MqttService.activityToken", this.reconnectActivityToken);
        ((Bundle)localObject1).putString("MqttService.invocationContext", null);
        ((Bundle)localObject1).putString("MqttService.callbackAction", "connect");
        try
        {
          this.myClient.reconnect();
        }
        catch (MqttException localMqttException1)
        {
          localObject3 = new java/lang/StringBuilder;
          ((StringBuilder)localObject3).<init>();
          ((StringBuilder)localObject3).append("Exception occurred attempting to reconnect: ");
          ((StringBuilder)localObject3).append(localMqttException1.getMessage());
          a.a("MqttConnection", ((StringBuilder)localObject3).toString(), new Object[0]);
          setConnectingState(false);
          handleException((Bundle)localObject1, localMqttException1);
        }
      }
      else if ((this.disconnected) && (!this.cleanSession))
      {
        this.service.traceDebug("MqttConnection", "Do Real Reconnect!");
        Bundle localBundle = new android/os/Bundle;
        localBundle.<init>();
        localBundle.putString("MqttService.activityToken", this.reconnectActivityToken);
        localBundle.putString("MqttService.invocationContext", null);
        localBundle.putString("MqttService.callbackAction", "connect");
        try
        {
          localObject3 = new org/eclipse/paho/android/service/MqttConnection$3;
          ((3)localObject3).<init>(this, localBundle, localBundle);
          this.myClient.connect(this.connectOptions, null, (IMqttActionListener)localObject3);
          setConnectingState(true);
        }
        catch (Exception localException)
        {
          localObject1 = this.service;
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("Cannot reconnect to remote server.");
          localStringBuilder.append(localException.getMessage());
          ((MqttService)localObject1).traceError("MqttConnection", localStringBuilder.toString());
          setConnectingState(false);
          localObject1 = new org/eclipse/paho/client/mqttv3/MqttException;
          ((MqttException)localObject1).<init>(6, localException.getCause());
          handleException(localBundle, (Exception)localObject1);
        }
        catch (MqttException localMqttException2)
        {
          MqttService localMqttService = this.service;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append("Cannot reconnect to remote server.");
          ((StringBuilder)localObject1).append(localMqttException2.getMessage());
          localMqttService.traceError("MqttConnection", ((StringBuilder)localObject1).toString());
          setConnectingState(false);
          handleException(localBundle, localMqttException2);
        }
      }
      return;
    }
    finally {}
  }
  
  public void setBufferOpts(DisconnectedBufferOptions paramDisconnectedBufferOptions)
  {
    this.bufferOpts = paramDisconnectedBufferOptions;
    this.myClient.setBufferOpts(paramDisconnectedBufferOptions);
  }
  
  public void setClientHandle(String paramString)
  {
    this.clientHandle = paramString;
  }
  
  public void setClientId(String paramString)
  {
    this.clientId = paramString;
  }
  
  public void setConnectOptions(MqttConnectOptions paramMqttConnectOptions)
  {
    this.connectOptions = paramMqttConnectOptions;
  }
  
  public void setServerURI(String paramString)
  {
    this.serverURI = paramString;
  }
  
  public void subscribe(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    Object localObject = this.service;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("subscribe({");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("},");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(",{");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("}, {");
    localStringBuilder.append(paramString3);
    localStringBuilder.append("}");
    ((MqttService)localObject).traceDebug("MqttConnection", localStringBuilder.toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.callbackAction", "subscribe");
    ((Bundle)localObject).putString("MqttService.activityToken", paramString3);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString2);
    paramString3 = this.myClient;
    if ((paramString3 != null) && (paramString3.isConnected()))
    {
      paramString3 = new MqttConnectionListener((Bundle)localObject, null);
      try
      {
        this.myClient.subscribe(paramString1, paramInt, paramString2, paramString3);
      }
      catch (Exception paramString1)
      {
        handleException((Bundle)localObject, paramString1);
      }
    }
    else
    {
      ((Bundle)localObject).putString("MqttService.errorMessage", "not connected");
      this.service.traceError("subscribe", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
    }
  }
  
  public void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, String paramString1, String paramString2)
  {
    Object localObject = this.service;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("subscribe({");
    localStringBuilder.append(Arrays.toString(paramArrayOfString));
    localStringBuilder.append("},");
    localStringBuilder.append(Arrays.toString(paramArrayOfInt));
    localStringBuilder.append(",{");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("}, {");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("}");
    ((MqttService)localObject).traceDebug("MqttConnection", localStringBuilder.toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.callbackAction", "subscribe");
    ((Bundle)localObject).putString("MqttService.activityToken", paramString2);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString1);
    paramString2 = this.myClient;
    if ((paramString2 != null) && (paramString2.isConnected()))
    {
      paramString2 = new MqttConnectionListener((Bundle)localObject, null);
      try
      {
        this.myClient.subscribe(paramArrayOfString, paramArrayOfInt, paramString1, paramString2);
      }
      catch (Exception paramArrayOfString)
      {
        handleException((Bundle)localObject, paramArrayOfString);
      }
    }
    else
    {
      ((Bundle)localObject).putString("MqttService.errorMessage", "not connected");
      this.service.traceError("subscribe", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
    }
  }
  
  public void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, String paramString1, String paramString2, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    Object localObject = this.service;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("subscribe({");
    localStringBuilder.append(Arrays.toString(paramArrayOfString));
    localStringBuilder.append("},");
    localStringBuilder.append(Arrays.toString(paramArrayOfInt));
    localStringBuilder.append(",{");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("}, {");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("}");
    ((MqttService)localObject).traceDebug("MqttConnection", localStringBuilder.toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.callbackAction", "subscribe");
    ((Bundle)localObject).putString("MqttService.activityToken", paramString2);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString1);
    paramString1 = this.myClient;
    if ((paramString1 != null) && (paramString1.isConnected()))
    {
      new MqttConnectionListener((Bundle)localObject, null);
      try
      {
        this.myClient.subscribe(paramArrayOfString, paramArrayOfInt, paramArrayOfIMqttMessageListener);
      }
      catch (Exception paramArrayOfString)
      {
        handleException((Bundle)localObject, paramArrayOfString);
      }
    }
    else
    {
      ((Bundle)localObject).putString("MqttService.errorMessage", "not connected");
      this.service.traceError("subscribe", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
    }
  }
  
  void unsubscribe(String paramString1, String paramString2, String paramString3)
  {
    MqttService localMqttService = this.service;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("unsubscribe({");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("},{");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append("}, {");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).append("})");
    localMqttService.traceDebug("MqttConnection", ((StringBuilder)localObject).toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.callbackAction", "unsubscribe");
    ((Bundle)localObject).putString("MqttService.activityToken", paramString3);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString2);
    paramString3 = this.myClient;
    if ((paramString3 != null) && (paramString3.isConnected()))
    {
      paramString3 = new MqttConnectionListener((Bundle)localObject, null);
      try
      {
        this.myClient.unsubscribe(paramString1, paramString2, paramString3);
      }
      catch (Exception paramString1)
      {
        handleException((Bundle)localObject, paramString1);
      }
    }
    else
    {
      ((Bundle)localObject).putString("MqttService.errorMessage", "not connected");
      this.service.traceError("subscribe", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
    }
  }
  
  void unsubscribe(String[] paramArrayOfString, String paramString1, String paramString2)
  {
    MqttService localMqttService = this.service;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("unsubscribe({");
    ((StringBuilder)localObject).append(Arrays.toString(paramArrayOfString));
    ((StringBuilder)localObject).append("},{");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("}, {");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append("})");
    localMqttService.traceDebug("MqttConnection", ((StringBuilder)localObject).toString());
    localObject = new Bundle();
    ((Bundle)localObject).putString("MqttService.callbackAction", "unsubscribe");
    ((Bundle)localObject).putString("MqttService.activityToken", paramString2);
    ((Bundle)localObject).putString("MqttService.invocationContext", paramString1);
    paramString2 = this.myClient;
    if ((paramString2 != null) && (paramString2.isConnected()))
    {
      paramString2 = new MqttConnectionListener((Bundle)localObject, null);
      try
      {
        this.myClient.unsubscribe(paramArrayOfString, paramString1, paramString2);
      }
      catch (Exception paramArrayOfString)
      {
        handleException((Bundle)localObject, paramArrayOfString);
      }
    }
    else
    {
      ((Bundle)localObject).putString("MqttService.errorMessage", "not connected");
      this.service.traceError("subscribe", "not connected");
      this.service.callbackToActivity(this.clientHandle, Status.ERROR, (Bundle)localObject);
    }
  }
  
  private class MqttConnectionListener
    implements IMqttActionListener
  {
    private final Bundle resultBundle;
    
    private MqttConnectionListener(Bundle paramBundle)
    {
      this.resultBundle = paramBundle;
    }
    
    public void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable)
    {
      this.resultBundle.putString("MqttService.errorMessage", paramThrowable.getLocalizedMessage());
      this.resultBundle.putSerializable("MqttService.exception", paramThrowable);
      MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, this.resultBundle);
    }
    
    public void onSuccess(IMqttToken paramIMqttToken)
    {
      MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.OK, this.resultBundle);
    }
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttConnection.class
 *
 * Reversed by:           J
 */