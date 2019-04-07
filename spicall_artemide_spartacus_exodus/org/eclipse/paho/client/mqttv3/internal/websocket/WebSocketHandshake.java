package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketHandshake
{
  private static final String ACCEPT_SALT = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
  private static final String EMPTY = "";
  private static final String HTTP_HEADER_CONNECTION = "connection";
  private static final String HTTP_HEADER_CONNECTION_VALUE = "upgrade";
  private static final String HTTP_HEADER_SEC_WEBSOCKET_ACCEPT = "sec-websocket-accept";
  private static final String HTTP_HEADER_SEC_WEBSOCKET_PROTOCOL = "sec-websocket-protocol";
  private static final String HTTP_HEADER_UPGRADE = "upgrade";
  private static final String HTTP_HEADER_UPGRADE_WEBSOCKET = "websocket";
  private static final String LINE_SEPARATOR = "\r\n";
  private static final String SHA1_PROTOCOL = "SHA1";
  String host;
  InputStream input;
  OutputStream output;
  int port;
  String uri;
  
  public WebSocketHandshake(InputStream paramInputStream, OutputStream paramOutputStream, String paramString1, String paramString2, int paramInt)
  {
    this.input = paramInputStream;
    this.output = paramOutputStream;
    this.uri = paramString1;
    this.host = paramString2;
    this.port = paramInt;
  }
  
  private Map getHeaders(ArrayList paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 1; i < paramArrayList.size(); i++)
    {
      String[] arrayOfString = ((String)paramArrayList.get(i)).split(":");
      localHashMap.put(arrayOfString[0].toLowerCase(), arrayOfString[1]);
    }
    return localHashMap;
  }
  
  private void receiveHandshakeResponse(String paramString)
  {
    Object localObject1 = new BufferedReader(new InputStreamReader(this.input));
    ArrayList localArrayList = new ArrayList();
    Object localObject2 = ((BufferedReader)localObject1).readLine();
    if (localObject2 != null)
    {
      while (!((String)localObject2).equals(""))
      {
        localArrayList.add(localObject2);
        localObject2 = ((BufferedReader)localObject1).readLine();
      }
      localObject2 = getHeaders(localArrayList);
      localObject1 = (String)((Map)localObject2).get("connection");
      if ((localObject1 != null) && (!((String)localObject1).equalsIgnoreCase("upgrade")))
      {
        localObject1 = (String)((Map)localObject2).get("upgrade");
        if ((localObject1 != null) && (((String)localObject1).toLowerCase().contains("websocket")))
        {
          if ((String)((Map)localObject2).get("sec-websocket-protocol") != null)
          {
            if (((Map)localObject2).containsKey("sec-websocket-accept")) {
              try
              {
                verifyWebSocketKey(paramString, (String)((Map)localObject2).get("sec-websocket-accept"));
                return;
              }
              catch (HandshakeFailedException paramString)
              {
                throw new IOException("WebSocket Response header: Incorrect Sec-WebSocket-Key");
              }
              catch (NoSuchAlgorithmException paramString)
              {
                throw new IOException(paramString.getMessage());
              }
            }
            throw new IOException("WebSocket Response header: Missing Sec-WebSocket-Accept");
          }
          throw new IOException("WebSocket Response header: empty sec-websocket-protocol");
        }
        throw new IOException("WebSocket Response header: Incorrect upgrade.");
      }
      throw new IOException("WebSocket Response header: Incorrect connection header");
    }
    throw new IOException("WebSocket Response header: Invalid response from Server, It may not support WebSockets.");
  }
  
  private void sendHandshakeRequest(String paramString)
  {
    Object localObject1 = "/mqtt";
    try
    {
      URI localURI = new java/net/URI;
      localURI.<init>(this.uri);
      Object localObject2 = localObject1;
      if (localURI.getRawPath() != null)
      {
        localObject2 = localObject1;
        if (!localURI.getRawPath().isEmpty())
        {
          localObject1 = localURI.getRawPath();
          localObject2 = localObject1;
          if (localURI.getRawQuery() != null)
          {
            localObject2 = localObject1;
            if (!localURI.getRawQuery().isEmpty())
            {
              localObject2 = new java/lang/StringBuilder;
              ((StringBuilder)localObject2).<init>();
              ((StringBuilder)localObject2).append((String)localObject1);
              ((StringBuilder)localObject2).append("?");
              ((StringBuilder)localObject2).append(localURI.getRawQuery());
              localObject2 = ((StringBuilder)localObject2).toString();
            }
          }
        }
      }
      localObject1 = new java/io/PrintWriter;
      ((PrintWriter)localObject1).<init>(this.output);
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("GET ");
      localStringBuilder.append((String)localObject2);
      localStringBuilder.append(" HTTP/1.1");
      localStringBuilder.append("\r\n");
      ((PrintWriter)localObject1).print(localStringBuilder.toString());
      if ((this.port != 80) && (this.port != 443))
      {
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("Host: ");
        ((StringBuilder)localObject2).append(this.host);
        ((StringBuilder)localObject2).append(":");
        ((StringBuilder)localObject2).append(this.port);
        ((StringBuilder)localObject2).append("\r\n");
        ((PrintWriter)localObject1).print(((StringBuilder)localObject2).toString());
      }
      else
      {
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("Host: ");
        ((StringBuilder)localObject2).append(this.host);
        ((StringBuilder)localObject2).append("\r\n");
        ((PrintWriter)localObject1).print(((StringBuilder)localObject2).toString());
      }
      ((PrintWriter)localObject1).print("Upgrade: websocket\r\n");
      ((PrintWriter)localObject1).print("Connection: Upgrade\r\n");
      localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Sec-WebSocket-Key: ");
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append("\r\n");
      ((PrintWriter)localObject1).print(((StringBuilder)localObject2).toString());
      ((PrintWriter)localObject1).print("Sec-WebSocket-Protocol: mqtt\r\n");
      ((PrintWriter)localObject1).print("Sec-WebSocket-Version: 13\r\n");
      paramString = localURI.getUserInfo();
      if (paramString != null)
      {
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("Authorization: Basic ");
        ((StringBuilder)localObject2).append(Base64.encode(paramString));
        ((StringBuilder)localObject2).append("\r\n");
        ((PrintWriter)localObject1).print(((StringBuilder)localObject2).toString());
      }
      ((PrintWriter)localObject1).print("\r\n");
      ((PrintWriter)localObject1).flush();
      return;
    }
    catch (URISyntaxException paramString)
    {
      throw new IllegalStateException(paramString.getMessage());
    }
  }
  
  private byte[] sha1(String paramString)
  {
    return MessageDigest.getInstance("SHA1").digest(paramString.getBytes());
  }
  
  private void verifyWebSocketKey(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
    if (Base64.encodeBytes(sha1(localStringBuilder.toString())).trim().equals(paramString2.trim())) {
      return;
    }
    throw new HandshakeFailedException();
  }
  
  public void execute()
  {
    Object localObject = new byte[16];
    System.arraycopy(UUID.randomUUID().toString().getBytes(), 0, localObject, 0, 16);
    localObject = Base64.encodeBytes((byte[])localObject);
    sendHandshakeRequest((String)localObject);
    receiveHandshakeResponse((String)localObject);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/websocket/WebSocketHandshake.class
 *
 * Reversed by:           J
 */