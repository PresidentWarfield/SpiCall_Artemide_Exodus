package okhttp3;

import java.net.Socket;
import javax.annotation.Nullable;

public abstract interface Connection
{
  @Nullable
  public abstract Handshake handshake();
  
  public abstract Protocol protocol();
  
  public abstract Route route();
  
  public abstract Socket socket();
}


/* Location:              ~/okhttp3/Connection.class
 *
 * Reversed by:           J
 */