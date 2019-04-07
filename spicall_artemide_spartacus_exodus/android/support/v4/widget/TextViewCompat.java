package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public final class TextViewCompat
{
  public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
  public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
  static final TextViewCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 26) {
      IMPL = new TextViewCompatApi26Impl();
    } else if (Build.VERSION.SDK_INT >= 23) {
      IMPL = new TextViewCompatApi23Impl();
    } else if (Build.VERSION.SDK_INT >= 18) {
      IMPL = new TextViewCompatApi18Impl();
    } else if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new TextViewCompatApi17Impl();
    } else if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new TextViewCompatApi16Impl();
    } else {
      IMPL = new TextViewCompatBaseImpl();
    }
  }
  
  public static int getAutoSizeMaxTextSize(TextView paramTextView)
  {
    return IMPL.getAutoSizeMaxTextSize(paramTextView);
  }
  
  public static int getAutoSizeMinTextSize(TextView paramTextView)
  {
    return IMPL.getAutoSizeMinTextSize(paramTextView);
  }
  
  public static int getAutoSizeStepGranularity(TextView paramTextView)
  {
    return IMPL.getAutoSizeStepGranularity(paramTextView);
  }
  
  public static int[] getAutoSizeTextAvailableSizes(TextView paramTextView)
  {
    return IMPL.getAutoSizeTextAvailableSizes(paramTextView);
  }
  
  public static int getAutoSizeTextType(TextView paramTextView)
  {
    return IMPL.getAutoSizeTextType(paramTextView);
  }
  
  public static Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
  {
    return IMPL.getCompoundDrawablesRelative(paramTextView);
  }
  
  public static int getMaxLines(TextView paramTextView)
  {
    return IMPL.getMaxLines(paramTextView);
  }
  
  public static int getMinLines(TextView paramTextView)
  {
    return IMPL.getMinLines(paramTextView);
  }
  
  public static void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setAutoSizeTextTypeUniformWithConfiguration(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
  {
    IMPL.setAutoSizeTextTypeUniformWithPresetSizes(paramTextView, paramArrayOfInt, paramInt);
  }
  
  public static void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt)
  {
    IMPL.setAutoSizeTextTypeWithDefaults(paramTextView, paramInt);
  }
  
  public static void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    IMPL.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setTextAppearance(TextView paramTextView, int paramInt)
  {
    IMPL.setTextAppearance(paramTextView, paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AutoSizeTextType {}
  
  static class TextViewCompatApi16Impl
    extends TextViewCompat.TextViewCompatBaseImpl
  {
    public int getMaxLines(TextView paramTextView)
    {
      return paramTextView.getMaxLines();
    }
    
    public int getMinLines(TextView paramTextView)
    {
      return paramTextView.getMinLines();
    }
  }
  
  static class TextViewCompatApi17Impl
    extends TextViewCompat.TextViewCompatApi16Impl
  {
    public Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
    {
      int i = paramTextView.getLayoutDirection();
      int j = 1;
      if (i != 1) {
        j = 0;
      }
      Drawable[] arrayOfDrawable = paramTextView.getCompoundDrawables();
      if (j != 0)
      {
        Drawable localDrawable = arrayOfDrawable[2];
        paramTextView = arrayOfDrawable[0];
        arrayOfDrawable[0] = localDrawable;
        arrayOfDrawable[2] = paramTextView;
      }
      return arrayOfDrawable;
    }
    
    public void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      int i = paramTextView.getLayoutDirection();
      int j = 1;
      if (i != 1) {
        j = 0;
      }
      Drawable localDrawable;
      if (j != 0) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (j == 0) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawables(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int i = paramTextView.getLayoutDirection();
      int j = 1;
      if (i != 1) {
        j = 0;
      }
      if (j != 0) {
        i = paramInt3;
      } else {
        i = paramInt1;
      }
      if (j == 0) {
        paramInt1 = paramInt3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(i, paramInt2, paramInt1, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      int i = paramTextView.getLayoutDirection();
      int j = 1;
      if (i != 1) {
        j = 0;
      }
      Drawable localDrawable;
      if (j != 0) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (j == 0) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
    }
  }
  
  static class TextViewCompatApi18Impl
    extends TextViewCompat.TextViewCompatApi17Impl
  {
    public Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawablesRelative();
    }
    
    public void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelative(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  static class TextViewCompatApi23Impl
    extends TextViewCompat.TextViewCompatApi18Impl
  {
    public void setTextAppearance(TextView paramTextView, int paramInt)
    {
      paramTextView.setTextAppearance(paramInt);
    }
  }
  
  static class TextViewCompatApi26Impl
    extends TextViewCompat.TextViewCompatApi23Impl
  {
    public int getAutoSizeMaxTextSize(TextView paramTextView)
    {
      return paramTextView.getAutoSizeMaxTextSize();
    }
    
    public int getAutoSizeMinTextSize(TextView paramTextView)
    {
      return paramTextView.getAutoSizeMinTextSize();
    }
    
    public int getAutoSizeStepGranularity(TextView paramTextView)
    {
      return paramTextView.getAutoSizeStepGranularity();
    }
    
    public int[] getAutoSizeTextAvailableSizes(TextView paramTextView)
    {
      return paramTextView.getAutoSizeTextAvailableSizes();
    }
    
    public int getAutoSizeTextType(TextView paramTextView)
    {
      return paramTextView.getAutoSizeTextType();
    }
    
    public void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
    {
      paramTextView.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
    
    public void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt)
    {
      paramTextView.setAutoSizeTextTypeWithDefaults(paramInt);
    }
  }
  
  static class TextViewCompatBaseImpl
  {
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompatBase";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;
    
    private static Field retrieveField(String paramString)
    {
      Object localObject = null;
      try
      {
        Field localField = TextView.class.getDeclaredField(paramString);
        localObject = localField;
        localField.setAccessible(true);
        localObject = localField;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Could not retrieve ");
        localStringBuilder.append(paramString);
        localStringBuilder.append(" field.");
        Log.e("TextViewCompatBase", localStringBuilder.toString());
      }
      return (Field)localObject;
    }
    
    private static int retrieveIntFromField(Field paramField, TextView paramTextView)
    {
      try
      {
        int i = paramField.getInt(paramTextView);
        return i;
      }
      catch (IllegalAccessException paramTextView)
      {
        paramTextView = new StringBuilder();
        paramTextView.append("Could not retrieve value of ");
        paramTextView.append(paramField.getName());
        paramTextView.append(" field.");
        Log.d("TextViewCompatBase", paramTextView.toString());
      }
      return -1;
    }
    
    public int getAutoSizeMaxTextSize(TextView paramTextView)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        return ((AutoSizeableTextView)paramTextView).getAutoSizeMaxTextSize();
      }
      return -1;
    }
    
    public int getAutoSizeMinTextSize(TextView paramTextView)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        return ((AutoSizeableTextView)paramTextView).getAutoSizeMinTextSize();
      }
      return -1;
    }
    
    public int getAutoSizeStepGranularity(TextView paramTextView)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        return ((AutoSizeableTextView)paramTextView).getAutoSizeStepGranularity();
      }
      return -1;
    }
    
    public int[] getAutoSizeTextAvailableSizes(TextView paramTextView)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        return ((AutoSizeableTextView)paramTextView).getAutoSizeTextAvailableSizes();
      }
      return new int[0];
    }
    
    public int getAutoSizeTextType(TextView paramTextView)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        return ((AutoSizeableTextView)paramTextView).getAutoSizeTextType();
      }
      return 0;
    }
    
    public Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawables();
    }
    
    public int getMaxLines(TextView paramTextView)
    {
      if (!sMaxModeFieldFetched)
      {
        sMaxModeField = retrieveField("mMaxMode");
        sMaxModeFieldFetched = true;
      }
      Field localField = sMaxModeField;
      if ((localField != null) && (retrieveIntFromField(localField, paramTextView) == 1))
      {
        if (!sMaximumFieldFetched)
        {
          sMaximumField = retrieveField("mMaximum");
          sMaximumFieldFetched = true;
        }
        localField = sMaximumField;
        if (localField != null) {
          return retrieveIntFromField(localField, paramTextView);
        }
      }
      return -1;
    }
    
    public int getMinLines(TextView paramTextView)
    {
      if (!sMinModeFieldFetched)
      {
        sMinModeField = retrieveField("mMinMode");
        sMinModeFieldFetched = true;
      }
      Field localField = sMinModeField;
      if ((localField != null) && (retrieveIntFromField(localField, paramTextView) == 1))
      {
        if (!sMinimumFieldFetched)
        {
          sMinimumField = retrieveField("mMinimum");
          sMinimumFieldFetched = true;
        }
        localField = sMinimumField;
        if (localField != null) {
          return retrieveIntFromField(localField, paramTextView);
        }
      }
      return -1;
    }
    
    public void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    public void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
      }
    }
    
    public void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt)
    {
      if ((paramTextView instanceof AutoSizeableTextView)) {
        ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeWithDefaults(paramInt);
      }
    }
    
    public void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    public void setTextAppearance(TextView paramTextView, int paramInt)
    {
      paramTextView.setTextAppearance(paramTextView.getContext(), paramInt);
    }
  }
}


/* Location:              ~/android/support/v4/widget/TextViewCompat.class
 *
 * Reversed by:           J
 */