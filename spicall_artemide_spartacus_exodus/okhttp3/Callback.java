package okhttp3;

import java.io.IOException;

public abstract interface Callback
{
  public abstract void onFailure(Call paramCall, IOException paramIOException);
  
  public abstract void onResponse(Call paramCall, Response paramResponse);
}


/* Location:              ~/okhttp3/Callback.class
 *
 * Reversed by:           J
 */