package a;

import java.io.Closeable;
import java.io.Flushable;

public abstract interface r
  extends Closeable, Flushable
{
  public abstract void close();
  
  public abstract void flush();
  
  public abstract t timeout();
  
  public abstract void write(c paramc, long paramLong);
}


/* Location:              ~/a/r.class
 *
 * Reversed by:           J
 */