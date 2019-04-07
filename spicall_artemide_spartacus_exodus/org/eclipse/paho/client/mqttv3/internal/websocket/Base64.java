package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;

public class Base64
{
  private static final Base64Encoder encoder;
  private static final Base64 instance = new Base64();
  
  static
  {
    Base64 localBase64 = instance;
    localBase64.getClass();
    encoder = new Base64Encoder();
  }
  
  public static String encode(String paramString)
  {
    encoder.putByteArray("akey", paramString.getBytes());
    return encoder.getBase64String();
  }
  
  public static String encodeBytes(byte[] paramArrayOfByte)
  {
    encoder.putByteArray("aKey", paramArrayOfByte);
    return encoder.getBase64String();
  }
  
  public class Base64Encoder
    extends AbstractPreferences
  {
    private String base64String = null;
    
    public Base64Encoder()
    {
      super("");
    }
    
    protected AbstractPreferences childSpi(String paramString)
    {
      return null;
    }
    
    protected String[] childrenNamesSpi()
    {
      return null;
    }
    
    protected void flushSpi() {}
    
    public String getBase64String()
    {
      return this.base64String;
    }
    
    protected String getSpi(String paramString)
    {
      return null;
    }
    
    protected String[] keysSpi()
    {
      return null;
    }
    
    protected void putSpi(String paramString1, String paramString2)
    {
      this.base64String = paramString2;
    }
    
    protected void removeNodeSpi() {}
    
    protected void removeSpi(String paramString) {}
    
    protected void syncSpi() {}
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/websocket/Base64.class
 *
 * Reversed by:           J
 */