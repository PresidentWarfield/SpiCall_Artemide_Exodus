package android.support.design.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

public class e
  extends SparseArray<Parcelable>
  implements Parcelable
{
  public static final Parcelable.Creator<e> CREATOR = new Parcelable.ClassLoaderCreator()
  {
    public e a(Parcel paramAnonymousParcel)
    {
      return new e(paramAnonymousParcel, null);
    }
    
    public e a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
    {
      return new e(paramAnonymousParcel, paramAnonymousClassLoader);
    }
    
    public e[] a(int paramAnonymousInt)
    {
      return new e[paramAnonymousInt];
    }
  };
  
  public e() {}
  
  public e(Parcel paramParcel, ClassLoader paramClassLoader)
  {
    int i = paramParcel.readInt();
    int[] arrayOfInt = new int[i];
    paramParcel.readIntArray(arrayOfInt);
    paramParcel = paramParcel.readParcelableArray(paramClassLoader);
    for (int j = 0; j < i; j++) {
      put(arrayOfInt[j], paramParcel[j]);
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = size();
    int[] arrayOfInt = new int[i];
    Parcelable[] arrayOfParcelable = new Parcelable[i];
    for (int j = 0; j < i; j++)
    {
      arrayOfInt[j] = keyAt(j);
      arrayOfParcelable[j] = ((Parcelable)valueAt(j));
    }
    paramParcel.writeInt(i);
    paramParcel.writeIntArray(arrayOfInt);
    paramParcel.writeParcelableArray(arrayOfParcelable, paramInt);
  }
}


/* Location:              ~/android/support/design/internal/e.class
 *
 * Reversed by:           J
 */