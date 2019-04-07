package org.eclipse.paho.client.mqttv3.internal;

import java.io.EOFException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingResp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientState
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientState";
  private static final int MAX_MSG_ID = 65535;
  private static final int MIN_MSG_ID = 1;
  private static final String PERSISTENCE_CONFIRMED_PREFIX = "sc-";
  private static final String PERSISTENCE_RECEIVED_PREFIX = "r-";
  private static final String PERSISTENCE_SENT_BUFFERED_PREFIX = "sb-";
  private static final String PERSISTENCE_SENT_PREFIX = "s-";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private int actualInFlight = 0;
  private CommsCallback callback = null;
  private boolean cleanSession;
  private ClientComms clientComms = null;
  private boolean connected = false;
  private int inFlightPubRels = 0;
  private Hashtable inUseMsgIds;
  private Hashtable inboundQoS2 = null;
  private long keepAlive;
  private long lastInboundActivity = 0L;
  private long lastOutboundActivity = 0L;
  private long lastPing = 0L;
  private int maxInflight = 0;
  private int nextMsgId = 0;
  private Hashtable outboundQoS0 = null;
  private Hashtable outboundQoS1 = null;
  private Hashtable outboundQoS2 = null;
  private volatile Vector pendingFlows;
  private volatile Vector pendingMessages;
  private MqttClientPersistence persistence;
  private MqttWireMessage pingCommand;
  private int pingOutstanding = 0;
  private Object pingOutstandingLock = new Object();
  private MqttPingSender pingSender = null;
  private Object queueLock = new Object();
  private Object quiesceLock = new Object();
  private boolean quiescing = false;
  private CommsTokenStore tokenStore;
  
  protected ClientState(MqttClientPersistence paramMqttClientPersistence, CommsTokenStore paramCommsTokenStore, CommsCallback paramCommsCallback, ClientComms paramClientComms, MqttPingSender paramMqttPingSender)
  {
    log.setResourceName(paramClientComms.getClient().getClientId());
    log.finer(CLASS_NAME, "<Init>", "");
    this.inUseMsgIds = new Hashtable();
    this.pendingFlows = new Vector();
    this.outboundQoS2 = new Hashtable();
    this.outboundQoS1 = new Hashtable();
    this.outboundQoS0 = new Hashtable();
    this.inboundQoS2 = new Hashtable();
    this.pingCommand = new MqttPingReq();
    this.inFlightPubRels = 0;
    this.actualInFlight = 0;
    this.persistence = paramMqttClientPersistence;
    this.callback = paramCommsCallback;
    this.tokenStore = paramCommsTokenStore;
    this.clientComms = paramClientComms;
    this.pingSender = paramMqttPingSender;
    restoreState();
  }
  
  private void decrementInFlight()
  {
    synchronized (this.queueLock)
    {
      this.actualInFlight -= 1;
      Logger localLogger = log;
      String str = CLASS_NAME;
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(this.actualInFlight);
      localLogger.fine(str, "decrementInFlight", "646", new Object[] { localInteger });
      if (!checkQuiesceLock()) {
        this.queueLock.notifyAll();
      }
      return;
    }
  }
  
  private int getNextMessageId()
  {
    try
    {
      int i = this.nextMsgId;
      int j = 0;
      Hashtable localHashtable;
      do
      {
        this.nextMsgId += 1;
        if (this.nextMsgId > 65535) {
          this.nextMsgId = 1;
        }
        int k = j;
        if (this.nextMsgId == i)
        {
          k = j + 1;
          if (k == 2) {
            throw ExceptionHelper.createMqttException(32001);
          }
        }
        localHashtable = this.inUseMsgIds;
        localInteger = new java/lang/Integer;
        localInteger.<init>(this.nextMsgId);
        j = k;
      } while (localHashtable.containsKey(localInteger));
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(this.nextMsgId);
      this.inUseMsgIds.put(localInteger, localInteger);
      j = this.nextMsgId;
      return j;
    }
    finally {}
  }
  
  private String getReceivedPersistenceKey(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("r-");
    localStringBuilder.append(paramInt);
    return localStringBuilder.toString();
  }
  
  private String getReceivedPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("r-");
    localStringBuilder.append(paramMqttWireMessage.getMessageId());
    return localStringBuilder.toString();
  }
  
  private String getSendBufferedPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sb-");
    localStringBuilder.append(paramMqttWireMessage.getMessageId());
    return localStringBuilder.toString();
  }
  
  private String getSendConfirmPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sc-");
    localStringBuilder.append(paramMqttWireMessage.getMessageId());
    return localStringBuilder.toString();
  }
  
  private String getSendPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("s-");
    localStringBuilder.append(paramMqttWireMessage.getMessageId());
    return localStringBuilder.toString();
  }
  
  private void insertInOrder(Vector paramVector, MqttWireMessage paramMqttWireMessage)
  {
    int i = paramMqttWireMessage.getMessageId();
    for (int j = 0; j < paramVector.size(); j++) {
      if (((MqttWireMessage)paramVector.elementAt(j)).getMessageId() > i)
      {
        paramVector.insertElementAt(paramMqttWireMessage, j);
        return;
      }
    }
    paramVector.addElement(paramMqttWireMessage);
  }
  
  private Vector reOrder(Vector paramVector)
  {
    Vector localVector = new Vector();
    if (paramVector.size() == 0) {
      return localVector;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    while (j < paramVector.size())
    {
      int i1 = ((MqttWireMessage)paramVector.elementAt(j)).getMessageId();
      k = i1 - k;
      int i2 = m;
      if (k > m)
      {
        n = j;
        i2 = k;
      }
      j++;
      k = i1;
      m = i2;
    }
    if (65535 - k + ((MqttWireMessage)paramVector.elementAt(0)).getMessageId() > m) {
      n = 0;
    }
    for (m = n;; m++)
    {
      j = i;
      if (m >= paramVector.size()) {
        break;
      }
      localVector.addElement(paramVector.elementAt(m));
    }
    while (j < n)
    {
      localVector.addElement(paramVector.elementAt(j));
      j++;
    }
    return localVector;
  }
  
  private void releaseMessageId(int paramInt)
  {
    try
    {
      Hashtable localHashtable = this.inUseMsgIds;
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(paramInt);
      localHashtable.remove(localInteger);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private void restoreInflightMessages()
  {
    this.pendingMessages = new Vector(this.maxInflight);
    this.pendingFlows = new Vector();
    Object localObject1 = this.outboundQoS2.keys();
    Object localObject3;
    while (((Enumeration)localObject1).hasMoreElements())
    {
      localObject2 = ((Enumeration)localObject1).nextElement();
      localObject3 = (MqttWireMessage)this.outboundQoS2.get(localObject2);
      if ((localObject3 instanceof MqttPublish))
      {
        log.fine(CLASS_NAME, "restoreInflightMessages", "610", new Object[] { localObject2 });
        ((MqttWireMessage)localObject3).setDuplicate(true);
        insertInOrder(this.pendingMessages, (MqttPublish)localObject3);
      }
      else if ((localObject3 instanceof MqttPubRel))
      {
        log.fine(CLASS_NAME, "restoreInflightMessages", "611", new Object[] { localObject2 });
        insertInOrder(this.pendingFlows, (MqttPubRel)localObject3);
      }
    }
    localObject1 = this.outboundQoS1.keys();
    while (((Enumeration)localObject1).hasMoreElements())
    {
      localObject2 = ((Enumeration)localObject1).nextElement();
      localObject3 = (MqttPublish)this.outboundQoS1.get(localObject2);
      ((MqttPublish)localObject3).setDuplicate(true);
      log.fine(CLASS_NAME, "restoreInflightMessages", "612", new Object[] { localObject2 });
      insertInOrder(this.pendingMessages, (MqttWireMessage)localObject3);
    }
    Object localObject2 = this.outboundQoS0.keys();
    while (((Enumeration)localObject2).hasMoreElements())
    {
      localObject1 = ((Enumeration)localObject2).nextElement();
      localObject3 = (MqttPublish)this.outboundQoS0.get(localObject1);
      log.fine(CLASS_NAME, "restoreInflightMessages", "512", new Object[] { localObject1 });
      insertInOrder(this.pendingMessages, (MqttWireMessage)localObject3);
    }
    this.pendingFlows = reOrder(this.pendingFlows);
    this.pendingMessages = reOrder(this.pendingMessages);
  }
  
  private MqttWireMessage restoreMessage(String paramString, MqttPersistable paramMqttPersistable)
  {
    try
    {
      paramMqttPersistable = MqttWireMessage.createWireMessage(paramMqttPersistable);
    }
    catch (MqttException paramMqttPersistable)
    {
      log.fine(CLASS_NAME, "restoreMessage", "602", new Object[] { paramString }, paramMqttPersistable);
      if (!(paramMqttPersistable.getCause() instanceof EOFException)) {
        break label92;
      }
    }
    if (paramString != null) {
      this.persistence.remove(paramString);
    }
    paramMqttPersistable = null;
    log.fine(CLASS_NAME, "restoreMessage", "601", new Object[] { paramString, paramMqttPersistable });
    return paramMqttPersistable;
    label92:
    throw paramMqttPersistable;
  }
  
  public MqttToken checkForActivity(IMqttActionListener paramIMqttActionListener)
  {
    log.fine(CLASS_NAME, "checkForActivity", "616", new Object[0]);
    synchronized (this.quiesceLock)
    {
      boolean bool = this.quiescing;
      ??? = null;
      Object localObject3 = null;
      if (bool) {
        return null;
      }
      getKeepAlive();
      ??? = ???;
      if (this.connected)
      {
        ??? = ???;
        if (this.keepAlive > 0L)
        {
          long l = System.currentTimeMillis();
          synchronized (this.pingOutstandingLock)
          {
            Object localObject4;
            Object localObject5;
            Long localLong1;
            Long localLong2;
            if ((this.pingOutstanding > 0) && (l - this.lastInboundActivity >= this.keepAlive + 100))
            {
              localObject4 = log;
              localObject3 = CLASS_NAME;
              paramIMqttActionListener = new java/lang/Long;
              paramIMqttActionListener.<init>(this.keepAlive);
              localObject5 = new java/lang/Long;
              ((Long)localObject5).<init>(this.lastOutboundActivity);
              localLong1 = new java/lang/Long;
              localLong1.<init>(this.lastInboundActivity);
              ??? = new java/lang/Long;
              ((Long)???).<init>(l);
              localLong2 = new java/lang/Long;
              localLong2.<init>(this.lastPing);
              ((Logger)localObject4).severe((String)localObject3, "checkForActivity", "619", new Object[] { paramIMqttActionListener, localObject5, localLong1, ???, localLong2 });
              throw ExceptionHelper.createMqttException(32000);
            }
            if ((this.pingOutstanding == 0) && (l - this.lastOutboundActivity >= this.keepAlive * 2L))
            {
              paramIMqttActionListener = log;
              localObject4 = CLASS_NAME;
              localObject3 = new java/lang/Long;
              ((Long)localObject3).<init>(this.keepAlive);
              localLong1 = new java/lang/Long;
              localLong1.<init>(this.lastOutboundActivity);
              localLong2 = new java/lang/Long;
              localLong2.<init>(this.lastInboundActivity);
              localObject5 = new java/lang/Long;
              ((Long)localObject5).<init>(l);
              ??? = new java/lang/Long;
              ((Long)???).<init>(this.lastPing);
              paramIMqttActionListener.severe((String)localObject4, "checkForActivity", "642", new Object[] { localObject3, localLong1, localLong2, localObject5, ??? });
              throw ExceptionHelper.createMqttException(32002);
            }
            if (((this.pingOutstanding == 0) && (l - this.lastInboundActivity >= this.keepAlive - 100)) || (l - this.lastOutboundActivity >= this.keepAlive - 100))
            {
              localObject3 = log;
              localObject5 = CLASS_NAME;
              localObject4 = new java/lang/Long;
              ((Long)localObject4).<init>(this.keepAlive);
              ??? = new java/lang/Long;
              ((Long)???).<init>(this.lastOutboundActivity);
              localLong1 = new java/lang/Long;
              localLong1.<init>(this.lastInboundActivity);
              ((Logger)localObject3).fine((String)localObject5, "checkForActivity", "620", new Object[] { localObject4, ???, localLong1 });
              ??? = new org/eclipse/paho/client/mqttv3/MqttToken;
              ((MqttToken)???).<init>(this.clientComms.getClient().getClientId());
              if (paramIMqttActionListener != null) {
                ((MqttToken)???).setActionCallback(paramIMqttActionListener);
              }
              this.tokenStore.saveToken((MqttToken)???, this.pingCommand);
              this.pendingFlows.insertElementAt(this.pingCommand, 0);
              l = getKeepAlive();
              notifyQueueLock();
              paramIMqttActionListener = (IMqttActionListener)???;
            }
            else
            {
              log.fine(CLASS_NAME, "checkForActivity", "634", null);
              l = Math.max(1L, getKeepAlive() - (l - this.lastOutboundActivity));
              paramIMqttActionListener = (IMqttActionListener)localObject3;
            }
            log.fine(CLASS_NAME, "checkForActivity", "624", new Object[] { new Long(l) });
            this.pingSender.schedule(l);
            ??? = paramIMqttActionListener;
          }
        }
      }
      return (MqttToken)???;
    }
  }
  
  protected boolean checkQuiesceLock()
  {
    int i = this.tokenStore.count();
    if ((this.quiescing) && (i == 0) && (this.pendingFlows.size() == 0) && (this.callback.isQuiesced()))
    {
      log.fine(CLASS_NAME, "checkQuiesceLock", "626", new Object[] { new Boolean(this.quiescing), new Integer(this.actualInFlight), new Integer(this.pendingFlows.size()), new Integer(this.inFlightPubRels), Boolean.valueOf(this.callback.isQuiesced()), new Integer(i) });
      synchronized (this.quiesceLock)
      {
        this.quiesceLock.notifyAll();
        return true;
      }
    }
    return false;
  }
  
  protected void clearState()
  {
    log.fine(CLASS_NAME, "clearState", ">");
    this.persistence.clear();
    this.inUseMsgIds.clear();
    this.pendingMessages.clear();
    this.pendingFlows.clear();
    this.outboundQoS2.clear();
    this.outboundQoS1.clear();
    this.outboundQoS0.clear();
    this.inboundQoS2.clear();
    this.tokenStore.clear();
  }
  
  protected void close()
  {
    this.inUseMsgIds.clear();
    if (this.pendingMessages != null) {
      this.pendingMessages.clear();
    }
    this.pendingFlows.clear();
    this.outboundQoS2.clear();
    this.outboundQoS1.clear();
    this.outboundQoS0.clear();
    this.inboundQoS2.clear();
    this.tokenStore.clear();
    this.inUseMsgIds = null;
    this.pendingMessages = null;
    this.pendingFlows = null;
    this.outboundQoS2 = null;
    this.outboundQoS1 = null;
    this.outboundQoS0 = null;
    this.inboundQoS2 = null;
    this.tokenStore = null;
    this.callback = null;
    this.clientComms = null;
    this.persistence = null;
    this.pingCommand = null;
  }
  
  public void connected()
  {
    log.fine(CLASS_NAME, "connected", "631");
    this.connected = true;
    this.pingSender.start();
  }
  
  protected void deliveryComplete(int paramInt)
  {
    log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[] { new Integer(paramInt) });
    this.persistence.remove(getReceivedPersistenceKey(paramInt));
    this.inboundQoS2.remove(new Integer(paramInt));
  }
  
  protected void deliveryComplete(MqttPublish paramMqttPublish)
  {
    log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[] { new Integer(paramMqttPublish.getMessageId()) });
    this.persistence.remove(getReceivedPersistenceKey(paramMqttPublish));
    this.inboundQoS2.remove(new Integer(paramMqttPublish.getMessageId()));
  }
  
  public void disconnected(MqttException paramMqttException)
  {
    log.fine(CLASS_NAME, "disconnected", "633", new Object[] { paramMqttException });
    this.connected = false;
    try
    {
      if (this.cleanSession) {
        clearState();
      }
      this.pendingMessages.clear();
      this.pendingFlows.clear();
      synchronized (this.pingOutstandingLock)
      {
        this.pingOutstanding = 0;
      }
      return;
    }
    catch (MqttException paramMqttException)
    {
      for (;;) {}
    }
  }
  
  /* Error */
  protected MqttWireMessage get()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 99	org/eclipse/paho/client/mqttv3/internal/ClientState:queueLock	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aconst_null
    //   8: astore_2
    //   9: aload_2
    //   10: ifnonnull +363 -> 373
    //   13: aload_0
    //   14: getfield 261	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   17: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   20: ifeq +13 -> 33
    //   23: aload_0
    //   24: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   27: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   30: ifne +30 -> 60
    //   33: aload_0
    //   34: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   37: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   40: ifeq +61 -> 101
    //   43: aload_0
    //   44: getfield 95	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   47: istore_3
    //   48: aload_0
    //   49: getfield 93	org/eclipse/paho/client/mqttv3/internal/ClientState:maxInflight	I
    //   52: istore 4
    //   54: iload_3
    //   55: iload 4
    //   57: if_icmplt +44 -> 101
    //   60: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   63: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   66: ldc_w 453
    //   69: ldc_w 455
    //   72: invokeinterface 414 4 0
    //   77: aload_0
    //   78: getfield 99	org/eclipse/paho/client/mqttv3/internal/ClientState:queueLock	Ljava/lang/Object;
    //   81: invokevirtual 458	java/lang/Object:wait	()V
    //   84: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   87: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   90: ldc_w 453
    //   93: ldc_w 460
    //   96: invokeinterface 414 4 0
    //   101: aload_0
    //   102: getfield 115	org/eclipse/paho/client/mqttv3/internal/ClientState:connected	Z
    //   105: ifne +51 -> 156
    //   108: aload_0
    //   109: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   112: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   115: ifne +20 -> 135
    //   118: aload_0
    //   119: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   122: iconst_0
    //   123: invokevirtual 242	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   126: checkcast 227	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   129: instanceof 462
    //   132: ifne +24 -> 156
    //   135: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   138: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   141: ldc_w 453
    //   144: ldc_w 464
    //   147: invokeinterface 414 4 0
    //   152: aload_1
    //   153: monitorexit
    //   154: aconst_null
    //   155: areturn
    //   156: aload_0
    //   157: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   160: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   163: ifne +88 -> 251
    //   166: aload_0
    //   167: getfield 161	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   170: iconst_0
    //   171: invokevirtual 466	java/util/Vector:remove	(I)Ljava/lang/Object;
    //   174: checkcast 227	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   177: astore_2
    //   178: aload_2
    //   179: instanceof 290
    //   182: ifeq +61 -> 243
    //   185: aload_0
    //   186: aload_0
    //   187: getfield 97	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   190: iconst_1
    //   191: iadd
    //   192: putfield 97	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   195: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   198: astore 5
    //   200: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   203: astore 6
    //   205: new 176	java/lang/Integer
    //   208: astore 7
    //   210: aload 7
    //   212: aload_0
    //   213: getfield 97	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   216: invokespecial 179	java/lang/Integer:<init>	(I)V
    //   219: aload 5
    //   221: aload 6
    //   223: ldc_w 453
    //   226: ldc_w 468
    //   229: iconst_1
    //   230: anewarray 4	java/lang/Object
    //   233: dup
    //   234: iconst_0
    //   235: aload 7
    //   237: aastore
    //   238: invokeinterface 186 5 0
    //   243: aload_0
    //   244: invokevirtual 190	org/eclipse/paho/client/mqttv3/internal/ClientState:checkQuiesceLock	()Z
    //   247: pop
    //   248: goto -239 -> 9
    //   251: aload_0
    //   252: getfield 261	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   255: invokevirtual 452	java/util/Vector:isEmpty	()Z
    //   258: ifne -249 -> 9
    //   261: aload_0
    //   262: getfield 95	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   265: aload_0
    //   266: getfield 93	org/eclipse/paho/client/mqttv3/internal/ClientState:maxInflight	I
    //   269: if_icmpge +84 -> 353
    //   272: aload_0
    //   273: getfield 261	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   276: iconst_0
    //   277: invokevirtual 242	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   280: checkcast 227	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   283: astore_2
    //   284: aload_0
    //   285: getfield 261	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   288: iconst_0
    //   289: invokevirtual 471	java/util/Vector:removeElementAt	(I)V
    //   292: aload_0
    //   293: aload_0
    //   294: getfield 95	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   297: iconst_1
    //   298: iadd
    //   299: putfield 95	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   302: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   305: astore 6
    //   307: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   310: astore 7
    //   312: new 176	java/lang/Integer
    //   315: astore 5
    //   317: aload 5
    //   319: aload_0
    //   320: getfield 95	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   323: invokespecial 179	java/lang/Integer:<init>	(I)V
    //   326: aload 6
    //   328: aload 7
    //   330: ldc_w 453
    //   333: ldc_w 473
    //   336: iconst_1
    //   337: anewarray 4	java/lang/Object
    //   340: dup
    //   341: iconst_0
    //   342: aload 5
    //   344: aastore
    //   345: invokeinterface 186 5 0
    //   350: goto -341 -> 9
    //   353: getstatic 80	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   356: getstatic 72	org/eclipse/paho/client/mqttv3/internal/ClientState:CLASS_NAME	Ljava/lang/String;
    //   359: ldc_w 453
    //   362: ldc_w 475
    //   365: invokeinterface 414 4 0
    //   370: goto -361 -> 9
    //   373: aload_1
    //   374: monitorexit
    //   375: aload_2
    //   376: areturn
    //   377: astore_2
    //   378: aload_1
    //   379: monitorexit
    //   380: aload_2
    //   381: athrow
    //   382: astore 6
    //   384: goto -283 -> 101
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	387	0	this	ClientState
    //   4	375	1	localObject1	Object
    //   8	368	2	localMqttWireMessage	MqttWireMessage
    //   377	4	2	localObject2	Object
    //   47	11	3	i	int
    //   52	6	4	j	int
    //   198	145	5	localObject3	Object
    //   203	124	6	localObject4	Object
    //   382	1	6	localInterruptedException	InterruptedException
    //   208	121	7	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   13	33	377	finally
    //   33	54	377	finally
    //   60	101	377	finally
    //   101	135	377	finally
    //   135	154	377	finally
    //   156	243	377	finally
    //   243	248	377	finally
    //   251	350	377	finally
    //   353	370	377	finally
    //   373	375	377	finally
    //   378	380	377	finally
    //   60	101	382	java/lang/InterruptedException
  }
  
  public int getActualInFlight()
  {
    return this.actualInFlight;
  }
  
  protected boolean getCleanSession()
  {
    return this.cleanSession;
  }
  
  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("In use msgids", this.inUseMsgIds);
    localProperties.put("pendingMessages", this.pendingMessages);
    localProperties.put("pendingFlows", this.pendingFlows);
    localProperties.put("maxInflight", new Integer(this.maxInflight));
    localProperties.put("nextMsgID", new Integer(this.nextMsgId));
    localProperties.put("actualInFlight", new Integer(this.actualInFlight));
    localProperties.put("inFlightPubRels", new Integer(this.inFlightPubRels));
    localProperties.put("quiescing", Boolean.valueOf(this.quiescing));
    localProperties.put("pingoutstanding", new Integer(this.pingOutstanding));
    localProperties.put("lastOutboundActivity", new Long(this.lastOutboundActivity));
    localProperties.put("lastInboundActivity", new Long(this.lastInboundActivity));
    localProperties.put("outboundQoS2", this.outboundQoS2);
    localProperties.put("outboundQoS1", this.outboundQoS1);
    localProperties.put("outboundQoS0", this.outboundQoS0);
    localProperties.put("inboundQoS2", this.inboundQoS2);
    localProperties.put("tokens", this.tokenStore);
    return localProperties;
  }
  
  protected long getKeepAlive()
  {
    return this.keepAlive;
  }
  
  public int getMaxInFlight()
  {
    return this.maxInflight;
  }
  
  protected void notifyComplete(MqttToken paramMqttToken)
  {
    MqttWireMessage localMqttWireMessage = paramMqttToken.internalTok.getWireMessage();
    if ((localMqttWireMessage != null) && ((localMqttWireMessage instanceof MqttAck)))
    {
      log.fine(CLASS_NAME, "notifyComplete", "629", new Object[] { new Integer(localMqttWireMessage.getMessageId()), paramMqttToken, localMqttWireMessage });
      paramMqttToken = (MqttAck)localMqttWireMessage;
      if ((paramMqttToken instanceof MqttPubAck))
      {
        this.persistence.remove(getSendPersistenceKey(localMqttWireMessage));
        this.persistence.remove(getSendBufferedPersistenceKey(localMqttWireMessage));
        this.outboundQoS1.remove(new Integer(paramMqttToken.getMessageId()));
        decrementInFlight();
        releaseMessageId(localMqttWireMessage.getMessageId());
        this.tokenStore.removeToken(localMqttWireMessage);
        log.fine(CLASS_NAME, "notifyComplete", "650", new Object[] { new Integer(paramMqttToken.getMessageId()) });
      }
      else if ((paramMqttToken instanceof MqttPubComp))
      {
        this.persistence.remove(getSendPersistenceKey(localMqttWireMessage));
        this.persistence.remove(getSendConfirmPersistenceKey(localMqttWireMessage));
        this.persistence.remove(getSendBufferedPersistenceKey(localMqttWireMessage));
        this.outboundQoS2.remove(new Integer(paramMqttToken.getMessageId()));
        this.inFlightPubRels -= 1;
        decrementInFlight();
        releaseMessageId(localMqttWireMessage.getMessageId());
        this.tokenStore.removeToken(localMqttWireMessage);
        log.fine(CLASS_NAME, "notifyComplete", "645", new Object[] { new Integer(paramMqttToken.getMessageId()), new Integer(this.inFlightPubRels) });
      }
      checkQuiesceLock();
    }
  }
  
  public void notifyQueueLock()
  {
    synchronized (this.queueLock)
    {
      log.fine(CLASS_NAME, "notifyQueueLock", "638");
      this.queueLock.notifyAll();
      return;
    }
  }
  
  protected void notifyReceivedAck(MqttAck arg1)
  {
    this.lastInboundActivity = System.currentTimeMillis();
    log.fine(CLASS_NAME, "notifyReceivedAck", "627", new Object[] { new Integer(???.getMessageId()), ??? });
    MqttToken localMqttToken1 = this.tokenStore.getToken(???);
    if (localMqttToken1 == null)
    {
      log.fine(CLASS_NAME, "notifyReceivedAck", "662", new Object[] { new Integer(???.getMessageId()) });
    }
    else if ((??? instanceof MqttPubRec))
    {
      send(new MqttPubRel((MqttPubRec)???), localMqttToken1);
    }
    else if ((!(??? instanceof MqttPubAck)) && (!(??? instanceof MqttPubComp)))
    {
      if ((??? instanceof MqttPingResp)) {
        synchronized (this.pingOutstandingLock)
        {
          this.pingOutstanding = Math.max(0, this.pingOutstanding - 1);
          notifyResult(???, localMqttToken1, null);
          if (this.pingOutstanding == 0) {
            this.tokenStore.removeToken(???);
          }
          log.fine(CLASS_NAME, "notifyReceivedAck", "636", new Object[] { new Integer(this.pingOutstanding) });
        }
      }
      if ((??? instanceof MqttConnack))
      {
        MqttConnack localMqttConnack = (MqttConnack)???;
        int i = localMqttConnack.getReturnCode();
        if (i == 0) {
          synchronized (this.queueLock)
          {
            if (this.cleanSession)
            {
              clearState();
              this.tokenStore.saveToken(localMqttToken1, ???);
            }
            this.inFlightPubRels = 0;
            this.actualInFlight = 0;
            restoreInflightMessages();
            connected();
            this.clientComms.connectComplete(localMqttConnack, null);
            notifyResult(???, localMqttToken1, null);
            this.tokenStore.removeToken(???);
            synchronized (this.queueLock)
            {
              this.queueLock.notifyAll();
            }
          }
        }
        throw ExceptionHelper.createMqttException(i);
      }
      notifyResult(???, localMqttToken2, null);
      releaseMessageId(???.getMessageId());
      this.tokenStore.removeToken(???);
    }
    else
    {
      notifyResult(???, localMqttToken2, null);
    }
    checkQuiesceLock();
  }
  
  public void notifyReceivedBytes(int paramInt)
  {
    if (paramInt > 0) {
      this.lastInboundActivity = System.currentTimeMillis();
    }
    log.fine(CLASS_NAME, "notifyReceivedBytes", "630", new Object[] { new Integer(paramInt) });
  }
  
  protected void notifyReceivedMsg(MqttWireMessage paramMqttWireMessage)
  {
    this.lastInboundActivity = System.currentTimeMillis();
    log.fine(CLASS_NAME, "notifyReceivedMsg", "651", new Object[] { new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage });
    if (!this.quiescing)
    {
      MqttPublish localMqttPublish;
      if ((paramMqttWireMessage instanceof MqttPublish))
      {
        localMqttPublish = (MqttPublish)paramMqttWireMessage;
        switch (localMqttPublish.getMessage().getQos())
        {
        default: 
          break;
        case 2: 
          this.persistence.put(getReceivedPersistenceKey(paramMqttWireMessage), localMqttPublish);
          this.inboundQoS2.put(new Integer(localMqttPublish.getMessageId()), localMqttPublish);
          send(new MqttPubRec(localMqttPublish), null);
          break;
        case 0: 
        case 1: 
          paramMqttWireMessage = this.callback;
          if (paramMqttWireMessage == null) {
            break;
          }
          paramMqttWireMessage.messageArrived(localMqttPublish);
          break;
        }
      }
      else if ((paramMqttWireMessage instanceof MqttPubRel))
      {
        localMqttPublish = (MqttPublish)this.inboundQoS2.get(new Integer(paramMqttWireMessage.getMessageId()));
        if (localMqttPublish != null)
        {
          paramMqttWireMessage = this.callback;
          if (paramMqttWireMessage != null) {
            paramMqttWireMessage.messageArrived(localMqttPublish);
          }
        }
        else
        {
          send(new MqttPubComp(paramMqttWireMessage.getMessageId()), null);
        }
      }
    }
  }
  
  protected void notifyResult(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken, MqttException paramMqttException)
  {
    paramMqttToken.internalTok.markComplete(paramMqttWireMessage, paramMqttException);
    paramMqttToken.internalTok.notifyComplete();
    if ((paramMqttWireMessage != null) && ((paramMqttWireMessage instanceof MqttAck)) && (!(paramMqttWireMessage instanceof MqttPubRec)))
    {
      log.fine(CLASS_NAME, "notifyResult", "648", new Object[] { paramMqttToken.internalTok.getKey(), paramMqttWireMessage, paramMqttException });
      this.callback.asyncOperationComplete(paramMqttToken);
    }
    if (paramMqttWireMessage == null)
    {
      log.fine(CLASS_NAME, "notifyResult", "649", new Object[] { paramMqttToken.internalTok.getKey(), paramMqttException });
      this.callback.asyncOperationComplete(paramMqttToken);
    }
  }
  
  protected void notifySent(MqttWireMessage arg1)
  {
    this.lastOutboundActivity = System.currentTimeMillis();
    log.fine(CLASS_NAME, "notifySent", "625", new Object[] { ???.getKey() });
    ??? = this.tokenStore.getToken(???);
    ((MqttToken)???).internalTok.notifySent();
    if ((??? instanceof MqttPingReq)) {
      synchronized (this.pingOutstandingLock)
      {
        long l = System.currentTimeMillis();
        synchronized (this.pingOutstandingLock)
        {
          this.lastPing = l;
          this.pingOutstanding += 1;
          Logger localLogger = log;
          ??? = CLASS_NAME;
          Integer localInteger = new java/lang/Integer;
          localInteger.<init>(this.pingOutstanding);
          localLogger.fine((String)???, "notifySent", "635", new Object[] { localInteger });
        }
      }
    }
    if (((??? instanceof MqttPublish)) && (((MqttPublish)???).getMessage().getQos() == 0))
    {
      localMqttToken.internalTok.markComplete(null, null);
      this.callback.asyncOperationComplete(localMqttToken);
      decrementInFlight();
      releaseMessageId(???.getMessageId());
      this.tokenStore.removeToken(???);
      checkQuiesceLock();
    }
  }
  
  public void notifySentBytes(int paramInt)
  {
    if (paramInt > 0) {
      this.lastOutboundActivity = System.currentTimeMillis();
    }
    log.fine(CLASS_NAME, "notifySentBytes", "643", new Object[] { new Integer(paramInt) });
  }
  
  public void persistBufferedMessage(MqttWireMessage paramMqttWireMessage)
  {
    String str1 = getSendBufferedPersistenceKey(paramMqttWireMessage);
    String str2 = str1;
    try
    {
      paramMqttWireMessage.setMessageId(getNextMessageId());
      str2 = str1;
      str1 = getSendBufferedPersistenceKey(paramMqttWireMessage);
      str2 = str1;
      try
      {
        this.persistence.put(str1, (MqttPublish)paramMqttWireMessage);
      }
      catch (MqttPersistenceException localMqttPersistenceException)
      {
        str3 = str1;
        log.fine(CLASS_NAME, "persistBufferedMessage", "515");
        str3 = str1;
        this.persistence.open(this.clientComms.getClient().getClientId(), this.clientComms.getClient().getServerURI());
        str3 = str1;
        this.persistence.put(str1, (MqttPublish)paramMqttWireMessage);
      }
      str3 = str1;
      log.fine(CLASS_NAME, "persistBufferedMessage", "513", new Object[] { str1 });
    }
    catch (MqttException paramMqttWireMessage)
    {
      String str3;
      log.warning(CLASS_NAME, "persistBufferedMessage", "513", new Object[] { str3 });
    }
  }
  
  public void quiesce(long paramLong)
  {
    if (paramLong > 0L)
    {
      log.fine(CLASS_NAME, "quiesce", "637", new Object[] { new Long(paramLong) });
      synchronized (this.queueLock)
      {
        this.quiescing = true;
        this.callback.quiesce();
        notifyQueueLock();
        for (;;)
        {
          try
          {
            synchronized (this.quiesceLock)
            {
              int i = this.tokenStore.count();
              if ((i > 0) || (this.pendingFlows.size() > 0) || (!this.callback.isQuiesced()))
              {
                Logger localLogger = log;
                String str = CLASS_NAME;
                Integer localInteger1 = new java/lang/Integer;
                localInteger1.<init>(this.actualInFlight);
                Integer localInteger2 = new java/lang/Integer;
                localInteger2.<init>(this.pendingFlows.size());
                Integer localInteger3 = new java/lang/Integer;
                localInteger3.<init>(this.inFlightPubRels);
                Integer localInteger4 = new java/lang/Integer;
                localInteger4.<init>(i);
                localLogger.fine(str, "quiesce", "639", new Object[] { localInteger1, localInteger2, localInteger3, localInteger4 });
                this.quiesceLock.wait(paramLong);
              }
            }
          }
          catch (InterruptedException localInterruptedException)
          {
            continue;
          }
          synchronized (this.queueLock)
          {
            this.pendingMessages.clear();
            this.pendingFlows.clear();
            this.quiescing = false;
            this.actualInFlight = 0;
            log.fine(CLASS_NAME, "quiesce", "640");
          }
        }
        throw ((Throwable)???);
      }
    }
  }
  
  public Vector resolveOldTokens(MqttException arg1)
  {
    log.fine(CLASS_NAME, "resolveOldTokens", "632", new Object[] { ??? });
    MqttException localMqttException = ???;
    if (??? == null) {
      localMqttException = new MqttException(32102);
    }
    Vector localVector = this.tokenStore.getOutstandingTokens();
    Enumeration localEnumeration = localVector.elements();
    while (localEnumeration.hasMoreElements()) {
      synchronized ((MqttToken)localEnumeration.nextElement())
      {
        if ((!???.isComplete()) && (!???.internalTok.isCompletePending()) && (???.getException() == null)) {
          ???.internalTok.setException(localMqttException);
        }
        if (!(??? instanceof MqttDeliveryToken)) {
          this.tokenStore.removeToken(???.internalTok.getKey());
        }
      }
    }
    return localVector;
  }
  
  protected void restoreState()
  {
    Enumeration localEnumeration = this.persistence.keys();
    int i = this.nextMsgId;
    Object localObject1 = new Vector();
    log.fine(CLASS_NAME, "restoreState", "600");
    while (localEnumeration.hasMoreElements())
    {
      localObject2 = (String)localEnumeration.nextElement();
      Object localObject3 = restoreMessage((String)localObject2, this.persistence.get((String)localObject2));
      if (localObject3 != null) {
        if (((String)localObject2).startsWith("r-"))
        {
          log.fine(CLASS_NAME, "restoreState", "604", new Object[] { localObject2, localObject3 });
          this.inboundQoS2.put(new Integer(((MqttWireMessage)localObject3).getMessageId()), localObject3);
        }
        else
        {
          Object localObject4;
          if (((String)localObject2).startsWith("s-"))
          {
            MqttPublish localMqttPublish = (MqttPublish)localObject3;
            i = Math.max(localMqttPublish.getMessageId(), i);
            if (this.persistence.containsKey(getSendConfirmPersistenceKey(localMqttPublish)))
            {
              localObject4 = (MqttPubRel)restoreMessage((String)localObject2, this.persistence.get(getSendConfirmPersistenceKey(localMqttPublish)));
              if (localObject4 != null)
              {
                log.fine(CLASS_NAME, "restoreState", "605", new Object[] { localObject2, localObject3 });
                this.outboundQoS2.put(new Integer(((MqttPubRel)localObject4).getMessageId()), localObject4);
              }
              else
              {
                log.fine(CLASS_NAME, "restoreState", "606", new Object[] { localObject2, localObject3 });
              }
            }
            else
            {
              localMqttPublish.setDuplicate(true);
              if (localMqttPublish.getMessage().getQos() == 2)
              {
                log.fine(CLASS_NAME, "restoreState", "607", new Object[] { localObject2, localObject3 });
                this.outboundQoS2.put(new Integer(localMqttPublish.getMessageId()), localMqttPublish);
              }
              else
              {
                log.fine(CLASS_NAME, "restoreState", "608", new Object[] { localObject2, localObject3 });
                this.outboundQoS1.put(new Integer(localMqttPublish.getMessageId()), localMqttPublish);
              }
            }
            this.tokenStore.restoreToken(localMqttPublish).internalTok.setClient(this.clientComms.getClient());
            this.inUseMsgIds.put(new Integer(localMqttPublish.getMessageId()), new Integer(localMqttPublish.getMessageId()));
          }
          else if (((String)localObject2).startsWith("sb-"))
          {
            localObject4 = (MqttPublish)localObject3;
            i = Math.max(((MqttPublish)localObject4).getMessageId(), i);
            if (((MqttPublish)localObject4).getMessage().getQos() == 2)
            {
              log.fine(CLASS_NAME, "restoreState", "607", new Object[] { localObject2, localObject3 });
              this.outboundQoS2.put(new Integer(((MqttPublish)localObject4).getMessageId()), localObject4);
            }
            else if (((MqttPublish)localObject4).getMessage().getQos() == 1)
            {
              log.fine(CLASS_NAME, "restoreState", "608", new Object[] { localObject2, localObject3 });
              this.outboundQoS1.put(new Integer(((MqttPublish)localObject4).getMessageId()), localObject4);
            }
            else
            {
              log.fine(CLASS_NAME, "restoreState", "511", new Object[] { localObject2, localObject3 });
              this.outboundQoS0.put(new Integer(((MqttPublish)localObject4).getMessageId()), localObject4);
              this.persistence.remove((String)localObject2);
            }
            this.tokenStore.restoreToken((MqttPublish)localObject4).internalTok.setClient(this.clientComms.getClient());
            this.inUseMsgIds.put(new Integer(((MqttPublish)localObject4).getMessageId()), new Integer(((MqttPublish)localObject4).getMessageId()));
          }
          else if (((String)localObject2).startsWith("sc-"))
          {
            localObject3 = (MqttPubRel)localObject3;
            if (!this.persistence.containsKey(getSendPersistenceKey((MqttWireMessage)localObject3))) {
              ((Vector)localObject1).addElement(localObject2);
            }
          }
        }
      }
    }
    Object localObject2 = ((Vector)localObject1).elements();
    while (((Enumeration)localObject2).hasMoreElements())
    {
      localObject1 = (String)((Enumeration)localObject2).nextElement();
      log.fine(CLASS_NAME, "restoreState", "609", new Object[] { localObject1 });
      this.persistence.remove((String)localObject1);
    }
    this.nextMsgId = i;
  }
  
  public void send(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
  {
    if ((paramMqttWireMessage.isMessageIdRequired()) && (paramMqttWireMessage.getMessageId() == 0)) {
      if (((paramMqttWireMessage instanceof MqttPublish)) && (((MqttPublish)paramMqttWireMessage).getMessage().getQos() != 0)) {
        paramMqttWireMessage.setMessageId(getNextMessageId());
      } else if (((paramMqttWireMessage instanceof MqttPubAck)) || ((paramMqttWireMessage instanceof MqttPubRec)) || ((paramMqttWireMessage instanceof MqttPubRel)) || ((paramMqttWireMessage instanceof MqttPubComp)) || ((paramMqttWireMessage instanceof MqttSubscribe)) || ((paramMqttWireMessage instanceof MqttSuback)) || ((paramMqttWireMessage instanceof MqttUnsubscribe)) || ((paramMqttWireMessage instanceof MqttUnsubAck))) {
        paramMqttWireMessage.setMessageId(getNextMessageId());
      }
    }
    if (paramMqttToken != null) {
      try
      {
        paramMqttToken.internalTok.setMessageID(paramMqttWireMessage.getMessageId());
      }
      catch (Exception localException) {}
    }
    if ((paramMqttWireMessage instanceof MqttPublish)) {
      synchronized (this.queueLock)
      {
        if (this.actualInFlight < this.maxInflight)
        {
          MqttMessage localMqttMessage = ((MqttPublish)paramMqttWireMessage).getMessage();
          Object localObject2 = log;
          String str = CLASS_NAME;
          Integer localInteger = new java/lang/Integer;
          localInteger.<init>(paramMqttWireMessage.getMessageId());
          localObject3 = new java/lang/Integer;
          ((Integer)localObject3).<init>(localMqttMessage.getQos());
          ((Logger)localObject2).fine(str, "send", "628", new Object[] { localInteger, localObject3, paramMqttWireMessage });
          switch (localMqttMessage.getQos())
          {
          default: 
            break;
          case 2: 
            localObject3 = this.outboundQoS2;
            localObject2 = new java/lang/Integer;
            ((Integer)localObject2).<init>(paramMqttWireMessage.getMessageId());
            ((Hashtable)localObject3).put(localObject2, paramMqttWireMessage);
            this.persistence.put(getSendPersistenceKey(paramMqttWireMessage), (MqttPublish)paramMqttWireMessage);
            break;
          case 1: 
            localObject3 = this.outboundQoS1;
            localObject2 = new java/lang/Integer;
            ((Integer)localObject2).<init>(paramMqttWireMessage.getMessageId());
            ((Hashtable)localObject3).put(localObject2, paramMqttWireMessage);
            this.persistence.put(getSendPersistenceKey(paramMqttWireMessage), (MqttPublish)paramMqttWireMessage);
          }
          this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
          this.pendingMessages.addElement(paramMqttWireMessage);
          this.queueLock.notifyAll();
          break label662;
        }
        Object localObject3 = log;
        paramMqttWireMessage = CLASS_NAME;
        paramMqttToken = new java/lang/Integer;
        paramMqttToken.<init>(this.actualInFlight);
        ((Logger)localObject3).fine(paramMqttWireMessage, "send", "613", new Object[] { paramMqttToken });
        paramMqttWireMessage = new org/eclipse/paho/client/mqttv3/MqttException;
        paramMqttWireMessage.<init>(32202);
        throw paramMqttWireMessage;
      }
    }
    log.fine(CLASS_NAME, "send", "615", new Object[] { new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage });
    if ((paramMqttWireMessage instanceof MqttConnect)) {
      synchronized (this.queueLock)
      {
        this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
        this.pendingFlows.insertElementAt(paramMqttWireMessage, 0);
        this.queueLock.notifyAll();
      }
    }
    if ((paramMqttWireMessage instanceof MqttPingReq))
    {
      this.pingCommand = paramMqttWireMessage;
    }
    else if ((paramMqttWireMessage instanceof MqttPubRel))
    {
      this.outboundQoS2.put(new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage);
      this.persistence.put(getSendConfirmPersistenceKey(paramMqttWireMessage), (MqttPubRel)paramMqttWireMessage);
    }
    else if ((paramMqttWireMessage instanceof MqttPubComp))
    {
      this.persistence.remove(getReceivedPersistenceKey(paramMqttWireMessage));
    }
    synchronized (this.queueLock)
    {
      if (!(paramMqttWireMessage instanceof MqttAck)) {
        this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
      }
      this.pendingFlows.addElement(paramMqttWireMessage);
      this.queueLock.notifyAll();
      label662:
      return;
    }
  }
  
  protected void setCleanSession(boolean paramBoolean)
  {
    this.cleanSession = paramBoolean;
  }
  
  public void setKeepAliveInterval(long paramLong)
  {
    this.keepAlive = paramLong;
  }
  
  protected void setKeepAliveSecs(long paramLong)
  {
    this.keepAlive = (paramLong * 1000L);
  }
  
  protected void setMaxInflight(int paramInt)
  {
    this.maxInflight = paramInt;
    this.pendingMessages = new Vector(this.maxInflight);
  }
  
  public void unPersistBufferedMessage(MqttWireMessage paramMqttWireMessage)
  {
    try
    {
      log.fine(CLASS_NAME, "unPersistBufferedMessage", "517", new Object[] { paramMqttWireMessage.getKey() });
      this.persistence.remove(getSendBufferedPersistenceKey(paramMqttWireMessage));
    }
    catch (MqttPersistenceException localMqttPersistenceException)
    {
      log.fine(CLASS_NAME, "unPersistBufferedMessage", "518", new Object[] { paramMqttWireMessage.getKey() });
    }
  }
  
  protected void undo(MqttPublish paramMqttPublish)
  {
    synchronized (this.queueLock)
    {
      Object localObject2 = log;
      String str = CLASS_NAME;
      Integer localInteger1 = new java/lang/Integer;
      localInteger1.<init>(paramMqttPublish.getMessageId());
      Integer localInteger2 = new java/lang/Integer;
      localInteger2.<init>(paramMqttPublish.getMessage().getQos());
      ((Logger)localObject2).fine(str, "undo", "618", new Object[] { localInteger1, localInteger2 });
      if (paramMqttPublish.getMessage().getQos() == 1)
      {
        localObject2 = this.outboundQoS1;
        localInteger2 = new java/lang/Integer;
        localInteger2.<init>(paramMqttPublish.getMessageId());
        ((Hashtable)localObject2).remove(localInteger2);
      }
      else
      {
        localObject2 = this.outboundQoS2;
        localInteger2 = new java/lang/Integer;
        localInteger2.<init>(paramMqttPublish.getMessageId());
        ((Hashtable)localObject2).remove(localInteger2);
      }
      this.pendingMessages.removeElement(paramMqttPublish);
      this.persistence.remove(getSendPersistenceKey(paramMqttPublish));
      this.tokenStore.removeToken(paramMqttPublish);
      if (paramMqttPublish.getMessage().getQos() > 0)
      {
        releaseMessageId(paramMqttPublish.getMessageId());
        paramMqttPublish.setMessageId(0);
      }
      checkQuiesceLock();
      return;
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/ClientState.class
 *
 * Reversed by:           J
 */