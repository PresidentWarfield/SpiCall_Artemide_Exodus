package okhttp3.internal.cache;

import a.c;
import a.g;
import a.r;
import java.io.IOException;

class FaultHidingSink
  extends g
{
  private boolean hasErrors;
  
  FaultHidingSink(r paramr)
  {
    super(paramr);
  }
  
  public void close()
  {
    if (this.hasErrors) {
      return;
    }
    try
    {
      super.close();
    }
    catch (IOException localIOException)
    {
      this.hasErrors = true;
      onException(localIOException);
    }
  }
  
  public void flush()
  {
    if (this.hasErrors) {
      return;
    }
    try
    {
      super.flush();
    }
    catch (IOException localIOException)
    {
      this.hasErrors = true;
      onException(localIOException);
    }
  }
  
  protected void onException(IOException paramIOException) {}
  
  public void write(c paramc, long paramLong)
  {
    if (this.hasErrors)
    {
      paramc.i(paramLong);
      return;
    }
    try
    {
      super.write(paramc, paramLong);
    }
    catch (IOException paramc)
    {
      this.hasErrors = true;
      onException(paramc);
    }
  }
}


/* Location:              ~/okhttp3/internal/cache/FaultHidingSink.class
 *
 * Reversed by:           J
 */