package android.support.v7.g;

import java.util.Comparator;

public class a<T>
{
  public static abstract class a<T2>
    implements Comparator<T2>
  {
    public abstract boolean areContentsTheSame(T2 paramT21, T2 paramT22);
    
    public abstract boolean areItemsTheSame(T2 paramT21, T2 paramT22);
    
    public abstract int compare(T2 paramT21, T2 paramT22);
    
    public abstract void onChanged(int paramInt1, int paramInt2);
    
    public void onChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      onChanged(paramInt1, paramInt2);
    }
  }
}


/* Location:              ~/android/support/v7/g/a.class
 *
 * Reversed by:           J
 */