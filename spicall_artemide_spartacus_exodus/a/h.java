package a;

public abstract class h
  implements s
{
  private final s delegate;
  
  public h(s params)
  {
    if (params != null)
    {
      this.delegate = params;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void close()
  {
    this.delegate.close();
  }
  
  public final s delegate()
  {
    return this.delegate;
  }
  
  public long read(c paramc, long paramLong)
  {
    return this.delegate.read(paramc, paramLong);
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
}


/* Location:              ~/a/h.class
 *
 * Reversed by:           J
 */