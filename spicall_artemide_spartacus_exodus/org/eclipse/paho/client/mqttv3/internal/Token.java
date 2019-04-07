package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Token
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.Token";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private IMqttActionListener callback = null;
  private IMqttAsyncClient client = null;
  private volatile boolean completed = false;
  private MqttException exception = null;
  private String key;
  protected MqttMessage message = null;
  private int messageID = 0;
  private boolean notified = false;
  private boolean pendingComplete = false;
  private MqttWireMessage response = null;
  private Object responseLock = new Object();
  private boolean sent = false;
  private Object sentLock = new Object();
  private String[] topics = null;
  private Object userContext = null;
  
  public Token(String paramString)
  {
    log.setResourceName(paramString);
  }
  
  public boolean checkResult()
  {
    if (getException() == null) {
      return true;
    }
    throw getException();
  }
  
  public IMqttActionListener getActionCallback()
  {
    return this.callback;
  }
  
  public IMqttAsyncClient getClient()
  {
    return this.client;
  }
  
  public MqttException getException()
  {
    return this.exception;
  }
  
  public int[] getGrantedQos()
  {
    int[] arrayOfInt = new int[0];
    MqttWireMessage localMqttWireMessage = this.response;
    if ((localMqttWireMessage instanceof MqttSuback)) {
      arrayOfInt = ((MqttSuback)localMqttWireMessage).getGrantedQos();
    }
    return arrayOfInt;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public MqttMessage getMessage()
  {
    return this.message;
  }
  
  public int getMessageID()
  {
    return this.messageID;
  }
  
  public MqttWireMessage getResponse()
  {
    return this.response;
  }
  
  public boolean getSessionPresent()
  {
    MqttWireMessage localMqttWireMessage = this.response;
    boolean bool;
    if ((localMqttWireMessage instanceof MqttConnack)) {
      bool = ((MqttConnack)localMqttWireMessage).getSessionPresent();
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String[] getTopics()
  {
    return this.topics;
  }
  
  public Object getUserContext()
  {
    return this.userContext;
  }
  
  public MqttWireMessage getWireMessage()
  {
    return this.response;
  }
  
  public boolean isComplete()
  {
    return this.completed;
  }
  
  protected boolean isCompletePending()
  {
    return this.pendingComplete;
  }
  
  protected boolean isInUse()
  {
    boolean bool;
    if ((getClient() != null) && (!isComplete())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isNotified()
  {
    return this.notified;
  }
  
  protected void markComplete(MqttWireMessage paramMqttWireMessage, MqttException paramMqttException)
  {
    log.fine(CLASS_NAME, "markComplete", "404", new Object[] { getKey(), paramMqttWireMessage, paramMqttException });
    synchronized (this.responseLock)
    {
      if ((paramMqttWireMessage instanceof MqttAck)) {
        this.message = null;
      }
      this.pendingComplete = true;
      this.response = paramMqttWireMessage;
      this.exception = paramMqttException;
      return;
    }
  }
  
  protected void notifyComplete()
  {
    log.fine(CLASS_NAME, "notifyComplete", "404", new Object[] { getKey(), this.response, this.exception });
    synchronized (this.responseLock)
    {
      if ((this.exception == null) && (this.pendingComplete))
      {
        this.completed = true;
        this.pendingComplete = false;
      }
      else
      {
        this.pendingComplete = false;
      }
      this.responseLock.notifyAll();
      synchronized (this.sentLock)
      {
        this.sent = true;
        this.sentLock.notifyAll();
        return;
      }
    }
  }
  
  protected void notifySent()
  {
    log.fine(CLASS_NAME, "notifySent", "403", new Object[] { getKey() });
    synchronized (this.responseLock)
    {
      this.response = null;
      this.completed = false;
      synchronized (this.sentLock)
      {
        this.sent = true;
        this.sentLock.notifyAll();
        return;
      }
    }
  }
  
  public void reset()
  {
    if (!isInUse())
    {
      log.fine(CLASS_NAME, "reset", "410", new Object[] { getKey() });
      this.client = null;
      this.completed = false;
      this.response = null;
      this.sent = false;
      this.exception = null;
      this.userContext = null;
      return;
    }
    throw new MqttException(32201);
  }
  
  public void setActionCallback(IMqttActionListener paramIMqttActionListener)
  {
    this.callback = paramIMqttActionListener;
  }
  
  protected void setClient(IMqttAsyncClient paramIMqttAsyncClient)
  {
    this.client = paramIMqttAsyncClient;
  }
  
  public void setException(MqttException paramMqttException)
  {
    synchronized (this.responseLock)
    {
      this.exception = paramMqttException;
      return;
    }
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public void setMessage(MqttMessage paramMqttMessage)
  {
    this.message = paramMqttMessage;
  }
  
  public void setMessageID(int paramInt)
  {
    this.messageID = paramInt;
  }
  
  public void setNotified(boolean paramBoolean)
  {
    this.notified = paramBoolean;
  }
  
  public void setTopics(String[] paramArrayOfString)
  {
    this.topics = paramArrayOfString;
  }
  
  public void setUserContext(Object paramObject)
  {
    this.userContext = paramObject;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("key=");
    localStringBuffer.append(getKey());
    localStringBuffer.append(" ,topics=");
    if (getTopics() != null) {
      for (int i = 0; i < getTopics().length; i++)
      {
        localStringBuffer.append(getTopics()[i]);
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append(" ,usercontext=");
    localStringBuffer.append(getUserContext());
    localStringBuffer.append(" ,isComplete=");
    localStringBuffer.append(isComplete());
    localStringBuffer.append(" ,isNotified=");
    localStringBuffer.append(isNotified());
    localStringBuffer.append(" ,exception=");
    localStringBuffer.append(getException());
    localStringBuffer.append(" ,actioncallback=");
    localStringBuffer.append(getActionCallback());
    return localStringBuffer.toString();
  }
  
  public void waitForCompletion()
  {
    waitForCompletion(-1L);
  }
  
  public void waitForCompletion(long paramLong)
  {
    log.fine(CLASS_NAME, "waitForCompletion", "407", new Object[] { getKey(), new Long(paramLong), this });
    if ((waitForResponse(paramLong) == null) && (!this.completed))
    {
      log.fine(CLASS_NAME, "waitForCompletion", "406", new Object[] { getKey(), this });
      this.exception = new MqttException(32000);
      throw this.exception;
    }
    checkResult();
  }
  
  protected MqttWireMessage waitForResponse()
  {
    return waitForResponse(-1L);
  }
  
  protected MqttWireMessage waitForResponse(long paramLong)
  {
    synchronized (this.responseLock)
    {
      Logger localLogger = log;
      String str1 = CLASS_NAME;
      String str2 = getKey();
      Object localObject2 = new java/lang/Long;
      ((Long)localObject2).<init>(paramLong);
      Object localObject3 = new java/lang/Boolean;
      ((Boolean)localObject3).<init>(this.sent);
      Boolean localBoolean = new java/lang/Boolean;
      localBoolean.<init>(this.completed);
      Object localObject4;
      if (this.exception == null) {
        localObject4 = "false";
      } else {
        localObject4 = "true";
      }
      MqttWireMessage localMqttWireMessage = this.response;
      MqttException localMqttException = this.exception;
      localLogger.fine(str1, "waitForResponse", "400", new Object[] { str2, localObject2, localObject3, localBoolean, localObject4, localMqttWireMessage, this }, localMqttException);
      while (!this.completed)
      {
        localObject4 = this.exception;
        if (localObject4 == null) {
          try
          {
            localObject2 = log;
            localObject4 = CLASS_NAME;
            str1 = getKey();
            localObject3 = new java/lang/Long;
            ((Long)localObject3).<init>(paramLong);
            ((Logger)localObject2).fine((String)localObject4, "waitForResponse", "408", new Object[] { str1, localObject3 });
            if (paramLong <= 0L) {
              this.responseLock.wait();
            } else {
              this.responseLock.wait(paramLong);
            }
          }
          catch (InterruptedException localInterruptedException)
          {
            localObject3 = new org/eclipse/paho/client/mqttv3/MqttException;
            ((MqttException)localObject3).<init>(localInterruptedException);
            this.exception = ((MqttException)localObject3);
          }
        }
        if (!this.completed) {
          if (this.exception == null)
          {
            if (paramLong > 0L) {
              break;
            }
          }
          else
          {
            log.fine(CLASS_NAME, "waitForResponse", "401", null, this.exception);
            throw this.exception;
          }
        }
      }
      log.fine(CLASS_NAME, "waitForResponse", "402", new Object[] { getKey(), this.response });
      return this.response;
    }
  }
  
  public void waitUntilSent()
  {
    for (;;)
    {
      synchronized (this.sentLock)
      {
        synchronized (this.responseLock)
        {
          if (this.exception == null)
          {
            boolean bool = this.sent;
            if (bool) {}
          }
          try
          {
            log.fine(CLASS_NAME, "waitUntilSent", "409", new Object[] { getKey() });
            this.sentLock.wait();
          }
          catch (InterruptedException localInterruptedException) {}
          if (!this.sent)
          {
            if (this.exception == null) {
              throw ExceptionHelper.createMqttException(6);
            }
            throw this.exception;
          }
          return;
          throw this.exception;
        }
      }
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/Token.class
 *
 * Reversed by:           J
 */