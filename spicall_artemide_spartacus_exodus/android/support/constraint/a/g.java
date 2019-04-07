package android.support.constraint.a;

final class g
{
  static abstract interface a<T>
  {
    public abstract T a();
    
    public abstract void a(T[] paramArrayOfT, int paramInt);
    
    public abstract boolean a(T paramT);
  }
  
  static class b<T>
    implements g.a<T>
  {
    private final Object[] a;
    private int b;
    
    b(int paramInt)
    {
      if (paramInt > 0)
      {
        this.a = new Object[paramInt];
        return;
      }
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    public T a()
    {
      int i = this.b;
      if (i > 0)
      {
        int j = i - 1;
        Object[] arrayOfObject = this.a;
        Object localObject = arrayOfObject[j];
        arrayOfObject[j] = null;
        this.b = (i - 1);
        return (T)localObject;
      }
      return null;
    }
    
    public void a(T[] paramArrayOfT, int paramInt)
    {
      int i = paramInt;
      if (paramInt > paramArrayOfT.length) {
        i = paramArrayOfT.length;
      }
      for (paramInt = 0; paramInt < i; paramInt++)
      {
        T ? = paramArrayOfT[paramInt];
        int j = this.b;
        Object[] arrayOfObject = this.a;
        if (j < arrayOfObject.length)
        {
          arrayOfObject[j] = ?;
          this.b = (j + 1);
        }
      }
    }
    
    public boolean a(T paramT)
    {
      int i = this.b;
      Object[] arrayOfObject = this.a;
      if (i < arrayOfObject.length)
      {
        arrayOfObject[i] = paramT;
        this.b = (i + 1);
        return true;
      }
      return false;
    }
  }
}


/* Location:              ~/android/support/constraint/a/g.class
 *
 * Reversed by:           J
 */