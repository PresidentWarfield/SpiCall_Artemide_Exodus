package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

class MqttTokenAndroid
  implements IMqttToken
{
  private MqttAndroidClient client;
  private IMqttToken delegate;
  private volatile boolean isComplete;
  private volatile MqttException lastException;
  private IMqttActionListener listener;
  private MqttException pendingException;
  private String[] topics;
  private Object userContext;
  private Object waitObject = new Object();
  
  MqttTokenAndroid(MqttAndroidClient paramMqttAndroidClient, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    this(paramMqttAndroidClient, paramObject, paramIMqttActionListener, null);
  }
  
  MqttTokenAndroid(MqttAndroidClient paramMqttAndroidClient, Object paramObject, IMqttActionListener paramIMqttActionListener, String[] paramArrayOfString)
  {
    this.client = paramMqttAndroidClient;
    this.userContext = paramObject;
    this.listener = paramIMqttActionListener;
    this.topics = paramArrayOfString;
  }
  
  public IMqttActionListener getActionCallback()
  {
    return this.listener;
  }
  
  public IMqttAsyncClient getClient()
  {
    return this.client;
  }
  
  public MqttException getException()
  {
    return this.lastException;
  }
  
  public int[] getGrantedQos()
  {
    return this.delegate.getGrantedQos();
  }
  
  public int getMessageId()
  {
    IMqttToken localIMqttToken = this.delegate;
    int i;
    if (localIMqttToken != null) {
      i = localIMqttToken.getMessageId();
    } else {
      i = 0;
    }
    return i;
  }
  
  public MqttWireMessage getResponse()
  {
    return this.delegate.getResponse();
  }
  
  public boolean getSessionPresent()
  {
    return this.delegate.getSessionPresent();
  }
  
  public String[] getTopics()
  {
    return this.topics;
  }
  
  public Object getUserContext()
  {
    return this.userContext;
  }
  
  public boolean isComplete()
  {
    return this.isComplete;
  }
  
  void notifyComplete()
  {
    synchronized (this.waitObject)
    {
      this.isComplete = true;
      this.waitObject.notifyAll();
      if (this.listener != null) {
        this.listener.onSuccess(this);
      }
      return;
    }
  }
  
  void notifyFailure(Throwable paramThrowable)
  {
    synchronized (this.waitObject)
    {
      this.isComplete = true;
      if ((paramThrowable instanceof MqttException))
      {
        this.pendingException = ((MqttException)paramThrowable);
      }
      else
      {
        MqttException localMqttException = new org/eclipse/paho/client/mqttv3/MqttException;
        localMqttException.<init>(paramThrowable);
        this.pendingException = localMqttException;
      }
      this.waitObject.notifyAll();
      if ((paramThrowable instanceof MqttException)) {
        this.lastException = ((MqttException)paramThrowable);
      }
      if (this.listener != null) {
        this.listener.onFailure(this, paramThrowable);
      }
      return;
    }
  }
  
  public void setActionCallback(IMqttActionListener paramIMqttActionListener)
  {
    this.listener = paramIMqttActionListener;
  }
  
  void setComplete(boolean paramBoolean)
  {
    this.isComplete = paramBoolean;
  }
  
  void setDelegate(IMqttToken paramIMqttToken)
  {
    this.delegate = paramIMqttToken;
  }
  
  void setException(MqttException paramMqttException)
  {
    this.lastException = paramMqttException;
  }
  
  public void setUserContext(Object paramObject)
  {
    this.userContext = paramObject;
  }
  
  public void waitForCompletion()
  {
    try
    {
      synchronized (this.waitObject)
      {
        this.waitObject.wait();
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
    ??? = this.pendingException;
    if (??? == null) {
      return;
    }
    throw ((Throwable)???);
    throw ((Throwable)localObject2);
  }
  
  public void waitForCompletion(long paramLong)
  {
    try
    {
      synchronized (this.waitObject)
      {
        this.waitObject.wait(paramLong);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      MqttException localMqttException;
      for (;;) {}
    }
    if (this.isComplete)
    {
      if (this.pendingException == null) {
        return;
      }
      throw this.pendingException;
    }
    localMqttException = new org/eclipse/paho/client/mqttv3/MqttException;
    localMqttException.<init>(32000);
    throw localMqttException;
    throw localMqttException;
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttTokenAndroid.class
 *
 * Reversed by:           J
 */