package android.support.constraint.a.a;

import java.util.Arrays;

public class j
  extends f
{
  protected f[] ai = new f[4];
  protected int aj = 0;
  
  public void J()
  {
    this.aj = 0;
  }
  
  public void b(f paramf)
  {
    int i = this.aj;
    f[] arrayOff = this.ai;
    if (i + 1 > arrayOff.length) {
      this.ai = ((f[])Arrays.copyOf(arrayOff, arrayOff.length * 2));
    }
    arrayOff = this.ai;
    i = this.aj;
    arrayOff[i] = paramf;
    this.aj = (i + 1);
  }
}


/* Location:              ~/android/support/constraint/a/a/j.class
 *
 * Reversed by:           J
 */