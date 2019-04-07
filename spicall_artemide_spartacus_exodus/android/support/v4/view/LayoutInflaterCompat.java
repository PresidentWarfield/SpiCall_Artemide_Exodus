package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat
{
  static final LayoutInflaterCompatBaseImpl IMPL;
  private static final String TAG = "LayoutInflaterCompatHC";
  private static boolean sCheckedField;
  private static Field sLayoutInflaterFactory2Field;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new LayoutInflaterCompatApi21Impl();
    } else {
      IMPL = new LayoutInflaterCompatBaseImpl();
    }
  }
  
  static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    if (!sCheckedField)
    {
      try
      {
        sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
        sLayoutInflaterFactory2Field.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("forceSetFactory2 Could not find field 'mFactory2' on class ");
        ((StringBuilder)localObject).append(LayoutInflater.class.getName());
        ((StringBuilder)localObject).append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", ((StringBuilder)localObject).toString(), localNoSuchFieldException);
      }
      sCheckedField = true;
    }
    Object localObject = sLayoutInflaterFactory2Field;
    if (localObject != null) {
      try
      {
        ((Field)localObject).set(paramLayoutInflater, paramFactory2);
      }
      catch (IllegalAccessException paramFactory2)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("forceSetFactory2 could not set the Factory2 on LayoutInflater ");
        ((StringBuilder)localObject).append(paramLayoutInflater);
        ((StringBuilder)localObject).append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", ((StringBuilder)localObject).toString(), paramFactory2);
      }
    }
  }
  
  @Deprecated
  public static LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater)
  {
    return IMPL.getFactory(paramLayoutInflater);
  }
  
  @Deprecated
  public static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
  {
    IMPL.setFactory(paramLayoutInflater, paramLayoutInflaterFactory);
  }
  
  public static void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    IMPL.setFactory2(paramLayoutInflater, paramFactory2);
  }
  
  static class Factory2Wrapper
    implements LayoutInflater.Factory2
  {
    final LayoutInflaterFactory mDelegateFactory;
    
    Factory2Wrapper(LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      this.mDelegateFactory = paramLayoutInflaterFactory;
    }
    
    public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    }
    
    public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(null, paramString, paramContext, paramAttributeSet);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getClass().getName());
      localStringBuilder.append("{");
      localStringBuilder.append(this.mDelegateFactory);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
  
  static class LayoutInflaterCompatApi21Impl
    extends LayoutInflaterCompat.LayoutInflaterCompatBaseImpl
  {
    public void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      if (paramLayoutInflaterFactory != null) {
        paramLayoutInflaterFactory = new LayoutInflaterCompat.Factory2Wrapper(paramLayoutInflaterFactory);
      } else {
        paramLayoutInflaterFactory = null;
      }
      paramLayoutInflater.setFactory2(paramLayoutInflaterFactory);
    }
    
    public void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
    {
      paramLayoutInflater.setFactory2(paramFactory2);
    }
  }
  
  static class LayoutInflaterCompatBaseImpl
  {
    public LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater)
    {
      paramLayoutInflater = paramLayoutInflater.getFactory();
      if ((paramLayoutInflater instanceof LayoutInflaterCompat.Factory2Wrapper)) {
        return ((LayoutInflaterCompat.Factory2Wrapper)paramLayoutInflater).mDelegateFactory;
      }
      return null;
    }
    
    public void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      if (paramLayoutInflaterFactory != null) {
        paramLayoutInflaterFactory = new LayoutInflaterCompat.Factory2Wrapper(paramLayoutInflaterFactory);
      } else {
        paramLayoutInflaterFactory = null;
      }
      setFactory2(paramLayoutInflater, paramLayoutInflaterFactory);
    }
    
    public void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
    {
      paramLayoutInflater.setFactory2(paramFactory2);
      LayoutInflater.Factory localFactory = paramLayoutInflater.getFactory();
      if ((localFactory instanceof LayoutInflater.Factory2)) {
        LayoutInflaterCompat.forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localFactory);
      } else {
        LayoutInflaterCompat.forceSetFactory2(paramLayoutInflater, paramFactory2);
      }
    }
  }
}


/* Location:              ~/android/support/v4/view/LayoutInflaterCompat.class
 *
 * Reversed by:           J
 */