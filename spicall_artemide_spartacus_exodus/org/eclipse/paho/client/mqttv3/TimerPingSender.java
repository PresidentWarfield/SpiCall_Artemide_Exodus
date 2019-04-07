package org.eclipse.paho.client.mqttv3;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TimerPingSender
  implements MqttPingSender
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.TimerPingSender";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private ClientComms comms;
  private Timer timer;
  
  public void init(ClientComms paramClientComms)
  {
    if (paramClientComms != null)
    {
      this.comms = paramClientComms;
      return;
    }
    throw new IllegalArgumentException("ClientComms cannot be null.");
  }
  
  public void schedule(long paramLong)
  {
    this.timer.schedule(new PingTask(null), paramLong);
  }
  
  public void start()
  {
    String str = this.comms.getClient().getClientId();
    log.fine(CLASS_NAME, "start", "659", new Object[] { str });
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MQTT Ping: ");
    localStringBuilder.append(str);
    this.timer = new Timer(localStringBuilder.toString());
    this.timer.schedule(new PingTask(null), this.comms.getKeepAlive());
  }
  
  public void stop()
  {
    log.fine(CLASS_NAME, "stop", "661", null);
    Timer localTimer = this.timer;
    if (localTimer != null) {
      localTimer.cancel();
    }
  }
  
  private class PingTask
    extends TimerTask
  {
    private static final String methodName = "PingTask.run";
    
    private PingTask() {}
    
    public void run()
    {
      TimerPingSender.log.fine(TimerPingSender.CLASS_NAME, "PingTask.run", "660", new Object[] { new Long(System.currentTimeMillis()) });
      TimerPingSender.this.comms.checkForActivity();
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/TimerPingSender.class
 *
 * Reversed by:           J
 */