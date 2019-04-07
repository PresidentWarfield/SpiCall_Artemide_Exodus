package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.h.b;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.o.a;
import android.support.v7.view.menu.p;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView
  extends LinearLayoutCompat
  implements h.b, p
{
  static final int GENERATED_ITEM_PADDING = 4;
  static final int MIN_CELL_SIZE = 56;
  private static final String TAG = "ActionMenuView";
  private o.a mActionMenuPresenterCallback;
  private boolean mFormatItems;
  private int mFormatItemsWidth;
  private int mGeneratedItemPadding;
  private h mMenu;
  h.a mMenuBuilderCallback;
  private int mMinCellSize;
  OnMenuItemClickListener mOnMenuItemClickListener;
  private Context mPopupContext;
  private int mPopupTheme;
  private c mPresenter;
  private boolean mReserveOverflow;
  
  public ActionMenuView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionMenuView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBaselineAligned(false);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mMinCellSize = ((int)(56.0F * f));
    this.mGeneratedItemPadding = ((int)(f * 4.0F));
    this.mPopupContext = paramContext;
    this.mPopupTheme = 0;
  }
  
  static int measureChildForCells(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt3) - paramInt4, View.MeasureSpec.getMode(paramInt3));
    ActionMenuItemView localActionMenuItemView;
    if ((paramView instanceof ActionMenuItemView)) {
      localActionMenuItemView = (ActionMenuItemView)paramView;
    } else {
      localActionMenuItemView = null;
    }
    boolean bool = true;
    if ((localActionMenuItemView != null) && (localActionMenuItemView.a())) {
      paramInt3 = 1;
    } else {
      paramInt3 = 0;
    }
    paramInt4 = 2;
    if ((paramInt2 > 0) && ((paramInt3 == 0) || (paramInt2 >= 2)))
    {
      paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt2 * paramInt1, Integer.MIN_VALUE), i);
      int j = paramView.getMeasuredWidth();
      int k = j / paramInt1;
      paramInt2 = k;
      if (j % paramInt1 != 0) {
        paramInt2 = k + 1;
      }
      if ((paramInt3 != 0) && (paramInt2 < 2)) {
        paramInt2 = paramInt4;
      }
    }
    else
    {
      paramInt2 = 0;
    }
    if ((localLayoutParams.isOverflowButton) || (paramInt3 == 0)) {
      bool = false;
    }
    localLayoutParams.expandable = bool;
    localLayoutParams.cellsUsed = paramInt2;
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1 * paramInt2, 1073741824), i);
    return paramInt2;
  }
  
  private void onMeasureExactFormat(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt2);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getSize(paramInt2);
    paramInt1 = getPaddingLeft();
    int m = getPaddingRight();
    int n = getPaddingTop() + getPaddingBottom();
    int i1 = getChildMeasureSpec(paramInt2, n, -2);
    int i2 = j - (paramInt1 + m);
    paramInt2 = this.mMinCellSize;
    paramInt1 = i2 / paramInt2;
    if (paramInt1 == 0)
    {
      setMeasuredDimension(i2, 0);
      return;
    }
    int i3 = paramInt2 + i2 % paramInt2 / paramInt1;
    int i4 = getChildCount();
    j = 0;
    m = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    long l1 = 0L;
    Object localObject1;
    Object localObject2;
    while (j < i4)
    {
      localObject1 = getChildAt(j);
      if (((View)localObject1).getVisibility() == 8)
      {
        paramInt2 = i8;
      }
      else
      {
        boolean bool = localObject1 instanceof ActionMenuItemView;
        i6++;
        if (bool)
        {
          paramInt2 = this.mGeneratedItemPadding;
          ((View)localObject1).setPadding(paramInt2, 0, paramInt2, 0);
        }
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        ((LayoutParams)localObject2).expanded = false;
        ((LayoutParams)localObject2).extraPixels = 0;
        ((LayoutParams)localObject2).cellsUsed = 0;
        ((LayoutParams)localObject2).expandable = false;
        ((LayoutParams)localObject2).leftMargin = 0;
        ((LayoutParams)localObject2).rightMargin = 0;
        if ((bool) && (((ActionMenuItemView)localObject1).a())) {
          bool = true;
        } else {
          bool = false;
        }
        ((LayoutParams)localObject2).preventEdgeOffset = bool;
        if (((LayoutParams)localObject2).isOverflowButton) {
          paramInt2 = 1;
        } else {
          paramInt2 = paramInt1;
        }
        i9 = measureChildForCells((View)localObject1, i3, paramInt2, i1, n);
        i7 = Math.max(i7, i9);
        paramInt2 = i8;
        if (((LayoutParams)localObject2).expandable) {
          paramInt2 = i8 + 1;
        }
        if (((LayoutParams)localObject2).isOverflowButton) {
          i5 = 1;
        }
        paramInt1 -= i9;
        m = Math.max(m, ((View)localObject1).getMeasuredHeight());
        if (i9 == 1) {
          l1 |= 1 << j;
        }
      }
      j++;
      i8 = paramInt2;
    }
    if ((i5 != 0) && (i6 == 2)) {
      n = 1;
    } else {
      n = 0;
    }
    j = 0;
    int i9 = paramInt1;
    paramInt2 = i2;
    paramInt1 = i;
    while ((i8 > 0) && (i9 > 0))
    {
      i2 = Integer.MAX_VALUE;
      int i10 = 0;
      int i11 = 0;
      long l2 = 0L;
      i = j;
      long l3;
      while (i10 < i4)
      {
        localObject1 = (LayoutParams)getChildAt(i10).getLayoutParams();
        if (!((LayoutParams)localObject1).expandable)
        {
          j = i11;
          l3 = l2;
        }
        else if (((LayoutParams)localObject1).cellsUsed < i2)
        {
          i2 = ((LayoutParams)localObject1).cellsUsed;
          l3 = 1 << i10;
          j = 1;
        }
        else if (((LayoutParams)localObject1).cellsUsed == i2)
        {
          l3 = 1 << i10;
          j = i11 + 1;
          l3 = l2 | l3;
        }
        else
        {
          l3 = l2;
          j = i11;
        }
        i10++;
        i11 = j;
        l2 = l3;
      }
      j = paramInt2;
      paramInt2 = m;
      l1 |= l2;
      if (i11 > i9)
      {
        i8 = paramInt1;
        paramInt1 = i;
        m = j;
        break label752;
      }
      m = i2 + 1;
      for (i = 0; i < i4; i++)
      {
        localObject2 = getChildAt(i);
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        long l4 = 1 << i;
        if ((l2 & l4) == 0L)
        {
          l3 = l1;
          if (((LayoutParams)localObject1).cellsUsed == m) {
            l3 = l1 | l4;
          }
          l1 = l3;
        }
        else
        {
          if ((n != 0) && (((LayoutParams)localObject1).preventEdgeOffset) && (i9 == 1))
          {
            i2 = this.mGeneratedItemPadding;
            ((View)localObject2).setPadding(i2 + i3, 0, i2, 0);
          }
          ((LayoutParams)localObject1).cellsUsed += 1;
          ((LayoutParams)localObject1).expanded = true;
          i9--;
        }
      }
      m = paramInt2;
      paramInt2 = j;
      j = 1;
    }
    i = paramInt2;
    paramInt2 = m;
    i8 = paramInt1;
    m = i;
    paramInt1 = j;
    label752:
    if ((i5 == 0) && (i6 == 1)) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i9 > 0) && (l1 != 0L) && ((i9 < i6 - 1) || (j != 0) || (i7 > 1)))
    {
      float f1 = Long.bitCount(l1);
      if (j == 0)
      {
        float f2;
        if ((l1 & 1L) != 0L)
        {
          f2 = f1;
          if (!((LayoutParams)getChildAt(0).getLayoutParams()).preventEdgeOffset) {
            f2 = f1 - 0.5F;
          }
        }
        else
        {
          f2 = f1;
        }
        j = i4 - 1;
        f1 = f2;
        if ((l1 & 1 << j) != 0L)
        {
          f1 = f2;
          if (!((LayoutParams)getChildAt(j).getLayoutParams()).preventEdgeOffset) {
            f1 = f2 - 0.5F;
          }
        }
      }
      if (f1 > 0.0F) {
        i5 = (int)(i9 * i3 / f1);
      } else {
        i5 = 0;
      }
      i = 0;
      for (j = paramInt1; i < i4; j = paramInt1)
      {
        if ((l1 & 1 << i) == 0L)
        {
          paramInt1 = j;
        }
        else
        {
          localObject1 = getChildAt(i);
          localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
          if ((localObject1 instanceof ActionMenuItemView))
          {
            ((LayoutParams)localObject2).extraPixels = i5;
            ((LayoutParams)localObject2).expanded = true;
            if ((i == 0) && (!((LayoutParams)localObject2).preventEdgeOffset)) {
              ((LayoutParams)localObject2).leftMargin = (-i5 / 2);
            }
            paramInt1 = 1;
          }
          else if (((LayoutParams)localObject2).isOverflowButton)
          {
            ((LayoutParams)localObject2).extraPixels = i5;
            ((LayoutParams)localObject2).expanded = true;
            ((LayoutParams)localObject2).rightMargin = (-i5 / 2);
            paramInt1 = 1;
          }
          else
          {
            if (i != 0) {
              ((LayoutParams)localObject2).leftMargin = (i5 / 2);
            }
            paramInt1 = j;
            if (i != i4 - 1)
            {
              ((LayoutParams)localObject2).rightMargin = (i5 / 2);
              paramInt1 = j;
            }
          }
        }
        i++;
      }
      paramInt1 = j;
    }
    j = 0;
    if (paramInt1 != 0) {
      for (paramInt1 = j; paramInt1 < i4; paramInt1++)
      {
        localObject2 = getChildAt(paramInt1);
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        if (((LayoutParams)localObject1).expanded) {
          ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(((LayoutParams)localObject1).cellsUsed * i3 + ((LayoutParams)localObject1).extraPixels, 1073741824), i1);
        }
      }
    }
    if (i8 != 1073741824) {
      paramInt1 = paramInt2;
    } else {
      paramInt1 = k;
    }
    setMeasuredDimension(m, paramInt1);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if ((paramLayoutParams != null) && ((paramLayoutParams instanceof LayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void dismissPopupMenus()
  {
    c localc = this.mPresenter;
    if (localc != null) {
      localc.h();
    }
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    return false;
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    LayoutParams localLayoutParams = new LayoutParams(-2, -2);
    localLayoutParams.gravity = 16;
    return localLayoutParams;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (paramLayoutParams != null)
    {
      if ((paramLayoutParams instanceof LayoutParams)) {
        paramLayoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
      } else {
        paramLayoutParams = new LayoutParams(paramLayoutParams);
      }
      if (paramLayoutParams.gravity <= 0) {
        paramLayoutParams.gravity = 16;
      }
      return paramLayoutParams;
    }
    return generateDefaultLayoutParams();
  }
  
  public LayoutParams generateOverflowButtonLayoutParams()
  {
    LayoutParams localLayoutParams = generateDefaultLayoutParams();
    localLayoutParams.isOverflowButton = true;
    return localLayoutParams;
  }
  
  public Menu getMenu()
  {
    if (this.mMenu == null)
    {
      Object localObject = getContext();
      this.mMenu = new h((Context)localObject);
      this.mMenu.a(new b());
      this.mPresenter = new c((Context)localObject);
      this.mPresenter.b(true);
      c localc = this.mPresenter;
      localObject = this.mActionMenuPresenterCallback;
      if (localObject == null) {
        localObject = new a();
      }
      localc.a((o.a)localObject);
      this.mMenu.a(this.mPresenter, this.mPopupContext);
      this.mPresenter.a(this);
    }
    return this.mMenu;
  }
  
  public Drawable getOverflowIcon()
  {
    getMenu();
    return this.mPresenter.e();
  }
  
  public int getPopupTheme()
  {
    return this.mPopupTheme;
  }
  
  public int getWindowAnimations()
  {
    return 0;
  }
  
  protected boolean hasSupportDividerBeforeChildAt(int paramInt)
  {
    boolean bool1 = false;
    if (paramInt == 0) {
      return false;
    }
    View localView1 = getChildAt(paramInt - 1);
    View localView2 = getChildAt(paramInt);
    boolean bool2 = bool1;
    if (paramInt < getChildCount())
    {
      bool2 = bool1;
      if ((localView1 instanceof ActionMenuChildView)) {
        bool2 = false | ((ActionMenuChildView)localView1).needsDividerAfter();
      }
    }
    bool1 = bool2;
    if (paramInt > 0)
    {
      bool1 = bool2;
      if ((localView2 instanceof ActionMenuChildView)) {
        bool1 = bool2 | ((ActionMenuChildView)localView2).needsDividerBefore();
      }
    }
    return bool1;
  }
  
  public boolean hideOverflowMenu()
  {
    c localc = this.mPresenter;
    boolean bool;
    if ((localc != null) && (localc.g())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void initialize(h paramh)
  {
    this.mMenu = paramh;
  }
  
  public boolean invokeItem(j paramj)
  {
    return this.mMenu.a(paramj, 0);
  }
  
  public boolean isOverflowMenuShowPending()
  {
    c localc = this.mPresenter;
    boolean bool;
    if ((localc != null) && (localc.k())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOverflowMenuShowing()
  {
    c localc = this.mPresenter;
    boolean bool;
    if ((localc != null) && (localc.j())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOverflowReserved()
  {
    return this.mReserveOverflow;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    paramConfiguration = this.mPresenter;
    if (paramConfiguration != null)
    {
      paramConfiguration.a(false);
      if (this.mPresenter.j())
      {
        this.mPresenter.g();
        this.mPresenter.f();
      }
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    dismissPopupMenus();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mFormatItems)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    int i = getChildCount();
    int j = (paramInt4 - paramInt2) / 2;
    int k = getDividerWidth();
    int m = paramInt3 - paramInt1;
    paramInt1 = getPaddingRight();
    paramInt2 = getPaddingLeft();
    paramBoolean = ViewUtils.isLayoutRtl(this);
    paramInt1 = m - paramInt1 - paramInt2;
    paramInt3 = 0;
    paramInt4 = 0;
    paramInt2 = 0;
    Object localObject1;
    Object localObject2;
    int n;
    int i2;
    while (paramInt3 < i)
    {
      localObject1 = getChildAt(paramInt3);
      if (((View)localObject1).getVisibility() != 8)
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (((LayoutParams)localObject2).isOverflowButton)
        {
          n = ((View)localObject1).getMeasuredWidth();
          paramInt4 = n;
          if (hasSupportDividerBeforeChildAt(paramInt3)) {
            paramInt4 = n + k;
          }
          int i1 = ((View)localObject1).getMeasuredHeight();
          if (paramBoolean)
          {
            n = getPaddingLeft() + ((LayoutParams)localObject2).leftMargin;
            i2 = n + paramInt4;
          }
          else
          {
            i2 = getWidth() - getPaddingRight() - ((LayoutParams)localObject2).rightMargin;
            n = i2 - paramInt4;
          }
          int i3 = j - i1 / 2;
          ((View)localObject1).layout(n, i3, i2, i1 + i3);
          paramInt1 -= paramInt4;
          paramInt4 = 1;
        }
        else
        {
          paramInt1 -= ((View)localObject1).getMeasuredWidth() + ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin;
          hasSupportDividerBeforeChildAt(paramInt3);
          paramInt2++;
        }
      }
      paramInt3++;
    }
    if ((i == 1) && (paramInt4 == 0))
    {
      localObject1 = getChildAt(0);
      paramInt1 = ((View)localObject1).getMeasuredWidth();
      paramInt2 = ((View)localObject1).getMeasuredHeight();
      paramInt3 = m / 2 - paramInt1 / 2;
      paramInt4 = j - paramInt2 / 2;
      ((View)localObject1).layout(paramInt3, paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
      return;
    }
    paramInt2 -= (paramInt4 ^ 0x1);
    if (paramInt2 > 0) {
      paramInt1 /= paramInt2;
    } else {
      paramInt1 = 0;
    }
    paramInt2 = 0;
    paramInt3 = 0;
    paramInt4 = Math.max(0, paramInt1);
    if (paramBoolean)
    {
      paramInt2 = getWidth() - getPaddingRight();
      paramInt1 = paramInt3;
      while (paramInt1 < i)
      {
        localObject2 = getChildAt(paramInt1);
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        paramInt3 = paramInt2;
        if (((View)localObject2).getVisibility() != 8) {
          if (((LayoutParams)localObject1).isOverflowButton)
          {
            paramInt3 = paramInt2;
          }
          else
          {
            i2 = paramInt2 - ((LayoutParams)localObject1).rightMargin;
            n = ((View)localObject2).getMeasuredWidth();
            paramInt2 = ((View)localObject2).getMeasuredHeight();
            paramInt3 = j - paramInt2 / 2;
            ((View)localObject2).layout(i2 - n, paramInt3, i2, paramInt2 + paramInt3);
            paramInt3 = i2 - (n + ((LayoutParams)localObject1).leftMargin + paramInt4);
          }
        }
        paramInt1++;
        paramInt2 = paramInt3;
      }
    }
    paramInt3 = getPaddingLeft();
    paramInt1 = paramInt2;
    while (paramInt1 < i)
    {
      localObject2 = getChildAt(paramInt1);
      localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
      paramInt2 = paramInt3;
      if (((View)localObject2).getVisibility() != 8) {
        if (((LayoutParams)localObject1).isOverflowButton)
        {
          paramInt2 = paramInt3;
        }
        else
        {
          paramInt3 += ((LayoutParams)localObject1).leftMargin;
          n = ((View)localObject2).getMeasuredWidth();
          paramInt2 = ((View)localObject2).getMeasuredHeight();
          i2 = j - paramInt2 / 2;
          ((View)localObject2).layout(paramInt3, i2, paramInt3 + n, paramInt2 + i2);
          paramInt2 = paramInt3 + (n + ((LayoutParams)localObject1).rightMargin + paramInt4);
        }
      }
      paramInt1++;
      paramInt3 = paramInt2;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool1 = this.mFormatItems;
    boolean bool2;
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mFormatItems = bool2;
    if (bool1 != this.mFormatItems) {
      this.mFormatItemsWidth = 0;
    }
    int i = View.MeasureSpec.getSize(paramInt1);
    Object localObject;
    if (this.mFormatItems)
    {
      localObject = this.mMenu;
      if ((localObject != null) && (i != this.mFormatItemsWidth))
      {
        this.mFormatItemsWidth = i;
        ((h)localObject).a(true);
      }
    }
    int j = getChildCount();
    if ((this.mFormatItems) && (j > 0))
    {
      onMeasureExactFormat(paramInt1, paramInt2);
    }
    else
    {
      for (i = 0; i < j; i++)
      {
        localObject = (LayoutParams)getChildAt(i).getLayoutParams();
        ((LayoutParams)localObject).rightMargin = 0;
        ((LayoutParams)localObject).leftMargin = 0;
      }
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public h peekMenu()
  {
    return this.mMenu;
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean)
  {
    this.mPresenter.c(paramBoolean);
  }
  
  public void setMenuCallbacks(o.a parama, h.a parama1)
  {
    this.mActionMenuPresenterCallback = parama;
    this.mMenuBuilderCallback = parama1;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable)
  {
    getMenu();
    this.mPresenter.a(paramDrawable);
  }
  
  public void setOverflowReserved(boolean paramBoolean)
  {
    this.mReserveOverflow = paramBoolean;
  }
  
  public void setPopupTheme(int paramInt)
  {
    if (this.mPopupTheme != paramInt)
    {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      } else {
        this.mPopupContext = new ContextThemeWrapper(getContext(), paramInt);
      }
    }
  }
  
  public void setPresenter(c paramc)
  {
    this.mPresenter = paramc;
    this.mPresenter.a(this);
  }
  
  public boolean showOverflowMenu()
  {
    c localc = this.mPresenter;
    boolean bool;
    if ((localc != null) && (localc.f())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static abstract interface ActionMenuChildView
  {
    public abstract boolean needsDividerAfter();
    
    public abstract boolean needsDividerBefore();
  }
  
  public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams
  {
    @ViewDebug.ExportedProperty
    public int cellsUsed;
    @ViewDebug.ExportedProperty
    public boolean expandable;
    boolean expanded;
    @ViewDebug.ExportedProperty
    public int extraPixels;
    @ViewDebug.ExportedProperty
    public boolean isOverflowButton;
    @ViewDebug.ExportedProperty
    public boolean preventEdgeOffset;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.isOverflowButton = false;
    }
    
    LayoutParams(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      super(paramInt2);
      this.isOverflowButton = paramBoolean;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.isOverflowButton = paramLayoutParams.isOverflowButton;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
  
  private static class a
    implements o.a
  {
    public void a(h paramh, boolean paramBoolean) {}
    
    public boolean a(h paramh)
    {
      return false;
    }
  }
  
  private class b
    implements h.a
  {
    b() {}
    
    public void a(h paramh)
    {
      if (ActionMenuView.this.mMenuBuilderCallback != null) {
        ActionMenuView.this.mMenuBuilderCallback.a(paramh);
      }
    }
    
    public boolean a(h paramh, MenuItem paramMenuItem)
    {
      boolean bool;
      if ((ActionMenuView.this.mOnMenuItemClickListener != null) && (ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(paramMenuItem))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              ~/android/support/v7/widget/ActionMenuView.class
 *
 * Reversed by:           J
 */