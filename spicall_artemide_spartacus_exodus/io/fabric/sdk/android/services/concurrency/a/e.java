package io.fabric.sdk.android.services.concurrency.a;

public class e
{
  private final int a;
  private final a b;
  private final d c;
  
  public e(int paramInt, a parama, d paramd)
  {
    this.a = paramInt;
    this.b = parama;
    this.c = paramd;
  }
  
  public e(a parama, d paramd)
  {
    this(0, parama, paramd);
  }
  
  public long a()
  {
    return this.b.getDelayMillis(this.a);
  }
  
  public e b()
  {
    return new e(this.a + 1, this.b, this.c);
  }
  
  public e c()
  {
    return new e(this.b, this.c);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/a/e.class
 *
 * Reversed by:           J
 */