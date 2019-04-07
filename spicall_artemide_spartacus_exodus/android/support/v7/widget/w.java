package android.support.v7.widget;

import java.util.List;

class w
{
  final a a;
  
  w(a parama)
  {
    this.a = parama;
  }
  
  private void a(List<d.b> paramList, int paramInt1, int paramInt2)
  {
    d.b localb1 = (d.b)paramList.get(paramInt1);
    d.b localb2 = (d.b)paramList.get(paramInt2);
    int i = localb2.a;
    if (i != 4) {
      switch (i)
      {
      default: 
        break;
      case 2: 
        a(paramList, paramInt1, localb1, paramInt2, localb2);
        break;
      case 1: 
        c(paramList, paramInt1, localb1, paramInt2, localb2);
        break;
      }
    } else {
      b(paramList, paramInt1, localb1, paramInt2, localb2);
    }
  }
  
  private int b(List<d.b> paramList)
  {
    int i = paramList.size() - 1;
    int k;
    for (int j = 0; i >= 0; j = k)
    {
      if (((d.b)paramList.get(i)).a == 8)
      {
        k = j;
        if (j != 0) {
          return i;
        }
      }
      else
      {
        k = 1;
      }
      i--;
    }
    return -1;
  }
  
  private void c(List<d.b> paramList, int paramInt1, d.b paramb1, int paramInt2, d.b paramb2)
  {
    int i;
    if (paramb1.d < paramb2.b) {
      i = -1;
    } else {
      i = 0;
    }
    int j = i;
    if (paramb1.b < paramb2.b) {
      j = i + 1;
    }
    if (paramb2.b <= paramb1.b) {
      paramb1.b += paramb2.d;
    }
    if (paramb2.b <= paramb1.d) {
      paramb1.d += paramb2.d;
    }
    paramb2.b += j;
    paramList.set(paramInt1, paramb2);
    paramList.set(paramInt2, paramb1);
  }
  
  void a(List<d.b> paramList)
  {
    for (;;)
    {
      int i = b(paramList);
      if (i == -1) {
        break;
      }
      a(paramList, i, i + 1);
    }
  }
  
  void a(List<d.b> paramList, int paramInt1, d.b paramb1, int paramInt2, d.b paramb2)
  {
    int i = paramb1.b;
    int j = paramb1.d;
    int k = 0;
    if (i < j)
    {
      if ((paramb2.b == paramb1.b) && (paramb2.d == paramb1.d - paramb1.b))
      {
        i = 0;
        k = 1;
      }
      else
      {
        i = 0;
      }
    }
    else if ((paramb2.b == paramb1.d + 1) && (paramb2.d == paramb1.b - paramb1.d))
    {
      i = 1;
      k = 1;
    }
    else
    {
      i = 1;
    }
    if (paramb1.d < paramb2.b)
    {
      paramb2.b -= 1;
    }
    else if (paramb1.d < paramb2.b + paramb2.d)
    {
      paramb2.d -= 1;
      paramb1.a = 2;
      paramb1.d = 1;
      if (paramb2.d == 0)
      {
        paramList.remove(paramInt2);
        this.a.a(paramb2);
      }
      return;
    }
    int m = paramb1.b;
    j = paramb2.b;
    d.b localb = null;
    if (m <= j)
    {
      paramb2.b += 1;
    }
    else if (paramb1.b < paramb2.b + paramb2.d)
    {
      int n = paramb2.b;
      m = paramb2.d;
      j = paramb1.b;
      localb = this.a.a(2, paramb1.b + 1, n + m - j, null);
      paramb2.d = (paramb1.b - paramb2.b);
    }
    if (k != 0)
    {
      paramList.set(paramInt1, paramb2);
      paramList.remove(paramInt2);
      this.a.a(paramb1);
      return;
    }
    if (i != 0)
    {
      if (localb != null)
      {
        if (paramb1.b > localb.b) {
          paramb1.b -= localb.d;
        }
        if (paramb1.d > localb.b) {
          paramb1.d -= localb.d;
        }
      }
      if (paramb1.b > paramb2.b) {
        paramb1.b -= paramb2.d;
      }
      if (paramb1.d > paramb2.b) {
        paramb1.d -= paramb2.d;
      }
    }
    else
    {
      if (localb != null)
      {
        if (paramb1.b >= localb.b) {
          paramb1.b -= localb.d;
        }
        if (paramb1.d >= localb.b) {
          paramb1.d -= localb.d;
        }
      }
      if (paramb1.b >= paramb2.b) {
        paramb1.b -= paramb2.d;
      }
      if (paramb1.d >= paramb2.b) {
        paramb1.d -= paramb2.d;
      }
    }
    paramList.set(paramInt1, paramb2);
    if (paramb1.b != paramb1.d) {
      paramList.set(paramInt2, paramb1);
    } else {
      paramList.remove(paramInt2);
    }
    if (localb != null) {
      paramList.add(paramInt1, localb);
    }
  }
  
  void b(List<d.b> paramList, int paramInt1, d.b paramb1, int paramInt2, d.b paramb2)
  {
    int i = paramb1.d;
    int j = paramb2.b;
    d.b localb1 = null;
    if (i < j)
    {
      paramb2.b -= 1;
    }
    else if (paramb1.d < paramb2.b + paramb2.d)
    {
      paramb2.d -= 1;
      localb2 = this.a.a(4, paramb1.b, 1, paramb2.c);
      break label96;
    }
    d.b localb2 = null;
    label96:
    if (paramb1.b <= paramb2.b)
    {
      paramb2.b += 1;
    }
    else if (paramb1.b < paramb2.b + paramb2.d)
    {
      i = paramb2.b + paramb2.d - paramb1.b;
      localb1 = this.a.a(4, paramb1.b + 1, i, paramb2.c);
      paramb2.d -= i;
    }
    paramList.set(paramInt2, paramb1);
    if (paramb2.d > 0)
    {
      paramList.set(paramInt1, paramb2);
    }
    else
    {
      paramList.remove(paramInt1);
      this.a.a(paramb2);
    }
    if (localb2 != null) {
      paramList.add(paramInt1, localb2);
    }
    if (localb1 != null) {
      paramList.add(paramInt1, localb1);
    }
  }
  
  static abstract interface a
  {
    public abstract d.b a(int paramInt1, int paramInt2, int paramInt3, Object paramObject);
    
    public abstract void a(d.b paramb);
  }
}


/* Location:              ~/android/support/v7/widget/w.class
 *
 * Reversed by:           J
 */