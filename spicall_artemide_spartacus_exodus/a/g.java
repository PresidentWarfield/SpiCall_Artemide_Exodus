package a;

public abstract class g
  implements r
{
  private final r delegate;
  
  public g(r paramr)
  {
    if (paramr != null)
    {
      this.delegate = paramr;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void close()
  {
    this.delegate.close();
  }
  
  public final r delegate()
  {
    return this.delegate;
  }
  
  public void flush()
  {
    this.delegate.flush();
  }
  
  public t timeout()
  {
    return this.delegate.timeout();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("(");
    localStringBuilder.append(this.delegate.toString());
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public void write(c paramc, long paramLong)
  {
    this.delegate.write(paramc, paramLong);
  }
}


/* Location:              ~/a/g.class
 *
 * Reversed by:           J
 */