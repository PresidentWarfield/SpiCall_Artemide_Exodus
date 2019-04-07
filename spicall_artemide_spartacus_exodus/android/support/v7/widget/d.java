package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import java.util.ArrayList;
import java.util.List;

class d
  implements w.a
{
  final ArrayList<b> a = new ArrayList();
  final ArrayList<b> b = new ArrayList();
  final a c;
  Runnable d;
  final boolean e;
  final w f;
  private Pools.Pool<b> g = new Pools.SimplePool(30);
  private int h = 0;
  
  d(a parama)
  {
    this(parama, false);
  }
  
  d(a parama, boolean paramBoolean)
  {
    this.c = parama;
    this.e = paramBoolean;
    this.f = new w(this);
  }
  
  private void b(b paramb)
  {
    g(paramb);
  }
  
  private void c(b paramb)
  {
    int i = paramb.b;
    int j = paramb.b + paramb.d;
    int k = paramb.b;
    int m = 0;
    int n = -1;
    while (k < j)
    {
      int i2;
      if ((this.c.a(k) == null) && (!d(k)))
      {
        if (n == 1)
        {
          g(a(2, i, m, null));
          n = 1;
        }
        else
        {
          n = 0;
        }
        int i1 = 0;
        i2 = n;
        n = i1;
      }
      else
      {
        if (n == 0)
        {
          e(a(2, i, m, null));
          i2 = 1;
        }
        else
        {
          i2 = 0;
        }
        n = 1;
      }
      if (i2 != 0)
      {
        k -= m;
        j -= m;
        i2 = 1;
      }
      else
      {
        i2 = m + 1;
      }
      k++;
      m = i2;
    }
    b localb = paramb;
    if (m != paramb.d)
    {
      a(paramb);
      localb = a(2, i, m, null);
    }
    if (n == 0) {
      e(localb);
    } else {
      g(localb);
    }
  }
  
  private int d(int paramInt1, int paramInt2)
  {
    int i = this.b.size() - 1;
    b localb;
    for (int j = paramInt1; i >= 0; j = paramInt1)
    {
      localb = (b)this.b.get(i);
      if (localb.a == 8)
      {
        int k;
        if (localb.b < localb.d)
        {
          k = localb.b;
          paramInt1 = localb.d;
        }
        else
        {
          k = localb.d;
          paramInt1 = localb.b;
        }
        if ((j >= k) && (j <= paramInt1))
        {
          if (k == localb.b)
          {
            if (paramInt2 == 1) {
              localb.d += 1;
            } else if (paramInt2 == 2) {
              localb.d -= 1;
            }
            paramInt1 = j + 1;
          }
          else
          {
            if (paramInt2 == 1) {
              localb.b += 1;
            } else if (paramInt2 == 2) {
              localb.b -= 1;
            }
            paramInt1 = j - 1;
          }
        }
        else
        {
          paramInt1 = j;
          if (j < localb.b) {
            if (paramInt2 == 1)
            {
              localb.b += 1;
              localb.d += 1;
              paramInt1 = j;
            }
            else
            {
              paramInt1 = j;
              if (paramInt2 == 2)
              {
                localb.b -= 1;
                localb.d -= 1;
                paramInt1 = j;
              }
            }
          }
        }
      }
      else if (localb.b <= j)
      {
        if (localb.a == 1)
        {
          paramInt1 = j - localb.d;
        }
        else
        {
          paramInt1 = j;
          if (localb.a == 2) {
            paramInt1 = j + localb.d;
          }
        }
      }
      else if (paramInt2 == 1)
      {
        localb.b += 1;
        paramInt1 = j;
      }
      else
      {
        paramInt1 = j;
        if (paramInt2 == 2)
        {
          localb.b -= 1;
          paramInt1 = j;
        }
      }
      i--;
    }
    for (paramInt1 = this.b.size() - 1; paramInt1 >= 0; paramInt1--)
    {
      localb = (b)this.b.get(paramInt1);
      if (localb.a == 8)
      {
        if ((localb.d == localb.b) || (localb.d < 0))
        {
          this.b.remove(paramInt1);
          a(localb);
        }
      }
      else if (localb.d <= 0)
      {
        this.b.remove(paramInt1);
        a(localb);
      }
    }
    return j;
  }
  
  private void d(b paramb)
  {
    int i = paramb.b;
    int j = paramb.b;
    int k = paramb.d;
    int m = paramb.b;
    int n = 0;
    int i3;
    for (int i1 = -1; m < j + k; i1 = i3)
    {
      int i2;
      if ((this.c.a(m) == null) && (!d(m)))
      {
        i2 = n;
        i3 = i;
        if (i1 == 1)
        {
          g(a(4, i, n, paramb.c));
          i3 = m;
          i2 = 0;
        }
        n = 0;
        i = i3;
        i3 = n;
      }
      else
      {
        i2 = n;
        int i4 = i;
        if (i1 == 0)
        {
          e(a(4, i, n, paramb.c));
          i4 = m;
          i2 = 0;
        }
        i3 = 1;
        i = i4;
      }
      n = i2 + 1;
      m++;
    }
    Object localObject = paramb;
    if (n != paramb.d)
    {
      localObject = paramb.c;
      a(paramb);
      localObject = a(4, i, n, localObject);
    }
    if (i1 == 0) {
      e((b)localObject);
    } else {
      g((b)localObject);
    }
  }
  
  private boolean d(int paramInt)
  {
    int i = this.b.size();
    for (int j = 0; j < i; j++)
    {
      b localb = (b)this.b.get(j);
      if (localb.a == 8)
      {
        if (a(localb.d, j + 1) == paramInt) {
          return true;
        }
      }
      else if (localb.a == 1)
      {
        int k = localb.b;
        int m = localb.d;
        for (int n = localb.b; n < k + m; n++) {
          if (a(n, j + 1) == paramInt) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private void e(b paramb)
  {
    if ((paramb.a != 1) && (paramb.a != 8))
    {
      int i = d(paramb.b, paramb.a);
      int j = paramb.b;
      int k = paramb.a;
      int m;
      if (k != 2)
      {
        if (k == 4)
        {
          m = 1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("op should be remove or update.");
          ((StringBuilder)localObject).append(paramb);
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else {
        m = 0;
      }
      int n = 1;
      for (int i1 = 1; n < paramb.d; i1 = k)
      {
        int i2 = d(paramb.b + m * n, paramb.a);
        k = paramb.a;
        if (k != 2)
        {
          if (k != 4) {
            k = 0;
          } else if (i2 == i + 1) {
            k = 1;
          } else {
            k = 0;
          }
        }
        else if (i2 == i) {
          k = 1;
        } else {
          k = 0;
        }
        if (k != 0)
        {
          k = i1 + 1;
        }
        else
        {
          localObject = a(paramb.a, i, i1, paramb.c);
          a((b)localObject, j);
          a((b)localObject);
          k = j;
          if (paramb.a == 4) {
            k = j + i1;
          }
          i = i2;
          i1 = 1;
          j = k;
          k = i1;
        }
        n++;
      }
      Object localObject = paramb.c;
      a(paramb);
      if (i1 > 0)
      {
        paramb = a(paramb.a, i, i1, localObject);
        a(paramb, j);
        a(paramb);
      }
      return;
    }
    throw new IllegalArgumentException("should not dispatch add or move for pre layout");
  }
  
  private void f(b paramb)
  {
    g(paramb);
  }
  
  private void g(b paramb)
  {
    this.b.add(paramb);
    int i = paramb.a;
    if (i != 4)
    {
      if (i != 8) {
        switch (i)
        {
        default: 
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unknown update op type for ");
          localStringBuilder.append(paramb);
          throw new IllegalArgumentException(localStringBuilder.toString());
        case 2: 
          this.c.b(paramb.b, paramb.d);
          break;
        case 1: 
          this.c.c(paramb.b, paramb.d);
          break;
        }
      } else {
        this.c.d(paramb.b, paramb.d);
      }
    }
    else {
      this.c.a(paramb.b, paramb.d, paramb.c);
    }
  }
  
  int a(int paramInt1, int paramInt2)
  {
    int i = this.b.size();
    int j = paramInt2;
    for (paramInt2 = paramInt1; j < i; paramInt2 = paramInt1)
    {
      b localb = (b)this.b.get(j);
      if (localb.a == 8)
      {
        if (localb.b == paramInt2)
        {
          paramInt1 = localb.d;
        }
        else
        {
          int k = paramInt2;
          if (localb.b < paramInt2) {
            k = paramInt2 - 1;
          }
          paramInt1 = k;
          if (localb.d <= k) {
            paramInt1 = k + 1;
          }
        }
      }
      else
      {
        paramInt1 = paramInt2;
        if (localb.b <= paramInt2) {
          if (localb.a == 2)
          {
            if (paramInt2 < localb.b + localb.d) {
              return -1;
            }
            paramInt1 = paramInt2 - localb.d;
          }
          else
          {
            paramInt1 = paramInt2;
            if (localb.a == 1) {
              paramInt1 = paramInt2 + localb.d;
            }
          }
        }
      }
      j++;
    }
    return paramInt2;
  }
  
  public b a(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    b localb = (b)this.g.acquire();
    if (localb == null)
    {
      paramObject = new b(paramInt1, paramInt2, paramInt3, paramObject);
    }
    else
    {
      localb.a = paramInt1;
      localb.b = paramInt2;
      localb.d = paramInt3;
      localb.c = paramObject;
      paramObject = localb;
    }
    return (b)paramObject;
  }
  
  void a()
  {
    a(this.a);
    a(this.b);
    this.h = 0;
  }
  
  public void a(b paramb)
  {
    if (!this.e)
    {
      paramb.c = null;
      this.g.release(paramb);
    }
  }
  
  void a(b paramb, int paramInt)
  {
    this.c.a(paramb);
    int i = paramb.a;
    if (i != 2)
    {
      if (i == 4) {
        this.c.a(paramInt, paramb.d, paramb.c);
      } else {
        throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
      }
    }
    else {
      this.c.a(paramInt, paramb.d);
    }
  }
  
  void a(List<b> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      a((b)paramList.get(j));
    }
    paramList.clear();
  }
  
  boolean a(int paramInt)
  {
    boolean bool;
    if ((paramInt & this.h) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean a(int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = false;
    if (paramInt1 == paramInt2) {
      return false;
    }
    if (paramInt3 == 1)
    {
      this.a.add(a(8, paramInt1, paramInt2, null));
      this.h |= 0x8;
      if (this.a.size() == 1) {
        bool = true;
      }
      return bool;
    }
    throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
  }
  
  boolean a(int paramInt1, int paramInt2, Object paramObject)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.a.add(a(4, paramInt1, paramInt2, paramObject));
    this.h |= 0x4;
    if (this.a.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  int b(int paramInt)
  {
    return a(paramInt, 0);
  }
  
  void b()
  {
    this.f.a(this.a);
    int i = this.a.size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = (b)this.a.get(j);
      int k = ((b)localObject).a;
      if (k != 4)
      {
        if (k != 8) {
          switch (k)
          {
          default: 
            break;
          case 2: 
            c((b)localObject);
            break;
          case 1: 
            f((b)localObject);
            break;
          }
        } else {
          b((b)localObject);
        }
      }
      else {
        d((b)localObject);
      }
      localObject = this.d;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
    }
    this.a.clear();
  }
  
  boolean b(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.a.add(a(1, paramInt1, paramInt2, null));
    this.h |= 0x1;
    if (this.a.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  public int c(int paramInt)
  {
    int i = this.a.size();
    int j = 0;
    for (int k = paramInt; j < i; k = paramInt)
    {
      b localb = (b)this.a.get(j);
      paramInt = localb.a;
      if (paramInt != 8)
      {
        switch (paramInt)
        {
        default: 
          paramInt = k;
          break;
        case 2: 
          paramInt = k;
          if (localb.b > k) {
            break;
          }
          if (localb.b + localb.d > k) {
            return -1;
          }
          paramInt = k - localb.d;
          break;
        case 1: 
          paramInt = k;
          if (localb.b > k) {
            break;
          }
          paramInt = k + localb.d;
          break;
        }
      }
      else if (localb.b == k)
      {
        paramInt = localb.d;
      }
      else
      {
        int m = k;
        if (localb.b < k) {
          m = k - 1;
        }
        paramInt = m;
        if (localb.d <= m) {
          paramInt = m + 1;
        }
      }
      j++;
    }
    return k;
  }
  
  void c()
  {
    int i = this.b.size();
    for (int j = 0; j < i; j++) {
      this.c.b((b)this.b.get(j));
    }
    a(this.b);
    this.h = 0;
  }
  
  boolean c(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt2 < 1) {
      return false;
    }
    this.a.add(a(2, paramInt1, paramInt2, null));
    this.h |= 0x2;
    if (this.a.size() == 1) {
      bool = true;
    }
    return bool;
  }
  
  boolean d()
  {
    boolean bool;
    if (this.a.size() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void e()
  {
    c();
    int i = this.a.size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = (b)this.a.get(j);
      int k = ((b)localObject).a;
      if (k != 4)
      {
        if (k != 8)
        {
          switch (k)
          {
          default: 
            break;
          case 2: 
            this.c.b((b)localObject);
            this.c.a(((b)localObject).b, ((b)localObject).d);
            break;
          case 1: 
            this.c.b((b)localObject);
            this.c.c(((b)localObject).b, ((b)localObject).d);
            break;
          }
        }
        else
        {
          this.c.b((b)localObject);
          this.c.d(((b)localObject).b, ((b)localObject).d);
        }
      }
      else
      {
        this.c.b((b)localObject);
        this.c.a(((b)localObject).b, ((b)localObject).d, ((b)localObject).c);
      }
      localObject = this.d;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
    }
    a(this.a);
    this.h = 0;
  }
  
  boolean f()
  {
    boolean bool;
    if ((!this.b.isEmpty()) && (!this.a.isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static abstract interface a
  {
    public abstract RecyclerView.ViewHolder a(int paramInt);
    
    public abstract void a(int paramInt1, int paramInt2);
    
    public abstract void a(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract void a(d.b paramb);
    
    public abstract void b(int paramInt1, int paramInt2);
    
    public abstract void b(d.b paramb);
    
    public abstract void c(int paramInt1, int paramInt2);
    
    public abstract void d(int paramInt1, int paramInt2);
  }
  
  static class b
  {
    int a;
    int b;
    Object c;
    int d;
    
    b(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
    {
      this.a = paramInt1;
      this.b = paramInt2;
      this.d = paramInt3;
      this.c = paramObject;
    }
    
    String a()
    {
      int i = this.a;
      if (i != 4)
      {
        if (i != 8)
        {
          switch (i)
          {
          default: 
            return "??";
          case 2: 
            return "rm";
          }
          return "add";
        }
        return "mv";
      }
      return "up";
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        b localb = (b)paramObject;
        int i = this.a;
        if (i != localb.a) {
          return false;
        }
        if ((i == 8) && (Math.abs(this.d - this.b) == 1) && (this.d == localb.b) && (this.b == localb.d)) {
          return true;
        }
        if (this.d != localb.d) {
          return false;
        }
        if (this.b != localb.b) {
          return false;
        }
        paramObject = this.c;
        if (paramObject != null)
        {
          if (!paramObject.equals(localb.c)) {
            return false;
          }
        }
        else if (localb.c != null) {
          return false;
        }
        return true;
      }
      return false;
    }
    
    public int hashCode()
    {
      return (this.a * 31 + this.b) * 31 + this.d;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append("[");
      localStringBuilder.append(a());
      localStringBuilder.append(",s:");
      localStringBuilder.append(this.b);
      localStringBuilder.append("c:");
      localStringBuilder.append(this.d);
      localStringBuilder.append(",p:");
      localStringBuilder.append(this.c);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              ~/android/support/v7/widget/d.class
 *
 * Reversed by:           J
 */