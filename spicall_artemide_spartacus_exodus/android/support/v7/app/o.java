package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.j;
import android.support.v7.view.d;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

class o
{
  private static final Class<?>[] a = { Context.class, AttributeSet.class };
  private static final int[] b = { 16843375 };
  private static final String[] c = { "android.widget.", "android.view.", "android.webkit." };
  private static final Map<String, Constructor<? extends View>> d = new ArrayMap();
  private final Object[] e = new Object[2];
  
  private static Context a(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.View, 0, 0);
    int i;
    if (paramBoolean1) {
      i = paramAttributeSet.getResourceId(a.j.View_android_theme, 0);
    } else {
      i = 0;
    }
    int j = i;
    if (paramBoolean2)
    {
      j = i;
      if (i == 0)
      {
        i = paramAttributeSet.getResourceId(a.j.View_theme, 0);
        j = i;
        if (i != 0)
        {
          Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
          j = i;
        }
      }
    }
    paramAttributeSet.recycle();
    paramAttributeSet = paramContext;
    if (j != 0) {
      if ((paramContext instanceof d))
      {
        paramAttributeSet = paramContext;
        if (((d)paramContext).a() == j) {}
      }
      else
      {
        paramAttributeSet = new d(paramContext, j);
      }
    }
    return paramAttributeSet;
  }
  
  /* Error */
  private View a(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    // Byte code:
    //   0: aload_2
    //   1: astore 4
    //   3: aload_2
    //   4: ldc 101
    //   6: invokevirtual 105	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   9: ifeq +14 -> 23
    //   12: aload_3
    //   13: aconst_null
    //   14: ldc 107
    //   16: invokeinterface 111 3 0
    //   21: astore 4
    //   23: aload_0
    //   24: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   27: iconst_0
    //   28: aload_1
    //   29: aastore
    //   30: aload_0
    //   31: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   34: iconst_1
    //   35: aload_3
    //   36: aastore
    //   37: iconst_m1
    //   38: aload 4
    //   40: bipush 46
    //   42: invokevirtual 115	java/lang/String:indexOf	(I)I
    //   45: if_icmpne +69 -> 114
    //   48: iconst_0
    //   49: istore 5
    //   51: iload 5
    //   53: getstatic 41	android/support/v7/app/o:c	[Ljava/lang/String;
    //   56: arraylength
    //   57: if_icmpge +42 -> 99
    //   60: aload_0
    //   61: aload_1
    //   62: aload 4
    //   64: getstatic 41	android/support/v7/app/o:c	[Ljava/lang/String;
    //   67: iload 5
    //   69: aaload
    //   70: invokespecial 118	android/support/v7/app/o:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   73: astore_2
    //   74: aload_2
    //   75: ifnull +18 -> 93
    //   78: aload_0
    //   79: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   82: astore_1
    //   83: aload_1
    //   84: iconst_0
    //   85: aconst_null
    //   86: aastore
    //   87: aload_1
    //   88: iconst_1
    //   89: aconst_null
    //   90: aastore
    //   91: aload_2
    //   92: areturn
    //   93: iinc 5 1
    //   96: goto -45 -> 51
    //   99: aload_0
    //   100: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   103: astore_1
    //   104: aload_1
    //   105: iconst_0
    //   106: aconst_null
    //   107: aastore
    //   108: aload_1
    //   109: iconst_1
    //   110: aconst_null
    //   111: aastore
    //   112: aconst_null
    //   113: areturn
    //   114: aload_0
    //   115: aload_1
    //   116: aload 4
    //   118: aconst_null
    //   119: invokespecial 118	android/support/v7/app/o:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   122: astore_1
    //   123: aload_0
    //   124: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   127: astore_2
    //   128: aload_2
    //   129: iconst_0
    //   130: aconst_null
    //   131: aastore
    //   132: aload_2
    //   133: iconst_1
    //   134: aconst_null
    //   135: aastore
    //   136: aload_1
    //   137: areturn
    //   138: astore_2
    //   139: aload_0
    //   140: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   143: astore_1
    //   144: aload_1
    //   145: iconst_0
    //   146: aconst_null
    //   147: aastore
    //   148: aload_1
    //   149: iconst_1
    //   150: aconst_null
    //   151: aastore
    //   152: aload_2
    //   153: athrow
    //   154: astore_1
    //   155: aload_0
    //   156: getfield 52	android/support/v7/app/o:e	[Ljava/lang/Object;
    //   159: astore_1
    //   160: aload_1
    //   161: iconst_0
    //   162: aconst_null
    //   163: aastore
    //   164: aload_1
    //   165: iconst_1
    //   166: aconst_null
    //   167: aastore
    //   168: aconst_null
    //   169: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	170	0	this	o
    //   0	170	1	paramContext	Context
    //   0	170	2	paramString	String
    //   0	170	3	paramAttributeSet	AttributeSet
    //   1	116	4	str	String
    //   49	45	5	i	int
    // Exception table:
    //   from	to	target	type
    //   23	48	138	finally
    //   51	74	138	finally
    //   114	123	138	finally
    //   23	48	154	java/lang/Exception
    //   51	74	154	java/lang/Exception
    //   114	123	154	java/lang/Exception
  }
  
  private View a(Context paramContext, String paramString1, String paramString2)
  {
    Constructor localConstructor = (Constructor)d.get(paramString1);
    Object localObject = localConstructor;
    if (localConstructor == null) {}
    try
    {
      localObject = paramContext.getClassLoader();
      if (paramString2 != null)
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(paramString2);
        paramContext.append(paramString1);
        paramContext = paramContext.toString();
      }
      else
      {
        paramContext = paramString1;
      }
      localObject = ((ClassLoader)localObject).loadClass(paramContext).asSubclass(View.class).getConstructor(a);
      d.put(paramString1, localObject);
      ((Constructor)localObject).setAccessible(true);
      paramContext = (View)((Constructor)localObject).newInstance(this.e);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  private void a(View paramView, AttributeSet paramAttributeSet)
  {
    Object localObject = paramView.getContext();
    if (((localObject instanceof ContextWrapper)) && ((Build.VERSION.SDK_INT < 15) || (ViewCompat.hasOnClickListeners(paramView))))
    {
      paramAttributeSet = ((Context)localObject).obtainStyledAttributes(paramAttributeSet, b);
      localObject = paramAttributeSet.getString(0);
      if (localObject != null) {
        paramView.setOnClickListener(new a(paramView, (String)localObject));
      }
      paramAttributeSet.recycle();
      return;
    }
  }
  
  public final View a(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if ((paramBoolean1) && (paramView != null)) {
      localObject = paramView.getContext();
    } else {
      localObject = paramContext;
    }
    if (!paramBoolean2)
    {
      paramView = (View)localObject;
      if (!paramBoolean3) {}
    }
    else
    {
      paramView = a((Context)localObject, paramAttributeSet, paramBoolean2, paramBoolean3);
    }
    Object localObject = paramView;
    if (paramBoolean4) {
      localObject = TintContextWrapper.wrap(paramView);
    }
    paramView = null;
    int i = -1;
    switch (paramString.hashCode())
    {
    default: 
      break;
    case 2001146706: 
      if (paramString.equals("Button")) {
        i = 2;
      }
      break;
    case 1666676343: 
      if (paramString.equals("EditText")) {
        i = 3;
      }
      break;
    case 1601505219: 
      if (paramString.equals("CheckBox")) {
        i = 6;
      }
      break;
    case 1413872058: 
      if (paramString.equals("AutoCompleteTextView")) {
        i = 9;
      }
      break;
    case 1125864064: 
      if (paramString.equals("ImageView")) {
        i = 1;
      }
      break;
    case 776382189: 
      if (paramString.equals("RadioButton")) {
        i = 7;
      }
      break;
    case -339785223: 
      if (paramString.equals("Spinner")) {
        i = 4;
      }
      break;
    case -658531749: 
      if (paramString.equals("SeekBar")) {
        i = 12;
      }
      break;
    case -937446323: 
      if (paramString.equals("ImageButton")) {
        i = 5;
      }
      break;
    case -938935918: 
      if (paramString.equals("TextView")) {
        i = 0;
      }
      break;
    case -1346021293: 
      if (paramString.equals("MultiAutoCompleteTextView")) {
        i = 10;
      }
      break;
    case -1455429095: 
      if (paramString.equals("CheckedTextView")) {
        i = 8;
      }
      break;
    case -1946472170: 
      if (paramString.equals("RatingBar")) {
        i = 11;
      }
      break;
    }
    switch (i)
    {
    default: 
      break;
    case 12: 
      paramView = new AppCompatSeekBar((Context)localObject, paramAttributeSet);
      break;
    case 11: 
      paramView = new AppCompatRatingBar((Context)localObject, paramAttributeSet);
      break;
    case 10: 
      paramView = new AppCompatMultiAutoCompleteTextView((Context)localObject, paramAttributeSet);
      break;
    case 9: 
      paramView = new AppCompatAutoCompleteTextView((Context)localObject, paramAttributeSet);
      break;
    case 8: 
      paramView = new AppCompatCheckedTextView((Context)localObject, paramAttributeSet);
      break;
    case 7: 
      paramView = new AppCompatRadioButton((Context)localObject, paramAttributeSet);
      break;
    case 6: 
      paramView = new AppCompatCheckBox((Context)localObject, paramAttributeSet);
      break;
    case 5: 
      paramView = new AppCompatImageButton((Context)localObject, paramAttributeSet);
      break;
    case 4: 
      paramView = new AppCompatSpinner((Context)localObject, paramAttributeSet);
      break;
    case 3: 
      paramView = new AppCompatEditText((Context)localObject, paramAttributeSet);
      break;
    case 2: 
      paramView = new AppCompatButton((Context)localObject, paramAttributeSet);
      break;
    case 1: 
      paramView = new AppCompatImageView((Context)localObject, paramAttributeSet);
      break;
    case 0: 
      paramView = new AppCompatTextView((Context)localObject, paramAttributeSet);
    }
    View localView = paramView;
    if (paramView == null)
    {
      localView = paramView;
      if (paramContext != localObject) {
        localView = a((Context)localObject, paramString, paramAttributeSet);
      }
    }
    if (localView != null) {
      a(localView, paramAttributeSet);
    }
    return localView;
  }
  
  private static class a
    implements View.OnClickListener
  {
    private final View a;
    private final String b;
    private Method c;
    private Context d;
    
    public a(View paramView, String paramString)
    {
      this.a = paramView;
      this.b = paramString;
    }
    
    private void a(Context paramContext, String paramString)
    {
      while (paramContext != null)
      {
        try
        {
          if (!paramContext.isRestricted())
          {
            paramString = paramContext.getClass().getMethod(this.b, new Class[] { View.class });
            if (paramString != null)
            {
              this.c = paramString;
              this.d = paramContext;
              return;
            }
          }
        }
        catch (NoSuchMethodException paramString)
        {
          int i;
          for (;;) {}
        }
        if ((paramContext instanceof ContextWrapper)) {
          paramContext = ((ContextWrapper)paramContext).getBaseContext();
        } else {
          paramContext = null;
        }
      }
      i = this.a.getId();
      if (i == -1)
      {
        paramContext = "";
      }
      else
      {
        paramContext = new StringBuilder();
        paramContext.append(" with id '");
        paramContext.append(this.a.getContext().getResources().getResourceEntryName(i));
        paramContext.append("'");
        paramContext = paramContext.toString();
      }
      paramString = new StringBuilder();
      paramString.append("Could not find method ");
      paramString.append(this.b);
      paramString.append("(View) in a parent or ancestor Context for android:onClick ");
      paramString.append("attribute defined on view ");
      paramString.append(this.a.getClass());
      paramString.append(paramContext);
      throw new IllegalStateException(paramString.toString());
    }
    
    public void onClick(View paramView)
    {
      if (this.c == null) {
        a(this.a.getContext(), this.b);
      }
      try
      {
        this.c.invoke(this.d, new Object[] { paramView });
        return;
      }
      catch (InvocationTargetException paramView)
      {
        throw new IllegalStateException("Could not execute method for android:onClick", paramView);
      }
      catch (IllegalAccessException paramView)
      {
        throw new IllegalStateException("Could not execute non-public method for android:onClick", paramView);
      }
    }
  }
}


/* Location:              ~/android/support/v7/app/o.class
 *
 * Reversed by:           J
 */