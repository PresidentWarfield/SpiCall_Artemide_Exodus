package android.support.v4.util;

public class LongSparseArray<E>
  implements Cloneable
{
  private static final Object DELETED = new Object();
  private boolean mGarbage = false;
  private long[] mKeys;
  private int mSize;
  private Object[] mValues;
  
  public LongSparseArray()
  {
    this(10);
  }
  
  public LongSparseArray(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mKeys = ContainerHelpers.EMPTY_LONGS;
      this.mValues = ContainerHelpers.EMPTY_OBJECTS;
    }
    else
    {
      paramInt = ContainerHelpers.idealLongArraySize(paramInt);
      this.mKeys = new long[paramInt];
      this.mValues = new Object[paramInt];
    }
    this.mSize = 0;
  }
  
  private void gc()
  {
    int i = this.mSize;
    long[] arrayOfLong = this.mKeys;
    Object[] arrayOfObject = this.mValues;
    int j = 0;
    int m;
    for (int k = 0; j < i; k = m)
    {
      Object localObject = arrayOfObject[j];
      m = k;
      if (localObject != DELETED)
      {
        if (j != k)
        {
          arrayOfLong[k] = arrayOfLong[j];
          arrayOfObject[k] = localObject;
          arrayOfObject[j] = null;
        }
        m = k + 1;
      }
      j++;
    }
    this.mGarbage = false;
    this.mSize = k;
  }
  
  public void append(long paramLong, E paramE)
  {
    int i = this.mSize;
    if ((i != 0) && (paramLong <= this.mKeys[(i - 1)]))
    {
      put(paramLong, paramE);
      return;
    }
    if ((this.mGarbage) && (this.mSize >= this.mKeys.length)) {
      gc();
    }
    int j = this.mSize;
    if (j >= this.mKeys.length)
    {
      i = ContainerHelpers.idealLongArraySize(j + 1);
      long[] arrayOfLong = new long[i];
      Object[] arrayOfObject = new Object[i];
      Object localObject = this.mKeys;
      System.arraycopy(localObject, 0, arrayOfLong, 0, localObject.length);
      localObject = this.mValues;
      System.arraycopy(localObject, 0, arrayOfObject, 0, localObject.length);
      this.mKeys = arrayOfLong;
      this.mValues = arrayOfObject;
    }
    this.mKeys[j] = paramLong;
    this.mValues[j] = paramE;
    this.mSize = (j + 1);
  }
  
  public void clear()
  {
    int i = this.mSize;
    Object[] arrayOfObject = this.mValues;
    for (int j = 0; j < i; j++) {
      arrayOfObject[j] = null;
    }
    this.mSize = 0;
    this.mGarbage = false;
  }
  
  public LongSparseArray<E> clone()
  {
    try
    {
      LongSparseArray localLongSparseArray = (LongSparseArray)super.clone();
      LongSparseArray<E> localLongSparseArray1;
      return localLongSparseArray1;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException1)
    {
      try
      {
        localLongSparseArray.mKeys = ((long[])this.mKeys.clone());
        localLongSparseArray.mValues = ((Object[])this.mValues.clone());
      }
      catch (CloneNotSupportedException localCloneNotSupportedException2)
      {
        for (;;) {}
      }
      localCloneNotSupportedException1 = localCloneNotSupportedException1;
      localLongSparseArray1 = null;
    }
  }
  
  public void delete(long paramLong)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0)
    {
      Object[] arrayOfObject = this.mValues;
      Object localObject1 = arrayOfObject[i];
      Object localObject2 = DELETED;
      if (localObject1 != localObject2)
      {
        arrayOfObject[i] = localObject2;
        this.mGarbage = true;
      }
    }
  }
  
  public E get(long paramLong)
  {
    return (E)get(paramLong, null);
  }
  
  public E get(long paramLong, E paramE)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0)
    {
      Object[] arrayOfObject = this.mValues;
      if (arrayOfObject[i] != DELETED) {
        return (E)arrayOfObject[i];
      }
    }
    return paramE;
  }
  
  public int indexOfKey(long paramLong)
  {
    if (this.mGarbage) {
      gc();
    }
    return ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
  }
  
  public int indexOfValue(E paramE)
  {
    if (this.mGarbage) {
      gc();
    }
    for (int i = 0; i < this.mSize; i++) {
      if (this.mValues[i] == paramE) {
        return i;
      }
    }
    return -1;
  }
  
  public long keyAt(int paramInt)
  {
    if (this.mGarbage) {
      gc();
    }
    return this.mKeys[paramInt];
  }
  
  public void put(long paramLong, E paramE)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0)
    {
      this.mValues[i] = paramE;
    }
    else
    {
      int j = i ^ 0xFFFFFFFF;
      Object localObject1;
      if (j < this.mSize)
      {
        localObject1 = this.mValues;
        if (localObject1[j] == DELETED)
        {
          this.mKeys[j] = paramLong;
          localObject1[j] = paramE;
          return;
        }
      }
      i = j;
      if (this.mGarbage)
      {
        i = j;
        if (this.mSize >= this.mKeys.length)
        {
          gc();
          i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong) ^ 0xFFFFFFFF;
        }
      }
      j = this.mSize;
      if (j >= this.mKeys.length)
      {
        j = ContainerHelpers.idealLongArraySize(j + 1);
        long[] arrayOfLong = new long[j];
        localObject1 = new Object[j];
        Object localObject2 = this.mKeys;
        System.arraycopy(localObject2, 0, arrayOfLong, 0, localObject2.length);
        localObject2 = this.mValues;
        System.arraycopy(localObject2, 0, localObject1, 0, localObject2.length);
        this.mKeys = arrayOfLong;
        this.mValues = ((Object[])localObject1);
      }
      int k = this.mSize;
      if (k - i != 0)
      {
        localObject1 = this.mKeys;
        j = i + 1;
        System.arraycopy(localObject1, i, localObject1, j, k - i);
        localObject1 = this.mValues;
        System.arraycopy(localObject1, i, localObject1, j, this.mSize - i);
      }
      this.mKeys[i] = paramLong;
      this.mValues[i] = paramE;
      this.mSize += 1;
    }
  }
  
  public void remove(long paramLong)
  {
    delete(paramLong);
  }
  
  public void removeAt(int paramInt)
  {
    Object[] arrayOfObject = this.mValues;
    Object localObject1 = arrayOfObject[paramInt];
    Object localObject2 = DELETED;
    if (localObject1 != localObject2)
    {
      arrayOfObject[paramInt] = localObject2;
      this.mGarbage = true;
    }
  }
  
  public void setValueAt(int paramInt, E paramE)
  {
    if (this.mGarbage) {
      gc();
    }
    this.mValues[paramInt] = paramE;
  }
  
  public int size()
  {
    if (this.mGarbage) {
      gc();
    }
    return this.mSize;
  }
  
  public String toString()
  {
    if (size() <= 0) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(this.mSize * 28);
    localStringBuilder.append('{');
    for (int i = 0; i < this.mSize; i++)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(keyAt(i));
      localStringBuilder.append('=');
      Object localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Map)");
      }
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public E valueAt(int paramInt)
  {
    if (this.mGarbage) {
      gc();
    }
    return (E)this.mValues[paramInt];
  }
}


/* Location:              ~/android/support/v4/util/LongSparseArray.class
 *
 * Reversed by:           J
 */