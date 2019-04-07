package android.support.v7.widget;

import android.support.v4.os.TraceCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class u
  implements Runnable
{
  static final ThreadLocal<u> a = new ThreadLocal();
  static Comparator<b> e = new Comparator()
  {
    public int a(u.b paramAnonymousb1, u.b paramAnonymousb2)
    {
      RecyclerView localRecyclerView = paramAnonymousb1.d;
      int i = 1;
      int j = 1;
      if (localRecyclerView == null) {
        k = 1;
      } else {
        k = 0;
      }
      int m;
      if (paramAnonymousb2.d == null) {
        m = 1;
      } else {
        m = 0;
      }
      if (k != m)
      {
        if (paramAnonymousb1.d == null) {
          k = j;
        } else {
          k = -1;
        }
        return k;
      }
      if (paramAnonymousb1.a != paramAnonymousb2.a)
      {
        k = i;
        if (paramAnonymousb1.a) {
          k = -1;
        }
        return k;
      }
      int k = paramAnonymousb2.b - paramAnonymousb1.b;
      if (k != 0) {
        return k;
      }
      k = paramAnonymousb1.c - paramAnonymousb2.c;
      if (k != 0) {
        return k;
      }
      return 0;
    }
  };
  ArrayList<RecyclerView> b = new ArrayList();
  long c;
  long d;
  private ArrayList<b> f = new ArrayList();
  
  private RecyclerView.ViewHolder a(RecyclerView paramRecyclerView, int paramInt, long paramLong)
  {
    if (a(paramRecyclerView, paramInt)) {
      return null;
    }
    RecyclerView.Recycler localRecycler = paramRecyclerView.mRecycler;
    try
    {
      paramRecyclerView.onEnterLayoutOrScroll();
      RecyclerView.ViewHolder localViewHolder = localRecycler.tryGetViewHolderForPositionByDeadline(paramInt, false, paramLong);
      if (localViewHolder != null) {
        if ((localViewHolder.isBound()) && (!localViewHolder.isInvalid())) {
          localRecycler.recycleView(localViewHolder.itemView);
        } else {
          localRecycler.addViewHolderToRecycledViewPool(localViewHolder, false);
        }
      }
      return localViewHolder;
    }
    finally
    {
      paramRecyclerView.onExitLayoutOrScroll(false);
    }
  }
  
  private void a()
  {
    int i = this.b.size();
    int j = 0;
    Object localObject;
    for (int k = 0; j < i; k = m)
    {
      localObject = (RecyclerView)this.b.get(j);
      m = k;
      if (((RecyclerView)localObject).getWindowVisibility() == 0)
      {
        ((RecyclerView)localObject).mPrefetchRegistry.a((RecyclerView)localObject, false);
        m = k + ((RecyclerView)localObject).mPrefetchRegistry.d;
      }
      j++;
    }
    this.f.ensureCapacity(k);
    int m = 0;
    k = 0;
    while (m < i)
    {
      RecyclerView localRecyclerView = (RecyclerView)this.b.get(m);
      if (localRecyclerView.getWindowVisibility() == 0)
      {
        a locala = localRecyclerView.mPrefetchRegistry;
        int n = Math.abs(locala.a) + Math.abs(locala.b);
        for (j = 0; j < locala.d * 2; j += 2)
        {
          if (k >= this.f.size())
          {
            localObject = new b();
            this.f.add(localObject);
          }
          else
          {
            localObject = (b)this.f.get(k);
          }
          int i1 = locala.c[(j + 1)];
          boolean bool;
          if (i1 <= n) {
            bool = true;
          } else {
            bool = false;
          }
          ((b)localObject).a = bool;
          ((b)localObject).b = n;
          ((b)localObject).c = i1;
          ((b)localObject).d = localRecyclerView;
          ((b)localObject).e = locala.c[j];
          k++;
        }
      }
      m++;
    }
    Collections.sort(this.f, e);
  }
  
  private void a(RecyclerView paramRecyclerView, long paramLong)
  {
    if (paramRecyclerView == null) {
      return;
    }
    if ((paramRecyclerView.mDataSetHasChangedAfterLayout) && (paramRecyclerView.mChildHelper.c() != 0)) {
      paramRecyclerView.removeAndRecycleViews();
    }
    a locala = paramRecyclerView.mPrefetchRegistry;
    locala.a(paramRecyclerView, true);
    if (locala.d != 0) {
      try
      {
        TraceCompat.beginSection("RV Nested Prefetch");
        paramRecyclerView.mState.prepareForNestedPrefetch(paramRecyclerView.mAdapter);
        for (int i = 0; i < locala.d * 2; i += 2) {
          a(paramRecyclerView, locala.c[i], paramLong);
        }
      }
      finally
      {
        TraceCompat.endSection();
      }
    }
  }
  
  private void a(b paramb, long paramLong)
  {
    long l;
    if (paramb.a) {
      l = Long.MAX_VALUE;
    } else {
      l = paramLong;
    }
    paramb = a(paramb.d, paramb.e, l);
    if ((paramb != null) && (paramb.mNestedRecyclerView != null) && (paramb.isBound()) && (!paramb.isInvalid())) {
      a((RecyclerView)paramb.mNestedRecyclerView.get(), paramLong);
    }
  }
  
  static boolean a(RecyclerView paramRecyclerView, int paramInt)
  {
    int i = paramRecyclerView.mChildHelper.c();
    for (int j = 0; j < i; j++)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramRecyclerView.mChildHelper.d(j));
      if ((localViewHolder.mPosition == paramInt) && (!localViewHolder.isInvalid())) {
        return true;
      }
    }
    return false;
  }
  
  private void b(long paramLong)
  {
    for (int i = 0; i < this.f.size(); i++)
    {
      b localb = (b)this.f.get(i);
      if (localb.d == null) {
        break;
      }
      a(localb, paramLong);
      localb.a();
    }
  }
  
  void a(long paramLong)
  {
    a();
    b(paramLong);
  }
  
  public void a(RecyclerView paramRecyclerView)
  {
    this.b.add(paramRecyclerView);
  }
  
  void a(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    if ((paramRecyclerView.isAttachedToWindow()) && (this.c == 0L))
    {
      this.c = paramRecyclerView.getNanoTime();
      paramRecyclerView.post(this);
    }
    paramRecyclerView.mPrefetchRegistry.a(paramInt1, paramInt2);
  }
  
  public void b(RecyclerView paramRecyclerView)
  {
    this.b.remove(paramRecyclerView);
  }
  
  public void run()
  {
    try
    {
      TraceCompat.beginSection("RV Prefetch");
      boolean bool = this.b.isEmpty();
      if (bool) {
        return;
      }
      int i = this.b.size();
      int j = 0;
      long l2;
      for (long l1 = 0L; j < i; l1 = l2)
      {
        RecyclerView localRecyclerView = (RecyclerView)this.b.get(j);
        l2 = l1;
        if (localRecyclerView.getWindowVisibility() == 0) {
          l2 = Math.max(localRecyclerView.getDrawingTime(), l1);
        }
        j++;
      }
      if (l1 == 0L) {
        return;
      }
      a(TimeUnit.MILLISECONDS.toNanos(l1) + this.d);
      return;
    }
    finally
    {
      this.c = 0L;
      TraceCompat.endSection();
    }
  }
  
  static class a
    implements RecyclerView.LayoutManager.LayoutPrefetchRegistry
  {
    int a;
    int b;
    int[] c;
    int d;
    
    void a()
    {
      int[] arrayOfInt = this.c;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
      this.d = 0;
    }
    
    void a(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
    
    void a(RecyclerView paramRecyclerView, boolean paramBoolean)
    {
      this.d = 0;
      Object localObject = this.c;
      if (localObject != null) {
        Arrays.fill((int[])localObject, -1);
      }
      localObject = paramRecyclerView.mLayout;
      if ((paramRecyclerView.mAdapter != null) && (localObject != null) && (((RecyclerView.LayoutManager)localObject).isItemPrefetchEnabled()))
      {
        if (paramBoolean)
        {
          if (!paramRecyclerView.mAdapterHelper.d()) {
            ((RecyclerView.LayoutManager)localObject).collectInitialPrefetchPositions(paramRecyclerView.mAdapter.getItemCount(), this);
          }
        }
        else if (!paramRecyclerView.hasPendingAdapterUpdates()) {
          ((RecyclerView.LayoutManager)localObject).collectAdjacentPrefetchPositions(this.a, this.b, paramRecyclerView.mState, this);
        }
        if (this.d > ((RecyclerView.LayoutManager)localObject).mPrefetchMaxCountObserved)
        {
          ((RecyclerView.LayoutManager)localObject).mPrefetchMaxCountObserved = this.d;
          ((RecyclerView.LayoutManager)localObject).mPrefetchMaxObservedInInitialPrefetch = paramBoolean;
          paramRecyclerView.mRecycler.updateViewCacheSize();
        }
      }
    }
    
    boolean a(int paramInt)
    {
      if (this.c != null)
      {
        int i = this.d;
        for (int j = 0; j < i * 2; j += 2) {
          if (this.c[j] == paramInt) {
            return true;
          }
        }
      }
      return false;
    }
    
    public void addPosition(int paramInt1, int paramInt2)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= 0)
        {
          int i = this.d * 2;
          int[] arrayOfInt = this.c;
          if (arrayOfInt == null)
          {
            this.c = new int[4];
            Arrays.fill(this.c, -1);
          }
          else if (i >= arrayOfInt.length)
          {
            this.c = new int[i * 2];
            System.arraycopy(arrayOfInt, 0, this.c, 0, arrayOfInt.length);
          }
          arrayOfInt = this.c;
          arrayOfInt[i] = paramInt1;
          arrayOfInt[(i + 1)] = paramInt2;
          this.d += 1;
          return;
        }
        throw new IllegalArgumentException("Pixel distance must be non-negative");
      }
      throw new IllegalArgumentException("Layout positions must be non-negative");
    }
  }
  
  static class b
  {
    public boolean a;
    public int b;
    public int c;
    public RecyclerView d;
    public int e;
    
    public void a()
    {
      this.a = false;
      this.b = 0;
      this.c = 0;
      this.d = null;
      this.e = 0;
    }
  }
}


/* Location:              ~/android/support/v7/widget/u.class
 *
 * Reversed by:           J
 */