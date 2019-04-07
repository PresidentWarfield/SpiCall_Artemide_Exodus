package android.support.v4.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils.TruncateAt;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Locale;

@ViewPager.DecorView
public class PagerTitleStrip
  extends ViewGroup
{
  private static final int[] ATTRS = { 16842804, 16842901, 16842904, 16842927 };
  private static final float SIDE_ALPHA = 0.6F;
  private static final int[] TEXT_ATTRS = { 16843660 };
  private static final int TEXT_SPACING = 16;
  TextView mCurrText;
  private int mGravity;
  private int mLastKnownCurrentPage = -1;
  float mLastKnownPositionOffset = -1.0F;
  TextView mNextText;
  private int mNonPrimaryAlpha;
  private final PageListener mPageListener = new PageListener();
  ViewPager mPager;
  TextView mPrevText;
  private int mScaledTextSpacing;
  int mTextColor;
  private boolean mUpdatingPositions;
  private boolean mUpdatingText;
  private WeakReference<PagerAdapter> mWatchingAdapter;
  
  public PagerTitleStrip(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PagerTitleStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TextView localTextView = new TextView(paramContext);
    this.mPrevText = localTextView;
    addView(localTextView);
    localTextView = new TextView(paramContext);
    this.mCurrText = localTextView;
    addView(localTextView);
    localTextView = new TextView(paramContext);
    this.mNextText = localTextView;
    addView(localTextView);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS);
    boolean bool = false;
    int i = paramAttributeSet.getResourceId(0, 0);
    if (i != 0)
    {
      TextViewCompat.setTextAppearance(this.mPrevText, i);
      TextViewCompat.setTextAppearance(this.mCurrText, i);
      TextViewCompat.setTextAppearance(this.mNextText, i);
    }
    int j = paramAttributeSet.getDimensionPixelSize(1, 0);
    if (j != 0) {
      setTextSize(0, j);
    }
    if (paramAttributeSet.hasValue(2))
    {
      j = paramAttributeSet.getColor(2, 0);
      this.mPrevText.setTextColor(j);
      this.mCurrText.setTextColor(j);
      this.mNextText.setTextColor(j);
    }
    this.mGravity = paramAttributeSet.getInteger(3, 80);
    paramAttributeSet.recycle();
    this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
    setNonPrimaryAlpha(0.6F);
    this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
    this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
    this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
    if (i != 0)
    {
      paramAttributeSet = paramContext.obtainStyledAttributes(i, TEXT_ATTRS);
      bool = paramAttributeSet.getBoolean(0, false);
      paramAttributeSet.recycle();
    }
    if (bool)
    {
      setSingleLineAllCaps(this.mPrevText);
      setSingleLineAllCaps(this.mCurrText);
      setSingleLineAllCaps(this.mNextText);
    }
    else
    {
      this.mPrevText.setSingleLine();
      this.mCurrText.setSingleLine();
      this.mNextText.setSingleLine();
    }
    this.mScaledTextSpacing = ((int)(paramContext.getResources().getDisplayMetrics().density * 16.0F));
  }
  
  private static void setSingleLineAllCaps(TextView paramTextView)
  {
    paramTextView.setTransformationMethod(new SingleLineAllCapsTransform(paramTextView.getContext()));
  }
  
  int getMinHeight()
  {
    Drawable localDrawable = getBackground();
    int i;
    if (localDrawable != null) {
      i = localDrawable.getIntrinsicHeight();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getTextSpacing()
  {
    return this.mScaledTextSpacing;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Object localObject = getParent();
    if ((localObject instanceof ViewPager))
    {
      localObject = (ViewPager)localObject;
      PagerAdapter localPagerAdapter = ((ViewPager)localObject).getAdapter();
      ((ViewPager)localObject).setInternalPageChangeListener(this.mPageListener);
      ((ViewPager)localObject).addOnAdapterChangeListener(this.mPageListener);
      this.mPager = ((ViewPager)localObject);
      localObject = this.mWatchingAdapter;
      if (localObject != null) {
        localObject = (PagerAdapter)((WeakReference)localObject).get();
      } else {
        localObject = null;
      }
      updateAdapter((PagerAdapter)localObject, localPagerAdapter);
      return;
    }
    throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ViewPager localViewPager = this.mPager;
    if (localViewPager != null)
    {
      updateAdapter(localViewPager.getAdapter(), null);
      this.mPager.setInternalPageChangeListener(null);
      this.mPager.removeOnAdapterChangeListener(this.mPageListener);
      this.mPager = null;
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mPager != null)
    {
      float f = this.mLastKnownPositionOffset;
      if (f < 0.0F) {
        f = 0.0F;
      }
      updateTextPositions(this.mLastKnownCurrentPage, f, true);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824)
    {
      int i = getPaddingTop() + getPaddingBottom();
      int j = getChildMeasureSpec(paramInt2, i, -2);
      int k = View.MeasureSpec.getSize(paramInt1);
      paramInt1 = getChildMeasureSpec(paramInt1, (int)(k * 0.2F), -2);
      this.mPrevText.measure(paramInt1, j);
      this.mCurrText.measure(paramInt1, j);
      this.mNextText.measure(paramInt1, j);
      if (View.MeasureSpec.getMode(paramInt2) == 1073741824)
      {
        paramInt1 = View.MeasureSpec.getSize(paramInt2);
      }
      else
      {
        paramInt1 = this.mCurrText.getMeasuredHeight();
        paramInt1 = Math.max(getMinHeight(), paramInt1 + i);
      }
      setMeasuredDimension(k, View.resolveSizeAndState(paramInt1, paramInt2, this.mCurrText.getMeasuredState() << 16));
      return;
    }
    throw new IllegalStateException("Must measure with an exact width");
  }
  
  public void requestLayout()
  {
    if (!this.mUpdatingText) {
      super.requestLayout();
    }
  }
  
  public void setGravity(int paramInt)
  {
    this.mGravity = paramInt;
    requestLayout();
  }
  
  public void setNonPrimaryAlpha(float paramFloat)
  {
    this.mNonPrimaryAlpha = ((int)(paramFloat * 255.0F) & 0xFF);
    int i = this.mNonPrimaryAlpha << 24 | this.mTextColor & 0xFFFFFF;
    this.mPrevText.setTextColor(i);
    this.mNextText.setTextColor(i);
  }
  
  public void setTextColor(int paramInt)
  {
    this.mTextColor = paramInt;
    this.mCurrText.setTextColor(paramInt);
    paramInt = this.mNonPrimaryAlpha << 24 | this.mTextColor & 0xFFFFFF;
    this.mPrevText.setTextColor(paramInt);
    this.mNextText.setTextColor(paramInt);
  }
  
  public void setTextSize(int paramInt, float paramFloat)
  {
    this.mPrevText.setTextSize(paramInt, paramFloat);
    this.mCurrText.setTextSize(paramInt, paramFloat);
    this.mNextText.setTextSize(paramInt, paramFloat);
  }
  
  public void setTextSpacing(int paramInt)
  {
    this.mScaledTextSpacing = paramInt;
    requestLayout();
  }
  
  void updateAdapter(PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2)
  {
    if (paramPagerAdapter1 != null)
    {
      paramPagerAdapter1.unregisterDataSetObserver(this.mPageListener);
      this.mWatchingAdapter = null;
    }
    if (paramPagerAdapter2 != null)
    {
      paramPagerAdapter2.registerDataSetObserver(this.mPageListener);
      this.mWatchingAdapter = new WeakReference(paramPagerAdapter2);
    }
    paramPagerAdapter1 = this.mPager;
    if (paramPagerAdapter1 != null)
    {
      this.mLastKnownCurrentPage = -1;
      this.mLastKnownPositionOffset = -1.0F;
      updateText(paramPagerAdapter1.getCurrentItem(), paramPagerAdapter2);
      requestLayout();
    }
  }
  
  void updateText(int paramInt, PagerAdapter paramPagerAdapter)
  {
    if (paramPagerAdapter != null) {
      i = paramPagerAdapter.getCount();
    } else {
      i = 0;
    }
    this.mUpdatingText = true;
    Object localObject1 = null;
    if ((paramInt >= 1) && (paramPagerAdapter != null)) {
      localObject2 = paramPagerAdapter.getPageTitle(paramInt - 1);
    } else {
      localObject2 = null;
    }
    this.mPrevText.setText((CharSequence)localObject2);
    TextView localTextView = this.mCurrText;
    if ((paramPagerAdapter != null) && (paramInt < i)) {
      localObject2 = paramPagerAdapter.getPageTitle(paramInt);
    } else {
      localObject2 = null;
    }
    localTextView.setText((CharSequence)localObject2);
    int j = paramInt + 1;
    Object localObject2 = localObject1;
    if (j < i)
    {
      localObject2 = localObject1;
      if (paramPagerAdapter != null) {
        localObject2 = paramPagerAdapter.getPageTitle(j);
      }
    }
    this.mNextText.setText((CharSequence)localObject2);
    j = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int)((getWidth() - getPaddingLeft() - getPaddingRight()) * 0.8F)), Integer.MIN_VALUE);
    int i = View.MeasureSpec.makeMeasureSpec(Math.max(0, getHeight() - getPaddingTop() - getPaddingBottom()), Integer.MIN_VALUE);
    this.mPrevText.measure(j, i);
    this.mCurrText.measure(j, i);
    this.mNextText.measure(j, i);
    this.mLastKnownCurrentPage = paramInt;
    if (!this.mUpdatingPositions) {
      updateTextPositions(paramInt, this.mLastKnownPositionOffset, false);
    }
    this.mUpdatingText = false;
  }
  
  void updateTextPositions(int paramInt, float paramFloat, boolean paramBoolean)
  {
    if (paramInt != this.mLastKnownCurrentPage) {
      updateText(paramInt, this.mPager.getAdapter());
    } else if ((!paramBoolean) && (paramFloat == this.mLastKnownPositionOffset)) {
      return;
    }
    this.mUpdatingPositions = true;
    int i = this.mPrevText.getMeasuredWidth();
    int j = this.mCurrText.getMeasuredWidth();
    int k = this.mNextText.getMeasuredWidth();
    int m = j / 2;
    int n = getWidth();
    int i1 = getHeight();
    int i2 = getPaddingLeft();
    int i3 = getPaddingRight();
    paramInt = getPaddingTop();
    int i4 = getPaddingBottom();
    int i5 = i3 + m;
    float f1 = 0.5F + paramFloat;
    float f2 = f1;
    if (f1 > 1.0F) {
      f2 = f1 - 1.0F;
    }
    m = n - i5 - (int)((n - (i2 + m) - i5) * f2) - m;
    i5 = j + m;
    int i6 = this.mPrevText.getBaseline();
    j = this.mCurrText.getBaseline();
    int i7 = this.mNextText.getBaseline();
    int i8 = Math.max(Math.max(i6, j), i7);
    i6 = i8 - i6;
    j = i8 - j;
    i7 = i8 - i7;
    i8 = this.mPrevText.getMeasuredHeight();
    int i9 = this.mCurrText.getMeasuredHeight();
    int i10 = this.mNextText.getMeasuredHeight();
    i8 = Math.max(Math.max(i8 + i6, i9 + j), i10 + i7);
    i10 = this.mGravity & 0x70;
    if (i10 != 16)
    {
      if (i10 != 80)
      {
        i1 = i6 + paramInt;
        j += paramInt;
        paramInt += i7;
      }
      else
      {
        paramInt = i1 - i4 - i8;
        i1 = i6 + paramInt;
        j += paramInt;
        paramInt += i7;
      }
    }
    else
    {
      paramInt = (i1 - paramInt - i4 - i8) / 2;
      i1 = i6 + paramInt;
      j += paramInt;
      paramInt += i7;
    }
    TextView localTextView = this.mCurrText;
    localTextView.layout(m, j, i5, localTextView.getMeasuredHeight() + j);
    j = Math.min(i2, m - this.mScaledTextSpacing - i);
    localTextView = this.mPrevText;
    localTextView.layout(j, i1, i + j, localTextView.getMeasuredHeight() + i1);
    i1 = Math.max(n - i3 - k, i5 + this.mScaledTextSpacing);
    localTextView = this.mNextText;
    localTextView.layout(i1, paramInt, i1 + k, localTextView.getMeasuredHeight() + paramInt);
    this.mLastKnownPositionOffset = paramFloat;
    this.mUpdatingPositions = false;
  }
  
  private class PageListener
    extends DataSetObserver
    implements ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener
  {
    private int mScrollState;
    
    PageListener() {}
    
    public void onAdapterChanged(ViewPager paramViewPager, PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2)
    {
      PagerTitleStrip.this.updateAdapter(paramPagerAdapter1, paramPagerAdapter2);
    }
    
    public void onChanged()
    {
      PagerTitleStrip localPagerTitleStrip = PagerTitleStrip.this;
      localPagerTitleStrip.updateText(localPagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
      float f1 = PagerTitleStrip.this.mLastKnownPositionOffset;
      float f2 = 0.0F;
      if (f1 >= 0.0F) {
        f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
      }
      localPagerTitleStrip = PagerTitleStrip.this;
      localPagerTitleStrip.updateTextPositions(localPagerTitleStrip.mPager.getCurrentItem(), f2, true);
    }
    
    public void onPageScrollStateChanged(int paramInt)
    {
      this.mScrollState = paramInt;
    }
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      paramInt2 = paramInt1;
      if (paramFloat > 0.5F) {
        paramInt2 = paramInt1 + 1;
      }
      PagerTitleStrip.this.updateTextPositions(paramInt2, paramFloat, false);
    }
    
    public void onPageSelected(int paramInt)
    {
      if (this.mScrollState == 0)
      {
        PagerTitleStrip localPagerTitleStrip = PagerTitleStrip.this;
        localPagerTitleStrip.updateText(localPagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
        float f1 = PagerTitleStrip.this.mLastKnownPositionOffset;
        float f2 = 0.0F;
        if (f1 >= 0.0F) {
          f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
        }
        localPagerTitleStrip = PagerTitleStrip.this;
        localPagerTitleStrip.updateTextPositions(localPagerTitleStrip.mPager.getCurrentItem(), f2, true);
      }
    }
  }
  
  private static class SingleLineAllCapsTransform
    extends SingleLineTransformationMethod
  {
    private Locale mLocale;
    
    SingleLineAllCapsTransform(Context paramContext)
    {
      this.mLocale = paramContext.getResources().getConfiguration().locale;
    }
    
    public CharSequence getTransformation(CharSequence paramCharSequence, View paramView)
    {
      paramCharSequence = super.getTransformation(paramCharSequence, paramView);
      if (paramCharSequence != null) {
        paramCharSequence = paramCharSequence.toString().toUpperCase(this.mLocale);
      } else {
        paramCharSequence = null;
      }
      return paramCharSequence;
    }
  }
}


/* Location:              ~/android/support/v4/view/PagerTitleStrip.class
 *
 * Reversed by:           J
 */