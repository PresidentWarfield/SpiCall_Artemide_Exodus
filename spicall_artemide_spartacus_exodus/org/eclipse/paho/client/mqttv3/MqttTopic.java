package org.eclipse.paho.client.mqttv3;

import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.util.Strings;

public class MqttTopic
{
  private static final int MAX_TOPIC_LEN = 65535;
  private static final int MIN_TOPIC_LEN = 1;
  public static final String MULTI_LEVEL_WILDCARD = "#";
  public static final String MULTI_LEVEL_WILDCARD_PATTERN = "/#";
  private static final char NUL = '\000';
  public static final String SINGLE_LEVEL_WILDCARD = "+";
  public static final String TOPIC_LEVEL_SEPARATOR = "/";
  public static final String TOPIC_WILDCARDS = "#+";
  private ClientComms comms;
  private String name;
  
  public MqttTopic(String paramString, ClientComms paramClientComms)
  {
    this.comms = paramClientComms;
    this.name = paramString;
  }
  
  private MqttPublish createPublish(MqttMessage paramMqttMessage)
  {
    return new MqttPublish(getName(), paramMqttMessage);
  }
  
  public static boolean isMatched(String paramString1, String paramString2)
  {
    int i = paramString2.length();
    int j = paramString1.length();
    boolean bool = true;
    validate(paramString1, true);
    validate(paramString2, false);
    if (paramString1.equals(paramString2)) {
      return true;
    }
    int k = 0;
    for (int m = 0; (k < j) && (m < i) && ((paramString2.charAt(m) != '/') || (paramString1.charAt(k) == '/')) && ((paramString1.charAt(k) == '+') || (paramString1.charAt(k) == '#') || (paramString1.charAt(k) == paramString2.charAt(m))); m++)
    {
      if (paramString1.charAt(k) == '+')
      {
        int n = m + 1;
        int i1 = m;
        for (;;)
        {
          m = i1;
          if (n >= i) {
            break;
          }
          m = i1;
          if (paramString2.charAt(n) == '/') {
            break;
          }
          i1++;
          n = i1 + 1;
        }
      }
      if (paramString1.charAt(k) == '#') {
        m = i - 1;
      }
      k++;
    }
    if ((m != i) || (k != j)) {
      bool = false;
    }
    return bool;
  }
  
  public static void validate(String paramString, boolean paramBoolean)
  {
    try
    {
      int i = paramString.getBytes("UTF-8").length;
      if ((i >= 1) && (i <= 65535))
      {
        if (paramBoolean)
        {
          if (Strings.equalsAny(paramString, new String[] { "#", "+" })) {
            return;
          }
          if ((Strings.countMatches(paramString, "#") <= 1) && ((!paramString.contains("#")) || (paramString.endsWith("/#"))))
          {
            validateSingleLevelWildcard(paramString);
            return;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid usage of multi-level wildcard in topic string: ");
          localStringBuilder.append(paramString);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
        if (!Strings.containsAny(paramString, "#+")) {
          return;
        }
        throw new IllegalArgumentException("The topic name MUST NOT contain any wildcard characters (#+)");
      }
      throw new IllegalArgumentException(String.format("Invalid topic length, should be in range[%d, %d]!", new Object[] { new Integer(1), new Integer(65535) }));
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new IllegalStateException(paramString.getMessage());
    }
  }
  
  private static void validateSingleLevelWildcard(String paramString)
  {
    int i = "+".charAt(0);
    int j = "/".charAt(0);
    char[] arrayOfChar = paramString.toCharArray();
    int k = arrayOfChar.length;
    int i1;
    for (int m = 0; m < k; m = i1)
    {
      int n = m - 1;
      if (n >= 0) {
        n = arrayOfChar[n];
      } else {
        n = 0;
      }
      i1 = m + 1;
      int i2;
      if (i1 < k) {
        i2 = arrayOfChar[i1];
      } else {
        i2 = 0;
      }
      if ((arrayOfChar[m] == i) && (((n != j) && (n != 0)) || ((i2 != j) && (i2 != 0)))) {
        throw new IllegalArgumentException(String.format("Invalid usage of single-level wildcard in topic string '%s'!", new Object[] { paramString }));
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public MqttDeliveryToken publish(MqttMessage paramMqttMessage)
  {
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(this.comms.getClient().getClientId());
    localMqttDeliveryToken.setMessage(paramMqttMessage);
    this.comms.sendNoWait(createPublish(paramMqttMessage), localMqttDeliveryToken);
    localMqttDeliveryToken.internalTok.waitUntilSent();
    return localMqttDeliveryToken;
  }
  
  public MqttDeliveryToken publish(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    paramArrayOfByte = new MqttMessage(paramArrayOfByte);
    paramArrayOfByte.setQos(paramInt);
    paramArrayOfByte.setRetained(paramBoolean);
    return publish(paramArrayOfByte);
  }
  
  public String toString()
  {
    return getName();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttTopic.class
 *
 * Reversed by:           J
 */