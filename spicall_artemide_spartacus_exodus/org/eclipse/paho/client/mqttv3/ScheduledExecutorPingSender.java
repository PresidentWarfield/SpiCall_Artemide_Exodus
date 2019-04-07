package org.eclipse.paho.client.mqttv3;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ScheduledExecutorPingSender
  implements MqttPingSender
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.ScheduledExecutorPingSender";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private String clientid;
  private ClientComms comms;
  private ScheduledExecutorService executorService;
  private ScheduledFuture scheduledFuture;
  
  public ScheduledExecutorPingSender(ScheduledExecutorService paramScheduledExecutorService)
  {
    if (paramScheduledExecutorService != null)
    {
      this.executorService = paramScheduledExecutorService;
      return;
    }
    throw new IllegalArgumentException("ExecutorService cannot be null.");
  }
  
  public void init(ClientComms paramClientComms)
  {
    if (paramClientComms != null)
    {
      this.comms = paramClientComms;
      this.clientid = paramClientComms.getClient().getClientId();
      return;
    }
    throw new IllegalArgumentException("ClientComms cannot be null.");
  }
  
  public void schedule(long paramLong)
  {
    this.scheduledFuture = this.executorService.schedule(new PingRunnable(null), paramLong, TimeUnit.MILLISECONDS);
  }
  
  public void start()
  {
    log.fine(CLASS_NAME, "start", "659", new Object[] { this.clientid });
    schedule(this.comms.getKeepAlive());
  }
  
  public void stop()
  {
    log.fine(CLASS_NAME, "stop", "661", null);
    ScheduledFuture localScheduledFuture = this.scheduledFuture;
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(true);
    }
  }
  
  private class PingRunnable
    implements Runnable
  {
    private static final String methodName = "PingTask.run";
    
    private PingRunnable() {}
    
    public void run()
    {
      String str = Thread.currentThread().getName();
      Thread localThread = Thread.currentThread();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MQTT Ping: ");
      localStringBuilder.append(ScheduledExecutorPingSender.this.clientid);
      localThread.setName(localStringBuilder.toString());
      ScheduledExecutorPingSender.log.fine(ScheduledExecutorPingSender.CLASS_NAME, "PingTask.run", "660", new Object[] { new Long(System.currentTimeMillis()) });
      ScheduledExecutorPingSender.this.comms.checkForActivity();
      Thread.currentThread().setName(str);
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/ScheduledExecutorPingSender.class
 *
 * Reversed by:           J
 */