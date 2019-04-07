package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.b.a.a;
import android.support.v7.b.a.c;
import android.support.v7.b.a.d;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class CardView
  extends FrameLayout
{
  private static final int[] COLOR_BACKGROUND_ATTR = { 16842801 };
  private static final q IMPL;
  private final p mCardViewDelegate = new p()
  {
    private Drawable b;
    
    public void a(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (paramAnonymousInt1 > CardView.this.mUserSetMinWidth) {
        CardView.this.setMinimumWidth(paramAnonymousInt1);
      }
      if (paramAnonymousInt2 > CardView.this.mUserSetMinHeight) {
        CardView.this.setMinimumHeight(paramAnonymousInt2);
      }
    }
    
    public void a(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      CardView.this.mShadowBounds.set(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4);
      CardView localCardView = CardView.this;
      localCardView.setPadding(paramAnonymousInt1 + localCardView.mContentPadding.left, paramAnonymousInt2 + CardView.this.mContentPadding.top, paramAnonymousInt3 + CardView.this.mContentPadding.right, paramAnonymousInt4 + CardView.this.mContentPadding.bottom);
    }
    
    public void a(Drawable paramAnonymousDrawable)
    {
      this.b = paramAnonymousDrawable;
      CardView.this.setBackgroundDrawable(paramAnonymousDrawable);
    }
    
    public boolean a()
    {
      return CardView.this.getUseCompatPadding();
    }
    
    public boolean b()
    {
      return CardView.this.getPreventCornerOverlap();
    }
    
    public Drawable c()
    {
      return this.b;
    }
    
    public View d()
    {
      return CardView.this;
    }
  };
  private boolean mCompatPadding;
  final Rect mContentPadding = new Rect();
  private boolean mPreventCornerOverlap;
  final Rect mShadowBounds = new Rect();
  int mUserSetMinHeight;
  int mUserSetMinWidth;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new n();
    } else if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new m();
    } else {
      IMPL = new o();
    }
    IMPL.a();
  }
  
  public CardView(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext, null, 0);
  }
  
  public CardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize(paramContext, paramAttributeSet, 0);
  }
  
  public CardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize(paramContext, paramAttributeSet, paramInt);
  }
  
  private void initialize(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, a.d.CardView, paramInt, a.c.CardView);
    if (localTypedArray.hasValue(a.d.CardView_cardBackgroundColor))
    {
      paramAttributeSet = localTypedArray.getColorStateList(a.d.CardView_cardBackgroundColor);
    }
    else
    {
      paramAttributeSet = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
      paramInt = paramAttributeSet.getColor(0, 0);
      paramAttributeSet.recycle();
      paramAttributeSet = new float[3];
      Color.colorToHSV(paramInt, paramAttributeSet);
      if (paramAttributeSet[2] > 0.5F) {
        paramInt = getResources().getColor(a.a.cardview_light_background);
      } else {
        paramInt = getResources().getColor(a.a.cardview_dark_background);
      }
      paramAttributeSet = ColorStateList.valueOf(paramInt);
    }
    float f1 = localTypedArray.getDimension(a.d.CardView_cardCornerRadius, 0.0F);
    float f2 = localTypedArray.getDimension(a.d.CardView_cardElevation, 0.0F);
    float f3 = localTypedArray.getDimension(a.d.CardView_cardMaxElevation, 0.0F);
    this.mCompatPadding = localTypedArray.getBoolean(a.d.CardView_cardUseCompatPadding, false);
    this.mPreventCornerOverlap = localTypedArray.getBoolean(a.d.CardView_cardPreventCornerOverlap, true);
    paramInt = localTypedArray.getDimensionPixelSize(a.d.CardView_contentPadding, 0);
    this.mContentPadding.left = localTypedArray.getDimensionPixelSize(a.d.CardView_contentPaddingLeft, paramInt);
    this.mContentPadding.top = localTypedArray.getDimensionPixelSize(a.d.CardView_contentPaddingTop, paramInt);
    this.mContentPadding.right = localTypedArray.getDimensionPixelSize(a.d.CardView_contentPaddingRight, paramInt);
    this.mContentPadding.bottom = localTypedArray.getDimensionPixelSize(a.d.CardView_contentPaddingBottom, paramInt);
    if (f2 > f3) {
      f3 = f2;
    }
    this.mUserSetMinWidth = localTypedArray.getDimensionPixelSize(a.d.CardView_android_minWidth, 0);
    this.mUserSetMinHeight = localTypedArray.getDimensionPixelSize(a.d.CardView_android_minHeight, 0);
    localTypedArray.recycle();
    IMPL.a(this.mCardViewDelegate, paramContext, paramAttributeSet, f1, f2, f3);
  }
  
  public ColorStateList getCardBackgroundColor()
  {
    return IMPL.i(this.mCardViewDelegate);
  }
  
  public float getCardElevation()
  {
    return IMPL.e(this.mCardViewDelegate);
  }
  
  public int getContentPaddingBottom()
  {
    return this.mContentPadding.bottom;
  }
  
  public int getContentPaddingLeft()
  {
    return this.mContentPadding.left;
  }
  
  public int getContentPaddingRight()
  {
    return this.mContentPadding.right;
  }
  
  public int getContentPaddingTop()
  {
    return this.mContentPadding.top;
  }
  
  public float getMaxCardElevation()
  {
    return IMPL.a(this.mCardViewDelegate);
  }
  
  public boolean getPreventCornerOverlap()
  {
    return this.mPreventCornerOverlap;
  }
  
  public float getRadius()
  {
    return IMPL.d(this.mCardViewDelegate);
  }
  
  public boolean getUseCompatPadding()
  {
    return this.mCompatPadding;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (!(IMPL instanceof n))
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      if ((i == Integer.MIN_VALUE) || (i == 1073741824)) {
        paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.max((int)Math.ceil(IMPL.b(this.mCardViewDelegate)), View.MeasureSpec.getSize(paramInt1)), i);
      }
      i = View.MeasureSpec.getMode(paramInt2);
      if ((i == Integer.MIN_VALUE) || (i == 1073741824)) {
        paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.max((int)Math.ceil(IMPL.c(this.mCardViewDelegate)), View.MeasureSpec.getSize(paramInt2)), i);
      }
      super.onMeasure(paramInt1, paramInt2);
    }
    else
    {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public void setCardBackgroundColor(int paramInt)
  {
    IMPL.a(this.mCardViewDelegate, ColorStateList.valueOf(paramInt));
  }
  
  public void setCardBackgroundColor(ColorStateList paramColorStateList)
  {
    IMPL.a(this.mCardViewDelegate, paramColorStateList);
  }
  
  public void setCardElevation(float paramFloat)
  {
    IMPL.c(this.mCardViewDelegate, paramFloat);
  }
  
  public void setContentPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    IMPL.f(this.mCardViewDelegate);
  }
  
  public void setMaxCardElevation(float paramFloat)
  {
    IMPL.b(this.mCardViewDelegate, paramFloat);
  }
  
  public void setMinimumHeight(int paramInt)
  {
    this.mUserSetMinHeight = paramInt;
    super.setMinimumHeight(paramInt);
  }
  
  public void setMinimumWidth(int paramInt)
  {
    this.mUserSetMinWidth = paramInt;
    super.setMinimumWidth(paramInt);
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void setPaddingRelative(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void setPreventCornerOverlap(boolean paramBoolean)
  {
    if (paramBoolean != this.mPreventCornerOverlap)
    {
      this.mPreventCornerOverlap = paramBoolean;
      IMPL.h(this.mCardViewDelegate);
    }
  }
  
  public void setRadius(float paramFloat)
  {
    IMPL.a(this.mCardViewDelegate, paramFloat);
  }
  
  public void setUseCompatPadding(boolean paramBoolean)
  {
    if (this.mCompatPadding != paramBoolean)
    {
      this.mCompatPadding = paramBoolean;
      IMPL.g(this.mCardViewDelegate);
    }
  }
}


/* Location:              ~/android/support/v7/widget/CardView.class
 *
 * Reversed by:           J
 */