package okhttp3;

import a.f;
import javax.annotation.Nullable;

public abstract class WebSocketListener
{
  public void onClosed(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onClosing(WebSocket paramWebSocket, int paramInt, String paramString) {}
  
  public void onFailure(WebSocket paramWebSocket, Throwable paramThrowable, @Nullable Response paramResponse) {}
  
  public void onMessage(WebSocket paramWebSocket, f paramf) {}
  
  public void onMessage(WebSocket paramWebSocket, String paramString) {}
  
  public void onOpen(WebSocket paramWebSocket, Response paramResponse) {}
}


/* Location:              ~/okhttp3/WebSocketListener.class
 *
 * Reversed by:           J
 */