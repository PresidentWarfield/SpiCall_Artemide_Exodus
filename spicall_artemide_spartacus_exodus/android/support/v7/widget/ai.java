package android.support.v7.widget;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;

class ai
{
  final ArrayMap<RecyclerView.ViewHolder, a> a = new ArrayMap();
  final LongSparseArray<RecyclerView.ViewHolder> b = new LongSparseArray();
  
  private RecyclerView.ItemAnimator.ItemHolderInfo a(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    int i = this.a.indexOfKey(paramViewHolder);
    if (i < 0) {
      return null;
    }
    a locala = (a)this.a.valueAt(i);
    if ((locala != null) && ((locala.a & paramInt) != 0))
    {
      locala.a &= (paramInt ^ 0xFFFFFFFF);
      if (paramInt == 4)
      {
        paramViewHolder = locala.b;
      }
      else
      {
        if (paramInt != 8) {
          break label110;
        }
        paramViewHolder = locala.c;
      }
      if ((locala.a & 0xC) == 0)
      {
        this.a.removeAt(i);
        a.a(locala);
      }
      return paramViewHolder;
      label110:
      throw new IllegalArgumentException("Must provide flag PRE or POST");
    }
    return null;
  }
  
  RecyclerView.ViewHolder a(long paramLong)
  {
    return (RecyclerView.ViewHolder)this.b.get(paramLong);
  }
  
  void a()
  {
    this.a.clear();
    this.b.clear();
  }
  
  void a(long paramLong, RecyclerView.ViewHolder paramViewHolder)
  {
    this.b.put(paramLong, paramViewHolder);
  }
  
  void a(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    a locala1 = (a)this.a.get(paramViewHolder);
    a locala2 = locala1;
    if (locala1 == null)
    {
      locala2 = a.a();
      this.a.put(paramViewHolder, locala2);
    }
    locala2.b = paramItemHolderInfo;
    locala2.a |= 0x4;
  }
  
  void a(b paramb)
  {
    for (int i = this.a.size() - 1; i >= 0; i--)
    {
      RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.a.keyAt(i);
      a locala = (a)this.a.removeAt(i);
      if ((locala.a & 0x3) == 3) {
        paramb.a(localViewHolder);
      } else if ((locala.a & 0x1) != 0)
      {
        if (locala.b == null) {
          paramb.a(localViewHolder);
        } else {
          paramb.a(localViewHolder, locala.b, locala.c);
        }
      }
      else if ((locala.a & 0xE) == 14) {
        paramb.b(localViewHolder, locala.b, locala.c);
      } else if ((locala.a & 0xC) == 12) {
        paramb.c(localViewHolder, locala.b, locala.c);
      } else if ((locala.a & 0x4) != 0) {
        paramb.a(localViewHolder, locala.b, null);
      } else if ((locala.a & 0x8) != 0) {
        paramb.b(localViewHolder, locala.b, locala.c);
      } else {
        int j = locala.a;
      }
      a.a(locala);
    }
  }
  
  boolean a(RecyclerView.ViewHolder paramViewHolder)
  {
    paramViewHolder = (a)this.a.get(paramViewHolder);
    boolean bool = true;
    if ((paramViewHolder == null) || ((paramViewHolder.a & 0x1) == 0)) {
      bool = false;
    }
    return bool;
  }
  
  RecyclerView.ItemAnimator.ItemHolderInfo b(RecyclerView.ViewHolder paramViewHolder)
  {
    return a(paramViewHolder, 4);
  }
  
  void b() {}
  
  void b(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    a locala1 = (a)this.a.get(paramViewHolder);
    a locala2 = locala1;
    if (locala1 == null)
    {
      locala2 = a.a();
      this.a.put(paramViewHolder, locala2);
    }
    locala2.a |= 0x2;
    locala2.b = paramItemHolderInfo;
  }
  
  RecyclerView.ItemAnimator.ItemHolderInfo c(RecyclerView.ViewHolder paramViewHolder)
  {
    return a(paramViewHolder, 8);
  }
  
  void c(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    a locala1 = (a)this.a.get(paramViewHolder);
    a locala2 = locala1;
    if (locala1 == null)
    {
      locala2 = a.a();
      this.a.put(paramViewHolder, locala2);
    }
    locala2.c = paramItemHolderInfo;
    locala2.a |= 0x8;
  }
  
  boolean d(RecyclerView.ViewHolder paramViewHolder)
  {
    paramViewHolder = (a)this.a.get(paramViewHolder);
    boolean bool;
    if ((paramViewHolder != null) && ((paramViewHolder.a & 0x4) != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void e(RecyclerView.ViewHolder paramViewHolder)
  {
    a locala1 = (a)this.a.get(paramViewHolder);
    a locala2 = locala1;
    if (locala1 == null)
    {
      locala2 = a.a();
      this.a.put(paramViewHolder, locala2);
    }
    locala2.a |= 0x1;
  }
  
  void f(RecyclerView.ViewHolder paramViewHolder)
  {
    paramViewHolder = (a)this.a.get(paramViewHolder);
    if (paramViewHolder == null) {
      return;
    }
    paramViewHolder.a &= 0xFFFFFFFE;
  }
  
  void g(RecyclerView.ViewHolder paramViewHolder)
  {
    for (int i = this.b.size() - 1; i >= 0; i--) {
      if (paramViewHolder == this.b.valueAt(i))
      {
        this.b.removeAt(i);
        break;
      }
    }
    paramViewHolder = (a)this.a.remove(paramViewHolder);
    if (paramViewHolder != null) {
      a.a(paramViewHolder);
    }
  }
  
  public void h(RecyclerView.ViewHolder paramViewHolder)
  {
    f(paramViewHolder);
  }
  
  static class a
  {
    static Pools.Pool<a> d = new Pools.SimplePool(20);
    int a;
    RecyclerView.ItemAnimator.ItemHolderInfo b;
    RecyclerView.ItemAnimator.ItemHolderInfo c;
    
    static a a()
    {
      a locala1 = (a)d.acquire();
      a locala2 = locala1;
      if (locala1 == null) {
        locala2 = new a();
      }
      return locala2;
    }
    
    static void a(a parama)
    {
      parama.a = 0;
      parama.b = null;
      parama.c = null;
      d.release(parama);
    }
    
    static void b()
    {
      while (d.acquire() != null) {}
    }
  }
  
  static abstract interface b
  {
    public abstract void a(RecyclerView.ViewHolder paramViewHolder);
    
    public abstract void a(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void b(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void c(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
  }
}


/* Location:              ~/android/support/v7/widget/ai.class
 *
 * Reversed by:           J
 */