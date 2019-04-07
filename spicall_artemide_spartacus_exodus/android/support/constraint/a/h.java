package android.support.constraint.a;

import java.util.Arrays;

public class h
{
  private static int j = 1;
  private static int k = 1;
  private static int l = 1;
  private static int m = 1;
  private static int n = 1;
  public int a = -1;
  int b = -1;
  public int c = 0;
  public float d;
  float[] e = new float[7];
  a f;
  b[] g = new b[8];
  int h = 0;
  public int i = 0;
  private String o;
  
  public h(a parama, String paramString)
  {
    this.f = parama;
  }
  
  static void a()
  {
    k += 1;
  }
  
  public final void a(b paramb)
  {
    int i2;
    for (int i1 = 0;; i1++)
    {
      i2 = this.h;
      if (i1 >= i2) {
        break;
      }
      if (this.g[i1] == paramb) {
        return;
      }
    }
    b[] arrayOfb = this.g;
    if (i2 >= arrayOfb.length) {
      this.g = ((b[])Arrays.copyOf(arrayOfb, arrayOfb.length * 2));
    }
    arrayOfb = this.g;
    i1 = this.h;
    arrayOfb[i1] = paramb;
    this.h = (i1 + 1);
  }
  
  public void a(a parama, String paramString)
  {
    this.f = parama;
  }
  
  public void b()
  {
    this.o = null;
    this.f = a.e;
    this.c = 0;
    this.a = -1;
    this.b = -1;
    this.d = 0.0F;
    this.h = 0;
    this.i = 0;
  }
  
  public final void b(b paramb)
  {
    int i1 = this.h;
    int i2 = 0;
    for (int i3 = 0; i3 < i1; i3++) {
      if (this.g[i3] == paramb)
      {
        while (i2 < i1 - i3 - 1)
        {
          paramb = this.g;
          int i4 = i3 + i2;
          paramb[i4] = paramb[(i4 + 1)];
          i2++;
        }
        this.h -= 1;
        return;
      }
    }
  }
  
  public final void c(b paramb)
  {
    int i1 = this.h;
    for (int i2 = 0; i2 < i1; i2++) {
      this.g[i2].d.a(this.g[i2], paramb, false);
    }
    this.h = 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(this.o);
    return localStringBuilder.toString();
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              ~/android/support/constraint/a/h.class
 *
 * Reversed by:           J
 */