package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat
  extends ViewGroup
{
  public static final int HORIZONTAL = 0;
  private static final int INDEX_BOTTOM = 2;
  private static final int INDEX_CENTER_VERTICAL = 0;
  private static final int INDEX_FILL = 3;
  private static final int INDEX_TOP = 1;
  public static final int SHOW_DIVIDER_BEGINNING = 1;
  public static final int SHOW_DIVIDER_END = 4;
  public static final int SHOW_DIVIDER_MIDDLE = 2;
  public static final int SHOW_DIVIDER_NONE = 0;
  public static final int VERTICAL = 1;
  private static final int VERTICAL_GRAVITY_COUNT = 4;
  private boolean mBaselineAligned = true;
  private int mBaselineAlignedChildIndex = -1;
  private int mBaselineChildTop = 0;
  private Drawable mDivider;
  private int mDividerHeight;
  private int mDividerPadding;
  private int mDividerWidth;
  private int mGravity = 8388659;
  private int[] mMaxAscent;
  private int[] mMaxDescent;
  private int mOrientation;
  private int mShowDividers;
  private int mTotalLength;
  private boolean mUseLargestChild;
  private float mWeightSum;
  
  public LinearLayoutCompat(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, a.j.LinearLayoutCompat, paramInt, 0);
    paramInt = paramContext.getInt(a.j.LinearLayoutCompat_android_orientation, -1);
    if (paramInt >= 0) {
      setOrientation(paramInt);
    }
    paramInt = paramContext.getInt(a.j.LinearLayoutCompat_android_gravity, -1);
    if (paramInt >= 0) {
      setGravity(paramInt);
    }
    boolean bool = paramContext.getBoolean(a.j.LinearLayoutCompat_android_baselineAligned, true);
    if (!bool) {
      setBaselineAligned(bool);
    }
    this.mWeightSum = paramContext.getFloat(a.j.LinearLayoutCompat_android_weightSum, -1.0F);
    this.mBaselineAlignedChildIndex = paramContext.getInt(a.j.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
    this.mUseLargestChild = paramContext.getBoolean(a.j.LinearLayoutCompat_measureWithLargestChild, false);
    setDividerDrawable(paramContext.getDrawable(a.j.LinearLayoutCompat_divider));
    this.mShowDividers = paramContext.getInt(a.j.LinearLayoutCompat_showDividers, 0);
    this.mDividerPadding = paramContext.getDimensionPixelSize(a.j.LinearLayoutCompat_dividerPadding, 0);
    paramContext.recycle();
  }
  
  private void forceUniformHeight(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
    for (int j = 0; j < paramInt1; j++)
    {
      View localView = getVirtualChildAt(j);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.height == -1)
        {
          int k = localLayoutParams.width;
          localLayoutParams.width = localView.getMeasuredWidth();
          measureChildWithMargins(localView, paramInt2, 0, i, 0);
          localLayoutParams.width = k;
        }
      }
    }
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (int j = 0; j < paramInt1; j++)
    {
      View localView = getVirtualChildAt(j);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.width == -1)
        {
          int k = localLayoutParams.height;
          localLayoutParams.height = localView.getMeasuredHeight();
          measureChildWithMargins(localView, i, 0, paramInt2, 0);
          localLayoutParams.height = k;
        }
      }
    }
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4 + paramInt2);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void drawDividersHorizontal(Canvas paramCanvas)
  {
    int i = getVirtualChildCount();
    boolean bool = ViewUtils.isLayoutRtl(this);
    View localView;
    LayoutParams localLayoutParams;
    for (int j = 0; j < i; j++)
    {
      localView = getVirtualChildAt(j);
      if ((localView != null) && (localView.getVisibility() != 8) && (hasDividerBeforeChildAt(j)))
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int k;
        if (bool) {
          k = localView.getRight() + localLayoutParams.rightMargin;
        } else {
          k = localView.getLeft() - localLayoutParams.leftMargin - this.mDividerWidth;
        }
        drawVerticalDivider(paramCanvas, k);
      }
    }
    if (hasDividerBeforeChildAt(i))
    {
      localView = getVirtualChildAt(i - 1);
      if (localView == null)
      {
        if (bool) {
          j = getPaddingLeft();
        } else {
          j = getWidth() - getPaddingRight() - this.mDividerWidth;
        }
      }
      else
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (bool) {
          j = localView.getLeft() - localLayoutParams.leftMargin - this.mDividerWidth;
        } else {
          j = localView.getRight() + localLayoutParams.rightMargin;
        }
      }
      drawVerticalDivider(paramCanvas, j);
    }
  }
  
  void drawDividersVertical(Canvas paramCanvas)
  {
    int i = getVirtualChildCount();
    Object localObject1;
    Object localObject2;
    for (int j = 0; j < i; j++)
    {
      localObject1 = getVirtualChildAt(j);
      if ((localObject1 != null) && (((View)localObject1).getVisibility() != 8) && (hasDividerBeforeChildAt(j)))
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        drawHorizontalDivider(paramCanvas, ((View)localObject1).getTop() - ((LayoutParams)localObject2).topMargin - this.mDividerHeight);
      }
    }
    if (hasDividerBeforeChildAt(i))
    {
      localObject2 = getVirtualChildAt(i - 1);
      if (localObject2 == null)
      {
        j = getHeight() - getPaddingBottom() - this.mDividerHeight;
      }
      else
      {
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        j = ((View)localObject2).getBottom() + ((LayoutParams)localObject1).bottomMargin;
      }
      drawHorizontalDivider(paramCanvas, j);
    }
  }
  
  void drawHorizontalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, paramInt, getWidth() - getPaddingRight() - this.mDividerPadding, this.mDividerHeight + paramInt);
    this.mDivider.draw(paramCanvas);
  }
  
  void drawVerticalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(paramInt, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + paramInt, getHeight() - getPaddingBottom() - this.mDividerPadding);
    this.mDivider.draw(paramCanvas);
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    int i = this.mOrientation;
    if (i == 0) {
      return new LayoutParams(-2, -2);
    }
    if (i == 1) {
      return new LayoutParams(-1, -2);
    }
    return null;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getBaseline()
  {
    if (this.mBaselineAlignedChildIndex < 0) {
      return super.getBaseline();
    }
    int i = getChildCount();
    int j = this.mBaselineAlignedChildIndex;
    if (i > j)
    {
      View localView = getChildAt(j);
      int k = localView.getBaseline();
      if (k == -1)
      {
        if (this.mBaselineAlignedChildIndex == 0) {
          return -1;
        }
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
      }
      i = this.mBaselineChildTop;
      j = i;
      if (this.mOrientation == 1)
      {
        int m = this.mGravity & 0x70;
        j = i;
        if (m != 48) {
          if (m != 16)
          {
            if (m != 80) {
              j = i;
            } else {
              j = getBottom() - getTop() - getPaddingBottom() - this.mTotalLength;
            }
          }
          else {
            j = i + (getBottom() - getTop() - getPaddingTop() - getPaddingBottom() - this.mTotalLength) / 2;
          }
        }
      }
      return j + ((LayoutParams)localView.getLayoutParams()).topMargin + k;
    }
    throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
  }
  
  public int getBaselineAlignedChildIndex()
  {
    return this.mBaselineAlignedChildIndex;
  }
  
  int getChildrenSkipCount(View paramView, int paramInt)
  {
    return 0;
  }
  
  public Drawable getDividerDrawable()
  {
    return this.mDivider;
  }
  
  public int getDividerPadding()
  {
    return this.mDividerPadding;
  }
  
  public int getDividerWidth()
  {
    return this.mDividerWidth;
  }
  
  public int getGravity()
  {
    return this.mGravity;
  }
  
  int getLocationOffset(View paramView)
  {
    return 0;
  }
  
  int getNextLocationOffset(View paramView)
  {
    return 0;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public int getShowDividers()
  {
    return this.mShowDividers;
  }
  
  View getVirtualChildAt(int paramInt)
  {
    return getChildAt(paramInt);
  }
  
  int getVirtualChildCount()
  {
    return getChildCount();
  }
  
  public float getWeightSum()
  {
    return this.mWeightSum;
  }
  
  protected boolean hasDividerBeforeChildAt(int paramInt)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramInt == 0)
    {
      if ((this.mShowDividers & 0x1) != 0) {
        bool3 = true;
      }
      return bool3;
    }
    if (paramInt == getChildCount())
    {
      bool3 = bool1;
      if ((this.mShowDividers & 0x4) != 0) {
        bool3 = true;
      }
      return bool3;
    }
    if ((this.mShowDividers & 0x2) != 0)
    {
      paramInt--;
      for (;;)
      {
        bool3 = bool2;
        if (paramInt < 0) {
          break;
        }
        if (getChildAt(paramInt).getVisibility() != 8)
        {
          bool3 = true;
          break;
        }
        paramInt--;
      }
      return bool3;
    }
    return false;
  }
  
  public boolean isBaselineAligned()
  {
    return this.mBaselineAligned;
  }
  
  public boolean isMeasureWithLargestChildEnabled()
  {
    return this.mUseLargestChild;
  }
  
  void layoutHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = ViewUtils.isLayoutRtl(this);
    int i = getPaddingTop();
    int j = paramInt4 - paramInt2;
    int k = getPaddingBottom();
    int m = getPaddingBottom();
    int n = getVirtualChildCount();
    paramInt4 = this.mGravity;
    paramInt2 = paramInt4 & 0x70;
    boolean bool2 = this.mBaselineAligned;
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    paramInt4 = GravityCompat.getAbsoluteGravity(0x800007 & paramInt4, ViewCompat.getLayoutDirection(this));
    if (paramInt4 != 1)
    {
      if (paramInt4 != 5) {
        paramInt1 = getPaddingLeft();
      } else {
        paramInt1 = getPaddingLeft() + paramInt3 - paramInt1 - this.mTotalLength;
      }
    }
    else {
      paramInt1 = getPaddingLeft() + (paramInt3 - paramInt1 - this.mTotalLength) / 2;
    }
    int i1;
    int i2;
    if (bool1)
    {
      i1 = n - 1;
      i2 = -1;
    }
    else
    {
      i1 = 0;
      i2 = 1;
    }
    paramInt4 = 0;
    paramInt3 = i;
    int i3 = paramInt1;
    while (paramInt4 < n)
    {
      int i4 = i1 + i2 * paramInt4;
      View localView = getVirtualChildAt(i4);
      if (localView == null)
      {
        i3 += measureNullChild(i4);
      }
      else if (localView.getVisibility() != 8)
      {
        int i5 = localView.getMeasuredWidth();
        int i6 = localView.getMeasuredHeight();
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if ((bool2) && (localLayoutParams.height != -1)) {
          paramInt1 = localView.getBaseline();
        } else {
          paramInt1 = -1;
        }
        int i7 = localLayoutParams.gravity;
        int i8 = i7;
        if (i7 < 0) {
          i8 = paramInt2;
        }
        i8 &= 0x70;
        if (i8 != 16)
        {
          if (i8 != 48)
          {
            if (i8 != 80)
            {
              paramInt1 = paramInt3;
            }
            else
            {
              i8 = j - k - i6 - localLayoutParams.bottomMargin;
              if (paramInt1 != -1)
              {
                i7 = localView.getMeasuredHeight();
                paramInt1 = i8 - (arrayOfInt2[2] - (i7 - paramInt1));
              }
              else
              {
                paramInt1 = i8;
              }
            }
          }
          else
          {
            i8 = localLayoutParams.topMargin + paramInt3;
            if (paramInt1 != -1) {
              paramInt1 = i8 + (arrayOfInt1[1] - paramInt1);
            } else {
              paramInt1 = i8;
            }
          }
        }
        else {
          paramInt1 = (j - i - m - i6) / 2 + paramInt3 + localLayoutParams.topMargin - localLayoutParams.bottomMargin;
        }
        i8 = i3;
        if (hasDividerBeforeChildAt(i4)) {
          i8 = i3 + this.mDividerWidth;
        }
        i3 = localLayoutParams.leftMargin + i8;
        setChildFrame(localView, i3 + getLocationOffset(localView), paramInt1, i5, i6);
        paramInt1 = localLayoutParams.rightMargin;
        i8 = getNextLocationOffset(localView);
        paramInt4 += getChildrenSkipCount(localView, i4);
        i3 += i5 + paramInt1 + i8;
      }
      paramInt4++;
    }
  }
  
  void layoutVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = paramInt3 - paramInt1;
    int k = getPaddingRight();
    int m = getPaddingRight();
    int n = getVirtualChildCount();
    int i1 = this.mGravity;
    paramInt1 = i1 & 0x70;
    if (paramInt1 != 16)
    {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
      } else {
        paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - this.mTotalLength;
      }
    }
    else {
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - this.mTotalLength) / 2;
    }
    for (paramInt2 = 0; paramInt2 < n; paramInt2++)
    {
      View localView = getVirtualChildAt(paramInt2);
      if (localView == null)
      {
        paramInt1 += measureNullChild(paramInt2);
      }
      else if (localView.getVisibility() != 8)
      {
        int i2 = localView.getMeasuredWidth();
        int i3 = localView.getMeasuredHeight();
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        paramInt4 = localLayoutParams.gravity;
        paramInt3 = paramInt4;
        if (paramInt4 < 0) {
          paramInt3 = i1 & 0x800007;
        }
        paramInt3 = GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection(this)) & 0x7;
        if (paramInt3 != 1)
        {
          if (paramInt3 != 5) {
            paramInt3 = localLayoutParams.leftMargin + i;
          } else {
            paramInt3 = j - k - i2 - localLayoutParams.rightMargin;
          }
        }
        else {
          paramInt3 = (j - i - m - i2) / 2 + i + localLayoutParams.leftMargin - localLayoutParams.rightMargin;
        }
        paramInt4 = paramInt1;
        if (hasDividerBeforeChildAt(paramInt2)) {
          paramInt4 = paramInt1 + this.mDividerHeight;
        }
        paramInt1 = paramInt4 + localLayoutParams.topMargin;
        setChildFrame(localView, paramInt3, paramInt1 + getLocationOffset(localView), i2, i3);
        paramInt4 = localLayoutParams.bottomMargin;
        paramInt3 = getNextLocationOffset(localView);
        paramInt2 += getChildrenSkipCount(localView, paramInt2);
        paramInt1 += i3 + paramInt4 + paramInt3;
      }
    }
  }
  
  void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    measureChildWithMargins(paramView, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  void measureHorizontal(int paramInt1, int paramInt2)
  {
    this.mTotalLength = 0;
    int i = getVirtualChildCount();
    int j = View.MeasureSpec.getMode(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    if ((this.mMaxAscent == null) || (this.mMaxDescent == null))
    {
      this.mMaxAscent = new int[4];
      this.mMaxDescent = new int[4];
    }
    int[] arrayOfInt = this.mMaxAscent;
    Object localObject1 = this.mMaxDescent;
    arrayOfInt[3] = -1;
    arrayOfInt[2] = -1;
    arrayOfInt[1] = -1;
    arrayOfInt[0] = -1;
    localObject1[3] = -1;
    localObject1[2] = -1;
    localObject1[1] = -1;
    localObject1[0] = -1;
    boolean bool1 = this.mBaselineAligned;
    boolean bool2 = this.mUseLargestChild;
    int m;
    if (j == 1073741824) {
      m = 1;
    } else {
      m = 0;
    }
    int n = 0;
    int i1 = Integer.MIN_VALUE;
    float f1 = 0.0F;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 1;
    int i8 = 0;
    Object localObject2;
    Object localObject3;
    int i9;
    int i10;
    while (n < i)
    {
      localObject2 = getVirtualChildAt(n);
      if (localObject2 == null)
      {
        this.mTotalLength += measureNullChild(n);
      }
      else if (((View)localObject2).getVisibility() == 8)
      {
        n += getChildrenSkipCount((View)localObject2, n);
      }
      else
      {
        if (hasDividerBeforeChildAt(n)) {
          this.mTotalLength += this.mDividerWidth;
        }
        localObject3 = (LayoutParams)((View)localObject2).getLayoutParams();
        f1 += ((LayoutParams)localObject3).weight;
        if ((j == 1073741824) && (((LayoutParams)localObject3).width == 0) && (((LayoutParams)localObject3).weight > 0.0F))
        {
          if (m != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin;
          }
          else
          {
            i9 = this.mTotalLength;
            this.mTotalLength = Math.max(i9, ((LayoutParams)localObject3).leftMargin + i9 + ((LayoutParams)localObject3).rightMargin);
          }
          if (bool1)
          {
            i9 = View.MeasureSpec.makeMeasureSpec(0, 0);
            ((View)localObject2).measure(i9, i9);
          }
          else
          {
            i5 = 1;
          }
        }
        else
        {
          if ((((LayoutParams)localObject3).width == 0) && (((LayoutParams)localObject3).weight > 0.0F))
          {
            ((LayoutParams)localObject3).width = -2;
            i9 = 0;
          }
          else
          {
            i9 = Integer.MIN_VALUE;
          }
          if (f1 == 0.0F) {
            i10 = this.mTotalLength;
          } else {
            i10 = 0;
          }
          Object localObject4 = localObject3;
          measureChildBeforeLayout((View)localObject2, n, paramInt1, i10, paramInt2, 0);
          if (i9 != Integer.MIN_VALUE) {
            ((LayoutParams)localObject4).width = i9;
          }
          i10 = ((View)localObject2).getMeasuredWidth();
          if (m != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject4).leftMargin + i10 + ((LayoutParams)localObject4).rightMargin + getNextLocationOffset((View)localObject2);
          }
          else
          {
            i9 = this.mTotalLength;
            this.mTotalLength = Math.max(i9, i9 + i10 + ((LayoutParams)localObject4).leftMargin + ((LayoutParams)localObject4).rightMargin + getNextLocationOffset((View)localObject2));
          }
          if (bool2) {
            i1 = Math.max(i10, i1);
          }
        }
        i9 = n;
        if ((k != 1073741824) && (((LayoutParams)localObject3).height == -1))
        {
          n = 1;
          i8 = 1;
        }
        else
        {
          n = 0;
        }
        i10 = ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin;
        i11 = ((View)localObject2).getMeasuredHeight() + i10;
        int i12 = View.combineMeasuredStates(i6, ((View)localObject2).getMeasuredState());
        if (bool1)
        {
          int i13 = ((View)localObject2).getBaseline();
          if (i13 != -1)
          {
            if (((LayoutParams)localObject3).gravity < 0) {
              i6 = this.mGravity;
            } else {
              i6 = ((LayoutParams)localObject3).gravity;
            }
            i6 = ((i6 & 0x70) >> 4 & 0xFFFFFFFE) >> 1;
            arrayOfInt[i6] = Math.max(arrayOfInt[i6], i13);
            localObject1[i6] = Math.max(localObject1[i6], i11 - i13);
          }
          else {}
        }
        i2 = Math.max(i2, i11);
        if ((i7 != 0) && (((LayoutParams)localObject3).height == -1)) {
          i7 = 1;
        } else {
          i7 = 0;
        }
        if (((LayoutParams)localObject3).weight > 0.0F)
        {
          if (n == 0) {
            i10 = i11;
          }
          n = Math.max(i4, i10);
        }
        else
        {
          if (n != 0) {
            i11 = i10;
          }
          i3 = Math.max(i3, i11);
          n = i4;
        }
        i4 = getChildrenSkipCount((View)localObject2, i9);
        i9 = i4 + i9;
        i6 = i12;
        i4 = n;
        n = i9;
      }
      n++;
    }
    if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(i))) {
      this.mTotalLength += this.mDividerWidth;
    }
    if ((arrayOfInt[1] == -1) && (arrayOfInt[0] == -1) && (arrayOfInt[2] == -1))
    {
      n = i2;
      if (arrayOfInt[3] == -1) {}
    }
    else
    {
      n = Math.max(i2, Math.max(arrayOfInt[3], Math.max(arrayOfInt[0], Math.max(arrayOfInt[1], arrayOfInt[2]))) + Math.max(localObject1[3], Math.max(localObject1[0], Math.max(localObject1[1], localObject1[2]))));
    }
    if ((bool2) && ((j == Integer.MIN_VALUE) || (j == 0)))
    {
      this.mTotalLength = 0;
      for (i2 = 0; i2 < i; i2++)
      {
        localObject3 = getVirtualChildAt(i2);
        if (localObject3 == null)
        {
          this.mTotalLength += measureNullChild(i2);
        }
        else if (((View)localObject3).getVisibility() == 8)
        {
          i2 += getChildrenSkipCount((View)localObject3, i2);
        }
        else
        {
          localObject2 = (LayoutParams)((View)localObject3).getLayoutParams();
          if (m != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject2).leftMargin + i1 + ((LayoutParams)localObject2).rightMargin + getNextLocationOffset((View)localObject3);
          }
          else
          {
            i9 = this.mTotalLength;
            this.mTotalLength = Math.max(i9, i9 + i1 + ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin + getNextLocationOffset((View)localObject3));
          }
        }
      }
    }
    this.mTotalLength += getPaddingLeft() + getPaddingRight();
    int i11 = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), paramInt1, 0);
    i2 = (0xFFFFFF & i11) - this.mTotalLength;
    if ((i5 == 0) && ((i2 == 0) || (f1 <= 0.0F)))
    {
      i4 = Math.max(i3, i4);
      if ((bool2) && (j != 1073741824)) {
        for (i3 = 0; i3 < i; i3++)
        {
          localObject1 = getVirtualChildAt(i3);
          if ((localObject1 != null) && (((View)localObject1).getVisibility() != 8) && (((LayoutParams)((View)localObject1).getLayoutParams()).weight > 0.0F)) {
            ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec(i1, 1073741824), View.MeasureSpec.makeMeasureSpec(((View)localObject1).getMeasuredHeight(), 1073741824));
          }
        }
      }
      i3 = i4;
      i4 = i7;
    }
    else
    {
      float f2 = this.mWeightSum;
      if (f2 > 0.0F) {
        f1 = f2;
      }
      arrayOfInt[3] = -1;
      arrayOfInt[2] = -1;
      arrayOfInt[1] = -1;
      arrayOfInt[0] = -1;
      localObject1[3] = -1;
      localObject1[2] = -1;
      localObject1[1] = -1;
      localObject1[0] = -1;
      this.mTotalLength = 0;
      i4 = i3;
      i1 = -1;
      i5 = 0;
      n = i7;
      i3 = i6;
      i7 = i2;
      for (i6 = i5; i6 < i; i6++)
      {
        localObject2 = getVirtualChildAt(i6);
        if ((localObject2 != null) && (((View)localObject2).getVisibility() != 8))
        {
          localObject3 = (LayoutParams)((View)localObject2).getLayoutParams();
          f2 = ((LayoutParams)localObject3).weight;
          if (f2 > 0.0F)
          {
            i5 = (int)(i7 * f2 / f1);
            i10 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom() + ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin, ((LayoutParams)localObject3).height);
            if ((((LayoutParams)localObject3).width == 0) && (j == 1073741824))
            {
              if (i5 > 0) {
                i2 = i5;
              } else {
                i2 = 0;
              }
              ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), i10);
            }
            else
            {
              i9 = ((View)localObject2).getMeasuredWidth() + i5;
              i2 = i9;
              if (i9 < 0) {
                i2 = 0;
              }
              ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), i10);
            }
            i3 = View.combineMeasuredStates(i3, ((View)localObject2).getMeasuredState() & 0xFF000000);
            f1 -= f2;
            i7 -= i5;
          }
          if (m != 0)
          {
            this.mTotalLength += ((View)localObject2).getMeasuredWidth() + ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2);
          }
          else
          {
            i2 = this.mTotalLength;
            this.mTotalLength = Math.max(i2, ((View)localObject2).getMeasuredWidth() + i2 + ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2));
          }
          if ((k != 1073741824) && (((LayoutParams)localObject3).height == -1)) {
            i2 = 1;
          } else {
            i2 = 0;
          }
          i10 = ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin;
          i9 = ((View)localObject2).getMeasuredHeight() + i10;
          i5 = Math.max(i1, i9);
          if (i2 != 0) {
            i1 = i10;
          } else {
            i1 = i9;
          }
          i1 = Math.max(i4, i1);
          if ((n != 0) && (((LayoutParams)localObject3).height == -1)) {
            n = 1;
          } else {
            n = 0;
          }
          if (bool1)
          {
            i2 = ((View)localObject2).getBaseline();
            if (i2 != -1)
            {
              if (((LayoutParams)localObject3).gravity < 0) {
                i4 = this.mGravity;
              } else {
                i4 = ((LayoutParams)localObject3).gravity;
              }
              i4 = ((i4 & 0x70) >> 4 & 0xFFFFFFFE) >> 1;
              arrayOfInt[i4] = Math.max(arrayOfInt[i4], i2);
              localObject1[i4] = Math.max(localObject1[i4], i9 - i2);
            }
            else {}
          }
          i4 = i1;
          i1 = i5;
        }
      }
      this.mTotalLength += getPaddingLeft() + getPaddingRight();
      if ((arrayOfInt[1] == -1) && (arrayOfInt[0] == -1) && (arrayOfInt[2] == -1) && (arrayOfInt[3] == -1)) {
        i7 = i1;
      } else {
        i7 = Math.max(i1, Math.max(arrayOfInt[3], Math.max(arrayOfInt[0], Math.max(arrayOfInt[1], arrayOfInt[2]))) + Math.max(localObject1[3], Math.max(localObject1[0], Math.max(localObject1[1], localObject1[2]))));
      }
      i1 = i4;
      i4 = n;
      i6 = i3;
      i3 = i1;
      n = i7;
    }
    i7 = n;
    if (i4 == 0)
    {
      i7 = n;
      if (k != 1073741824) {
        i7 = i3;
      }
    }
    setMeasuredDimension(i11 | 0xFF000000 & i6, View.resolveSizeAndState(Math.max(i7 + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), paramInt2, i6 << 16));
    if (i8 != 0) {
      forceUniformHeight(i, paramInt1);
    }
  }
  
  int measureNullChild(int paramInt)
  {
    return 0;
  }
  
  void measureVertical(int paramInt1, int paramInt2)
  {
    int i = 0;
    this.mTotalLength = 0;
    int j = getVirtualChildCount();
    int k = View.MeasureSpec.getMode(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    int n = this.mBaselineAlignedChildIndex;
    boolean bool = this.mUseLargestChild;
    float f1 = 0.0F;
    int i1 = 0;
    int i2 = Integer.MIN_VALUE;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 1;
    int i7 = 0;
    int i8 = 0;
    Object localObject1;
    Object localObject2;
    int i10;
    while (i4 < j)
    {
      localObject1 = getVirtualChildAt(i4);
      if (localObject1 == null)
      {
        this.mTotalLength += measureNullChild(i4);
      }
      else if (((View)localObject1).getVisibility() == 8)
      {
        i4 += getChildrenSkipCount((View)localObject1, i4);
      }
      else
      {
        if (hasDividerBeforeChildAt(i4)) {
          this.mTotalLength += this.mDividerHeight;
        }
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        f1 += ((LayoutParams)localObject2).weight;
        if ((m == 1073741824) && (((LayoutParams)localObject2).height == 0) && (((LayoutParams)localObject2).weight > 0.0F))
        {
          i5 = this.mTotalLength;
          this.mTotalLength = Math.max(i5, ((LayoutParams)localObject2).topMargin + i5 + ((LayoutParams)localObject2).bottomMargin);
          i5 = 1;
        }
        else
        {
          if ((((LayoutParams)localObject2).height == 0) && (((LayoutParams)localObject2).weight > 0.0F))
          {
            ((LayoutParams)localObject2).height = -2;
            i9 = 0;
          }
          else
          {
            i9 = Integer.MIN_VALUE;
          }
          if (f1 == 0.0F) {
            i10 = this.mTotalLength;
          } else {
            i10 = 0;
          }
          Object localObject3 = localObject2;
          measureChildBeforeLayout((View)localObject1, i4, paramInt1, 0, paramInt2, i10);
          if (i9 != Integer.MIN_VALUE) {
            ((LayoutParams)localObject3).height = i9;
          }
          i9 = ((View)localObject1).getMeasuredHeight();
          i10 = this.mTotalLength;
          this.mTotalLength = Math.max(i10, i10 + i9 + ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin + getNextLocationOffset((View)localObject1));
          if (bool) {
            i2 = Math.max(i9, i2);
          }
        }
        i9 = i8;
        int i11 = i4;
        if ((n >= 0) && (n == i11 + 1)) {
          this.mBaselineChildTop = this.mTotalLength;
        }
        if ((i11 < n) && (((LayoutParams)localObject2).weight > 0.0F)) {
          throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
        }
        if ((k != 1073741824) && (((LayoutParams)localObject2).width == -1))
        {
          i4 = 1;
          i7 = 1;
        }
        else
        {
          i4 = 0;
        }
        i10 = ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin;
        i12 = ((View)localObject1).getMeasuredWidth() + i10;
        int i13 = Math.max(i1, i12);
        i = View.combineMeasuredStates(i, ((View)localObject1).getMeasuredState());
        if ((i6 != 0) && (((LayoutParams)localObject2).width == -1)) {
          i8 = 1;
        } else {
          i8 = 0;
        }
        if (((LayoutParams)localObject2).weight > 0.0F)
        {
          if (i4 == 0) {
            i10 = i12;
          }
          i6 = Math.max(i3, i10);
          i1 = i9;
        }
        else
        {
          i6 = i3;
          if (i4 != 0) {
            i12 = i10;
          }
          i1 = Math.max(i9, i12);
        }
        i9 = i11 + getChildrenSkipCount((View)localObject1, i11);
        i4 = i8;
        i3 = i6;
        i8 = i1;
        i6 = i4;
        i1 = i13;
        i4 = i9;
      }
      i4++;
    }
    i4 = i8;
    if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(j))) {
      this.mTotalLength += this.mDividerHeight;
    }
    int i9 = j;
    if (bool)
    {
      i8 = m;
      if ((i8 == Integer.MIN_VALUE) || (i8 == 0))
      {
        this.mTotalLength = 0;
        for (i8 = 0; i8 < i9; i8++)
        {
          localObject2 = getVirtualChildAt(i8);
          if (localObject2 == null)
          {
            this.mTotalLength += measureNullChild(i8);
          }
          else if (((View)localObject2).getVisibility() == 8)
          {
            i8 += getChildrenSkipCount((View)localObject2, i8);
          }
          else
          {
            localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
            j = this.mTotalLength;
            this.mTotalLength = Math.max(j, j + i2 + ((LayoutParams)localObject1).topMargin + ((LayoutParams)localObject1).bottomMargin + getNextLocationOffset((View)localObject2));
          }
        }
      }
    }
    j = m;
    this.mTotalLength += getPaddingTop() + getPaddingBottom();
    int i12 = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), paramInt2, 0);
    i8 = (0xFFFFFF & i12) - this.mTotalLength;
    if ((i5 == 0) && ((i8 == 0) || (f1 <= 0.0F)))
    {
      m = Math.max(i4, i3);
      if ((bool) && (j != 1073741824)) {
        for (i8 = 0; i8 < i9; i8++)
        {
          localObject2 = getVirtualChildAt(i8);
          if ((localObject2 != null) && (((View)localObject2).getVisibility() != 8) && (((LayoutParams)((View)localObject2).getLayoutParams()).weight > 0.0F)) {
            ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(((View)localObject2).getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
          }
        }
      }
      i8 = m;
    }
    else
    {
      float f2 = this.mWeightSum;
      if (f2 > 0.0F) {
        f1 = f2;
      }
      this.mTotalLength = 0;
      i2 = 0;
      m = i1;
      i1 = i4;
      i4 = j;
      for (j = i2; j < i9; j++)
      {
        localObject1 = getVirtualChildAt(j);
        if (((View)localObject1).getVisibility() != 8)
        {
          localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
          f2 = ((LayoutParams)localObject2).weight;
          if (f2 > 0.0F)
          {
            i3 = (int)(i8 * f2 / f1);
            i2 = getPaddingLeft();
            i5 = getPaddingRight();
            f1 -= f2;
            i10 = getChildMeasureSpec(paramInt1, i2 + i5 + ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin, ((LayoutParams)localObject2).width);
            if ((((LayoutParams)localObject2).height == 0) && (i4 == 1073741824))
            {
              if (i3 > 0) {
                i2 = i3;
              } else {
                i2 = 0;
              }
              ((View)localObject1).measure(i10, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
            }
            else
            {
              i5 = ((View)localObject1).getMeasuredHeight() + i3;
              i2 = i5;
              if (i5 < 0) {
                i2 = 0;
              }
              ((View)localObject1).measure(i10, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
            }
            i = View.combineMeasuredStates(i, ((View)localObject1).getMeasuredState() & 0xFF00);
            i8 -= i3;
          }
          i5 = ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin;
          i10 = ((View)localObject1).getMeasuredWidth() + i5;
          i3 = Math.max(m, i10);
          if ((k != 1073741824) && (((LayoutParams)localObject2).width == -1)) {
            i2 = 1;
          } else {
            i2 = 0;
          }
          m = i8;
          i8 = i10;
          if (i2 != 0) {
            i8 = i5;
          }
          i1 = Math.max(i1, i8);
          if ((i6 != 0) && (((LayoutParams)localObject2).width == -1)) {
            i8 = 1;
          } else {
            i8 = 0;
          }
          i6 = this.mTotalLength;
          this.mTotalLength = Math.max(i6, ((View)localObject1).getMeasuredHeight() + i6 + ((LayoutParams)localObject2).topMargin + ((LayoutParams)localObject2).bottomMargin + getNextLocationOffset((View)localObject1));
          i6 = i8;
          i8 = m;
          m = i3;
        }
      }
      this.mTotalLength += getPaddingTop() + getPaddingBottom();
      i8 = i1;
      i1 = m;
    }
    if ((i6 != 0) || (k == 1073741824)) {
      i8 = i1;
    }
    setMeasuredDimension(View.resolveSizeAndState(Math.max(i8 + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), paramInt1, i), i12);
    if (i7 != 0) {
      forceUniformWidth(i9, paramInt2);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mDivider == null) {
      return;
    }
    if (this.mOrientation == 1) {
      drawDividersVertical(paramCanvas);
    } else {
      drawDividersHorizontal(paramCanvas);
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mOrientation == 1) {
      layoutVertical(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      layoutHorizontal(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 1) {
      measureVertical(paramInt1, paramInt2);
    } else {
      measureHorizontal(paramInt1, paramInt2);
    }
  }
  
  public void setBaselineAligned(boolean paramBoolean)
  {
    this.mBaselineAligned = paramBoolean;
  }
  
  public void setBaselineAlignedChildIndex(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getChildCount()))
    {
      this.mBaselineAlignedChildIndex = paramInt;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("base aligned child index out of range (0, ");
    localStringBuilder.append(getChildCount());
    localStringBuilder.append(")");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void setDividerDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mDivider) {
      return;
    }
    this.mDivider = paramDrawable;
    boolean bool = false;
    if (paramDrawable != null)
    {
      this.mDividerWidth = paramDrawable.getIntrinsicWidth();
      this.mDividerHeight = paramDrawable.getIntrinsicHeight();
    }
    else
    {
      this.mDividerWidth = 0;
      this.mDividerHeight = 0;
    }
    if (paramDrawable == null) {
      bool = true;
    }
    setWillNotDraw(bool);
    requestLayout();
  }
  
  public void setDividerPadding(int paramInt)
  {
    this.mDividerPadding = paramInt;
  }
  
  public void setGravity(int paramInt)
  {
    if (this.mGravity != paramInt)
    {
      int i = paramInt;
      if ((0x800007 & paramInt) == 0) {
        i = paramInt | 0x800003;
      }
      paramInt = i;
      if ((i & 0x70) == 0) {
        paramInt = i | 0x30;
      }
      this.mGravity = paramInt;
      requestLayout();
    }
  }
  
  public void setHorizontalGravity(int paramInt)
  {
    paramInt &= 0x800007;
    int i = this.mGravity;
    if ((0x800007 & i) != paramInt)
    {
      this.mGravity = (paramInt | 0xFF7FFFF8 & i);
      requestLayout();
    }
  }
  
  public void setMeasureWithLargestChildEnabled(boolean paramBoolean)
  {
    this.mUseLargestChild = paramBoolean;
  }
  
  public void setOrientation(int paramInt)
  {
    if (this.mOrientation != paramInt)
    {
      this.mOrientation = paramInt;
      requestLayout();
    }
  }
  
  public void setShowDividers(int paramInt)
  {
    if (paramInt != this.mShowDividers) {
      requestLayout();
    }
    this.mShowDividers = paramInt;
  }
  
  public void setVerticalGravity(int paramInt)
  {
    int i = paramInt & 0x70;
    paramInt = this.mGravity;
    if ((paramInt & 0x70) != i)
    {
      this.mGravity = (i | paramInt & 0xFFFFFF8F);
      requestLayout();
    }
  }
  
  public void setWeightSum(float paramFloat)
  {
    this.mWeightSum = Math.max(0.0F, paramFloat);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DividerMode {}
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public int gravity = -1;
    public float weight;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.weight = 0.0F;
    }
    
    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2);
      this.weight = paramFloat;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.LinearLayoutCompat_Layout);
      this.weight = paramContext.getFloat(a.j.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
      this.gravity = paramContext.getInt(a.j.LinearLayoutCompat_Layout_android_layout_gravity, -1);
      paramContext.recycle();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.weight = paramLayoutParams.weight;
      this.gravity = paramLayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface OrientationMode {}
}


/* Location:              ~/android/support/v7/widget/LinearLayoutCompat.class
 *
 * Reversed by:           J
 */