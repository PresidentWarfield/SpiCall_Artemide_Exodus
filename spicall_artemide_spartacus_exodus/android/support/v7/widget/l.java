package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.support.v7.a.a.j;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

class l
{
  private static final RectF a = new RectF();
  private static Hashtable<String, Method> b = new Hashtable();
  private int c = 0;
  private boolean d = false;
  private float e = -1.0F;
  private float f = -1.0F;
  private float g = -1.0F;
  private int[] h = new int[0];
  private boolean i = false;
  private TextPaint j;
  private final TextView k;
  private final Context l;
  
  l(TextView paramTextView)
  {
    this.k = paramTextView;
    this.l = this.k.getContext();
  }
  
  private int a(RectF paramRectF)
  {
    int m = this.h.length;
    if (m != 0)
    {
      int n = m - 1;
      m = 1;
      int i1 = 0;
      while (m <= n)
      {
        int i2 = (m + n) / 2;
        if (a(this.h[i2], paramRectF))
        {
          i1 = m;
          m = i2 + 1;
        }
        else
        {
          i1 = i2 - 1;
          n = i1;
        }
      }
      return this.h[i1];
    }
    throw new IllegalStateException("No available text sizes to choose from.");
  }
  
  @TargetApi(14)
  private StaticLayout a(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt)
  {
    float f1;
    float f2;
    boolean bool;
    float f3;
    if (Build.VERSION.SDK_INT >= 16)
    {
      f1 = this.k.getLineSpacingMultiplier();
      f2 = this.k.getLineSpacingExtra();
      bool = this.k.getIncludeFontPadding();
      f3 = f2;
    }
    else
    {
      f2 = ((Float)a(this.k, "getLineSpacingMultiplier", Float.valueOf(1.0F))).floatValue();
      f3 = ((Float)a(this.k, "getLineSpacingExtra", Float.valueOf(0.0F))).floatValue();
      bool = ((Boolean)a(this.k, "getIncludeFontPadding", Boolean.valueOf(true))).booleanValue();
      f1 = f2;
    }
    return new StaticLayout(paramCharSequence, this.j, paramInt, paramAlignment, f1, f3, bool);
  }
  
  @TargetApi(23)
  private StaticLayout a(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2)
  {
    TextDirectionHeuristic localTextDirectionHeuristic = (TextDirectionHeuristic)a(this.k, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR);
    paramCharSequence = StaticLayout.Builder.obtain(paramCharSequence, 0, paramCharSequence.length(), this.j, paramInt1).setAlignment(paramAlignment).setLineSpacing(this.k.getLineSpacingExtra(), this.k.getLineSpacingMultiplier()).setIncludePad(this.k.getIncludeFontPadding()).setBreakStrategy(this.k.getBreakStrategy()).setHyphenationFrequency(this.k.getHyphenationFrequency());
    paramInt1 = paramInt2;
    if (paramInt2 == -1) {
      paramInt1 = Integer.MAX_VALUE;
    }
    return paramCharSequence.setMaxLines(paramInt1).setTextDirection(localTextDirectionHeuristic).build();
  }
  
  /* Error */
  private <T> T a(Object paramObject, String paramString, T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_2
    //   2: invokespecial 196	android/support/v7/widget/l:a	(Ljava/lang/String;)Ljava/lang/reflect/Method;
    //   5: aload_1
    //   6: iconst_0
    //   7: anewarray 4	java/lang/Object
    //   10: invokevirtual 202	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   13: astore_1
    //   14: aload_1
    //   15: astore_3
    //   16: goto +49 -> 65
    //   19: astore_1
    //   20: goto +47 -> 67
    //   23: astore 4
    //   25: new 204	java/lang/StringBuilder
    //   28: astore_1
    //   29: aload_1
    //   30: invokespecial 205	java/lang/StringBuilder:<init>	()V
    //   33: aload_1
    //   34: ldc -49
    //   36: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload_1
    //   41: aload_2
    //   42: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_1
    //   47: ldc -43
    //   49: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: ldc -41
    //   55: aload_1
    //   56: invokevirtual 219	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   59: aload 4
    //   61: invokestatic 225	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   64: pop
    //   65: aload_3
    //   66: areturn
    //   67: aload_1
    //   68: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	this	l
    //   0	69	1	paramObject	Object
    //   0	69	2	paramString	String
    //   0	69	3	paramT	T
    //   23	37	4	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   0	14	19	finally
    //   25	65	19	finally
    //   0	14	23	java/lang/Exception
  }
  
  private Method a(String paramString)
  {
    try
    {
      Method localMethod = (Method)b.get(paramString);
      localObject = localMethod;
      if (localMethod == null)
      {
        localMethod = TextView.class.getDeclaredMethod(paramString, new Class[0]);
        localObject = localMethod;
        if (localMethod != null)
        {
          localMethod.setAccessible(true);
          b.put(paramString, localMethod);
          localObject = localMethod;
        }
      }
      return (Method)localObject;
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Failed to retrieve TextView#");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("() method");
      Log.w("ACTVAutoSizeHelper", ((StringBuilder)localObject).toString(), localException);
    }
    return null;
  }
  
  private void a(float paramFloat)
  {
    if (paramFloat != this.k.getPaint().getTextSize())
    {
      this.k.getPaint().setTextSize(paramFloat);
      boolean bool;
      if (Build.VERSION.SDK_INT >= 18) {
        bool = this.k.isInLayout();
      } else {
        bool = false;
      }
      if (this.k.getLayout() != null)
      {
        this.d = false;
        try
        {
          Method localMethod = a("nullLayouts");
          if (localMethod != null) {
            localMethod.invoke(this.k, new Object[0]);
          }
        }
        catch (Exception localException)
        {
          Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", localException);
        }
        if (!bool) {
          this.k.requestLayout();
        } else {
          this.k.forceLayout();
        }
        this.k.invalidate();
      }
    }
  }
  
  private void a(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > 0.0F)
    {
      if (paramFloat2 > paramFloat1)
      {
        if (paramFloat3 > 0.0F)
        {
          this.c = 1;
          this.f = paramFloat1;
          this.g = paramFloat2;
          this.e = paramFloat3;
          this.i = false;
          return;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("The auto-size step granularity (");
        localStringBuilder.append(paramFloat3);
        localStringBuilder.append("px) is less or equal to (0px)");
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Maximum auto-size text size (");
      localStringBuilder.append(paramFloat2);
      localStringBuilder.append("px) is less or equal to minimum auto-size ");
      localStringBuilder.append("text size (");
      localStringBuilder.append(paramFloat1);
      localStringBuilder.append("px)");
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Minimum auto-size text size (");
    localStringBuilder.append(paramFloat1);
    localStringBuilder.append("px) is less or equal to (0px)");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  private void a(TypedArray paramTypedArray)
  {
    int m = paramTypedArray.length();
    int[] arrayOfInt = new int[m];
    if (m > 0)
    {
      for (int n = 0; n < m; n++) {
        arrayOfInt[n] = paramTypedArray.getDimensionPixelSize(n, -1);
      }
      this.h = a(arrayOfInt);
      h();
    }
  }
  
  private boolean a(int paramInt, RectF paramRectF)
  {
    CharSequence localCharSequence = this.k.getText();
    int m;
    if (Build.VERSION.SDK_INT >= 16) {
      m = this.k.getMaxLines();
    } else {
      m = -1;
    }
    Object localObject = this.j;
    if (localObject == null) {
      this.j = new TextPaint();
    } else {
      ((TextPaint)localObject).reset();
    }
    this.j.set(this.k.getPaint());
    this.j.setTextSize(paramInt);
    localObject = (Layout.Alignment)a(this.k, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL);
    if (Build.VERSION.SDK_INT >= 23) {
      localObject = a(localCharSequence, (Layout.Alignment)localObject, Math.round(paramRectF.right), m);
    } else {
      localObject = a(localCharSequence, (Layout.Alignment)localObject, Math.round(paramRectF.right));
    }
    if ((m != -1) && ((((StaticLayout)localObject).getLineCount() > m) || (((StaticLayout)localObject).getLineEnd(((StaticLayout)localObject).getLineCount() - 1) != localCharSequence.length()))) {
      return false;
    }
    return ((StaticLayout)localObject).getHeight() <= paramRectF.bottom;
  }
  
  private int[] a(int[] paramArrayOfInt)
  {
    int m = paramArrayOfInt.length;
    if (m == 0) {
      return paramArrayOfInt;
    }
    Arrays.sort(paramArrayOfInt);
    ArrayList localArrayList = new ArrayList();
    int n = 0;
    for (int i1 = 0; i1 < m; i1++)
    {
      int i2 = paramArrayOfInt[i1];
      if ((i2 > 0) && (Collections.binarySearch(localArrayList, Integer.valueOf(i2)) < 0)) {
        localArrayList.add(Integer.valueOf(i2));
      }
    }
    if (m == localArrayList.size()) {
      return paramArrayOfInt;
    }
    m = localArrayList.size();
    paramArrayOfInt = new int[m];
    for (i1 = n; i1 < m; i1++) {
      paramArrayOfInt[i1] = ((Integer)localArrayList.get(i1)).intValue();
    }
    return paramArrayOfInt;
  }
  
  private boolean h()
  {
    int m = this.h.length;
    boolean bool;
    if (m > 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.i = bool;
    if (this.i)
    {
      this.c = 1;
      int[] arrayOfInt = this.h;
      this.f = arrayOfInt[0];
      this.g = arrayOfInt[(m - 1)];
      this.e = -1.0F;
    }
    return this.i;
  }
  
  private boolean i()
  {
    boolean bool = k();
    int m = 0;
    if ((bool) && (this.c == 1))
    {
      if ((!this.i) || (this.h.length == 0))
      {
        float f1 = Math.round(this.f);
        int n = 1;
        while (Math.round(this.e + f1) <= Math.round(this.g))
        {
          n++;
          f1 += this.e;
        }
        int[] arrayOfInt = new int[n];
        f1 = this.f;
        while (m < n)
        {
          arrayOfInt[m] = Math.round(f1);
          f1 += this.e;
          m++;
        }
        this.h = a(arrayOfInt);
      }
      this.d = true;
    }
    else
    {
      this.d = false;
    }
    return this.d;
  }
  
  private void j()
  {
    this.c = 0;
    this.f = -1.0F;
    this.g = -1.0F;
    this.e = -1.0F;
    this.h = new int[0];
    this.d = false;
  }
  
  private boolean k()
  {
    boolean bool;
    if (!(this.k instanceof AppCompatEditText)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int a()
  {
    return this.c;
  }
  
  void a(int paramInt)
  {
    if (k())
    {
      Object localObject;
      switch (paramInt)
      {
      default: 
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unknown auto-size text type: ");
        ((StringBuilder)localObject).append(paramInt);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      case 1: 
        localObject = this.l.getResources().getDisplayMetrics();
        a(TypedValue.applyDimension(2, 12.0F, (DisplayMetrics)localObject), TypedValue.applyDimension(2, 112.0F, (DisplayMetrics)localObject), 1.0F);
        if (i()) {
          f();
        }
        break;
      case 0: 
        j();
      }
    }
  }
  
  void a(int paramInt, float paramFloat)
  {
    Object localObject = this.l;
    if (localObject == null) {
      localObject = Resources.getSystem();
    } else {
      localObject = ((Context)localObject).getResources();
    }
    a(TypedValue.applyDimension(paramInt, paramFloat, ((Resources)localObject).getDisplayMetrics()));
  }
  
  void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (k())
    {
      DisplayMetrics localDisplayMetrics = this.l.getResources().getDisplayMetrics();
      a(TypedValue.applyDimension(paramInt4, paramInt1, localDisplayMetrics), TypedValue.applyDimension(paramInt4, paramInt2, localDisplayMetrics), TypedValue.applyDimension(paramInt4, paramInt3, localDisplayMetrics));
      if (i()) {
        f();
      }
    }
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = this.l.obtainStyledAttributes(paramAttributeSet, a.j.AppCompatTextView, paramInt, 0);
    if (localTypedArray.hasValue(a.j.AppCompatTextView_autoSizeTextType)) {
      this.c = localTypedArray.getInt(a.j.AppCompatTextView_autoSizeTextType, 0);
    }
    float f1;
    if (localTypedArray.hasValue(a.j.AppCompatTextView_autoSizeStepGranularity)) {
      f1 = localTypedArray.getDimension(a.j.AppCompatTextView_autoSizeStepGranularity, -1.0F);
    } else {
      f1 = -1.0F;
    }
    float f2;
    if (localTypedArray.hasValue(a.j.AppCompatTextView_autoSizeMinTextSize)) {
      f2 = localTypedArray.getDimension(a.j.AppCompatTextView_autoSizeMinTextSize, -1.0F);
    } else {
      f2 = -1.0F;
    }
    float f3;
    if (localTypedArray.hasValue(a.j.AppCompatTextView_autoSizeMaxTextSize)) {
      f3 = localTypedArray.getDimension(a.j.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
    } else {
      f3 = -1.0F;
    }
    if (localTypedArray.hasValue(a.j.AppCompatTextView_autoSizePresetSizes))
    {
      paramInt = localTypedArray.getResourceId(a.j.AppCompatTextView_autoSizePresetSizes, 0);
      if (paramInt > 0)
      {
        paramAttributeSet = localTypedArray.getResources().obtainTypedArray(paramInt);
        a(paramAttributeSet);
        paramAttributeSet.recycle();
      }
    }
    localTypedArray.recycle();
    if (k())
    {
      if (this.c == 1)
      {
        if (!this.i)
        {
          paramAttributeSet = this.l.getResources().getDisplayMetrics();
          float f4 = f2;
          if (f2 == -1.0F) {
            f4 = TypedValue.applyDimension(2, 12.0F, paramAttributeSet);
          }
          f2 = f3;
          if (f3 == -1.0F) {
            f2 = TypedValue.applyDimension(2, 112.0F, paramAttributeSet);
          }
          f3 = f1;
          if (f1 == -1.0F) {
            f3 = 1.0F;
          }
          a(f4, f2, f3);
        }
        i();
      }
    }
    else {
      this.c = 0;
    }
  }
  
  void a(int[] paramArrayOfInt, int paramInt)
  {
    if (k())
    {
      int m = paramArrayOfInt.length;
      int n = 0;
      if (m > 0)
      {
        int[] arrayOfInt = new int[m];
        Object localObject;
        if (paramInt == 0)
        {
          localObject = Arrays.copyOf(paramArrayOfInt, m);
        }
        else
        {
          DisplayMetrics localDisplayMetrics = this.l.getResources().getDisplayMetrics();
          for (;;)
          {
            localObject = arrayOfInt;
            if (n >= m) {
              break;
            }
            arrayOfInt[n] = Math.round(TypedValue.applyDimension(paramInt, paramArrayOfInt[n], localDisplayMetrics));
            n++;
          }
        }
        this.h = a((int[])localObject);
        if (!h())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("None of the preset sizes is valid: ");
          ((StringBuilder)localObject).append(Arrays.toString(paramArrayOfInt));
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        this.i = false;
      }
      if (i()) {
        f();
      }
    }
  }
  
  int b()
  {
    return Math.round(this.e);
  }
  
  int c()
  {
    return Math.round(this.f);
  }
  
  int d()
  {
    return Math.round(this.g);
  }
  
  int[] e()
  {
    return this.h;
  }
  
  void f()
  {
    if (!g()) {
      return;
    }
    if (this.d)
    {
      if ((this.k.getMeasuredHeight() > 0) && (this.k.getMeasuredWidth() > 0))
      {
        int m;
        if (((Boolean)a(this.k, "getHorizontallyScrolling", Boolean.valueOf(false))).booleanValue()) {
          m = 1048576;
        } else {
          m = this.k.getMeasuredWidth() - this.k.getTotalPaddingLeft() - this.k.getTotalPaddingRight();
        }
        int n = this.k.getHeight() - this.k.getCompoundPaddingBottom() - this.k.getCompoundPaddingTop();
        if ((m > 0) && (n > 0)) {
          synchronized (a)
          {
            a.setEmpty();
            a.right = m;
            a.bottom = n;
            float f1 = a(a);
            if (f1 != this.k.getTextSize()) {
              a(0, f1);
            }
          }
        }
        return;
      }
      return;
    }
    this.d = true;
  }
  
  boolean g()
  {
    boolean bool;
    if ((k()) && (this.c != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v7/widget/l.class
 *
 * Reversed by:           J
 */