package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;

class ad
{
  static final int[] a = { -16842910 };
  static final int[] b = { 16842908 };
  static final int[] c = { 16843518 };
  static final int[] d = { 16842919 };
  static final int[] e = { 16842912 };
  static final int[] f = { 16842913 };
  static final int[] g = { -16842919, -16842908 };
  static final int[] h = new int[0];
  private static final ThreadLocal<TypedValue> i = new ThreadLocal();
  private static final int[] j = new int[1];
  
  public static int a(Context paramContext, int paramInt)
  {
    Object localObject = j;
    localObject[0] = paramInt;
    localObject = TintTypedArray.obtainStyledAttributes(paramContext, null, (int[])localObject);
    try
    {
      paramInt = ((TintTypedArray)localObject).getColor(0, 0);
      return paramInt;
    }
    finally
    {
      ((TintTypedArray)localObject).recycle();
    }
  }
  
  static int a(Context paramContext, int paramInt, float paramFloat)
  {
    paramInt = a(paramContext, paramInt);
    return ColorUtils.setAlphaComponent(paramInt, Math.round(Color.alpha(paramInt) * paramFloat));
  }
  
  private static TypedValue a()
  {
    TypedValue localTypedValue1 = (TypedValue)i.get();
    TypedValue localTypedValue2 = localTypedValue1;
    if (localTypedValue1 == null)
    {
      localTypedValue2 = new TypedValue();
      i.set(localTypedValue2);
    }
    return localTypedValue2;
  }
  
  public static ColorStateList b(Context paramContext, int paramInt)
  {
    Object localObject1 = j;
    localObject1[0] = paramInt;
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, null, (int[])localObject1);
    try
    {
      localObject1 = paramContext.getColorStateList(0);
      return (ColorStateList)localObject1;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static int c(Context paramContext, int paramInt)
  {
    Object localObject = b(paramContext, paramInt);
    if ((localObject != null) && (((ColorStateList)localObject).isStateful())) {
      return ((ColorStateList)localObject).getColorForState(a, ((ColorStateList)localObject).getDefaultColor());
    }
    localObject = a();
    paramContext.getTheme().resolveAttribute(16842803, (TypedValue)localObject, true);
    return a(paramContext, paramInt, ((TypedValue)localObject).getFloat());
  }
}


/* Location:              ~/android/support/v7/widget/ad.class
 *
 * Reversed by:           J
 */