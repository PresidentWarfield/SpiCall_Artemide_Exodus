package android.support.v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E>
  implements Collection<E>, Set<E>
{
  private static final int BASE_SIZE = 4;
  private static final int CACHE_SIZE = 10;
  private static final boolean DEBUG = false;
  private static final int[] INT = new int[0];
  private static final Object[] OBJECT = new Object[0];
  private static final String TAG = "ArraySet";
  static Object[] sBaseCache;
  static int sBaseCacheSize;
  static Object[] sTwiceBaseCache;
  static int sTwiceBaseCacheSize;
  Object[] mArray;
  MapCollections<E, E> mCollections;
  int[] mHashes;
  final boolean mIdentityHashCode;
  int mSize;
  
  public ArraySet()
  {
    this(0, false);
  }
  
  public ArraySet(int paramInt)
  {
    this(paramInt, false);
  }
  
  public ArraySet(int paramInt, boolean paramBoolean)
  {
    this.mIdentityHashCode = paramBoolean;
    if (paramInt == 0)
    {
      this.mHashes = INT;
      this.mArray = OBJECT;
    }
    else
    {
      allocArrays(paramInt);
    }
    this.mSize = 0;
  }
  
  public ArraySet(ArraySet<E> paramArraySet)
  {
    this();
    if (paramArraySet != null) {
      addAll(paramArraySet);
    }
  }
  
  public ArraySet(Collection<E> paramCollection)
  {
    this();
    if (paramCollection != null) {
      addAll(paramCollection);
    }
  }
  
  private void allocArrays(int paramInt)
  {
    if (paramInt == 8) {
      try
      {
        if (sTwiceBaseCache != null)
        {
          Object[] arrayOfObject1 = sTwiceBaseCache;
          this.mArray = arrayOfObject1;
          sTwiceBaseCache = (Object[])arrayOfObject1[0];
          this.mHashes = ((int[])arrayOfObject1[1]);
          arrayOfObject1[1] = null;
          arrayOfObject1[0] = null;
          sTwiceBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    if (paramInt == 4) {
      try
      {
        if (sBaseCache != null)
        {
          Object[] arrayOfObject2 = sBaseCache;
          this.mArray = arrayOfObject2;
          sBaseCache = (Object[])arrayOfObject2[0];
          this.mHashes = ((int[])arrayOfObject2[1]);
          arrayOfObject2[1] = null;
          arrayOfObject2[0] = null;
          sBaseCacheSize -= 1;
          return;
        }
      }
      finally {}
    }
    this.mHashes = new int[paramInt];
    this.mArray = new Object[paramInt];
  }
  
  private static void freeArrays(int[] paramArrayOfInt, Object[] paramArrayOfObject, int paramInt)
  {
    if (paramArrayOfInt.length == 8) {
      try
      {
        if (sTwiceBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sTwiceBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt--;
          while (paramInt >= 2)
          {
            paramArrayOfObject[paramInt] = null;
            paramInt--;
          }
          sTwiceBaseCache = paramArrayOfObject;
          sTwiceBaseCacheSize += 1;
        }
      }
      finally {}
    }
    if (paramArrayOfInt.length == 4) {
      try
      {
        if (sBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt--;
          while (paramInt >= 2)
          {
            paramArrayOfObject[paramInt] = null;
            paramInt--;
          }
          sBaseCache = paramArrayOfObject;
          sBaseCacheSize += 1;
        }
      }
      finally {}
    }
  }
  
  private MapCollections<E, E> getCollection()
  {
    if (this.mCollections == null) {
      this.mCollections = new MapCollections()
      {
        protected void colClear()
        {
          ArraySet.this.clear();
        }
        
        protected Object colGetEntry(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return ArraySet.this.mArray[paramAnonymousInt1];
        }
        
        protected Map<E, E> colGetMap()
        {
          throw new UnsupportedOperationException("not a map");
        }
        
        protected int colGetSize()
        {
          return ArraySet.this.mSize;
        }
        
        protected int colIndexOfKey(Object paramAnonymousObject)
        {
          return ArraySet.this.indexOf(paramAnonymousObject);
        }
        
        protected int colIndexOfValue(Object paramAnonymousObject)
        {
          return ArraySet.this.indexOf(paramAnonymousObject);
        }
        
        protected void colPut(E paramAnonymousE1, E paramAnonymousE2)
        {
          ArraySet.this.add(paramAnonymousE1);
        }
        
        protected void colRemoveAt(int paramAnonymousInt)
        {
          ArraySet.this.removeAt(paramAnonymousInt);
        }
        
        protected E colSetValue(int paramAnonymousInt, E paramAnonymousE)
        {
          throw new UnsupportedOperationException("not a map");
        }
      };
    }
    return this.mCollections;
  }
  
  private int indexOf(Object paramObject, int paramInt)
  {
    int i = this.mSize;
    if (i == 0) {
      return -1;
    }
    int j = ContainerHelpers.binarySearch(this.mHashes, i, paramInt);
    if (j < 0) {
      return j;
    }
    if (paramObject.equals(this.mArray[j])) {
      return j;
    }
    for (int k = j + 1; (k < i) && (this.mHashes[k] == paramInt); k++) {
      if (paramObject.equals(this.mArray[k])) {
        return k;
      }
    }
    for (i = j - 1; (i >= 0) && (this.mHashes[i] == paramInt); i--) {
      if (paramObject.equals(this.mArray[i])) {
        return i;
      }
    }
    return k ^ 0xFFFFFFFF;
  }
  
  private int indexOfNull()
  {
    int i = this.mSize;
    if (i == 0) {
      return -1;
    }
    int j = ContainerHelpers.binarySearch(this.mHashes, i, 0);
    if (j < 0) {
      return j;
    }
    if (this.mArray[j] == null) {
      return j;
    }
    for (int k = j + 1; (k < i) && (this.mHashes[k] == 0); k++) {
      if (this.mArray[k] == null) {
        return k;
      }
    }
    j--;
    while ((j >= 0) && (this.mHashes[j] == 0))
    {
      if (this.mArray[j] == null) {
        return j;
      }
      j--;
    }
    return k ^ 0xFFFFFFFF;
  }
  
  public boolean add(E paramE)
  {
    int j;
    if (paramE == null)
    {
      i = indexOfNull();
      j = 0;
    }
    else
    {
      if (this.mIdentityHashCode) {
        i = System.identityHashCode(paramE);
      } else {
        i = paramE.hashCode();
      }
      k = indexOf(paramE, i);
      j = i;
      i = k;
    }
    if (i >= 0) {
      return false;
    }
    int k = i ^ 0xFFFFFFFF;
    int m = this.mSize;
    Object localObject;
    if (m >= this.mHashes.length)
    {
      i = 4;
      if (m >= 8) {
        i = (m >> 1) + m;
      } else if (m >= 4) {
        i = 8;
      }
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(i);
      localObject = this.mHashes;
      if (localObject.length > 0)
      {
        System.arraycopy(arrayOfInt, 0, localObject, 0, arrayOfInt.length);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, arrayOfObject.length);
      }
      freeArrays(arrayOfInt, arrayOfObject, this.mSize);
    }
    int i = this.mSize;
    if (k < i)
    {
      localObject = this.mHashes;
      m = k + 1;
      System.arraycopy(localObject, k, localObject, m, i - k);
      localObject = this.mArray;
      System.arraycopy(localObject, k, localObject, m, this.mSize - k);
    }
    this.mHashes[k] = j;
    this.mArray[k] = paramE;
    this.mSize += 1;
    return true;
  }
  
  public void addAll(ArraySet<? extends E> paramArraySet)
  {
    int i = paramArraySet.mSize;
    ensureCapacity(this.mSize + i);
    int j = this.mSize;
    int k = 0;
    if (j == 0)
    {
      if (i > 0)
      {
        System.arraycopy(paramArraySet.mHashes, 0, this.mHashes, 0, i);
        System.arraycopy(paramArraySet.mArray, 0, this.mArray, 0, i);
        this.mSize = i;
      }
    }
    else {
      while (k < i)
      {
        add(paramArraySet.valueAt(k));
        k++;
      }
    }
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    ensureCapacity(this.mSize + paramCollection.size());
    paramCollection = paramCollection.iterator();
    boolean bool = false;
    while (paramCollection.hasNext()) {
      bool |= add(paramCollection.next());
    }
    return bool;
  }
  
  public void append(E paramE)
  {
    int i = this.mSize;
    int j;
    if (paramE == null) {
      j = 0;
    } else if (this.mIdentityHashCode) {
      j = System.identityHashCode(paramE);
    } else {
      j = paramE.hashCode();
    }
    int[] arrayOfInt = this.mHashes;
    if (i < arrayOfInt.length)
    {
      if ((i > 0) && (arrayOfInt[(i - 1)] > j))
      {
        add(paramE);
        return;
      }
      this.mSize = (i + 1);
      this.mHashes[i] = j;
      this.mArray[i] = paramE;
      return;
    }
    throw new IllegalStateException("Array is full");
  }
  
  public void clear()
  {
    int i = this.mSize;
    if (i != 0)
    {
      freeArrays(this.mHashes, this.mArray, i);
      this.mHashes = INT;
      this.mArray = OBJECT;
      this.mSize = 0;
    }
  }
  
  public boolean contains(Object paramObject)
  {
    boolean bool;
    if (indexOf(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!contains(paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  public void ensureCapacity(int paramInt)
  {
    int[] arrayOfInt = this.mHashes;
    if (arrayOfInt.length < paramInt)
    {
      Object[] arrayOfObject = this.mArray;
      allocArrays(paramInt);
      paramInt = this.mSize;
      if (paramInt > 0)
      {
        System.arraycopy(arrayOfInt, 0, this.mHashes, 0, paramInt);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, this.mSize);
      }
      freeArrays(arrayOfInt, arrayOfObject, this.mSize);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject instanceof Set))
    {
      paramObject = (Set)paramObject;
      if (size() != ((Set)paramObject).size()) {
        return false;
      }
      int i = 0;
      try
      {
        while (i < this.mSize)
        {
          boolean bool = ((Set)paramObject).contains(valueAt(i));
          if (!bool) {
            return false;
          }
          i++;
        }
        return true;
      }
      catch (ClassCastException paramObject)
      {
        return false;
      }
      catch (NullPointerException paramObject)
      {
        return false;
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    int[] arrayOfInt = this.mHashes;
    int i = this.mSize;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      k += arrayOfInt[j];
      j++;
    }
    return k;
  }
  
  public int indexOf(Object paramObject)
  {
    int i;
    if (paramObject == null)
    {
      i = indexOfNull();
    }
    else
    {
      if (this.mIdentityHashCode) {
        i = System.identityHashCode(paramObject);
      } else {
        i = paramObject.hashCode();
      }
      i = indexOf(paramObject, i);
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.mSize <= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Iterator<E> iterator()
  {
    return getCollection().getKeySet().iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    int i = indexOf(paramObject);
    if (i >= 0)
    {
      removeAt(i);
      return true;
    }
    return false;
  }
  
  public boolean removeAll(ArraySet<? extends E> paramArraySet)
  {
    int i = paramArraySet.mSize;
    int j = this.mSize;
    boolean bool = false;
    for (int k = 0; k < i; k++) {
      remove(paramArraySet.valueAt(k));
    }
    if (j != this.mSize) {
      bool = true;
    }
    return bool;
  }
  
  public boolean removeAll(Collection<?> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    boolean bool = false;
    while (paramCollection.hasNext()) {
      bool |= remove(paramCollection.next());
    }
    return bool;
  }
  
  public E removeAt(int paramInt)
  {
    Object localObject = this.mArray;
    E ? = localObject[paramInt];
    int i = this.mSize;
    if (i <= 1)
    {
      freeArrays(this.mHashes, (Object[])localObject, i);
      this.mHashes = INT;
      this.mArray = OBJECT;
      this.mSize = 0;
    }
    else
    {
      localObject = this.mHashes;
      int j = localObject.length;
      int k = 8;
      if ((j > 8) && (i < localObject.length / 3))
      {
        if (i > 8) {
          k = i + (i >> 1);
        }
        int[] arrayOfInt = this.mHashes;
        localObject = this.mArray;
        allocArrays(k);
        this.mSize -= 1;
        if (paramInt > 0)
        {
          System.arraycopy(arrayOfInt, 0, this.mHashes, 0, paramInt);
          System.arraycopy(localObject, 0, this.mArray, 0, paramInt);
        }
        i = this.mSize;
        if (paramInt < i)
        {
          k = paramInt + 1;
          System.arraycopy(arrayOfInt, k, this.mHashes, paramInt, i - paramInt);
          System.arraycopy(localObject, k, this.mArray, paramInt, this.mSize - paramInt);
        }
      }
      else
      {
        this.mSize -= 1;
        i = this.mSize;
        if (paramInt < i)
        {
          localObject = this.mHashes;
          k = paramInt + 1;
          System.arraycopy(localObject, k, localObject, paramInt, i - paramInt);
          localObject = this.mArray;
          System.arraycopy(localObject, k, localObject, paramInt, this.mSize - paramInt);
        }
        this.mArray[this.mSize] = null;
      }
    }
    return ?;
  }
  
  public boolean retainAll(Collection<?> paramCollection)
  {
    int i = this.mSize - 1;
    boolean bool = false;
    while (i >= 0)
    {
      if (!paramCollection.contains(this.mArray[i]))
      {
        removeAt(i);
        bool = true;
      }
      i--;
    }
    return bool;
  }
  
  public int size()
  {
    return this.mSize;
  }
  
  public Object[] toArray()
  {
    int i = this.mSize;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(this.mArray, 0, arrayOfObject, 0, i);
    return arrayOfObject;
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Object localObject = paramArrayOfT;
    if (paramArrayOfT.length < this.mSize) {
      localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.mSize);
    }
    System.arraycopy(this.mArray, 0, localObject, 0, this.mSize);
    int i = localObject.length;
    int j = this.mSize;
    if (i > j) {
      localObject[j] = null;
    }
    return (T[])localObject;
  }
  
  public String toString()
  {
    if (isEmpty()) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(this.mSize * 14);
    localStringBuilder.append('{');
    for (int i = 0; i < this.mSize; i++)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      Object localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Set)");
      }
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public E valueAt(int paramInt)
  {
    return (E)this.mArray[paramInt];
  }
}


/* Location:              ~/android/support/v4/util/ArraySet.class
 *
 * Reversed by:           J
 */