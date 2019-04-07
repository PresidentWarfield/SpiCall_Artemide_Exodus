package okhttp3;

import a.f;
import javax.annotation.Nullable;

public abstract interface WebSocket
{
  public abstract void cancel();
  
  public abstract boolean close(int paramInt, @Nullable String paramString);
  
  public abstract long queueSize();
  
  public abstract Request request();
  
  public abstract boolean send(f paramf);
  
  public abstract boolean send(String paramString);
  
  public static abstract interface Factory
  {
    public abstract WebSocket newWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener);
  }
}


/* Location:              ~/okhttp3/WebSocket.class
 *
 * Reversed by:           J
 */