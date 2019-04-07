package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class ContentFrameLayout
  extends FrameLayout
{
  private OnAttachListener mAttachListener;
  private final Rect mDecorPadding = new Rect();
  private TypedValue mFixedHeightMajor;
  private TypedValue mFixedHeightMinor;
  private TypedValue mFixedWidthMajor;
  private TypedValue mFixedWidthMinor;
  private TypedValue mMinWidthMajor;
  private TypedValue mMinWidthMinor;
  
  public ContentFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void dispatchFitSystemWindows(Rect paramRect)
  {
    fitSystemWindows(paramRect);
  }
  
  public TypedValue getFixedHeightMajor()
  {
    if (this.mFixedHeightMajor == null) {
      this.mFixedHeightMajor = new TypedValue();
    }
    return this.mFixedHeightMajor;
  }
  
  public TypedValue getFixedHeightMinor()
  {
    if (this.mFixedHeightMinor == null) {
      this.mFixedHeightMinor = new TypedValue();
    }
    return this.mFixedHeightMinor;
  }
  
  public TypedValue getFixedWidthMajor()
  {
    if (this.mFixedWidthMajor == null) {
      this.mFixedWidthMajor = new TypedValue();
    }
    return this.mFixedWidthMajor;
  }
  
  public TypedValue getFixedWidthMinor()
  {
    if (this.mFixedWidthMinor == null) {
      this.mFixedWidthMinor = new TypedValue();
    }
    return this.mFixedWidthMinor;
  }
  
  public TypedValue getMinWidthMajor()
  {
    if (this.mMinWidthMajor == null) {
      this.mMinWidthMajor = new TypedValue();
    }
    return this.mMinWidthMajor;
  }
  
  public TypedValue getMinWidthMinor()
  {
    if (this.mMinWidthMinor == null) {
      this.mMinWidthMinor = new TypedValue();
    }
    return this.mMinWidthMinor;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    OnAttachListener localOnAttachListener = this.mAttachListener;
    if (localOnAttachListener != null) {
      localOnAttachListener.onAttachedFromWindow();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    OnAttachListener localOnAttachListener = this.mAttachListener;
    if (localOnAttachListener != null) {
      localOnAttachListener.onDetachedFromWindow();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    DisplayMetrics localDisplayMetrics = getContext().getResources().getDisplayMetrics();
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    int k = 1;
    if (i < j) {
      j = 1;
    } else {
      j = 0;
    }
    int m = View.MeasureSpec.getMode(paramInt1);
    int n = View.MeasureSpec.getMode(paramInt2);
    TypedValue localTypedValue;
    if (m == Integer.MIN_VALUE)
    {
      if (j != 0) {
        localTypedValue = this.mFixedWidthMinor;
      } else {
        localTypedValue = this.mFixedWidthMajor;
      }
      if ((localTypedValue != null) && (localTypedValue.type != 0))
      {
        if (localTypedValue.type == 5) {
          i = (int)localTypedValue.getDimension(localDisplayMetrics);
        } else if (localTypedValue.type == 6) {
          i = (int)localTypedValue.getFraction(localDisplayMetrics.widthPixels, localDisplayMetrics.widthPixels);
        } else {
          i = 0;
        }
        if (i > 0)
        {
          i1 = View.MeasureSpec.makeMeasureSpec(Math.min(i - (this.mDecorPadding.left + this.mDecorPadding.right), View.MeasureSpec.getSize(paramInt1)), 1073741824);
          i2 = 1;
          break label198;
        }
      }
    }
    int i2 = 0;
    int i1 = paramInt1;
    label198:
    i = paramInt2;
    if (n == Integer.MIN_VALUE)
    {
      if (j != 0) {
        localTypedValue = this.mFixedHeightMajor;
      } else {
        localTypedValue = this.mFixedHeightMinor;
      }
      i = paramInt2;
      if (localTypedValue != null)
      {
        i = paramInt2;
        if (localTypedValue.type != 0)
        {
          if (localTypedValue.type == 5) {
            paramInt1 = (int)localTypedValue.getDimension(localDisplayMetrics);
          } else if (localTypedValue.type == 6) {
            paramInt1 = (int)localTypedValue.getFraction(localDisplayMetrics.heightPixels, localDisplayMetrics.heightPixels);
          } else {
            paramInt1 = 0;
          }
          i = paramInt2;
          if (paramInt1 > 0) {
            i = View.MeasureSpec.makeMeasureSpec(Math.min(paramInt1 - (this.mDecorPadding.top + this.mDecorPadding.bottom), View.MeasureSpec.getSize(paramInt2)), 1073741824);
          }
        }
      }
    }
    super.onMeasure(i1, i);
    n = getMeasuredWidth();
    i1 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
    if ((i2 == 0) && (m == Integer.MIN_VALUE))
    {
      if (j != 0) {
        localTypedValue = this.mMinWidthMinor;
      } else {
        localTypedValue = this.mMinWidthMajor;
      }
      if ((localTypedValue != null) && (localTypedValue.type != 0))
      {
        if (localTypedValue.type == 5) {
          paramInt1 = (int)localTypedValue.getDimension(localDisplayMetrics);
        } else if (localTypedValue.type == 6) {
          paramInt1 = (int)localTypedValue.getFraction(localDisplayMetrics.widthPixels, localDisplayMetrics.widthPixels);
        } else {
          paramInt1 = 0;
        }
        paramInt2 = paramInt1;
        if (paramInt1 > 0) {
          paramInt2 = paramInt1 - (this.mDecorPadding.left + this.mDecorPadding.right);
        }
        if (n < paramInt2)
        {
          paramInt2 = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
          paramInt1 = k;
          break label505;
        }
      }
    }
    paramInt1 = 0;
    paramInt2 = i1;
    label505:
    if (paramInt1 != 0) {
      super.onMeasure(paramInt2, i);
    }
  }
  
  public void setAttachListener(OnAttachListener paramOnAttachListener)
  {
    this.mAttachListener = paramOnAttachListener;
  }
  
  public void setDecorPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mDecorPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    if (ViewCompat.isLaidOut(this)) {
      requestLayout();
    }
  }
  
  public static abstract interface OnAttachListener
  {
    public abstract void onAttachedFromWindow();
    
    public abstract void onDetachedFromWindow();
  }
}


/* Location:              ~/android/support/v7/widget/ContentFrameLayout.class
 *
 * Reversed by:           J
 */