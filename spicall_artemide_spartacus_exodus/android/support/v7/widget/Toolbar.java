package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.j;
import android.support.v7.c.a.b;
import android.support.v7.view.g;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.o;
import android.support.v7.view.menu.o.a;
import android.support.v7.view.menu.u;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
  extends ViewGroup
{
  private static final String TAG = "Toolbar";
  private o.a mActionMenuPresenterCallback;
  int mButtonGravity;
  ImageButton mCollapseButtonView;
  private CharSequence mCollapseDescription;
  private Drawable mCollapseIcon;
  private boolean mCollapsible;
  private int mContentInsetEndWithActions;
  private int mContentInsetStartWithNavigation;
  private aa mContentInsets;
  private boolean mEatingHover;
  private boolean mEatingTouch;
  View mExpandedActionView;
  private a mExpandedMenuPresenter;
  private int mGravity = 8388627;
  private final ArrayList<View> mHiddenViews = new ArrayList();
  private ImageView mLogoView;
  private int mMaxButtonHeight;
  private h.a mMenuBuilderCallback;
  private ActionMenuView mMenuView;
  private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener()
  {
    public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
    {
      if (Toolbar.this.mOnMenuItemClickListener != null) {
        return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(paramAnonymousMenuItem);
      }
      return false;
    }
  };
  private ImageButton mNavButtonView;
  OnMenuItemClickListener mOnMenuItemClickListener;
  private c mOuterActionMenuPresenter;
  private Context mPopupContext;
  private int mPopupTheme;
  private final Runnable mShowOverflowMenuRunnable = new Runnable()
  {
    public void run()
    {
      Toolbar.this.showOverflowMenu();
    }
  };
  private CharSequence mSubtitleText;
  private int mSubtitleTextAppearance;
  private int mSubtitleTextColor;
  private TextView mSubtitleTextView;
  private final int[] mTempMargins = new int[2];
  private final ArrayList<View> mTempViews = new ArrayList();
  private int mTitleMarginBottom;
  private int mTitleMarginEnd;
  private int mTitleMarginStart;
  private int mTitleMarginTop;
  private CharSequence mTitleText;
  private int mTitleTextAppearance;
  private int mTitleTextColor;
  private TextView mTitleTextView;
  private ToolbarWidgetWrapper mWrapper;
  
  public Toolbar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, android.support.v7.a.a.a.toolbarStyle);
  }
  
  public Toolbar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, a.j.Toolbar, paramInt, 0);
    this.mTitleTextAppearance = paramContext.getResourceId(a.j.Toolbar_titleTextAppearance, 0);
    this.mSubtitleTextAppearance = paramContext.getResourceId(a.j.Toolbar_subtitleTextAppearance, 0);
    this.mGravity = paramContext.getInteger(a.j.Toolbar_android_gravity, this.mGravity);
    this.mButtonGravity = paramContext.getInteger(a.j.Toolbar_buttonGravity, 48);
    int i = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMargin, 0);
    paramInt = i;
    if (paramContext.hasValue(a.j.Toolbar_titleMargins)) {
      paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMargins, i);
    }
    this.mTitleMarginBottom = paramInt;
    this.mTitleMarginTop = paramInt;
    this.mTitleMarginEnd = paramInt;
    this.mTitleMarginStart = paramInt;
    paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMarginStart, -1);
    if (paramInt >= 0) {
      this.mTitleMarginStart = paramInt;
    }
    paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMarginEnd, -1);
    if (paramInt >= 0) {
      this.mTitleMarginEnd = paramInt;
    }
    paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMarginTop, -1);
    if (paramInt >= 0) {
      this.mTitleMarginTop = paramInt;
    }
    paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_titleMarginBottom, -1);
    if (paramInt >= 0) {
      this.mTitleMarginBottom = paramInt;
    }
    this.mMaxButtonHeight = paramContext.getDimensionPixelSize(a.j.Toolbar_maxButtonHeight, -1);
    int j = paramContext.getDimensionPixelOffset(a.j.Toolbar_contentInsetStart, Integer.MIN_VALUE);
    paramInt = paramContext.getDimensionPixelOffset(a.j.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
    int k = paramContext.getDimensionPixelSize(a.j.Toolbar_contentInsetLeft, 0);
    i = paramContext.getDimensionPixelSize(a.j.Toolbar_contentInsetRight, 0);
    ensureContentInsets();
    this.mContentInsets.b(k, i);
    if ((j != Integer.MIN_VALUE) || (paramInt != Integer.MIN_VALUE)) {
      this.mContentInsets.a(j, paramInt);
    }
    this.mContentInsetStartWithNavigation = paramContext.getDimensionPixelOffset(a.j.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
    this.mContentInsetEndWithActions = paramContext.getDimensionPixelOffset(a.j.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
    this.mCollapseIcon = paramContext.getDrawable(a.j.Toolbar_collapseIcon);
    this.mCollapseDescription = paramContext.getText(a.j.Toolbar_collapseContentDescription);
    paramAttributeSet = paramContext.getText(a.j.Toolbar_title);
    if (!TextUtils.isEmpty(paramAttributeSet)) {
      setTitle(paramAttributeSet);
    }
    paramAttributeSet = paramContext.getText(a.j.Toolbar_subtitle);
    if (!TextUtils.isEmpty(paramAttributeSet)) {
      setSubtitle(paramAttributeSet);
    }
    this.mPopupContext = getContext();
    setPopupTheme(paramContext.getResourceId(a.j.Toolbar_popupTheme, 0));
    paramAttributeSet = paramContext.getDrawable(a.j.Toolbar_navigationIcon);
    if (paramAttributeSet != null) {
      setNavigationIcon(paramAttributeSet);
    }
    paramAttributeSet = paramContext.getText(a.j.Toolbar_navigationContentDescription);
    if (!TextUtils.isEmpty(paramAttributeSet)) {
      setNavigationContentDescription(paramAttributeSet);
    }
    paramAttributeSet = paramContext.getDrawable(a.j.Toolbar_logo);
    if (paramAttributeSet != null) {
      setLogo(paramAttributeSet);
    }
    paramAttributeSet = paramContext.getText(a.j.Toolbar_logoDescription);
    if (!TextUtils.isEmpty(paramAttributeSet)) {
      setLogoDescription(paramAttributeSet);
    }
    if (paramContext.hasValue(a.j.Toolbar_titleTextColor)) {
      setTitleTextColor(paramContext.getColor(a.j.Toolbar_titleTextColor, -1));
    }
    if (paramContext.hasValue(a.j.Toolbar_subtitleTextColor)) {
      setSubtitleTextColor(paramContext.getColor(a.j.Toolbar_subtitleTextColor, -1));
    }
    paramContext.recycle();
  }
  
  private void addCustomViewsWithGravity(List<View> paramList, int paramInt)
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = 0;
    if (i == 1) {
      i = 1;
    } else {
      i = 0;
    }
    int k = getChildCount();
    int m = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
    paramList.clear();
    paramInt = j;
    Object localObject1;
    Object localObject2;
    if (i != 0) {
      for (paramInt = k - 1; paramInt >= 0; paramInt--)
      {
        localObject1 = getChildAt(paramInt);
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if ((((LayoutParams)localObject2).mViewType == 0) && (shouldLayout((View)localObject1)) && (getChildHorizontalGravity(((LayoutParams)localObject2).gravity) == m)) {
          paramList.add(localObject1);
        }
      }
    }
    while (paramInt < k)
    {
      localObject2 = getChildAt(paramInt);
      localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
      if ((((LayoutParams)localObject1).mViewType == 0) && (shouldLayout((View)localObject2)) && (getChildHorizontalGravity(((LayoutParams)localObject1).gravity) == m)) {
        paramList.add(localObject2);
      }
      paramInt++;
    }
  }
  
  private void addSystemView(View paramView, boolean paramBoolean)
  {
    Object localObject = paramView.getLayoutParams();
    if (localObject == null) {
      localObject = generateDefaultLayoutParams();
    } else if (!checkLayoutParams((ViewGroup.LayoutParams)localObject)) {
      localObject = generateLayoutParams((ViewGroup.LayoutParams)localObject);
    } else {
      localObject = (LayoutParams)localObject;
    }
    ((LayoutParams)localObject).mViewType = 1;
    if ((paramBoolean) && (this.mExpandedActionView != null))
    {
      paramView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mHiddenViews.add(paramView);
    }
    else
    {
      addView(paramView, (ViewGroup.LayoutParams)localObject);
    }
  }
  
  private void ensureContentInsets()
  {
    if (this.mContentInsets == null) {
      this.mContentInsets = new aa();
    }
  }
  
  private void ensureLogoView()
  {
    if (this.mLogoView == null) {
      this.mLogoView = new AppCompatImageView(getContext());
    }
  }
  
  private void ensureMenu()
  {
    ensureMenuView();
    if (this.mMenuView.peekMenu() == null)
    {
      h localh = (h)this.mMenuView.getMenu();
      if (this.mExpandedMenuPresenter == null) {
        this.mExpandedMenuPresenter = new a();
      }
      this.mMenuView.setExpandedActionViewsExclusive(true);
      localh.a(this.mExpandedMenuPresenter, this.mPopupContext);
    }
  }
  
  private void ensureMenuView()
  {
    if (this.mMenuView == null)
    {
      this.mMenuView = new ActionMenuView(getContext());
      this.mMenuView.setPopupTheme(this.mPopupTheme);
      this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
      this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
      LayoutParams localLayoutParams = generateDefaultLayoutParams();
      localLayoutParams.gravity = (0x800005 | this.mButtonGravity & 0x70);
      this.mMenuView.setLayoutParams(localLayoutParams);
      addSystemView(this.mMenuView, false);
    }
  }
  
  private void ensureNavButtonView()
  {
    if (this.mNavButtonView == null)
    {
      this.mNavButtonView = new AppCompatImageButton(getContext(), null, android.support.v7.a.a.a.toolbarNavigationButtonStyle);
      LayoutParams localLayoutParams = generateDefaultLayoutParams();
      localLayoutParams.gravity = (0x800003 | this.mButtonGravity & 0x70);
      this.mNavButtonView.setLayoutParams(localLayoutParams);
    }
  }
  
  private int getChildHorizontalGravity(int paramInt)
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = GravityCompat.getAbsoluteGravity(paramInt, i) & 0x7;
    if (j != 1)
    {
      paramInt = 3;
      if ((j != 3) && (j != 5))
      {
        if (i == 1) {
          paramInt = 5;
        }
        return paramInt;
      }
    }
    return j;
  }
  
  private int getChildTop(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = paramView.getMeasuredHeight();
    if (paramInt > 0) {
      paramInt = (i - paramInt) / 2;
    } else {
      paramInt = 0;
    }
    int j = getChildVerticalGravity(localLayoutParams.gravity);
    if (j != 48)
    {
      if (j != 80)
      {
        int k = getPaddingTop();
        paramInt = getPaddingBottom();
        int m = getHeight();
        j = (m - k - paramInt - i) / 2;
        if (j < localLayoutParams.topMargin)
        {
          paramInt = localLayoutParams.topMargin;
        }
        else
        {
          i = m - paramInt - i - j - k;
          paramInt = j;
          if (i < localLayoutParams.bottomMargin) {
            paramInt = Math.max(0, j - (localLayoutParams.bottomMargin - i));
          }
        }
        return k + paramInt;
      }
      return getHeight() - getPaddingBottom() - i - localLayoutParams.bottomMargin - paramInt;
    }
    return getPaddingTop() - paramInt;
  }
  
  private int getChildVerticalGravity(int paramInt)
  {
    paramInt &= 0x70;
    if ((paramInt != 16) && (paramInt != 48) && (paramInt != 80)) {
      return this.mGravity & 0x70;
    }
    return paramInt;
  }
  
  private int getHorizontalMargins(View paramView)
  {
    paramView = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(paramView) + MarginLayoutParamsCompat.getMarginEnd(paramView);
  }
  
  private MenuInflater getMenuInflater()
  {
    return new g(getContext());
  }
  
  private int getVerticalMargins(View paramView)
  {
    paramView = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    return paramView.topMargin + paramView.bottomMargin;
  }
  
  private int getViewListMeasuredWidth(List<View> paramList, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    int j = paramArrayOfInt[1];
    int k = paramList.size();
    int m = 0;
    int n = 0;
    while (m < k)
    {
      paramArrayOfInt = (View)paramList.get(m);
      LayoutParams localLayoutParams = (LayoutParams)paramArrayOfInt.getLayoutParams();
      i = localLayoutParams.leftMargin - i;
      j = localLayoutParams.rightMargin - j;
      int i1 = Math.max(0, i);
      int i2 = Math.max(0, j);
      i = Math.max(0, -i);
      j = Math.max(0, -j);
      n += i1 + paramArrayOfInt.getMeasuredWidth() + i2;
      m++;
    }
    return n;
  }
  
  private boolean isChildOrHidden(View paramView)
  {
    boolean bool;
    if ((paramView.getParent() != this) && (!this.mHiddenViews.contains(paramView))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isCustomView(View paramView)
  {
    boolean bool;
    if (((LayoutParams)paramView.getLayoutParams()).mViewType == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private int layoutChildLeft(View paramView, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = localLayoutParams.leftMargin - paramArrayOfInt[0];
    paramInt1 += Math.max(0, i);
    paramArrayOfInt[0] = Math.max(0, -i);
    i = getChildTop(paramView, paramInt2);
    paramInt2 = paramView.getMeasuredWidth();
    paramView.layout(paramInt1, i, paramInt1 + paramInt2, paramView.getMeasuredHeight() + i);
    return paramInt1 + (paramInt2 + localLayoutParams.rightMargin);
  }
  
  private int layoutChildRight(View paramView, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = localLayoutParams.rightMargin - paramArrayOfInt[1];
    paramInt1 -= Math.max(0, i);
    paramArrayOfInt[1] = Math.max(0, -i);
    paramInt2 = getChildTop(paramView, paramInt2);
    i = paramView.getMeasuredWidth();
    paramView.layout(paramInt1 - i, paramInt2, paramInt1, paramView.getMeasuredHeight() + paramInt2);
    return paramInt1 - (i + localLayoutParams.leftMargin);
  }
  
  private int measureChildCollapseMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int i = localMarginLayoutParams.leftMargin - paramArrayOfInt[0];
    int j = localMarginLayoutParams.rightMargin - paramArrayOfInt[1];
    int k = Math.max(0, i) + Math.max(0, j);
    paramArrayOfInt[0] = Math.max(0, -i);
    paramArrayOfInt[1] = Math.max(0, -j);
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + k + paramInt2, localMarginLayoutParams.width), getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin + paramInt4, localMarginLayoutParams.height));
    return paramView.getMeasuredWidth() + k;
  }
  
  private void measureChildConstrained(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    int i = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin + paramInt2, localMarginLayoutParams.width);
    paramInt2 = getChildMeasureSpec(paramInt3, getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin + paramInt4, localMarginLayoutParams.height);
    paramInt3 = View.MeasureSpec.getMode(paramInt2);
    paramInt1 = paramInt2;
    if (paramInt3 != 1073741824)
    {
      paramInt1 = paramInt2;
      if (paramInt5 >= 0)
      {
        paramInt1 = paramInt5;
        if (paramInt3 != 0) {
          paramInt1 = Math.min(View.MeasureSpec.getSize(paramInt2), paramInt5);
        }
        paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824);
      }
    }
    paramView.measure(i, paramInt1);
  }
  
  private void postShowOverflowMenu()
  {
    removeCallbacks(this.mShowOverflowMenuRunnable);
    post(this.mShowOverflowMenuRunnable);
  }
  
  private boolean shouldCollapse()
  {
    if (!this.mCollapsible) {
      return false;
    }
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if ((shouldLayout(localView)) && (localView.getMeasuredWidth() > 0) && (localView.getMeasuredHeight() > 0)) {
        return false;
      }
    }
    return true;
  }
  
  private boolean shouldLayout(View paramView)
  {
    boolean bool;
    if ((paramView != null) && (paramView.getParent() == this) && (paramView.getVisibility() != 8)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void addChildrenForExpandedActionView()
  {
    for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
      addView((View)this.mHiddenViews.get(i));
    }
    this.mHiddenViews.clear();
  }
  
  public boolean canShowOverflowMenu()
  {
    if (getVisibility() == 0)
    {
      ActionMenuView localActionMenuView = this.mMenuView;
      if ((localActionMenuView != null) && (localActionMenuView.isOverflowReserved())) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if ((super.checkLayoutParams(paramLayoutParams)) && ((paramLayoutParams instanceof LayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void collapseActionView()
  {
    Object localObject = this.mExpandedMenuPresenter;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((a)localObject).b;
    }
    if (localObject != null) {
      ((j)localObject).collapseActionView();
    }
  }
  
  public void dismissPopupMenus()
  {
    ActionMenuView localActionMenuView = this.mMenuView;
    if (localActionMenuView != null) {
      localActionMenuView.dismissPopupMenus();
    }
  }
  
  void ensureCollapseButtonView()
  {
    if (this.mCollapseButtonView == null)
    {
      this.mCollapseButtonView = new AppCompatImageButton(getContext(), null, android.support.v7.a.a.a.toolbarNavigationButtonStyle);
      this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
      this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
      LayoutParams localLayoutParams = generateDefaultLayoutParams();
      localLayoutParams.gravity = (0x800003 | this.mButtonGravity & 0x70);
      localLayoutParams.mViewType = 2;
      this.mCollapseButtonView.setLayoutParams(localLayoutParams);
      this.mCollapseButtonView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Toolbar.this.collapseActionView();
        }
      });
    }
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof LayoutParams)) {
      return new LayoutParams((LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof android.support.v7.app.a.a)) {
      return new LayoutParams((android.support.v7.app.a.a)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getContentInsetEnd()
  {
    aa localaa = this.mContentInsets;
    int i;
    if (localaa != null) {
      i = localaa.d();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getContentInsetEndWithActions()
  {
    int i = this.mContentInsetEndWithActions;
    if (i == Integer.MIN_VALUE) {
      i = getContentInsetEnd();
    }
    return i;
  }
  
  public int getContentInsetLeft()
  {
    aa localaa = this.mContentInsets;
    int i;
    if (localaa != null) {
      i = localaa.a();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getContentInsetRight()
  {
    aa localaa = this.mContentInsets;
    int i;
    if (localaa != null) {
      i = localaa.b();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getContentInsetStart()
  {
    aa localaa = this.mContentInsets;
    int i;
    if (localaa != null) {
      i = localaa.c();
    } else {
      i = 0;
    }
    return i;
  }
  
  public int getContentInsetStartWithNavigation()
  {
    int i = this.mContentInsetStartWithNavigation;
    if (i == Integer.MIN_VALUE) {
      i = getContentInsetStart();
    }
    return i;
  }
  
  public int getCurrentContentInsetEnd()
  {
    Object localObject = this.mMenuView;
    int i;
    if (localObject != null)
    {
      localObject = ((ActionMenuView)localObject).peekMenu();
      if ((localObject != null) && (((h)localObject).hasVisibleItems())) {
        i = 1;
      } else {
        i = 0;
      }
    }
    else
    {
      i = 0;
    }
    if (i != 0) {
      i = Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
    } else {
      i = getContentInsetEnd();
    }
    return i;
  }
  
  public int getCurrentContentInsetLeft()
  {
    int i;
    if (ViewCompat.getLayoutDirection(this) == 1) {
      i = getCurrentContentInsetEnd();
    } else {
      i = getCurrentContentInsetStart();
    }
    return i;
  }
  
  public int getCurrentContentInsetRight()
  {
    int i;
    if (ViewCompat.getLayoutDirection(this) == 1) {
      i = getCurrentContentInsetStart();
    } else {
      i = getCurrentContentInsetEnd();
    }
    return i;
  }
  
  public int getCurrentContentInsetStart()
  {
    int i;
    if (getNavigationIcon() != null) {
      i = Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
    } else {
      i = getContentInsetStart();
    }
    return i;
  }
  
  public Drawable getLogo()
  {
    Object localObject = this.mLogoView;
    if (localObject != null) {
      localObject = ((ImageView)localObject).getDrawable();
    } else {
      localObject = null;
    }
    return (Drawable)localObject;
  }
  
  public CharSequence getLogoDescription()
  {
    Object localObject = this.mLogoView;
    if (localObject != null) {
      localObject = ((ImageView)localObject).getContentDescription();
    } else {
      localObject = null;
    }
    return (CharSequence)localObject;
  }
  
  public Menu getMenu()
  {
    ensureMenu();
    return this.mMenuView.getMenu();
  }
  
  public CharSequence getNavigationContentDescription()
  {
    Object localObject = this.mNavButtonView;
    if (localObject != null) {
      localObject = ((ImageButton)localObject).getContentDescription();
    } else {
      localObject = null;
    }
    return (CharSequence)localObject;
  }
  
  public Drawable getNavigationIcon()
  {
    Object localObject = this.mNavButtonView;
    if (localObject != null) {
      localObject = ((ImageButton)localObject).getDrawable();
    } else {
      localObject = null;
    }
    return (Drawable)localObject;
  }
  
  c getOuterActionMenuPresenter()
  {
    return this.mOuterActionMenuPresenter;
  }
  
  public Drawable getOverflowIcon()
  {
    ensureMenu();
    return this.mMenuView.getOverflowIcon();
  }
  
  Context getPopupContext()
  {
    return this.mPopupContext;
  }
  
  public int getPopupTheme()
  {
    return this.mPopupTheme;
  }
  
  public CharSequence getSubtitle()
  {
    return this.mSubtitleText;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitleText;
  }
  
  public int getTitleMarginBottom()
  {
    return this.mTitleMarginBottom;
  }
  
  public int getTitleMarginEnd()
  {
    return this.mTitleMarginEnd;
  }
  
  public int getTitleMarginStart()
  {
    return this.mTitleMarginStart;
  }
  
  public int getTitleMarginTop()
  {
    return this.mTitleMarginTop;
  }
  
  public DecorToolbar getWrapper()
  {
    if (this.mWrapper == null) {
      this.mWrapper = new ToolbarWidgetWrapper(this, true);
    }
    return this.mWrapper;
  }
  
  public boolean hasExpandedActionView()
  {
    a locala = this.mExpandedMenuPresenter;
    boolean bool;
    if ((locala != null) && (locala.b != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hideOverflowMenu()
  {
    ActionMenuView localActionMenuView = this.mMenuView;
    boolean bool;
    if ((localActionMenuView != null) && (localActionMenuView.hideOverflowMenu())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void inflateMenu(int paramInt)
  {
    getMenuInflater().inflate(paramInt, getMenu());
  }
  
  public boolean isOverflowMenuShowPending()
  {
    ActionMenuView localActionMenuView = this.mMenuView;
    boolean bool;
    if ((localActionMenuView != null) && (localActionMenuView.isOverflowMenuShowPending())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOverflowMenuShowing()
  {
    ActionMenuView localActionMenuView = this.mMenuView;
    boolean bool;
    if ((localActionMenuView != null) && (localActionMenuView.isOverflowMenuShowing())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isTitleTruncated()
  {
    Object localObject = this.mTitleTextView;
    if (localObject == null) {
      return false;
    }
    localObject = ((TextView)localObject).getLayout();
    if (localObject == null) {
      return false;
    }
    int i = ((Layout)localObject).getLineCount();
    for (int j = 0; j < i; j++) {
      if (((Layout)localObject).getEllipsisCount(j) > 0) {
        return true;
      }
    }
    return false;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    removeCallbacks(this.mShowOverflowMenuRunnable);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    if (i == 9) {
      this.mEatingHover = false;
    }
    if (!this.mEatingHover)
    {
      boolean bool = super.onHoverEvent(paramMotionEvent);
      if ((i == 9) && (!bool)) {
        this.mEatingHover = true;
      }
    }
    if ((i == 10) || (i == 3)) {
      this.mEatingHover = false;
    }
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 1) {
      i = 1;
    } else {
      i = 0;
    }
    int j = getWidth();
    int k = getHeight();
    paramInt3 = getPaddingLeft();
    int m = getPaddingRight();
    int n = getPaddingTop();
    int i1 = getPaddingBottom();
    int i2 = j - m;
    int[] arrayOfInt = this.mTempMargins;
    arrayOfInt[1] = 0;
    arrayOfInt[0] = 0;
    paramInt1 = ViewCompat.getMinimumHeight(this);
    if (paramInt1 >= 0) {
      paramInt4 = Math.min(paramInt1, paramInt4 - paramInt2);
    } else {
      paramInt4 = 0;
    }
    if (shouldLayout(this.mNavButtonView))
    {
      if (i != 0)
      {
        paramInt2 = layoutChildRight(this.mNavButtonView, i2, arrayOfInt, paramInt4);
        paramInt1 = paramInt3;
      }
      else
      {
        paramInt1 = layoutChildLeft(this.mNavButtonView, paramInt3, arrayOfInt, paramInt4);
        paramInt2 = i2;
      }
    }
    else
    {
      paramInt1 = paramInt3;
      paramInt2 = i2;
    }
    int i3 = paramInt1;
    int i4 = paramInt2;
    if (shouldLayout(this.mCollapseButtonView)) {
      if (i != 0)
      {
        i4 = layoutChildRight(this.mCollapseButtonView, paramInt2, arrayOfInt, paramInt4);
        i3 = paramInt1;
      }
      else
      {
        i3 = layoutChildLeft(this.mCollapseButtonView, paramInt1, arrayOfInt, paramInt4);
        i4 = paramInt2;
      }
    }
    paramInt2 = i3;
    paramInt1 = i4;
    if (shouldLayout(this.mMenuView)) {
      if (i != 0)
      {
        paramInt2 = layoutChildLeft(this.mMenuView, i3, arrayOfInt, paramInt4);
        paramInt1 = i4;
      }
      else
      {
        paramInt1 = layoutChildRight(this.mMenuView, i4, arrayOfInt, paramInt4);
        paramInt2 = i3;
      }
    }
    i4 = getCurrentContentInsetLeft();
    i3 = getCurrentContentInsetRight();
    arrayOfInt[0] = Math.max(0, i4 - paramInt2);
    arrayOfInt[1] = Math.max(0, i3 - (i2 - paramInt1));
    paramInt2 = Math.max(paramInt2, i4);
    i3 = Math.min(paramInt1, i2 - i3);
    i4 = paramInt2;
    paramInt1 = i3;
    if (shouldLayout(this.mExpandedActionView)) {
      if (i != 0)
      {
        paramInt1 = layoutChildRight(this.mExpandedActionView, i3, arrayOfInt, paramInt4);
        i4 = paramInt2;
      }
      else
      {
        i4 = layoutChildLeft(this.mExpandedActionView, paramInt2, arrayOfInt, paramInt4);
        paramInt1 = i3;
      }
    }
    paramInt2 = i4;
    i3 = paramInt1;
    if (shouldLayout(this.mLogoView)) {
      if (i != 0)
      {
        i3 = layoutChildRight(this.mLogoView, paramInt1, arrayOfInt, paramInt4);
        paramInt2 = i4;
      }
      else
      {
        paramInt2 = layoutChildLeft(this.mLogoView, i4, arrayOfInt, paramInt4);
        i3 = paramInt1;
      }
    }
    paramBoolean = shouldLayout(this.mTitleTextView);
    boolean bool = shouldLayout(this.mSubtitleTextView);
    Object localObject1;
    if (paramBoolean)
    {
      localObject1 = (LayoutParams)this.mTitleTextView.getLayoutParams();
      paramInt1 = ((LayoutParams)localObject1).topMargin + this.mTitleTextView.getMeasuredHeight() + ((LayoutParams)localObject1).bottomMargin + 0;
    }
    else
    {
      paramInt1 = 0;
    }
    if (bool)
    {
      localObject1 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
      paramInt1 += ((LayoutParams)localObject1).topMargin + this.mSubtitleTextView.getMeasuredHeight() + ((LayoutParams)localObject1).bottomMargin;
    }
    if ((!paramBoolean) && (!bool))
    {
      paramInt1 = paramInt2;
      paramInt2 = i3;
    }
    else
    {
      if (paramBoolean) {
        localObject1 = this.mTitleTextView;
      } else {
        localObject1 = this.mSubtitleTextView;
      }
      if (bool) {
        localObject2 = this.mSubtitleTextView;
      } else {
        localObject2 = this.mTitleTextView;
      }
      localObject1 = (LayoutParams)((View)localObject1).getLayoutParams();
      Object localObject2 = (LayoutParams)((View)localObject2).getLayoutParams();
      if (((paramBoolean) && (this.mTitleTextView.getMeasuredWidth() > 0)) || ((bool) && (this.mSubtitleTextView.getMeasuredWidth() > 0))) {
        i4 = 1;
      } else {
        i4 = 0;
      }
      i2 = this.mGravity & 0x70;
      if (i2 != 48)
      {
        if (i2 != 80)
        {
          i2 = (k - n - i1 - paramInt1) / 2;
          if (i2 < ((LayoutParams)localObject1).topMargin + this.mTitleMarginTop)
          {
            paramInt1 = ((LayoutParams)localObject1).topMargin + this.mTitleMarginTop;
          }
          else
          {
            i1 = k - i1 - paramInt1 - i2 - n;
            paramInt1 = i2;
            if (i1 < ((LayoutParams)localObject1).bottomMargin + this.mTitleMarginBottom) {
              paramInt1 = Math.max(0, i2 - (((LayoutParams)localObject2).bottomMargin + this.mTitleMarginBottom - i1));
            }
          }
          paramInt1 = n + paramInt1;
        }
        else
        {
          paramInt1 = k - i1 - ((LayoutParams)localObject2).bottomMargin - this.mTitleMarginBottom - paramInt1;
        }
      }
      else {
        paramInt1 = getPaddingTop() + ((LayoutParams)localObject1).topMargin + this.mTitleMarginTop;
      }
      i2 = paramInt2;
      if (i != 0)
      {
        if (i4 != 0) {
          paramInt2 = this.mTitleMarginStart;
        } else {
          paramInt2 = 0;
        }
        i = paramInt2 - arrayOfInt[1];
        paramInt2 = i3 - Math.max(0, i);
        arrayOfInt[1] = Math.max(0, -i);
        if (paramBoolean)
        {
          localObject1 = (LayoutParams)this.mTitleTextView.getLayoutParams();
          i = paramInt2 - this.mTitleTextView.getMeasuredWidth();
          i3 = this.mTitleTextView.getMeasuredHeight() + paramInt1;
          this.mTitleTextView.layout(i, paramInt1, paramInt2, i3);
          paramInt1 = i - this.mTitleMarginEnd;
          i = i3 + ((LayoutParams)localObject1).bottomMargin;
        }
        else
        {
          i3 = paramInt2;
          i = paramInt1;
          paramInt1 = i3;
        }
        if (bool)
        {
          localObject1 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
          n = i + ((LayoutParams)localObject1).topMargin;
          i3 = this.mSubtitleTextView.getMeasuredWidth();
          i = this.mSubtitleTextView.getMeasuredHeight();
          this.mSubtitleTextView.layout(paramInt2 - i3, n, paramInt2, i + n);
          i3 = paramInt2 - this.mTitleMarginEnd;
          i = ((LayoutParams)localObject1).bottomMargin;
        }
        else
        {
          i3 = paramInt2;
        }
        if (i4 != 0) {
          paramInt2 = Math.min(paramInt1, i3);
        }
        paramInt1 = i2;
      }
      else
      {
        if (i4 != 0) {
          paramInt2 = this.mTitleMarginStart;
        } else {
          paramInt2 = 0;
        }
        i = paramInt2 - arrayOfInt[0];
        paramInt2 = i2 + Math.max(0, i);
        arrayOfInt[0] = Math.max(0, -i);
        if (paramBoolean)
        {
          localObject1 = (LayoutParams)this.mTitleTextView.getLayoutParams();
          i = this.mTitleTextView.getMeasuredWidth() + paramInt2;
          i2 = this.mTitleTextView.getMeasuredHeight() + paramInt1;
          this.mTitleTextView.layout(paramInt2, paramInt1, i, i2);
          i += this.mTitleMarginEnd;
          paramInt1 = i2 + ((LayoutParams)localObject1).bottomMargin;
        }
        else
        {
          i = paramInt2;
        }
        if (bool)
        {
          localObject1 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
          i2 = paramInt1 + ((LayoutParams)localObject1).topMargin;
          n = this.mSubtitleTextView.getMeasuredWidth() + paramInt2;
          paramInt1 = this.mSubtitleTextView.getMeasuredHeight();
          this.mSubtitleTextView.layout(paramInt2, i2, n, paramInt1 + i2);
          i2 = n + this.mTitleMarginEnd;
          paramInt1 = ((LayoutParams)localObject1).bottomMargin;
        }
        else
        {
          i2 = paramInt2;
        }
        paramInt1 = paramInt2;
        paramInt2 = i3;
        if (i4 != 0)
        {
          paramInt1 = Math.max(i, i2);
          paramInt2 = i3;
        }
      }
    }
    i4 = paramInt3;
    i3 = 0;
    addCustomViewsWithGravity(this.mTempViews, 3);
    int i = this.mTempViews.size();
    for (paramInt3 = 0; paramInt3 < i; paramInt3++) {
      paramInt1 = layoutChildLeft((View)this.mTempViews.get(paramInt3), paramInt1, arrayOfInt, paramInt4);
    }
    addCustomViewsWithGravity(this.mTempViews, 5);
    i = this.mTempViews.size();
    for (paramInt3 = 0; paramInt3 < i; paramInt3++) {
      paramInt2 = layoutChildRight((View)this.mTempViews.get(paramInt3), paramInt2, arrayOfInt, paramInt4);
    }
    addCustomViewsWithGravity(this.mTempViews, 1);
    i = getViewListMeasuredWidth(this.mTempViews, arrayOfInt);
    paramInt3 = i4 + (j - i4 - m) / 2 - i / 2;
    i4 = i + paramInt3;
    if (paramInt3 >= paramInt1) {
      if (i4 > paramInt2) {
        paramInt1 = paramInt3 - (i4 - paramInt2);
      } else {
        paramInt1 = paramInt3;
      }
    }
    paramInt3 = this.mTempViews.size();
    for (paramInt2 = i3; paramInt2 < paramInt3; paramInt2++) {
      paramInt1 = layoutChildLeft((View)this.mTempViews.get(paramInt2), paramInt1, arrayOfInt, paramInt4);
    }
    this.mTempViews.clear();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = this.mTempMargins;
    if (ViewUtils.isLayoutRtl(this))
    {
      i = 1;
      j = 0;
    }
    else
    {
      i = 0;
      j = 1;
    }
    if (shouldLayout(this.mNavButtonView))
    {
      measureChildConstrained(this.mNavButtonView, paramInt1, 0, paramInt2, 0, this.mMaxButtonHeight);
      k = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
      m = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
      n = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
    }
    else
    {
      k = 0;
      m = 0;
      n = 0;
    }
    int i1 = k;
    int i2 = m;
    int k = n;
    if (shouldLayout(this.mCollapseButtonView))
    {
      measureChildConstrained(this.mCollapseButtonView, paramInt1, 0, paramInt2, 0, this.mMaxButtonHeight);
      i1 = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
      i2 = Math.max(m, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView));
      k = View.combineMeasuredStates(n, this.mCollapseButtonView.getMeasuredState());
    }
    int n = getCurrentContentInsetStart();
    int m = 0 + Math.max(n, i1);
    arrayOfInt[i] = Math.max(0, n - i1);
    if (shouldLayout(this.mMenuView))
    {
      measureChildConstrained(this.mMenuView, paramInt1, m, paramInt2, 0, this.mMaxButtonHeight);
      n = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
      i2 = Math.max(i2, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
      k = View.combineMeasuredStates(k, this.mMenuView.getMeasuredState());
    }
    else
    {
      n = 0;
    }
    i1 = getCurrentContentInsetEnd();
    int i = m + Math.max(i1, n);
    arrayOfInt[j] = Math.max(0, i1 - n);
    int j = i;
    m = i2;
    n = k;
    if (shouldLayout(this.mExpandedActionView))
    {
      j = i + measureChildCollapseMargins(this.mExpandedActionView, paramInt1, i, paramInt2, 0, arrayOfInt);
      m = Math.max(i2, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
      n = View.combineMeasuredStates(k, this.mExpandedActionView.getMeasuredState());
    }
    i = j;
    i2 = m;
    k = n;
    if (shouldLayout(this.mLogoView))
    {
      i = j + measureChildCollapseMargins(this.mLogoView, paramInt1, j, paramInt2, 0, arrayOfInt);
      i2 = Math.max(m, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
      k = View.combineMeasuredStates(n, this.mLogoView.getMeasuredState());
    }
    int i3 = getChildCount();
    m = i;
    n = 0;
    for (j = i2; n < i3; j = i2)
    {
      View localView = getChildAt(n);
      i1 = m;
      i = k;
      i2 = j;
      if (((LayoutParams)localView.getLayoutParams()).mViewType == 0) {
        if (!shouldLayout(localView))
        {
          i1 = m;
          i = k;
          i2 = j;
        }
        else
        {
          i1 = m + measureChildCollapseMargins(localView, paramInt1, m, paramInt2, 0, arrayOfInt);
          i2 = Math.max(j, localView.getMeasuredHeight() + getVerticalMargins(localView));
          i = View.combineMeasuredStates(k, localView.getMeasuredState());
        }
      }
      n++;
      m = i1;
      k = i;
    }
    i1 = this.mTitleMarginTop + this.mTitleMarginBottom;
    i = this.mTitleMarginStart + this.mTitleMarginEnd;
    if (shouldLayout(this.mTitleTextView))
    {
      measureChildCollapseMargins(this.mTitleTextView, paramInt1, m + i, paramInt2, i1, arrayOfInt);
      n = this.mTitleTextView.getMeasuredWidth();
      i3 = getHorizontalMargins(this.mTitleTextView);
      int i4 = this.mTitleTextView.getMeasuredHeight();
      i2 = getVerticalMargins(this.mTitleTextView);
      k = View.combineMeasuredStates(k, this.mTitleTextView.getMeasuredState());
      i2 = i4 + i2;
      n += i3;
    }
    else
    {
      n = 0;
      i2 = 0;
    }
    if (shouldLayout(this.mSubtitleTextView))
    {
      n = Math.max(n, measureChildCollapseMargins(this.mSubtitleTextView, paramInt1, m + i, paramInt2, i2 + i1, arrayOfInt));
      i2 += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
      k = View.combineMeasuredStates(k, this.mSubtitleTextView.getMeasuredState());
    }
    j = Math.max(j, i2);
    i1 = getPaddingLeft();
    i3 = getPaddingRight();
    i2 = getPaddingTop();
    i = getPaddingBottom();
    n = View.resolveSizeAndState(Math.max(m + n + (i1 + i3), getSuggestedMinimumWidth()), paramInt1, 0xFF000000 & k);
    paramInt1 = View.resolveSizeAndState(Math.max(j + (i2 + i), getSuggestedMinimumHeight()), paramInt2, k << 16);
    if (shouldCollapse()) {
      paramInt1 = 0;
    }
    setMeasuredDimension(n, paramInt1);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    paramParcelable = this.mMenuView;
    if (paramParcelable != null) {
      paramParcelable = paramParcelable.peekMenu();
    } else {
      paramParcelable = null;
    }
    if ((localSavedState.expandedMenuItemId != 0) && (this.mExpandedMenuPresenter != null) && (paramParcelable != null))
    {
      paramParcelable = paramParcelable.findItem(localSavedState.expandedMenuItemId);
      if (paramParcelable != null) {
        paramParcelable.expandActionView();
      }
    }
    if (localSavedState.isOverflowOpen) {
      postShowOverflowMenu();
    }
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      super.onRtlPropertiesChanged(paramInt);
    }
    ensureContentInsets();
    aa localaa = this.mContentInsets;
    boolean bool = true;
    if (paramInt != 1) {
      bool = false;
    }
    localaa.a(bool);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    a locala = this.mExpandedMenuPresenter;
    if ((locala != null) && (locala.b != null)) {
      localSavedState.expandedMenuItemId = this.mExpandedMenuPresenter.b.getItemId();
    }
    localSavedState.isOverflowOpen = isOverflowMenuShowing();
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    if (i == 0) {
      this.mEatingTouch = false;
    }
    if (!this.mEatingTouch)
    {
      boolean bool = super.onTouchEvent(paramMotionEvent);
      if ((i == 0) && (!bool)) {
        this.mEatingTouch = true;
      }
    }
    if ((i == 1) || (i == 3)) {
      this.mEatingTouch = false;
    }
    return true;
  }
  
  void removeChildrenForExpandedActionView()
  {
    for (int i = getChildCount() - 1; i >= 0; i--)
    {
      View localView = getChildAt(i);
      if ((((LayoutParams)localView.getLayoutParams()).mViewType != 2) && (localView != this.mMenuView))
      {
        removeViewAt(i);
        this.mHiddenViews.add(localView);
      }
    }
  }
  
  public void setCollapsible(boolean paramBoolean)
  {
    this.mCollapsible = paramBoolean;
    requestLayout();
  }
  
  public void setContentInsetEndWithActions(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = Integer.MIN_VALUE;
    }
    if (i != this.mContentInsetEndWithActions)
    {
      this.mContentInsetEndWithActions = i;
      if (getNavigationIcon() != null) {
        requestLayout();
      }
    }
  }
  
  public void setContentInsetStartWithNavigation(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = Integer.MIN_VALUE;
    }
    if (i != this.mContentInsetStartWithNavigation)
    {
      this.mContentInsetStartWithNavigation = i;
      if (getNavigationIcon() != null) {
        requestLayout();
      }
    }
  }
  
  public void setContentInsetsAbsolute(int paramInt1, int paramInt2)
  {
    ensureContentInsets();
    this.mContentInsets.b(paramInt1, paramInt2);
  }
  
  public void setContentInsetsRelative(int paramInt1, int paramInt2)
  {
    ensureContentInsets();
    this.mContentInsets.a(paramInt1, paramInt2);
  }
  
  public void setLogo(int paramInt)
  {
    setLogo(b.b(getContext(), paramInt));
  }
  
  public void setLogo(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      ensureLogoView();
      if (!isChildOrHidden(this.mLogoView)) {
        addSystemView(this.mLogoView, true);
      }
    }
    else
    {
      localImageView = this.mLogoView;
      if ((localImageView != null) && (isChildOrHidden(localImageView)))
      {
        removeView(this.mLogoView);
        this.mHiddenViews.remove(this.mLogoView);
      }
    }
    ImageView localImageView = this.mLogoView;
    if (localImageView != null) {
      localImageView.setImageDrawable(paramDrawable);
    }
  }
  
  public void setLogoDescription(int paramInt)
  {
    setLogoDescription(getContext().getText(paramInt));
  }
  
  public void setLogoDescription(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      ensureLogoView();
    }
    ImageView localImageView = this.mLogoView;
    if (localImageView != null) {
      localImageView.setContentDescription(paramCharSequence);
    }
  }
  
  public void setMenu(h paramh, c paramc)
  {
    if ((paramh == null) && (this.mMenuView == null)) {
      return;
    }
    ensureMenuView();
    h localh = this.mMenuView.peekMenu();
    if (localh == paramh) {
      return;
    }
    if (localh != null)
    {
      localh.b(this.mOuterActionMenuPresenter);
      localh.b(this.mExpandedMenuPresenter);
    }
    if (this.mExpandedMenuPresenter == null) {
      this.mExpandedMenuPresenter = new a();
    }
    paramc.c(true);
    if (paramh != null)
    {
      paramh.a(paramc, this.mPopupContext);
      paramh.a(this.mExpandedMenuPresenter, this.mPopupContext);
    }
    else
    {
      paramc.a(this.mPopupContext, null);
      this.mExpandedMenuPresenter.a(this.mPopupContext, null);
      paramc.a(true);
      this.mExpandedMenuPresenter.a(true);
    }
    this.mMenuView.setPopupTheme(this.mPopupTheme);
    this.mMenuView.setPresenter(paramc);
    this.mOuterActionMenuPresenter = paramc;
  }
  
  public void setMenuCallbacks(o.a parama, h.a parama1)
  {
    this.mActionMenuPresenterCallback = parama;
    this.mMenuBuilderCallback = parama1;
    ActionMenuView localActionMenuView = this.mMenuView;
    if (localActionMenuView != null) {
      localActionMenuView.setMenuCallbacks(parama, parama1);
    }
  }
  
  public void setNavigationContentDescription(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getContext().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setNavigationContentDescription(localCharSequence);
  }
  
  public void setNavigationContentDescription(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence)) {
      ensureNavButtonView();
    }
    ImageButton localImageButton = this.mNavButtonView;
    if (localImageButton != null) {
      localImageButton.setContentDescription(paramCharSequence);
    }
  }
  
  public void setNavigationIcon(int paramInt)
  {
    setNavigationIcon(b.b(getContext(), paramInt));
  }
  
  public void setNavigationIcon(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      ensureNavButtonView();
      if (!isChildOrHidden(this.mNavButtonView)) {
        addSystemView(this.mNavButtonView, true);
      }
    }
    else
    {
      localImageButton = this.mNavButtonView;
      if ((localImageButton != null) && (isChildOrHidden(localImageButton)))
      {
        removeView(this.mNavButtonView);
        this.mHiddenViews.remove(this.mNavButtonView);
      }
    }
    ImageButton localImageButton = this.mNavButtonView;
    if (localImageButton != null) {
      localImageButton.setImageDrawable(paramDrawable);
    }
  }
  
  public void setNavigationOnClickListener(View.OnClickListener paramOnClickListener)
  {
    ensureNavButtonView();
    this.mNavButtonView.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable)
  {
    ensureMenu();
    this.mMenuView.setOverflowIcon(paramDrawable);
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
  
  public void setSubtitle(int paramInt)
  {
    setSubtitle(getContext().getText(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      if (this.mSubtitleTextView == null)
      {
        localObject = getContext();
        this.mSubtitleTextView = new AppCompatTextView((Context)localObject);
        this.mSubtitleTextView.setSingleLine();
        this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        int i = this.mSubtitleTextAppearance;
        if (i != 0) {
          this.mSubtitleTextView.setTextAppearance((Context)localObject, i);
        }
        i = this.mSubtitleTextColor;
        if (i != 0) {
          this.mSubtitleTextView.setTextColor(i);
        }
      }
      if (!isChildOrHidden(this.mSubtitleTextView)) {
        addSystemView(this.mSubtitleTextView, true);
      }
    }
    else
    {
      localObject = this.mSubtitleTextView;
      if ((localObject != null) && (isChildOrHidden((View)localObject)))
      {
        removeView(this.mSubtitleTextView);
        this.mHiddenViews.remove(this.mSubtitleTextView);
      }
    }
    Object localObject = this.mSubtitleTextView;
    if (localObject != null) {
      ((TextView)localObject).setText(paramCharSequence);
    }
    this.mSubtitleText = paramCharSequence;
  }
  
  public void setSubtitleTextAppearance(Context paramContext, int paramInt)
  {
    this.mSubtitleTextAppearance = paramInt;
    TextView localTextView = this.mSubtitleTextView;
    if (localTextView != null) {
      localTextView.setTextAppearance(paramContext, paramInt);
    }
  }
  
  public void setSubtitleTextColor(int paramInt)
  {
    this.mSubtitleTextColor = paramInt;
    TextView localTextView = this.mSubtitleTextView;
    if (localTextView != null) {
      localTextView.setTextColor(paramInt);
    }
  }
  
  public void setTitle(int paramInt)
  {
    setTitle(getContext().getText(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      if (this.mTitleTextView == null)
      {
        localObject = getContext();
        this.mTitleTextView = new AppCompatTextView((Context)localObject);
        this.mTitleTextView.setSingleLine();
        this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        int i = this.mTitleTextAppearance;
        if (i != 0) {
          this.mTitleTextView.setTextAppearance((Context)localObject, i);
        }
        i = this.mTitleTextColor;
        if (i != 0) {
          this.mTitleTextView.setTextColor(i);
        }
      }
      if (!isChildOrHidden(this.mTitleTextView)) {
        addSystemView(this.mTitleTextView, true);
      }
    }
    else
    {
      localObject = this.mTitleTextView;
      if ((localObject != null) && (isChildOrHidden((View)localObject)))
      {
        removeView(this.mTitleTextView);
        this.mHiddenViews.remove(this.mTitleTextView);
      }
    }
    Object localObject = this.mTitleTextView;
    if (localObject != null) {
      ((TextView)localObject).setText(paramCharSequence);
    }
    this.mTitleText = paramCharSequence;
  }
  
  public void setTitleMargin(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mTitleMarginStart = paramInt1;
    this.mTitleMarginTop = paramInt2;
    this.mTitleMarginEnd = paramInt3;
    this.mTitleMarginBottom = paramInt4;
    requestLayout();
  }
  
  public void setTitleMarginBottom(int paramInt)
  {
    this.mTitleMarginBottom = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginEnd(int paramInt)
  {
    this.mTitleMarginEnd = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginStart(int paramInt)
  {
    this.mTitleMarginStart = paramInt;
    requestLayout();
  }
  
  public void setTitleMarginTop(int paramInt)
  {
    this.mTitleMarginTop = paramInt;
    requestLayout();
  }
  
  public void setTitleTextAppearance(Context paramContext, int paramInt)
  {
    this.mTitleTextAppearance = paramInt;
    TextView localTextView = this.mTitleTextView;
    if (localTextView != null) {
      localTextView.setTextAppearance(paramContext, paramInt);
    }
  }
  
  public void setTitleTextColor(int paramInt)
  {
    this.mTitleTextColor = paramInt;
    TextView localTextView = this.mTitleTextView;
    if (localTextView != null) {
      localTextView.setTextColor(paramInt);
    }
  }
  
  public boolean showOverflowMenu()
  {
    ActionMenuView localActionMenuView = this.mMenuView;
    boolean bool;
    if ((localActionMenuView != null) && (localActionMenuView.showOverflowMenu())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static class LayoutParams
    extends android.support.v7.app.a.a
  {
    static final int CUSTOM = 0;
    static final int EXPANDED = 2;
    static final int SYSTEM = 1;
    int mViewType = 0;
    
    public LayoutParams(int paramInt)
    {
      this(-2, -1, paramInt);
    }
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.gravity = 8388627;
    }
    
    public LayoutParams(int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt2);
      this.gravity = paramInt3;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(android.support.v7.app.a.a parama)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.mViewType = paramLayoutParams.mViewType;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
      copyMarginsFromCompat(paramMarginLayoutParams);
    }
    
    void copyMarginsFromCompat(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      this.leftMargin = paramMarginLayoutParams.leftMargin;
      this.topMargin = paramMarginLayoutParams.topMargin;
      this.rightMargin = paramMarginLayoutParams.rightMargin;
      this.bottomMargin = paramMarginLayoutParams.bottomMargin;
    }
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public Toolbar.SavedState a(Parcel paramAnonymousParcel)
      {
        return new Toolbar.SavedState(paramAnonymousParcel, null);
      }
      
      public Toolbar.SavedState a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new Toolbar.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public Toolbar.SavedState[] a(int paramAnonymousInt)
      {
        return new Toolbar.SavedState[paramAnonymousInt];
      }
    };
    int expandedMenuItemId;
    boolean isOverflowOpen;
    
    public SavedState(Parcel paramParcel)
    {
      this(paramParcel, null);
    }
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.expandedMenuItemId = paramParcel.readInt();
      boolean bool;
      if (paramParcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.isOverflowOpen = bool;
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.expandedMenuItemId);
      paramParcel.writeInt(this.isOverflowOpen);
    }
  }
  
  private class a
    implements o
  {
    h a;
    j b;
    
    a() {}
    
    public void a(Context paramContext, h paramh)
    {
      h localh = this.a;
      if (localh != null)
      {
        paramContext = this.b;
        if (paramContext != null) {
          localh.d(paramContext);
        }
      }
      this.a = paramh;
    }
    
    public void a(Parcelable paramParcelable) {}
    
    public void a(h paramh, boolean paramBoolean) {}
    
    public void a(o.a parama) {}
    
    public void a(boolean paramBoolean)
    {
      if (this.b != null)
      {
        h localh = this.a;
        int i = 0;
        int j = i;
        if (localh != null)
        {
          int k = localh.size();
          for (int m = 0;; m++)
          {
            j = i;
            if (m >= k) {
              break;
            }
            if (this.a.getItem(m) == this.b)
            {
              j = 1;
              break;
            }
          }
        }
        if (j == 0) {
          b(this.a, this.b);
        }
      }
    }
    
    public boolean a()
    {
      return false;
    }
    
    public boolean a(h paramh, j paramj)
    {
      Toolbar.this.ensureCollapseButtonView();
      Object localObject = Toolbar.this.mCollapseButtonView.getParent();
      paramh = Toolbar.this;
      if (localObject != paramh) {
        paramh.addView(paramh.mCollapseButtonView);
      }
      Toolbar.this.mExpandedActionView = paramj.getActionView();
      this.b = paramj;
      paramh = Toolbar.this.mExpandedActionView.getParent();
      localObject = Toolbar.this;
      if (paramh != localObject)
      {
        paramh = ((Toolbar)localObject).generateDefaultLayoutParams();
        paramh.gravity = (0x800003 | Toolbar.this.mButtonGravity & 0x70);
        paramh.mViewType = 2;
        Toolbar.this.mExpandedActionView.setLayoutParams(paramh);
        paramh = Toolbar.this;
        paramh.addView(paramh.mExpandedActionView);
      }
      Toolbar.this.removeChildrenForExpandedActionView();
      Toolbar.this.requestLayout();
      paramj.e(true);
      if ((Toolbar.this.mExpandedActionView instanceof android.support.v7.view.c)) {
        ((android.support.v7.view.c)Toolbar.this.mExpandedActionView).onActionViewExpanded();
      }
      return true;
    }
    
    public boolean a(u paramu)
    {
      return false;
    }
    
    public int b()
    {
      return 0;
    }
    
    public boolean b(h paramh, j paramj)
    {
      if ((Toolbar.this.mExpandedActionView instanceof android.support.v7.view.c)) {
        ((android.support.v7.view.c)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
      }
      paramh = Toolbar.this;
      paramh.removeView(paramh.mExpandedActionView);
      paramh = Toolbar.this;
      paramh.removeView(paramh.mCollapseButtonView);
      paramh = Toolbar.this;
      paramh.mExpandedActionView = null;
      paramh.addChildrenForExpandedActionView();
      this.b = null;
      Toolbar.this.requestLayout();
      paramj.e(false);
      return true;
    }
    
    public Parcelable c()
    {
      return null;
    }
  }
}


/* Location:              ~/android/support/v7/widget/Toolbar.class
 *
 * Reversed by:           J
 */