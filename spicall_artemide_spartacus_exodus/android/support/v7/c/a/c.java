package android.support.v7.c.a;

import java.lang.reflect.Array;

final class c
{
  public static int a(int paramInt)
  {
    if (paramInt <= 4) {
      paramInt = 8;
    } else {
      paramInt *= 2;
    }
    return paramInt;
  }
  
  public static int[] a(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    if ((!a) && (paramInt1 > paramArrayOfInt.length)) {
      throw new AssertionError();
    }
    int[] arrayOfInt = paramArrayOfInt;
    if (paramInt1 + 1 > paramArrayOfInt.length)
    {
      arrayOfInt = new int[a(paramInt1)];
      System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt1);
    }
    arrayOfInt[paramInt1] = paramInt2;
    return arrayOfInt;
  }
  
  public static <T> T[] a(T[] paramArrayOfT, int paramInt, T paramT)
  {
    if ((!a) && (paramInt > paramArrayOfT.length)) {
      throw new AssertionError();
    }
    Object localObject = paramArrayOfT;
    if (paramInt + 1 > paramArrayOfT.length)
    {
      localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), a(paramInt));
      System.arraycopy(paramArrayOfT, 0, localObject, 0, paramInt);
    }
    localObject[paramInt] = paramT;
    return (T[])localObject;
  }
}


/* Location:              ~/android/support/v7/c/a/c.class
 *
 * Reversed by:           J
 */