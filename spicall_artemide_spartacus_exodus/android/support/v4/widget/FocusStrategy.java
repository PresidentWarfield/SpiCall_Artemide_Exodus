package android.support.v4.widget;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class FocusStrategy
{
  private static boolean beamBeats(int paramInt, Rect paramRect1, Rect paramRect2, Rect paramRect3)
  {
    boolean bool1 = beamsOverlap(paramInt, paramRect1, paramRect2);
    if ((!beamsOverlap(paramInt, paramRect1, paramRect3)) && (bool1))
    {
      boolean bool2 = isToDirectionOf(paramInt, paramRect1, paramRect3);
      bool1 = true;
      if (!bool2) {
        return true;
      }
      if ((paramInt != 17) && (paramInt != 66))
      {
        if (majorAxisDistance(paramInt, paramRect1, paramRect2) >= majorAxisDistanceToFarEdge(paramInt, paramRect1, paramRect3)) {
          bool1 = false;
        }
        return bool1;
      }
      return true;
    }
    return false;
  }
  
  private static boolean beamsOverlap(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt == 66) {
          break label74;
        }
        if (paramInt != 130) {
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
      }
      if ((paramRect2.right < paramRect1.left) || (paramRect2.left > paramRect1.right)) {
        bool2 = false;
      }
      return bool2;
    }
    label74:
    if ((paramRect2.bottom >= paramRect1.top) && (paramRect2.top <= paramRect1.bottom)) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    return bool2;
  }
  
  public static <L, T> T findNextFocusInAbsoluteDirection(L paramL, CollectionAdapter<L, T> paramCollectionAdapter, BoundsAdapter<T> paramBoundsAdapter, T paramT, Rect paramRect, int paramInt)
  {
    Rect localRect1 = new Rect(paramRect);
    int i = 0;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130) {
            localRect1.offset(0, -(paramRect.height() + 1));
          } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
          }
        }
        else {
          localRect1.offset(-(paramRect.width() + 1), 0);
        }
      }
      else {
        localRect1.offset(0, paramRect.height() + 1);
      }
    }
    else {
      localRect1.offset(paramRect.width() + 1, 0);
    }
    Object localObject1 = null;
    int j = paramCollectionAdapter.size(paramL);
    Rect localRect2 = new Rect();
    while (i < j)
    {
      Object localObject2 = paramCollectionAdapter.get(paramL, i);
      if (localObject2 != paramT)
      {
        paramBoundsAdapter.obtainBounds(localObject2, localRect2);
        if (isBetterCandidate(paramInt, paramRect, localRect2, localRect1))
        {
          localRect1.set(localRect2);
          localObject1 = localObject2;
        }
      }
      i++;
    }
    return (T)localObject1;
  }
  
  public static <L, T> T findNextFocusInRelativeDirection(L paramL, CollectionAdapter<L, T> paramCollectionAdapter, BoundsAdapter<T> paramBoundsAdapter, T paramT, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramCollectionAdapter.size(paramL);
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(paramCollectionAdapter.get(paramL, j));
    }
    Collections.sort(localArrayList, new SequentialComparator(paramBoolean1, paramBoundsAdapter));
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
    case 2: 
      return (T)getNextFocusable(paramT, localArrayList, paramBoolean2);
    }
    return (T)getPreviousFocusable(paramT, localArrayList, paramBoolean2);
  }
  
  private static <T> T getNextFocusable(T paramT, ArrayList<T> paramArrayList, boolean paramBoolean)
  {
    int i = paramArrayList.size();
    int j;
    if (paramT == null) {
      j = -1;
    } else {
      j = paramArrayList.lastIndexOf(paramT);
    }
    j++;
    if (j < i) {
      return (T)paramArrayList.get(j);
    }
    if ((paramBoolean) && (i > 0)) {
      return (T)paramArrayList.get(0);
    }
    return null;
  }
  
  private static <T> T getPreviousFocusable(T paramT, ArrayList<T> paramArrayList, boolean paramBoolean)
  {
    int i = paramArrayList.size();
    int j;
    if (paramT == null) {
      j = i;
    } else {
      j = paramArrayList.indexOf(paramT);
    }
    j--;
    if (j >= 0) {
      return (T)paramArrayList.get(j);
    }
    if ((paramBoolean) && (i > 0)) {
      return (T)paramArrayList.get(i - 1);
    }
    return null;
  }
  
  private static int getWeightedDistanceFor(int paramInt1, int paramInt2)
  {
    return paramInt1 * 13 * paramInt1 + paramInt2 * paramInt2;
  }
  
  private static boolean isBetterCandidate(int paramInt, Rect paramRect1, Rect paramRect2, Rect paramRect3)
  {
    boolean bool1 = isCandidate(paramRect1, paramRect2, paramInt);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if (!isCandidate(paramRect1, paramRect3, paramInt)) {
      return true;
    }
    if (beamBeats(paramInt, paramRect1, paramRect2, paramRect3)) {
      return true;
    }
    if (beamBeats(paramInt, paramRect1, paramRect3, paramRect2)) {
      return false;
    }
    if (getWeightedDistanceFor(majorAxisDistance(paramInt, paramRect1, paramRect2), minorAxisDistance(paramInt, paramRect1, paramRect2)) < getWeightedDistanceFor(majorAxisDistance(paramInt, paramRect1, paramRect3), minorAxisDistance(paramInt, paramRect1, paramRect3))) {
      bool2 = true;
    }
    return bool2;
  }
  
  private static boolean isCandidate(Rect paramRect1, Rect paramRect2, int paramInt)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool4 = true;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130)
          {
            if (((paramRect1.top >= paramRect2.top) && (paramRect1.bottom > paramRect2.top)) || (paramRect1.bottom >= paramRect2.bottom)) {
              bool4 = false;
            }
            return bool4;
          }
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        if (((paramRect1.left < paramRect2.left) || (paramRect1.right <= paramRect2.left)) && (paramRect1.right < paramRect2.right)) {
          bool4 = bool1;
        } else {
          bool4 = false;
        }
        return bool4;
      }
      if (((paramRect1.bottom > paramRect2.bottom) || (paramRect1.top >= paramRect2.bottom)) && (paramRect1.top > paramRect2.top)) {
        bool4 = bool2;
      } else {
        bool4 = false;
      }
      return bool4;
    }
    if (((paramRect1.right > paramRect2.right) || (paramRect1.left >= paramRect2.right)) && (paramRect1.left > paramRect2.left)) {
      bool4 = bool3;
    } else {
      bool4 = false;
    }
    return bool4;
  }
  
  private static boolean isToDirectionOf(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool4 = true;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130)
          {
            if (paramRect1.bottom > paramRect2.top) {
              bool4 = false;
            }
            return bool4;
          }
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        if (paramRect1.right <= paramRect2.left) {
          bool4 = bool1;
        } else {
          bool4 = false;
        }
        return bool4;
      }
      if (paramRect1.top >= paramRect2.bottom) {
        bool4 = bool2;
      } else {
        bool4 = false;
      }
      return bool4;
    }
    if (paramRect1.left >= paramRect2.right) {
      bool4 = bool3;
    } else {
      bool4 = false;
    }
    return bool4;
  }
  
  private static int majorAxisDistance(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    return Math.max(0, majorAxisDistanceRaw(paramInt, paramRect1, paramRect2));
  }
  
  private static int majorAxisDistanceRaw(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130) {
            return paramRect2.top - paramRect1.bottom;
          }
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return paramRect2.left - paramRect1.right;
      }
      return paramRect1.top - paramRect2.bottom;
    }
    return paramRect1.left - paramRect2.right;
  }
  
  private static int majorAxisDistanceToFarEdge(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    return Math.max(1, majorAxisDistanceToFarEdgeRaw(paramInt, paramRect1, paramRect2));
  }
  
  private static int majorAxisDistanceToFarEdgeRaw(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130) {
            return paramRect2.bottom - paramRect1.bottom;
          }
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return paramRect2.right - paramRect1.right;
      }
      return paramRect1.top - paramRect2.top;
    }
    return paramRect1.left - paramRect2.left;
  }
  
  private static int minorAxisDistance(int paramInt, Rect paramRect1, Rect paramRect2)
  {
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt == 66) {
          break label65;
        }
        if (paramInt != 130) {
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
      }
      return Math.abs(paramRect1.left + paramRect1.width() / 2 - (paramRect2.left + paramRect2.width() / 2));
    }
    label65:
    return Math.abs(paramRect1.top + paramRect1.height() / 2 - (paramRect2.top + paramRect2.height() / 2));
  }
  
  public static abstract interface BoundsAdapter<T>
  {
    public abstract void obtainBounds(T paramT, Rect paramRect);
  }
  
  public static abstract interface CollectionAdapter<T, V>
  {
    public abstract V get(T paramT, int paramInt);
    
    public abstract int size(T paramT);
  }
  
  private static class SequentialComparator<T>
    implements Comparator<T>
  {
    private final FocusStrategy.BoundsAdapter<T> mAdapter;
    private final boolean mIsLayoutRtl;
    private final Rect mTemp1 = new Rect();
    private final Rect mTemp2 = new Rect();
    
    SequentialComparator(boolean paramBoolean, FocusStrategy.BoundsAdapter<T> paramBoundsAdapter)
    {
      this.mIsLayoutRtl = paramBoolean;
      this.mAdapter = paramBoundsAdapter;
    }
    
    public int compare(T paramT1, T paramT2)
    {
      Rect localRect1 = this.mTemp1;
      Rect localRect2 = this.mTemp2;
      this.mAdapter.obtainBounds(paramT1, localRect1);
      this.mAdapter.obtainBounds(paramT2, localRect2);
      int i = localRect1.top;
      int j = localRect2.top;
      int k = -1;
      if (i < j) {
        return -1;
      }
      if (localRect1.top > localRect2.top) {
        return 1;
      }
      if (localRect1.left < localRect2.left)
      {
        if (this.mIsLayoutRtl) {
          k = 1;
        }
        return k;
      }
      if (localRect1.left > localRect2.left)
      {
        if (!this.mIsLayoutRtl) {
          k = 1;
        }
        return k;
      }
      if (localRect1.bottom < localRect2.bottom) {
        return -1;
      }
      if (localRect1.bottom > localRect2.bottom) {
        return 1;
      }
      if (localRect1.right < localRect2.right)
      {
        if (this.mIsLayoutRtl) {
          k = 1;
        }
        return k;
      }
      if (localRect1.right > localRect2.right)
      {
        if (!this.mIsLayoutRtl) {
          k = 1;
        }
        return k;
      }
      return 0;
    }
  }
}


/* Location:              ~/android/support/v4/widget/FocusStrategy.class
 *
 * Reversed by:           J
 */