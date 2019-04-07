package android.support.design.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class d<T>
{
  private final Pools.Pool<ArrayList<T>> a = new Pools.SimplePool(10);
  private final SimpleArrayMap<T, ArrayList<T>> b = new SimpleArrayMap();
  private final ArrayList<T> c = new ArrayList();
  private final HashSet<T> d = new HashSet();
  
  private void a(T paramT, ArrayList<T> paramArrayList, HashSet<T> paramHashSet)
  {
    if (paramArrayList.contains(paramT)) {
      return;
    }
    if (!paramHashSet.contains(paramT))
    {
      paramHashSet.add(paramT);
      ArrayList localArrayList = (ArrayList)this.b.get(paramT);
      if (localArrayList != null)
      {
        int i = 0;
        int j = localArrayList.size();
        while (i < j)
        {
          a(localArrayList.get(i), paramArrayList, paramHashSet);
          i++;
        }
      }
      paramHashSet.remove(paramT);
      paramArrayList.add(paramT);
      return;
    }
    throw new RuntimeException("This graph contains cyclic dependencies");
  }
  
  private void a(ArrayList<T> paramArrayList)
  {
    paramArrayList.clear();
    this.a.release(paramArrayList);
  }
  
  private ArrayList<T> c()
  {
    ArrayList localArrayList1 = (ArrayList)this.a.acquire();
    ArrayList localArrayList2 = localArrayList1;
    if (localArrayList1 == null) {
      localArrayList2 = new ArrayList();
    }
    return localArrayList2;
  }
  
  void a()
  {
    int i = this.b.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList = (ArrayList)this.b.valueAt(j);
      if (localArrayList != null) {
        a(localArrayList);
      }
    }
    this.b.clear();
  }
  
  void a(T paramT)
  {
    if (!this.b.containsKey(paramT)) {
      this.b.put(paramT, null);
    }
  }
  
  void a(T paramT1, T paramT2)
  {
    if ((this.b.containsKey(paramT1)) && (this.b.containsKey(paramT2)))
    {
      ArrayList localArrayList1 = (ArrayList)this.b.get(paramT1);
      ArrayList localArrayList2 = localArrayList1;
      if (localArrayList1 == null)
      {
        localArrayList2 = c();
        this.b.put(paramT1, localArrayList2);
      }
      localArrayList2.add(paramT2);
      return;
    }
    throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
  }
  
  ArrayList<T> b()
  {
    this.c.clear();
    this.d.clear();
    int i = this.b.size();
    for (int j = 0; j < i; j++) {
      a(this.b.keyAt(j), this.c, this.d);
    }
    return this.c;
  }
  
  boolean b(T paramT)
  {
    return this.b.containsKey(paramT);
  }
  
  List c(T paramT)
  {
    return (List)this.b.get(paramT);
  }
  
  List<T> d(T paramT)
  {
    int i = this.b.size();
    Object localObject1 = null;
    int j = 0;
    while (j < i)
    {
      ArrayList localArrayList = (ArrayList)this.b.valueAt(j);
      Object localObject2 = localObject1;
      if (localArrayList != null)
      {
        localObject2 = localObject1;
        if (localArrayList.contains(paramT))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add(this.b.keyAt(j));
        }
      }
      j++;
      localObject1 = localObject2;
    }
    return (List<T>)localObject1;
  }
  
  boolean e(T paramT)
  {
    int i = this.b.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList = (ArrayList)this.b.valueAt(j);
      if ((localArrayList != null) && (localArrayList.contains(paramT))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:              ~/android/support/design/widget/d.class
 *
 * Reversed by:           J
 */