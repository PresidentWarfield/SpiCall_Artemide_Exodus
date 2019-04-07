package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.AutoSizeableTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppCompatTextView
  extends TextView
  implements TintableBackgroundView, AutoSizeableTextView
{
  private final e mBackgroundTintHelper = new e(this);
  private final j mTextHelper;
  
  public AppCompatTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842884);
  }
  
  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    this.mBackgroundTintHelper.a(paramAttributeSet, paramInt);
    this.mTextHelper = j.a(this);
    this.mTextHelper.a(paramAttributeSet, paramInt);
    this.mTextHelper.a();
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      ((e)localObject).c();
    }
    localObject = this.mTextHelper;
    if (localObject != null) {
      ((j)localObject).a();
    }
  }
  
  public int getAutoSizeMaxTextSize()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return super.getAutoSizeMaxTextSize();
    }
    j localj = this.mTextHelper;
    if (localj != null) {
      return localj.g();
    }
    return -1;
  }
  
  public int getAutoSizeMinTextSize()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return super.getAutoSizeMinTextSize();
    }
    j localj = this.mTextHelper;
    if (localj != null) {
      return localj.f();
    }
    return -1;
  }
  
  public int getAutoSizeStepGranularity()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return super.getAutoSizeStepGranularity();
    }
    j localj = this.mTextHelper;
    if (localj != null) {
      return localj.e();
    }
    return -1;
  }
  
  public int[] getAutoSizeTextAvailableSizes()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return super.getAutoSizeTextAvailableSizes();
    }
    j localj = this.mTextHelper;
    if (localj != null) {
      return localj.h();
    }
    return new int[0];
  }
  
  public int getAutoSizeTextType()
  {
    int i = Build.VERSION.SDK_INT;
    int j = 0;
    if (i >= 26)
    {
      if (super.getAutoSizeTextType() == 1) {
        j = 1;
      }
      return j;
    }
    j localj = this.mTextHelper;
    if (localj != null) {
      return localj.d();
    }
    return 0;
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).a();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).b();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    j localj = this.mTextHelper;
    if (localj != null) {
      localj.a(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if ((this.mTextHelper != null) && (Build.VERSION.SDK_INT < 26) && (this.mTextHelper.c())) {
      this.mTextHelper.b();
    }
  }
  
  public void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      super.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else
    {
      j localj = this.mTextHelper;
      if (localj != null) {
        localj.a(paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
  }
  
  public void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfInt, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      super.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
    else
    {
      j localj = this.mTextHelper;
      if (localj != null) {
        localj.a(paramArrayOfInt, paramInt);
      }
    }
  }
  
  public void setAutoSizeTextTypeWithDefaults(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      super.setAutoSizeTextTypeWithDefaults(paramInt);
    }
    else
    {
      j localj = this.mTextHelper;
      if (localj != null) {
        localj.a(paramInt);
      }
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramInt);
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramMode);
    }
  }
  
  public void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    j localj = this.mTextHelper;
    if (localj != null) {
      localj.a(paramContext, paramInt);
    }
  }
  
  public void setTextSize(int paramInt, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      super.setTextSize(paramInt, paramFloat);
    }
    else
    {
      j localj = this.mTextHelper;
      if (localj != null) {
        localj.a(paramInt, paramFloat);
      }
    }
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatTextView.class
 *
 * Reversed by:           J
 */