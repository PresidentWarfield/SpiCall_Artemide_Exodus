package android.support.v4.util;

public final class Pools
{
  public static abstract interface Pool<T>
  {
    public abstract T acquire();
    
    public abstract boolean release(T paramT);
  }
  
  public static class SimplePool<T>
    implements Pools.Pool<T>
  {
    private final Object[] mPool;
    private int mPoolSize;
    
    public SimplePool(int paramInt)
    {
      if (paramInt > 0)
      {
        this.mPool = new Object[paramInt];
        return;
      }
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    private boolean isInPool(T paramT)
    {
      for (int i = 0; i < this.mPoolSize; i++) {
        if (this.mPool[i] == paramT) {
          return true;
        }
      }
      return false;
    }
    
    public T acquire()
    {
      int i = this.mPoolSize;
      if (i > 0)
      {
        int j = i - 1;
        Object[] arrayOfObject = this.mPool;
        Object localObject = arrayOfObject[j];
        arrayOfObject[j] = null;
        this.mPoolSize = (i - 1);
        return (T)localObject;
      }
      return null;
    }
    
    public boolean release(T paramT)
    {
      if (!isInPool(paramT))
      {
        int i = this.mPoolSize;
        Object[] arrayOfObject = this.mPool;
        if (i < arrayOfObject.length)
        {
          arrayOfObject[i] = paramT;
          this.mPoolSize = (i + 1);
          return true;
        }
        return false;
      }
      throw new IllegalStateException("Already in the pool!");
    }
  }
  
  public static class SynchronizedPool<T>
    extends Pools.SimplePool<T>
  {
    private final Object mLock = new Object();
    
    public SynchronizedPool(int paramInt)
    {
      super();
    }
    
    public T acquire()
    {
      synchronized (this.mLock)
      {
        Object localObject2 = super.acquire();
        return (T)localObject2;
      }
    }
    
    public boolean release(T paramT)
    {
      synchronized (this.mLock)
      {
        boolean bool = super.release(paramT);
        return bool;
      }
    }
  }
}


/* Location:              ~/android/support/v4/util/Pools.class
 *
 * Reversed by:           J
 */